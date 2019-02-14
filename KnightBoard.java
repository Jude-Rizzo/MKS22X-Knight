public class KnightBoard{
  private int[][] board;
  public KnightBoard(int startingRows,int startingCols) throws IllegalArgumentException{
    if(startingRows <= 0 || startingCols <=0) throw new IllegalArgumentException();
    else board = new int[startingRows][startingCols];
  }

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
    boolean ans = (solveH(row + 2, col + 1, level + 1)||solveH(row + 2, col - 1, level + 1)

    || solveH(row - 2, col + 1, level + 1)||solveH(row - 2, col - 1, level + 1) ||

    solveH(row + 1, col + 2, level + 1)||solveH(row + 1, col - 2, level + 1)

    || solveH(row - 1, col + 2, level + 1)||solveH(row - 1, col - 2, level + 1));
    //^^ trying all the possible knight moves
    if(!ans)
    board[row][col] = 0;

    return ans;

  }

  public boolean solve(int row, int col) throws IllegalArgumentException{
    if(row < 0 || col < 0)throw new IllegalArgumentException();
    return solveH(row, col, 1);
  }

  public static void main(String[] args){
    KnightBoard k = new KnightBoard(5,5);
    k.solve(0,0);
    System.out.println(k);
  }

}
