package PROPassignment0;

import java.io.IOException;
import java.util.Map;
import java.util.HashSet;

public class Tokenizer {

    private static Map<Character, Token> symbols = null;
    private static HashSet<String> nouns = null;
    private static HashSet<String> verbs = null;
    private static HashSet<String> determiners = null;

    private Scanner scanner = null;
    private Lexeme current = null;
    private Lexeme next = null;


    public Tokenizer() {

       /* symbols = new HashMap<Character, Token>();
        nouns = new HashSet<String>();
        verbs = new HashSet<String>();
        determiners = new HashSet<String>();

        symbols.put('.', Token.STOP);
        symbols.put(Scanner.EOF, Token.EOF);

        verbs.add("scares");
        verbs.add("hates");

        nouns.add("cat");
        nouns.add("mouse");

        determiners.add("a");
        determiners.add("the");*/
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
        if (next.token() != Token.EOF)
            next = extractLexeme();
    }

    /**
     * Closes the file and releases any system resources associated with it.
     */
    public void close() throws IOException {
        if (scanner != null)
            scanner.close();
    }

}
