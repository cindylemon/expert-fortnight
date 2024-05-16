package tictactooe;

import java.util.Random;
import java.util.Scanner;

public class tictactoe {
	public static int number;
	public static int counter;
	public static boolean youFirst;
	public static int coordinate;
	private static int board[][] = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

	/**
	 * starts the whole thing
	 */
	public static void start() {
		counter = 0;
		System.out.println("Would you like to go first?");
		System.out.println("   [1] Yes (X)");
		System.out.println("   [2] No (O)");
		Scanner first = new Scanner(System.in);
		numbers(first, 1, 2, "Please indicate a response:");
		if (number == 1) {
			youFirst = true; // my boolean to control who plays x and who plays o
			grid(board);
			human();
		} else {
			youFirst = false;
			grid(board);
			computer();
		}
		grid(board);
	}

	/**
	 * updates the grid with whats been going on
	 * @param freak --> array with the grid coordinates
	 */
	public static void grid(int[][] freak) {
		for (int i = 0; i < board.length; i++) // i representing the board rows
		{
			for (int j = 0; j < board.length; j++) // j representing the columns
			{
				if (board[i][j] == 0) // print the x or o depending on the value in the thing
				{
					System.out.print(" ");
				} else if (board[i][j] == 1) {
					System.out.print("X");
				} else if (board[i][j] == 2) {
					System.out.print("O");
				}

				// vertical line printer
				if (((j == 0) || (j == 1))) {
					System.out.print("|");
				} else {
					System.out.print("");
				}
			}
			System.out.print("\n");
			// the dashes that separate the rows
			if ((i == 1) || (i == 0)) {
				System.out.print("------");
				System.out.print("\n");
			}
		}
	}

	/**
	 * the persons turn! YOUR turn!!!!!!
	 */
	public static void human() {
		System.out.println("\nYour turn:");
		while (true) {
			Scanner human = new Scanner(System.in);
			int column = numbers(human, 1, 3, "What column would you like to play in?") - 1; // columns is j
			int row = numbers(human, 1, 3, "What row would you like to play in?") - 1; // rows is i
			if (board[row][column] == 0) {
				if (youFirst == true) {
					board[row][column] = 1; //if you went first you're x so it makes it 1
				} else {
					board[row][column] = 2; //otherwise youre o so it becomes 2
				}
				break;
			} else {
				System.err.println("\nTile is already in use! Try again.");
			}

		}
		counter++; //adds to the counter so it knows when it draws
		grid(board);
		winner(board);
		computer(); //starts the computers turn
	}

	/**
	 * starts the computers turn!!!
	 */
	public static void computer() {
		Random hello = new Random();
		System.out.println("\nComputer's Turn:");
		while (true) {
			int row = hello.nextInt(3); //will choose a random number for the rows and the columns
			int column = hello.nextInt(3);
			if (board[row][column] == 0) {
				if (youFirst == true) { //depending on the x or o "value" its been assigned it will change the board
					board[row][column] = 2;
					break;
				} else {
					board[row][column] = 1;
					break;
				}
			}
		}
		
		grid(board); //shows the grid
		counter++; //will add to the counter
		winner(board); //checks for a winner

		human(); //runs the humans turn
	}

	/**
	 * error checking method i just stole from my rpg game project and i think i
	 * stole that from my game of nim(so versatile!)
	 * 
	 * @param scan --> the scanner that will be used
	 * @param min  --> the minimum number that can be chosen
	 * @param max  --> the maximum number that can be chosen
	 * @param text --> text that will be printed
	 * @return --> returns the valid number
	 */
	public static int numbers(Scanner scan, int min, int max, String text) {
		number = 0; // the number that will say what the user picked
		while (true) {
			System.out.println(text); // the text that will output -- if the user gets it out of range it will say it
										// again
			if (scan.hasNextInt()) { // makes sure the input is an integer
				number = scan.nextInt(); // sets the number as the int
				if (number < min || number > max) { // checks the range of the input
					System.err.println("ERROR. INPUT IS NOT IN RANGE."); // if input is not in range will output error
																			// message and loop again
					// scan.next();
					// continue;
				} else {
					break; // breaks out when its in range
				}
			} else {
				System.err.println("ERROR. INVALID INPUT."); // if its not an int it will output this
				scan.next();

			}
		}
		return number;
	}

	/**
	 * checks whether or not a win has been gotten
	 * 
	 * @param freak --> the board to check
	 */
	public static void winner(int[][] freak) {
	    int win;

	    // checks each row for a winner, but only x winner
	    for (int i = 0; i < freak.length; i++) {
	        win = 0;
	        for (int j = 0; j < freak[i].length; j++) {
	            if (freak[i][j] == 1) {
	                win++;
	            }
	        }
	        if (win == 3 && youFirst) {
	            youWin();
	        } else if (win == 3 && !youFirst) {
	            youLose();
	        }
	    }
	    
	    // checks each row for a winner, but only o winner
	    for (int i = 0; i < freak.length; i++) {
	        win = 0;
	        for (int j = 0; j < freak[i].length; j++) {
	            if (freak[i][j] == 2) {
	                win++;
	            }
	        }
	        if (win == 3 && !youFirst) {
	            youWin();
	        } else if (win == 3 && youFirst) {
	            youLose();
	        }
	    }

	    // checks each column for a winner, but only x winner
	    for (int j = 0; j < freak[0].length; j++) {
	        win = 0;
	        for (int i = 0; i < freak.length; i++) {
	            if (freak[i][j] == 1) {
	                win++;
	            }
	        }
	        if (win == 3 && youFirst) { //if you went first (are x) and you get three then u win, but if you are o and there are 3 u lose
	            youWin();
	        } else if (win == 3 && !youFirst) {
	            youLose();
	        }
	    }
	    
	    // checks each column for a winner, but only o winner
	    for (int j = 0; j < freak[0].length; j++) {
	        win = 0;
	        for (int i = 0; i < freak.length; i++) {
	            if (freak[i][j] == 2) {
	                win++;
	            }
	        }
	        if (win == 3 && !youFirst) { //if you went first (are x) and you get three then u win, but if you are o and there are 3 u lose
	            youWin();
	        } else if (win == 3 && youFirst) {
	            youLose();
	        }
	    }

	    // checks the first diagonal for a x winner
	    win = 0;
	    for (int diag = 0; diag < freak.length; diag++) {
	        if (freak[diag][diag] == 1) {
	            win++;
	        }
	    }
	    if (win == 3 && youFirst) { //same as above, checking which player won if it reaches 3
	        youWin();
	    } else if (win == 3 && !youFirst) {
	        youLose();
	    }
	    
	 // checks the first diagonal for an o winner
	    win = 0;
	    for (int diag = 0; diag < freak.length; diag++) {
	        if (freak[diag][diag] == 2) {
	            win++;
	        }
	    }
	    if (win == 3 && !youFirst) { //same as above, checking which player won if it reaches 3
	        youWin();
	    } else if (win == 3 && youFirst) {
	        youLose();
	    }

	    // checks the other diagonal for a winner
	    win = 0;
	    for (int diag = 0; diag < freak.length; diag++) {
	        if (freak[diag][freak.length - 1 - diag] == 1) {
	            win++;
	        }
	    }
	    if (win == 3 && youFirst) {
	        youWin();
	    } else if (win == 3 && !youFirst) {
	        youLose();
	    }

	    if (counter == 9) { //if the counter reaches 9 there are no more availale spots on the board
	        draw();
	    }
	    
	    // checks the other diagonal for an o winner
	    win = 0;
	    for (int diag = 0; diag < freak.length; diag++) {
	        if (freak[diag][freak.length - 1 - diag] == 2) {
	            win++;
	        }
	    }
	    if (win == 3 && !youFirst) {
	        youWin();
	    } else if (win == 3 && youFirst) {
	        youLose();
	    }

	    if (counter == 9) { //if the counter reaches 9 there are no more availale spots on the board
	        draw();
	    }
	}

	/**
	 * prints out that there was a draw
	 */
	private static void draw() {
		System.out.println("The game ended in a draw. Nice try!");
		System.exit(0);

	}

	/**
	 * prints out that yo ulost
	 */
	private static void youLose() {
		System.out.println("You lost!");
		System.exit(0);

	}

	/**
	 * prints out that you won
	 */
	private static void youWin() {
		System.out.println("You won!");
		System.exit(0);
		
	}

}
