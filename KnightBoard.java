public class KnightBoard{
  private int[][] board;
  public KnightBoard(int startingRows,int startingCols) throws IllegalArgumentException{
    if(startingRows <= 0 || startingCols <=0) throw new IllegalArgumentException();
    else board = new int[startingRows][startingCols];
  }

  public static int[][] moves = {{1,2}, {2,1}, {2,-1}, {1,-2}, {-1,-2}, {-2,-1}, {-2,1}, {-1,2}};

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

  public boolean solveH(int row, int col, int level, int[][] moves){
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
		if(level == startingRows * startingCols - 1){
      return true;
    }
		for(int i = -2; i <= 2; i += 4){
			for(int j = -1; j <= 1; j += 2){
				if(t.x + i >= 0 && t.x + i < startingRows){
					if(t.y + j >= 0 && t.y + j < startingCols){
						moves[t.x + i][t.y + j] -= 1;
					}
				}
				if(t.x + j >= 0 && t.x + j < startingRows){
					if(t.y + i >= 0 && t.y + i < startingCols){
						moves[t.x + j][t.y + i] -= 1;
					}
				}
			}
		}
  }



  public boolean solve(int row, int col) throws IllegalArgumentException{
    if(row < 0 || col < 0)throw new IllegalArgumentException();
    return solveH(row, col, 1);
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







}
