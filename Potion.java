//import com.modeliosoft.modelio.javadesigner.annotations.objid;

public class Potion extends Equipement {
  
    private int Puissance;

  
    public Potion(int puissance, String nom, char type) {
        super(nom, type);
        this.Puissance = puissance;
    }

  
    public int getPuissance() {
        return this.Puissance;
    }

}
