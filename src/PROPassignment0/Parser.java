package PROPassignment0;

import java.io.IOException;

public class Parser implements IParser {
    Tokenizer t = null;

    public Parser(){

    }

    public void open(String fileName) throws IOException, TokenizerException{
        t = new Tokenizer();
        t.open(fileName);
        t.moveNext();
    }

    /**
     * Parses a program from file returning a parse tree (the root node of a parse tree).
     */
    public INode parse() throws IOException, TokenizerException, ParserException{
        if(t == null)
            throw new IOException("No open file.");
        return new AssignNode(t);
    }

    /**
     * Closes the file and releases any system resources associated with it.
     */
    public void close() throws IOException{
        if(t != null)
            t.close();
    }

    class AssignNode implements INode{
        ExpressNode expressNode = null;
        Lexeme l = null;

        public AssignNode(Tokenizer t) throws IOException, TokenizerException {
            l = t.current();
            t.moveNext();
            expressNode = new ExpressNode(t);
        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {

        }
    }

    class ExpressNode implements INode{
        TermNode termNode = null;
        ExpressNode expressNode = null;
        public ExpressNode(Tokenizer t) throws IOException, TokenizerException {
            termNode = new TermNode(t);
            if(t.current().token() != Token.EOF)
                expressNode = new ExpressNode(t);
        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {

        }
    }

    class TermNode implements INode{
        FactorNode factorNode = null;
        TermNode termNode = null;
        public TermNode(Tokenizer t) throws IOException, TokenizerException {
            factorNode = new FactorNode(t);
            if(t.current().token() != Token.EOF)
                termNode = new TermNode(t);

        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {

        }
    }

    class FactorNode implements INode{
        ExpressNode expressNode = null;
        Lexeme l = null;

        public FactorNode(Tokenizer t) throws IOException, TokenizerException {
            if(t.current().token() == Token.INT_LIT){
                l = t.current();
                t.moveNext();
            }
            else if(t.current().token() != Token.EOF) {
                expressNode = new ExpressNode(t);
            }
        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {

        }
    }

   /* class IntNode implements INode{

        public IntNode(Tokenizer t){

        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {

        }
    }

    class IdentNode implements INode{

        public IdentNode(Tokenizer t){

        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {

        }
    }*/
}
