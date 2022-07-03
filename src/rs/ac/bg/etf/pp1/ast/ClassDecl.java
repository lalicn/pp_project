// generated with ast extension for cup
// version 0.8
// 25/5/2022 21:14:37


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private ExtendingClass ExtendingClass;
    private VarDeclList VarDeclList;
    private ClassMethod ClassMethod;

    public ClassDecl (String I1, ExtendingClass ExtendingClass, VarDeclList VarDeclList, ClassMethod ClassMethod) {
        this.I1=I1;
        this.ExtendingClass=ExtendingClass;
        if(ExtendingClass!=null) ExtendingClass.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.ClassMethod=ClassMethod;
        if(ClassMethod!=null) ClassMethod.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ExtendingClass getExtendingClass() {
        return ExtendingClass;
    }

    public void setExtendingClass(ExtendingClass ExtendingClass) {
        this.ExtendingClass=ExtendingClass;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public ClassMethod getClassMethod() {
        return ClassMethod;
    }

    public void setClassMethod(ClassMethod ClassMethod) {
        this.ClassMethod=ClassMethod;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendingClass!=null) ExtendingClass.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(ClassMethod!=null) ClassMethod.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendingClass!=null) ExtendingClass.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(ClassMethod!=null) ClassMethod.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendingClass!=null) ExtendingClass.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(ClassMethod!=null) ClassMethod.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExtendingClass!=null)
            buffer.append(ExtendingClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethod!=null)
            buffer.append(ClassMethod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
