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
        Lexeme l1 = null;
        Lexeme l2 = null;
        Lexeme l3 = null;

        public AssignNode(Tokenizer t) throws IOException, TokenizerException {
            l1 = t.current();
            t.moveNext();
            l2 = t.current();
            t.moveNext();
            expressNode = new ExpressNode(t);
            l3 = t.current();
            t.moveNext();
        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {
            builder.append("AssignmentNode\n");
            builder.append("\t" + l1.toString() + "\n");
            builder.append("\t" + l2.toString() + "\n");
            builder.append("\tExpressionNode" + "\n");
            expressNode.buildString(builder,1);
            builder.append("\t" + l3.toString() + "\n");

        }
    }

    class ExpressNode implements INode{
        TermNode termNode = null;
        Lexeme l = null;
        ExpressNode expressNode = null;
        public ExpressNode(Tokenizer t) throws IOException, TokenizerException {
            termNode = new TermNode(t);
            if(t.current().token() != Token.EOF && t.current().token() != Token.RIGHT_PAREN && t.current().token() != Token.SEMICOLON) {
                l = t.current();
                t.moveNext();
                expressNode = new ExpressNode(t);
            }
        }


        @Override
        public Object evaluate(Object[] args) throws Exception {
            return null;
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {
            for(int i = 0; i<=1; i++){//add tabs ,,, add function?
                builder.append("\t");
            }
            builder.append("TermNode\n");

        }
    }

    class TermNode implements INode{
        FactorNode factorNode = null;
        Lexeme l = null;
        TermNode termNode = null;
        public TermNode(Tokenizer t) throws IOException, TokenizerException {
            factorNode = new FactorNode(t);
            if((t.current().token() != Token.EOF) &&(t.current().token() != Token.RIGHT_PAREN) && (t.current().token() != Token.SEMICOLON)) {
                l = t.current();
                t.moveNext();
                termNode = new TermNode(t);
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

    class FactorNode implements INode{
        ExpressNode expressNode = null;
        Lexeme l1 = null;
        Lexeme l2 = null;

        public FactorNode(Tokenizer t) throws IOException, TokenizerException {
            if(t.current().token() == Token.INT_LIT){
                l1 = t.current();
                t.moveNext();
            }
            else if(t.current().token() != Token.EOF) {
                l1 = t.current();
                t.moveNext();
                expressNode = new ExpressNode(t);
                l2 = t.current();
                t.moveNext();
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
