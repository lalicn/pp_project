// generated with ast extension for cup
// version 0.8
// 25/5/2022 21:14:37


package rs.ac.bg.etf.pp1.ast;

public class ConstDBool extends ConstD {

    private String varName;
    private Assignop Assignop;
    private Boolean boolConst;

    public ConstDBool (String varName, Assignop Assignop, Boolean boolConst) {
        this.varName=varName;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.boolConst=boolConst;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Boolean getBoolConst() {
        return boolConst;
    }

    public void setBoolConst(Boolean boolConst) {
        this.boolConst=boolConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Assignop!=null) Assignop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDBool(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+boolConst);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDBool]");
        return buffer.toString();
    }
}
