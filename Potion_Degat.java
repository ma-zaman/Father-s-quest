//import com.modeliosoft.modelio.javadesigner.annotations.objid;


public class Potion_Degat extends Potion {
  
    public Potion_Degat(int degats) {
        super(degats, "Potion de dégats", 'd');
    }

  
    public String toString() {
        return(this.getNom()+" : \n - Dégats : "+this.getPuissance()+"\n");
    }

}
