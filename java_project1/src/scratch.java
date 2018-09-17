class Scratch {
    public static void main(String[] args) {
        if (args.length != 2){
            System.err.println(" ");
            System.exit(1);
        }
        double x = Double.parseDouble(args[0]);
        int k = Integer.parseInt(args[1]);
        if(k <= 1)
        {
            System.err.println(" ");
            System.exit(1);
        }
        double step = 1;
        double result = step;
        int i = 0;
        double Eps = 1 / Math.pow( 10, k + 1 );
        while( Math.abs(step) >= Eps ){
            step = step * x * x / ( ( i + 1 ) * (i + 2) );
            result += step;
            i += 2;
        }
        double f;
        f=(Math.exp(x)+Math.exp(-x))/2;
        String fmt = "%10." + k + "f\n";
        System.out.format(fmt,result);
        System.out.format(fmt,f);
        System.exit(0);

    }
}