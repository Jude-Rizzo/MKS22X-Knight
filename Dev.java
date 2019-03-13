import java.util.Arrays;
import java.util.Random;
import java.lang.Math;
public class Dev{
  public static String printPiece(KnightBoard b){
    Piece[][] l = b.posMoves;
    String ans = "";
    for(int i = 0; i < l.length; i++){
      ans += "\n";
      for(int j = 0; j < l[i].length; j++){
        ans+= l[i][j].moves + " ";
      }

  }
  return ans;
}

  public static void main(String[] a){
  KnightBoard b = new KnightBoard(12,12);
  b.solve(2,4);
  //System.out.println(printPiece(b));
  //ArrayList of Pieces works
  System.out.print(b.toString());
}
}
