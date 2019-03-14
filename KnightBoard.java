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

  public String toString(){
    String ans = "";
    ans = ans + "Row: " + row + " Col: " + col;
    return ans;
  }


}


public class KnightBoard{
  public int[][] board;
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

  public static String printPiece1(Piece[][] l){
    String ans = "";
    for(int i = 0; i < l.length; i++){
      ans += "\n";
      for(int j = 0; j < l[i].length; j++){
        ans+= l[i][j].moves + " ";
      }

  }
  return ans;
}



public String toString(){
  String output = "";
  for (int r = 0; r < board.length; r++){
    for (int c = 0; c < board[r].length;c++){
      if (board[r][c] == 0){
        output += " _ ";
      }
      else if (board[r][c] < 10){
        output += " " + board[r][c] + " ";
      }
      else{
        output += board[r][c] + " ";
      }
    }
    output += "\n";
  }
  return output;
}

  public boolean clear(){
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
          board[i][j] = 0;
        }
    }
    return true;
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
        Piece p = new Piece(i, j, mcounter);
        posMoves[i][j] = p;
        System.out.println(p);
        //System.out.println(printPiece1(posMoves));

      }
    }
    return solveH(startingRow, startingCol, 1, posMoves);
  }


  public boolean solveH(int row, int col, int level, Piece[][] posMoves){

  //If level is the total area, return true and finish the solution
  if(level == board.length * board[1].length){
    board[row][col] = level;
    return true;
  }
  ArrayList<Piece> options = new ArrayList<Piece>();
  //else were going to look at all the possible moves and put them into an arrayList
  for(int i = 0; i < 8; i++){
    //make sure its a valid move
    //System.out.println(Moves[i][1]);
    if(row + Moves[i][0] >= 0 &&
       row + Moves[i][0] < startingRows &&
       col + Moves[i][1] >= 0 &&
       col + Moves[i][1] < startingCols &&
       board[row + Moves[i][0]][col+Moves[i][1]] == 0
       ){
         //if it is, add it to the ArrayList of options to move to
         //System.out.println(posMoves[row + Moves[i][0]][col+Moves[i][1]]);

         options.add(posMoves[row + Moves[i][0]][col+Moves[i][1]]);
         //System.out.println("Moves");
         //System.out.println(row + Moves[i][0]);
        // System.out.println(col+Moves[i][1]);


       }
  }
  //System.out.println(printPiece(posMoves));
  //now sort options from moves least to greatest
  Collections.sort(options);
  ////////////////////////////
  if(options.size() > 0){
    for(int i = 0; i < options.size(); i++){
      //System.out.println(options.get(i));
      //in options removing all the possible moves from the amount of possibilities --> ADD THEM BACK IF FALSE
      options.get(i).moves --;
    }
    //now add the level
    //System.out.println("row:"  + row);
    //System.out.println("col:"  + col);
    //System.out.println(level);
    board[row][col] = level;
    //if there are remaining options, set this value equal to the level and move on
    for(int i = 0; i < options.size(); i++){
      Piece next = options.get(i);
      //System.out.println(next.row);
      if(solveH(next.row, next.col, level + 1, posMoves)){

        return true;

      }
    }
  }
  //now, if we got a false then function is still alive, go back into options if size > 0 and give the possible move back
  //add one to the possible moves
  if(options.size() > 0){
    for(int i = 0; i < options.size(); i++){
      //in options removing all the possible moves from the amount of possibilities --> ADD THEM BACK IF FALSE
      options.get(i).moves ++;
    }
  }
  board[row][col] = 0;
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
                        int r = row + Moves[i][0];
                        int c = col + Moves[i][1];
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







/*





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
