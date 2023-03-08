// Generated from c:\..LEIC\LEIC_2022_2023\COMP\comp2023-9e\src\main\antlr\comp2023\grammar\Javamm.g4 by ANTLR 4.9.2

    package pt.up.fe.comp2023;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavammParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, INTEGER=38, 
		ID=39, WS=40, TRADICIONAL_COMMENT=41, EOL_COMMENT=42;
	public static final int
		RULE_program = 0, RULE_importDeclaration = 1, RULE_classDeclaration = 2, 
		RULE_varDeclaration = 3, RULE_methodDeclaration = 4, RULE_args = 5, RULE_argums = 6, 
		RULE_type = 7, RULE_statement = 8, RULE_returnExpression = 9, RULE_expression = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importDeclaration", "classDeclaration", "varDeclaration", 
			"methodDeclaration", "args", "argums", "type", "statement", "returnExpression", 
			"expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'import'", "'.'", "';'", "'class'", "'extends'", "'{'", "'}'", 
			"'public'", "'('", "')'", "'static'", "'void'", "'main'", "'['", "']'", 
			"','", "'int'", "'boolean'", "'if'", "'else'", "'while'", "'='", "'return'", 
			"'!'", "'*'", "'/'", "'+'", "'-'", "'>'", "'<'", "'&&'", "'||'", "'length'", 
			"'new'", "'true'", "'false'", "'this'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "INTEGER", "ID", "WS", "TRADICIONAL_COMMENT", "EOL_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Javamm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavammParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JavammParser.EOF, 0); }
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<MethodDeclarationContext> methodDeclaration() {
			return getRuleContexts(MethodDeclarationContext.class);
		}
		public MethodDeclarationContext methodDeclaration(int i) {
			return getRuleContext(MethodDeclarationContext.class,i);
		}
		public List<ClassDeclarationContext> classDeclaration() {
			return getRuleContexts(ClassDeclarationContext.class);
		}
		public ClassDeclarationContext classDeclaration(int i) {
			return getRuleContext(ClassDeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(22);
				importDeclaration();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__10) | (1L << T__16) | (1L << T__17) | (1L << ID))) != 0)) {
				{
				{
				setState(28);
				methodDeclaration();
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(34);
				classDeclaration();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(40);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDeclarationContext extends ParserRuleContext {
		public Token ID;
		public List<Token> value = new ArrayList<Token>();
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(T__0);
			setState(43);
			((ImportDeclarationContext)_localctx).ID = match(ID);
			((ImportDeclarationContext)_localctx).value.add(((ImportDeclarationContext)_localctx).ID);
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(44);
				match(T__1);
				setState(45);
				((ImportDeclarationContext)_localctx).ID = match(ID);
				((ImportDeclarationContext)_localctx).value.add(((ImportDeclarationContext)_localctx).ID);
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public Token name;
		public Token extension;
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<MethodDeclarationContext> methodDeclaration() {
			return getRuleContexts(MethodDeclarationContext.class);
		}
		public MethodDeclarationContext methodDeclaration(int i) {
			return getRuleContext(MethodDeclarationContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(T__3);
			setState(54);
			((ClassDeclarationContext)_localctx).name = match(ID);
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(55);
				match(T__4);
				setState(56);
				((ClassDeclarationContext)_localctx).extension = match(ID);
				}
			}

			setState(59);
			match(T__5);
			setState(63);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(60);
					varDeclaration();
					}
					} 
				}
				setState(65);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__10) | (1L << T__16) | (1L << T__17) | (1L << ID))) != 0)) {
				{
				{
				setState(66);
				methodDeclaration();
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(72);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public Token varName;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			type();
			setState(75);
			((VarDeclarationContext)_localctx).varName = match(ID);
			setState(76);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclarationContext extends ParserRuleContext {
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
	 
		public MethodDeclarationContext() { }
		public void copyFrom(MethodDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MethodDeclContext extends MethodDeclarationContext {
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ReturnExpressionContext returnExpression() {
			return getRuleContext(ReturnExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MethodDeclContext(MethodDeclarationContext ctx) { copyFrom(ctx); }
	}
	public static class MainMethodDeclContext extends MethodDeclarationContext {
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MainMethodDeclContext(MethodDeclarationContext ctx) { copyFrom(ctx); }
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_methodDeclaration);
		int _la;
		try {
			int _alt;
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new MethodDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(78);
					match(T__7);
					}
				}

				setState(81);
				type();
				setState(82);
				((MethodDeclContext)_localctx).name = match(ID);
				setState(83);
				match(T__8);
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << ID))) != 0)) {
					{
					setState(84);
					args();
					}
				}

				setState(87);
				match(T__9);
				setState(88);
				match(T__5);
				setState(92);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(89);
						varDeclaration();
						}
						} 
					}
					setState(94);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__18) | (1L << T__20) | (1L << T__23) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(95);
					statement();
					}
					}
					setState(100);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(101);
				returnExpression();
				setState(102);
				match(T__6);
				}
				break;
			case 2:
				_localctx = new MainMethodDeclContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(104);
					match(T__7);
					}
				}

				setState(107);
				match(T__10);
				setState(108);
				match(T__11);
				setState(109);
				match(T__12);
				setState(110);
				match(T__8);
				setState(111);
				match(ID);
				setState(112);
				match(T__13);
				setState(113);
				match(T__14);
				setState(114);
				match(ID);
				setState(115);
				match(T__9);
				setState(116);
				match(T__5);
				setState(120);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(117);
						varDeclaration();
						}
						} 
					}
					setState(122);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__18) | (1L << T__20) | (1L << T__23) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(123);
					statement();
					}
					}
					setState(128);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(129);
				match(T__6);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgsContext extends ParserRuleContext {
		public List<ArgumsContext> argums() {
			return getRuleContexts(ArgumsContext.class);
		}
		public ArgumsContext argums(int i) {
			return getRuleContext(ArgumsContext.class,i);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_args);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			argums();
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(133);
				match(T__15);
				setState(134);
				argums();
				}
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumsContext extends ParserRuleContext {
		public Token argName;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ArgumsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argums; }
	}

	public final ArgumsContext argums() throws RecognitionException {
		ArgumsContext _localctx = new ArgumsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argums);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			type();
			setState(141);
			((ArgumsContext)_localctx).argName = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BoolTypeContext extends TypeContext {
		public BoolTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class IntArrayTypeContext extends TypeContext {
		public IntArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class IntTypeContext extends TypeContext {
		public IntTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class IdTypeContext extends TypeContext {
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public IdTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type);
		try {
			setState(149);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new IntArrayTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				match(T__16);
				setState(144);
				match(T__13);
				setState(145);
				match(T__14);
				}
				break;
			case 2:
				_localctx = new BoolTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(146);
				match(T__17);
				}
				break;
			case 3:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(147);
				match(T__16);
				}
				break;
			case 4:
				_localctx = new IdTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(148);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprStmtContext(StatementContext ctx) { copyFrom(ctx); }
	}
	public static class IfElseStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfElseStmtContext(StatementContext ctx) { copyFrom(ctx); }
	}
	public static class WhileStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStmtContext(StatementContext ctx) { copyFrom(ctx); }
	}
	public static class AssignStmtContext extends StatementContext {
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignStmtContext(StatementContext ctx) { copyFrom(ctx); }
	}
	public static class BlockStmtContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockStmtContext(StatementContext ctx) { copyFrom(ctx); }
	}
	public static class ArrayAssignStmtContext extends StatementContext {
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayAssignStmtContext(StatementContext ctx) { copyFrom(ctx); }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_statement);
		int _la;
		try {
			setState(189);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				match(T__5);
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__18) | (1L << T__20) | (1L << T__23) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(152);
					statement();
					}
					}
					setState(157);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(158);
				match(T__6);
				}
				break;
			case 2:
				_localctx = new IfElseStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				match(T__18);
				setState(160);
				match(T__8);
				setState(161);
				expression(0);
				setState(162);
				match(T__9);
				setState(163);
				statement();
				setState(164);
				match(T__19);
				setState(165);
				statement();
				}
				break;
			case 3:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(167);
				match(T__20);
				setState(168);
				match(T__8);
				setState(169);
				expression(0);
				setState(170);
				match(T__9);
				setState(171);
				statement();
				}
				break;
			case 4:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(173);
				expression(0);
				setState(174);
				match(T__2);
				}
				break;
			case 5:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(176);
				match(ID);
				setState(177);
				match(T__21);
				setState(178);
				expression(0);
				setState(179);
				match(T__2);
				}
				break;
			case 6:
				_localctx = new ArrayAssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(181);
				match(ID);
				setState(182);
				match(T__13);
				setState(183);
				expression(0);
				setState(184);
				match(T__14);
				setState(185);
				match(T__21);
				setState(186);
				expression(0);
				setState(187);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnExpression; }
	}

	public final ReturnExpressionContext returnExpression() throws RecognitionException {
		ReturnExpressionContext _localctx = new ReturnExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_returnExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(T__22);
			setState(192);
			expression(0);
			setState(193);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MultDivExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MultDivExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BoolExprContext extends ExpressionContext {
		public Token bool;
		public BoolExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class IdExprContext extends ExpressionContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public IdExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class RelExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RelExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class ArrayAccessExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayAccessExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NewObjectExprContext extends ExpressionContext {
		public Token object;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public NewObjectExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class AndOrExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AndOrExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NewIntArrayExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NewIntArrayExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class ArrayLengthExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayLengthExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NotExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class IntExprContext extends ExpressionContext {
		public Token value;
		public TerminalNode INTEGER() { return getToken(JavammParser.INTEGER, 0); }
		public IntExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class ParenExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class AddSubExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AddSubExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class ThisExprContext extends ExpressionContext {
		public ThisExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class MethodCallExprContext extends ExpressionContext {
		public Token method;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public MethodCallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(196);
				match(T__23);
				setState(197);
				expression(15);
				}
				break;
			case 2:
				{
				_localctx = new NewIntArrayExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(198);
				match(T__33);
				setState(199);
				match(T__16);
				setState(200);
				match(T__13);
				setState(201);
				expression(0);
				setState(202);
				match(T__14);
				}
				break;
			case 3:
				{
				_localctx = new NewObjectExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(204);
				match(T__33);
				setState(205);
				((NewObjectExprContext)_localctx).object = match(ID);
				setState(206);
				match(T__8);
				setState(207);
				match(T__9);
				}
				break;
			case 4:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(208);
				match(T__8);
				setState(209);
				expression(0);
				setState(210);
				match(T__9);
				}
				break;
			case 5:
				{
				_localctx = new BoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(212);
				((BoolExprContext)_localctx).bool = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__34 || _la==T__35) ) {
					((BoolExprContext)_localctx).bool = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 6:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(213);
				((IdExprContext)_localctx).value = match(ID);
				}
				break;
			case 7:
				{
				_localctx = new IntExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(214);
				((IntExprContext)_localctx).value = match(INTEGER);
				}
				break;
			case 8:
				{
				_localctx = new ThisExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(215);
				match(T__36);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(255);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(253);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new MultDivExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(218);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(219);
						((MultDivExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__24 || _la==T__25) ) {
							((MultDivExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(220);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(221);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(222);
						((AddSubExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__26 || _la==T__27) ) {
							((AddSubExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(223);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new RelExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(224);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(225);
						((RelExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__28 || _la==T__29) ) {
							((RelExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(226);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new AndOrExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(227);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(228);
						((AndOrExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__30 || _la==T__31) ) {
							((AndOrExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(229);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new ArrayAccessExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(230);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(231);
						match(T__13);
						setState(232);
						expression(0);
						setState(233);
						match(T__14);
						}
						break;
					case 6:
						{
						_localctx = new ArrayLengthExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(235);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(236);
						match(T__1);
						setState(237);
						match(T__32);
						}
						break;
					case 7:
						{
						_localctx = new MethodCallExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(238);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(239);
						match(T__1);
						setState(240);
						((MethodCallExprContext)_localctx).method = match(ID);
						setState(241);
						match(T__8);
						setState(250);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__23) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << INTEGER) | (1L << ID))) != 0)) {
							{
							setState(242);
							expression(0);
							setState(247);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==T__15) {
								{
								{
								setState(243);
								match(T__15);
								setState(244);
								expression(0);
								}
								}
								setState(249);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(252);
						match(T__9);
						}
						break;
					}
					} 
				}
				setState(257);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 13);
		case 2:
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 10);
		case 5:
			return precpred(_ctx, 9);
		case 6:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3,\u0105\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\2\7\2 \n\2\f\2\16\2#\13"+
		"\2\3\2\7\2&\n\2\f\2\16\2)\13\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3\61\n\3\f\3"+
		"\16\3\64\13\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4<\n\4\3\4\3\4\7\4@\n\4\f\4\16"+
		"\4C\13\4\3\4\7\4F\n\4\f\4\16\4I\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\5\6R"+
		"\n\6\3\6\3\6\3\6\3\6\5\6X\n\6\3\6\3\6\3\6\7\6]\n\6\f\6\16\6`\13\6\3\6"+
		"\7\6c\n\6\f\6\16\6f\13\6\3\6\3\6\3\6\3\6\5\6l\n\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\7\6y\n\6\f\6\16\6|\13\6\3\6\7\6\177\n\6\f\6\16"+
		"\6\u0082\13\6\3\6\5\6\u0085\n\6\3\7\3\7\3\7\7\7\u008a\n\7\f\7\16\7\u008d"+
		"\13\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0098\n\t\3\n\3\n\7\n\u009c"+
		"\n\n\f\n\16\n\u009f\13\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\5\n\u00c0\n\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00db\n\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u00f8\n\f\f\f\16\f\u00fb\13"+
		"\f\5\f\u00fd\n\f\3\f\7\f\u0100\n\f\f\f\16\f\u0103\13\f\3\f\2\3\26\r\2"+
		"\4\6\b\n\f\16\20\22\24\26\2\7\3\2%&\3\2\33\34\3\2\35\36\3\2\37 \3\2!\""+
		"\2\u0122\2\33\3\2\2\2\4,\3\2\2\2\6\67\3\2\2\2\bL\3\2\2\2\n\u0084\3\2\2"+
		"\2\f\u0086\3\2\2\2\16\u008e\3\2\2\2\20\u0097\3\2\2\2\22\u00bf\3\2\2\2"+
		"\24\u00c1\3\2\2\2\26\u00da\3\2\2\2\30\32\5\4\3\2\31\30\3\2\2\2\32\35\3"+
		"\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34!\3\2\2\2\35\33\3\2\2\2\36 \5\n\6"+
		"\2\37\36\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"\'\3\2\2\2#!\3\2\2"+
		"\2$&\5\6\4\2%$\3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(*\3\2\2\2)\'\3"+
		"\2\2\2*+\7\2\2\3+\3\3\2\2\2,-\7\3\2\2-\62\7)\2\2./\7\4\2\2/\61\7)\2\2"+
		"\60.\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\65\3\2\2\2\64"+
		"\62\3\2\2\2\65\66\7\5\2\2\66\5\3\2\2\2\678\7\6\2\28;\7)\2\29:\7\7\2\2"+
		":<\7)\2\2;9\3\2\2\2;<\3\2\2\2<=\3\2\2\2=A\7\b\2\2>@\5\b\5\2?>\3\2\2\2"+
		"@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BG\3\2\2\2CA\3\2\2\2DF\5\n\6\2ED\3\2\2\2"+
		"FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2HJ\3\2\2\2IG\3\2\2\2JK\7\t\2\2K\7\3\2\2"+
		"\2LM\5\20\t\2MN\7)\2\2NO\7\5\2\2O\t\3\2\2\2PR\7\n\2\2QP\3\2\2\2QR\3\2"+
		"\2\2RS\3\2\2\2ST\5\20\t\2TU\7)\2\2UW\7\13\2\2VX\5\f\7\2WV\3\2\2\2WX\3"+
		"\2\2\2XY\3\2\2\2YZ\7\f\2\2Z^\7\b\2\2[]\5\b\5\2\\[\3\2\2\2]`\3\2\2\2^\\"+
		"\3\2\2\2^_\3\2\2\2_d\3\2\2\2`^\3\2\2\2ac\5\22\n\2ba\3\2\2\2cf\3\2\2\2"+
		"db\3\2\2\2de\3\2\2\2eg\3\2\2\2fd\3\2\2\2gh\5\24\13\2hi\7\t\2\2i\u0085"+
		"\3\2\2\2jl\7\n\2\2kj\3\2\2\2kl\3\2\2\2lm\3\2\2\2mn\7\r\2\2no\7\16\2\2"+
		"op\7\17\2\2pq\7\13\2\2qr\7)\2\2rs\7\20\2\2st\7\21\2\2tu\7)\2\2uv\7\f\2"+
		"\2vz\7\b\2\2wy\5\b\5\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{\u0080"+
		"\3\2\2\2|z\3\2\2\2}\177\5\22\n\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080~\3"+
		"\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080\3\2\2\2\u0083"+
		"\u0085\7\t\2\2\u0084Q\3\2\2\2\u0084k\3\2\2\2\u0085\13\3\2\2\2\u0086\u008b"+
		"\5\16\b\2\u0087\u0088\7\22\2\2\u0088\u008a\5\16\b\2\u0089\u0087\3\2\2"+
		"\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\r"+
		"\3\2\2\2\u008d\u008b\3\2\2\2\u008e\u008f\5\20\t\2\u008f\u0090\7)\2\2\u0090"+
		"\17\3\2\2\2\u0091\u0092\7\23\2\2\u0092\u0093\7\20\2\2\u0093\u0098\7\21"+
		"\2\2\u0094\u0098\7\24\2\2\u0095\u0098\7\23\2\2\u0096\u0098\7)\2\2\u0097"+
		"\u0091\3\2\2\2\u0097\u0094\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0096\3\2"+
		"\2\2\u0098\21\3\2\2\2\u0099\u009d\7\b\2\2\u009a\u009c\5\22\n\2\u009b\u009a"+
		"\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00c0\7\t\2\2\u00a1\u00a2\7\25"+
		"\2\2\u00a2\u00a3\7\13\2\2\u00a3\u00a4\5\26\f\2\u00a4\u00a5\7\f\2\2\u00a5"+
		"\u00a6\5\22\n\2\u00a6\u00a7\7\26\2\2\u00a7\u00a8\5\22\n\2\u00a8\u00c0"+
		"\3\2\2\2\u00a9\u00aa\7\27\2\2\u00aa\u00ab\7\13\2\2\u00ab\u00ac\5\26\f"+
		"\2\u00ac\u00ad\7\f\2\2\u00ad\u00ae\5\22\n\2\u00ae\u00c0\3\2\2\2\u00af"+
		"\u00b0\5\26\f\2\u00b0\u00b1\7\5\2\2\u00b1\u00c0\3\2\2\2\u00b2\u00b3\7"+
		")\2\2\u00b3\u00b4\7\30\2\2\u00b4\u00b5\5\26\f\2\u00b5\u00b6\7\5\2\2\u00b6"+
		"\u00c0\3\2\2\2\u00b7\u00b8\7)\2\2\u00b8\u00b9\7\20\2\2\u00b9\u00ba\5\26"+
		"\f\2\u00ba\u00bb\7\21\2\2\u00bb\u00bc\7\30\2\2\u00bc\u00bd\5\26\f\2\u00bd"+
		"\u00be\7\5\2\2\u00be\u00c0\3\2\2\2\u00bf\u0099\3\2\2\2\u00bf\u00a1\3\2"+
		"\2\2\u00bf\u00a9\3\2\2\2\u00bf\u00af\3\2\2\2\u00bf\u00b2\3\2\2\2\u00bf"+
		"\u00b7\3\2\2\2\u00c0\23\3\2\2\2\u00c1\u00c2\7\31\2\2\u00c2\u00c3\5\26"+
		"\f\2\u00c3\u00c4\7\5\2\2\u00c4\25\3\2\2\2\u00c5\u00c6\b\f\1\2\u00c6\u00c7"+
		"\7\32\2\2\u00c7\u00db\5\26\f\21\u00c8\u00c9\7$\2\2\u00c9\u00ca\7\23\2"+
		"\2\u00ca\u00cb\7\20\2\2\u00cb\u00cc\5\26\f\2\u00cc\u00cd\7\21\2\2\u00cd"+
		"\u00db\3\2\2\2\u00ce\u00cf\7$\2\2\u00cf\u00d0\7)\2\2\u00d0\u00d1\7\13"+
		"\2\2\u00d1\u00db\7\f\2\2\u00d2\u00d3\7\13\2\2\u00d3\u00d4\5\26\f\2\u00d4"+
		"\u00d5\7\f\2\2\u00d5\u00db\3\2\2\2\u00d6\u00db\t\2\2\2\u00d7\u00db\7)"+
		"\2\2\u00d8\u00db\7(\2\2\u00d9\u00db\7\'\2\2\u00da\u00c5\3\2\2\2\u00da"+
		"\u00c8\3\2\2\2\u00da\u00ce\3\2\2\2\u00da\u00d2\3\2\2\2\u00da\u00d6\3\2"+
		"\2\2\u00da\u00d7\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00d9\3\2\2\2\u00db"+
		"\u0101\3\2\2\2\u00dc\u00dd\f\20\2\2\u00dd\u00de\t\3\2\2\u00de\u0100\5"+
		"\26\f\21\u00df\u00e0\f\17\2\2\u00e0\u00e1\t\4\2\2\u00e1\u0100\5\26\f\20"+
		"\u00e2\u00e3\f\16\2\2\u00e3\u00e4\t\5\2\2\u00e4\u0100\5\26\f\17\u00e5"+
		"\u00e6\f\r\2\2\u00e6\u00e7\t\6\2\2\u00e7\u0100\5\26\f\16\u00e8\u00e9\f"+
		"\f\2\2\u00e9\u00ea\7\20\2\2\u00ea\u00eb\5\26\f\2\u00eb\u00ec\7\21\2\2"+
		"\u00ec\u0100\3\2\2\2\u00ed\u00ee\f\13\2\2\u00ee\u00ef\7\4\2\2\u00ef\u0100"+
		"\7#\2\2\u00f0\u00f1\f\n\2\2\u00f1\u00f2\7\4\2\2\u00f2\u00f3\7)\2\2\u00f3"+
		"\u00fc\7\13\2\2\u00f4\u00f9\5\26\f\2\u00f5\u00f6\7\22\2\2\u00f6\u00f8"+
		"\5\26\f\2\u00f7\u00f5\3\2\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2"+
		"\u00f9\u00fa\3\2\2\2\u00fa\u00fd\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc\u00f4"+
		"\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u0100\7\f\2\2\u00ff"+
		"\u00dc\3\2\2\2\u00ff\u00df\3\2\2\2\u00ff\u00e2\3\2\2\2\u00ff\u00e5\3\2"+
		"\2\2\u00ff\u00e8\3\2\2\2\u00ff\u00ed\3\2\2\2\u00ff\u00f0\3\2\2\2\u0100"+
		"\u0103\3\2\2\2\u0101\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102\27\3\2\2"+
		"\2\u0103\u0101\3\2\2\2\32\33!\'\62;AGQW^dkz\u0080\u0084\u008b\u0097\u009d"+
		"\u00bf\u00da\u00f9\u00fc\u00ff\u0101";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}