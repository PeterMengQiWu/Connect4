/* File name: Connect4.java
 * Author: Peter Wu & Janice Ho
 * Description: Connect 4 game in java
 * Date: Nov 17, 2015
 */
import java.util.Scanner;
public class Connect4{
  
  static final int MAXROW = 6; //constant variable for maximum row the grid needs
  static final int MAXCOL = 7; //constant variable for minimum column the grid needs
  static final int MAXDEPTH = 8;
  static Scanner k = new Scanner(System.in);
  static int playerscore = 0;
  static int aiscore = 0;
  static int move = -1;
  static int AINextMove = -1;
  public static int[][] grid = new int[MAXROW][MAXCOL];
  
  
  public static void main(String args[]){
    String answer;
    
    for(int a = 0; a < MAXROW; a++){
      for(int b = 0; b < MAXCOL; b++){//for loop for initializing the grid with 0s(grid is clean)
        grid[a][b] = 0;
      }
    }
    
    System.out.print("Would you like to go first?(yes/no)");
    answer = k.next().trim();
    
    if(answer.equalsIgnoreCase("no")){
      AIMove(grid);
    }
    
    Print(grid);
    boolean inGame = true;   
    
    while(inGame){//while loop for placing each move on grid 
      PlayerMove(grid);
      if (CheckResult(grid) != -1){//check the grid if the move is illegal
        inGame = false;
      }
      
      if(inGame){
        AIMove(grid);
        if (CheckResult(grid) != -1){
          inGame = false;
        }
      }
      
    }
  }
  
  
  public static void PlayerMove(int[][] grid){// method for player1's input
    boolean moved = false;
    int playerMove = -1;
    
    do{
      System.out.print("\nPlayer 1's turn, enter which column to drop your disc.");
      playerMove = k.nextInt();
      
      if (CheckPlayerMove(grid, playerMove)){
        MakeMove(grid, playerMove-1, 1);
        moved = true;
      }
      
    }while(!moved);
    System.out.println("Board after player's move: " + playerMove); 
    Print(grid);
    return;
    
  }
  
  public static void AIMove(int[][] grid){
    Minimax(grid, 2, 0);
    MakeMove(grid, AINextMove, 2);
    System.out.println("Board after AI's move: " + (AINextMove+1));
    Print(grid);
    return;
  }
  
  public static boolean CheckPlayerMove(int[][] grid, int col){
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
  
  
  public static void MakeMove(int[][] grid, int col, int turn){
    for(int i = MAXROW-1 ; i >= 0; i--){
      if (grid[i][col]==0){
        grid[i][col] = turn;
        break;
      }
    }
    return;
  }
  
  public static void UndoMove(int[][] grid, int col){
    for(int i = 0 ; i < MAXROW; i++){
      if (grid[i][col]!=0){
        grid[i][col] = 0;
        break;
      }
    }
    return;
  }
  
  public static int Minimax(int[][] grid, int turn, int depth){//method for determining player1 and AI's gamestates
    
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
  
  public static int GameResult(int[][] grid){ //method for checking win condition for player
    
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
  
  public static int CheckResult(int[][] grid){
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
  
  
  public static int Evaluate(int[][] grid){// method for evaluating each gamestate and deciding AI's move
    
    int aiScore=1;//score counted to ai
    int score=0;//score after evaluation in favor of ai
    int blanks = 0;//variable for how many blanks(clear positions) left for each gamestates
    int k=0, moreMoves=0;
    
    int gameResult = GameResult(grid);
    if(gameResult==1)return Integer.MAX_VALUE;
    else if(gameResult==2)return Integer.MIN_VALUE;
   
    for(int i=MAXROW-1;i>=0;i--){
      for(int j=0;j<MAXCOL;j++){
        
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
  
  public static int calculateScore(int aiScore, int moreMoves){
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
  
  
  public static void Print(int[][] grid){
    for(int r = 0; r < MAXROW; r++){
      for(int c = 0 ; c < MAXCOL; c++){
        System.out.print(grid[r][c] + "  ");
      }
      System.out.println();
    }
    System.out.println();
  }
}

















