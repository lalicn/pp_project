// generated with ast extension for cup
// version 0.8
// 25/5/2022 21:14:37


package rs.ac.bg.etf.pp1.ast;

public class ConstDNum extends ConstD {

    private String varName;
    private Assignop Assignop;
    private Integer numConst;

    public ConstDNum (String varName, Assignop Assignop, Integer numConst) {
        this.varName=varName;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.numConst=numConst;
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

    public Integer getNumConst() {
        return numConst;
    }

    public void setNumConst(Integer numConst) {
        this.numConst=numConst;
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
        buffer.append("ConstDNum(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+numConst);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDNum]");
        return buffer.toString();
    }
}
