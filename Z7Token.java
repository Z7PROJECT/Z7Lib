/*
 * Decompiled with CFR 0.152.
 */
package com.z7.lib.lang.parser;

public class Z7Token {
    private final TokenType type;
    private final String value;
    private final int line;
    private final int column;

    public Z7Token(TokenType type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public String toString() {
        return String.valueOf((Object)this.type) + "('" + this.value + "', " + this.line + ":" + this.column + ")";
    }

    public static enum TokenType {
        MOD,
        CLASS,
        FUNCTION,
        FUNC,
        VOID,
        INT,
        DOUBLE,
        BOOLEAN,
        STRING,
        VAR,
        CONST,
        IF,
        ELSE,
        ELSE_IF,
        FOR,
        WHILE,
        REPEAT,
        RETURN,
        RET,
        BREAK,
        CONTINUE,
        AND,
        OR,
        NOT,
        PRINT,
        PRINTLN,
        INPUT,
        READ,
        TRUE,
        FALSE,
        NULL,
        IMPORT,
        INCLUDE,
        NEW,
        THIS,
        SUPER,
        PUBLIC,
        PRIVATE,
        PROTECTED,
        STATIC,
        PLAYER,
        WORLD,
        ITEM,
        BLOCK,
        INVENTORY,
        CHAT,
        COMMAND,
        IDENTIFIER,
        NUMBER,
        STRING_LITERAL,
        CHAR_LITERAL,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        MODULO,
        ASSIGN,
        EQUAL,
        NOT_EQUAL,
        LESS,
        GREATER,
        LESS_OR_EQUAL,
        GREATER_OR_EQUAL,
        LPAREN,
        RPAREN,
        LBRACE,
        RBRACE,
        LBRACKET,
        RBRACKET,
        SEMICOLON,
        COMMA,
        DOT,
        EOF;

    }
}

