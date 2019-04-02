package ru.grushetsky.m3uparser

data class PlaylistEntry(
    val name: String,
    val path: String,
    val length: Int,
    val group: String?,
    val parameters: Map<String, String>
)