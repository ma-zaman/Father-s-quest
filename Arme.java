//import com.modeliosoft.modelio.javadesigner.annotations//.objid;

//@objid ("ba49366f-11c1-4d6a-be18-626dd6ffaaec")
public class Arme extends Equipement {
    //@objid ("909a5a25-6245-4c10-8485-e1a2ad7daa37")
    private int Impact;

    //@objid ("4ef7e0e3-c46a-492f-9701-1f21ea8c8d16")
    private int Maniabilite;

    //@objid ("17bde784-a78c-4305-a25c-609db0e61b5b")
    public int getImpact() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.Impact;
    }

    //@objid ("8b78305c-64d8-4a03-9ff3-344acd547a41")
    public void setImpact(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.Impact = value;
    }

    //@objid ("3d05f314-ba6e-4bd9-addd-056d291fd338")
    public int getManiabilite() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.Maniabilite;
    }

    //@objid ("2b8fd36c-8bbe-49b0-9a3f-7672fc643241")
    public void setManiabilite(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.Maniabilite = value;
    }

    //@objid ("0472b6ee-5245-49a2-8081-e683adda315c")
    public Arme(int impact, int maniabilite) {
        super("Arme", 'w'); //w = type d'arme
        this.Impact = impact;
        this.Maniabilite = maniabilite;
    }

    //@objid ("afd1ce3f-cd10-42cb-9626-0c6e2cb22105")
    public String toString() {
        return(this.getNom()+" : \n - Impact : "+this.Impact+"\n - Maniabilite : "+this.Maniabilite+"\n\n");
    }

}
