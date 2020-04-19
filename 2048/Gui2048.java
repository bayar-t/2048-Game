/** Gui2048.java */
/** PSA8 Release */
/*Name:Sergelenbayar Tsogtbaatar
 * Login:cs8bwatz
 * Date:3/3/2016
 * File:Gui2048.java
 * Sources of Help:CSE8b textbook, PSA 8 doc
 * Description of Program: 
 * Implements the GUI of 2048 using JavaFX 8.
 */
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

public class Gui2048 extends Application
{
   private String outputBoard; // The filename for where to save the Board
   private Board board; // The 2048 Game Board

   private static final int TILE_WIDTH = 106;

   private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
   private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
   //(128, 256, 512)
   private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
   //(1024, 2048, Higher)

   // Fill colors for each of the Tile values
   private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
   private static final Color COLOR_2 = Color.rgb(238, 228, 218);
   private static final Color COLOR_4 = Color.rgb(237, 224, 200);
   private static final Color COLOR_8 = Color.rgb(242, 177, 121);
   private static final Color COLOR_16 = Color.rgb(245, 149, 99);
   private static final Color COLOR_32 = Color.rgb(246, 124, 95);
   private static final Color COLOR_64 = Color.rgb(246, 94, 59);
   private static final Color COLOR_128 = Color.rgb(237, 207, 114);
   private static final Color COLOR_256 = Color.rgb(237, 204, 97);
   private static final Color COLOR_512 = Color.rgb(237, 200, 80);
   private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
   private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
   private static final Color COLOR_OTHER = Color.BLACK;
   private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

   private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
   // For tiles >= 8

   private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
   // For tiles < 8

   private GridPane pane;

   /** Add your own Instance Variables here */
   //declare/initialize instance variables
   private int value = 0;
   private boolean checkGameOver = false;

   //Class Name: myKeyHandler
   //Purpose: handles events such as key strokes
   //Parameters:N/A
   //Return:N/A
   private class myKeyHandler implements EventHandler<KeyEvent>
   {
      @Override
         //Method Name: handle
         //Purpose: handles the keystrokes such as up/down/left/right
         //Parameters: KeyEvent e
         //Return: N/A
         public void handle(KeyEvent e){
            switch(e.getCode()){
               //handles the up key
               case UP: if(board.canMove(Direction.UP)){
                           board.move(Direction.UP);
                           System.out.println("Moving Up");
                           board.addRandomTile();
                           updateBoard();
                           //check if game is over after updating board
                           if(board.isGameOver() == true){
                              checkGameOver = true;
                              gameOver();
                              break;
                           }
                           break;
                        }
                        //if board can't move, prints out statement
                        else if(checkGameOver == false){
                           System.out.println("Invalid move"); break;
                        }
                        break;
                        //handles the down key
               case DOWN: if(board.canMove(Direction.DOWN)){
                             board.move(Direction.DOWN);
                             System.out.println("Moving Down");
                             board.addRandomTile();
                             updateBoard();
                             //check if game is over after updating board
                             if(board.isGameOver() == true){
                                checkGameOver= true;
                                gameOver();
                                break;
                             }
                             break;
                          }
                          //if board can't move, prints out statement
                          else if(checkGameOver == false){
                             System.out.println("Invalid move"); break;
                          }
                          break;
                          //handles the left key
               case LEFT: if(board.canMove(Direction.LEFT)){
                             board.move(Direction.LEFT);
                             System.out.println("Moving Left"); 
                             board.addRandomTile();
                             updateBoard();
                             //check if game is over after updating board
                             if(board.isGameOver() == true){
                                checkGameOver = true;
                                gameOver();
                                break;
                             }
                             break;
                          }
                          //if board can't move, prints out statement
                          else if(checkGameOver==false){
                             System.out.println("Invalid move"); break;
                          }
                          break;
                          //handles the right key
               case RIGHT: if(board.canMove(Direction.RIGHT)){
                              board.move(Direction.RIGHT);
                              System.out.println("Moving Right");
                              board.addRandomTile();
                              updateBoard();
                              //check if game is over after update
                              if(board.isGameOver() == true){
                                 checkGameOver = true;
                                 gameOver();
                                 break;
                              }
                              break;
                           }
                           //if board can't move, prints out statement
                           else if(checkGameOver==false){
                              System.out.println("Invalid move"); break;
                           }
                           break;
                           //handles the rotation of board
               case R: if(checkGameOver == false){
                          board.rotate(true);
                          updateBoard();
                          System.out.println("Rotating Board Clockwise"); break;
                       }
                       else{
                          break;
                       }
                       //saved the board using try/catch to catch exception
               case S: try{
                          System.out.println("Saving Board");
                          board.saveBoard(outputBoard);
                       } catch (IOException i) {
                          System.out.println("saveBoard threw an Exception");
                          break;
                       }

            }
         }
   }
   @Override
      //Method Name:start
      //Purpose:initializes the game
      //Parameter:Stage primaryStage
      //Return:N/A
      public void start(Stage primaryStage)
      {
         // Process Arguments and Initialize the Game Board
         processArgs(getParameters().getRaw().toArray(new String[0]));

         // Create the pane that will hold all of the visual objects
         pane = new GridPane();
         pane.setAlignment(Pos.CENTER);
         pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
         pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
         // Set the spacing between the Tiles
         pane.setHgap(15); 
         pane.setVgap(15);

         /** Add your Code for the GUI Here */
         //sets window width/height at start
         primaryStage.setMinWidth(this.board.GRID_SIZE*130);
         primaryStage.setMinHeight(this.board.GRID_SIZE*150);
         //sets the title text
         Text title = new Text();
         title.setText("2048");
         title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
         //sets the score text
         Text score = new Text();
         score.setText("Score: " + this.board.getScore());
         score.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 28));
         //adds score/title to pane
         pane.add(title, 0, 0, 2, 1);
         pane.add(score, this.board.GRID_SIZE -2, 0, 2, 1);
         //aligns the title/score to center
         GridPane.setHalignment(title, HPos.CENTER);
         GridPane.setValignment(title, VPos.CENTER);
         GridPane.setHalignment(score, HPos.CENTER);
         GridPane.setValignment(score, VPos.CENTER);
         //add pane to scene
         Scene scene = new Scene(pane);
         //sets the name of primaryStage
         primaryStage.setTitle("Gui2048");
         //add scene to stage
         primaryStage.setScene(scene);
         //show stage
         primaryStage.show();
         //create the board and event handler
         this.createSquare(this.board.GRID_SIZE, pane);
         scene.setOnKeyPressed(new myKeyHandler());

      }
   /** Add your own Instance Methods Here */
   //Method Name:createSquare
   //Purpose:creates and initializes the initial board
   //Parameters:int value, GridPane pane
   //Return:N/A
   public void createSquare(int bLength, GridPane pane){
      //nested for loop for to itirate through board
      for(int i = 0;i<board.GRID_SIZE;i++){
         for(int k = 0; k<board.GRID_SIZE; k++){
            //initialize/set tiles
            Rectangle tile = new Rectangle();
            Text tileValue = new Text();
            tile.setWidth(this.TILE_WIDTH);
            tile.setHeight(this.TILE_WIDTH);
            tile.setFill(this.COLOR_EMPTY);
            this.value = board.getGrid()[i][k];
            //sets the initial values on board GUI which can be 2 or 4
            if(this.value==2){
               tile.setFill(this.COLOR_2);
               tileValue.setText("2");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_DARK);
               updateBoard();
            }
            else if(this.value ==4){
               tile.setFill(this.COLOR_4);
               tileValue.setText("4");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_DARK);
               updateBoard();
            }
            //add rectangle and tile value to pane
            pane.add(tile, i, k+1);
            pane.add(tileValue, i, k+1);
            //centers the tile value
            GridPane.setHalignment(tileValue, HPos.CENTER);
            GridPane.setValignment(tileValue, VPos.CENTER);
            updateBoard();
         }
      }
   }
   //Method Name: gameOver
   //Purpose:overlays the game over rectangle over board GUI
   //Parameters:N/A
   //Return:N/A
   private void gameOver(){
      //initialize/set rectangle that is to be covering the board
      Rectangle gameOver = new Rectangle();
      Text gameOverText = new Text();
      gameOver.setFill(COLOR_GAME_OVER);
      gameOver.setWidth(pane.getWidth()*2);
      gameOver.setHeight(pane.getHeight()*2);
      gameOverText.setText("Game Over!");
      gameOverText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,60));
      //add nodes to pane
      pane.add(gameOver, 1, 0, this.board.GRID_SIZE, this.board.GRID_SIZE);
      pane.add(gameOverText,0,0, this.board.GRID_SIZE, this.board.GRID_SIZE);
      //align rectangle and text to center
      GridPane.setHalignment(gameOver, HPos.CENTER);
      GridPane.setHalignment(gameOverText, HPos.CENTER);
      GridPane.setValignment(gameOver, VPos.CENTER);
      GridPane.setValignment(gameOverText, VPos.CENTER);
   }

   //Method Name:updateBoard
   //Purpose:updates board
   //Parameters:N/A
   //Return:N/A
   private void updateBoard() {
      //clear board so text dont stack
      pane.getChildren().clear();
      //iterate through board
      for(int i = 0; i<this.board.GRID_SIZE;i++){
         for(int k =0; k<this.board.GRID_SIZE;k++){
            //checks each tile and sets text/font
            this.value = this.board.getGrid()[i][k];
            Rectangle tile = new Rectangle();
            Text tileValue = new Text();
            //set width/height of tile
            tile.setWidth(this.TILE_WIDTH);
            tile.setHeight(this.TILE_WIDTH);

            //checks each tile and sets the fill/font size/font based on
            //value.
            if(this.value == 0){
               tile.setFill(this.COLOR_EMPTY);
            }
            else if(this.value == 2) {
               tile.setFill(this.COLOR_2);
               tileValue.setText("2");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_DARK);
            }

            else if(this.value == 4){
               tile.setFill(this.COLOR_4);
               tileValue.setText("4");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_DARK);
            }

            else if(this.value == 8){
               tile.setFill(this.COLOR_8);
               tileValue.setText("8");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }

            else if(this.value == 16){
               tile.setFill(this.COLOR_8);
               tileValue.setText("16");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            else if(this.value == 32){
               tile.setFill(this.COLOR_32);
               tileValue.setText("32");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            else if(this.value == 64){
               tile.setFill(this.COLOR_64);
               tileValue.setText("64");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_LOW));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            else if(this.value == 128){
               tile.setFill(this.COLOR_128);
               tileValue.setText("128");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_MID));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            else if(this.value == 256){
               tile.setFill(this.COLOR_256);
               tileValue.setText("256");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_MID));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            else if(this.value == 512){
               tile.setFill(this.COLOR_512);
               tileValue.setText("512");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_MID));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            else if(this.value == 1024){
               tile.setFill(this.COLOR_1024);
               tileValue.setText("1024");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_HIGH));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            else if(this.value == 2048){
               tile.setFill(this.COLOR_2048);
               tileValue.setText("2048");
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_HIGH));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            //if value>2048 then it sets it to a generic font
            else{
               tile.setFill(this.COLOR_OTHER);
               tileValue.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,
                        this.TEXT_SIZE_HIGH));
               tileValue.setFill(this.COLOR_VALUE_LIGHT);
            }
            //add tile/rectangle to pane
            pane.add(tile, k, i+1);
            pane.add(tileValue, k, i+1);
            //centers tileValue
            GridPane.setHalignment(tileValue, HPos.CENTER);
            GridPane.setValignment(tileValue, VPos.CENTER);
         }
      }
      //sets the title
      Text title = new Text();
      title.setText("2048");
      title.setFont(Font.font("Comic Sans", FontWeight.BOLD, 40));
      //updates the score along with board
      Text score = new Text();
      score.setText("Score: " + board.getScore());
      score.setFont(Font.font("Comic Sans", FontWeight.BOLD, 28));
      //add nodes to pane
      pane.add(title, 0, 0, 2, 1);
      pane.add(score, this.board.GRID_SIZE -2, 0, 2, 1);
      //align nodes to center
      GridPane.setHalignment(title, HPos.CENTER);
      GridPane.setHalignment(score, HPos.CENTER);
   }

   /** DO NOT EDIT BELOW */

   // The method used to process the command line arguments
   private void processArgs(String[] args)
   {
      String inputBoard = null;   // The filename for where to load the Board
      int boardSize = 0;          // The Size of the Board

      // Arguments must come in pairs
      if((args.length % 2) != 0)
      {
         printUsage();
         System.exit(-1);
      }

      // Process all the arguments 
      for(int i = 0; i < args.length; i += 2)
      {
         if(args[i].equals("-i"))
         {   // We are processing the argument that specifies
            // the input file to be used to set the board
            inputBoard = args[i + 1];
         }
         else if(args[i].equals("-o"))
         {   // We are processing the argument that specifies
            // the output file to be used to save the board
            outputBoard = args[i + 1];
         }
         else if(args[i].equals("-s"))
         {   // We are processing the argument that specifies
            // the size of the Board
            boardSize = Integer.parseInt(args[i + 1]);
         }
         else
         {   // Incorrect Argument 
            printUsage();
            System.exit(-1);
         }
      }

      // Set the default output file if none specified
      if(outputBoard == null)
         outputBoard = "2048.board";
      // Set the default Board size if none specified or less than 2
      if(boardSize < 2)
         boardSize = 4;

      // Initialize the Game Board
      try{
         if(inputBoard != null)
            board = new Board(inputBoard, new Random());
         else
            board = new Board(boardSize, new Random());
      }
      catch (Exception e)
      {
         System.out.println(e.getClass().getName() + 
               " was thrown while creating a " +
               "Board from file " + inputBoard);
         System.out.println("Either your Board(String, Random) " +
               "Constructor is broken or the file isn't " +
               "formated correctly");
         System.exit(-1);
      }
   }

   // Print the Usage Message 
   private static void printUsage()
   {
      System.out.println("Gui2048");
      System.out.println("Usage:  Gui2048 [-i|o file ...]");
      System.out.println();
      System.out.println("  Command line arguments come in pairs of the "+ 
            "form: <command> <argument>");
      System.out.println();
      System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
            "should be loaded");
      System.out.println();
      System.out.println("  -o [file]  -> Specifies a file that should be " + 
            "used to save the 2048 board");
      System.out.println("                If none specified then the " + 
            "default \"2048.board\" file will be used");  
      System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
            "board if an input file hasn't been"); 
      System.out.println("                specified.  If both -s and -i" + 
            "are used, then the size of the board"); 
      System.out.println("                will be determined by the input" +
            " file. The default size is 4.");
   }
}
