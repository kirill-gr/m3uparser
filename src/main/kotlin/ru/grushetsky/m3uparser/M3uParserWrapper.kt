package ru.grushetsky.m3uparser

interface M3uParserWrapper {
    fun getPlaylistProperties(): Map<String, String>
    fun getPlaylistEntries(): List<PlaylistEntry>
}