package com.company;
import java.util.Scanner;
import java.util.Random;
public class Main {





        public static void main(String[] args) {
            int kol = 0;
            Scanner in = new Scanner(System.in);
            System.out.print("Enter n: ");
            int n = in.nextInt();
            if ( n <= 1 ) {
                System.err.println(
                        "Invalid n value (require: n > 1)");
                System.exit( 1 );
            }
            int[][] a = new int [n][n];
            Random rnd = new Random() ;
            rnd.setSeed( System.currentTimeMillis() );



            System.out.println("Source values: ");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int temp = rnd.nextInt();
                    a[i][j] = temp % (n + 1);
                    System.out.print(a[i][j] + "  ");

                }
                System.out.println();
            }


            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //угловые точки ----------------------------
                    //верхний левый
                    if(i==0 && j==0 && a[i][j]<a[i+1][j] && a[i][j]<a[i+1][j+1] && a[i][j]<a[i][j+1])
                    {
                        kol+=1;
                    }
                    //нижний правый
                    if(i==n-1 && j==n-1 && a[i][j]<a[n-2][j] && a[i][j]<a[n-2][n-2] && a[i][j]<a[i][n-2])
                    {
                        kol+=1;
                    }
                    //верхний правый
                    if(i==0 && j==n-1 && a[i][j]<a[i+1][j] && a[i][j]<a[i+1][j-1] && a[i][j]<a[i][j-1])
                    {
                        kol+=1;
                    }
                    //нижний левый
                    if(i==n-1 && j==0 && a[i][j]<a[i-1][j] && a[i][j]<a[i-1][j+1] && a[i][j]<a[i][j+1])
                    {
                        kol+=1;
                    }
                    //-------------------------------------------


                    //верхняя и нижняя горизонтали---------------
                    //верхняя
                    if ( i==0 && j>0 && j<n-1 &&  a[i][j]<a[i+1][j-1]  && a[i][j]<a[i][j-1] && a[i][j]<a[i+1][j]
                            && a[i][j]<a[i+1][j+1] && a[i][j]<a[i][j+1]  )
                    {
                        kol+=1;
                    }

                    //нижняя
                    if ( i==n-1 && j>0 && j<n-1 &&  a[i][j]<a[i-1][j-1]  && a[i][j]<a[i][j-1] && a[i][j]<a[i-1][j]
                            && a[i][j]<a[i-1][j+1] && a[i][j]<a[i][j+1]  )
                    {
                        kol+=1;
                    }
                    //-------------------------------------------

                    //крайняя левая и правая вертикали-----------
                    //левая
                    if ( i>0 && i<n-1 && j==0 && a[i][j]<a[i-1][j]  && a[i][j]<a[i-1][j+1] && a[i][j]<a[i][j+1]
                            && a[i][j]<a[i+1][j+1] && a[i][j]<a[i+1][j] )
                    {
                        kol+=1;
                    }
                    //правая
                    if ( i>0 && i<n-1 && j==n-1 && a[i][j]<a[i-1][j]  && a[i][j]<a[i-1][j-1] && a[i][j]<a[i][j-1]
                            && a[i][j]<a[i+1][j-1] && a[i][j]<a[i+1][j] )
                    {
                        kol+=1;
                    }
                    //--------------------------------------------

                    //элементы с четырьмя соседями----------------

                    if(i>0 && j>0 && i<n-1 && j<n-1
                            && a[i][j]<a[i+1][j]
                            && a[i][j]<a[i+1][j+1]
                            && a[i][j]<a[i][j+1]
                            && a[i][j]<a[i-1][j+1]
                            && a[i][j]<a[i-1][j]
                            && a[i][j]<a[i-1][j-1]
                            && a[i][j]<a[i][j-1]
                            && a[i][j]<a[i+1][j-1] )
                    {
                        kol+=1;
                    }

                    //---------------------------------------------

                }
                System.out.println();
            }

            System.out.println("кол-во локальных минимумов: " + kol);


            System.exit(0);
        }
    }



