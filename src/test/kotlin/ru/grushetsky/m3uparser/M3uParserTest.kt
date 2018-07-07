package ru.grushetsky.m3uparser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class M3uParserTest {

    companion object {
        const val FREE_TEXT_MODE = 1
    }

    private fun getParser(input: String, initMode: Int = 0): M3uParser {
        val m3uLexer = M3uLexer(CharStreams.fromString(input))
        m3uLexer.mode(initMode)
        return M3uParser(CommonTokenStream(m3uLexer))
    }

    private fun getResourceAsString(filePath: String): String =
            this.javaClass.getResource(filePath).readText()

    @ParameterizedTest
    @CsvSource(
            "foo=bar,      foo,   bar",
            "ratio=16:9,   ratio, 16:9"
    )
    fun `parameter parsed correctly`(parameter: String, expectedKey: String, expectedValue: String) {
        val parameterParser = getParser(parameter).parameter()

        assertThat(parameterParser.key().text).isEqualTo(expectedKey)
        assertThat(parameterParser.value().text).isEqualTo(expectedValue)
    }

    @Test // junit CsvSource doesn't handle escaped characters correctly
    fun `parameter with special characters parsed correctly`() {
        `parameter parsed correctly`("foo=\",=:\"", "foo", "\",=:\"")
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "safe_name\n12345",
        "12345\n:= ",
        ":= \nsafe_name",
        "\"quoted name\"\nfoo"
    ])
    fun `entry basic info parsed correctly`(entryBasicInfo: String) {
        val entryNameParser = getParser(entryBasicInfo, FREE_TEXT_MODE).enrty_basic_info()
        val (entryName, entryUri) = entryBasicInfo.split("\n")

        assertThat(entryNameParser.entry_name().text).isEqualTo(entryName)
        assertThat(entryNameParser.entry_uri().text).isEqualTo(entryUri)
    }

    @Test
    fun `entry elements detected correctly`() {
        val entryParser = getParser(getResourceAsString("/entry_example.txt")).entry_info()

        assertThat(entryParser.enrty_basic_info().entry_name().text).isEqualTo("Channel 1")
        assertThat(entryParser.enrty_basic_info().entry_uri().text).isEqualTo("http://1.2.3.4:1234/udp/4.3.2.1:5678")
        assertThat(entryParser.parameters().parameter().size).isEqualTo(3)
        assertThat(entryParser.length().text).isEqualTo("-1")
    }

    @ParameterizedTest
    @CsvSource(
            "header_example.txt, 5",
            "playlist_no_header_params.txt, 0"
    )
    fun `file header parsed correctly`(filePath: String, paramNumber: Int) {
        val headerParser = getParser(getResourceAsString("/$filePath")).file_header()

        assertThat(headerParser.parameters().parameter().size).isEqualTo(paramNumber)
    }

    @ParameterizedTest
    @CsvSource(
            "playlist_example.txt",
            "playlist_no_header_params.txt"
    )
    fun `playlist elements detected correctly`(filePath: String) {
        val fileParser = getParser(getResourceAsString("/$filePath")).file()

        assertThat(fileParser.entries().entry_info().size).isEqualTo(2)
    }
}