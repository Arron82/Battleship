/* Single player battleship.  This game contains no A.I., and is won by sinking ships before running out of missiles.  This is the 
 * main screen/startup menu.  It will call gameBoard.initialize(...).  
 * 
 * Author:	Arron Pelc
 * Date:	02/24/2020*/

import java.util.Scanner;
import java.util.InputMismatchException;

public class menuScreen 
{
	public static void main (String[] argrs)
	{
		System.out.println("This is a single-player version of Battleship!  To win you must destroy all enemy ships\n"
						+  "before you run out of missiles.  If you run out of missiles you lose.  Strikes are marked\n"
						+  "with an '*' and misses are marked with an 'x' on the grid.\n\n"
						+  "Please make a selection from the menu below.\n\n\n");	
		menu();
	}
	
	public static void menu()
	{
		Scanner input = new Scanner(System.in);
				
		int		choice = 0;
		
		try
		{
			System.out.println("\n\nBattleship!\n\nSelect difficulty:");
			System.out.print("1. Easy\n2. Standard\n3. Hard\n\nChoice: ");
			choice = input.nextInt();
			gameBoard.initialize(choice);		
		}
		
		catch(InputMismatchException e)
		{
			System.out.println("Invalid entry. Please choose from the menu options.");
			menu();
		}
	}
}