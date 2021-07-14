import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Bani {
    
    static class Task {
        
        public static final String INPUT_FILE = "bani.in";
        public static final String OUTPUT_FILE = "bani.out";
        int iType;  //tipul instructiunii (1 sau 2)
        int N;  //numarul de bancnote
        private final static int MOD = 1000000007;  //pentru numere mari

        //citeste datele de intrare din fisierul specificat
        private void readInput() {
            try {
                BufferedReader sc = new BufferedReader(
                        new FileReader(INPUT_FILE));
                String line = sc.readLine();
                sc.close(); //avem date doar pe prima linie
                iType = Integer.parseInt(line.split(" ")[0]);  //citire
                N = Integer.parseInt(line.split(" ")[1]);
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        //scrie rezultatul in fisierul de iesire specificat
        private void writeOutput(Number result) {
            try {
                FileWriter fw = new FileWriter(OUTPUT_FILE);
                fw.write(result.toString());
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //functie pentru ridicarea la putere a numerelor mari <- din laborator
        long fast_pow(int base, int exponent) {
            
            if (exponent == 0)
                return 1;
            if (exponent % 2 == 0) {
                int rez = (int) ((1L * base * base)%MOD);
                return fast_pow(rez, exponent/2);
            }
            else {
                int rez= (int) (1L * base * fast_pow(base, exponent - 1)%MOD);
                return rez;
            }
        }

        //functia in care calculam solutia problemei
        private long compute_solution() {
            
            //daca avem instructiuni de tip 1, rezulatul are forma constanta:
            if (iType == 1) {
                return 5 * fast_pow(2, N - 1)%MOD;
            }
            
            //la instructiunile de tip 2 aplicam DP
            else {
                long dp[][] = new long [N + 1][5];
                long res = 0;
                
                dp[0][0] = 1;   //dp pentru: prima bancnota este de 10
                dp[0][1] = 1;   //dp pentru: prima bancnota este de 50
                dp[0][2] = 1;   //dp pentru: prima bancnota este de 100
                dp[0][3] = 1;   //dp pentru: prima bancnota este de 200
                dp[0][4] = 1;   //dp pentru: prima bancnota este de 500
                
                /*numarul de bancnote de un anumit tip la momentul k este egal
                cu numarul de bancnote care genereaza acel tip la momentul k-1*/
                for (int i = 0; i < N; i++) {
                    dp[i + 1][0] = dp[i][1]%MOD + dp[i][2]%MOD + dp[i][4]%MOD;
                    dp[i + 1][1] = dp[i][0]%MOD + dp[i][3]%MOD;
                    dp[i + 1][2] = dp[i][0]%MOD + dp[i][2]%MOD + dp[i][3]%MOD;
                    dp[i + 1][3] = dp[i][1]%MOD + dp[i][4]%MOD;
                    dp[i + 1][4] = dp[i][3]%MOD;
                }
                for (int j = 0; j < 5; j++) {
                    res += dp[N - 1][j]%MOD;
                }
                /*suma tuturor tipurilor de bancnote la momentul N
                                        (pozitia N-1 in vector)*/
                return res%MOD;
            }
        }
        
        //functia de rezolvare a problemei (citire+calcul+scriere)
        public void solve() {
            readInput();
            compute_solution();
            writeOutput(compute_solution());
        }
    }
    
    //in main apelam rezolvarea problemei
    public static void main(String[] args) {
        new Task().solve();
    }
    
}