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
      for(int j = 0; j < board.length; j++){
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
    board[row][col] = level;
    boolean ans = false;
    for(int m = 0; m < moves.length-1; m+=2){
      ans = ans || solveH(moves[m], moves[m+1], level+1);
    }
    //^^ trying all the possible knight moves
    if(!ans)
    board[row][col] = 0;

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
      throw new IllegalArgumentException();
    return countH(row, column, 0);



  }

  private int countH(int row, int col, int level) {
                int count = 0;
                if (level - 1 == row * col) {
                        count++;
                }
                if (level == 1) {
                        board[row][col] = level;
                        return countH(row, col, level + 1);
                }
                
                for (int i = 0; i < 4; i++) {
                        int X = row + moves[2*i];
                        int Y = col + moves[2*i + 1];
                        board[X][Y] = level;
                        count += countH(newX, newY, level + 1);
                        board[newX][newY] = 0;

                }
                return count;
        }


  public boolean checkBoard(){
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board.length; j++){
        if(board[i][j] != 0)
        return false;
      }
    }
    return true;
  }


public static void main(String[] args){
  KnightBoard k = new KnightBoard(6,10);
  k.solve(0,0);
  k.countSolutions(0,0);
  System.out.println(k);
}


}
