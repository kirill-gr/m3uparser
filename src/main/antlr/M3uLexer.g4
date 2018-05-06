lexer grammar M3uLexer;

WS: SPACES+ -> skip;
NEWLINE: NEWLINES -> skip;
FILE_START: '#EXTM3U';
CHANNEL_START: EXTINF;
NUMBER: '-'? DIGIT+;
SAFE_STRING: SAFE_CHAR+;
QUOTED_STRING: QUOTE (SAFE_CHAR | NUMBER | COLON | EQUALS | SPACES | COMMA)+ QUOTE;
COMMA: ',' -> pushMode(FREE_TEXT);
COLON: ':';
EQUALS: '=';

mode FREE_TEXT;

FREE_TEXT_NEWLINE: NEWLINES -> skip;
FREE_TEXT_CHANNEL_START: EXTINF -> popMode;
FREE_TEXT_TEXT: FREE_TEXT_SAFE_CHAR+;

fragment NEWLINES: ('\r\n' | '\r' | '\n');
fragment EXTINF: '#EXTINF';
fragment QUOTE: '"';
fragment SPACES: [ \t];
fragment DIGIT: [0-9];
fragment SAFE_CHAR: ~[":= \t,\r\n];
fragment FREE_TEXT_SAFE_CHAR: ~[#\r\n];
