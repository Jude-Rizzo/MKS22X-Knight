import java.util.*;

public class KnightBoard{
  private int[][] board;
  int[][] moves;
  int startingRows;
  int startingCols;
  public KnightBoard(int startingRows1,int startingCols1) throws IllegalArgumentException{
    startingRows = startingRows1;
    startingCols = startingCols1;
    if(startingRows <= 0 || startingCols <=0) throw new IllegalArgumentException();
    else board = new int[startingRows][startingCols];

  }



  public String toString(){
    String ans = "";
    for(int i = 0; i < board.length; i++){
      ans += "\n";
      for(int j = 0; j < board[0].length; j++){
        if(board[i][j]<10)ans += "_"+board[i][j] + "  ";
        else ans+=board[i][j] + "  ";
      }
    }return ans;
  }


  /*private boolean solveH(int row, int col, int level){
    //level is the move number of the knight
    if(level == board.length * board[0].length + 1){

      return true;
    }
    if (level == 1) {
                        board[row][col] = level;
                        return solveH(row, col, level + 1);
    }

    for (int i = 0; i < 8; i++) {
                        int X = row + moves[i][0];
                        int Y = col + moves[i][1];
                        if (checker(X, Y)) {
                                board[X][Y] = level;
                                if (solveH(X, Y, level + 1)) {
                                        return true;
                                }
                                board[X][Y] = 0;
                        }
                }
                return false;
        }*/


//NEW OPTIMIZATION
private class piece{
	int x, y, n;

	public piece(){
		x = 0;
		y = 0;
		n = 0;
	}

	public piece(int a, int b, int c){
		x = a;
    y = b;
    n = c;
	}
}

//Note - Friend helped come up with algorythm on paper but did not share code

class Pieces implements Comparator<piece>{
	public int compare(piece a, piece b){
		return a.n - b.n;
	}
}

  public boolean solveH(int row, int col, int level){

    ArrayList<piece> possibleMoves = new ArrayList<piece>();
    //create a structure of board pieces which each possible move and put them into the array ArrayList

    for(int i = -2; i <= 2; i += 4){
      for(int j = -1; j <= 1; j += 2){
        if(row + i >= 0 && row + i < startingRows){
          if(col + j >= 0 && col + j < startingCols){
            if(board[row + i][col + j] == 0){
              //putting every item into an arrayList
              possibleMoves.add(new piece(row + i, col + j, moves[row + i][col + j]));
            }
          }
        }
        if(row + j >= 0 && row + j < startingRows){
          if(col + i >= 0 && col + i < startingCols){
            if(board[row + j][col + i] == 0){
              possibleMoves.add(new piece(row + j, col + i, moves[row + j][col + i]));
            }
          }
        }
      }
    }

  //sort the array to prioritize the tours with the least amount of possiblr movrd
  Collections.sort(possibleMoves, new Pieces());
	for(int i = 0; i < possibleMoves.size(); i++){
    piece t = possibleMoves.get(i);
		board[t.x][t.y] = level + 1;
    //make the base case
		if(level == startingRows * startingCols - 1){
      return true;
    }
    //filter through all the possible moves, alter each move uf they
    //are less than rows and clumns by one
		for(int n = -2; n <= 2; n += 4){
			for(int j = -1; j <= 1; j += 2){
				if(t.x + n >= 0 && t.x + n < startingRows){
					if(t.y + j >= 0 && t.y + j < startingCols){
						moves[t.x + n][t.y + j] -= 1;
					}
				}
				if(t.x + j >= 0 && t.x + j < startingRows){
					if(t.y + n >= 0 && t.y + n < startingCols){
						moves[t.x + j][t.y + n] -= 1;
					}
				}
			}
		}

  for(int n = -2; n <= 2; n += 4){
			for(int j = -1; j <= 1; j += 2){
				if(t.x + n >= 0 && t.x + n < startingRows){
					if(t.y + j >= 0 && t.y + j < startingCols){
						moves[t.x + n][t.y + j] += 1;
					}
				}
				if(t.x + j >= 0 && t.x + j < startingRows){
					if(t.y + n >= 0 && t.y + n < startingCols){
						moves[t.x + j][t.y + n] += 1;
					}
				}
			}
		}
		board[t.x][t.y] = 0;
	}
	return false;
}
// level is the # of the knight




  public boolean solve(int rows, int cols) throws IllegalArgumentException{
    if(rows < 0 || cols < 0)throw new IllegalArgumentException();
    moves = new int[startingRows][startingCols];
    for(int row = 0; row < startingRows; row++){
      for(int col = 0; col < startingCols; col++){
        for(int i = -2; i <= 2; i += 4){
          for(int j = -1; j <= 1; j += 2){
            if(row + i >= 0 && row + i < startingRows){
              if(col + j >= 0 && col + j < startingCols){
                moves[row][col] += 1;
              }
            }
            if(row + j >= 0 && row + j < startingRows){
              if(col + i >= 0 && col + i < startingCols){
                moves[row][col] += 1;
              }
            }
          }
        }
      }
    }
    return solveH(rows, cols, 1);
  }

  public int countSolutions(int r, int c) {
                //make board valied
                clear();
                return countH(r, c, 1);
        }

        private int countH(int row, int col, int level) {
                int count = 0;

              //checks last one
                if (level == board.length * board[0].length + 1) {
                        count++;
                }
                //start off the count
                if (level == 1) {
                        board[row][col] = level;
                        return countH(row, col, level + 1);
                }
                //do all possible moves, add them all together
                for (int i = 0; i < 8; i++) {
                        int r = row + moves[i][0];
                        int c = col + moves[i][1];
                        if (checker(r, c)) {
                                board[r][c] = level;
                                count += countH(r, c, level + 1);
                                board[r][c] = 0;
                        }
                }
                return count;
        }




private boolean checker(int row, int col) {
                return ((row >= 0) && (row < board.length) && (col >= 0) && (col < board[0].length) && (board[row][col] == 0));
        }

  public boolean checkBoard(){
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[0].length; j++){
        if(board[i][j] != 0)
        return false;
      }
    }
    return true;
  }
  public void clear(){
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[0].length; j++){
        board[i][j] = 0;

      }
    }

  }


  public static void runTest(int i){

    KnightBoard b;
    int[]m =   {4,5,5,5,5};
    int[]n =   {4,5,4,5,5};
    int[]startx = {0,0,0,1,2};
    int[]starty = {0,0,0,1,2};
    int[]answers = {0,304,32,56,64};
    if(i >= 0 ){
      try{
        int correct = answers[i];
        b = new KnightBoard(m[i%m.length],n[i%m.length]);

        int ans  = b.countSolutions(startx[i],starty[i]);

        if(correct==ans){
          System.out.println("PASS board size: "+m[i%m.length]+"x"+n[i%m.length]+" "+ans);
        }else{
          System.out.println("FAIL board size: "+m[i%m.length]+"x"+n[i%m.length]+" "+ans+" vs "+correct);
        }
      }catch(Exception e){
        System.out.println("FAIL Exception case: "+i);

      }
    }
  }








}
