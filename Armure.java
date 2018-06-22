//import com.modeliosoft.modelio.javadesigner.annotations.objid;


public class Armure extends Equipement {

    private int Encombrement;


    private int Solidite;


    public int getSolidite() {
        return this.Solidite;
    }


    public void setSolidite(int value) {
        this.Solidite = value;
    }


    public int getEncombrement() {
        return this.Encombrement;
    }


    public void setEncombrement(int value) {
        this.Encombrement = value;
    }


    public Armure(int encombrement, int solidite) {
        super("Armure", 'a');
        this.Encombrement = encombrement;
        this.Solidite = solidite;
    }


    public String toString() {
        return(this.getNom()+" : \n - Solidite : "+this.Solidite+"\n - Encombrement : "+this.Encombrement+"\n\n");
    }

}
