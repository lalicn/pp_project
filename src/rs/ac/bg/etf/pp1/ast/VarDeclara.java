// generated with ast extension for cup
// version 0.8
// 25/5/2022 21:14:37


package rs.ac.bg.etf.pp1.ast;

public class VarDeclara extends VarDecl {

    private VarType VarType;
    private VarList VarList;
    private VarPos VarPos;

    public VarDeclara (VarType VarType, VarList VarList, VarPos VarPos) {
        this.VarType=VarType;
        if(VarType!=null) VarType.setParent(this);
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
        this.VarPos=VarPos;
        if(VarPos!=null) VarPos.setParent(this);
    }

    public VarType getVarType() {
        return VarType;
    }

    public void setVarType(VarType VarType) {
        this.VarType=VarType;
    }

    public VarList getVarList() {
        return VarList;
    }

    public void setVarList(VarList VarList) {
        this.VarList=VarList;
    }

    public VarPos getVarPos() {
        return VarPos;
    }

    public void setVarPos(VarPos VarPos) {
        this.VarPos=VarPos;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarType!=null) VarType.accept(visitor);
        if(VarList!=null) VarList.accept(visitor);
        if(VarPos!=null) VarPos.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarType!=null) VarType.traverseTopDown(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
        if(VarPos!=null) VarPos.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarType!=null) VarType.traverseBottomUp(visitor);
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        if(VarPos!=null) VarPos.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclara(\n");

        if(VarType!=null)
            buffer.append(VarType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarPos!=null)
            buffer.append(VarPos.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclara]");
        return buffer.toString();
    }
}
