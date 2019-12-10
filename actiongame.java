/* actiongame.java is called by battleship.java where main resides.  It allows user to chooose
 * difficulty and whether to reveal placement of opposing ships
 * 
 * Author:	Arron Pelc
 * Date:	12/1/2019*/
 
 
import java.util.Scanner;

public class actiongame
{
	public static void actionGame()
	{
		Scanner input = new Scanner(System.in);
		
		int 	selection;
		char    choice;
		boolean players;
		boolean cheater;
		
		System.out.print("BATTLESHIP!\n\nPlease select difficulty:\n  1. Beginner\n  2. Standard\n  3. Advanced\n\nSelection: ");
		selection = input.nextInt();
		selection = Character.toUpperCase(selection);
		
				
		System.out.print("Would you like to see the computer's board? (Y/N) ");
		choice = input.next().charAt(0);
		choice = Character.toUpperCase(choice);
		cheater = gameboard.yesOrNo(choice);
		
		gameboard.gameBoard(gameboard.difficulty(selection), cheater);
		
		
		
		
	}
}
