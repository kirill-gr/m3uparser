package ru.grushetsky.m3uparser

interface M3uParserWrapper {
    fun getPlaylistParameters(): Map<String, String>
    fun getPlaylistEntries(): List<PlaylistEntry>
}