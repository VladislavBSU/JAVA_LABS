package gauss;

public class Main {

    public static void main(String[] args) {
        int n = 5;
        double[][] matrix = new double[n][n];
        double[] x = new double[n];
        double[] f = new double[n];
        double[] X = new double[n];// x with line
        double[][] B_matrix = new double[n][n+1];//matrix + f

        //matrix elements ---------------------------------------------------------------------------------------------
        matrix[0][0] = 6;      matrix[0][1] = 0;      matrix[0][2] = -2;      matrix[0][3] = 0;      matrix[0][4] = -1;
        matrix[1][0] = 0;      matrix[1][1] = 5;      matrix[1][2] = 0;       matrix[1][3] = -2;     matrix[1][4] = 0;
        matrix[2][0] = -2;     matrix[2][1] = 0;      matrix[2][2] = 4;       matrix[2][3] = 0;      matrix[2][4] = -1;
        matrix[3][0] = 0;      matrix[3][1] = -2;     matrix[3][2] = 0;       matrix[3][3] = 3;      matrix[3][4] = 0;
        matrix[4][0] = 1;      matrix[4][1] = 0;      matrix[4][2] = -1;      matrix[4][3] = 0;      matrix[4][4] = 3;
        //-------------------------------------------------------------------------------------------------------------


        // elements x--------------------------------------------------------------------
        x[0] = 1.2  ;     x[1] = 2.3 ;    x[2] = -3.4 ;    x[3] = -4.5 ;   x[4] = 5.6  ;
        //-------------------------------------------------------------------------------


        //print matrix--------------------------------------
        System.out.println("First Matrix");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] >= 0)
                    System.out.print(matrix[i][j] + "    ");
                if (matrix[i][j] < 0)
                    System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
        //---------------------------------------------------


        //find f and create  B_matrix-------------
        for (int i = 0; i < n; i++) {
            double e = 0;
            for (int j = 0; j < n; j++) {
                e += matrix[i][j] * x[j];
                B_matrix[i][j] = matrix[i][j];
            }
            B_matrix[i][n] = e ;
        }
        //-----------------------------------------



        //print B_matrix-----------------------------------------------
        System.out.println();
        System.out.println();
        System.out.println("Bigger Matrix");
        for ( int i = 0 ; i < n; i++ ) {
            for ( int j = 0; j < n+1; j++ ) {
                    System.out.print( B_matrix[i][j] + "           " ) ;
            }
            System.out.println() ;
        }
        //-------------------------------------------------------------


        //way gauss---------------------------------------------------------------------------
        for ( int k = 0 ; k < n - 1 ; k++ )
            for ( int i = k + 1 ; i < n; i++ )
                for (int j = n ; j >= 0; j-- )
                    B_matrix[i][j] -= ( B_matrix[k][j] * B_matrix[i][k] / B_matrix[k][k] ) ;
        //-------------------------------------------------------------------------------------



        //print after gauss--------------------------------------------------------------------
        System.out.println();
        System.out.println("Bigger Matrix after gauss");
        for ( int i = 0 ; i < n; i++ ) {
            for ( int j = 0; j < n+1; j++ ) {
                System.out.print( B_matrix[i][j] + "               " ) ;
            }
            System.out.println() ;
        }
        //------------------------------------------------------------------------------------




        //find X-------------------------------------------------------------
        for ( int k = 0; k < n - 1; k++ )
            for ( int i = n - 1 ; i >= 0 ; i-- ) {
                X[i] = B_matrix[i][n] / B_matrix[i][i];
                for (int c = n - 1;c > i;c--)
                    X[i] -= B_matrix[i][c] * X[c] /  B_matrix[i][i];
            }
        //---------------------------------------------------------------------

        //print x---------------------------------------------------------------
        System.out.println();
        System.out.println("Start x");
        for ( int i = 0 ; i < n; i++ ) {
            System.out.print( x[i] + "           " ) ;
        }
        //----------------------------------------------------------------------



        //print X---------------------------------------------------------------
        System.out.println();
        System.out.println();
        System.out.println("Finish X");
        for ( int i = 0 ; i < n; i++ ) {
                System.out.print( X[i] + "     " ) ;
                    }
        System.out.println();
        //----------------------------------------------------------------------


    }
}
