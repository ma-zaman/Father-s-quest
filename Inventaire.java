//import com.modeliosoft.modelio.javadesigner.annotations.objid;
import java.util.Scanner;
import java.util.* ;

public class Inventaire {


    private int Capacite_Max;



    private int Nb_Equipement;

    private ArrayList<Equipement> Contenu ;

    public Inventaire()
    {
      this.Capacite_Max = 5;
      this.Nb_Equipement = 0;

      this.Contenu = new ArrayList<Equipement>();
    }

    public Inventaire(int capa_max)
    {
      this.Capacite_Max = capa_max;
      this.Nb_Equipement = 0;

      this.Contenu = new ArrayList<Equipement>();
    }



    public int getCapacite_Max() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.Capacite_Max;
    }



    public void setCapacite_Max(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.Capacite_Max = value;
    }



    public void AddElementBP(Equipement e) {
      if(this.getNb_Equipement() < this.getCapacite_Max())
      {
        this.Contenu.add(e);
        this.setNb_Equipement(this.getNb_Equipement()+1);
      }
    }

    public void jeterObjet(int i)
    {
      if(this.getNb_Equipement() > (i))
      {
        this.Contenu.remove(i);
        this.setNb_Equipement(this.getNb_Equipement()-1);
      }
    }

    public Equipement getObjet(int i)
    {
      return this.Contenu.get(i);
    }



    public int getNb_Equipement() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.Nb_Equipement;
    }



    public void setNb_Equipement(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.Nb_Equipement = value;
    }

    public String toString()
    {
      String str = "";
      str += "\n_______________________________________\n Votre inventaire : \n\n";
      for (int i = 0; i< this.getNb_Equipement(); i++)
      {
        str += "Equipement n°"+(i+1)+" "+this.getObjet(i);
      }
      str += ("\n_______________________________________\n\n¡¡¡¡¡ Vous pouvez encore prendre "+(this.getCapacite_Max()-this.getNb_Equipement())+" objets dans votre sac !!!!!\n");
      return str;
    }

    public String toStringC()
    {
      String str = "";
      str += "\n_______________________________________\n Coffre : \n\n";
      for (int i = 0; i< this.getNb_Equipement(); i++)
      {
        str += "Equipement n°"+(i+1)+" "+this.getObjet(i);
      }
      str += ("\n_______________________________________\n\n");
      return str;
    }

}
