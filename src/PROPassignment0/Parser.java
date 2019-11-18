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
            if(t.current().token() != Token.IDENT){
                throw new TokenizerException(t.current() + "is an invalid token. Excpected: IDENT");
            }
            l1 = t.current();
            t.moveNext();
            if(t.current().token() != Token.ASSIGN_OP){
                throw new TokenizerException(t.current() + "is an invalid token. Excpected: ASSIGN_OP");
            }
            l2 = t.current();
            t.moveNext();

            expressNode = new ExpressNode(t);
            if(t.current().token() != Token.SEMICOLON){
                throw new TokenizerException(t.current() + "is an invalid token. Excpected: SEMICOLON");
            }
            l3 = t.current();
            t.moveNext();

        }

        public void addTabs(StringBuilder builder, int tabs){
            for(int i = 0; i<=tabs; i++){
                builder.append("\t");
            }
        }

        @Override
        public Object evaluate(Object[] args) throws Exception {
            return l1.value().toString() + " " + l2.value().toString() + " " +expressNode.evaluate(null).toString();
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {
            builder.append("AssignmentNode\n");
            addTabs(builder, tabs);
            builder.append(l1.toString() + "\n");
            addTabs(builder, tabs);
            builder.append(l2.toString() + "\n");
            addTabs(builder, tabs);
            builder.append("ExpressionNode" + "\n");
            expressNode.buildString(builder,tabs+1);
            addTabs(builder, tabs);
            builder.append(l3.toString() + "\n");

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

        public void addTabs(StringBuilder builder, int tabs){
            for(int i = 0; i<=tabs; i++){
                builder.append("\t");
            }
        }

        @Override
        public Object evaluate(Object[] args) throws Exception {
            if(l!=null){
                if(l.token()==Token.ADD_OP){
                    return Double.parseDouble(String.valueOf(termNode.evaluate(null))) + Double.parseDouble(String.valueOf(expressNode.evaluate(null)));
                }else{
                    return Double.parseDouble(String.valueOf(termNode.evaluate(null))) - Double.parseDouble(String.valueOf(expressNode.evaluate(null)));
                }
            }
            return termNode.evaluate(null);
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {
            addTabs(builder, tabs);
            builder.append("TermNode\n");
            termNode.buildString(builder, tabs+1);
            if(l!=null){
                addTabs(builder, tabs);
                builder.append(l.toString()+"\n");
                addTabs(builder, tabs);
                builder.append("ExpressionNode\n");
                expressNode.buildString(builder, tabs+1);
            }

        }
    }

    class TermNode implements INode{
        FactorNode factorNode = null;
        Lexeme l = null;
        TermNode termNode = null;
        public TermNode(Tokenizer t) throws IOException, TokenizerException {
            factorNode = new FactorNode(t);
            if((t.current().token() != Token.EOF) &&(t.current().token() != Token.RIGHT_PAREN) && (t.current().token() != Token.SEMICOLON) && (t.current().token() != Token.ADD_OP)&&(t.current().token() != Token.SUB_OP)) {
                l = t.current();
                t.moveNext();
                termNode = new TermNode(t);
            }
        }

        public void addTabs(StringBuilder builder, int tabs){
            for(int i = 0; i<=tabs; i++){
                builder.append("\t");
            }
        }

        @Override
        public Object evaluate(Object[] args) throws Exception {
            if(l!=null){
                if(l.token()==Token.MULT_OP){
                    return Double.parseDouble(String.valueOf(factorNode.evaluate(null))) * Double.parseDouble(String.valueOf(termNode.evaluate(null)));
                }else{
                    return Double.parseDouble(String.valueOf(factorNode.evaluate(null))) / Double.parseDouble(String.valueOf(termNode.evaluate(null)));
                }
            }
            return factorNode.evaluate(null);
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {
            addTabs(builder, tabs);
            builder.append("FactorNode\n");
            factorNode.buildString(builder, tabs+1);
            if(l!=null){
                addTabs(builder, tabs);
                builder.append(l.toString()+"\n");
                addTabs(builder, tabs);
                builder.append("TermNode\n");
                termNode.buildString(builder, tabs+1);
            }
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
            else if((t.current().token() != Token.EOF) && (t.current().token() != Token.MULT_OP)&&(t.current().token() != Token.DIV_OP)) {
                l1 = t.current();
                t.moveNext();
                expressNode = new ExpressNode(t);
                l2 = t.current();
                t.moveNext();
            }
        }

        public void addTabs(StringBuilder builder, int tabs){
            for(int i = 0; i<=tabs; i++){
                builder.append("\t");
            }
        }

        @Override
        public Object evaluate(Object[] args) throws Exception {
            if(l1.token()== Token.INT_LIT){
                return l1.value();
            }
            return expressNode.evaluate(null);
        }

        @Override
        public void buildString(StringBuilder builder, int tabs) {
            if(l2==null){
                addTabs(builder, tabs);
                builder.append(l1.toString()+"\n");
            }else{
                addTabs(builder, tabs);
                builder.append(l1.toString()+"\n");
                addTabs(builder, tabs);
                builder.append("ExpressionNode\n");
                expressNode.buildString(builder, tabs+1);
                addTabs(builder, tabs);
                builder.append(l2.toString()+"\n");

            }
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
