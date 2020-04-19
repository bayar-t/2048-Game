//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/**
 *Name: Sergelenbayar Tsogtbaatar
 *Login: 
 *Date: 2/5/2016
 *File:Board.java
 *Sources of Help: CSE8b Textbook, Tutor
 *Description:
 *Contains the saveBoard, addRandomTile, and rotate methods. 
 *Has 2 Board constructors which either load and create a new board game based
 *on the passed parameters. 
 *Also contains play, isGameOver, and canMove methods. Play is the actual
 *method which runs the game. isGameOver checks if game is over.
 *canMove method checks whether a move is possible.
 *
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
   public final int NUM_START_TILES = 2;
   public final int TWO_PROBABILITY = 90;
   public final int GRID_SIZE;


   private final Random random;
   private int[][] grid;
   private int score;

   // Constructs a fresh board with random tiles
   public Board(int boardSize, Random random) {
      //edge case test
      if (boardSize < 2){
         boardSize = 4;
      }
      //initialize
      this.random = random; 
      this.GRID_SIZE = boardSize;
      this.score = 0;
      this.grid = new int[boardSize][boardSize];
      //runs through for loop and adds random tile
      //depending on the NUM_START_TILES final variable.
      for(int i = 0; i< this.NUM_START_TILES;i++){
         this.addRandomTile();
      }
   }

   // Construct a board based off of an input file
   public Board(String inputBoard, Random random) throws IOException {
      this.random = random; 
      //create scanner to scan file
      Scanner input = new Scanner(new File(inputBoard));
      this.GRID_SIZE = input.nextInt();
      //initialize instance variables
      this.score = input.nextInt();
      this.grid = new int[this.GRID_SIZE][this.GRID_SIZE];
      //runs through the 2d array and gets the grid from inputBoard
      //to this.grid
      for(int i =0;i<this.grid.length;i++){
         for(int k = 0; k<this.grid[i].length;k++){
            this.grid[i][k] = input.nextInt();
         }
      }
   }

   // Saves the current board to a file
   public void saveBoard(String outputBoard) throws IOException {
      //initialize
      int thisScore = this.score;
      int size = this.GRID_SIZE;
      PrintWriter output = new PrintWriter (new File(outputBoard));
      //prints size and score to outputBoard
      output.println(size);
      output.println(thisScore);
      //prints rest of the grid to outputboard
      for(int i =0;i< this.grid.length;i++){
         for(int k =0;k<this.grid[i].length;k++){
            output.print(this.grid[i][k]+ " ");
         }
         output.println();
      }
      //close PrintWriter
      output.close();
   }

   // Adds a random tile (of value 2 or 4) to a
   // random empty space on the board
   public void addRandomTile() {
      //declare/initialize variables.
      int count = 0;
      int secCount = 0;
      int location = 0;
      int value = 0;

      //loops through the 2d array to count empty tiles
      for(int i=0;i<this.grid.length;i++){
         for(int k = 0; k<this.grid[i].length;k++){
            if(this.grid[i][k] == 0){
               count = count +1;
            }
         }
      }
      //edge case if count is 0, then return
      if(count == 0){
         return;
      }
      //assign values to value and location
      location = random.nextInt(count);
      value = random.nextInt(100);
      //loops through the 2d array to assign 2 or 4
      //based on location value
      for(int i =0; i<this.grid.length;i++){
         for(int k = 0; k<this.grid[i].length;k++){
            //checks if the value on the grid is 0
            if(this.grid[i][k] == 0){

               if (secCount == location){
                  //assigns values based on value and
                  //final variable TWO_PROBABILITY
                  if (value < this.TWO_PROBABILITY){
                     this.grid[i][k] = 2;
                  }
                  else{
                     this.grid[i][k] = 4;
                  }
               }
               //adds count to secCount 
               secCount = secCount +1;
            }
         }
      }
   }

   // Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise.
   // If rotateClockwise == true, rotates the board clockwise , else rotates
   // the board counter-clockwise
   public void rotate(boolean rotateClockwise){
      //initializes copy of this.grid
      int[][] gridCopy = new int[this.GRID_SIZE][this.GRID_SIZE];
      //runs through 2d array and copies to gridCopy
      for(int i = 0; i<this.grid.length;i++){
         for(int k = 0;k<this.grid[i].length;k++){
            gridCopy[i][k] = this.grid[i][k];
         }
      }
      //Rotates clockwise if true
      if(rotateClockwise ==true){
         for(int i = 0; i<this.grid.length;i++){
            for(int k = 0; k<this.grid[i].length;k++){
               this.grid[i][k] = gridCopy[grid.length - 1 -k][i];
            }
         }
      }
      //Rotates counter clockwise if false
      else{
         for(int i = 0; i<this.grid.length;i++){
            for(int k = 0 ; k<this.grid[i].length;k++){
               this.grid[i][k] = gridCopy[k][grid[i].length -1-i];
            }
         }
      }

   }

   //Complete this method ONLY if you want to attempt at getting the extra credit
   //Returns true if the file to be read is in the correct format, else return
   //false
   public static boolean isInputFileCorrectFormat(String inputFile) {
      //The try and catch block are used to handle any exceptions
      //Do not worry about the details, just write all your conditions inside the
      //try block
      try {
         //write your code to check for all conditions and return true if it satisfies
         //all conditions else return false
         return true;
      } catch (Exception e) {
         return false;
      }
   }

   // No need to change this for PSA3
   // Performs a move Operation
   public boolean move(Direction direction) {
      if(direction.equals(Direction.LEFT)){
         if(this.canMove(direction)){
            //shift left all the way
            moveLeft();

            //loop through the 2d array
            for(int i =0; i<this.grid.length; i++){
               for(int k = 1; k<this.grid.length; k++){
                  if(this.grid[i][k-1] == this.grid[i][k]){
                     this.grid[i][k-1] *= 2;
                     this.score = this.score + (this.grid[i][k-1]);
                     //temporary assignment to 0 until moveLeft called again
                     this.grid[i][k] = 0;
                  }
               }
            }
            //shift left all the way again
            moveLeft();
         }
         return true;
      }



      else if(direction.equals(Direction.UP)){
         if(this.canMove(direction)){
            //rotates counter clockwise and will rotate back later
            this.rotate(false);
            moveLeft();
            //same code as moving left, but with one counter clock wise
            //rotation.
            for(int i =0; i<this.grid.length; i++){
               for(int k = 1; k<this.grid.length; k++){
                  if(this.grid[i][k-1] == this.grid[i][k]){
                     this.grid[i][k-1] *= 2;
                     this.score = this.score + (this.grid[i][k-1]);
                     this.grid[i][k] = 0;
                  }
               }
            }
            moveLeft();
            //rotate back to normal
            this.rotate(true);
         }

      }
      else if(direction.equals(Direction.RIGHT)){
         if(this.canMove(direction)){
            //rotates counter clockwise twice, will rotate back later
            this.rotate(false);
            this.rotate(false);
            //same code as moving left, but with 2 Counter clockwise
            //rotations
            for(int i =0; i<this.grid.length; i++){
               for(int k = 1; k<this.grid.length; k++){
                  if(this.grid[i][k-1] == this.grid[i][k]){
                     this.grid[i][k-1] *= 2;
                     this.score = this.score + (this.grid[i][k-1]);
                     this.grid[i][k] = 0;
                  }
               }
            }
            moveLeft();
            //rotate back to normal
            this.rotate(true);
            this.rotate(true);
         }
      }
      else{
         if(this.canMove(direction)){
            //rotate clockwise
            this.rotate(true);
            //same code as moving left, but with 1 clockwise rotation
            for(int i =0; i<this.grid.length; i++){
               for(int k = 1; k<this.grid.length; k++){
                  if(this.grid[i][k-1] == this.grid[i][k]){
                     this.grid[i][k-1] *= 2;
                     this.score = this.score + (this.grid[i][k-1]);
                     this.grid[i][k] = 0;
                  }
               }
            }
            moveLeft();
            //rotate back to normal
            this.rotate(false);
         }
      }



      return true;
   }

   public void moveLeft(){
      //run through rows
      for (int i = 0; i<this.grid.length; i++){
         //create copy of row
         int [] row = new int[this.grid.length];
         //run through columns
         for (int k=0; k<this.grid.length; k++){

            if(this.grid[i][k] != 0){
               int curr = 0;
               //pushes everything as left as possible
               while (row[curr] !=0){
                  curr++;
               }
               row[curr] = this.grid[i][k];
            }
         }
         this.grid[i] = row;
      }
   }
   // No need to change this for PSA3
   // Check to see if we have a game over
   public boolean isGameOver() {
      //check if all canMove is false for each direction, and if they are all 
      //false then return true for isGameOver.
      if(this.canMove(Direction.UP) == false &&
            this.canMove(Direction.DOWN) == false &&
            this.canMove(Direction.LEFT) == false &&
            this.canMove(Direction.RIGHT) == false){
         return true;
      }
      else{
         //return false for isGameOver
         return false;
      }
   }

   // No need to change this for PSA3
   // Determine if we can move in a given direction
   public boolean canMove(Direction direction) {
      //check which direction it is
      if(direction == Direction.DOWN){

         //runs through 2d array
         for(int i = 0; i <this.grid.length-1;i++){
            for(int k = 0;k<this.grid.length;k++){
               //edge case to check if grid[i][k] is bigger than 0
               if(this.grid[i][k] > 0){
                  //compares current position to current position + 1 to column
                  //and compares current position +1 to column to 0
                  //if either is true then it returns true
                  if(this.grid[i][k] == this.grid[i+1][k] ||
                        this.grid[i+1][k] == 0){
                     //returns true if it can move
                     return true;
                  }
               }
            }
         }
         return false;
      }

      else if(direction == Direction.UP) {
         //run through 2d array
         for(int i = this.grid.length-1; i > 0; i--){
            for( int k = 0; k<this.grid.length; k++){
               //edge case
               if(this.grid[i][k] > 0){
                  //same thing as above, but different values of i in grid[i][k]
                  if(this.grid[i][k] == this.grid[i-1][k] ||
                        this.grid[i-1][k] == 0){
                     //returns true if it can move
                     return true;
                  }
               }
            }
         }
         return false;
      }
      else if(direction == Direction.RIGHT){
         //run through 2d array
         for(int k = 0; k<this.grid.length-1;k++){
            for(int i = this.grid.length-1; i > 0;i--){
               if(this.grid[i][k] > 0){
                  //same thing as above, but uses k since it's checking right
                  if(this.grid[i][k] == this.grid[i][k+1] ||
                        this.grid[i][k+1] == 0){
                     //returns true if it can move
                     return true;
                  }
               }
            }
         }
         return false;
      }
      else if(direction == Direction.LEFT){
         //run through 2d array
         for(int k = 1; k<this.grid.length;k++){
            for(int i = 0 ; i< this.grid.length-1; i++){
               if(this.grid[i][k] > 0){
                  //same thing as above, but uses k and is (k-1) instead of
                  //(k+1)
                  if(this.grid[i][k] == this.grid[i][k-1] ||
                        this.grid[i][k-1] == 0){
                     //returns true if it can move
                     return true;
                  }
               }
            }
         }
         return false;
      }
      return true;
   }

   // Return the reference to the 2048 Grid
   public int[][] getGrid() {
      return grid;
   }

   // Return the score
   public int getScore() {
      return score;
   }

   @Override
      public String toString() {
         StringBuilder outputString = new StringBuilder();
         outputString.append(String.format("Score: %d\n", score));
         for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
               outputString.append(grid[row][column] == 0 ? "    -" :
                     String.format("%5d", grid[row][column]));

            outputString.append("\n");
         }
         return outputString.toString();
      }
}
