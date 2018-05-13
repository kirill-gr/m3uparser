package ru.grushetsky.m3uparser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.net.URI

class M3uParserWrapper(input: String) {

    private val parser: M3uParser

    init {
        val m3uLexer = M3uLexer(CharStreams.fromString(input))
        parser = M3uParser(CommonTokenStream(m3uLexer))
    }

    fun getPlaylistParameters(): Map<String, String> {
        return createParameterMap(parser.file_header().parameters().parameter())
    }

    fun getPlaylistEntries(): List<PlaylistEntry> {
        return parser.entries().entry_info()
                .map { createPlaylistEntry(it) }
    }

    private fun createPlaylistEntry(parsedEntry: M3uParser.Entry_infoContext): PlaylistEntry {
        return PlaylistEntry(
                parsedEntry.enrty_basic_info().entry_name().text,
                URI(parsedEntry.enrty_basic_info().entry_uri().text),
                parsedEntry.length().text.toInt(),
                createParameterMap(parsedEntry.parameters().parameter())
        )
    }

    private fun createParameterMap(parsedParameters: List<M3uParser.ParameterContext>): Map<String, String> {
        return parsedParameters.associateBy({ it.key().text }, { it.value().text })
    }
}