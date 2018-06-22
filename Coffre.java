import java.util.Random;
import java.util.Scanner;

public class Coffre {

  private Inventaire coffre;

  public Coffre() {
    Random rand = new Random();

    int nbEquipement = rand.nextInt(3)+1;

    coffre = new Inventaire(nbEquipement);

    int  n;

    for (int i = 0; i<nbEquipement; i++) {
      n = rand.nextInt(4);
      if(n == 0)
      {
        int  Solidite = rand.nextInt(11);
        int  Encombrement = rand.nextInt(11+(Solidite/2));
        coffre.AddElementBP(new Armure(Encombrement, Solidite));
      }

      else if(n == 1)
      {
        int Impact = rand.nextInt(11);
        int Maniabilite = rand.nextInt(11-(Impact/2));
        coffre.AddElementBP(new Arme(Impact, Maniabilite));
      }

      else if(n == 2)
      {
        int  Degats = rand.nextInt(11);
        coffre.AddElementBP(new Potion_Degat(Degats));
      }

      else
      {
        int  Soin = rand.nextInt(11);
        coffre.AddElementBP(new Potion_Soin(Soin));
      }

    }
  }


  public boolean getLoot(Personnage p1)
  {
    PJ p = (PJ)p1;
    Scanner sc = new Scanner(System.in);
    int indice = 1;
    while(this.coffre.getNb_Equipement() > 0 && indice != 0)
    {
      System.out.print("\033[H\033[2J");
      System.out.println(p);
      System.out.println(p.getSac());
      System.out.println(this.coffre.toStringC());
      do {
        System.out.print("Entrer le numéro de l'equipement que vous voulez selectionner ou 0 pour quitter le coffre : ");
        indice = sc.nextInt();
      } while (indice < 0 || indice > this.coffre.getNb_Equipement());

      if(indice != 0)
      {
        char action;

        do {
          System.out.print("Voulez-vous prendre cet equipement (p = prendre, e = equiper, n = ne rien faire): ");
          action = sc.next().charAt(0);
        } while (action != 'n' && action != 'p' && action != 'e');

        if(action == 'e')
        {
          if(this.coffre.getObjet(indice-1) instanceof Potion_Soin)
          {
            p.utiliserPotionSoin((Potion_Soin)this.coffre.getObjet(indice-1));
            this.coffre.jeterObjet(indice-1);
          }
          else if(this.coffre.getObjet(indice-1) instanceof Potion_Degat)
          {
            System.out.println("Vous ne pouvez pas utiliser une potion de degat ici");
          }
          else if(this.coffre.getObjet(indice-1) instanceof Arme)
          {
            Arme a = p.getArme();
            p.setArme((Arme)this.coffre.getObjet(indice-1));
            this.coffre.jeterObjet(indice-1);
            this.coffre.AddElementBP(a);
          }
          else
          {
            Armure a = p.getArmure();
            p.setArmure((Armure)this.coffre.getObjet(indice-1));
            this.coffre.jeterObjet(indice-1);
            this.coffre.AddElementBP(a);
          }
        }
        else if(action == 'p')
        {
          if(this.coffre.getObjet(indice-1) instanceof Potion_Soin)
          {
            p.getSac().AddElementBP((Potion_Soin)this.coffre.getObjet(indice-1));
            this.coffre.jeterObjet(indice-1);
          }
          else if(this.coffre.getObjet(indice-1) instanceof Potion_Degat)
          {
            p.getSac().AddElementBP((Potion_Degat)this.coffre.getObjet(indice-1));
            this.coffre.jeterObjet(indice-1);
          }
          else if(this.coffre.getObjet(indice-1) instanceof Arme)
          {
            p.getSac().AddElementBP((Arme)this.coffre.getObjet(indice-1));
            this.coffre.jeterObjet(indice-1);
          }
          else
          {
            p.getSac().AddElementBP((Armure)this.coffre.getObjet(indice-1));
            this.coffre.jeterObjet(indice-1);
          }
        }
      }
    }
    if(this.coffre.getNb_Equipement() == 0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

}
