package com.kazimirm.hddlParser.parser;
import com.kazimirm.hddlParser.hddlObjects.Domain;
import com.kazimirm.hddlParser.hddlObjects.Type;

import java.io.*;
import java.util.*;

public class ParserHDDL implements ParserHDDLConstants {

    public static void main(String[] args) throws ParseException,FileNotFoundException {

        if (args.length < 1) {
            System.out.println("Please pass in the filename for a parameter.");
            System.exit(1);
        }

        ParserHDDL parser = new ParserHDDL(new FileInputStream(args[0]));

        parser.start();
        System.out.println("Parse completed.");
    }

    static final public void start() throws ParseException {
        Domain domain = domain();
        jj_consume_token(0);
    }

    static final public Domain domain() throws ParseException {Domain domain = new Domain();
        List<Type> types = new ArrayList<>();
        String domainName;
        jj_consume_token(LPAR);
        jj_consume_token(DEFINE);
        jj_consume_token(LPAR);
        jj_consume_token(DOMAIN);
        domainName = getAttribute();
        jj_consume_token(RPAR);
        types = getTypes();
        domain.setName(domainName);
        domain.setTypes(types);
        {if ("" != null) return domain;}
        throw new Error("Missing return statement in function");
    }

    static final public List<Type> getTypes() throws ParseException {List<Type> types = new ArrayList<>();
        Type type;
        jj_consume_token(LPAR);
        jj_consume_token(COLON);
        jj_consume_token(TYPES);
        label_1:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
                case VAR:{
                    ;
                    break;
                }
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
            type = getType();
            types.add(type);
        }
        jj_consume_token(RPAR);
        {if ("" != null) return types;}
        throw new Error("Missing return statement in function");
    }

    static final public Type getType() throws ParseException {Type type = new Type();
        String name;
        String baseType;
        name = getAttribute();
        jj_consume_token(DASH);
        baseType = getAttribute();
        type.setName(name);
        type.setBaseType(baseType);
        {if ("" != null) return type;}
        throw new Error("Missing return statement in function");
    }

    static final public String getAttribute() throws ParseException {String attribute;
        attribute = jj_consume_token(VAR).toString();
        {if ("" != null) return attribute;}
        throw new Error("Missing return statement in function");
    }

    static private boolean jj_initialized_once = false;
    /** Generated Token Manager. */
    static public ParserHDDLTokenManager token_source;
    static SimpleCharStream jj_input_stream;
    /** Current token. */
    static public Token token;
    /** Next token. */
    static public Token jj_nt;
    static private int jj_ntk;
    static private int jj_gen;
    static final private int[] jj_la1 = new int[1];
    static private int[] jj_la1_0;
    static {
        jj_la1_init_0();
    }
    private static void jj_la1_init_0() {
        jj_la1_0 = new int[] {0x1000000,};
    }

    /** Constructor with InputStream. */
    public ParserHDDL(java.io.InputStream stream) {
        this(stream, null);
    }
    /** Constructor with InputStream and supplied encoding */
    public ParserHDDL(java.io.InputStream stream, String encoding) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser.  ");
            System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("	   during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
        token_source = new ParserHDDLTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /** Reinitialise. */
    static public void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }
    /** Reinitialise. */
    static public void ReInit(java.io.InputStream stream, String encoding) {
        try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /** Constructor. */
    public ParserHDDL(java.io.Reader stream) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser. ");
            System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("	   during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new ParserHDDLTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /** Reinitialise. */
    static public void ReInit(java.io.Reader stream) {
        if (jj_input_stream == null) {
            jj_input_stream = new SimpleCharStream(stream, 1, 1);
        } else {
            jj_input_stream.ReInit(stream, 1, 1);
        }
        if (token_source == null) {
            token_source = new ParserHDDLTokenManager(jj_input_stream);
        }

        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /** Constructor with generated Token Manager. */
    public ParserHDDL(ParserHDDLTokenManager tm) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser. ");
            System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("	   during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /** Reinitialise. */
    public void ReInit(ParserHDDLTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    static private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }


    /** Get the next Token. */
    static final public Token getNextToken() {
        if (token.next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /** Get the specific Token. */
    static final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) t = t.next;
            else t = t.next = token_source.getNextToken();
        }
        return t;
    }

    static private int jj_ntk_f() {
        if ((jj_nt=token.next) == null)
            return (jj_ntk = (token.next=token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }

    static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    static private int[] jj_expentry;
    static private int jj_kind = -1;

    /** Generate ParseException. */
    static public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[26];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 1; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1<<j)) != 0) {
                        la1tokens[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 26; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    static private boolean trace_enabled;

    /** Trace enabled. */
    static final public boolean trace_enabled() {
        return trace_enabled;
    }

    /** Enable tracing. */
    static final public void enable_tracing() {
    }

    /** Disable tracing. */
    static final public void disable_tracing() {
    }

}
