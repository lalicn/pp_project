package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormalParamArrayDecl;
import rs.ac.bg.etf.pp1.ast.FormalParamD;
import rs.ac.bg.etf.pp1.ast.FormalParamDecl;
import rs.ac.bg.etf.pp1.ast.VarArrayD;
import rs.ac.bg.etf.pp1.ast.VarArrayDComma;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VarDeclaration;
import rs.ac.bg.etf.pp1.ast.VarDeclarationComma;
import rs.ac.bg.etf.pp1.ast.VarPos;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static class FormParamCounter extends CounterVisitor {

		public void visit(FormalParamD formalParamD) {
			count++;
		}
		
		public void visit(FormalParamArrayDecl formalParamArrayDecl) {
			count++;
		}
	}

	public static class VarCounter extends CounterVisitor {

		public void visit(VarDeclaration varDeclaration) {
			count++;
		}
		
		public void visit(VarArrayD varArrayD) {
			count++;
		}
		
		public void visit(VarDeclarationComma varDeclarationComma) {
			count++;
		}
		
		public void visit(VarArrayDComma varArrayDComma) {
			count++;
		}
		
		
		
		
	}

}
