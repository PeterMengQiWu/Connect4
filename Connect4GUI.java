/*
 * [Connect4GUI.java]
 * This program will be used to play Connect 4
 * Author: Janice Ho and Peter Wu
 * Due Date: December 3, 2015
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//have border around the connect 4 game board
import javax.swing.BorderFactory;

public class Connect4GUI extends JFrame implements ActionListener { 
  
  /******************** ~CREATING COMPONENTS~ ********************/
  
  static int count = 0; //counter to see which player makes the move
  final int MAXROW = 6; //constant variable for maximum row the grid needs
  final int MAXCOL = 7; //constant variable for minimum column the grid needs
  final int MAXDEPTH = 4;//constant variable for maximum depth for ai to look ahead
  int playerscore = 0;//variable for recording player's score
  int aiscore = 0;//variable for recording ai's score
  int AINextMove = -1;//variable for recording ai's move after evaluating.
  int[][] grid = new int[MAXROW][MAXCOL];//2D array for digital grid
  
  /********** 1. PANELS **********/
  
  JPanel welcomePANEL = new JPanel(); //creates the panel for the welcome, instruction, and goal label (box layout)
  JPanel startorendPANEL = new JPanel(); //creates the panel for the start and exit button (flow layout)
  
  JPanel gamemodePANEL = new JPanel(); //creates the panel to get the game mode that the user prefers to play in (flow layout)
  
  JPanel levelPANEL = new JPanel(); //creates the panel to get the level that the user prefers to play in (flow layout)
  
  JPanel gofirstPANEL = new JPanel(); //creates the panel to see if the user would prefer to go first in connect 4 or not (flow layout)
  
  static JPanel boardPANEL = new JPanel(); //creates the panel for the connect 4 game board (grid layout)
  static JPanel pvspbuttonPANEL = new JPanel(); //creates the panel for the buttons that will drop the coloured disks when playing player vs player (flow layout)
  static JPanel pvsaibuttonPANEL = new JPanel(); //creates the panel for the buttons that will drop the coloured disks when playing player vs ai (flow layout)
  
  /********** 2. BUTTONS **********/
  
  // start and exit button to start game or exit game
  JButton startBUTTON = new JButton("New Game"); //creates a button called "startBUTTON" to start a new connect 4 game
  JButton exitBUTTON = new JButton("Exit Program"); //creates a button called "exitBUTTON" that allows the user to exit the game
  
  // p vs p OR p vs ai
  JButton pvspBUTTON = new JButton("Player vs Player");
  JButton pvsaiBUTTON = new JButton("Player vs Computer");
  
  // level of difficulty
  JButton easyBUTTON = new JButton("Easy"); //puts disk randomly
  JButton mediumBUTTON = new JButton("Medium"); //puts disk at winning space
  JButton hardBUTTON = new JButton("Hard"); //tries to block the opponent too
  
  // yes or no button for if the user wants to go first or not
  JButton yesBUTTON = new JButton("Yes"); //wants to start first
  JButton noBUTTON = new JButton("No"); //doesn't want to start first
  
  // player vs player buttons to input disks onto specific columns
  JButton pvspONE = new JButton("    1    "); //creates a button called "pvspONE" to insert a disk into column #1
  JButton pvspTWO = new JButton("    2    "); //creates a button called "pvspTWO" to insert a disk into column #2
  JButton pvspTHREE = new JButton("    3    "); //creates a button called "pvspTHREE" to insert a disk into column #3
  JButton pvspFOUR = new JButton("    4    "); //creates a button called "pvspFOUR" to insert a disk into column #4
  JButton pvspFIVE = new JButton("    5    "); //creates a button called "pvspFIVE" to insert a disk into column #5
  JButton pvspSIX = new JButton("    6    "); //creates a button called "pvspSIX" to insert a disk into column #6
  JButton pvspSEVEN = new JButton("    7    "); //creates a button called "pvspSEVEN" to insert a disk into column #7
  
  // player vs computer buttons to input disks onto specific columns
  JButton pvsaiONE = new JButton("     1    "); //creates a button called "pvsaiONE" to insert a disk into column #1
  JButton pvsaiTWO = new JButton("     2    "); //creates a button called "pvsaiTWO" to insert a disk into column #2
  JButton pvsaiTHREE = new JButton("     3    "); //creates a button called "pvsaiTHREE" to insert a disk into column #3
  JButton pvsaiFOUR = new JButton("     4    "); //creates a button called "pvsaiFOUR" to insert a disk into column #4
  JButton pvsaiFIVE = new JButton("     5    "); //creates a button called "pvsaiFIVE" to insert a disk into column #5
  JButton pvsaiSIX = new JButton("     6    "); //creates a button called "pvsaiSIX" to insert a disk into column #6
  JButton pvsaiSEVEN = new JButton("     7    "); //creates a button called "pvsaiSEVEN" to insert a disk into column #7
  
  /********** 3. LABELS **********/
  
  JLabel welcomeLABEL = new JLabel("Welcome to Connect 4!"); //creates a label called "welcomeLABEL" to welcome the user
  JLabel instructionsLABEL = new JLabel("INSTRUCTIONS: click on the button under the column that you would like to place your disk to put it there."); //creates a label called "instructionsLABEL" that informs the user about the number 0
  JLabel goalLABEL = new JLabel("GOAL: place 4 of your disks in a row, whether it is vertically, horizontally, or diagonally."); //creates a label called "goalLABEL" that informs the user about the number 1
  
  JLabel askgamemodeLABEL = new JLabel("Which game mode would you like to play in?"); //creates a label called "askgamemodeLABEL" to ask the user which game mode they would like to be in
  
  JLabel asklevelLABEL = new JLabel("Choose a level of difficulty"); //creates a label called "asklevelLABEL" to ask the user which level of difficulty they would want to play against the ai
  
  JLabel askwhotogofirstLABEL = new JLabel("Would you like to go first?"); //creates a label called "askwhotogofirstLABEL" to see if the player wants to go first against the ai or not
  
  //We will be using a 2d label array for the connect 4 board
  JLabel[][] connect4 = new JLabel[6][7];
  
  /******************** ~CONSTRUCTOR~ ********************/
  //This part of the program runs once when the program begins
  //This is where the GUInumbers should be configured.
  
  public Connect4GUI() { 
    setTitle("CONNECT 4"); //Set the title of the window shown for this program
    setSize(700, 500); //Set the dimensions of the window shown for this program
    
    for(int r = 0; r < MAXROW; r++){//for loop for initializing the grid with 0s(grid is clean)
      for(int c = 0; c < MAXCOL; c++){
        grid[r][c] = 0;
      }
    }
    
    /********** 1. LAYOUTS **********/
    
    BoxLayout welcomeLAYOUT = new BoxLayout(welcomePANEL, BoxLayout.Y_AXIS); //creates a box layout for the labels on welcomePANEL
    FlowLayout startorendLAYOUT = new FlowLayout(); //creates a flow layout for the buttons on startorendPANEL
    
    FlowLayout gamemodeLAYOUT = new FlowLayout(); //creates a flow layout for the label on gamemodePANEL
    
    FlowLayout levelLAYOUT = new FlowLayout(); //creates a flow layout for the buttons on levelPANEL
    
    FlowLayout gofirstLAYOUT = new FlowLayout(); //creates a flow layout for the buttons on gofirstPANEL
    
    GridLayout boardLAYOUT = new GridLayout(6,7); //creates a grid layout for the connect 4 board on boardPANEL
    FlowLayout pvspbuttonLAYOUT = new FlowLayout(); //creates a flow layout for the buttons on pvspbuttonPANEL
    FlowLayout pvsaibuttonLAYOUT = new FlowLayout(); //creates a flow layout for the buttons on pvsaibuttonPANEL
    
    /********** 2. SETS THE LAYOUT AND PANELS **********/
    
    // sets the frame layout
    
    setLayout(welcomeLAYOUT); //sets welcome - box layout
    setLayout(startorendLAYOUT); //sets start or end - flow layout
    
    setLayout(gamemodeLAYOUT); //sets game mode - flow layout
    
    setLayout(levelLAYOUT); //asks level - flow layout
    
    setLayout(gofirstLAYOUT); //asks who to go first - flow layout
    
    setLayout(boardLAYOUT); //sets connect 4 board - grid layout
    setLayout(pvspbuttonLAYOUT); //sets connect 4 board buttons (player vs player) - flow layout
    setLayout(pvsaibuttonLAYOUT); //sets connect 4 board buttons (player vs ai) - flow layout
    
    // sets the panel layout
    welcomePANEL.setLayout(welcomeLAYOUT); //layout for welcome, instruction, and goal labels
    startorendPANEL.setLayout(startorendLAYOUT); //layout for buttons start and end
    gamemodePANEL.setLayout(gamemodeLAYOUT); //layout for the game modes
    levelPANEL.setLayout(levelLAYOUT); //layout for the types of level of difficulties
    gofirstPANEL.setLayout(gofirstLAYOUT); //layout to see who goes first
    boardPANEL.setLayout(boardLAYOUT); //layout for connect 4 board
    pvspbuttonPANEL.setLayout(pvspbuttonLAYOUT); //layout for connect 4 board buttons (player vs player)
    pvsaibuttonPANEL.setLayout(pvsaibuttonLAYOUT); //layout for connect 4 board buttons (player vs ai)
    
    /********** 3. ADDING LABELS OR BUTTONS TO PANELS **********/
    
    ///// welcomePANEL /////
    
    welcomePANEL.add(welcomeLABEL);
    welcomePANEL.add(instructionsLABEL);
    welcomePANEL.add(goalLABEL);
    
    ///// startorendPANEL /////
    
    startorendPANEL.add(startBUTTON);
    startorendPANEL.add(exitBUTTON);
    
    ///// gamemodePANEL /////
    
    gamemodePANEL.add(askgamemodeLABEL); //only visible when user presses startBUTTON for a new game
    gamemodePANEL.add(pvspBUTTON); //adds player vs player button
    gamemodePANEL.add(pvsaiBUTTON); //adds player vs computer button
    gamemodePANEL.setVisible(false); //only visible when user presses startBUTTON for a new game
    
    ///// levelPANEL /////
    
    levelPANEL.add(asklevelLABEL);
    levelPANEL.add(easyBUTTON);
    levelPANEL.add(mediumBUTTON);
    levelPANEL.add(hardBUTTON);
    levelPANEL.setVisible(false); //only visible when user presses pvsaiBUTTON to play a new game with the computer
    
    ///// gofirstPANEL /////
    
    gofirstPANEL.add(askwhotogofirstLABEL);
    gofirstPANEL.add(yesBUTTON);
    gofirstPANEL.add(noBUTTON);
    gofirstPANEL.setVisible(false); //only visible when user presses pvsaiBUTTON to play a new game with the computer
    
    ///// boardPANEL/////
    
    //put some information onto the 2d array (connect 4 board) so it has information to show
    
    for (int row = 0; row < 6; row++){
      for (int column = 0; column < 7; column++){
        connect4[row][column] = new JLabel("                      ");
        connect4[row][column].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
      }
    }
    
    //adds the connect 4 board to the boardPANEL
    for (int row = 0; row < 6; row++){
      for (int column = 0; column < 7; column++){
        boardPANEL.add(connect4[row][column]);
      }
    }
    
    boardPANEL.setVisible(false); //only visible when the user has gone through the process of all questions   
    
    ///// pvspbuttonPANEL /////
    
    pvspbuttonPANEL.add(pvspONE);
    pvspbuttonPANEL.add(pvspTWO);
    pvspbuttonPANEL.add(pvspTHREE);
    pvspbuttonPANEL.add(pvspFOUR);
    pvspbuttonPANEL.add(pvspFIVE);
    pvspbuttonPANEL.add(pvspSIX);
    pvspbuttonPANEL.add(pvspSEVEN);
    pvspbuttonPANEL.setVisible(false); //only visible when the user has gone through the process of all questions   
    
    ///// pvsaibuttonPANEL /////
    
    pvsaibuttonPANEL.add(pvsaiONE);
    pvsaibuttonPANEL.add(pvsaiTWO);
    pvsaibuttonPANEL.add(pvsaiTHREE);
    pvsaibuttonPANEL.add(pvsaiFOUR);
    pvsaibuttonPANEL.add(pvsaiFIVE);
    pvsaibuttonPANEL.add(pvsaiSIX);
    pvsaibuttonPANEL.add(pvsaiSEVEN);
    pvsaibuttonPANEL.setVisible(false); //only visible when the user has gone through the process of all questions 
    
    /********** 4. ADDING BUTTONS **********/
    //adds the buttons to this program making it work
    
    startBUTTON.addActionListener(this); //adds an action listener to the dimension button
    exitBUTTON.addActionListener(this); //adds an action listener to the exit button
    
    pvspBUTTON.addActionListener(this); //adds an action listener to the pvsp button
    pvsaiBUTTON.addActionListener(this); //adds an action listener to the pvsai button
    
    easyBUTTON.addActionListener(this); //adds an action listener to the easy button
    mediumBUTTON.addActionListener(this); //adds an action listener to the medium button
    hardBUTTON.addActionListener(this); //adds an action listener to the hard button
    
    yesBUTTON.addActionListener(this); //adds an action listener to the yes button
    noBUTTON.addActionListener(this); //adds an action listener to the no button
    
    //player vs player buttons
    pvspONE.addActionListener(this); //adds an action listener to the one button
    pvspTWO.addActionListener(this); //adds an action listener to the two button
    pvspTHREE.addActionListener(this); //adds an action listener to the three button
    pvspFOUR.addActionListener(this); //adds an action listener to the four button
    pvspFIVE.addActionListener(this); //adds an action listener to the five button
    pvspSIX.addActionListener(this); //adds an action listener to the six button
    pvspSEVEN.addActionListener(this); //adds an action listener to the seven button
    
    //player vs ai buttons
    pvsaiONE.addActionListener(this); //adds an action listener to the one button
    pvsaiTWO.addActionListener(this); //adds an action listener to the two button
    pvsaiTHREE.addActionListener(this); //adds an action listener to the three button
    pvsaiFOUR.addActionListener(this); //adds an action listener to the four button
    pvsaiFIVE.addActionListener(this); //adds an action listener to the five button
    pvsaiSIX.addActionListener(this); //adds an action listener to the six button
    pvsaiSEVEN.addActionListener(this); //adds an action listener to the seven button
    
    /********** 5. ADDING PANELS AND DISPLAY FRAME **********/
    add(welcomePANEL); //adds the welcomePANEL to the frame to display it on the window
    add(startorendPANEL); //adds the startorendPANEL to the frame to display it on the window
    
    add(gamemodePANEL); //adds the gamemodePANEL to the frame to display it on the window
    
    add(levelPANEL); //adds the levelPANEL to the frame to display it on the window
    
    add(gofirstPANEL); //adds the gofirstPANEL to the frame to display it on the window
    
    add(boardPANEL); //adds the boardPANEL to the frame to display it on the window
    add(pvspbuttonPANEL); //adds the pvspbuttonPANEL to the frame to display it on the window
    add(pvsaibuttonPANEL); //adds the pvsaibuttonPANEL to the frame to display it on the window
    
    setResizable(false); //lets the user able to adjust the size of the window
    setVisible(true); //makes everything visible
    
  } //ends setting things onto the window
  
  /******************** ~ACTION LISTENER~ ********************/
  //THIS IS MY ACTION PERFORMED METHOD (it listens to events)
  public void actionPerformed(ActionEvent event) {
    
    String command = event.getActionCommand();  //finds out the name of the component that was used
    
    /********** 1. START BUTTON **********/
    
    //does the following if the dimension button is clicked
    if (command.equals("New Game")) {
      
      for (int row = 0; row < 6; row++){
        for (int column = 0; column < 7; column++){
          connect4[row][column].setText("                      ");
        }
      }
      
      welcomeLABEL.setText("Welcome to Connect 4!"); //reset the label
      
      //sets true to ask the user which of the 2 game modes they want to play in
      gamemodePANEL.setVisible(true);
      
      //sets false so that the user responds to each question one at a time
      startorendPANEL.setVisible(false);
      levelPANEL.setVisible(false);
      gofirstPANEL.setVisible(false);
      boardPANEL.setVisible(false);
      pvspbuttonPANEL.setVisible(false);
      pvsaibuttonPANEL.setVisible(false);
      
      /********** 2. EXIT PROGRAM BUTTON **********/
      
    }else if (command.equals("Exit Program")) { //does the following if the exit button is clicked
      welcomeLABEL.setText("THE WINDOW WILL NOW CLOSE!"); //changes the label message to this message (informs the user that the window will close)
      dispose(); //closes the window
      
      /********** 3. P VS P BUTTON **********/
      
    }else if (command.equals("Player vs Player")){
      
      welcomeLABEL.setText("Player 1 (Red) make your move!"); //informs player 1 to make their move
      
      //sets true to let the user play connect 4 game
      startorendPANEL.setVisible(true);
      boardPANEL.setVisible(true);
      pvspbuttonPANEL.setVisible(true);
      
      //sets false so that the user cannot edit their option afterwards
      gamemodePANEL.setVisible(false);
      
      /********** 4. P VS AI BUTTON **********/
      
    }else if (command.equals("Player vs Computer")){
      
      //sets true to ask for the level of difficulty
      levelPANEL.setVisible(true);
      
      //sets false so that the user cannot edit their option afterwards
      gamemodePANEL.setVisible(false);
      
      /********** 5. EASY BUTTON **********/
      
    }else if (command.equals("Easy")){
      
      //sets true to let the user pick if they want to go first or not
      gofirstPANEL.setVisible(true);
      
      //sets false so that the user cannot edit their option afterwards
      levelPANEL.setVisible(false);
      
      /********** 6. MEDIUM BUTTON **********/
      
    }else if (command.equals("Medium")){
      
      //sets true to let the user pick if they want to go first or not
      gofirstPANEL.setVisible(true);
      
      //sets false so that the user cannot edit their option afterwards
      levelPANEL.setVisible(false);
      
      /********** 7. HARD BUTTON **********/
      
    }else if (command.equals("Hard")){
      
      //sets true to let the user pick if they want to go first or not
      gofirstPANEL.setVisible(true);
      
      //sets false so that the user cannot edit their option afterwards
      levelPANEL.setVisible(false);
      
      /********** 8. YES BUTTON **********/
      
    }else if (command.equals("Yes")){
      
      welcomeLABEL.setText("Player 1 (Red) make your move!"); //informs player 1 to make their move
      
      //sets true to let the user play the game connect 4
      startorendPANEL.setVisible(true);
      boardPANEL.setVisible(true);
      pvsaibuttonPANEL.setVisible(true);
      
      //sets false so that the user cannot edit their option afterwards
      gofirstPANEL.setVisible(false);
      
      /********** 9. NO BUTTON **********/
      
    }else if (command.equals("No")){
      
      welcomeLABEL.setText("Player 2 (Blue) make your move!"); //informs player 1 to make their move
      
      //sets true to let the user play the game connect 4
      startorendPANEL.setVisible(true);
      boardPANEL.setVisible(true);
      pvsaibuttonPANEL.setVisible(true);
      
      //sets false so that the user cannot edit their option afterwards
      gofirstPANEL.setVisible(false);
      
      AIMove(grid);
      
      /********** 10. PVSP ONE BUTTON **********/
      
    }else if (command.equals("    1    ")) { //does the following if the 1 button is clicked
      
      int column = 0;
      
      placeDiskpvsp(column, connect4, welcomeLABEL);
      
      WinOrLosePvsP(connect4, welcomeLABEL);
      
      /********** 11. PVSP TWO BUTTON **********/
      
    }else if (command.equals("    2    ")) { //does the following if the 2 button is clicked
      
      int column = 1;
      
      placeDiskpvsp(column, connect4, welcomeLABEL);
      
      WinOrLosePvsP(connect4, welcomeLABEL);
      
      /********** 12. PVSP THREE BUTTON **********/
      
    }else if (command.equals("    3    ")) { //does the following if the 3 button is clicked
      
      int column = 2;
      
      placeDiskpvsp(column, connect4, welcomeLABEL);
      
      WinOrLosePvsP(connect4, welcomeLABEL);
      /********** 13. PVSP FOUR BUTTON **********/
      
    }else if (command.equals("    4    ")) { //does the following if the 4 button is clicked
      
      int column = 3;
      
      placeDiskpvsp(column, connect4, welcomeLABEL);
      
      WinOrLosePvsP(connect4, welcomeLABEL);
      
      /********** 14. PVSP FIVE BUTTON **********/
      
    }else if (command.equals("    5    ")) { //does the following if the 5 button is clicked
      
      int column = 4;
      
      placeDiskpvsp(column, connect4, welcomeLABEL);
      
      WinOrLosePvsP(connect4, welcomeLABEL);
      
      /********** 15. PVSP SIX BUTTON **********/
      
    }else if (command.equals("    6    ")) { //does the following if the 6 button is clicked
      
      int column = 5;
      
      placeDiskpvsp(column, connect4, welcomeLABEL);
      
      WinOrLosePvsP(connect4, welcomeLABEL);
      
      /********** 16. PVSP SEVEN BUTTON **********/
      
    }else if (command.equals("    7    ")) { //does the following if the 7 button is clicked
      
      int column = 6;
      
      placeDiskpvsp(column, connect4, welcomeLABEL);
      
      WinOrLosePvsP(connect4, welcomeLABEL);
      
      /********** 17. PVSAI ONE BUTTON **********/
      
    }else if (command.equals("     1    ")) { //does the following if the 1 button is clicked
      
      int row=0;
      for (int i = 0; i < 6; i++){
        if (connect4[i][0].getText().equals("                      ")){
          row = i;
        }
      }
      connect4[row][0].setText("         O         ");
      connect4[row][0].setForeground(Color.BLUE);
      grid[row][0] = 1;
      
      GameConsole(grid);
      WinOrLosePvsAI(connect4, welcomeLABEL);
      
      /********** 18. PVSAI TWO BUTTON **********/
      
    }else if (command.equals("     2    ")) { //does the following if the 2 button is clicked
      
      int row=0;
      for (int i = 0; i < 6; i++){
        if (connect4[i][1].getText().equals("                      ")){
          row = i;
        }
      }
      connect4[row][1].setText("         O         ");
      connect4[row][1].setForeground(Color.BLUE);
      grid[row][1] = 1;
      
      GameConsole(grid);
      WinOrLosePvsAI(connect4, welcomeLABEL);
      
      /********** 19. PVSAI THREE BUTTON **********/
      
    }else if (command.equals("     3    ")) { //does the following if the 3 button is clicked
      
      int row=0;
      for (int i = 0; i < 6; i++){
        if (connect4[i][2].getText().equals("                      ")){
          row = i;
        }
      }
      connect4[row][2].setText("         O         ");
      connect4[row][2].setForeground(Color.BLUE);
      grid[row][2] = 1;
      
      GameConsole(grid);
      WinOrLosePvsAI(connect4, welcomeLABEL);
      
      /********** 20. PVSAI FOUR BUTTON **********/
      
    }else if (command.equals("     4    ")) { //does the following if the 4 button is clicked
      
      int row=0;
      for (int i = 0; i < 6; i++){
        if (connect4[i][3].getText().equals("                      ")){
          row = i;
        }
      }
      connect4[row][3].setText("         O         ");
      connect4[row][3].setForeground(Color.BLUE);
      grid[row][3] = 1;
      
      GameConsole(grid);
      WinOrLosePvsAI(connect4, welcomeLABEL);
      
      /********** 21. PVSAI FIVE BUTTON **********/
      
    }else if (command.equals("     5    ")) { //does the following if the 5 button is clicked
      
      int row=0;
      for (int i = 0; i < 6; i++){
        if (connect4[i][4].getText().equals("                      ")){
          row = i;
        }
      }
      connect4[row][4].setText("         O         ");
      connect4[row][4].setForeground(Color.BLUE);
      grid[row][4] = 1;
      
      GameConsole(grid);
      WinOrLosePvsAI(connect4, welcomeLABEL);
      
      /********** 22. PVSAI SIX BUTTON **********/
      
    }else if (command.equals("     6    ")) { //does the following if the 6 button is clicked
      
      int row=0;
      for (int i = 0; i < 6; i++){
        if (connect4[i][5].getText().equals("                      ")){
          row = i;
        }
      }
      connect4[row][5].setText("         O         ");
      connect4[row][5].setForeground(Color.BLUE);
      grid[row][5] = 1;
      
      GameConsole(grid);
      WinOrLosePvsAI(connect4, welcomeLABEL);
      
      /********** 23. PVSAI SEVEN BUTTON **********/
      
    }else if (command.equals("     7    ")) { //does the following if the 7 button is clicked
      
      int row=0;
      for (int i = 0; i < 6; i++){
        if (connect4[i][6].getText().equals("                      ")){
          row = i;
        }
      }
      connect4[row][6].setText("         O         ");
      connect4[row][6].setForeground(Color.BLUE);
      grid[row][6] = 1;
      
      GameConsole(grid);
      WinOrLosePvsAI(connect4, welcomeLABEL);
      
    } //ends the if statement
  } //ends action listener
  
  public static void placeDiskpvsp (int c, JLabel[][] c4, JLabel welcomeLABEL){ //c stands for column; c4 stands for connect4
    
    int row=0; //get the row number
    
    for (int r = 0; r < 6; r++){ //row stands for row
      if (c4[r][c].getText().equals("                      ")){
        row = r;
      }       
    }
    
    count++;
    
    if (count%2 != 0){ //if player one makes the move
      welcomeLABEL.setText("Player 2 (Blue) make your move!"); //informs player 2 to make their move since player 1 just made their move
      c4[row][c].setText("         O         ");
      c4[row][c].setForeground(Color.RED);
      
    }else if (count%2 == 0){ //if player two makes the move
      welcomeLABEL.setText("Player 1 (Red) make your move!"); //informs player 1 to make their move since player 2 just made their move
      c4[row][c].setText("         o         ");
      c4[row][c].setForeground(Color.BLUE);
    }
  }
  
  public static void WinOrLosePvsP(JLabel[][] c4, JLabel welcomeLABEL){ //method for checking win condition for player
    
    for (int r = 0; r < 6; r++){
      for (int c = 0; c < 7; c++){
        
        //horizontal
        if (c <= 3){ //make sure not out of bounds
          if (c4[r][c].getText().equals("         O         ") && c4[r][c+1].getText().equals("         O         ") && c4[r][c+2].getText().equals("         O         ") && c4[r][c+3].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }else if (c4[r][c].getText().equals("         o         ") && c4[r][c+1].getText().equals("         o         ") && c4[r][c+2].getText().equals("         o         ") && c4[r][c+3].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }
        }
        
        //vertical
        if (r >= 3){ //make sure not out of bounds
          if (c4[r][c].getText().equals("         O         ") && c4[r-1][c].getText().equals("         O         ") && c4[r-2][c].getText().equals("         O         ") && c4[r-3][c].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }else if (c4[r][c].getText().equals("         o         ") && c4[r-1][c].getText().equals("         o         ") && c4[r-2][c].getText().equals("         o         ") && c4[r-3][c].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }
        }
        
        //horizontal left
        if (r >= 3 && c >=3){ //make sure not out of bounds
          if (c4[r][c].getText().equals("         O         ") && c4[r-1][c-1].getText().equals("         O         ") && c4[r-2][c-2].getText().equals("         O         ") && c4[r-3][c-3].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }else if (c4[r][c].getText().equals("         o         ") && c4[r-1][c-1].getText().equals("         o         ") && c4[r-2][c-2].getText().equals("         o         ") && c4[r-3][c-3].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }
        }
        
        //horizontal left
        if (r >= 3 && c <=3){ //make sure not out of bounds
          if (c4[r][c].getText().equals("         O         ") && c4[r-1][c+1].getText().equals("         O         ") && c4[r-2][c+2].getText().equals("         O         ") && c4[r-3][c+3].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }else if (c4[r][c].getText().equals("         o         ") && c4[r-1][c+1].getText().equals("         o         ") && c4[r-2][c+2].getText().equals("         o         ") && c4[r-3][c+3].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvspbuttonPANEL.setVisible(false); //doesn't let the user continue playing
          }
        }
      }
    }
  } //ends the method "WinOrLosePvsP"
  
  public static void WinOrLosePvsAI(JLabel[][] c4, JLabel welcomeLABEL){ //method for checking win condition for player
    
    for (int r = 0; r < 6; r++){
      for (int c = 0; c < 7; c++){
        
        //horizontal
        if (c <= 3){
          if (c4[r][c].getText().equals("         O         ") && c4[r][c+1].getText().equals("         O         ") && c4[r][c+2].getText().equals("         O         ") && c4[r][c+3].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }else if (c4[r][c].getText().equals("         o         ") && c4[r][c+1].getText().equals("         o         ") && c4[r][c+2].getText().equals("         o         ") && c4[r][c+3].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }
        }
        
        //vertical
        if (r >= 3){
          if (c4[r][c].getText().equals("         O         ") && c4[r-1][c].getText().equals("         O         ") && c4[r-2][c].getText().equals("         O         ") && c4[r-3][c].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }else if (c4[r][c].getText().equals("         o         ") && c4[r-1][c].getText().equals("         o         ") && c4[r-2][c].getText().equals("         o         ") && c4[r-3][c].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }
        }
        
        //horizontal left
        if (r >= 3 && c >=3){
          if (c4[r][c].getText().equals("         O         ") && c4[r-1][c-1].getText().equals("         O         ") && c4[r-2][c-2].getText().equals("         O         ") && c4[r-3][c-3].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }else if (c4[r][c].getText().equals("         o         ") && c4[r-1][c-1].getText().equals("         o         ") && c4[r-2][c-2].getText().equals("         o         ") && c4[r-3][c-3].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }
        }
        
        //horizontal left
        if (r >= 3 && c <=3){
          if (c4[r][c].getText().equals("         O         ") && c4[r-1][c+1].getText().equals("         O         ") && c4[r-2][c+2].getText().equals("         O         ") && c4[r-3][c+3].getText().equals("         O         ")){
            welcomeLABEL.setText("Player Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }else if (c4[r][c].getText().equals("         o         ") && c4[r-1][c+1].getText().equals("         o         ") && c4[r-2][c+2].getText().equals("         o         ") && c4[r-3][c+3].getText().equals("         o         ")){
            welcomeLABEL.setText("AI Wins!");
            pvsaibuttonPANEL.setVisible(false);
          }
        }
      }
    }
  }
  
  
  public void GameConsole(int[][] grid){
    
    Print(grid);
    boolean inGame = true;   
    
    if (CheckResult(grid) != -1){//check the grid if the move is illegal
      inGame = false;
    }
    
    if(inGame){
      AIMove(grid);
      if (CheckResult(grid) != -1){
        inGame = false;
      }
    }
    
    Transfer(grid);
    
    
  }
  
  
  public  void AIMove(int[][] grid){
    Minimax(grid, 2, 0);
    MakeMove(grid, AINextMove, 2);
    System.out.println("Board after AI's move: " + (AINextMove+1));
    Print(grid);
    return;
  }
  
  public  boolean CheckPlayerMove(int[][] grid, int col){
    if (col >= MAXCOL+1 || col < 0) {
      System.out.println("Illegal move: no such column, please input column 1 - " + MAXCOL+1);
      return false;
    }
    
    if(grid[0][col-1] != 0){
      System.out.println("Illegal move: selected column is full");
      return false;
    } 
    return true;
  }
  
  
  public  void MakeMove(int[][] grid, int col, int turn){
    for(int i = MAXROW-1 ; i >= 0; i--){
      if (grid[i][col]==0){
        grid[i][col] = turn;
        break;
      }
    }
    return;
  }
  
  public  void UndoMove(int[][] grid, int col){
    for(int i = 0 ; i < MAXROW; i++){
      if (grid[i][col]!=0){
        grid[i][col] = 0;
        break;
      }
    }
    return;
  }
  
  public  int Minimax(int[][] grid, int turn, int depth){//method for determining player1 and AI's gamestates
    
    int max = Integer.MIN_VALUE,min = Integer.MAX_VALUE;
    int res = 0;
    
    int gameResult = GameResult(grid);
    if(gameResult==1)return Integer.MAX_VALUE;
    else if(gameResult==2)return Integer.MIN_VALUE;
    else if(gameResult==0)return 0;
    
    if(depth == MAXDEPTH){
      res =  Evaluate(grid);
    }
    
    else{
      // Go through all possible move
      for(int c = 0; c < MAXCOL; c++){
        if(grid[0][c] != 0){
          continue;
        }
        
        if(turn == 2){ // AI's turn
          MakeMove(grid, c, 2);
          int score = Minimax(grid, 1, depth + 1);
          max = Math.max(score, max);
          // At depth 0, we check if the current move can give us max score.
          if(depth==0){
            // current move can give us max score
            if(score == max) AINextMove = c;
          }
          res = max;
        }
        
        else{//player's turn
          MakeMove(grid, c, 1);
          int score = Minimax(grid, 2, depth + 1);
          min = Math.min(score, min);
          res = min;
        }
        UndoMove(grid, c);
      }
    }
    
    return res;
  }
  
  public  int GameResult(int[][] grid){ //method for checking win condition for player
    
    int aiscore=0,playerscore=0;
    for(int i=MAXROW-1;i>=0;i--){
      for(int j=0;j<MAXCOL;j++){
        if(grid[i][j]==0) continue;
        
        //Checking cells to the right
        if(j<=3){
          for(int k=0;k<4;k++){ 
            if(grid[i][j+k]==1){
              playerscore++;
            }
            
            else if(grid[i][j+k]==2){
              aiscore++;
            }
            
            else {
              break; 
            }
          }
          if(aiscore==4){
            return 1; 
          }
          
          else if (playerscore==4){
            return 2;
          }
          
          
          aiscore = 0; 
          playerscore = 0;
        } 
        
        //Checking cells up
        if(i>=3){
          for(int k=0;k<4;k++){
            if(grid[i-k][j]==1){
              playerscore++;
            }
            else if(grid[i-k][j]==2){
              aiscore++;
            }
            else{
              break;
            }
          }
          
          if(aiscore==4){
            return 1;
          }
          
          else if (playerscore==4){
            return 2;
          }
          aiscore = 0; 
          playerscore = 0;
        } 
        
        //Checking diagonal up-right
        if(j<=3 && i>= 3){
          for(int k=0;k<4;k++){
            if(grid[i-k][j+k]==1){
              playerscore++;
            }
            
            else if(grid[i-k][j+k]==2){
              aiscore++;
            }
            
            else{ 
              break;
            }
          }
          if(aiscore==4){
            return 1;
          }
          
          else if (playerscore==4){
            return 2;
          }
          
          aiscore = 0;
          playerscore = 0;
        }
        
        //Checking diagonal up-left
        if(j>=3 && i>=3){
          for(int k=0;k<4;k++){
            if(grid[i-k][j-k]==1){
              playerscore++;
            }
            
            else if(grid[i-k][j-k]==2){
              aiscore++;
            }
            
            else{
              break;
            }
          } 
          if(aiscore==4){
            return 1;
          }
          
          else if (playerscore==4){
            return 2;
          }
          
          aiscore = 0; 
          playerscore = 0;
        }  
      }
    }
    
    for(int j=0;j<MAXCOL;j++){
      //Game has not ended yet
      if(grid[0][j]==0){
        return -1;
      }
    }
    //Game draw!
    return 0; 
  }
  
  public  int CheckResult(int[][] grid){
    int gameResult = GameResult(grid);
    if(gameResult ==1){
      System.out.println("AI Wins");
    }
    else if(gameResult == 2){
      System.out.println("Player Wins");
    }
    else if (gameResult == 0){
      System.out.println("Game is draw");
    }
    
    
    return gameResult;
    
    
  }
  
  
  public  int Evaluate(int[][] grid){// method for evaluating each gamestate and deciding AI's move
    
    int aiScore=1;//score counted to ai
    int score=0;//score after evaluation in favor of ai
    int blanks = 0;//variable for how many blanks(clear positions) left for each gamestates
    int k=0, moreMoves=0;
    for(int i=5;i>=0;i--){
      for(int j=0;j<=6;j++){
        
        if(grid[i][j]==0 || grid[i][j]==2){
          continue; 
        }
        
        if(j<=3){ 
          for(k=1;k<4;k++){
            if(grid[i][j+k]==1){
              aiScore++;
            }
            else if(grid[i][j+k]==2){
              aiScore=0;
              blanks = 0;
              break;
            }
            else {
              blanks++;
            }
          }
          
          moreMoves = 0; 
          if(blanks>0) 
            for(int c=1;c<4;c++){
            int column = j+c;
            for(int m=i; m<= 5;m++){
              if(grid[m][column]==0){
                moreMoves++;
              }
              else {
                break;
              }
            } 
          } 
          
          if(moreMoves!=0) {
            score += calculateScore(aiScore, moreMoves);
            aiScore=1;   
            blanks = 0;
          }
        } 
        
        if(i>=3){
          for(k=1;k<4;k++){
            if(grid[i-k][j]==1){
              aiScore++;
            }
            else if(grid[i-k][j]==2){
              aiScore=0;break;
            } 
          } 
          moreMoves = 0; 
          
          if(aiScore>0){
            int column = j;
            for(int m=i-k+1; m<=i-1;m++){
              if(grid[m][column]==0){
                moreMoves++;
              }
              else {
                break;
              }
            }  
          }
          if(moreMoves!=0) {
            score += calculateScore(aiScore, moreMoves);
            aiScore=1;  
            blanks = 0;
          }
        }
        
        if(j>=3){
          for(k=1;k<4;k++){
            if(grid[i][j-k]==1){
              aiScore++;
            }
            else if(grid[i][j-k]==2){
              aiScore=0;
              blanks=0;
              break;
            }
            else {
              blanks++;
            }
          }
          moreMoves=0;
          if(blanks>0){ 
            for(int c=1;c<4;c++){
              int column = j- c;
              for(int m=i; m<= 5;m++){
                if(grid[m][column]==0){
                  moreMoves++;
                }
                else{ 
                  break;
                }
              } 
            } 
            
            if(moreMoves!=0) {
              score += calculateScore(aiScore, moreMoves);
              aiScore=1; 
              blanks = 0;
            }
          }
        }
        
        if(j<=3 && i>=3){
          for(k=1;k<4;k++){
            if(grid[i-k][j+k]==1){
              aiScore++;
            }
            else if(grid[i-k][j+k]==2){
              aiScore=0;
              blanks=0;
              break;
            }
            else {
              blanks++; 
              
            }
            moreMoves=0;
            if(blanks>0){
              for(int c=1;c<4;c++){
                int column = j+c, row = i-c;
                for(int m=row;m<=5;++m){
                  if(grid[m][column]==0){
                    moreMoves++;
                  }
                  else if(grid[m][column]==1);
                  else break;
                }
              } 
              if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
              aiScore=1;
              blanks = 0;
            }
          }
          
          if(i>=3 && j>=3){
            for(k=1;k<4;k++){
              if(grid[i-k][j-k]==1)aiScore++;
              else if(grid[i-k][j-k]==2){aiScore=0;blanks=0;break;}
              else blanks++;                        
            }
            moreMoves=0;
            if(blanks>0){
              for(int c=1;c<4;c++){
                int column = j-c, row = i-c;
                for(int m=row;m<=5;++m){
                  if(grid[m][column]==0)moreMoves++;
                  else if(grid[m][column]==1);
                  else{
                    break;
                  }
                }
              } 
              if(moreMoves!=0){
                score += calculateScore(aiScore, moreMoves);
                aiScore=1;
                blanks = 0;
              }
            }
          } 
        }
      }
    }
    return score;
  }
  
  public  int calculateScore(int aiScore, int moreMoves){
    int moveScore = 4 - moreMoves;
    if(aiScore==0){
      return 0;
    }
    else if(aiScore==1){
      return 1*moveScore;
    }
    else if(aiScore==2){
      return 10*moveScore;
    }
    else if(aiScore==3){
      return 100*moveScore;
    }
    else{
      return 1000;
    }
  }
  
  
  public  void Print(int[][] grid){
    for(int r = 0; r < MAXROW; r++){
      for(int c = 0 ; c < MAXCOL; c++){
        System.out.print(grid[r][c] + "  ");
      }
      System.out.println();
    }
    System.out.println();
  }
  
  public void Transfer(int[][] grid){
    for(int r = 0; r < MAXROW; r++){
      for(int c = 0; c < MAXCOL; c++){
        if(grid[r][c] == 1){
          connect4[r][c].setText("         O         ");
          connect4[r][c].setForeground(Color.BLUE);
        }
        
        else if(grid[r][c] == 2){
          connect4[r][c].setText("         O         ");
          connect4[r][c].setForeground(Color.RED);
        }
      }
    }
  }
  
  
  /******************** ~MAIN METHOD~ ********************/
  
  public static void main(String[] args) {
    Connect4GUI frame1 = new Connect4GUI();  //start the GUInumbers!
  }
} //class ends here