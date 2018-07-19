import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class Father_s_Quest extends JFrame {

  private JButton butAventure;
  private JButton butSansFin;
  private JButton butCharger;

  private char mode;
  private int h;
  private int w;


  public Father_s_Quest(int h, int w)
  {
    super("FATHER'S QUEST");

    this.w = w;
    this.h = h;

    this.setSize(w,h-10);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(null);
    this.initialisebtn();
    this.setVisible(true);
    this.setBackground(Color.black);
    setResizable(false);
    this.initialiseBackground();

    this.setSize(500, 300);
    this.revalidate();
    this.setSize(w, h-10);
    this.revalidate();

  }

  public void initialiseBackground()
  {
    Random rand = new Random();

    ImageIcon solIcon = new ImageIcon("Server/Images/Tile/medievalTile_57.png");
    Image sol = solIcon.getImage();
	  Image solnew = sol.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);

    ImageIcon arbreIcon = new ImageIcon("Server/Images/Environment/medievalEnvironment_02.png");
    Image arbre = arbreIcon.getImage();
	  Image arbrenew = arbre.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);

    ImageIcon soldatIcon = new ImageIcon("Server/Images/Unit/medievalUnit_03.png");
    Image soldat = soldatIcon.getImage();
	  Image soldatnew = soldat.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);

    ImageIcon eauIcon = new ImageIcon("Server/Images/Tile/medievalTile_28.png");
    Image eau = eauIcon.getImage();
	  Image eaunew = eau.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);

    System.out.println(this.w);
    System.out.println(this.h);
    for (int i= 0; i<this.h; i+=120) {
      for (int j = 0; j<this.w; j+=120) {
        if(rand.nextInt(10) == 0)
        {
          JLabel eauLbl = new JLabel(new ImageIcon(eaunew));
          eauLbl.setLocation(j, i);
          eauLbl.setSize(120, 120);
          this.add(eauLbl);
        }
        else
        {
          JLabel solLbl = new JLabel(new ImageIcon(solnew));
          solLbl.setLocation(j, i);
          solLbl.setSize(120, 120);
          int n = rand.nextInt(10);

          if(n == 0)
          {
            JLabel img = new JLabel(new ImageIcon(soldatnew));
            img.setLocation(j, i);
            img.setSize(120, 120);
            this.add(img);
          }
          else if(n<7)
          {
            JLabel img = new JLabel(new ImageIcon(arbrenew));
            img.setLocation(j, i);
            img.setSize(120, 120);
            this.add(img);
          }
          this.add(solLbl);
        }
      }
    }
  }

  public void initialisebtn()
  {
    JLabel jlbl_titre = new JLabel("FATHER'S QUEST");
    JLabel jlbl_texte = new JLabel("Voulez-vous jouer au mode aventure, en mode sans fin ou charger une partie : ");
    butAventure = new JButton("Aventure");
    butSansFin = new JButton("Sans fin");
    butCharger = new JButton("Charger");


    jlbl_titre.setForeground(Color.white);
    jlbl_texte.setForeground(Color.white);

    jlbl_titre.setFont(new Font("Serif", Font.PLAIN, (this.w)/30));
    jlbl_texte.setFont(new Font("Serif", Font.PLAIN, (this.w)/50));

    jlbl_titre.setLocation(((this.w*2)/5), this.h/6);
    jlbl_texte.setLocation((this.w/6), (2*this.h)/6);
    butAventure.setLocation((this.w/4), (3*this.h)/6);
    butSansFin.setLocation((this.w/2), (3*this.h)/6);
    butCharger.setLocation(((this.w*3)/4), (3*this.h)/6);

    jlbl_titre.setSize(1000, 100);
    jlbl_texte.setSize(1500, 35);
    butAventure.setSize(100, 50);
    butSansFin.setSize(100, 50);
    butCharger.setSize(100, 50);

    this.add(jlbl_titre);
    this.add(jlbl_texte);

    this.add(butAventure);
    this.add(butSansFin);
    this.add(butCharger);

    ButtonModeListener blis = new ButtonModeListener();

    butAventure.addActionListener(blis);
    butSansFin.addActionListener(blis);
    butCharger.addActionListener(blis);

  }
  class ButtonModeListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      dispose();
      if(e.getSource() == butAventure)
      {
        Jeu tm = new Jeu('a', w, h);
      }

      else if(e.getSource() == butSansFin)
      {
        Jeu tm = new Jeu('f', w, h);
      }

      else
      {
        Jeu tm = new Jeu('c', w, h);
      }
    }

  }

  public static void main(String[] args)
  {
    Toolkit aTK= Toolkit.getDefaultToolkit();
		Dimension dim = aTK.getScreenSize();
    new Father_s_Quest(dim.height, dim.width);
  }

}
