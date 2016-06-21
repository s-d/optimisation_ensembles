package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadAlgFile {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("res/algorithmOrder.csv"));
        scanner.useDelimiter("\r\n");

        int[] alg = new int[343];
        int counter = 0;

        while (scanner.hasNext()) {
            alg[counter] = Integer.parseInt(scanner.next());
            counter++;
        }
        scanner.close();

        for (int i : alg) {
            System.out.println(i);

        }
    }

}
