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
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		INTEGER=39, ID=40, WS=41, TRADICIONAL_COMMENT=42, EOL_COMMENT=43;
	public static final int
		RULE_program = 0, RULE_importDeclaration = 1, RULE_classDeclaration = 2, 
		RULE_varDeclaration = 3, RULE_methodDeclaration = 4, RULE_type = 5, RULE_statement = 6, 
		RULE_expression = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importDeclaration", "classDeclaration", "varDeclaration", 
			"methodDeclaration", "type", "statement", "expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'import'", "'.'", "';'", "'class'", "'extends'", "'{'", "'}'", 
			"'public'", "'('", "','", "')'", "'return'", "'static'", "'void'", "'main'", 
			"'String'", "'['", "']'", "'int'", "'boolean'", "'if'", "'else'", "'while'", 
			"'='", "'!'", "'*'", "'/'", "'+'", "'-'", "'>'", "'<'", "'&&'", "'||'", 
			"'length'", "'new'", "'true'", "'false'", "'this'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "INTEGER", "ID", "WS", "TRADICIONAL_COMMENT", "EOL_COMMENT"
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
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(16);
				importDeclaration();
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__18) | (1L << T__19) | (1L << ID))) != 0)) {
				{
				{
				setState(22);
				methodDeclaration();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(28);
				classDeclaration();
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(34);
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
		public Token value;
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
			setState(36);
			match(T__0);
			setState(37);
			((ImportDeclarationContext)_localctx).value = match(ID);
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(38);
				match(T__1);
				setState(39);
				((ImportDeclarationContext)_localctx).value = match(ID);
				}
				}
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(45);
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
		public Token className;
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
			setState(47);
			match(T__3);
			setState(48);
			((ClassDeclarationContext)_localctx).className = match(ID);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(49);
				match(T__4);
				setState(50);
				((ClassDeclarationContext)_localctx).extension = match(ID);
				}
			}

			setState(53);
			match(T__5);
			setState(57);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(54);
					varDeclaration();
					}
					} 
				}
				setState(59);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__18) | (1L << T__19) | (1L << ID))) != 0)) {
				{
				{
				setState(60);
				methodDeclaration();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66);
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
		public Token value;
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
			setState(68);
			type();
			setState(69);
			((VarDeclarationContext)_localctx).value = match(ID);
			setState(70);
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
		public Token arg;
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
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
		public MethodDeclContext(MethodDeclarationContext ctx) { copyFrom(ctx); }
	}
	public static class MainMethodDeclContext extends MethodDeclarationContext {
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
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
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new MethodDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(72);
					match(T__7);
					}
				}

				setState(75);
				type();
				setState(76);
				((MethodDeclContext)_localctx).name = match(ID);
				setState(77);
				match(T__8);
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__18) | (1L << T__19) | (1L << ID))) != 0)) {
					{
					setState(78);
					type();
					setState(79);
					((MethodDeclContext)_localctx).arg = match(ID);
					setState(86);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__9) {
						{
						{
						setState(80);
						match(T__9);
						setState(81);
						type();
						setState(82);
						match(ID);
						}
						}
						setState(88);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(91);
				match(T__10);
				setState(92);
				match(T__5);
				setState(96);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(93);
						varDeclaration();
						}
						} 
					}
					setState(98);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__20) | (1L << T__22) | (1L << T__24) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(99);
					statement();
					}
					}
					setState(104);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(105);
				match(T__11);
				setState(106);
				expression(0);
				setState(107);
				match(T__2);
				setState(108);
				match(T__6);
				}
				break;
			case 2:
				_localctx = new MainMethodDeclContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(110);
					match(T__7);
					}
				}

				setState(113);
				match(T__12);
				setState(114);
				match(T__13);
				setState(115);
				match(T__14);
				setState(116);
				match(T__8);
				setState(117);
				match(T__15);
				setState(118);
				match(T__16);
				setState(119);
				match(T__17);
				setState(120);
				match(ID);
				setState(121);
				match(T__10);
				setState(122);
				match(T__5);
				setState(126);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(123);
						varDeclaration();
						}
						} 
					}
					setState(128);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__20) | (1L << T__22) | (1L << T__24) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(129);
					statement();
					}
					}
					setState(134);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(135);
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

	public static class TypeContext extends ParserRuleContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type);
		try {
			setState(144);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				((TypeContext)_localctx).value = match(T__18);
				setState(139);
				match(T__16);
				setState(140);
				match(T__17);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(141);
				((TypeContext)_localctx).value = match(T__19);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(142);
				((TypeContext)_localctx).value = match(T__18);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(143);
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
		enterRule(_localctx, 12, RULE_statement);
		int _la;
		try {
			setState(184);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(146);
				match(T__5);
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__20) | (1L << T__22) | (1L << T__24) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(147);
					statement();
					}
					}
					setState(152);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(153);
				match(T__6);
				}
				break;
			case 2:
				_localctx = new IfElseStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				match(T__20);
				setState(155);
				match(T__8);
				setState(156);
				expression(0);
				setState(157);
				match(T__10);
				setState(158);
				statement();
				setState(159);
				match(T__21);
				setState(160);
				statement();
				}
				break;
			case 3:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(162);
				match(T__22);
				setState(163);
				match(T__8);
				setState(164);
				expression(0);
				setState(165);
				match(T__10);
				setState(166);
				statement();
				}
				break;
			case 4:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(168);
				expression(0);
				setState(169);
				match(T__2);
				}
				break;
			case 5:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(171);
				match(ID);
				setState(172);
				match(T__23);
				setState(173);
				expression(0);
				setState(174);
				match(T__2);
				}
				break;
			case 6:
				_localctx = new ArrayAssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(176);
				match(ID);
				setState(177);
				match(T__16);
				setState(178);
				expression(0);
				setState(179);
				match(T__17);
				setState(180);
				match(T__23);
				setState(181);
				expression(0);
				setState(182);
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
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(187);
				match(T__24);
				setState(188);
				expression(15);
				}
				break;
			case 2:
				{
				_localctx = new NewIntArrayExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(189);
				match(T__34);
				setState(190);
				match(T__18);
				setState(191);
				match(T__16);
				setState(192);
				expression(0);
				setState(193);
				match(T__17);
				}
				break;
			case 3:
				{
				_localctx = new NewObjectExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(195);
				match(T__34);
				setState(196);
				((NewObjectExprContext)_localctx).object = match(ID);
				setState(197);
				match(T__8);
				setState(198);
				match(T__10);
				}
				break;
			case 4:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(199);
				match(T__8);
				setState(200);
				expression(0);
				setState(201);
				match(T__10);
				}
				break;
			case 5:
				{
				_localctx = new BoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(203);
				((BoolExprContext)_localctx).bool = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__35 || _la==T__36) ) {
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
				setState(204);
				((IdExprContext)_localctx).value = match(ID);
				}
				break;
			case 7:
				{
				_localctx = new IntExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(205);
				((IntExprContext)_localctx).value = match(INTEGER);
				}
				break;
			case 8:
				{
				_localctx = new ThisExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(206);
				match(T__37);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(246);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(244);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new MultDivExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(209);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(210);
						((MultDivExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__25 || _la==T__26) ) {
							((MultDivExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(211);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(212);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(213);
						((AddSubExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__27 || _la==T__28) ) {
							((AddSubExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(214);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new RelExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(215);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(216);
						((RelExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__29 || _la==T__30) ) {
							((RelExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(217);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new AndOrExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(218);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(219);
						((AndOrExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__31 || _la==T__32) ) {
							((AndOrExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(220);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new ArrayAccessExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(221);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(222);
						match(T__16);
						setState(223);
						expression(0);
						setState(224);
						match(T__17);
						}
						break;
					case 6:
						{
						_localctx = new ArrayLengthExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(226);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(227);
						match(T__1);
						setState(228);
						match(T__33);
						}
						break;
					case 7:
						{
						_localctx = new MethodCallExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(229);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(230);
						match(T__1);
						setState(231);
						((MethodCallExprContext)_localctx).method = match(ID);
						setState(232);
						match(T__8);
						setState(241);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__24) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << INTEGER) | (1L << ID))) != 0)) {
							{
							setState(233);
							expression(0);
							setState(238);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==T__9) {
								{
								{
								setState(234);
								match(T__9);
								setState(235);
								expression(0);
								}
								}
								setState(240);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(243);
						match(T__10);
						}
						break;
					}
					} 
				}
				setState(248);
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
		case 7:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3-\u00fc\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\7\2\24\n\2"+
		"\f\2\16\2\27\13\2\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\2\7\2 \n\2\f\2\16"+
		"\2#\13\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3+\n\3\f\3\16\3.\13\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\5\4\66\n\4\3\4\3\4\7\4:\n\4\f\4\16\4=\13\4\3\4\7\4@\n\4\f\4"+
		"\16\4C\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\5\6L\n\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\7\6W\n\6\f\6\16\6Z\13\6\5\6\\\n\6\3\6\3\6\3\6\7\6a\n\6"+
		"\f\6\16\6d\13\6\3\6\7\6g\n\6\f\6\16\6j\13\6\3\6\3\6\3\6\3\6\3\6\3\6\5"+
		"\6r\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\177\n\6\f\6\16"+
		"\6\u0082\13\6\3\6\7\6\u0085\n\6\f\6\16\6\u0088\13\6\3\6\5\6\u008b\n\6"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u0093\n\7\3\b\3\b\7\b\u0097\n\b\f\b\16\b"+
		"\u009a\13\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b"+
		"\u00bb\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00d2\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\7\t\u00ef\n\t\f\t\16\t\u00f2\13\t\5\t\u00f4\n\t\3\t\7\t\u00f7\n"+
		"\t\f\t\16\t\u00fa\13\t\3\t\2\3\20\n\2\4\6\b\n\f\16\20\2\7\3\2&\'\3\2\34"+
		"\35\3\2\36\37\3\2 !\3\2\"#\2\u011c\2\25\3\2\2\2\4&\3\2\2\2\6\61\3\2\2"+
		"\2\bF\3\2\2\2\n\u008a\3\2\2\2\f\u0092\3\2\2\2\16\u00ba\3\2\2\2\20\u00d1"+
		"\3\2\2\2\22\24\5\4\3\2\23\22\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26"+
		"\3\2\2\2\26\33\3\2\2\2\27\25\3\2\2\2\30\32\5\n\6\2\31\30\3\2\2\2\32\35"+
		"\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34!\3\2\2\2\35\33\3\2\2\2\36 \5\6"+
		"\4\2\37\36\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"$\3\2\2\2#!\3\2\2"+
		"\2$%\7\2\2\3%\3\3\2\2\2&\'\7\3\2\2\',\7*\2\2()\7\4\2\2)+\7*\2\2*(\3\2"+
		"\2\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-/\3\2\2\2.,\3\2\2\2/\60\7\5\2\2\60"+
		"\5\3\2\2\2\61\62\7\6\2\2\62\65\7*\2\2\63\64\7\7\2\2\64\66\7*\2\2\65\63"+
		"\3\2\2\2\65\66\3\2\2\2\66\67\3\2\2\2\67;\7\b\2\28:\5\b\5\298\3\2\2\2:"+
		"=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<A\3\2\2\2=;\3\2\2\2>@\5\n\6\2?>\3\2\2\2"+
		"@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7\t\2\2E\7\3\2\2"+
		"\2FG\5\f\7\2GH\7*\2\2HI\7\5\2\2I\t\3\2\2\2JL\7\n\2\2KJ\3\2\2\2KL\3\2\2"+
		"\2LM\3\2\2\2MN\5\f\7\2NO\7*\2\2O[\7\13\2\2PQ\5\f\7\2QX\7*\2\2RS\7\f\2"+
		"\2ST\5\f\7\2TU\7*\2\2UW\3\2\2\2VR\3\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2\2"+
		"\2Y\\\3\2\2\2ZX\3\2\2\2[P\3\2\2\2[\\\3\2\2\2\\]\3\2\2\2]^\7\r\2\2^b\7"+
		"\b\2\2_a\5\b\5\2`_\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2ch\3\2\2\2db\3"+
		"\2\2\2eg\5\16\b\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh"+
		"\3\2\2\2kl\7\16\2\2lm\5\20\t\2mn\7\5\2\2no\7\t\2\2o\u008b\3\2\2\2pr\7"+
		"\n\2\2qp\3\2\2\2qr\3\2\2\2rs\3\2\2\2st\7\17\2\2tu\7\20\2\2uv\7\21\2\2"+
		"vw\7\13\2\2wx\7\22\2\2xy\7\23\2\2yz\7\24\2\2z{\7*\2\2{|\7\r\2\2|\u0080"+
		"\7\b\2\2}\177\5\b\5\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2\u0080"+
		"\u0081\3\2\2\2\u0081\u0086\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0085\5\16"+
		"\b\2\u0084\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0087\u0089\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008b\7\t"+
		"\2\2\u008aK\3\2\2\2\u008aq\3\2\2\2\u008b\13\3\2\2\2\u008c\u008d\7\25\2"+
		"\2\u008d\u008e\7\23\2\2\u008e\u0093\7\24\2\2\u008f\u0093\7\26\2\2\u0090"+
		"\u0093\7\25\2\2\u0091\u0093\7*\2\2\u0092\u008c\3\2\2\2\u0092\u008f\3\2"+
		"\2\2\u0092\u0090\3\2\2\2\u0092\u0091\3\2\2\2\u0093\r\3\2\2\2\u0094\u0098"+
		"\7\b\2\2\u0095\u0097\5\16\b\2\u0096\u0095\3\2\2\2\u0097\u009a\3\2\2\2"+
		"\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009b\3\2\2\2\u009a\u0098"+
		"\3\2\2\2\u009b\u00bb\7\t\2\2\u009c\u009d\7\27\2\2\u009d\u009e\7\13\2\2"+
		"\u009e\u009f\5\20\t\2\u009f\u00a0\7\r\2\2\u00a0\u00a1\5\16\b\2\u00a1\u00a2"+
		"\7\30\2\2\u00a2\u00a3\5\16\b\2\u00a3\u00bb\3\2\2\2\u00a4\u00a5\7\31\2"+
		"\2\u00a5\u00a6\7\13\2\2\u00a6\u00a7\5\20\t\2\u00a7\u00a8\7\r\2\2\u00a8"+
		"\u00a9\5\16\b\2\u00a9\u00bb\3\2\2\2\u00aa\u00ab\5\20\t\2\u00ab\u00ac\7"+
		"\5\2\2\u00ac\u00bb\3\2\2\2\u00ad\u00ae\7*\2\2\u00ae\u00af\7\32\2\2\u00af"+
		"\u00b0\5\20\t\2\u00b0\u00b1\7\5\2\2\u00b1\u00bb\3\2\2\2\u00b2\u00b3\7"+
		"*\2\2\u00b3\u00b4\7\23\2\2\u00b4\u00b5\5\20\t\2\u00b5\u00b6\7\24\2\2\u00b6"+
		"\u00b7\7\32\2\2\u00b7\u00b8\5\20\t\2\u00b8\u00b9\7\5\2\2\u00b9\u00bb\3"+
		"\2\2\2\u00ba\u0094\3\2\2\2\u00ba\u009c\3\2\2\2\u00ba\u00a4\3\2\2\2\u00ba"+
		"\u00aa\3\2\2\2\u00ba\u00ad\3\2\2\2\u00ba\u00b2\3\2\2\2\u00bb\17\3\2\2"+
		"\2\u00bc\u00bd\b\t\1\2\u00bd\u00be\7\33\2\2\u00be\u00d2\5\20\t\21\u00bf"+
		"\u00c0\7%\2\2\u00c0\u00c1\7\25\2\2\u00c1\u00c2\7\23\2\2\u00c2\u00c3\5"+
		"\20\t\2\u00c3\u00c4\7\24\2\2\u00c4\u00d2\3\2\2\2\u00c5\u00c6\7%\2\2\u00c6"+
		"\u00c7\7*\2\2\u00c7\u00c8\7\13\2\2\u00c8\u00d2\7\r\2\2\u00c9\u00ca\7\13"+
		"\2\2\u00ca\u00cb\5\20\t\2\u00cb\u00cc\7\r\2\2\u00cc\u00d2\3\2\2\2\u00cd"+
		"\u00d2\t\2\2\2\u00ce\u00d2\7*\2\2\u00cf\u00d2\7)\2\2\u00d0\u00d2\7(\2"+
		"\2\u00d1\u00bc\3\2\2\2\u00d1\u00bf\3\2\2\2\u00d1\u00c5\3\2\2\2\u00d1\u00c9"+
		"\3\2\2\2\u00d1\u00cd\3\2\2\2\u00d1\u00ce\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1"+
		"\u00d0\3\2\2\2\u00d2\u00f8\3\2\2\2\u00d3\u00d4\f\20\2\2\u00d4\u00d5\t"+
		"\3\2\2\u00d5\u00f7\5\20\t\21\u00d6\u00d7\f\17\2\2\u00d7\u00d8\t\4\2\2"+
		"\u00d8\u00f7\5\20\t\20\u00d9\u00da\f\16\2\2\u00da\u00db\t\5\2\2\u00db"+
		"\u00f7\5\20\t\17\u00dc\u00dd\f\r\2\2\u00dd\u00de\t\6\2\2\u00de\u00f7\5"+
		"\20\t\16\u00df\u00e0\f\f\2\2\u00e0\u00e1\7\23\2\2\u00e1\u00e2\5\20\t\2"+
		"\u00e2\u00e3\7\24\2\2\u00e3\u00f7\3\2\2\2\u00e4\u00e5\f\13\2\2\u00e5\u00e6"+
		"\7\4\2\2\u00e6\u00f7\7$\2\2\u00e7\u00e8\f\n\2\2\u00e8\u00e9\7\4\2\2\u00e9"+
		"\u00ea\7*\2\2\u00ea\u00f3\7\13\2\2\u00eb\u00f0\5\20\t\2\u00ec\u00ed\7"+
		"\f\2\2\u00ed\u00ef\5\20\t\2\u00ee\u00ec\3\2\2\2\u00ef\u00f2\3\2\2\2\u00f0"+
		"\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f4\3\2\2\2\u00f2\u00f0\3\2"+
		"\2\2\u00f3\u00eb\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5"+
		"\u00f7\7\r\2\2\u00f6\u00d3\3\2\2\2\u00f6\u00d6\3\2\2\2\u00f6\u00d9\3\2"+
		"\2\2\u00f6\u00dc\3\2\2\2\u00f6\u00df\3\2\2\2\u00f6\u00e4\3\2\2\2\u00f6"+
		"\u00e7\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2"+
		"\2\2\u00f9\21\3\2\2\2\u00fa\u00f8\3\2\2\2\32\25\33!,\65;AKX[bhq\u0080"+
		"\u0086\u008a\u0092\u0098\u00ba\u00d1\u00f0\u00f3\u00f6\u00f8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}