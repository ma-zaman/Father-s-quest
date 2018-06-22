import java.util.*;

public class Father_s_Quest
{
  public static void main(String[] args) {
    System.out.print("\033[H\033[2J");
    System.out.println("FATHER'S QUEST\n");
    System.out.print("Voulez-vous jouer au mode aventure(= a), en mode sans fin(= f) ou charger une partie(= c) : ");
    Scanner sc = new Scanner(System.in);
    char rep;
    do {
      rep = sc.next().charAt(0);
      if(rep != 'a' && rep != 'f' && rep != 'c')
      {
        System.out.print("Entrer a pour le mode aventure, f pour le mode sans fin ou c pour charger une partie : ");
      }
    } while (rep != 'a' && rep != 'f' && rep != 'c');

    Jeu tm = new Jeu(rep);
  }
}
