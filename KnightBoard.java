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


public static void main(String[] args){
  for(int i = 0; i < 5; i++){
    runTest(i);
  }
  KnightBoard c = new KnightBoard(6,10);
  c.solve(0,1);
  System.out.println(c);

}


}
