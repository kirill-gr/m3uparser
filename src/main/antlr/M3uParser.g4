parser grammar M3uParser;

options { tokenVocab=M3uLexer; }

file: file_header entries;
file_header: FILE_START parameters;
entries: entry_info+;
entry_info: (CHANNEL_START | FREE_TEXT_CHANNEL_START) COLON length parameters COMMA enrty_basic_info;
length: NUMBER;
enrty_basic_info: entry_name entry_uri;
entry_name: FREE_TEXT_TEXT;
entry_uri: FREE_TEXT_TEXT;
parameters: parameter*;
parameter: key EQUALS value;
key: SAFE_STRING;
value: (SAFE_STRING | NUMBER | COLON)+ | QUOTED_STRING;
