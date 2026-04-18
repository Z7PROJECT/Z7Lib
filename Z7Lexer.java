/*
 * Decompiled with CFR 0.152.
 */
package com.z7.lib.lang.parser;

import com.z7.lib.lang.Z7LangKeywords;
import com.z7.lib.lang.parser.Z7Token;
import java.util.ArrayList;
import java.util.List;

public class Z7Lexer {
    private String source;
    private int pos;
    private int line;
    private int column;
    private List<Z7Token> tokens;

    public Z7Lexer(String source) {
        this.source = source;
        this.pos = 0;
        this.line = 1;
        this.column = 1;
        this.tokens = new ArrayList<Z7Token>();
    }

    public List<Z7Token> tokenize() {
        while (this.pos < this.source.length()) {
            char c = this.peek();
            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    ++this.line;
                    this.column = 1;
                } else {
                    ++this.column;
                }
                this.advance();
                continue;
            }
            if (c == '/' && this.peekNext() == '/') {
                this.skipLineComment();
                continue;
            }
            if (c == '#') {
                this.skipLineComment();
                continue;
            }
            if (c == '\"') {
                this.tokens.add(this.readString());
                continue;
            }
            if (c == '\'') {
                this.tokens.add(this.readChar());
                continue;
            }
            if (Character.isDigit(c)) {
                this.tokens.add(this.readNumber());
                continue;
            }
            if (Character.isLetter(c) || c == '_') {
                this.tokens.add(this.readIdentifier());
                continue;
            }
            this.tokens.add(this.readOperator());
        }
        this.tokens.add(new Z7Token(Z7Token.TokenType.EOF, "", this.line, this.column));
        return this.tokens;
    }

    private char peek() {
        return this.pos >= this.source.length() ? (char)'\u0000' : this.source.charAt(this.pos);
    }

    private char peekNext() {
        return this.pos + 1 >= this.source.length() ? (char)'\u0000' : this.source.charAt(this.pos + 1);
    }

    private char advance() {
        ++this.column;
        return this.source.charAt(this.pos++);
    }

    private void skipLineComment() {
        while (this.pos < this.source.length() && this.peek() != '\n') {
            this.advance();
        }
    }

    private Z7Token readString() {
        int startLine = this.line;
        int startCol = this.column;
        this.advance();
        StringBuilder value = new StringBuilder();
        block7: while (this.pos < this.source.length() && this.peek() != '\"') {
            if (this.peek() == '\\' && this.pos + 1 < this.source.length()) {
                this.advance();
                char escaped = this.advance();
                switch (escaped) {
                    case 'n': {
                        value.append('\n');
                        continue block7;
                    }
                    case 't': {
                        value.append('\t');
                        continue block7;
                    }
                    case 'r': {
                        value.append('\r');
                        continue block7;
                    }
                    case '\\': {
                        value.append('\\');
                        continue block7;
                    }
                    case '\"': {
                        value.append('\"');
                        continue block7;
                    }
                }
                value.append(escaped);
                continue;
            }
            value.append(this.advance());
        }
        this.advance();
        return new Z7Token(Z7Token.TokenType.STRING_LITERAL, value.toString(), startLine, startCol);
    }

    private Z7Token readChar() {
        int startLine = this.line;
        int startCol = this.column;
        this.advance();
        StringBuilder value = new StringBuilder();
        while (this.pos < this.source.length() && this.peek() != '\'') {
            value.append(this.advance());
        }
        this.advance();
        return new Z7Token(Z7Token.TokenType.CHAR_LITERAL, value.toString(), startLine, startCol);
    }

    private Z7Token readNumber() {
        int startLine = this.line;
        int startCol = this.column;
        StringBuilder value = new StringBuilder();
        while (this.pos < this.source.length() && (Character.isDigit(this.peek()) || this.peek() == '.')) {
            value.append(this.advance());
        }
        return new Z7Token(Z7Token.TokenType.NUMBER, value.toString(), startLine, startCol);
    }

    private Z7Token readIdentifier() {
        int startLine = this.line;
        int startCol = this.column;
        StringBuilder value = new StringBuilder();
        while (this.pos < this.source.length() && (Character.isLetterOrDigit(this.peek()) || this.peek() == '_')) {
            value.append(this.advance());
        }
        String identifier = value.toString();
        Z7Token.TokenType type = this.getKeywordType(identifier);
        return new Z7Token(type, identifier, startLine, startCol);
    }

    private Z7Token.TokenType getKeywordType(String word) {
        String translated = Z7LangKeywords.translate(word);
        switch (translated.toLowerCase()) {
            case "mod": {
                return Z7Token.TokenType.MOD;
            }
            case "class": {
                return Z7Token.TokenType.CLASS;
            }
            case "function": 
            case "func": {
                return Z7Token.TokenType.FUNCTION;
            }
            case "void": {
                return Z7Token.TokenType.VOID;
            }
            case "int": {
                return Z7Token.TokenType.INT;
            }
            case "double": {
                return Z7Token.TokenType.DOUBLE;
            }
            case "boolean": {
                return Z7Token.TokenType.BOOLEAN;
            }
            case "string": {
                return Z7Token.TokenType.STRING;
            }
            case "var": {
                return Z7Token.TokenType.VAR;
            }
            case "const": {
                return Z7Token.TokenType.CONST;
            }
            case "if": {
                return Z7Token.TokenType.IF;
            }
            case "else": {
                return Z7Token.TokenType.ELSE;
            }
            case "else_if": {
                return Z7Token.TokenType.ELSE_IF;
            }
            case "for": {
                return Z7Token.TokenType.FOR;
            }
            case "while": {
                return Z7Token.TokenType.WHILE;
            }
            case "repeat": {
                return Z7Token.TokenType.REPEAT;
            }
            case "return": 
            case "ret": {
                return Z7Token.TokenType.RETURN;
            }
            case "break": {
                return Z7Token.TokenType.BREAK;
            }
            case "continue": {
                return Z7Token.TokenType.CONTINUE;
            }
            case "and": {
                return Z7Token.TokenType.AND;
            }
            case "or": {
                return Z7Token.TokenType.OR;
            }
            case "not": {
                return Z7Token.TokenType.NOT;
            }
            case "print": {
                return Z7Token.TokenType.PRINT;
            }
            case "println": {
                return Z7Token.TokenType.PRINTLN;
            }
            case "input": {
                return Z7Token.TokenType.INPUT;
            }
            case "read": {
                return Z7Token.TokenType.READ;
            }
            case "true": {
                return Z7Token.TokenType.TRUE;
            }
            case "false": {
                return Z7Token.TokenType.FALSE;
            }
            case "null": {
                return Z7Token.TokenType.NULL;
            }
            case "import": {
                return Z7Token.TokenType.IMPORT;
            }
            case "include": {
                return Z7Token.TokenType.INCLUDE;
            }
            case "new": {
                return Z7Token.TokenType.NEW;
            }
            case "this": {
                return Z7Token.TokenType.THIS;
            }
            case "super": {
                return Z7Token.TokenType.SUPER;
            }
            case "public": {
                return Z7Token.TokenType.PUBLIC;
            }
            case "private": {
                return Z7Token.TokenType.PRIVATE;
            }
            case "protected": {
                return Z7Token.TokenType.PROTECTED;
            }
            case "static": {
                return Z7Token.TokenType.STATIC;
            }
            case "player": {
                return Z7Token.TokenType.PLAYER;
            }
            case "world": {
                return Z7Token.TokenType.WORLD;
            }
            case "item": {
                return Z7Token.TokenType.ITEM;
            }
            case "block": {
                return Z7Token.TokenType.BLOCK;
            }
            case "inventory": {
                return Z7Token.TokenType.INVENTORY;
            }
            case "chat": {
                return Z7Token.TokenType.CHAT;
            }
            case "command": {
                return Z7Token.TokenType.COMMAND;
            }
        }
        return Z7Token.TokenType.IDENTIFIER;
    }

    private Z7Token readOperator() {
        int startLine = this.line;
        int startCol = this.column;
        char c = this.advance();
        switch (c) {
            case '+': {
                return new Z7Token(Z7Token.TokenType.PLUS, "+", startLine, startCol);
            }
            case '-': {
                return new Z7Token(Z7Token.TokenType.MINUS, "-", startLine, startCol);
            }
            case '*': {
                return new Z7Token(Z7Token.TokenType.MULTIPLY, "*", startLine, startCol);
            }
            case '/': {
                return new Z7Token(Z7Token.TokenType.DIVIDE, "/", startLine, startCol);
            }
            case '%': {
                return new Z7Token(Z7Token.TokenType.MODULO, "%", startLine, startCol);
            }
            case '=': {
                if (this.peek() == '=') {
                    this.advance();
                    return new Z7Token(Z7Token.TokenType.EQUAL, "==", startLine, startCol);
                }
                return new Z7Token(Z7Token.TokenType.ASSIGN, "=", startLine, startCol);
            }
            case '!': {
                if (this.peek() == '=') {
                    this.advance();
                    return new Z7Token(Z7Token.TokenType.NOT_EQUAL, "!=", startLine, startCol);
                }
                return new Z7Token(Z7Token.TokenType.NOT, "!", startLine, startCol);
            }
            case '<': {
                if (this.peek() == '=') {
                    this.advance();
                    return new Z7Token(Z7Token.TokenType.LESS_OR_EQUAL, "<=", startLine, startCol);
                }
                return new Z7Token(Z7Token.TokenType.LESS, "<", startLine, startCol);
            }
            case '>': {
                if (this.peek() == '=') {
                    this.advance();
                    return new Z7Token(Z7Token.TokenType.GREATER_OR_EQUAL, ">=", startLine, startCol);
                }
                return new Z7Token(Z7Token.TokenType.GREATER, ">", startLine, startCol);
            }
            case '&': {
                if (this.peek() == '&') {
                    this.advance();
                    return new Z7Token(Z7Token.TokenType.AND, "&&", startLine, startCol);
                }
                return new Z7Token(Z7Token.TokenType.AND, "&", startLine, startCol);
            }
            case '|': {
                if (this.peek() == '|') {
                    this.advance();
                    return new Z7Token(Z7Token.TokenType.OR, "||", startLine, startCol);
                }
                return new Z7Token(Z7Token.TokenType.OR, "|", startLine, startCol);
            }
            case '(': {
                return new Z7Token(Z7Token.TokenType.LPAREN, "(", startLine, startCol);
            }
            case ')': {
                return new Z7Token(Z7Token.TokenType.RPAREN, ")", startLine, startCol);
            }
            case '{': {
                return new Z7Token(Z7Token.TokenType.LBRACE, "{", startLine, startCol);
            }
            case '}': {
                return new Z7Token(Z7Token.TokenType.RBRACE, "}", startLine, startCol);
            }
            case '[': {
                return new Z7Token(Z7Token.TokenType.LBRACKET, "[", startLine, startCol);
            }
            case ']': {
                return new Z7Token(Z7Token.TokenType.RBRACKET, "]", startLine, startCol);
            }
            case ';': {
                return new Z7Token(Z7Token.TokenType.SEMICOLON, ";", startLine, startCol);
            }
            case ',': {
                return new Z7Token(Z7Token.TokenType.COMMA, ",", startLine, startCol);
            }
            case '.': {
                return new Z7Token(Z7Token.TokenType.DOT, ".", startLine, startCol);
            }
        }
        return new Z7Token(Z7Token.TokenType.IDENTIFIER, String.valueOf(c), startLine, startCol);
    }
}

