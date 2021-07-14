import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Gard {
    
    static class Task {
        
        class Pair { //va retine coordonatele intervalelor
            public int start; //x
            public int end; //y

            public Pair(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
        
        //pentru a sorta intervalele crescator dupa x, apoi dupa y
        class Cmp implements Comparator<Pair> {
            public int compare(Pair a, Pair b) {
                return (a.start == b.start) ? 
                        (b.end) - (a.end) :
                        (a.start) - (b.start);
            }
        }
        
        public static final String INPUT_FILE = "gard.in";
        public static final String OUTPUT_FILE = "gard.out";
        long N; //numarul de bucati/intervale
        ArrayList<Pair> v = new ArrayList<>(); //va stoca intervalele
        
        //citire date de intrare
        private void readInput() {
            try {
                BufferedReader sc = new BufferedReader(new FileReader(
                                                INPUT_FILE));
                String line = sc.readLine();
                N = parseInt(line);
                line = sc.readLine();
                while (line != null) {
                    v.add(new Pair(Integer.parseInt(line.split(" ")[0]),
                            Integer.parseInt(line.split(" ")[1])));
                    line = sc.readLine();
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        //scriere date de iesire
        private void writeOutput(long result) {
            try {
                PrintWriter pw = new PrintWriter(
                        new BufferedWriter(new FileWriter(
                                            OUTPUT_FILE)));
                pw.printf("%d/n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        //calculeaza rezultatul
        private long compute_solution() {
            
            long end = v.get(0).end; //primul y
            int not_discarded = 1; //bucati pe care le pastram
            Collections.sort(v, new Cmp()); //sortare
            for (int i = 1; i < N; i++) { //parcurge toate bucatile
                if (v.get(i).end > end) { //daca nu e inclusa, mareste gardul
                    end = v.get(i).end;
                    not_discarded++;
                }
            }
            return N - not_discarded; //bucatile excluse/redundante
        }
        
        //rezolva problema
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
