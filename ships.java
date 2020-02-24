/* This file contains ship information, and a method for placing ships in the 2D array without overlap.  Spacing 
 * was not created between ships on the board simply because the 6x6 board would often have issues due to room.
 * This was maintained throughout all 3 board sizes for consistency.
 * 
 * Author:	Arron Pelc
 * Date:	02/24/2020*/

public class ships 
{
	public static void carrier(char gameBoard[][], int boardSize)
	{
		final int  shipLength = 5;
		char shipType = 'C';
		
		checkPlacement(gameBoard,boardSize,shipLength,shipType);
	}
	
	public static void destroyer(char gameBoard[][], int boardSize)
	{
		final int  shipLength = 4;
		char shipType = 'D';
		
		checkPlacement(gameBoard,boardSize,shipLength,shipType);
	}
	
	public static void battleship(char gameBoard[][], int boardSize)
	{
		final int  shipLength = 4;
		char shipType = 'B';
		
		checkPlacement(gameBoard,boardSize,shipLength,shipType);
	}
	
	public static void submarine(char gameBoard[][], int boardSize)
	{
		final int  shipLength = 3;
		char shipType = 'S';
		
		checkPlacement(gameBoard,boardSize,shipLength,shipType);
	}
	
	public static void patrol(char gameBoard[][], int boardSize)
	{
		final int  shipLength = 2;
		char shipType = 'P';
		
		checkPlacement(gameBoard,boardSize,shipLength,shipType);
	}
	
	
	/* Places ships in game board. xCoord and yCoord are randomly generated x,y coordinates. num3 determines
	 * whether ship is horizontally or vertically placed (1 = horizontal, 2 = vertical).  Method will take 
	 * generated coordinates and attempt to place ship. If a ship already exists at those grid points, new 
	 * x,y coordinates will be generated along with a new num3 to randomize the placement as much as possible.
	 * Each ship calls this method individually (working in order from largest to smallest) so that each 
	 * ship's placement will work independently of the others. The max parameter of the method randomNum is 
	 * set to boardSize-shipLength so that the initial x or y coordinate will never force the ship outside
	 * of the array grid. */
	private static void checkPlacement(char gameBoard[][], int boardSize, int shipLength, char shipType)
	{
		boolean occupied = true;			
		
		do
		{
			int xCoord = randomNum(0,(boardSize-shipLength));
			int yCoord = randomNum(0,(boardSize-shipLength));
			int num3 = randomNum(1,2);
			boolean somethingHere = false;
			
			if (num3 == 1)
			{
				for (int i = xCoord; i < xCoord+shipLength; i++)
				{
					for (int j = yCoord; j < yCoord+1; j++)
					{
						if (gameBoard[i][j] != ' ')
						{
							somethingHere = true;
						}
					}
				}
				
				if (somethingHere == false)
				{
					occupied = false;
					for (int i = xCoord; i < xCoord+shipLength; i++)
					{
						for (int j = yCoord; j < yCoord+1; j++)
						{
							gameBoard[i][j] = shipType;
						}
					}
				}
			}
			
			else if (num3 == 2)
			{
				for (int i = xCoord; i < xCoord+1; i++)
				{
					for (int j = yCoord; j < yCoord+shipLength; j++)
					{
						if (gameBoard[i][j] != ' ')
						{
							somethingHere = true;
						}
					}
				}
				
				if (somethingHere == false)
				{
					occupied = false;
					for (int i = xCoord; i < xCoord+1; i++)
					{
						for (int j = yCoord; j < yCoord+shipLength; j++)
						{
							gameBoard[i][j] = shipType;
						}
					}
				}
			}
		}while (occupied == true);
	}
	
	private static int randomNum(int min, int max)
	{
		int randomNumber = (int) (Math.random()*((max-min)+1))+min;
		
		return randomNumber;
	}
}