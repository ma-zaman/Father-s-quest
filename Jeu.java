import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class Jeu{
  private ArrayList<PNJ> ennemies;
  private ArrayList<PJ> joueurs;
  private ArrayList<Integer> t;
  private int level;
  private Map m;
  private int taille;
  private char mode;
  private int nbJoueurs;
  private Double avancement;

  public Jeu(char c)
  {
    this.level = 1;
    this.mode = c;
    this.t = new ArrayList<Integer>();
    this.ennemies = new ArrayList<PNJ>();
    this.joueurs = new ArrayList<PJ>();
    System.out.print("\033[H\033[2J");
    if(this.mode == 'a')
    {
      System.out.println("Bonjour Aventurier,\n");
      System.out.println("Votre histoire commence ici. Nous sommes en 436E4 (436 ème année de la 4ème ère).");
      System.out.println("Vous êtes le fils ainé du roi de la province de Fox Hound, votre père est le héros de cette province et il vous remet une quête de la plus haute importance.");
      System.out.println("Il vous demande d'entrer dans un ancien avant-poste de l'alliance, d'éléminer tout les monstres qui l'occupe et de récupérer l'épée des lames, une épée légendaire qui peut térrasser un dragon en un seul coup.\n");

      this.genererMap();
    }

    else if(this.mode == 'f')
    {
      System.out.println("Bonjour Aventurier,\n");
      System.out.println("Votre histoire commence ici et elle se finira ici.");
      System.out.println("Des monstres ont réussi à tuer votre père le roi de la province de Fox hound, maintenant cette province s'appelle Xof et elle est dirigée par Skull face le mage noir.");
      System.out.println("Il vous a lancé un sort grâce à sa maîtrise de la magie noire, vous condamnant à vous battre contre des monstres jusqu'à votre mort.\n");

    }

    else
    {
      System.out.println("Bonjour Aventurier,\n");

      this.charger();

      jeu('c');
    }

    System.out.println("Vous pouvez demander de l'aide à votre frère si vous le souhaitez.");
    System.out.print("Voulez-vous vous de l'aide (o = oui/ n = non) : ");
    Scanner sc = new Scanner(System.in);
    char rep;
    do {
      rep = sc.next().charAt(0);
      if(rep != 'o' && rep != 'n')
      {
        System.out.print("Entrez o pour oui et n pour non : ");
      }
    } while (rep != 'o' && rep != 'n');

    if(rep == 'o')
    {
      this.nbJoueurs = 2;
    }
    else
    {
      this.nbJoueurs = 1;
    }

    this.jeu('a');
  }

  public void menu()
  {
    System.out.print("\033[H\033[2J");
    System.out.println("Menu\n");
    System.out.println("Reprendre la partie (r)");
    System.out.println("Sauvegarder (s)");
    System.out.println("Charger (c)");
    System.out.println("Quitter (q)\n");
    Scanner sc = new Scanner(System.in);
    char rep;
    do {
      System.out.print("Votre choix : ");
      rep = sc.next().charAt(0);
    } while (rep != 'r' && rep != 's' && rep != 'c' && rep != 'q');
    if(rep == 's')
    {
      String fichier;
      System.out.print("Entrer le nom de votre sauvegarde : ");
      fichier = sc.next();
      System.out.println(fichier);
      this.sauver(fichier);
    }
    else if(rep == 'c')
    {
      this.charger();
    }
    else if(rep == 'q')
    {
      System.exit(0);
    }
  }

  public int nbrElements(String fichier)
  {
    int i =0;
    try
    {
      Scanner a = new Scanner(new File("Server/Save/"+fichier+".txt"));
      while(a.hasNextInt())
      {
        a.nextInt();
        i++;
      }
    }catch (Exception e) {
      System.out.println("erreur lors du chargement !!!!");
    }
    return i;
  }

 public String avancementCharge(int a)
 {
   System.out.print("\033[H\033[2J");
   String str = "";
   this.avancement += (100.0/a);
   for(int i=0; i<this.avancement/(100.0/a); i++)
   {
     str += "#";
   }
   return(str + " ("+Math.floor(this.avancement)+" %)");
 }

  public void charger()
  {
    this.ennemies.clear();
    this.joueurs.clear();
    try
    {
      System.out.println("Nous avons trouvez ces parchemins, choisissez en un : \n");
      File fic = new File("./Server/Save/");

      File[] files = fic.listFiles();

      for (File file : files) {
          String che = file.getPath();
          String fi = che.substring(14, che.length()-4);
          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
          System.out.println(fi+" : "+sdf.format(file.lastModified()));
        }
      System.out.println("\n(Faites attention au majuscule !!!!!)");
      Scanner sc = new Scanner(System.in);
      String fichier = sc.next();
      System.out.println(fichier);

      int nb = nbrElements(fichier);
      this.avancement = 0.0;
      Scanner f = new Scanner(new File("Server/Save/"+fichier+".txt"));
      this.mode = ((char)f.nextInt());
      System.out.println(this.avancementCharge(nb));
      this.level = f.nextInt();
      System.out.println(this.avancementCharge(nb));
      this.taille = f.nextInt();
      System.out.println(this.avancementCharge(nb));
      t.clear();

      for (int i = 0; i<this.taille*this.taille; i++)
      {
        t.add(f.nextInt());
        System.out.println(this.avancementCharge(nb));
      }

      this.nbJoueurs = f.nextInt();
      System.out.println(this.avancementCharge(nb));
      for (int i = 0; i<this.nbJoueurs; i++)
      {
        int longNom = f.nextInt();
        System.out.println(this.avancementCharge(nb));
        String nom = "";
        for(int j = 0; j<longNom ; j++)
        {
          nom += ((char)f.nextInt());
          System.out.println(this.avancementCharge(nb));
        }
        PJ p = new PJ();
        p.setNom(nom);
        p.setLvl(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setForce(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setAdresse(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setResistance(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setXp(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setVie(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setPosX(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setPosY(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setPa(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        p.setArme(new Arme(f.nextInt(), f.nextInt()));
        System.out.println(this.avancementCharge(nb));
        p.setArmure(new Armure(f.nextInt(), f.nextInt()));
        System.out.println(this.avancementCharge(nb));
        p.setSac(new Inventaire());
        System.out.println(this.avancementCharge(nb));
        int nombre_elements = f.nextInt();
        System.out.println(this.avancementCharge(nb));
        for (int j = 0; j<nombre_elements; j++)
        {
          //System.out.println("j = "+j+"\nnb = "+p.getSac().getNb_Equipement());
          int equipement = f.nextInt();
          System.out.println(this.avancementCharge(nb));
          if(equipement == 0)
          {
            p.getSac().AddElementBP(new Arme(f.nextInt(), f.nextInt()));
            System.out.println(this.avancementCharge(nb));
          }
          else if(equipement == 1)
          {
            p.getSac().AddElementBP(new Armure(f.nextInt(), f.nextInt()));
            System.out.println(this.avancementCharge(nb));
          }
          else if(equipement == 2)
          {
            p.getSac().AddElementBP(new Potion_Degat(f.nextInt()));
            System.out.println(this.avancementCharge(nb));
          }
          else if(equipement == 3)
          {
            p.getSac().AddElementBP(new Potion_Soin(f.nextInt()));
            System.out.println(this.avancementCharge(nb));
          }
        }
        this.joueurs.add(p);
      }
      if(this.nbJoueurs == 1)
      {
        this.joueurs.add(new PJ());
      }
      Map.Nb_Enemies = f.nextInt();
      System.out.println(this.avancementCharge(nb));

      for(int i = 0; i<Map.Nb_Enemies; i++)
      {
        PNJ pnj = new PNJ();
        pnj.setNom("Monstre");
        pnj.setLvl(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setForce(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setAdresse(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setResistance(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setXp(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setVie(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setPosX(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setPosY(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setTypeMonstre(f.nextInt());
        System.out.println(this.avancementCharge(nb));
        pnj.setLoot(new Arme(f.nextInt(), f.nextInt()));
        System.out.println(this.avancementCharge(nb));

        this.ennemies.add(pnj);
        System.out.println(this.avancementCharge(nb));
      }
      System.out.println(this.avancementCharge(nb));
      System.out.println(this.avancementCharge(nb));
      System.out.println("chargement éffectué");
    }catch (Exception e) {
      System.out.println("erreur lors du chargement !!!!");
      charger();
    }
    System.out.println("(Entrer)");
    Scanner sc = new Scanner(System.in);
    sc.nextLine();

    this.m = new Map(t, level, taille, ennemies, joueurs);
  }

  public void sauver(String fichier)
  {
    System.out.println("Sauvegarde en cours : \n");
    try
    {
      PrintWriter writer = new PrintWriter("Server/Save/"+fichier+".txt", "UTF-8");
      writer.println(((int)this.mode));
      writer.println(this.level);
      writer.println(this.taille);
      for (int i = 0; i<this.taille; i++)
      {
        for (int j = 0; j<this.taille; j++)
        {
          if(this.m.get_Case_Map(i,j) instanceof Mur)
          {

            writer.print("1 ");
          }
          else if(this.m.get_Case_Map(i,j) instanceof PNJ)
          {

            writer.print("2 ");
          }
          else if(this.m.get_Case_Map(i,j) instanceof Coffre)
          {

            writer.print("3 ");
          }
          else
          {

            writer.print("0 ");
          }
        }

        writer.println();
      }
      writer.println(this.nbJoueurs);
      for(int i = 0; i<this.nbJoueurs; i++)
      {

        writer.println(this.joueurs.get(i).getNom().length());
        for (int lettre = 0; lettre<this.joueurs.get(i).getNom().length() ; lettre++)
        {

          writer.print(((int)this.joueurs.get(i).getNom().charAt(lettre))+" ");
        }

        writer.println();

        writer.println(this.joueurs.get(i).getLvl());

        writer.println(this.joueurs.get(i).getForce());

        writer.println(this.joueurs.get(i).getAdresse());

        writer.println(this.joueurs.get(i).getResistance());

        writer.println(this.joueurs.get(i).getXp());

        writer.println(this.joueurs.get(i).getVie());

        writer.println(this.joueurs.get(i).getPosX());

        writer.println(this.joueurs.get(i).getPosY());

        writer.println(this.joueurs.get(i).getPa());

        writer.println(this.joueurs.get(i).getArme().getImpact());

        writer.println(this.joueurs.get(i).getArme().getManiabilite());

        writer.println(this.joueurs.get(i).getArmure().getEncombrement());

        writer.println(this.joueurs.get(i).getArmure().getSolidite());

        writer.println(this.joueurs.get(i).getSac().getNb_Equipement());
        for(int j=0; j<this.joueurs.get(i).getSac().getNb_Equipement(); j++)
        {
          if(this.joueurs.get(i).getSac().getObjet(j) instanceof Arme)
          {

            writer.println(0);

            writer.println(((Arme)(this.joueurs.get(i).getSac().getObjet(j))).getImpact());

            writer.println(((Arme)(this.joueurs.get(i).getSac().getObjet(j))).getManiabilite());
          }
          else if(this.joueurs.get(i).getSac().getObjet(j) instanceof Armure)
          {

            writer.println(1);

            writer.println(((Armure)(this.joueurs.get(i).getSac().getObjet(j))).getEncombrement());

            writer.println(((Armure)(this.joueurs.get(i).getSac().getObjet(j))).getSolidite());
          }
          else if(this.joueurs.get(i).getSac().getObjet(j) instanceof Potion_Degat)
          {

            writer.println(2);

            writer.println(((Potion_Degat)(this.joueurs.get(i).getSac().getObjet(j))).getPuissance());
          }
          else if(this.joueurs.get(i).getSac().getObjet(j) instanceof Potion_Soin)
          {

            writer.println(3);

            writer.println(((Potion_Soin)(this.joueurs.get(i).getSac().getObjet(j))).getPuissance());
          }
        }
      }
      writer.println(Map.Nb_Enemies);
      for(int i = 0; i<Map.Nb_Enemies; i++)
      {

        writer.println(this.ennemies.get(i).getLvl());

        writer.println(this.ennemies.get(i).getForce());

        writer.println(this.ennemies.get(i).getAdresse());

        writer.println(this.ennemies.get(i).getResistance());

        writer.println(this.ennemies.get(i).getXp());

        writer.println(this.ennemies.get(i).getVie());

        writer.println(this.ennemies.get(i).getPosX());

        writer.println(this.ennemies.get(i).getPosY());

        writer.println(this.ennemies.get(i).getTypeMonstre());

        writer.println(((Arme)this.ennemies.get(i).getLoot()).getImpact());

        writer.println(((Arme)this.ennemies.get(i).getLoot()).getManiabilite());
      }
      System.out.print("# (100%)\n");
      writer.close();
      System.out.println("Sauvegarde effectué ");
    }catch (Exception e) {
      System.out.println("erreur lors de la sauvegarde");
      //System.err.println(e);
    }
    System.out.println("(Entrer)");
    Scanner sc = new Scanner(System.in);
    sc.nextLine();

  }

  public void genererMap()
  {
    try
    {
      Scanner scanner = new Scanner(new File("Server/Levels/Level_"+level+".txt"));
      this.taille = scanner.nextInt();
      t.clear();
      while(scanner.hasNextInt())
      {
        t.add(scanner.nextInt());
      }

    }catch (Exception e) {
      System.err.println(e);
    }
    this.m = new Map(t, level, taille);

  }

  public void creationPJ()
  {
    for (int i = 0; i<this.nbJoueurs; i++)
    {
      this.m.set_Case_Map(1, i+1, new PJ(i+1, 1));
      this.joueurs.add((PJ)this.m.get_Case_Map(1, i+1));
    }
    if(this.nbJoueurs == 1)
    {
      this.joueurs.add(new PJ());
    }
  }

  public void initailisePJ()
  {
    for (int i = 0; i<this.nbJoueurs; i++)
    {
      this.m.set_Case_Map(1, i+1, this.joueurs.get(i));
      this.joueurs.get(i).setPosX(i+1);
      this.joueurs.get(i).setPosY(1);
    }
  }

  public void initailisePNJ()
  {
    Random rand = new Random();
    for (int i = 0; i<this.taille; i++)
    {
      for (int j = 0; j<this.taille; j++)
      {
        if(this.m.get_Case_Map(i, j) instanceof PNJ)
        {
          if (this.mode == 'a' && this.level == 5)
          {
            this.m.set_Case_Map(i, j, new PNJ("Alduin", 50, 50, 50, j, i, 0, rand.nextInt(this.joueurs.get(0).getLvl()+this.joueurs.get(1).getLvl())+1, 20, 20, 20, new Arme(99, 99)));
          }
          else
          {
            this.m.set_Case_Map(i, j, new PNJ("Monstre", rand.nextInt(9)+1, rand.nextInt(9)+1, rand.nextInt(9)+1, j, i, 0, rand.nextInt(this.joueurs.get(0).getLvl()+this.joueurs.get(1).getLvl())+1, rand.nextInt(9)+1, rand.nextInt(9)+1, rand.nextInt(9)+1, new Arme(rand.nextInt(this.level)+1, rand.nextInt(this.level)+1)));
          }
          this.ennemies.add((PNJ)this.m.get_Case_Map(i, j));
        }
      }
    }
  }

  public void jeu(char c)
  {
    boolean flag = true;
    while(flag || !this.joueurs.get(0).estMort() || !this.joueurs.get(1).estMort())
    {
      if(this.mode == 'a' && c != 'c')
      {
        if(this.level == 6)
        {
          System.out.println("Bravo aventurier,\n");
          System.out.println("Vous avez liberer l'avant-poste et vous avez l'epee des lames.");
          System.out.println("Grace à vous la province de Fox Hound peut vivre en paix");
          System.exit(0);
        }
        this.genererMap();
      }
      else if(this.mode == 'f' && c != 'c')
      {
        this.m = new Map(this.level);
        this.taille = this.level*5;
        if(this.taille > 50)
        {
          this.taille = 50;
        }
      }
      if(flag && c != 'c')
      {
        this.creationPJ();
        flag = false;
      }
      else
      {
        flag = false;
      }
      if(c != 'c')
      {
        this.initailisePJ();
        this.initailisePNJ();
      }

      while(Map.Nb_Enemies > 0)
      {
        c = 'a';
        if(this.joueurs.get(0).estMort() && this.joueurs.get(1).estMort())
        {
          System.out.println("¡¡¡¡¡ GAME OVER !!!!!");
          Map.Nb_Enemies = -1;
        }
        else
        {
          for (int i = 0; i<2; i++)
          {
            if(!this.joueurs.get(i).estMort())
            {
              if(this.m.mouvementPJ(this.joueurs, i))
              {
                this.menu();
              }
              this.joueurs.get(i).setPa(3);
            }
          }
        }
        for (int i = 0; i<Map.Nb_Enemies; i++)
        {
          if(this.ennemies.get(i).estMort())
          {
            this.ennemies.remove(i);
            Map.Nb_Enemies-=1;
            i--;
          }
          if(i>=0)
          {
            Random rand = new Random();
            int alea = rand.nextInt(2);
            if(!this.joueurs.get(alea).estMort())
            {
              this.m.mouvementPNJ(this.ennemies.get(i), this.joueurs, alea);
            }
            else
            {
              if(alea == 1)
              {
                this.m.mouvementPNJ(this.ennemies.get(i), this.joueurs, 0);
              }
              else
              {
                this.m.mouvementPNJ(this.ennemies.get(i), this.joueurs, 1);
              }
            }
          }
        }
      }
      this.level++;
    }
  }
}
