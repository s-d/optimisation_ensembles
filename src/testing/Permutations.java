package testing;

import java.util.ArrayList;
import java.util.Arrays;

public class Permutations {

    public static void main(String[] args0) {

        int maxValue = 8;

        ArrayList<int[]> results = new ArrayList<int[]>();

        for (int i = 0; i < maxValue; i++) {
            for (int j = 0; j < maxValue; j++) {
                for (int k = 0; k < maxValue; k++) {
                    int[] result = new int[3];
                    result[0] = i;
                    result[1] = j;
                    result[2] = k;
                    results.add(result);

                    System.out.println(Arrays.toString(result));
                }
            }
        }
        System.out.println(results.size());

    }

}
