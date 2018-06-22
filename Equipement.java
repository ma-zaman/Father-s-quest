//import com.modeliosoft.modelio.javadesigner.annotations.objid;


public abstract class Equipement {

    private String Nom;


    private char Type;


    public String getNom() {
        return this.Nom;
    }


    public void setNom(String value) {
        this.Nom = value;
    }


    public char getType() {
        return this.Type;
    }


    public void setType(char value) {
        this.Type = value;
    }


    public Equipement(String nom, char type) {
        this.Nom = nom;
        this.Type = type;
    }

}
