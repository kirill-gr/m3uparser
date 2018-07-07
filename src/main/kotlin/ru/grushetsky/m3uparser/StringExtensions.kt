package ru.grushetsky.m3uparser

internal fun String.withoutBom(): String {
    return if (this.isNotEmpty() && this[0] == '\uFEFF')
        this.drop(1)
    else
        this
}