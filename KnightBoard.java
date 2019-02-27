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


  private boolean solveH(int row, int col, int level){
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
        }


  public boolean solve(int row, int col) throws IllegalArgumentException{
    if(row < 0 || col < 0)throw new IllegalArgumentException();
    return solveH(row, col, 1);
  }

  public int countSolutions(int row, int column)throws IllegalStateException, IllegalArgumentException{
    clear();
    if(!checkBoard()){
      throw new IllegalStateException("Clear the Board");
    }
    if(row < 0 || column < 0 || row >= board.length || column > board[0].length)
      throw new IllegalArgumentException("No");
    return countH(row, column, 1);


  }




  private int countH(int row, int col, int level) {

    int count = 0;
    if(level == board.length * board[0].length){
      count += 1;
    }
    if (level == 1){
                board[row][col] = level;
                return countH(row, col, level + 1);
    }

    for(int i = 0; i < moves.length - 1; i+=1){
      //before we move just check all the conditions
      int r = row + moves[i][0];
      int c = col + moves[i][1];
      if(checker(r,c)){
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


public static void main(String[] args){
  KnightBoard k = new KnightBoard(6,6);
  k.solve(0,0);
  System.out.print(k.countSolutions(0,0));
  System.out.println(k);
}


}
