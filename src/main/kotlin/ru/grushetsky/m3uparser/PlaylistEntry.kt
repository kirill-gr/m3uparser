package ru.grushetsky.m3uparser

data class PlaylistEntry(
    val title: String,
    val path: String,
    val length: Int,
    val group: String?,
    val properties: Map<String, String>
)