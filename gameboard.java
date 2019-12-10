/* gameboard.java is called by actiongame.java.  This is where the board, ships, 
 * and all user entry within the game is handled. It also produces output for 
 * in-game events
 * 
 * Author:	Arron Pelc
 * Date:	12/1/2019*/
 
 
import java.util.Scanner;
public class gameboard
{
	static int carrierLife = 5;
	static int battleshipLife = 4;
	static int destroyerLife = 3;
	static int submarineLife = 3;
	static int patrolLife = 2;
	static int shipCount = 5;
	
	/* gameBoard displays the actual game */
	public static void gameBoard(int arraySize, boolean cheater)
	{
		char[][] playerBoard 	= new char[arraySize][arraySize];
		char[][] referenceBoard = new char[arraySize][arraySize];
		int missiles = missileCount(arraySize);
		
		System.out.println("\n\n\nThis is a single-player version of Battleship!  \nTo win you must sink the opposing ships before you run out of missiles.  "
						+  "\n\nTo enter coordinates, please enter a letter and number with a space between (A 1, C 3, etc...) \n\nGood luck!");
		setSingleBoard(arraySize, referenceBoard);
		
		if (cheater == false)
		{
			System.out.println("\n\n");
			
			showSingleBoard(arraySize, playerBoard);
		
		
			while (missiles > 0)
			{
				missileFire(arraySize, referenceBoard, playerBoard);
				
				showSingleBoard(arraySize, playerBoard);
				
				System.out.println("\n\nMissiles left: " + (missiles-1));
				missiles--;
				
			}
			
			System.out.print("\n\n\nYou have run out of missiles!!  You lose!!\n\n\n");
			newGame();
		}
		
		if (cheater == true)
		{
			showDoubleBoard(arraySize, playerBoard, referenceBoard);
			
			while (missiles > 0)
			{
				missileFire(arraySize, referenceBoard, playerBoard);
				
				showDoubleBoard(arraySize, playerBoard, referenceBoard);
				
				System.out.println("\n\nMissiles left: " + (missiles-1));
				missiles--;
			}
			
			System.out.print("\n\n\nYou have run out of missiles!!  You lose!!\n\n\n");
			newGame();
		}
		
	}
	
	/* setSingleBoard creates the layout for the player board and 
	 * is where the ship methods are called to be placed */
	private static void setSingleBoard(int arraySize, char gameBoard[][])
	{
		char letterCoordinates = 'A';
		for (int i = 0; i < arraySize; i++)
		{
			for (int j = 0; j < arraySize; j++)
			{
				gameBoard[i][j] = ' ';				
			}
		}
		
		carrier(arraySize, gameBoard);
		battleship(arraySize, gameBoard);
		submarine(arraySize, gameBoard);
		destroyer(arraySize, gameBoard);
		patrol(arraySize, gameBoard);
	}
	
	/* showSingleBoard is called and displays the board when player 
	 * does not wish to see coordinates of opponent ships. */
	private static void showSingleBoard(int arraySize, char gameBoard[][])
	{
		
		char letterCoordinates = 'A';
		int numberCoordinates = 1;
		
		System.out.println("\n\n");
		for (int i = 0; i < arraySize; i++)
		{
			System.out.print("   "+numberCoordinates);
			numberCoordinates++;
		}
		System.out.println();
		
		System.out.println();
		for (int i = 0; i < arraySize; i++)
		{
			System.out.print(letterCoordinates+" ");
			for (int j = 0; j < arraySize; j++)
			{
				System.out.print("|_"+gameBoard[i][j]+"_");				
			}
			letterCoordinates++;
			System.out.println("\n");
		}
	}
	
	/* showDoubleBoard is called and displays both single board and 
	 * referenceBoard where opponent ships locations are revealed */
	private static void showDoubleBoard(int arraySize, char playerBoard[][], char referenceBoard[][])
	{
		char letterCoordinates = 'A';
		int numberCoordinates = 1;
		
		System.out.println("\n\n");
		for (int i = 0; i < arraySize; i++)
		{
			System.out.print("   "+numberCoordinates);
			numberCoordinates++;
		}
		System.out.println();
		
		
		for (int i = 0; i < arraySize; i++)
		{
			System.out.print(letterCoordinates);
			for (int j = 0; j < arraySize; j++)
			{
				System.out.print("|_"+playerBoard[i][j]+"_");
                if (playerBoard[i][j] == ' ')
                {
                    System.out.print ("|_ _");
                }
			}
			
			System.out.print("       X       ");
			for (int j = 0; j < arraySize; j++)
			{
				System.out.print("|_"+referenceBoard[i][j]+"_");
                
			}
			System.out.println("\n");
			letterCoordinates++;
		}
	}
	
	/* The following 5 methods contain information about each ship.
	 * Each ship method calls the placeShips method, in which each
	 * grid location will be checked before the ship is placed. */
	private static void carrier(int arraySize, char gameBoard[][])
	{
		final int SHIPLENGTH = 5;
		char shipType = 'C';
		
		placeShips(arraySize, SHIPLENGTH, shipType, gameBoard);
	}
	private static void battleship(int arraySize, char gameBoard[][])
	{
		final int SHIPLENGTH = 4;
		char shipType = 'B';
		
		placeShips(arraySize, SHIPLENGTH, shipType, gameBoard);
	}
	private static void submarine(int arraySize, char gameBoard[][])
	{
		final int SHIPLENGTH = 3;
		char shipType = 'S';
		
		placeShips(arraySize, SHIPLENGTH, shipType, gameBoard);
	}
	private static void destroyer(int arraySize, char gameBoard[][])
	{
		final int SHIPLENGTH = 3;
		char shipType = 'D';
		
		placeShips(arraySize, SHIPLENGTH, shipType, gameBoard);
	}
	private static void patrol(int arraySize, char gameBoard[][])
	{
		final int SHIPLENGTH = 2;
		char shipType = 'P';
		
		placeShips(arraySize, SHIPLENGTH, shipType, gameBoard);
	}
	
	/* The placeShips method generates random coordinates, and scans
	 * the array using those coordinates + SHIPLENGTH from each ship's
	 * method. If those coordinates are already occupied, another set 
	 * of random coordinates are generated. This loop continues until 
	 * empty coordinates are found. Vertical or horizontal placement
	 * is determined by another randomly generated number that produces
	 * 1 or 2. 1 generates vertical placement, 2 generates horizontal*/
	private static void placeShips(int arraySize, int shipLength, char shipType, char gameBoard[][])
	{
		boolean occupied = true;
		
		char grid;
		
		while (occupied == true)
		{
			int rand1 = randNum(0, (arraySize-shipLength));
			int rand2 = randNum(0, (arraySize-shipLength));
			int vOrH = randNum(1,2);
			boolean loop = true;
			
			if (vOrH == 1)
			{
				for (int i = rand1; i < rand1+shipLength; i++)
				{
					for (int j = rand2; j < rand2+1; j++)
					{
						if (gameBoard[i][j] != ' ')
						{
							loop = false;
						}
					}
				}
				
				if (loop == true)
				{
					occupied = false;
					
					for (int i = rand1; i < rand1+shipLength; i++)
					{
						for (int j = rand2; j < rand2+1; j++)
						{
							gameBoard[i][j] = shipType;
						}
					}
				}
			}
			
			else if (vOrH == 2)
			{
				for (int i = rand1; i < rand1+1; i++)
				{
					for (int j = rand2; j < rand2+shipLength; j++)
					{
						if (gameBoard[i][j] != ' ')
						{
							loop = false;
						}
					}
				}
				
				if (loop == true)
				{
					occupied = false;
					
					for (int i = rand1; i < rand1+1; i++)
					{
						for (int j = rand2; j < rand2+shipLength; j++)
						{
							gameBoard[i][j] = shipType;
						}
					}
				}
			}
		}
	}
	
	/* the whatIsHere method checks the array for char shipType and 
	 * returns 'X' if shipType is found (ship exists at selected 
	 * coordinates) or '*' if shipType is not found */
	private static char whatIsHere(char shipType)
	{
		
		char somethingHere;
		
		switch (shipType)
		{
			case 'C':
			{
				carrierLife--;
				somethingHere = '*';
				System.out.print("\n\n\n\n\n\n\n\n\nHIT!");
				break;
			}
			case 'B':
			{
				battleshipLife--;
				somethingHere = '*';
				System.out.print("\n\n\n\n\n\n\n\n\nHIT!");
				break;
			}
			case 'D':
			{
				destroyerLife--;
				somethingHere = '*';
				System.out.print("\n\n\n\n\n\n\n\n\nHIT!");
				break;
			}
			case 'S':
			{
				submarineLife--;
				somethingHere = '*';
				System.out.print("\n\n\n\n\n\n\n\n\nHIT!");
				break;
			}
			case 'P':
			{
				patrolLife--;
				somethingHere = '*';
				System.out.print("\n\n\n\n\n\n\n\n\nHIT!");
				break;
			}
			case ' ':
			{
				somethingHere = 'x';
				System.out.print("\n\n\n\n\n\n\n\n\nYou missed!");
				break;
			}
			case '*':
			{
				somethingHere = '*';
				System.out.print("\n\n\n\n\n\n\n\\n\nYou've already tried that, genius.  Missiles aren't free you know...");
				break;
			}
			case 'x':
			{
				somethingHere = 'x';
				System.out.print("\n\n\n\n\n\n\n\\n\nYou've already tried that, genius.  Missiles aren't free you know...");
				break;
			}
			default:
			{
				somethingHere = 'x';
			}
		}
		
		if (carrierLife == 0)
		{
			System.out.print("\nYou have destroyed the carrier!");
			carrierLife --;
			shipCount--;
			System.out.print("\n"+shipCount+" ships remain!");
		}
		else if (battleshipLife == 0)
		{
			System.out.print("\nYou have destroyed the battleship!");
			battleshipLife --;
			shipCount--;
			System.out.print("\n"+shipCount+" ships remain!");
		}
		else if (destroyerLife == 0)
		{
			System.out.print("\nYou have destroyed the destroyer!");
			destroyerLife --;
			shipCount--;
			System.out.print("\n"+shipCount+" ships remain!");
		}
		else if (submarineLife == 0)
		{
			System.out.print("\nYou have destroyed the submarine!");
			submarineLife --;
			shipCount--;
			System.out.print("\n"+shipCount+" ships remain!");
		}
		else if (patrolLife == 0)
		{
			System.out.print("\nYou have destroyed the patrol ship!");
			patrolLife --;
			shipCount--;
			System.out.print("\n"+shipCount+" ships remain!");
		}
		
		if (shipCount == 0)
		{
			System.out.print("\n\n\n\n\n\n\n\n\nYou have destoyed all the ships!!!  You Win!!!\n\n");
			newGame();
		}
		
		
		return somethingHere;
		
	}
	
	/* randNum generates random numbers for ship coordinates */
	private static int randNum(int min, int max)
	{
		int randomNumber = (int) (Math.random()*((max-min)+1))+min;
		
		return randomNumber;
	}
	
	/* difficulty translates selection from ArronPelc_gameboard.java 
	 * and converts the entry into an integer used for arraySize */
	public static int 	difficulty(int selection)
	{
		int arraySize;
		
		switch (selection)
		{
			case 1:
			{
				arraySize = 6;
				break;
			}
			case 2:
			{
				arraySize = 9;
				break;
			}
			default:
			{
				arraySize = 12;
			}
		}
		return arraySize;
	}
	
	/* charToInt converts letter coordinates into integers and returns
	 * the integer value of coordinates */
	private static int charToInt(char coord1)
	{
		int convert;
		
		switch (coord1)
		{
			case 'A':
			{
				convert = 0;
				break;
			}
			case 'B':
			{
				convert = 1;
				break;
			}
			case 'C':
			{
				convert = 2;
				break;
			}
			case 'D':
			{
				convert = 3;
				break;
			}
			case 'E':
			{
				convert = 4;
				break;
			}
			case 'F':
			{
				convert = 5;
				break;
			}
			case 'G':
			{
				convert = 6;
				break;
			}
			case 'H':
			{
				convert = 7;
				break;
			}
			case 'I':
			{
				convert = 8;
				break;
			}
			case 'J':
			{
				convert = 9;
				break;
			}
			case 'K':
			{
				convert = 10;
				break;
			}
			default:
			{
				convert = 11;
				break;
			}
		}
		return convert;
	}

	
	/* missileFire takes entered coordinates and checks array for ship
	 * placement. This method also tracks ship life and outputs commentary
	 * when a ship has been destroyed */
	private static void missileFire(int arraySize, char referenceBoard[][], char playerBoard[][])
	{
		Scanner input = new Scanner(System.in);
		char 	coord1;
		int     coord1Int;
		int 	coord2;
		
		System.out.print("Please enter strike coordinates: ");
		coord1 = input.next().charAt(0);
		coord1 = Character.toUpperCase(coord1);
		coord2 = input.nextInt();
		coord2 = coord2-1;
		coord1Int = charToInt(coord1);
		
		while (coord1Int > arraySize || coord1Int < 0)
		{
			System.out.print("\nThose coordinates do not exist!  Try again.\n");
			System.out.print("Please enter strike coordinates: ");
			coord1 = input.next().charAt(0);
			coord1 = Character.toUpperCase(coord1);
			coord2 = input.nextInt();
			coord2 = coord2-1;
			coord1Int = charToInt(coord1);
		}
		
		while (coord2 > arraySize || coord2 < 0)
		{
			System.out.print("\nThose coordinates do not exist!  Try again.\n");
			System.out.print("Please enter strike coordinates: ");
			coord1 = input.next().charAt(0);
			coord1 = Character.toUpperCase(coord1);
			coord2 = input.nextInt();
			coord2 = coord2-1;
			coord1Int = charToInt(coord1);
		}
		
		if (playerBoard[coord1Int][coord2] == 'x' || playerBoard[coord1Int][coord2] == '*')
		{
			System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\nYou've already tried that, genius.  Missiles aren't free you know...");
		}
		
		playerBoard[coord1Int][coord2] = whatIsHere(referenceBoard[coord1Int][coord2]);
		
		referenceBoard[coord1Int][coord2] = Character.toLowerCase(referenceBoard[coord1Int][coord2]);
	}
	
	/* missileCount generates number of missiles based on difficulty selected */
	private static int missileCount(int arraySize)
	{
		int missiles;
		
		switch(arraySize)
		{
			case 6:
			{
				missiles = 30;
				break;
			}
			case 9:
			{
				missiles = 50;
				break;
			}
			default:
			{
				missiles = 75;
			}
		}
		return missiles;
	}

	
	/* yesOrNo takes char entry in Yes or No choices and converts selection
	 * to boolean */
	public static boolean yesOrNo(char choice)
	{
		boolean answer = false;
		if (choice == 'Y')
		{
			answer = true;
		}
		
		return answer;
	}

	private static void newGame()
	{
		Scanner input = new Scanner(System.in);
		char answer;
		
		System.out.print("Would you like to play again?");
		answer = input.next().charAt(0);
		answer = Character.toUpperCase(answer);
		
		if (answer == 'Y')
		{
			System.out.println("\n\n\n\n\n\n\n\n\n\n");
			Main.main(null);
		}
		else
		{
			System.exit(0);
		}
	}
}
