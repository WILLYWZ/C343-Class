import org.hamcrest.MatcherAssert;

import javax.swing.text.GapContent;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;

class minDistance {
    final static int GAP = 2;
    final static int MATCH = 0;
    final static int MISMATCH = 1;

    enum BASE { A, T, G, C }

    // recursive min distance
    static int minDistance (List<BASE> dna1, List<BASE> dna2) {
        try {
            int current = dna1.getFirst() == dna2.getFirst() ? MATCH : MISMATCH;
            int d1 = current + minDistance(dna1.getRest(), dna2.getRest());
            int d2 = GAP + minDistance(dna1.getRest(), dna2);
            int d3 = GAP + minDistance(dna1, dna2.getRest());
            return d1 < d2 ? d1 : d2;
        }
        catch (EmptyListE e) {
            if (dna1.isEmpty()) return GAP * dna2.length();
            else return GAP * dna1.length();
        }
    }

    static Map<Pair<List<BASE>,List<BASE>>,Integer> minDistanceMemo = new WeakHashMap<>();

    // memoized (top down) min distance
    static int mminDistance (List<BASE> dna11, List<BASE> dna21) {
        return minDistanceMemo.computeIfAbsent(new Pair<>(dna11, dna21), p -> {
            List<BASE> dna1 = p.getFirst();
            List<BASE> dna2 = p.getSecond();
            try {
                int current = dna1.getFirst() == dna2.getFirst() ? MATCH : MISMATCH;
                int d1 = current + mminDistance(dna1.getRest(), dna2.getRest());
                int d2 = GAP + mminDistance(dna1.getRest(), dna2);
                int d3 = GAP + mminDistance(dna1, dna2.getRest());
                return Math.min(d1,Math.min(d2,d3));
            }
            catch (EmptyListE e) {
                if (dna1.isEmpty()) return GAP * dna2.length();
                else return GAP * dna1.length();
            }
        });
    }

    // bottom up min distance
    static int buminDistance (List<BASE> dna11, List<BASE> dna21) {
        ArrayList<BASE> dna1 = dna11.toArrayList();
        ArrayList<BASE> dna2 = dna21.toArrayList();
        int size1 = dna1.size()+1;
        int size2 = dna2.size()+1;
        int[][]array = new int[size1][size2];

        // horizontal
        for (int i = 1; i < size2; i++){
            array[0][i] = array[0][i-1] + GAP;
        }

        // vertical
        for (int i = 1; i < size1; i++){
            array[i][0] = array[i-1][0] + GAP;
        }

        // fill the table
        for (int i = 1; i < size1; i++){
            for(int j = 1; j < size2;j++){
                int ver = array[i][j-1] + GAP;
                int left =array[i-1][j] + GAP;
                int diagonal;

                if (dna1.get(i-1) ==  dna2.get(j-1)){
                    diagonal = array[i-1][j-1] + MATCH;
                }else{
                    diagonal = array[i - 1][j - 1]+ MISMATCH;
                }
                array[i][j]= Math.min(diagonal, Math.min(left,ver));
            }
        }
        return array[size1-1][size2-1];
    }
}
