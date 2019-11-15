package PROPassignment0;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

public class Tokenizer {

    private static Map<Character, Token> symbols = null;

    private Scanner scanner = null;
    private Lexeme current = null;
    private Lexeme next = null;


    public Tokenizer() {
        symbols = new HashMap<Character, Token>();
        symbols.put('=',Token.ASSIGN_OP);
        symbols.put(';',Token.SEMICOLON);
        symbols.put('+',Token.ADD_OP);
        symbols.put('-',Token.SUB_OP);
        symbols.put('*',Token.MULT_OP);
        symbols.put('/',Token.DIV_OP);
        symbols.put('(',Token.LEFT_PAREN);
        symbols.put(')',Token.RIGHT_PAREN);
        symbols.put('{',Token.LEFT_CURLY);
        symbols.put('}',Token.RIGHT_CURLY);

/*
        for(int i = 0; i<10;i++){
            symbols.put(Integer.toString(i).charAt(0),Token.INT_LIT);
        }

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            symbols.put(alphabet, Token.IDENT);

            System.out.println(alphabet);
        }

        System.out.println(symbols);
*/

    }

    /**
     * Opens a file for tokenizing.
     */
    void open(String fileName) throws IOException, TokenizerException {
        scanner = new Scanner();
        scanner.open(fileName);
        scanner.moveNext();
        next = extractLexeme();
    }

    /**
     * Returns the current token in the stream.
     */
    Lexeme current() {
        return current;
    }

    /**
     * Moves current to the next token in the stream.
     */
    void moveNext() throws IOException, TokenizerException {
        if (scanner == null)
            throw new IOException("No open file.");
        current = next;
        if (next.token() != Token.EOF)
            next = extractLexeme();
    }

    private void consumeWhiteSpaces() throws IOException {
        while (Character.isWhitespace(scanner.current())) {
            scanner.moveNext();
        }
    }

    private Lexeme extractLexeme() throws IOException, TokenizerException {
        consumeWhiteSpaces();

        Character ch = scanner.current();
        StringBuilder strBuilder = new StringBuilder();

        if (ch == Scanner.EOF) {
            return new Lexeme(ch, Token.EOF);
        }else if (Character.isLetter(ch)) { //Stora bokstäver?? åäö?
            while (Character.isLetter(scanner.current())) {
                strBuilder.append(scanner.current());
                scanner.moveNext();
            }
            String id = strBuilder.toString();
            return new Lexeme(id, Token.IDENT);
        }else if (Character.isDigit(ch)) {
                while (Character.isDigit(scanner.current())) {
                    strBuilder.append(scanner.current());
                    scanner.moveNext();
                }
                String id = strBuilder.toString();
                return new Lexeme(id, Token.INT_LIT);
        }else if (symbols.containsKey(ch)){
            strBuilder.append(scanner.current());
            String id = strBuilder.toString();
            scanner.moveNext();
            return new Lexeme(id, symbols.get(ch));
        }

            throw new TokenizerException("error");
        }









    /**
     * Closes the file and releases any system resources associated with it.
     */
    public void close() throws IOException {
        if (scanner != null)
            scanner.close();
    }
    public static void main(String[] args) {

        try {
            Tokenizer t = new Tokenizer();
            t.open("program1.txt");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
