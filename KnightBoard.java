import java.util.*;

//make your board piece class
class Piece implements Comparable<Piece>{
  public int row;
  public int col;
  public int moves;
//r is move number
  public Piece(int q, int w, int r){
    row = q;
    col = w;
    moves = r;
  }

  public int compareTo(Piece p){
    return moves - p.moves;
  }

  public int compare(Piece p, Piece q){
    return p.moves - q.moves;
  }


}


public class KnightBoard{
  private int[][] board;
  public int[][] Moves = {{1,2}, {-1,2}, {1,-2}, {-1, -2}, {2,1}, {2,-1}, {-2, 1}, {-2,-1}};
  public int startingRows;
  public int startingCols;
  public Piece[][] posMoves;
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

  public boolean clear(){
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
          board[i][j] = 0;
        }
    }

  }

  public boolean solve(int startingRow, int startingCol) throws IllegalArgumentException, IllegalStateException{
    //first check for non0 values
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(board[i][j] != 0){
          throw new IllegalStateException("Board is not cleared");
        }
      }
    }

    //then check for IllegalArguments
    if(startingRow < 0 || startingCol < 0 || startingRow > startingRows || startingCol > startingCols){
      throw new IllegalArgumentException("inputs are off");
    }
    //now make the arrayList of all the boardSquares and all the possible moves

    posMoves = new Piece[startingRows][startingCols];
    //for loop through the board, and test out all possible knightmoves
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        int mcounter = 0;
        for(int k = 0; k < 8; k++){
          //see if anything is out of bounds, start off with rows
          //the js are the columns
          if(!(i + Moves[k][0] < 0 || i + Moves[k][0] >= startingRows
          || j + Moves[k][1] < 0 || j + Moves[k][1] >= startingCols)) mcounter +=1;
        }
        //make a new piece for the poind and put it ib the row
        Piece p = new Piece(startingRow, startingCol, mcounter);
        posMoves[i][j] = p;
      }
    }
    return solveH(startingRow, startingCol, 1, posMoves);
  }


  public boolean solveH(int row, int col, int level, Piece[][] posMoves){
  //If level is the total area, return true and finish the solution
  if(level == board.length * board[1].length){
    return true;
  }
  ArrayList<Piece> options = new ArrayList<Piece>();
  //else were going to look at all the possible moves and put them into an arrayList
  for(int i = 0; i < 8; i++){
    //make sure its a valid move
    if(row + moves[i][0] >= 0 &&
       row + moves[i][0] <= startingRows &&
       col + moves[i][1] >= 0 &&
       col + moves[i][1] <= startingCols &&
       board[row + moves[i][0]][col+moves[i][1]] == 0
       ){
         //if it is, add it to the ArrayList of options to move to

         options.add(posMoves[row + moves[i][0]][col+moves[i][1]]]);
       }
  }
  //now sort options from moves least to greatest
  Collections.sort(options);
  //
  if(options.size() > 0){

    //if there are remaining options, set this value equal to the level and move on
    for(int i = 0; i < options.size(); i++){
      Piece next = options.get(i);
      return solveH(next.row, next.col, level + 1, posMoves);
    }
  }
  //add one to the possible moves
  return false;

  }






//TIME TO REDO THE WHOLE LAB

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




//Note - Friend helped come up with algorythm on paper but did not share code
/*
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
    //initialize the moves array with every possible move in the beginning;
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
    //if there is a solution return true
    if(this.solveH(rows, cols, 1)){
      board[rows][cols] = 1;
     return true;
   }
   //otherwise undo the board making that you did
	   board[rows][cols] = 0;
	   return false;
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
  } */








}
