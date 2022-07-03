package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.NumberConst;
import rs.ac.bg.etf.pp1.ast.Plus;
import rs.ac.bg.etf.pp1.ast.PrintExprStatement;
import rs.ac.bg.etf.pp1.ast.AddopExpr;
import rs.ac.bg.etf.pp1.ast.ArrayDesignator;
import rs.ac.bg.etf.pp1.ast.AssignopDesignator;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.DecrementDesignator;
import rs.ac.bg.etf.pp1.ast.Divide;
import rs.ac.bg.etf.pp1.ast.Expression;
import rs.ac.bg.etf.pp1.ast.FuncCall;
import rs.ac.bg.etf.pp1.ast.IdentDesignator;
import rs.ac.bg.etf.pp1.ast.IncrementDesignator;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.Minus;
import rs.ac.bg.etf.pp1.ast.Modulo;
import rs.ac.bg.etf.pp1.ast.MultipleBaseExpsFactor;
import rs.ac.bg.etf.pp1.ast.MultipleFactorsTerm;
import rs.ac.bg.etf.pp1.ast.Multiply;
import rs.ac.bg.etf.pp1.ast.NegativeTermExpr;
import rs.ac.bg.etf.pp1.ast.NewArray;
import rs.ac.bg.etf.pp1.ast.PrintStatement;
import rs.ac.bg.etf.pp1.ast.ReadStatement;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Var;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	CodeGenerator() {
		super();

		Obj chrObj = Tab.find("chr");
		Obj ordObj = Tab.find("ord");
		chrObj.setAdr(Code.pc);
		ordObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		Obj lenObj = Tab.find("len");
		lenObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public int getMainPc() {
		return mainPc;
	}

	public void setMainPc(int mainPc) {
		this.mainPc = mainPc;
	}

	public void visit(ReadStatement readStatement) {
		if (readStatement.getDesignator().obj.getType() == Tab.intType) {
			Code.put(Code.read);
		} else {
			Code.put(Code.bread);
		}
		if (readStatement.getDesignator().obj.getType().getKind() == Struct.Array) {
			Code.store(new Obj(Obj.Elem, "elem", readStatement.getDesignator().obj.getType().getElemType()));
		} else {
			Code.store(readStatement.getDesignator().obj);
		}
	}

	public void visit(PrintExprStatement printExprStatement) {
		if (printExprStatement.getExpr().struct == Tab.intType) {
			Code.loadConst(printExprStatement.getNumConst());
			Code.put(Code.print);
		} else {
			Code.loadConst(printExprStatement.getNumConst());
			Code.put(Code.bprint);
		}
	}

	public void visit(PrintStatement printStatement) {
		if (printStatement.getExpr().struct == Tab.intType) {
			Code.loadConst(5); // Sirina za int
			Code.put(Code.print);
		} else {
			Code.loadConst(1); // Sirina za char
			Code.put(Code.bprint);
		}
	}

	public void visit(MethodTypeName methodTypeName) {
		if ("main".equalsIgnoreCase(methodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);

		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	}

	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(AssignopDesignator assignopDesignator) {
		Code.store(assignopDesignator.getDesignator().obj);
	}

	public void visit(IncrementDesignator incrementDesignator) {
		if (incrementDesignator.getDesignator().getClass() == ArrayDesignator.class) {
			Code.put(Code.dup2);
		} 
		Code.load(incrementDesignator.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(incrementDesignator.getDesignator().obj);
	}

	public void visit(DecrementDesignator decrementDesignator) {
		if (decrementDesignator.getDesignator().getClass() == ArrayDesignator.class) {
			Code.put(Code.dup2);
		} 
		Code.load(decrementDesignator.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(decrementDesignator.getDesignator().obj);
	}

//	public void visit(ArrayDesignator arrayDesignator) {
//		Code.load(arrayDesignator.getDesignator().obj);
//	}

	public void visit(IdentDesignator identDesignator) {
		if (identDesignator.getParent().getClass() == ArrayDesignator.class) {
			Code.load(identDesignator.obj);	//Adr niza ide na ExprStack
		}
	}

	public void visit(AddopExpr addopExpr) {
		if (addopExpr.getAddop().getClass() == Plus.class) {
			Code.put(Code.add);
		} else if (addopExpr.getAddop().getClass() == Minus.class) {
			Code.put(Code.sub);
		}

	}

	public void visit(NegativeTermExpr negativeTermExpr) {
		Code.put(Code.neg);
	}

	public void visit(MultipleFactorsTerm multipleFactorsTerm) {
		if (multipleFactorsTerm.getMulop().getClass() == Multiply.class) {
			Code.put(Code.mul);
		} else if (multipleFactorsTerm.getMulop().getClass() == Divide.class) {
			Code.put(Code.div);
		} else if (multipleFactorsTerm.getMulop().getClass() == Modulo.class) {
			Code.put(Code.rem);
		}
	}

	public void visit(MultipleBaseExpsFactor multipleBaseExpsFactor) {
		Code.loadConst(1); //vr, brojac, 1
		Code.put(Code.sub);	//vr, brojac = brojac - 1
		Code.put(Code.dup_x1); //brojac, vr, brojac
		Code.put(Code.pop);	//brojac, vr
		Code.put(Code.dup);	//brojac, vr, osn
		
		//ulazim pocetni brojac - 1 puta u petlju!
		 
		int adrPocWhile = Code.pc;  //putJump skace ovdje
		
		Code.put(Code.dup_x2); //osn, brojac, vr, osn
		Code.put(Code.pop);	//osn, brojac, vr
		Code.put(Code.dup_x2); //vr, osn, brojac, vr
		Code.put(Code.pop);//vr, osn, brojac
		Code.put(Code.dup); //vr, osn, brojac, brojac
		Code.loadConst(0); //vr, osn, brojac, brojac, 0
		
		//ulazak u while petlju
		Code.putFalseJump(Code.gt, 0); //vr, osn, brojac (skloni poslednja 2 zbog poredjenja)
		int adrIzlIzWhile = Code.pc - 2;
		 
		 
		Code.put(Code.dup_x2);	//brojac, vr, osn, brojac
		Code.put(Code.pop);	//brojac, vr, osn
		Code.put(Code.dup_x2); //osn, brojac, vr, osn
		Code.put(Code.mul);	//osn, brojac, vr = vr * osn
		Code.put(Code.dup_x2);	//vr, osn, brojac, vr
		Code.put(Code.pop);	//vr, osn, brojac
		Code.loadConst(1);	//vr, osn, brojac, 1
		Code.put(Code.sub);	//vr, osn, brojac = brojac - 1
		Code.put(Code.dup_x2); //brojac, vr, osn, brojac
		Code.put(Code.pop); //brojac, vr, osn
		 
		Code.putJump(adrPocWhile);
		Code.fixup(adrIzlIzWhile); 
		
		//vr, osn, brojac
		Code.put(Code.pop);	//vr, osn
		Code.put(Code.pop); //vr

	}

	public void visit(NumberConst numberConst) {
		Obj numberCnt = Tab.insert(Obj.Con, "$", numberConst.struct);
		numberCnt.setLevel(0);
		numberCnt.setAdr(numberConst.getNumConst());
		Code.load(numberCnt);

	}

	public void visit(CharConst charConst) {
		Obj charCnt = Tab.insert(Obj.Con, "$", charConst.struct);
		charCnt.setLevel(0);
		charCnt.setAdr(charConst.getCharConst());
		Code.load(charCnt);
	}

	public void visit(BoolConst boolConst) {
		Obj boolCnt = Tab.insert(Obj.Con, "$", boolConst.struct);
		boolCnt.setLevel(0);
		if (boolConst.getBoolConst().booleanValue() == true) {
			boolCnt.setAdr(1);
		} else {
			boolCnt.setAdr(0);
		}
		Code.load(boolCnt);
	}
	
	public void visit(Expression expression) {
		if(expression.getParent().getClass() == MultipleBaseExpsFactor.class) {
			
		}
	}

	public void visit(NewArray newArray) {
		Code.put(Code.newarray);
		if (newArray.struct.getElemType() == Tab.charType) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	public void visit(Var var) {
		Code.load(var.getDesignator().obj);
	}

	public void visit(FuncCall funcCall) {
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
//		if(funcCall.getDesignator().obj.getType() != Tab.noType) {
//			Code.put(Code.pop);
//		}
	}

}
