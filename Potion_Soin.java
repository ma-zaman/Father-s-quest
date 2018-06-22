//import com.modeliosoft.modelio.javadesigner.annotations//.objid;

public class Potion_Soin extends Potion {
  
    public Potion_Soin(int soin) {
        super(soin, "Potion de Soin", 'd');
    }

  
    public String toString() {
        return(this.getNom()+" : \n - Soin : "+this.getPuissance()+"\n");
    }

}
