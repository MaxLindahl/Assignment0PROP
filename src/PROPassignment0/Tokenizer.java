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

        for(int i = 0; i<10;i++){
            symbols.put(Integer.toString(i).charAt(0),Token.INT_LIT);
        }


    }

    /**
     * Opens a file for tokenizing.
     */
    void open(String fileName) throws IOException, TokenizerException {
        scanner = new Scanner();
        scanner.open(fileName);
        scanner.moveNext();
       // next = extractLexeme();
    }

    /**
     * Returns the current token in the stream.
     */
    Lexeme current(){
        return current;
    }

    /**
     * Moves current to the next token in the stream.
     */
    void moveNext() throws IOException, TokenizerException {
        if (scanner == null)
            throw new IOException("No open file.");
        current = next;
       // if (next.token() != Token.EOF)
         //   next = extractLexeme();
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

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
