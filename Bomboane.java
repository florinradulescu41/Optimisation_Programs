import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Bomboane {
    
    static class Task {
        
        public static final String INPUT_FILE = "bomboane.in";
        public static final String OUTPUT_FILE = "bomboane.out";
        int N; //numarul de copii
        int M; //numarul de bomboane
        private final static int MOD = 1000000007;
        
        //interval de tip [x,y]
        class Pair{
            
            public int x;
            public int y;

            public Pair(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        
        ArrayList<Pair> v = new ArrayList<>();
        
        //citire date de intrare
        private void readInput() {
            try {
                BufferedReader sc = new BufferedReader(new FileReader(
                                                INPUT_FILE));
                String line = sc.readLine();    
                N = Integer.parseInt(line.split(" ")[0]);
                M = Integer.parseInt(line.split(" ")[1]);
                String line2 = sc.readLine();
                while(line2 != null){
                    v.add(new Pair(Integer.parseInt(line2.split(" ")[0]),
                            Integer.parseInt(line2.split(" ")[1])));
                    line2 = sc.readLine();
                }
                sc.close();
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        //scriere date de iesire
        private void writeOutput(Number result) {
            try {
                FileWriter fw = new FileWriter(OUTPUT_FILE);
                fw.write(result.toString());
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //calcul solutie
        private long compute_solution() {
            long dp[][] = new long [N+1][M+1];
            long difference[] = new long[N];
            for (int i = 0; i< N;i++){
                int x, y;
                x = v.get(i).x;
                y = v.get(i).y;
                M -= x;
                if(i>=1)
                    dp[i][0] = 1;
                difference[i]= y-x;
            }
            dp[N][0] = 1;
            
                for (int i = 1; i <= N; i++){
                    boolean flag = true;
                    for(int j = 1; j <= M; j++){
                        if(i == 1 && flag)
                            if(j <= difference[0]){
                                dp[1][j]=1;
                                
                            }
                            else{flag = false;}
                        for(int k = j; k >= 0; k--){
                            if(j - k <= difference[i-1]){
                                dp[i][j] = (dp[i][j] %MOD + dp[i-1][k]%MOD ) %MOD;
                            }
                      else
                          break;
                        }
                    }
                }
            return dp[N][M] %MOD %MOD;
        }
        
        //rezolvarea problemei
        public void solve() {
            readInput();
            compute_solution();
            writeOutput(compute_solution());
        }
    }
    
    //main
    public static void main(String[] args) {
        new Task().solve();
    }
    
}