/* This file handles the actual game. It initializes and generates boards, tracks scoring, and win/loss conditions.
 * 
 * Author:	Arron Pelc
 * Date:	02/24/2020*/

import java.util.Scanner;

public class gameBoard 
{
	static Scanner input = new Scanner(System.in);
	
	/* takes data from main menu and sets up game board. Determines whether 
	 * player wishes to see opponent board*/
	public static void initialize(int choice)
	{
		int		boardSize = 0;
		int		missileCount = 0;
		char	seeOtherBoard;
		boolean cheater = false;
		
		switch (choice)
		{
			case 1:
				boardSize = 6;
				missileCount = 25;
				break;
			case 2:
				boardSize = 9;
				missileCount = 50;
				break;
			case 3:
				boardSize = 12;
				missileCount = 75;
				break;
			default:
				System.out.println("Invalid entry. Please choose from the menu options.");
				menuScreen.menu();
		}
		
		
		/* this loop checks if player would like to see opponent board/ship locations. If user
		 * enters anything other than 'y' or 'n', do/while loop will repeat until satisfied. */
		do
		{
			System.out.print("\n\nWould you like to see the opponent board? (Y/N): ");
			seeOtherBoard = input.next().charAt(0);
			seeOtherBoard = Character.toUpperCase(seeOtherBoard);
			
			if (seeOtherBoard == 'Y')
			{
				cheater = true;
			}
			
			if (seeOtherBoard != 'Y' || seeOtherBoard != 'N')
			{
				System.out.println("\n\nInvalid entry.  Try again.\n\n");
			}
		}while (seeOtherBoard != 'Y' && seeOtherBoard != 'N');
		playGame(boardSize,missileCount,cheater);
	}
		
	/* The game loop, playGame(...) initializes player/opponent boards to 0, tracks
	 * scoring, and displays output for win/loss conditions*/
	private static void playGame(int boardSize, int missileCount, boolean cheater)
	{
		char[][] playerBoard = new char[boardSize][boardSize];		//Player board always visible to user
		/* opponentBoard is only visible to the user if indicated in game start. Ship locations stored here
		 * regardless of user visibility. */
		char[][] opponentBoard = new char[boardSize][boardSize];	
		
		//game flags to determine win/loss conditions
		boolean  missilesRemain = true;		//flag for whether user has run out of missiles
		boolean  opponentAlive = true;		//flag for whether user has defeated all enemy ships
		
		//ship life values to be tracked within game loop
		int 	shipCount = 5;
		int 	carrierLife = 5;
		int 	battleshipLife = 4;
		int 	destroyerLife = 4;
		int 	submarineLife = 3;
		int 	patrolLife = 2;
		
		char 	 shipType;			//stores return value of playerMoves(...) to track scoring
		
		//initializes arrays to empty for each board for ship placement
		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				playerBoard[i][j] = ' ';
				opponentBoard[i][j] = ' ';
			}
		}
		
		//places ships on opponent board
		ships.battleship(opponentBoard,boardSize);
		ships.carrier(opponentBoard,boardSize);
		ships.destroyer(opponentBoard,boardSize);
		ships.patrol(opponentBoard,boardSize);
		ships.submarine(opponentBoard,boardSize);	
		
		
		//game loop continues while missilesRemain and opponentAlive flags remain true
		do
		{
			shipType = playerMoves(playerBoard,opponentBoard,boardSize,missileCount,shipCount,cheater);
			missileCount--;
			
			
			//decrements ship life and ship count from playerMoves(...) return value and produces commentary if ship has sank
			shipType = Character.toUpperCase(shipType);
			switch (shipType)
			{
				case 'C':
					carrierLife--;
					if (carrierLife == 0)
					{
						System.out.println("You sank the carrier!");
						shipCount--;
					}
					break;
					
				case 'B':
					battleshipLife--;
					if (battleshipLife == 0)
					{
						System.out.println("You sank the battleship!");
						shipCount--;
					}
					break;
					
				case 'D':
					destroyerLife--;
					if (destroyerLife == 0)
					{
						System.out.println("You sank the destroyer!");
						shipCount--;
					}
					break;
					
				case 'P':
					patrolLife--;
					if (patrolLife == 0)
					{
						System.out.println("You sank the patrol ship!");
						shipCount--;
					}
					break;
					
				case 'S':
					submarineLife--;
					if (submarineLife == 0)
					{
						System.out.println("You sank the submarine!");
						shipCount--;
					}
					break;
			}
			
			if (shipCount == 0)
			{
				opponentAlive = false;  //if all ships sank, loop breaks
			}
			
			if (missileCount < 1)
			{
				missilesRemain = false;	//if all missiles used, loop breaks
			}
		}while (missilesRemain == true && opponentAlive == true);
		
		
		// Win/loss conditions
		if (missilesRemain == false)
		{
			System.out.println("\nMissiles remaining: "+missileCount+"\n\n");
			System.out.println("You have run out of missiles... You lose!");
			newGame();
		}
		
		if (opponentAlive == false)
		{
			System.out.println("\nShips remaining: "+shipCount+"\n\n");
			System.out.println("You have destroyed all enemy ships!  You win!");
			newGame();
		}
	}
	
	// Game engine. Generates/displays board(s) and stats, takes coordinates, displays hit or miss
	private static char playerMoves(char playerBoard[][], char opponentBoard[][], int boardSize, int missileCount, int shipCount, boolean cheater)
	{
		char	hitOrMiss; 				//stores return value of checkCoordinates(...) 
		char	coord1Char;				//stores value of letter coordinate that will be converted by charToInt(...)
		char	columnLetter = 'A';		//letter coordinate marker
		char	result;					//stores result of strike coordinates compared against opponent board
		char	cheaterHit;				//stores return value of cheaterBoardHits(...) 
		int		coord1;					//stores return value of charToInt(...)
		int		coord2;					//stores value of number coordinate
		int		rowNumber = 1;			//stores value of row coordinate numbers
		
		
		//generates array of row numbers at top of player board
		for (int i = 0; i < boardSize; i++)
		{
			System.out.print("   "+rowNumber);
			rowNumber++;
		}
		
		System.out.println("\n");	//creates blank space between row numbers and player board
		
		// IF player elects to NOT see opponent board, this loop is run and opponent board is not visible
		if (cheater == false)
		{
			for (int i = 0; i < boardSize; i++)
			{
				System.out.print(columnLetter+" ");
				for (int j = 0; j < boardSize; j++)
				{
					System.out.print("_" + playerBoard[i][j] + "_|");
				}
				System.out.println("\n");
				columnLetter++;
			}
		}
		
		// IF player elects to see opponent board, this loop is run showing both player and opponent boards
		if (cheater == true)
		{
			for (int i = 0; i < boardSize; i++)
			{
				System.out.print(columnLetter+" ");
				for (int j = 0; j < boardSize; j++)
				{
					System.out.print("_" + playerBoard[i][j] + "_|");
				}
				
				
				System.out.print("      " + columnLetter+" ");
				
				
				for (int j = 0; j < boardSize; j++)
				{
					System.out.print("_"+opponentBoard[i][j]+"_|");
				}
				columnLetter++;
				System.out.println("\n");
			}
		}
		
		System.out.println("Missiles remaining: " + missileCount + "       Ships remaining: " + shipCount);
		
		/* This section of code takes user-entered coordinates.  If the letter coordinate (coord1) is 
		 * outside of range, charToInt will return a value of -1. The user will be alerted and the 
		 * do/while loop will run again.*/
		do
		{
			System.out.print("Enter letter coordinate: ");
			coord1Char = input.next().charAt(0);
			coord1Char = Character.toUpperCase(coord1Char);
			System.out.print("Enter number coordinate: ");
			coord2 = (input.nextInt()-1);
			coord1 = charToInt(coord1Char);
			
			
			if (coord1 == -1)
			{
				System.out.println("Invalid entry.  Try again.\n\n");
			}
		}while (coord1 == -1);
	
		//determines whether a strike or miss has occurred
		hitOrMiss = checkCoordinates(opponentBoard,coord1,coord2);
		playerBoard[coord1][coord2] = hitOrMiss;		//stores '*' or 'x' at coordinates if hit or miss
		
		//determines whether a strike or miss has occurred for visible opponent board
		cheaterHit = cheaterBoardHits(opponentBoard,coord1,coord2);
		opponentBoard[coord1][coord2] = cheaterHit;		//stores lower-case value of ship type at opponent board coordinates if hit
		
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		//displays status of strike attempt for user
		if (hitOrMiss == '*')
		{
			System.out.println("HIT!!\n\n");
		}
		else
		{
			System.out.println("MISS!!\n\n");
		}
		
		return result = opponentBoard[coord1][coord2];
	}
	
	/* IF player elects to see opponent board:  Opponent ship locations are marked by capital letter
	 * representing each ship. If ship is struck, the capital letter is changed to lower-case to 
	 * make tracking of ships hit between two boards easier to follow*/
	private static char cheaterBoardHits(char gameBoard[][], int coord1, int coord2)
	{
		char  lowerCase;
		
		lowerCase = gameBoard[coord1][coord2];
		
		lowerCase = Character.toLowerCase(lowerCase);
		
		return lowerCase;
	}
	
	/* Takes coordinates from playerMoves and checks them against opponent's board.
	 * Returns char value based on whether an object exists at those coordinates*/
	private static char checkCoordinates(char gameBoard[][], int coord1, int coord2)
	{
		char result = '!';
		
		if (gameBoard[coord1][coord2] == ' ')
		{
			result = 'x';
		}
		else if (gameBoard[coord1][coord2] != ' ')
		{
			result = '*';
		}
		
		
		return result;
	}
	
	/* Simple method to transpose coordinate letters to integers.  If letter value entered by user
	 * is outside of array bounds, method will return value of -1, which will be caught in the 
	 * playerMoves(...) method. */
	private static int charToInt(char coord1)
	{
		int newCoord1 = 0;
		
		switch (coord1)
		{
			case 'A': 
				newCoord1 = 0;
				break;
			case 'B':
				newCoord1 = 1;
				break;
			case 'C':
				newCoord1 = 2;
				break;
			case 'D':
				newCoord1 = 3;
				break;
			case 'E':
				newCoord1 = 4;
				break;
			case 'F': 
				newCoord1 = 5;
				break;
			case 'G':
				newCoord1 = 6;
				break;
			case 'H':
				newCoord1 = 7;
				break;
			case 'I':
				newCoord1 = 8;
				break;
			case 'J':
				newCoord1 = 9;
				break;
			case 'K':
				newCoord1 = 10;
				break;
			case 'L':
				newCoord1 = 11;
				break;
			default:
				newCoord1 = -1;
		}
		
		return newCoord1;
	}
	
	// After win/loss this method is called. Gives user option to play again.
 	private static void newGame()
	{
		
		char	answer;
		System.out.print("\n\nWould you like to play again? (Y/N): ");
		answer = input.next().charAt(0);
		answer = Character.toUpperCase(answer);
		
		if (answer == 'Y')
		{
			menuScreen.menu();
		}
		else if (answer == 'N')
		{
			System.exit(0);;
		}
	}
}