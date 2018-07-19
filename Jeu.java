import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;

public class Jeu extends JFrame {
  private ArrayList<PNJ> ennemies;
  private ArrayList<PJ> joueurs;
  private ArrayList<Integer> t;
  private int level;
  private Map m;
  private int taille;
  private char mode;
  private int nbJoueurs;
  private Double avancement;
  private int w;
  private int h;
  private JButton butOui;
  private JButton butNon;

  public Jeu(char c, int w, int h)
  {
    super("FATHER'S QUEST");
    this.w = w;
    this.h = h;

    this.setSize(w,h-10);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(null);
    this.setVisible(true);
    this.setBackground(Color.black);
    this.setResizable(false);
    this.level = 1;
    this.mode = c;
    this.t = new ArrayList<Integer>();
    this.ennemies = new ArrayList<PNJ>();
    this.joueurs = new ArrayList<PJ>();

    this.setFocusable(true);

    KListener lis = new KListener();
    this.addKeyListener(lis);

    //System.out.print("\033[H\033[2J");

    if(this.mode == 'a')
    {
      this.add(initialiseLabel("Bonjour Aventurier,", ((this.w)/7), (this.h/8)+0));
      this.add(initialiseLabel("Votre histoire commence ici. Nous sommes en 436E4 (436 ème année de la 4ème ère).", ((this.w)/7), (this.h/8)+100));
      this.add(initialiseLabel("Vous êtes le fils ainé du roi de la province de Fox Hound,", ((this.w)/7), (this.h/8)+150));
      this.add(initialiseLabel("votre père est le héros de cette province et il vous remet une quête de la plus haute importance.", ((this.w)/7), (this.h/8)+200));
      this.add(initialiseLabel("Il vous demande d'entrer dans un ancien avant-poste de l'alliance,", ((this.w)/7), (this.h/8)+250));
      this.add(initialiseLabel("d'éléminer tout les monstres qui l'occupe et de récupérer l'épée des lames,", ((this.w)/7), (this.h/8)+300));
      this.add(initialiseLabel("une épée légendaire qui peut térrasser un dragon en un seul coup.", ((this.w)/7), (this.h/8)+350));



      this.genererMap();
    }

    else if(this.mode == 'f')
    {
      this.add(initialiseLabel("Bonjour Aventurier,", ((this.w)/7), (this.h/8)+0));
      this.add(initialiseLabel("Votre histoire commence ici et elle se finira ici.", ((this.w)/7), (this.h/8)+100));
      this.add(initialiseLabel("Des monstres ont réussi à tuer votre père le roi de la province de Fox hound,", ((this.w)/7), (this.h/8)+50));
      this.add(initialiseLabel("maintenant cette province s'appelle Xof et elle est dirigée par Skull face le mage noir.", ((this.w)/7), (this.h/8)+00));
      this.add(initialiseLabel("Il vous a lancé un sort grâce à sa maîtrise de la magie noire,", ((this.w)/7), (this.h/8)+50));
      this.add(initialiseLabel("vous condamnant à vous battre contre des monstres jusqu'à votre mort.\n", ((this.w)/7), (this.h/8)+00));

    }

    else
    {
      this.add(initialiseLabel("Bonjour Aventurier,", ((this.w)/7), (this.h/8)+0));

      this.afficheCharger();
    }

    this.add(initialiseLabel("Vous pouvez demander de l'aide à votre frère si vous le souhaitez.", ((this.w)/7), (this.h/8)+450));

    this.butOui = new JButton("Oui");
    this.butNon = new JButton("Non");

    butNon.setLocation((this.w/3), ((this.h)/8)+550);
    butOui.setLocation((this.w/6), ((this.h)/8)+550);

    butNon.setSize(100, 50);
    butOui.setSize(100, 50);

    this.add(butOui);
    this.add(butNon);

    ButtonAideListener blis = new ButtonAideListener();

    butOui.addActionListener(blis);
    butNon.addActionListener(blis);


    this.refresh();

  }


  public int getW()
  {
    return this.w;
  }

  public int getH()
  {
    return this.h;
  }

  public void refresh()
  {
    Image im = Toolkit.getDefaultToolkit().getImage("Server/Images/Environment/bg.jpg");
    JLabel bg = new JLabel(new ImageIcon(im));
    bg.setLocation(0, 0);
    bg.setSize(w,h-10);

    this.add(bg);
    this.setSize(500, 300);
    this.revalidate();
    this.setSize(w, h-10);
    this.revalidate();
  }

  public JLabel initialiseLabel(String s, int x, int y)
  {
    JLabel jlbl = new JLabel(s);
    jlbl.setForeground(Color.black);
    jlbl.setFont(new Font("Cochin", Font.PLAIN, (this.w)/50));
    jlbl.setLocation(x, y);
    jlbl.setSize(1500, 50);

    return jlbl;

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
      this.afficheCharger();
    }
    else if(rep == 'q')
    {
      System.exit(0);
    }
  }

  public void afficheCharger()
  {
    try
    {
      this.add(initialiseLabel("Nous avons trouvez ces parchemins, choisissez en un : ", ((this.w)/7), (this.h/8)+100));
      File fic = new File("./Server/Save/");

      File[] files = fic.listFiles();
      int k = 150;

      CliqueAdapter blis = new CliqueAdapter();

      for (File file : files) {
          String che = file.getPath();
          String fi = che.substring(14, che.length()-4);
          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
          JLabel lbl = initialiseLabel(fi+"\n: "+sdf.format(file.lastModified()), ((this.w)/7), (this.h/8)+k);
          this.add(lbl);
          lbl.addMouseListener(blis);
          k+=50;
        }
      this.refresh();
    }catch (Exception e) {
      System.out.println("erreur lors du chargement !!!!");
    }
  }

  public void charger(String s)
  {
    this.ennemies.clear();
    this.joueurs.clear();
    try
    {
      this.avancement = 0.0;
      Scanner f = new Scanner(new File("Server/Save/"+s+".txt"));
      this.mode = ((char)f.nextInt());
      this.level = f.nextInt();
      this.taille = f.nextInt();
      t.clear();

      for (int i = 0; i<this.taille*this.taille; i++)
      {
        t.add(f.nextInt());
      }

      this.nbJoueurs = f.nextInt();
      for (int i = 0; i<this.nbJoueurs; i++)
      {
        int longNom = f.nextInt();
        String nom = "";
        for(int j = 0; j<longNom ; j++)
        {
          nom += ((char)f.nextInt());
        }
        PJ p = new PJ();
        p.setNom(nom);
        p.setLvl(f.nextInt());
        p.setForce(f.nextInt());
        p.setAdresse(f.nextInt());
        p.setResistance(f.nextInt());
        p.setXp(f.nextInt());
        p.setVie(f.nextInt());
        p.setPosX(f.nextInt());
        p.setPosY(f.nextInt());
        p.setPa(f.nextInt());
        p.setArme(new Arme(f.nextInt(), f.nextInt()));
        p.setArmure(new Armure(f.nextInt(), f.nextInt()));
        p.setSac(new Inventaire());
        int nombre_elements = f.nextInt();
        for (int j = 0; j<nombre_elements; j++)
        {
          //System.out.println("j = "+j+"\nnb = "+p.getSac().getNb_Equipement());
          int equipement = f.nextInt();
          if(equipement == 0)
          {
            p.getSac().AddElementBP(new Arme(f.nextInt(), f.nextInt()));
          }
          else if(equipement == 1)
          {
            p.getSac().AddElementBP(new Armure(f.nextInt(), f.nextInt()));
          }
          else if(equipement == 2)
          {
            p.getSac().AddElementBP(new Potion_Degat(f.nextInt()));
          }
          else if(equipement == 3)
          {
            p.getSac().AddElementBP(new Potion_Soin(f.nextInt()));
          }
        }
        this.joueurs.add(p);
      }
      if(this.nbJoueurs == 1)
      {
        this.joueurs.add(new PJ());
      }
      Map.Nb_Enemies = f.nextInt();

      for(int i = 0; i<Map.Nb_Enemies; i++)
      {
        PNJ pnj = new PNJ();
        pnj.setNom("Monstre");
        pnj.setLvl(f.nextInt());
        pnj.setForce(f.nextInt());
        pnj.setAdresse(f.nextInt());
        pnj.setResistance(f.nextInt());
        pnj.setXp(f.nextInt());
        pnj.setVie(f.nextInt());
        pnj.setPosX(f.nextInt());
        pnj.setPosY(f.nextInt());
        pnj.setTypeMonstre(f.nextInt());
        pnj.setLoot(new Arme(f.nextInt(), f.nextInt()));

        this.ennemies.add(pnj);
      }
      System.out.println("chargement éffectué");
    }catch (Exception e) {
      System.out.println("erreur lors du chargement !!!!");
      afficheCharger();
    }
    System.out.println("(Entrer)");
    Scanner sc = new Scanner(System.in);
    sc.nextLine();

    this.m = new Map(t, level, taille, ennemies, joueurs);

    jeu('c', true);
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
      this.m.set_Case_Map(1, i+1, new PJ(this, i+1, 1));
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

  public void jeu(char c, boolean test)
  {
    boolean flag = test;
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

    if(this.joueurs.get(0).getPa()<=0 && this.joueurs.get(1).getPa()<=0)
    {
      c = 'a';
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
    if(this.joueurs.get(0).estMort() && this.joueurs.get(1).estMort())
    {
      System.out.println("¡¡¡¡¡ GAME OVER !!!!!");
      Map.Nb_Enemies = -1;
    }
    else
    {
      for (int i = 0; i<2; i++)
      {
        if(!this.joueurs.get(i).estMort() && this.joueurs.get(i).getPa() <= 0)
        {
          this.joueurs.get(i).setPa(3);
        }
      }
    }
    if(Map.Nb_Enemies == 0)
    {
      this.level++;
      jeu('a', false);
    }
    else if(Map.Nb_Enemies == -1)
    {
      System.exit(0);
    }
    if((flag || !this.joueurs.get(0).estMort() || !this.joueurs.get(1).estMort()))
    {
      try
      {
        this.m.Afficher();
        Thread.sleep(100);
        jeu('c', false);
      }
      catch (Exception e) {
        System.out.println("llllolomlololololollolo");
      }
    }
  }

  class ButtonAideListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if(e.getSource() == butOui)
      {
        nbJoueurs = 2;
        jeu('a', true);
      }

      else if(e.getSource() == butNon)
      {
        nbJoueurs = 1;
        jeu('a', true);
      }
      else
      {
        System.out.println("LLLLLLLOOOOOOOOLLLLLLLLLJEJZJ");
        System.out.println(e.getActionCommand());
      }
    }

  }

  class CliqueAdapter extends MouseAdapter
  {
    public void mouseClicked(MouseEvent e)
    {
      JLabel labelClicked = (JLabel) e.getSource();
      String s = "";
      String s1 = labelClicked.getText();
      int i = 0;
      while(s1.charAt(i) != '\n')
      {
        s+=s1.charAt(i);
        i++;
      }
      System.out.println(s);
      dispose();
      charger(s);

    }

    public void mousePressed(MouseEvent e)
    {
      JLabel labelClicked = (JLabel) e.getSource();
      //System.out.println(e.paramString());
    }

    public void mouseReleased(MouseEvent e)
    {
      JLabel labelClicked = (JLabel) e.getSource();
      //System.out.println(e.paramString());
    }

    public void mouseEntered(MouseEvent e)
    {
      JLabel labelClicked = (JLabel) e.getSource();
      labelClicked.setForeground(Color.red);
      //System.out.println(e.paramString());
    }

    public void mouseExited(MouseEvent e)
    {
      JLabel labelClicked = (JLabel) e.getSource();
      labelClicked.setForeground(Color.black);
      //System.out.println(e.paramString());
    }
  }

  class KListener implements KeyListener
  {
    public void keyReleased(KeyEvent e)
    {
      System.out.println((char)e.getKeyCode()+"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      if(m.mouvementPJ(joueurs, 1, (char)e.getKeyCode()))
      {
        menu();
      }
    }
    public void keyPressed(KeyEvent e)
    {
      return;
    }
    public void keyTyped(KeyEvent e)
    {
      return;
    }
  }
}
