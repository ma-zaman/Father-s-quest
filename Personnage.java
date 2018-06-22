//import com.modeliosoft.modelio.javadesigner.annotations.objid;
import java.util.Scanner;
import java.util.Random;
import java.util.*;
//import com.modeliosoft.modelio.javadesigner.annotations.objid;
public abstract class Personnage {
  private int lvl;
  private String nom;
  private int force;
  private int adresse;
  private int resistance;
  private int xp;
  private int vie;
  private int vieMax;
  private int posX;
  private int posY;

    public Personnage()
    {
      this.posX = -1;
      this.posY = -1;

      this.vie = 0;
      this.vieMax = 0;

      this.xp = 0;

      this.force = 0;
      this.adresse = 0;
      this.resistance = 0;
    }

    public Personnage(String nom, int vie, int xp, int x, int y, int f, int a, int r)
    {
      this.nom = nom;

      this.vie = vie;
      this.vieMax = vie;

      this.xp = xp;

      this.posX = x;
      this.posY = y;

      this.force = f;
      this.adresse = a;
      this.resistance = r;
    }

    public Personnage(int x, int y) {
        this.posX = x;
        this.posY = y;

        this.vie = 10;
        this.vieMax = 10;

        this.xp = 0;

        this.force = -1;
        this.adresse = -1;
        this.resistance = -1;

        try {
          this.setCaracteristiques();
        }
        catch (InputMismatchException e) {
          System.out.println("Erreur : "+e+"\n Entrez des entiers\n");
          this.setCaracteristiques();
        }
    }

    public void setCaracteristiques() throws InputMismatchException
    {
      int i=0;
      int Val;
      Scanner sc = new Scanner(System.in);
      System.out.println("Entrez le nom de votre hero : ");
      this.nom = sc.next();
      while (i<18)
      {

        Representation_caracteristiques();
        System.out.println("force : ");
        Val = sc.nextInt();
        i += Val;
        if(i<=18)
        {
          this.force = Val;
          Representation_caracteristiques();
          System.out.println("adresse : ");
          Val = sc.nextInt();
          i += Val;
          if(i<=18)
          {
            this.adresse = Val;
            this.resistance = (18-i);
            i+=this.resistance;
            Representation_caracteristiques();
            System.out.println("Voulez-vous valider vos capacitées (o :oui/n :non): ");
            char c = sc.next().charAt(0);

            if (c != 'o')
            {
              this.force = -1;
              this.adresse = -1;
              this.resistance = -1;

              i=0;
            }
          }

          else
          {
            this.force = -1;
            this.adresse = -1;
            this.resistance = -1;

            i=0;
          }
        }

        else
        {
          i=0;
        }

      }
    }

    private void Representation_caracteristiques() {
        int i=0;
        int pts=18;
        String F="";
        String A="";
        String R="";
        if(this.force != -1)
        {
          pts -= this.force;
        }
        if(this.adresse != -1)
        {
          pts -= this.adresse;
        }
        if(this.resistance != -1)
        {
          pts -= this.resistance;
        }
        System.out.print("\033[H\033[2J");
        System.out.println("\nForce : la puissance physique du hero.");
        System.out.println("adresse : la capacité du hero à mouvoir son corps comme il l’entend.");
        System.out.println("Résistance : le point jusqu’auquel le corps du hero peut résister aux agressions extérieures\n");
        System.out.println("Il vous reste " + pts + " points à répartir\n");
        while(i<this.force)
        {
            F+="#";
              i++;
        }
        i=0;
        while(i<this.adresse)
        {
              A+="#";
              i++;
        }
        i=0;
        while(i<this.resistance)
        {
              R+="#";
              i++;
        }

        if(this.force != -1)
        {
              System.out.println("Force : "+F+" ("+this.force+")");
        }

        if(this.adresse != -1)
        {
              System.out.println("adresse : "+A+" ("+this.adresse+")");
        }

        if(this.resistance != -1)
        {
              System.out.println("Résistance : "+R+" ("+this.resistance+")");
        }
    }

    public int getLvl(){
    	return this.lvl;
    }

    public void setLvl(int lvl){
    	this.lvl = lvl;
    }

    public void setNom(String nom)
    {
      this.nom = nom;
    }

    public String getNom()
    {
      return this.nom;
    }

    public int getForce() {
        return this.force;
    }

    public void setForce(int value) {
        this.force = value;
    }

    public int getXp() {
        return this.xp;
    }

    public void setXp(int value) {
        this.xp = value;
    }

    public int getAdresse() {
        return this.adresse;
    }

    public void setAdresse(int value) {
        this.adresse = value;
    }

    public void setResistance(int value) {
        this.resistance = value;
    }

    public int getResistance() {
        return this.resistance;
    }

    public int getVie() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.vie;
    }

    public void setVie(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.vie = value;
    }

    public int getPosX()
    {
      return this.posX;
    }

    public void setPosX(int i)
    {
      this.posX = i;
    }

    public int getPosY()
    {
      return this.posY;
    }

    public void setPosY(int i)
    {
      this.posY = i;
    }

    public int lancerDee()
    {
    	Random rand= new Random();
      int resultat_dee = 0;
      for (int i = 0; i< this.getLvl(); i++)
      {
        resultat_dee += (rand.nextInt(6)+1);
      }
    	return resultat_dee;
    }

    public boolean estMort()
    {
  		if(this.vie <= 0)
      {
  			return true;
  		}
  		else
      {
  			return false;
  		}
  	}

}
