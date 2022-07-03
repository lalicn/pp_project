// generated with ast extension for cup
// version 0.8
// 25/5/2022 21:14:37


package rs.ac.bg.etf.pp1.ast;

public class Consts extends ConstList {

    private ConstList ConstList;
    private ConstD ConstD;

    public Consts (ConstList ConstList, ConstD ConstD) {
        this.ConstList=ConstList;
        if(ConstList!=null) ConstList.setParent(this);
        this.ConstD=ConstD;
        if(ConstD!=null) ConstD.setParent(this);
    }

    public ConstList getConstList() {
        return ConstList;
    }

    public void setConstList(ConstList ConstList) {
        this.ConstList=ConstList;
    }

    public ConstD getConstD() {
        return ConstD;
    }

    public void setConstD(ConstD ConstD) {
        this.ConstD=ConstD;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstList!=null) ConstList.accept(visitor);
        if(ConstD!=null) ConstD.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstList!=null) ConstList.traverseTopDown(visitor);
        if(ConstD!=null) ConstD.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstList!=null) ConstList.traverseBottomUp(visitor);
        if(ConstD!=null) ConstD.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Consts(\n");

        if(ConstList!=null)
            buffer.append(ConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstD!=null)
            buffer.append(ConstD.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Consts]");
        return buffer.toString();
    }
}
