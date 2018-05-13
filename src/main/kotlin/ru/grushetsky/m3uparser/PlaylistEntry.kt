package ru.grushetsky.m3uparser

import java.net.URI

data class PlaylistEntry(val name: String,
                         val uri: URI,
                         val length: Int,
                         val parameters: Map<String, String>)