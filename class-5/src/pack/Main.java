package pack;

import java.util.*;

public class Main {
    static final int SIZE = 15;
    public static void main(String[] args) {
        try {
            Exponential[] array1 = new Exponential[SIZE];
            System.out.println(Series.titleString);
            for (int i = 0; i < SIZE; i++) {
                array1[i] = new Exponential((int) (-10 + Math.random() * 20), (int) (5 + Math.random() * 10));
                System.out.println(array1[i].toString());
            }

            Exponential.setSortBy(1);
            System.out.println("Sorting by " + Exponential.getSortBy() + " field:\n" + Series.titleString);
            Arrays.sort(array1);
            for (int i = 0; i < SIZE; i++) {
                System.out.println(array1[i].toString());
            }

            for (int i = 0; i < SIZE; i++) {
                while (array1[i].hasNext()) {
                    System.out.print(array1[i].next());
                }
            }
            System.out.println();

            for (int i = 0; i < SIZE; i++) {
                for (Integer integer : array1[i]) {
                    System.out.print(integer);
                }
            }
            System.out.println();

            int index = 1;
            for(Exponential serie : array1) {
                System.out.printf("Summary of %1$d progression %1$d elements: %2$d\n",index, serie.progSum(index++));
            }
        }
        catch (Exception e) {
            System.out.println( "Exception: " + e.getMessage() );
        }
    }
}

