/*
 * Decompiled with CFR 0.152.
 */
package com.z7.lib.lang.compiler;

import com.z7.lib.lang.Z7LangKeywords;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Z7LangCompiler {
    private List<String> errors = new ArrayList<String>();
    private List<String> warnings = new ArrayList<String>();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Z7Lang Compiler v1.1.0");
            System.out.println("Usage: z7lang <input.z7> [output.java]");
            return;
        }
        Z7LangCompiler compiler = new Z7LangCompiler();
        File inputFile = new File(args[0]);
        File outputFile = args.length > 1 ? new File(args[1]) : new File(inputFile.getName().replace(".z7", ".java"));
        try {
            String source = Z7LangCompiler.readFile(inputFile);
            String javaCode = compiler.compile(source);
            Z7LangCompiler.writeFile(outputFile, javaCode);
            System.out.println("\u2713 Compiled: " + outputFile.getName());
            if (!compiler.getErrors().isEmpty()) {
                System.out.println("Errors: " + compiler.getErrors().size());
                for (String error : compiler.getErrors()) {
                    System.out.println("  \u2717 " + error);
                }
            }
        }
        catch (Exception e) {
            System.out.println("\u2717 Compilation failed: " + e.getMessage());
        }
    }

    public String compile(String source) throws Exception {
        StringBuilder result = new StringBuilder();
        String[] lines = source.split("\n");
        for (int i = 0; i < lines.length; ++i) {
            String line = lines[i];
            String compiledLine = this.compileLine(line, i + 1);
            result.append(compiledLine).append("\n");
        }
        return result.toString();
    }

    private String compileLine(String line, int lineNum) {
        String trimmed = line.trim();
        if (trimmed.isEmpty() || trimmed.startsWith("//") || trimmed.startsWith("#")) {
            return line;
        }
        if (trimmed.startsWith("/*")) {
            return line;
        }
        String translated = this.translateKeywords(line);
        translated = this.processPrintStatement(translated);
        translated = this.processInputStatement(translated);
        translated = this.processModDeclaration(translated);
        return translated;
    }

    private String translateKeywords(String line) {
        String result = line;
        StringBuilder output = new StringBuilder();
        boolean inString = false;
        boolean inChar = false;
        StringBuilder currentWord = new StringBuilder();
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            if (c == '\"' && !inChar) {
                if (currentWord.length() > 0) {
                    output.append(this.translateWord(currentWord.toString()));
                    currentWord.setLength(0);
                }
                inString = !inString;
                output.append(c);
                continue;
            }
            if (c == '\'' && !inString) {
                if (currentWord.length() > 0) {
                    output.append(this.translateWord(currentWord.toString()));
                    currentWord.setLength(0);
                }
                inChar = !inChar;
                output.append(c);
                continue;
            }
            if (Character.isWhitespace(c) || this.isOperator(c)) {
                if (currentWord.length() > 0) {
                    output.append(this.translateWord(currentWord.toString()));
                    currentWord.setLength(0);
                }
                if (inString || inChar) continue;
                output.append(c);
                continue;
            }
            currentWord.append(c);
        }
        if (currentWord.length() > 0) {
            output.append(this.translateWord(currentWord.toString()));
        }
        return output.toString();
    }

    private String translateWord(String word) {
        String base;
        String translatedBase;
        String translated = Z7LangKeywords.translate(word);
        if (word.contains("[]") && !(translatedBase = Z7LangKeywords.translate(base = word.replace("[]", ""))).equals(base)) {
            return translatedBase + "[]";
        }
        return translated;
    }

    private String processPrintStatement(String line) {
        Pattern printPattern = Pattern.compile("(print|println)\\s*\\(([^)]+)\\)");
        Matcher matcher = printPattern.matcher(line);
        if (matcher.find()) {
            String func = matcher.group(1);
            String arg = matcher.group(2);
            if (func.equals("println") || func.equals("\u0432\u044b\u0432\u043e\u0434")) {
                return line.replace(matcher.group(0), "System.out.println(" + arg + ")");
            }
            return line.replace(matcher.group(0), "System.out.print(" + arg + ")");
        }
        return line;
    }

    private String processInputStatement(String line) {
        if (line.contains("input()") || line.contains("\u0432\u0432\u043e\u0434()")) {
            return line.replace("input()", "new java.util.Scanner(System.in).nextLine()").replace("\u0432\u0432\u043e\u0434()", "new java.util.Scanner(System.in).nextLine()");
        }
        return line;
    }

    private String processModDeclaration(String line) {
        Pattern modPattern = Pattern.compile("mod\\s*\\(\\s*\"([^\"]+)\"\\s*,\\s*\"([^\"]+)\"\\s*\\)");
        Matcher matcher = modPattern.matcher(line);
        if (matcher.find()) {
            String modId = matcher.group(1);
            String version = matcher.group(2);
            return "@net.minecraftforge.fml.common.Mod(\"" + modId + "\")\npublic class Main {\n    public static final String VERSION = \"" + version + "\";\n    \n    public Main() {\n        // Mod initialized\n    }\n}";
        }
        return line;
    }

    private boolean isOperator(char c) {
        return "+-*/%=<>!&|".indexOf(c) >= 0 || c == '.' || c == ',' || c == ';' || c == ':' || c == '(' || c == ')' || c == '{' || c == '}';
    }

    private static String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));){
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void writeFile(File file, String content) throws IOException {
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));){
            writer.write(content);
        }
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public List<String> getWarnings() {
        return this.warnings;
    }
}

