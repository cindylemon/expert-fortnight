import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TicTacToe {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Random ai = new Random();
		boolean turn = true;
		int draw = 0;
		int x = 1;
		int y = 1;
		//extra columns and rows are for the cpu to cheat with
		//0 = blank, 1 = X(player), 2 = O(cpu)
		int[][] board = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0 ,0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
		
		System.out.println("WELCOME TO ULTIMATE");
		System.out.println("TIC");
		System.out.println("TAC");
		System.out.println("TOE");
		System.out.println("!!!!!!!!!!!!!!! D:<<<<<<<<<<<<<<");
		// Randomly determines whether the cpu or the player will go first
		if(ai.nextInt(2) == 0)
		{
			turn = false;
		}else
		{
			turn = true;
		}
		// Previous code is to prove I can make it random, but I prefer when you go first so the CPU has the opportunity to cheat
		turn = true;
		
		//Endlessly loops turns until the game is over(exits the system within the while loop
		while(true)
		{
			System.out.println("--------------------------------------------------");
			drawGrid(board);
			
			draw = 0;
			//Checks if every spot in the board has been filled
			for(int row = 1; row < 4; row ++)
			{
				for(int column = 1; column < 4; column++)
				{
					if(board[row][column] != 0)
					{
						draw += 1;
					}
				}
			}
			//Decides whose turn it currently is
			if(turn)
			{
				System.out.println("Your Move!");
				//Ends the game as a draw if every spot has been filled
				if(draw == 9)
				{
					System.out.println("no more moves!");
					System.out.println("Its a tie!!!");
					System.out.println("CBot: Suck it loser!!! >:(");
					System.exit(0);
				}
				
				//Error checking loop for the coordinate inputs
				while(true)
				{
					x = isInt(in, 1, 3, "What row would you like to play in?");
					y = isInt(in, 1, 3, "What column would you like to play in?");
					
					//Checks if the move inputted is for an empty coordinate
					if(board[x][y] == 0)
					{
						board[x][y] = 1;
						break;
					}else
					{
						System.err.println("CBot: Are you blind?!? That spot is already filled...");
					}
				}
				//Turn then rotates to the CPU
				turn = false;
			}else
			{
				System.out.println("CarbyBot's Move!");
				System.out.println("Computing...");
				//Waits for 2 seconds to think a little
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
				}
				
				//Cheats to win the game if every spot has been filled
				if(draw == 9)
				{
					pickMove(board, true);
				}else
				{
					int[] coord = pickMove(board, false);
					board[coord[0]][coord[1]] = 2;
				}
				//Turn then rotates to the player
				turn = true;
			}
			
			//Checks if the CPU has won yet, and ends the game if so
			if(checkWin(board))
			{
				drawGrid(board);
				System.out.println("------------------------");
				System.out.println("GAME OVER");
				System.out.println("CBot: I'd say good game, but let's be f&@$@ng honest here...");
				break;
			}
		}
	}
	
	/**
	 * Checks if the cpu has won yet
	 * @param grid: The board array to check
	 * @return whether the cpu won yet
	 */
	public static boolean checkWin(int[][] grid)
	{
		int win = 0;
		
		//Checks each row to see if all the spots are Xs
		for(int row = 1; row < 4; row++)
		{
			win = 0;
			//Adds to a counter for each spot in the row that is an X
			for(int column = 1; column < 4; column++)
			{
				if (grid[row][column] == 2)
				{
					win += 1;
				}
			}
			//Checks if the counter has reached 3
			if(win == 3)
			{
				return true;
			}
		}
		
		//Checks each column to see if all the spots are Xs
		for(int column = 1; column < 4; column++)
		{
			win = 0;
			//Adds to a counter for each spot in the column that is an X
			for(int row = 1; row < 4; row ++)
			{
				if (grid[row][column] == 2)
				{
					win += 1;
				}
			}
			//Checks if the counter has reached 3
			if(win == 3)
			{
				return true;
			}
		}
		
		//Checks the first diagonal to see if all the spots are Xs
		win = 0;
		for(int diag = 1; diag < 4; diag++)
		{
			//Adds to a counter for each spot in the diagonal that is an X
			if (grid[diag][diag] == 2)
			{
				win += 1;
			}
		}
		//Checks if the counter has reached 3
		if (win == 3)
		{
			return true;
		}
		
		//Checks the second diagonal to see if all the spots are Xs
		win = 0;
		for(int diag = 1; diag < 4; diag++)
		{
			//Adds to a counter for each spot in the diagonal that is an X
			if (grid[4 - diag][diag] == 2)
			{
				win += 1;
				
			}
		}
		//Checks if the counter has reached 3
		if (win == 3)
		{
			return true;
		}
		
		//Returns false is none of the rows, columns, nor diagonals are filled with Xs
		return false;
	}
	
	/**
	 * Checks the positions on the board, and determines if any row is in danger of being won by a side
	 * @param grid: the board array to check
	 * @param side: the side to check
	 * @return The coordinates needed to block a move, or 0,0 if there are none
	 */
	public static int[] checkPos(int[][] grid, int side)
	{
		int[] coord = {0, 0};
		int win = 0; //Counter for checking if one side is about to win(2 of either X or O in one row, column, or diag)
		boolean isWin = false;
		
		//Checks each row to see if it needs to be defended
		for(int row = 1; row < 4; row++)
		{
			win = 0;
			isWin = false;
			for(int column = 1; column < 4; column++)
			{
				//Checks if there are two of either X or O in that row, or an empty spot in the row
				if(grid[row][column] == side)
				{
					win += 1;
				}else if(grid[row][column] == 0)
				{
					coord[0] = row;
					coord[1] = column;
					isWin = true;
				}
			}
			
			//Returns the empty coordinate if there is both an empty spot and two of either X or O in a row
			if(win == 2 && isWin)
			{
				return coord;
			}
		}
		
		//Checks each column to see if it needs to be defended
		for(int column = 1; column < 4; column++)
		{
			win = 0;
			isWin = false;
			for(int row = 1; row < 4; row++)
			{
				//Checks if there are two of either X or O in that column, or an empty spot in the column
				if(grid[row][column] == side)
				{
					win += 1;
				}else if(grid[row][column] == 0)
				{
					coord[0] = row;
					coord[1] = column;
					isWin = true;
				}
			}
			
			//Returns the empty coordinate if there is both an empty spot and two of either X or O in that column
			if(win == 2 && isWin)
			{
				return coord;
			}
		}
		
		//Checks the first diagonal to see if it needs defending
		win = 0;
		isWin = false;
		for(int diag = 1; diag < 4; diag++)
		{
			//Adds to a counter for each spot in the diagonal that is an X or O
			if (grid[diag][diag] == side)
			{
				win += 1;
			}
			if(grid[diag][diag] == 0)
			{
				coord[0] = diag;
				coord[1] = diag;
				isWin = true;
			}
		}
		//Returns the empty coordinate if there is both an empty spot and two of either X or O in that diagonal
		if (win == 2 && isWin)
		{
			return coord;
		}
		
		//Checks the second diagonal to see if it needs defending
		win = 0;
		isWin = false;
		for(int diag = 1; diag < 4; diag++)
		{
			//Adds to a counter for each spot in the diagonal that is an X or O
			if (grid[diag][4 - diag] == side)
			{
				win += 1;
			}
			if(grid[diag][4-diag] == 0)
			{
				coord[0] = diag;
				coord[1] = 4 - diag;
				isWin = true;
			}
		}
		//Returns the empty coordinate if there is both an empty spot and two of either X or O in that diagonal
		if (win == 2 && isWin)
		{
			return coord;
		}
		
		coord[0] = 0;
		coord[1] = 1;
		return coord;
	}
	
	/**
	 * Picks and executes the bot's move
	 * @param grid: array with grid coordinates
	 * @param isDraw: whether the computer should cheat to win or not
	 * @return the coordinates that the bot will take
	 */
	public static int[] pickMove(int[][] grid, boolean isDraw)
	{
		//Cheats if the bot is about to draw, otherwise picks a regular move
		if(isDraw)
		{
			//Runs through every row and column to check for Os to cheat with
			for(int row = 1; row < 4; row++)
			{
				for(int column = 1; column < 4;column++)
				{
					//Ignores the middle spot because checking it is redundant
					if((row != 2) || (column != 2))
					{
						//Checks for each O
						if(grid[row][column] == 2)
						{
							//Checks every spot next to the O
							for(int nRow = -1; nRow < 2; nRow ++)
							{
								for(int nColumn = -1; nColumn < 2; nColumn++)
								{
									//Ignores the spot itself
									if(nRow != 0 || nColumn != 0)
									{
										//Checks if there is an adjacent O
										if(grid[row + nRow][column + nColumn] == 2)
										{
											//Checks if the spot opposite to the adjacent spot is empty
											if(grid[row - nRow][column - nColumn] == 0)
											{
												grid[row - nRow][column - nColumn] = 2;
												drawGrid(grid);
												System.out.println("GAME OVER");
												System.out.println("CBot: Didn't see that coming, did ya?");
												System.exit(0);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}else //If the bot is not cheating
		{
			int[] coord = {0, 0};
			int test = 0;
			
			//returns any position that the bot can win with
			coord = checkPos(grid, 2);
			if(coord[0] != 0 && coord[1] != 0)
			{
				return coord;
			}
			
			//returns any position the bot must block the player from winning with
			coord = checkPos(grid, 1);
			if(coord[0] != 0 && coord[1] != 0)
			{
				return coord;
			}
			
			//Returns the center if it is empty
			if(grid[2][2] == 0)
			{
				coord[0] = 2;
				coord[1] = 2;
				return coord;
			}else
			{
				//If the center is occupied, the bot will prioritize filling a corner with an empty opposing corner
				for(int row = 1; row < 4; row += 2)
				{
					for(int column = 1; column < 4; column +=2)
					{
						if(grid[row][column] == 0 && grid[4-row][4-column] == 0)
						{
							coord[0] = row;
							coord[1] = column;
							return coord;
						}
					}
				}
				
				//If none of the other moves are playable, the bot will play the first available position
				for(int row = 1;row < 4; row++)
				{
					for(int column = 1;column < 4; column++)
					{
						if(grid[row][column] == 0)
						{
							coord[0] = row;
							coord[1] = column;
							return coord;
						}
					}
				}
			}
			

		}
		
		int[] error = {14, 14};
		return error;
	}
	
	/**
	 * Prints out the current grid
	 * @param grid: array with the grid coordinates
	 */
	public static void drawGrid(int[][] grid)
	{
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				//prints either an X, O , or a blank space, based on which number is stored in the coordinate
				if(grid[row][column] == 0)
				{
					System.out.print(" ");
				}else if(grid[row][column] == 1)
				{
					System.out.print("X");
				}else
				{
					System.out.print("O");
				}
				
				//Prints a vertical line to separate each column, or a blank space for the cheating zone
				if(((column == 1) || (column == 2)) && ((row != 0) && (row != 4)))
				{
					System.out.print("|");
				}else
				{
					System.out.print(" ");
				}
			}
			System.out.print("\n");
			//Prints a horizontal line to separate each row, or a blank line for the cheating zone
			if((row == 1) || (row == 2))
			{
				System.out.print("  -----");
				System.out.print("\n");
			}else
			{
				System.out.print("\n");
			}
		}
	}
	
	/**
	 * Takes an integer input and checks it for errors
	 * @param in: the Scanner object used for input
	 * @param min: the minimum range of the integer
	 * @param max: the maximum range of the integer
	 * @param message: The message to print asking for an input
	 * @return: the input after it has been checked for errors
	 */
	public static int isInt(Scanner in, int min, int max, String message)
	{
		boolean check = false;
		int input = 0;
		// Loops forever until the message contains no errors
		while(!check)
		{
			System.out.println(message);
			check = in.hasNextInt();
			// Checks if the message is an integer
			if(!check)
			{
				System.err.println("CBot: Stop typing errors and accept defeat!!");
				in.next();
				continue;
			}
			
			input = in.nextInt();
			//checks if the message is within the specified range
			if((input < min) || (input > max))
			{
				System.err.println("CBot: Learn the rules of the game before facing me d*$b@$&@!!!");
				check = false;
			}
			
		}
		
		return input;
	}

}
