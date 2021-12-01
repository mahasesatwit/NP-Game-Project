import java.util.Scanner;
////////////////////////////////////////////////////////////////////////////////////////////////////
//								TCP-Tac-Toe ---- Game Code										  //
//								Sam Bogonis & Shemar Mahase										  //
//							Network Programming - Final Project									  //
//										TicTacToe												  //
////////////////////////////////////////////////////////////////////////////////////////////////////

public class TicTacToe {
	
////////////////////////////////////////////////////////////////////////////////////////////////////	
//				Tic-Tac-Toe code based off of the following GitHub project: 					  //
//			https://github.com/aoyshi/Java-Tic-Tac-Toe/blob/master/TicTacToe.java				  //
////////////////////////////////////////////////////////////////////////////////////////////////////	
	
////////////////////////////////////////////////////////////////////////////////////////////////////
//getValidInt(String prompt): Checks to see if the player entered a valid play
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int getValidInt (String prompt) {
		
		//the scanner will need to be replaced for it to take tcp inputs 
		
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print(prompt);
			String input = in.nextLine();
			int num = 0;
			
			try {
				num = Integer.parseInt(input);
			}
			catch (Exception e) {
				System.out.println("Invalid Integer!");
				continue;
			}
			if (num < 0 || num > 2) {
				System.out.println("Integer must be between 0 and 2");
			}
			return num;
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
//checkRows(int[][] A): Checks the rows of the board for a win
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean checkRows(int[][] A) {
		for (int i = 0; i < A.length; i++) {
			
			if ( (A[i][0] == A [i][1]) && (A[i][1] == A[i][2]) && A[i][0] != 0 ) {
				return true;
			}
		}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
//checkCols(int[][] A): Checks the columns of the board for a win
////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static boolean checkCols(int[][] A) {
		for (int i = 0; i < A[0].length; i++) {
			if ( (A[0][i] == A[1][i]) && (A[1][1] == A[1][i]) && A[0][i] != 0 ) {
				return true;
			}
		}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
//checkDiags(int[][] A): Checks the diagonals of the board for a win
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean checkDiags(int[][] A) {
		if( (A[0][0]==A[1][1]) && (A[1][1]==A[2][2]) && A[0][0] !=0) {
			return true;
		}
		else if ( (A[0][2]==A[1][1]) && (A[1][1]==A[2][0]) && A[1][1] !=0) {
			return true;
		}
		else {
			return false;
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
//checkHit(int[][] A): Checks if there is a 3 in a row on the board
////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static boolean checkHit(int[][] A) {
		if(checkRows(A) || checkCols(A) || checkDiags(A)) {
			return true;
		}
	    else {
	    	return false;
	    }
	  }
////////////////////////////////////////////////////////////////////////////////////////////////////
//isFree(int[][] A, int row, int col): Checks if the space the player chose is free
////////////////////////////////////////////////////////////////////////////////////////////////////

	  public static boolean isFree(int[][] A, int row, int col) {
		  if(A[row][col] == 0) {
			  return true;
		  }
		  else {
			  return false;
		  }
	  }
////////////////////////////////////////////////////////////////////////////////////////////////////
//getWinner(String turnPrompt, int[][] A, int playerNumber): Determines the game winner
////////////////////////////////////////////////////////////////////////////////////////////////////  
	  public static boolean getWinner(String turnPrompt, int[][] A, int playerNumber) {
		  System.out.println(turnPrompt);
		  int row = 0;
		  int col = 0;
		  while(true) {
			  row = getValidInt("Enter row: ");
			  col = getValidInt("Enter col: ");  
			  if(isFree(A,row,col)) {
				  break;
			  }
			  System.out.printf("[%d,%d] is already filled!\n",row,col);
		  }  
		  A[row][col] = playerNumber;     
		  return checkHit(A);
	  }
	  
////////////////////////////////////////////////////////////////////////////////////////////////////
//printBoard(int[][] A): Prints game board as is 
////////////////////////////////////////////////////////////////////////////////////////////////////
	  
	  public static void printBoard(int[][] A) {
		  System.out.println("-------------");
		  for (int i = 0; i < 3; i++) {
			  System.out.print("| ");
			  
			  for (int j = 0; j < 3; j++) {
				  System.out.print(A[i][j] + " | ");
			  }
			  
			  System.out.println();
			  System.out.println("-------------");
		  }

	  }
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////
//main(String[] args): Plays the game with two players, determines a winner or a draw
////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [][] grid = new int[3][3];
		int foundWinner = 0;
		
		printBoard(grid);
		
		int i = 0;
		while (i < 9) {
			if (i % 2 == 0) {
				if (getWinner("Player 1 turn", grid, 1)) {
					foundWinner = 1;
					System.out.println("Player 1 Wins!");
					break;
				}
				printBoard(grid);
				System.out.println();
			}
			else {
				if (getWinner("Player 2 turn", grid, 2)) {
					foundWinner = 1;
					System.out.println("Player 2 Wins!");
					break;
				}
				printBoard(grid);
				System.out.println();
			}
			i++;
		}
		if (foundWinner == 0) {
			System.out.println("Draw!");
		}
		
		
	}

}
