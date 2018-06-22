import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class TestInterface extends JFrame {

  private JButton butAventure;
  private JButton butSansFin;
  private JButton butCharger;
  private JButton butValider;

  private char mode;


  public TestInterface(int h, int w)
  {
    super("FATHER'S QUEST");

    this.setSize(w,h-10);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.initialisebtn(w);
    this.setVisible(true);

  }

  public void initialisebtn(int w)
  {
    JLabel jlbl_titre = new JLabel("FATHER'S QUEST");
    JLabel jlbl_texte = new JLabel("Voulez-vous jouer au mode aventure, en mode sans fin ou charger une partie : ");
    butAventure = new JButton("Aventure");
    butSansFin = new JButton("Sans fin");
    butCharger = new JButton("Charger");
    butValider = new JButton("Valider");
    jlbl_titre.setPreferredSize(new Dimension(w/2, 50));
    jlbl_texte.setPreferredSize(new Dimension(w/2, 50));
    butAventure.setPreferredSize(new Dimension(w/2, 50));
    butSansFin.setPreferredSize(new Dimension(w/2, 50));
    butCharger.setPreferredSize(new Dimension(w/2, 50));
    butValider.setPreferredSize(new Dimension(w/2, 50));

    this.setLayout(new FlowLayout());

    this.add(jlbl_titre);
    this.add(jlbl_texte);

    this.add(butAventure);
    this.add(butSansFin);
    this.add(butCharger);
    this.add(butValider);

    ButtonModeListener blis = new ButtonModeListener();

    butAventure.addActionListener(blis);
    butSansFin.addActionListener(blis);
    butCharger.addActionListener(blis);
    butValider.addActionListener(blis);

  }
  class ButtonModeListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if(e.getSource() == butAventure)
      {
        mode = 'a';
      }

      else if(e.getSource() == butSansFin)
      {
        mode = 'f';
      }

      else if(e.getSource() == butCharger)
      {
        mode = 'c';
      }

      else
      {
        dispose();
        Jeu tm = new Jeu(mode);
      }
    }

  }

  public static void main(String[] args)
  {
    Toolkit aTK= Toolkit.getDefaultToolkit();
		Dimension dim = aTK.getScreenSize();
    new TestInterface(dim.height, dim.width);
  }

}
