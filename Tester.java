public class Tester{
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
        System.out.print(b);
      }else{
        System.out.println("FAIL board size: "+m[i%m.length]+"x"+n[i%m.length]+" "+ans+" vs "+correct);
        System.out.print(b);
        System.out.print("00000");
      }
    }catch(Exception e){
      System.out.println("FAIL Exception case: "+i);

    }

  }
}

public static void main(String[] args){
  runTest(4);
  KnightBoard b = new KnightBoard(16, 16);
  System.out.println(b.solve(0,0));
}

}
