//import com.modeliosoft.modelio.javadesigner.annotations.objid;
import java.util.Scanner;
import java.util.Random;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;

//import com.modeliosoft.modelio.javadesigner.annotations.objid;
public abstract class Personnage {
  private int lvl;
  private String nom;
  private int force;
  private JLabel lblForce;
  private int adresse;
  private JLabel lblAdresse;
  private int resistance;
  private JLabel lblResistance;
  private int xp;
  private int vie;
  private int vieMax;
  private int posX;
  private int posY;
  private Jeu jeu;
  private JTextField lab;

  private JLabel lblPtsRestant;

  private JButton forcePlus;
  private JButton forceMoins;
  private JButton adressePlus;
  private JButton adresseMoins;
  private JButton resistancePlus;
  private JButton resistanceMoins;

  private Image player;

  public static int nbPerso = 0;

    public Personnage()
    {
      nbPerso++;
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
      nbPerso++;
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

    public Personnage(Jeu j, int x, int y) {
        nbPerso++;
        this.jeu = j;
        jeu.getContentPane().removeAll();

        this.posX = x;
        this.posY = y;

        this.vie = 10;
        this.vieMax = 10;

        this.xp = 0;

        this.force = 0;
        this.adresse = 0;
        this.resistance = 0;

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
      String color;
      if(nbPerso == 1)
      {
        color = "Blue";
      }
      else
      {
        color = "Green";
      }
      ImageIcon playerIcon = new ImageIcon("Server/Images/Unit/"+color+"_Villager.png");
      Image playerimage = playerIcon.getImage();
      player = playerimage.getScaledInstance((jeu.getW()/3)*2, (jeu.getH()/10)*9,  java.awt.Image.SCALE_SMOOTH);
      JLabel playerLabel = new JLabel(new ImageIcon(player));
      playerLabel.setLocation(((jeu.getW())/3), (jeu.getH()/10));
      playerLabel.setSize((jeu.getW()/3)*2, (jeu.getH()/10)*9);
      jeu.add(jeu.initialiseLabel("Entrez le nom de votre hero : ", 200, (jeu.getH()/8)+0));
      lab = new JTextField(25);
      lab.setText("");
      lab.setOpaque(false);
      lab.setLocation(((jeu.getW())/4)+200, (jeu.getH()/8)+20);
      lab.setSize(150, 25);
      jeu.add(lab);
      jeu.add(jeu.initialiseLabel("Force : ", 200, (jeu.getH()/8)*2));
      jeu.add(jeu.initialiseLabel("Adresse : ", 200, (jeu.getH()/8)*3));
      jeu.add(jeu.initialiseLabel("Resistance : ", 200, (jeu.getH()/8)*4));
      forcePlus = new JButton("+");
      lblForce = jeu.initialiseLabel(""+force, (jeu.getW()/6+165), (jeu.getH()/8)*2);
      forceMoins = new JButton("-");
      adressePlus = new JButton("+");
      lblAdresse = jeu.initialiseLabel(""+adresse, (jeu.getW()/6+165), (jeu.getH()/8)*3);
      adresseMoins = new JButton("-");
      resistancePlus = new JButton("+");
      lblResistance = jeu.initialiseLabel(""+resistance, (jeu.getW()/6+165), (jeu.getH()/8)*4);
      resistanceMoins = new JButton("-");

      JButton valide = new JButton("Valider");

      lblPtsRestant = jeu.initialiseLabel("18 points restants.", (jeu.getW()/6), (jeu.getH()/8)*5);

      forcePlus.setLocation((jeu.getW()/6+200), (jeu.getH()/8)*2+20);
      forcePlus.setSize(50, 25);

      forceMoins.setLocation((jeu.getW()/6+100), (jeu.getH()/8)*2+20);
      forceMoins.setSize(50, 25);

      adressePlus.setLocation((jeu.getW()/6+200), (jeu.getH()/8)*3+20);
      adressePlus.setSize(50, 25);

      adresseMoins.setLocation((jeu.getW()/6+100), (jeu.getH()/8)*3+20);
      adresseMoins.setSize(50, 25);

      resistancePlus.setLocation((jeu.getW()/6+200), (jeu.getH()/8)*4+20);
      resistancePlus.setSize(50, 25);

      resistanceMoins.setLocation((jeu.getW()/6+100), (jeu.getH()/8)*4+20);
      resistanceMoins.setSize(50, 25);

      valide.setLocation((jeu.getW()/6+50), (jeu.getH()/8)*5+100);
      valide.setSize(200, 50);

      forceMoins.setEnabled(false);
      adresseMoins.setEnabled(false);
      resistanceMoins.setEnabled(false);

      jeu.add(forceMoins);
      jeu.add(lblForce);
      jeu.add(forcePlus);
      jeu.add(adresseMoins);
      jeu.add(lblAdresse);
      jeu.add(adressePlus);
      jeu.add(resistanceMoins);
      jeu.add(lblResistance);
      jeu.add(resistancePlus);
      jeu.add(lblPtsRestant);
      jeu.add(playerLabel);
      jeu.add(valide);

      BoutonStatListener bSL = new BoutonStatListener();

      forcePlus.addActionListener(bSL);
      forceMoins.addActionListener(bSL);
      adressePlus.addActionListener(bSL);
      adresseMoins.addActionListener(bSL);
      resistancePlus.addActionListener(bSL);
      resistanceMoins.addActionListener(bSL);
      valide.addActionListener(bSL);

      jeu.refresh();
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

    class BoutonStatListener implements ActionListener
    {

      public void actionPerformed(ActionEvent e)
      {
        if(e.getActionCommand().equals("Valider") && force+adresse+resistance == 18 && !(lab.getText().equals("")) && lab.getText().charAt(0)!=' ')
        {
          System.exit(0);
        }
        if(e.getSource() == forcePlus && force+adresse+resistance < 18)
        {
          force++;

          lblForce.setText(""+force);
        }

        else if(e.getSource() == forceMoins && force > 0)
        {
          force--;

          lblForce.setText(""+force);
        }

        else if(e.getSource() == resistancePlus && force+adresse+resistance < 18)
        {
          resistance++;

          lblResistance.setText(""+resistance);
        }

        else if(e.getSource() == resistanceMoins && resistance > 0)
        {
          resistance--;

          lblResistance.setText(""+resistance);
        }

        else if(e.getSource() == adressePlus && force+adresse+resistance < 18)
        {
          adresse++;

          lblAdresse.setText(""+adresse);
        }

        else if(e.getSource() == adresseMoins && adresse > 0)
        {
          adresse--;

          lblAdresse.setText(""+adresse);
        }

        if (force+adresse+resistance >= 18)
        {
          forcePlus.setEnabled(false);
          adressePlus.setEnabled(false);
          resistancePlus.setEnabled(false);
        }
        else
        {
          forcePlus.setEnabled(true);
          adressePlus.setEnabled(true);
          resistancePlus.setEnabled(true);
        }
        if(force <= 0)
        {
          forceMoins.setEnabled(false);
        }
        else
        {
          forceMoins.setEnabled(true);
        }
        if(adresse <= 0)
        {
          adresseMoins.setEnabled(false);
        }
        else
        {
          adresseMoins.setEnabled(true);
        }
        if(resistance <= 0)
        {
          resistanceMoins.setEnabled(false);
        }

        else
        {
          resistanceMoins.setEnabled(true);
        }
        int pts = 18-(force+adresse+resistance);
        lblPtsRestant.setText(""+pts+" points restants.");
      }
    }

}
