// generated with ast extension for cup
// version 0.8
// 25/5/2022 21:14:37


package rs.ac.bg.etf.pp1.ast;

public class Decl extends Declarations {

    private Declarations Declarations;
    private SpecificDeclaration SpecificDeclaration;

    public Decl (Declarations Declarations, SpecificDeclaration SpecificDeclaration) {
        this.Declarations=Declarations;
        if(Declarations!=null) Declarations.setParent(this);
        this.SpecificDeclaration=SpecificDeclaration;
        if(SpecificDeclaration!=null) SpecificDeclaration.setParent(this);
    }

    public Declarations getDeclarations() {
        return Declarations;
    }

    public void setDeclarations(Declarations Declarations) {
        this.Declarations=Declarations;
    }

    public SpecificDeclaration getSpecificDeclaration() {
        return SpecificDeclaration;
    }

    public void setSpecificDeclaration(SpecificDeclaration SpecificDeclaration) {
        this.SpecificDeclaration=SpecificDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Declarations!=null) Declarations.accept(visitor);
        if(SpecificDeclaration!=null) SpecificDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Declarations!=null) Declarations.traverseTopDown(visitor);
        if(SpecificDeclaration!=null) SpecificDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Declarations!=null) Declarations.traverseBottomUp(visitor);
        if(SpecificDeclaration!=null) SpecificDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Decl(\n");

        if(Declarations!=null)
            buffer.append(Declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SpecificDeclaration!=null)
            buffer.append(SpecificDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Decl]");
        return buffer.toString();
    }
}
