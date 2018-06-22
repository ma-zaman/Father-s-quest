import java.util.Scanner;



public class PJ extends Personnage {

  private int pa;
  private int pamax;
  private char classe;
  private int xpmax;
  private Inventaire sac;
  private Armure armure_P;
  private Arme arme_P;



  public PJ()
  {
    super();
  	this.setLvl(0);
  	this.setPa(0);
  	this.setPamax(0);
  	this.setClasse('0');
  	this.setXp(0);
    this.setVie(0);
  }

  public PJ(int x, int y)
  {
    super(x, y);
    this.selectionClasse();
  	this.setLvl(1);
  	this.setPa(3);
  	this.setPamax(3);
  	this.setXp(0);
    this.sac = new Inventaire();
  }

  public PJ(String nom, int vie, int viemax,int posX, int posY, int f, int a, int r, int lvl, int pa, int pamax, char classe, int xp, int xpmax){
  	super(nom, vie, xp, posX, posY, f, a, r);
  	this.setForce(f);
  	this.setAdresse(a);
  	this.setResistance(r);
  	this.setLvl(lvl);
  	this.setPa(pa);
  	this.setPamax(pamax);
  	this.setClasse(classe);
  	this.setXp(xp);
  	this.setXpmax(xpmax);

  }

  public void selectionClasse()
  {
    System.out.println("Avant de partir en mission, vous devez choisir votre équipement : ");
    System.out.println("Choisissez parmis les classes suivantes celle qui vous correspond le mieux.\n");
    System.out.println("    - Voleur (v) :");
    System.out.println("        - Équipement de départ :");
    System.out.println("            - Cimeterre de voleur (impact = 2, maniabilité = 6)");
    System.out.println("            - Armure de voleur (encombrement = 0, solidité = 2)\n");

    System.out.println("    - Berserker (b) :");
    System.out.println("        - Équipement de départ :");
    System.out.println("            - Hallebarde de berserker (impact = 7, maniabilité = 1)");
    System.out.println("            - Armure de berserker (encombrement = 6, solidité = 8)\n");

    System.out.println("    - Guerrier (g) :");
    System.out.println("        - Équipement de départ :");
    System.out.println("            - Épée de guerrier (impact = 4, maniabilité = 4)");
    System.out.println("            - Armure de guerrier (encombrement = 3, solidité = 5)\n");

    Scanner sc = new Scanner(System.in);
    char rep;

    do {
      System.out.print("Choisir une classe(v/ b/ g) : ");
      this.classe = sc.next().charAt(0);
    } while (this.classe != 'v' && this.classe != 'b' && this.classe != 'g');

    if(this.classe == 'v')
    {
      this.setArme(new Arme(2, 6));
      this.setArmure(new Armure(0, 2));
    }

    else if(this.classe == 'b')
    {
      this.setArme(new Arme(7, 1));
      this.setArmure(new Armure(6, 8));
    }

    else
    {
      this.setArme(new Arme(4, 4));
      this.setArmure(new Armure(3, 5));
    }
  }

  public void setArme(Arme a)
  {
    this.arme_P = a;
  }

  public Arme getArme()
  {
    return this.arme_P;
  }

  public void setArmure(Armure a)
  {
    this.armure_P = a;
  }

  public Armure getArmure()
  {
    return this.armure_P;
  }

  public void setXpmax(int xpMax)
  {
    this.xpmax = xpMax;
  }

  public int getXpmax(){
  	return this.xpmax;
  }

  public int getPa(){
  	return this.pa;
  }

  public void setPa(int pa){
  	this.pa = pa;
  }

  public int getPamax(){
  	return this.pamax;
  }

  public void setPamax(int pamax){
  	this.pamax = pamax;
  }

  public char getClasse(){
  	return this.classe;
  }

  public void setClasse(char classe){
  	this.classe = classe;
  }

  public int getEsquive()
  {
    if(this.getAdresse()-this.armure_P.getEncombrement() < 0)
    {
      return 0;
    }
    else
    {
      return(this.getAdresse()-this.armure_P.getEncombrement()+this.lancerDee());
    }
  }

  public int getDefense()
  {
  	return(this.getResistance()+this.armure_P.getSolidite()+this.lancerDee());
  }

  public int getInitiative()
  {
    if(this.getResistance()-this.armure_P.getEncombrement() < 0)
    {
      return 0;
    }
    else
    {
      return(this.getResistance()-this.armure_P.getEncombrement()+this.lancerDee());
    }
  }

  public int getAttaque()
  {
    return(this.getAdresse()+this.arme_P.getManiabilite()+this.lancerDee());
  }

  public int getDegats()
  {
    return(this.getForce()+this.arme_P.getImpact()+this.lancerDee());
  }

  public boolean combattre(PNJ p)
  {
    boolean flag = false;
    int deg = this.getDegats();
    int def = p.getDefense();
    System.out.println("Vos points de degats : "+deg);
    System.out.println("Points de defense de votre adversaire : "+def+"\n");
    if(deg > def)
    {
      p.setVie(p.getVie()-(deg-def));
			System.out.println(this.getNom()+" inflige "+(deg-def)+" points de dégats à "+ p.getNom());
			System.out.println("Points de vie de "+this.getNom()+" : "+this.getVie());
			System.out.println("Points de vie de "+p.getNom()+" : "+p.getVie());
			flag = true;
    }
    else
    {
      p.setResistance(p.getResistance()-(this.getForce()/2));
    }
    System.out.println("(Entrer)");
    Scanner sc = new Scanner(System.in);
    sc.nextLine();
    return flag;
  }

  public boolean attaquer(PNJ p)
  {
    int atk = this.getAttaque();
    int esq = p.getEsquive();
    System.out.println("\nVos points d'attaque : "+atk);
    System.out.println("Points d'esquive de votre adversaire : "+esq+"\n");
    if(atk > esq)
    {
      System.out.println(this.getNom()+" a réussi son attaque contre "+p.getNom()+"\n");
      if(this.combattre(p))
      {
        return true;
      }

    }
    return false;
  }

  public void lvlUp()
  {
  		if(this.getXp() >= this.getXpmax())
      {
  			this.setLvl(this.getLvl()+1);
  			this.setXp(this.getXp()-this.getXpmax());
  			this.setXpmax(10*this.getLvl());
        System.out.print("\033[H\033[2J");
        System.out.println("Vous avez gagner un niveau !!!!!!");
        if(this.classe == 'v')
        {
          System.out.println("Ni vu, ni connu");
        }
        else if(this.classe == 'b')
        {
          System.out.println("Plus de sang PLUS !!!!!!");
        }
        else
        {
          System.out.println("Pour l'alliance");
        }
        this.affichageCapacite();
        char c;
        do {
          System.out.println("Améliorer votre force (f), votre adresse (a) ou votre resistance (r) : ");
          Scanner sc = new Scanner(System.in);
          c = sc.next().charAt(0);
        } while (c != 'f' && c != 'a' && c!='r');
        if(c == 'f')
        {
          this.setForce(this.getForce()+1);
        }
        else if(c == 'a')
        {
          this.setAdresse(this.getAdresse()+1);
        }
        else
        {
          this.setResistance(this.getResistance()+1);
        }
  		}
  }
  public boolean prendreEquipement(Equipement e)
  {
    System.out.print(this.sac);
    System.out.println("Voulez-vous prendre cette objet avec vous ?");
    System.out.println(e);

    char c;
    Scanner sc = new Scanner(System.in);
    do
    {
      System.out.print("Entrez o pour oui ou n pour non : ");
      c = sc.next().charAt(0);
    } while (c != 'o' && c != 'n');
    if(c == 'o')
    {
      this.sac.AddElementBP(e);
      System.out.print(this.sac);
      return true;
    }
    else
    {
      return false;
    }
  }

  public void utiliserPotionSoin(Potion_Soin p)
  {
    this.setVie(this.getVie() + p.getPuissance());
  }

  public Inventaire getSac()
  {
    return this.sac;
  }

  public String affichageDiez(int nb)
  {
    String s = "";
    for (int i = 0; i<nb; i++)
    {
      s+="#";
    }
    return s;
  }

  public void setSac(Inventaire i)
  {
    this.sac = i;
  }

  public void affichageCapacite()
  {
    int i=0;
    String F="";
    String A="";
    String R="";
    while(i<this.getForce())
    {
        F+="#";
          i++;
    }
    i=0;
    while(i<this.getAdresse())
    {
          A+="#";
          i++;
    }
    i=0;
    while(i<this.getResistance())
    {
          R+="#";
          i++;
    }

    if(this.getForce() != -1)
    {
          System.out.println("Force : "+F+" ("+this.getForce()+")");
    }

    if(this.getAdresse() != -1)
    {
          System.out.println("adresse : "+A+" ("+this.getAdresse()+")");
    }

    if(this.getResistance() != -1)
    {
          System.out.println("Résistance : "+R+" ("+this.getResistance()+")");
    }
  }

  public String toString()
  {
    return (this.getNom()+"\n\nNiveau : "+this.getLvl()+"\n\n"+this.getArme()+this.getArmure()+"Vie : "+affichageDiez(this.getVie())+" ("+this.getVie()+")"+"\n\nXP : "+affichageDiez(this.getXp())+" ("+this.getXp()+")"+"\n");
  }
}
