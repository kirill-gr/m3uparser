grammar M3u;

// Parser

file: file_header (WS | NEWLINE)+ channels;
file_header: FILE_START (WS | NEWLINE)+ parameters;
channels: channel_info ((WS | NEWLINE)+ channel_info)+;
channel_info: CHANNEL_START COLON length (WS | NEWLINE)+ parameters COMMA channel_name NEWLINE channel_url;
length: NUMBER;
channel_name: (SAFE_STRING | NUMBER | COLON | EQUALS | WS)+ | QUOTED_STRING;
channel_url: (SAFE_STRING | NUMBER | COLON | EQUALS | WS)+;
parameters: parameter ((WS | NEWLINE)+ parameter)*;
parameter: key EQUALS value;
key: SAFE_STRING;
value: (SAFE_STRING | NUMBER | COLON)+ | QUOTED_STRING;

// Lexer

WS: SPACES+;
NEWLINE: ('\r\n' | '\r' | '\n') ;
FILE_START: '#EXTM3U';
CHANNEL_START: '#EXTINF';
NUMBER: '-'? DIGIT+;
SAFE_STRING: SAFE_CHAR+;
QUOTED_STRING: QUOTE (SAFE_CHAR | NUMBER | COLON | EQUALS | SPACES | COMMA)+ QUOTE;
COMMA: ',';
COLON: ':';
EQUALS: '=';

fragment QUOTE: '"';
fragment SPACES: [ \t];
fragment DIGIT: [0-9];
fragment SAFE_CHAR: ~[":= \t,\r\n];
