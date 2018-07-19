 import java.util.Random;
 import java.util.*;
 import java.util.concurrent.TimeUnit;
 import java.util.Scanner;

 public class Map {
    private Object[][] Room;
    private int level;
    private int taille;

    public static int Nb_Enemies;

    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";


    public static final String ANSI_RESET = "\u001B[0m";

    public Map(ArrayList<Integer> t,int lvl, int taille)
    {
      this.taille = taille;
      this.level = lvl;
      this.Nb_Enemies = 0;
      this.Room = new Object[taille][taille];
      for(int i = 0; i<taille; i++)
      {
        for (int j = 0; j<taille; j++)
        {
          if(t.get((i*taille)+j) == 1)
          {
            this.Room[i][j] = new Mur();
          }
          else if (t.get((i*taille)+j) == 0)
          {
            this.Room[i][j] = null;
          }
          else if(t.get((i*taille)+j) == 2)
          {
            this.set_Case_Map(i, j, new PNJ());
            this.Nb_Enemies ++;
          }
          else
          {
            this.Room[i][j] = new Coffre();
          }
        }
      }
    }

    public Map(ArrayList<Integer> t,int lvl, int taille, ArrayList<PNJ> ennemies, ArrayList<PJ> joueurs)
    {
      this.taille = taille;
      this.level = lvl;
      this.Room = new Object[taille][taille];
      for(int i = 0; i<taille; i++)
      {
        for (int j = 0; j<taille; j++)
        {
          if(t.get((i*taille)+j) == 1)
          {
            this.Room[i][j] = new Mur();
          }
          else if (t.get((i*taille)+j) == 0)
          {
            this.Room[i][j] = null;
          }
          else if(t.get((i*taille)+j) == 3)
          {
            this.Room[i][j] = new Coffre();
          }
        }
      }
      for (PNJ pnj : ennemies)
      {
        this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), pnj);
      }
      for (PJ pj : joueurs)
      {
        if(pj.getPosX() >0)
        {
          this.set_Case_Map(pj.getPosY(), pj.getPosX(), pj);
        }
      }
    }

    public Map(int level)
    {
      this.level = level;

      this.taille = this.level*5;

      if(this.taille > 50)
      {
        this.taille = 50;
      }

      this.Room = new Object[this.taille][this.taille];

      this.Nb_Enemies = level;

      Random rand = new Random();

      boolean coffre = false;

      for (int i = 0; i<this.taille ; i++)
      {
        for (int j = 0; j<this.taille; j++)
        {
            if(i == 0 || j == 0 || i == this.taille-1 || j == this.taille-1)
            {
              this.Room[i][j] = new Mur();
            }
            else
            {
              int  n = rand.nextInt(10);

              if(n == 0)
              {
                this.Room[i][j] = new Mur();
              }
              else
              {
                this.Room[i][j] = null;
              }
            }
        }
      }
      for (int i = 0; i<this.Nb_Enemies; i++)
      {
        int n = rand.nextInt(this.taille);
        int m = rand.nextInt(this.taille);
        if(this.estVide(n, m) && !coffre)
        {
          this.Room[n][m] = new Coffre();
          coffre = true;
          i--;
        }
        else if(this.estVide(n, m) && !(this.get_Case_Map(n-1,m) instanceof Mur))
        {
          this.set_Case_Map(n, m, new PNJ());
        }
        else
        {
          i--;
        }
      }
    }

    public boolean estVide(int i, int j)
    {
      return(this.get_Case_Map(i, j) == null);
    }

    public void setNb_Enemies(int value)
    {
        this.Nb_Enemies = value;
    }

    public int getNb_Enemies()
    {
        return this.Nb_Enemies;
    }

  /*  public char[] getRoom()
  {
        return this.Room;
    }
*/
    public void set_Case_Map(int x, int y, Object type)
    {
    	this.Room[x][y] = type;
    }

    public Object get_Case_Map(int x, int y)
    {
      return this.Room[x][y];
    }

    public void testLoot(Personnage p)
    {
      if(this.Room[p.getPosY()-1][p.getPosX()] instanceof Coffre)
      {
        Coffre c = (Coffre) this.Room[p.getPosY()-1][p.getPosX()];
        if(c.getLoot(p))
        {
          this.set_Case_Map(p.getPosY()-1, p.getPosX(), null);
        }
      }
      else if(this.Room[p.getPosY()+1][p.getPosX()] instanceof Coffre)
      {
        Coffre c = (Coffre) this.Room[p.getPosY()+1][p.getPosX()];
        if(c.getLoot(p))
        {
          this.set_Case_Map(p.getPosY()+1, p.getPosX(), null);
        }
      }
      else if(this.Room[p.getPosY()][p.getPosX()-1] instanceof Coffre)
      {
        Coffre c = (Coffre) this.Room[p.getPosY()][p.getPosX()-1];
        if(c.getLoot(p))
        {
          this.set_Case_Map(p.getPosY(), p.getPosX()-1, null);
        }
      }
      else if(this.Room[p.getPosY()][p.getPosX()+1] instanceof Coffre)
      {
        Coffre c = (Coffre) this.Room[p.getPosY()][p.getPosX()+1];
        if(c.getLoot(p))
        {
          this.set_Case_Map(p.getPosY(), p.getPosX()+1, null);
        }
      }
      else if(this.Room[p.getPosY()-1][p.getPosX()] instanceof Equipement)
      {
        Equipement c = (Equipement) this.Room[p.getPosY()-1][p.getPosX()];
        if(this.getLoot(c, (PJ)p))
        {
          this.set_Case_Map(p.getPosY()-1, p.getPosX(), null);
        }
      }
      else if(this.Room[p.getPosY()+1][p.getPosX()] instanceof Equipement)
      {
        Equipement c = (Equipement) this.Room[p.getPosY()+1][p.getPosX()];
        if(this.getLoot(c, (PJ)p))
        {
          this.set_Case_Map(p.getPosY()+1, p.getPosX(), null);
        }
      }
      else if(this.Room[p.getPosY()][p.getPosX()-1] instanceof Equipement)
      {
        Equipement c = (Equipement) this.Room[p.getPosY()][p.getPosX()-1];
        if(this.getLoot(c, (PJ)p))
        {
          this.set_Case_Map(p.getPosY(), p.getPosX()-1, null);
        }
      }
      else if(this.Room[p.getPosY()][p.getPosX()+1] instanceof Equipement)
      {
        Equipement c = (Equipement) this.Room[p.getPosY()][p.getPosX()+1];
        if(this.getLoot(c, (PJ)p))
        {
          this.set_Case_Map(p.getPosY(), p.getPosX()+1, null);
        }
      }
    }

    public boolean getLoot(Equipement e, PJ p)
    {
      boolean flag = false;
      if(e != null)
      {
        flag = p.prendreEquipement(e);
      }
      else
      {
        System.out.println("Aucun objet à proximite");
      }
      return flag;
    }

    public int testCombat(Personnage p)
    {
      if(this.Room[p.getPosY()-1][p.getPosX()] instanceof Personnage)
      {
        return 1;
      }
      else if(this.Room[p.getPosY()+1][p.getPosX()] instanceof Personnage)
      {
        return 2;
      }
      else if(this.Room[p.getPosY()][p.getPosX()-1] instanceof Personnage)
      {
        return 3;
      }
      else if(this.Room[p.getPosY()][p.getPosX()+1] instanceof Personnage)
      {
        return 4;
      }
      else
      {
        return 0;
      }
    }

    public void passeTour(PJ p)
    {
      p.setPa(0);
    }

    public boolean mouvementPJ(ArrayList<PJ> joueurs, int i, char c)
    {
      PJ p = joueurs.get(i);
      while(p.getPa()>0)
      {
        //System.out.print("\033[H\033[2J");
        //System.out.println(ANSI_GREEN_BACKGROUND + ANSI_BLUE + "Tour de " + p.getNom()+ANSI_RESET);
        this.Afficher();
        //System.out.println(p);
        //char c = getValAction();
        p.setPa(p.getPa()-1);
        if(c == 'M')
        {
          return true;
        }
        if(c == 'A')
        {
          PNJ p1;
          int posE = this.testCombat(p);
          if(posE != 0)
          {
            boolean atkR;
            if(posE == 1)
            {
              p1 = (PNJ)this.Room[p.getPosY()-1][p.getPosX()];
            }
            else if(posE == 2)
            {
              p1 = (PNJ)this.Room[p.getPosY()+1][p.getPosX()];
            }
            else if(posE == 3)
            {
              p1 = (PNJ)this.Room[p.getPosY()][p.getPosX()-1];
            }
            else
            {
              p1 = (PNJ)this.Room[p.getPosY()][p.getPosX()+1];
            }
            atkR = p.attaquer(p1);
            this.clearCase(atkR, p, p1);
          }
          else
          {
            System.out.println("Aucun ennemi à proximite !!!!");
            p.setPa(p.getPa()+1);
          }
        }

        else if (c == 'W')
        {
          testLoot(p);
        }

        else if(c == 'I')
        {
          System.out.println(p.getSac());
          Scanner sc = new Scanner(System.in);
          if(p.getSac().getNb_Equipement() > 0)
          {
            int indice;
            do {
              System.out.print("Entrer le numéro de l'equipement que vous voulez selectionner ou 0 pour quitter le menu d'inventaire : ");
              indice = sc.nextInt();
            } while (indice < 0 || indice > p.getSac().getNb_Equipement());

            if(indice != 0)
            {
              char action;

              do {
                System.out.print("Que voulez vous faire avec (j = Jeter, e = Equiper) : ");
                action = sc.next().charAt(0);
              } while (action != 'j' && action != 'e');

              if(action == 'j')
              {
                p.getSac().jeterObjet(indice-1);
              }
              else
              {
                if(p.getSac().getObjet(indice-1) instanceof Potion_Soin)
                {
                  p.utiliserPotionSoin((Potion_Soin)p.getSac().getObjet(indice-1));
                  p.getSac().jeterObjet(indice-1);
                }
                else if(p.getSac().getObjet(indice-1) instanceof Potion_Degat)
                {
                  this.explosion(p, (Potion_Degat)p.getSac().getObjet(indice-1));
                  p.getSac().jeterObjet(indice-1);
                }
                else if(p.getSac().getObjet(indice-1) instanceof Arme)
                {
                  Arme a = p.getArme();
                  p.setArme((Arme)p.getSac().getObjet(indice-1));
                  p.getSac().jeterObjet(indice-1);
                  p.getSac().AddElementBP(a);
                }
                else
                {
                  Armure a = p.getArmure();
                  p.setArmure((Armure)p.getSac().getObjet(indice-1));
                  p.getSac().jeterObjet(indice-1);
                  p.getSac().AddElementBP(a);
                }
              }
            }
          }
          else
          {
            System.out.println("Votre inventaire est vide \n(Entrer)");
            sc.nextLine();
          }
          p.setPa(p.getPa()+1);
        }

        else if(c == 'P')
        {
          this.passeTour(p);
        }

        else if(c == 'Z' && this.Room[p.getPosY()-1][p.getPosX()] == null)
        {
          this.set_Case_Map(p.getPosY()-1, p.getPosX(), p);
          this.set_Case_Map(p.getPosY(), p.getPosX(), null);
          p.setPosY(p.getPosY()-1);
        }

        else if(c == 'Q' && this.Room[p.getPosY()][p.getPosX()-1] == null)
        {
          this.set_Case_Map(p.getPosY(), p.getPosX()-1, p);
          this.set_Case_Map(p.getPosY(), p.getPosX(), null);
          p.setPosX(p.getPosX()-1);
        }

        else if(c == 'S' && this.Room[p.getPosY()+1][p.getPosX()] == null)
        {
          this.set_Case_Map(p.getPosY()+1, p.getPosX(), p);
          this.set_Case_Map(p.getPosY(), p.getPosX(), null);
          p.setPosY(p.getPosY()+1);
        }

        else if(c == 'D' && this.Room[p.getPosY()][p.getPosX()+1] == null)
        {
          this.set_Case_Map(p.getPosY(), p.getPosX()+1, p);
          this.set_Case_Map(p.getPosY(), p.getPosX(), null);
          p.setPosX(p.getPosX()+1);
        }
        else
        {
          p.setPa(p.getPa()+1);
        }
      }
      return false;
    }

    public void clearCase(boolean flag, Personnage p, Personnage p1)
    {
      if(flag && p1.estMort())
      {
        p.setXp(p.getXp()+p1.getXp());
        ((PJ)p).lvlUp();
        this.set_Case_Map(p1.getPosY(), p1.getPosX(), ((PNJ)p1).getLoot());
      }
    }

    public void explosion(Personnage perso, Potion_Degat potion)
    {
      Personnage p;
      if(this.Room[perso.getPosY()-1][perso.getPosX()] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()-1][perso.getPosX()];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
      if(this.Room[perso.getPosY()+1][perso.getPosX()] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()+1][perso.getPosX()];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
      if(this.Room[perso.getPosY()][perso.getPosX()-1] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()][perso.getPosX()-1];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
      if(this.Room[perso.getPosY()][perso.getPosX()+1] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()][perso.getPosX()+1];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
      if(this.Room[perso.getPosY()-1][perso.getPosX()-1] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()-1][perso.getPosX()-1];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
      if(this.Room[perso.getPosY()+1][perso.getPosX()+1] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()+1][perso.getPosX()+1];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
      if(this.Room[perso.getPosY()-1][perso.getPosX()+1] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()-1][perso.getPosX()+1];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
      if(this.Room[perso.getPosY()+1][perso.getPosX()-1] instanceof Personnage)
      {
        p = (Personnage)this.Room[perso.getPosY()+1][perso.getPosX()-1];
        p.setVie(p.getVie()-potion.getPuissance());
        this.clearCase(true, perso, p);
      }
    }

    public char getValAction()
    {
      char c;
      Scanner sc = new Scanner(System.in);
      do
      {
        System.out.print("Entrer un valeur d'action(z = ↑, q = ←, s = ↓, d = →, a = Attaquer, i = Voir inventaire, p = Passer votre tour, w = interagir avec un objet/ coffre, m = menu) :  ");
        c = sc.next().charAt(0);
      } while (c != 'z' && c != 'q' && c != 's' && c != 'd' && c != 'a' && c != 'i' && c != 'p' && c != 'w' && c != 'm');
      return c;
    }

    public void mouvementPNJ(PNJ pnj, ArrayList<PJ> joueurs, int indice)
    {
      PJ pj = joueurs.get(indice);
      int i = 0;
      while(i<3)
      {
        int posPJ = this.testCombat(pj);
        int posPNJ = this.testCombat(pnj);
        if(posPJ != 0 && posPNJ != 0)
        {
          boolean atkR;
          atkR = pnj.attaquer(pj);
          if(atkR && pj.estMort())
          {
            this.set_Case_Map(pj.getPosY(), pj.getPosX(), null);
          }
        }
        else if(pj.getPosX() < pnj.getPosX() && this.Room[pnj.getPosY()][pnj.getPosX()-1] == null)
        {
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX()-1, pnj);
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), null);
          pnj.setPosX(pnj.getPosX()-1);
        }
        else if(pj.getPosY() < pnj.getPosY() && this.Room[pnj.getPosY()-1][pnj.getPosX()] == null)
        {
          this.set_Case_Map(pnj.getPosY()-1, pnj.getPosX(), pnj);
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), null);
          pnj.setPosY(pnj.getPosY()-1);
        }
        else if(pj.getPosX() > pnj.getPosX() && this.Room[pnj.getPosY()][pnj.getPosX()+1] == null)
        {
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX()+1, pnj);
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), null);
          pnj.setPosX(pnj.getPosX()+1);
        }
        else if(this.Room[pnj.getPosY()+1][pnj.getPosX()] == null)
        {
          this.set_Case_Map(pnj.getPosY()+1, pnj.getPosX(), pnj);
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), null);
          pnj.setPosY(pnj.getPosY()+1);
        }
        else if(this.Room[pnj.getPosY()][pnj.getPosX()-1] == null)
        {
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX()-1, pnj);
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), null);
          pnj.setPosX(pnj.getPosX()-1);
        }
        else if(this.Room[pnj.getPosY()][pnj.getPosX()+1] == null)
        {
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX()+1, pnj);
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), null);
          pnj.setPosX(pnj.getPosX()+1);
        }
        else
        {
          this.set_Case_Map(pnj.getPosY()-1, pnj.getPosX(), pnj);
          this.set_Case_Map(pnj.getPosY(), pnj.getPosX(), null);
          pnj.setPosY(pnj.getPosY()-1);
        }

        i++;
        System.out.print("\033[H\033[2J");
        System.out.println(ANSI_GREEN_BACKGROUND + ANSI_BLUE + "Tour des Monstres"+ANSI_RESET);
        System.out.println();
        this.Afficher();
        try
        {
            Thread.sleep(200);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
      }
    }

    public void Afficher()
    {
      int i = 0;
      int j = 0;
      String ligne = new String("");
      for (int espace = 0; espace < ((1.5*this.taille)-5); espace++)
      {
        ligne+=" ";
      }
      System.out.println(ligne+"Level "+this.level+"\n");

      ligne = "";
      for (i=0; i<this.taille; i++)
      {
        for (j=0; j<this.taille; j++)
        {
          if(get_Case_Map(i, j) instanceof Mur)
          {
            ligne+=(ANSI_GREEN_BACKGROUND + " " + ANSI_GREEN + "# ");
          }
          else if(get_Case_Map(i, j) instanceof PJ)
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_BLUE + ((PJ)this.get_Case_Map(i, j)).getNom().charAt(0) + " ");
          }
          else if(get_Case_Map(i, j) instanceof PNJ)
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_RED + "M ");
          }
          else if(get_Case_Map(i, j) instanceof Coffre)
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_PURPLE + "∏ ");
          }
          else if(get_Case_Map(i, j) instanceof Arme)
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "⚔ ");
          }
          else if(get_Case_Map(i, j) instanceof Armure)
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "O ");
          }
          else if(get_Case_Map(i, j) instanceof Potion_Degat)
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "☠ ");
          }
          else if(get_Case_Map(i, j) instanceof Potion_Soin)
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "☤ ");
          }
          else
          {
            ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_YELLOW + "  ");
          }
        }
        System.out.println(ligne + ANSI_RESET);
        //if(j == 1)
        ligne="";
      }
    }

    public void Afficher(ArrayList<PJ> joueurs)
    {
      int i = 0;
      int j = 0;
      String ligne = new String("");
      for (int espace = 0; espace < ((1.5*this.taille)-5); espace++)
      {
        ligne+=" ";
      }
      System.out.println(ligne+"Level "+this.level+"\n");

      ligne = "";
      boolean test = true;
      for (i=0; i<this.taille; i++)
      {
        for (j=0; j<this.taille; j++)
        {
          for (PJ pj : joueurs)
          {
            if(test && i>=pj.getPosY()-2 && i<=pj.getPosY()+2 && j>=pj.getPosX()-2 && j<=pj.getPosX()+2 && (!pj.estMort()))
            {
              if(get_Case_Map(i, j) instanceof Mur)
              {
                test = false;
                ligne+=(ANSI_GREEN_BACKGROUND + " " + ANSI_GREEN + "# " + ANSI_RESET);
              }
              else if(get_Case_Map(i, j) instanceof PJ)
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_BLUE + ((PJ)this.get_Case_Map(i, j)).getNom().charAt(0) + " " + ANSI_RESET);
              }
              else if(get_Case_Map(i, j) instanceof PNJ)
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_RED + "M " + ANSI_RESET);
              }
              else if(get_Case_Map(i, j) instanceof Coffre)
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_PURPLE + "∏ " + ANSI_RESET);
              }
              else if(get_Case_Map(i, j) instanceof Arme)
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "⚔ " + ANSI_RESET);
              }
              else if(get_Case_Map(i, j) instanceof Armure)
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "O " + ANSI_RESET);
              }
              else if(get_Case_Map(i, j) instanceof Potion_Degat)
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "☠ " + ANSI_RESET);
              }
              else if(get_Case_Map(i, j) instanceof Potion_Soin)
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_CYAN + "☤ " + ANSI_RESET);
              }
              else
              {
                test = false;
                ligne+=(ANSI_YELLOW_BACKGROUND + " " + ANSI_YELLOW + "  " + ANSI_RESET);
              }
            }
            else if(!pj.estMort() && test)
            {
              ligne+="   ";
            }
          }
          test =true;
        }
        System.out.println(ligne + ANSI_RESET);
        //if(j == 1)
        ligne="";
      }
    }

}
