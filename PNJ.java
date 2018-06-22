import java.util.Scanner;

public class PNJ extends Personnage{
	private int typeMonstre;
	private Equipement loot;

	public PNJ(){}

	public PNJ(String nom, int vie, int xp, int viemax,int posX, int posY, int typeMonstre, int nivDifficulte, int f, int a, int r, Equipement loot){
			super(nom, vie, xp, posX, posY, f, a, r);
			this.setTypeMonstre(typeMonstre);
			this.setLvl(nivDifficulte);
			this.setLoot(loot);
	}

	public int getTypeMonstre(){
		return this.typeMonstre;
	}

	public void setTypeMonstre(int typeMonstre){
		this.typeMonstre = typeMonstre;
	}

	public Equipement getLoot(){
		return this.loot;
	}

	public void setLoot(Equipement loot){
		this.loot= loot;
	}

	public int getEsquive()
  {
    if(this.getAdresse()-6 < 0)
    {
      return 0;
    }
    else
    {
      return(this.getAdresse()-6+this.lancerDee());
    }
  }

  public int getDefense()
  {
  	return(this.getResistance()+this.lancerDee());
  }

  public int getInitiative()
  {
    if(this.getResistance()-6 < 0)
    {
      return 0;
    }
    else
    {
      return(this.getResistance()-6+this.lancerDee());
    }
  }

  public int getAttaque()
  {
    return(this.getAdresse()+this.lancerDee());
  }

  public int getDegats()
  {
    return(this.getForce()+this.lancerDee());
  }

	public boolean combattre(PJ p)
	{
		int deg = this.getDegats();
		int def = p.getDefense();
		System.out.println("Points de degats du Monstre: "+deg);
    System.out.println("Vos points de defense : "+def+"\n");
		if(deg > def)
		{
			p.setVie(p.getVie()-(deg-def));
			System.out.println(this.getNom()+" inflige "+(deg-def)+" points de dégats à "+ p.getNom());
			System.out.println("Points de vie de "+this.getNom()+" : "+this.getVie());
			System.out.println("Points de vie de "+p.getNom()+" : "+p.getVie() + "\n(Entrer)");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			return true;
		}
		else
		{
			System.out.println(this.getNom()+" inflige 0 points de dégats à "+ p.getNom());
			System.out.println("Points de vie de "+this.getNom()+" : "+this.getVie());
			System.out.println("Points de vie de "+p.getNom()+" : "+p.getVie() + "\n(Entrer)");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
		}
		return false;
	}

	public boolean attaquer(PJ p)
	{
		if(this.getAttaque() > p.getEsquive())
		{
			System.out.println(this.getNom()+" a réussi son attaque contre "+p.getNom());
			if(this.combattre(p))
			{
				return true;
			}

		}
		return false;
	}




	public void lacherloot(Object[][] map){
		if(this.estMort()){
			map[this.getPosX()][this.getPosY()] = this. getLoot();
		}
	}
}
