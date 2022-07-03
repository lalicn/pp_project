// generated with ast extension for cup
// version 0.8
// 25/5/2022 21:14:37


package rs.ac.bg.etf.pp1.ast;

public class NoVarCommaDecl extends VarList {

    public NoVarCommaDecl () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoVarCommaDecl(\n");

        buffer.append(tab);
        buffer.append(") [NoVarCommaDecl]");
        return buffer.toString();
    }
}
