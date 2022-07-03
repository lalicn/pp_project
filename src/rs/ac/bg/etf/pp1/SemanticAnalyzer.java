package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
	private Struct bool_type = new Struct(Struct.Bool);
	private Struct var_type = Tab.noType, method_type = Tab.noType, const_type = Tab.noType;
	private Obj currentMethod = null;
	private boolean errorDetected = false;
	private int nVars, brMain = 0;
	private boolean returnFound = false;

	Logger log = Logger.getLogger(getClass());

	public SemanticAnalyzer() {
		initialization();
	}

	public void initialization() {
		Tab.init();
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", bool_type));
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(VarType varType) {
		var_type = varType.getType().struct;
	}

	public void visit(VarDeclaration varDeclaration) {
		if (Tab.currentScope().findSymbol(varDeclaration.getVarName()) == null) {
			Obj var_node = Tab.insert(Obj.Var, varDeclaration.getVarName(), var_type);
			DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
			stv.visitObjNode(var_node);
			report_info("Deklarisana promjenljiva " + varDeclaration.getVarName() + ": " + stv.getOutput(),
					varDeclaration);
		} else {
			report_error("Greska: " + varDeclaration.getVarName() + " je vec definisana", varDeclaration);
		}
	}

	public void visit(VarArrayD varArrayD) {
		if (Tab.currentScope().findSymbol(varArrayD.getVarName()) == null) {
			Obj var_node = Tab.insert(Obj.Var, varArrayD.getVarName(), new Struct(Struct.Array, var_type));
			DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
			stv.visitObjNode(var_node);
			report_info("Deklarisan niz " + varArrayD.getVarName() + ": " + stv.getOutput(), varArrayD);
		} else {
			report_error("Greska: " + varArrayD.getVarName() + " je vec definisana", varArrayD);
		}

	}

	public void visit(ConstType constType) {
		const_type = constType.getType().struct;
	}

	public void visit(ConstDNum constDNum) {
		if (const_type == Tab.intType) {
			if (Tab.currentScope().findSymbol(constDNum.getVarName()) == null) {
				Obj constNode = Tab.insert(Obj.Con, constDNum.getVarName(), const_type);
				constNode.setAdr(constDNum.getNumConst().intValue());
				constNode.setLevel(0);
				DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
				stv.visitObjNode(constNode);
				report_info("Deklarisana konstanta " + constDNum.getVarName() + ": " + stv.getOutput(), constDNum);
			} else {
				report_error("Greska: " + constDNum.getVarName() + " je vec definisana", constDNum);
			}
		} else {
			report_error(
					"Greska na liniji " + constDNum.getLine() + " : nekompatibilni tipovi u deklaraciji konstante.",
					null);
		}
	}

	public void visit(ConstDChar constDChar) {
		if (const_type == Tab.charType) {
			if (Tab.currentScope().findSymbol(constDChar.getVarName()) == null) {
				Obj charNode = Tab.insert(Obj.Con, constDChar.getVarName(), const_type);
				charNode.setAdr(constDChar.getCharConst().charValue());
				charNode.setLevel(0);
				DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
				stv.visitObjNode(charNode);
				report_info("Deklarisana konstanta " + constDChar.getVarName() + ": " + stv.getOutput(), constDChar);
			} else {
				report_error("Greska: " + constDChar.getVarName() + " je vec definisana", constDChar);
			}
		} else {
			report_error(
					"Greska na liniji " + constDChar.getLine() + " : nekompatibilni tipovi u deklaraciji konstante.",
					null);
		}
	}

	public void visit(ConstDBool constDBool) {
		if (const_type == bool_type) {
			if (Tab.currentScope().findSymbol(constDBool.getVarName()) == null) {
				Obj boolNode = Tab.insert(Obj.Con, constDBool.getVarName(), const_type);
				if (constDBool.getBoolConst().booleanValue() == true) {
					boolNode.setAdr(1);
				} else {
					boolNode.setAdr(0);
				}
				boolNode.setLevel(0);
				DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
				stv.visitObjNode(boolNode);
				report_info("Deklarisana konstanta " + constDBool.getVarName() + ": " + stv.getOutput(), constDBool);
			} else {
				report_error("Greska: " + constDBool.getVarName() + " je vec definisana", constDBool);
			}
		} else {
			report_error(
					"Greska na liniji " + constDBool.getLine() + " : nekompatibilni tipovi u deklaraciji konstante.",
					null);
		}
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip! ", type);
				type.struct = Tab.noType;
			}
		}
	}

	public void visit(MethodTypeName methodTypeName) {
		if (methodTypeName.getMethName().equalsIgnoreCase("main") && method_type != Tab.noType) {
			report_error("Greska: Main funkcija mora biti deklarisana kao void metoda! ", null);
			return;
		}
		if (methodTypeName.getMethName().equalsIgnoreCase("main")) {
			brMain++;
		}
		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), method_type);
		methodTypeName.obj = currentMethod;
		Tab.openScope();
	}

	public void visit(MethodDecl methodDecl) {
		if (methodDecl.getMethodTypeName().getMethName().equalsIgnoreCase("main")
				&& methodDecl.getFormPars().getClass() != NoFormParam.class) {
			report_error("Greska: Main funkcija mora biti deklarisana bez argumenata! ", null);
			return;
		}
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName()
					+ " nema return iskaz!", null);
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		returnFound = false;
		currentMethod = null;
	}

	public void visit(TypeReturn typeReturn) {
		method_type = typeReturn.getType().struct;
	}

	public void visit(VoidReturn voidReturn) {
		method_type = Tab.noType;
	}

//	public void visit(FuncCall funcCall) {
//		Obj func = funcCall.getDesignator().obj;
//		if(Obj.Meth == func.getKind()) {
//			if(Tab.noType == func.getType()) {
//				report_error("Semanticka greska " + func.getName() + " ne moze se koristiti u izrazima, jer nema povratnu vrijednost ", funcCall);
//			}else {
//				report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
//				funcCall.struct = func.getType();
//			}
//			
//		} else {
//			report_error("Greska na liniji " + funcCall.getLine() + " : ime " + func.getName() + " nije funkcija! ", null);
//			funcCall.struct = Tab.noType;
//		}
//	}

	public void visit(PrintStatement printStatement) {
		if (printStatement.getExpr().struct != Tab.intType && printStatement.getExpr().struct != Tab.charType
				&& printStatement.getExpr().struct.getKind() != Struct.Bool)
			report_error("Semanticka greska na liniji " + printStatement.getLine()
					+ "; Operand instrukcije PRINT mora biti char, int ili bool tipa", null);

	}

	public void visit(PrintExprStatement printExprStatement) {
		if (printExprStatement.getExpr().struct != Tab.intType && printExprStatement.getExpr().struct != Tab.charType
				&& printExprStatement.getExpr().struct.getKind() != Struct.Bool)
			report_error("Semanticka greska na liniji " + printExprStatement.getLine()
					+ "; Operand instrukcije PRINT mora biti char, int ili bool tipa", null);

	}

	public void visit(MultipleFactorsTerm multipleFactorsTerm) {
		if (multipleFactorsTerm.getTerm().struct == Tab.intType
				&& multipleFactorsTerm.getFactor().struct == Tab.intType) {
			multipleFactorsTerm.struct = multipleFactorsTerm.getFactor().struct;
		} else {
			report_error("Greska: Term i Factor moraju biti int tipa", multipleFactorsTerm);
		}

	}

	public void visit(SingleFactorTerm singleFactorTerm) {
		singleFactorTerm.struct = singleFactorTerm.getFactor().struct;
	}

	public void visit(MultipleBaseExpsFactor multipleBaseExpsFactor) {
		multipleBaseExpsFactor.struct = multipleBaseExpsFactor.getBaseExp().struct;
	}

	public void visit(SingleBaseExpFactor singleBaseExpFactor) {
		singleBaseExpFactor.struct = singleBaseExpFactor.getBaseExp().struct;
	}

	public void visit(NumberConst numberConst) {
		numberConst.struct = Tab.intType;
	}

	public void visit(CharConst charConst) {
		charConst.struct = Tab.charType;
	}

	public void visit(BoolConst boolConst) {
		boolConst.struct = bool_type;
	}

	public void visit(Expression expression) {
		expression.struct = expression.getExpr().struct;
	}

	public void visit(NewArray newArray) {
		if (newArray.getExpr().struct == Tab.intType) {
			newArray.struct = new Struct(Struct.Array, newArray.getType().struct);
		} else {
			report_error("Greska: Mora biti int tipa", newArray);
		}

	}

	public void visit(Var var) {
		var.struct = var.getDesignator().obj.getType();
	}

	public void visit(AddopExpr addopExpr) {
		if (addopExpr.getExpr().struct == Tab.intType && addopExpr.getTerm().struct == Tab.intType) {
			addopExpr.struct = addopExpr.getExpr().struct;
		} else {
			report_error("Greska: Mora biti int tipa", addopExpr);
		}

	}

	public void visit(TermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
	}

	public void visit(NegativeTermExpr negativeTermExpr) {
		if (negativeTermExpr.getTerm().struct == Tab.intType) {
			negativeTermExpr.struct = negativeTermExpr.getTerm().struct;
		} else {
			report_error("Greska: Mora biti int tipa", negativeTermExpr);
		}

	}

	public void visit(AssignopDesignator assignopDesignator) {
		if (assignopDesignator.getDesignator().getClass() == IdentDesignator.class
				|| assignopDesignator.getDesignator().getClass() == ArrayDesignator.class) {
			Struct designator = assignopDesignator.getDesignator().obj.getType();
			Struct expr = assignopDesignator.getExpr().struct;
			if (!designator.equals(expr)) {
				report_error("Greska na liniji " + assignopDesignator.getLine() + " : "
						+ "nekompatibilni tipovi u dodjeli vrednosti! ", null);
			}
		} else {
			report_error(
					"Semanticka greska na liniji " + assignopDesignator.getLine()
							+ "; Designator mora oznacavati promjenljivu, element niza ili polje unutar objekta!",
					null);
		}
	}

	public void visit(IncrementDesignator incrementDesignator) {
		if (incrementDesignator.getDesignator().getClass() == IdentDesignator.class
				|| incrementDesignator.getDesignator().getClass() == ArrayDesignator.class) {
			Struct designator = incrementDesignator.getDesignator().obj.getType();
			if (designator != Tab.intType) {
				report_error(
						"Greska na liniji " + incrementDesignator.getLine() + " : " + "designator mora biti tipa int! ",
						null);
			}
		} else {
			report_error(
					"Semanticka greska na liniji " + incrementDesignator.getLine()
							+ "; Designator mora oznacavati promjenljivu, element niza ili polje unutar objekta!",
					null);
		}
	}

	public void visit(DecrementDesignator decrementDesignator) {
		if (decrementDesignator.getDesignator().getClass() == IdentDesignator.class
				|| decrementDesignator.getDesignator().getClass() == ArrayDesignator.class) {
			Struct designator = decrementDesignator.getDesignator().obj.getType();
			if (designator != Tab.intType) {
				report_error(
						"Greska na liniji " + decrementDesignator.getLine() + " : " + "designator mora biti tipa int! ",
						null);
			}
		} else {
			report_error(
					"Semanticka greska na liniji " + decrementDesignator.getLine()
							+ "; Designator mora oznacavati promjenljivu, element niza ili polje unutar objekta!",
					null);
		}
	}

	public void visit(ArrayDesignator arrayDesignator) {
		if (arrayDesignator.getDesignator().obj.getType().getKind() == Struct.Array) {
			if (arrayDesignator.getExpr().struct == Tab.intType) {
				Obj childObj = arrayDesignator.getDesignator().obj;
				arrayDesignator.obj = new Obj(Obj.Elem, "elem", childObj.getType().getElemType());

			} else {
				report_error("Greska: Expr mora biti int tipa!", arrayDesignator);
			}

		} else {
			report_error("Greska: Designator mora biti niz!", arrayDesignator);
		}

	}

	public void visit(IdentDesignator identDesignator) {
		Obj obj = Tab.find(identDesignator.getName());
		if (obj == Tab.noObj) {
			report_error("Greska na liniji " + identDesignator.getLine() + " : ime " + identDesignator.getName()
					+ " nije deklarisano! ", null);
		}
		identDesignator.obj = obj;
	}

	public void visit(ReadStatement readStatement) {
		if (readStatement.getDesignator().obj.getKind() == Obj.Var
				|| readStatement.getDesignator().obj.getKind() == Obj.Elem
				|| readStatement.getDesignator().obj.getKind() == Obj.Fld) {
			if (readStatement.getDesignator().obj.getType() != Tab.intType
					&& readStatement.getDesignator().obj.getType() != Tab.charType
					&& readStatement.getDesignator().obj.getType().getKind() != Struct.Bool)
				report_error("Semanticka greska na liniji " + readStatement.getLine()
						+ "; Operand instrukcije READ mora biti char, int ili bool tipa", null);
		} else {
			report_error("Semanticka greska na liniji " + readStatement.getLine()
					+ "; Operand instrukcije READ mora biti promjenljiva, element niza ili polje unutar objekta", null);
		}

	}

	public boolean passed() {
		return (errorDetected == false && brMain == 1);
	}

	public int getnVars() {
		return nVars;
	}

	public void setnVars(int nVars) {
		this.nVars = nVars;
	}

}
