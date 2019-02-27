public class KnightBoard{
  private int[][] board;
  public KnightBoard(int startingRows,int startingCols) throws IllegalArgumentException{
    if(startingRows <= 0 || startingCols <=0) throw new IllegalArgumentException();
    else board = new int[startingRows][startingCols];
  }

  public static int[] moves = {1,2,1,-2,-1,2,-1,-2,2,1,2,-1,-2,1,-2,-1};

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
    if(row < 0 || col< 0){
      return false;
    }

    if(row >= board.length || col >= board[0].length)
    return false;

    if(board[row][col] > 0)
    return false;

    //make sure the move is valid, then move the knight there and continue



    return ans;

  }

  public boolean solve(int row, int col) throws IllegalArgumentException{
    if(row < 0 || col < 0)throw new IllegalArgumentException();
    return solveH(row, col, 1);
  }

  public int countSolutions(int row, int column)throws IllegalStateException, IllegalArgumentException{
    if(!checkBoard()){
      throw new IllegalStateException("Clear the Board");
    }
    if(row < 0 || column < 0 || row >= board.length || column > board[0].length)
      throw new IllegalArgumentException("No");
    return countH(row, column, 1, 0);



  }

  private int countH(int row, int col, int level, int count) {
    if(level == board.length * board[0].length){
      count += 1;
      return count;
    }
    for(int i = 0; i < moves.length - 1; i+=2){
      //before we move just check all the conditions
      int r = row + moves[i];
      int c = col + moves[i+1];
      if(r < 0 || c< 0 || r >= board.length || c > board[0].length || board[r][c] != 0)
        return 0;
      board[r][c] = level;
      count += countH(r, c, level + 1, count);

    }
    return count;
  }



private boolean checker(int row, int col) {
                return ((row >= 0) && (row < rows) && (col >= 0) && (col < cols) && (board[row][col] == 0));
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


public static void main(String[] args){
  KnightBoard k = new KnightBoard(6,6);
  k.solve(0,0);
  //System.out.print(k.countSolutions(0,0));
  System.out.println(k);
}


}
