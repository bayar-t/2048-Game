//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//
/*Name:Sergelenbayar Tsogtbaatar
 *Login:
 *Date:2/5/2016
 *File:GameManager.java
 *Sources of Help:CSE8b textbook, tutor
 *Description of Program: 
 *This program contains the play() method. Uses 2 Constructors
 *where one creates a new board while the other constructor
 *loads a board.
 */
import java.util.*;
import java.io.*;

public class GameManager {
   // Instance variables
   private Board board; // The actual 2048 board
   private String outputFileName; // File to save the board to when exiting

   // GameManager Constructor
   // Generate new game
   public GameManager(int boardSize, String outputBoard, Random random) {
      System.out.println("Generating a New Board");
      //initialize constructor
      board = new Board(boardSize, random);
      outputFileName = outputBoard;
   }

   // GameManager Constructor
   // Load a saved game
   public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
      System.out.println("Loading Board from " + inputBoard);
      //initialize constructor
      board = new Board(inputBoard, random);
      outputFileName = outputBoard;

   }

   // Main play loop
   // Takes in input from the user to specify moves to execute
   // valid moves are:
   //      k - Move up
   //      j - Move Down
   //      h - Move Left
   //      l - Move Right
   //      q - Quit and Save Board
   //
   //  If an invalid command is received then print the controls
   //  to remind the user of the valid moves.
   //
   //  Once the player decides to quit or the game is over,
   //  save the game board to a file based on the outputFileName
   //  string that was set in the constructor and then return
   //
   //  If the game is over print "Game Over!" to the terminal
   public void play() throws IOException {
      //initialize variables
      String move = null;
      Direction directionReal;
      //checks if game is over.
      while(!this.board.isGameOver()){
         //prints control and prompts for user to enter a move.
         this.printControls();
         String game = new String(board.toString());
         System.out.println(game);
         System.out.println("Please enter one of the controls listed above");
         Scanner direction = new Scanner(System.in);
         move = direction.next();
         //checks if moves are valid and if not, prints out controls and
         //prompts user for valid move.
         while( move.equals("k") == false &&
               move.equals("j") == false &&
               move.equals("h") == false &&
               move.equals("l") == false &&
               move.equals("q") == false){
            this.printControls();
            System.out.println(game);
            Scanner validDirection = new Scanner(System.in);
            move = validDirection.next();
         }
         //sets direction based on what move they entered
         if(move.equals("k")){
            directionReal = Direction.UP;
         }
         else if(move.equals("j")){
            directionReal = Direction.DOWN;
         }
         else if(move.equals("h")){
            directionReal = Direction.LEFT;
         }
         else if(move.equals("l")){
            directionReal = Direction.RIGHT;
         }
         else{
            this.board.saveBoard(outputFileName);
            return;
         }
         //if the board move can move, then moves it and
         //adds a random tile.
         if(this.board.canMove(directionReal) ==true){
            this.board.move(directionReal);
            this.board.addRandomTile();
         }
         //prints this if move is invalid then goes through
         //while condition again.
         else{
            System.out.println("Move is invalid. Please try again");
         }
      }
      //if it's game over, then saves and returns
      System.out.println("Game Over!");
      this.board.saveBoard(outputFileName);
      return;
   }

   // Print the Controls for the Game
   private void printControls() {
      System.out.println("  Controls:");
      System.out.println("    k - Move Up");
      System.out.println("    j - Move Down");
      System.out.println("    h - Move Left");
      System.out.println("    l - Move Right");
      System.out.println("    q - Quit and Save Board");
      System.out.println();
   }
}
