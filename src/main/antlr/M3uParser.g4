parser grammar M3uParser;

options { tokenVocab=M3uLexer; }

file: file_header entries;
file_header: FILE_START parameters;
entries: entry_info+;
entry_info: (CHANNEL_START | CHANNEL_DESC_CHANNEL_START) COLON length parameters COMMA enrty_basic_info;
length: NUMBER;
enrty_basic_info: entry_name (CHANNEL_DESC_GROUP CHANNEL_GROUP_COLON group_name)? (CHANNEL_GROUP_HASH comment)* entry_uri;
entry_name: CHANNEL_DESC_TEXT;
group_name: CHANNEL_GROUP_TEXT;
comment: COMMENT_TEXT;
entry_uri: CHANNEL_DESC_TEXT;
parameters: parameter*;
parameter: key EQUALS value;
key: SAFE_STRING;
value: (SAFE_STRING | NUMBER | COLON)+ | QUOTED_STRING;
