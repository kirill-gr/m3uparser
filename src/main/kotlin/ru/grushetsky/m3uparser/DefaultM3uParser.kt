package ru.grushetsky.m3uparser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class DefaultM3uParser(input: String) : M3uParserWrapper {
    private val parser: M3uParser

    init {
        val m3uLexer = M3uLexer(CharStreams.fromString(input.withoutBom()))
        parser = M3uParser(CommonTokenStream(m3uLexer))
    }

    override fun getPlaylistProperties(): Map<String, String> {
        return createParameterMap(parser.file().file_header().parameters().parameter())
    }

    override fun getPlaylistEntries(): List<PlaylistEntry> {
        return parser.file().entries().entry_info()
            .map { createPlaylistEntry(it) }
    }

    private fun createPlaylistEntry(parsedEntry: M3uParser.Entry_infoContext): PlaylistEntry {
        return PlaylistEntry(
            parsedEntry.enrty_basic_info().entry_name().text.trim(),
            parsedEntry.enrty_basic_info().entry_uri().text.trim(),
            parsedEntry.length().text.toInt(),
            parsedEntry.enrty_basic_info().group_name()?.text?.trim(),
            createParameterMap(parsedEntry.parameters().parameter())
        )
    }

    private fun createParameterMap(parsedParameters: List<M3uParser.ParameterContext>): Map<String, String> {
        return parsedParameters.associateBy({ it.key().text }, { it.value().text })
    }
}
