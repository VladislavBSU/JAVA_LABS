package v8;

import java.io.*;
import java.util.Scanner;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String toyCode;
	static final String P_toyCode = "ToyCode";	
	String toyName;
	static final String P_toyName = "ToyName";	
	private int lowerBound;
	static final String P_lowerBound = "LowerBound";	
	private int upperBound;
	static final String P_upperBound = "UpperBound";	
	double price;
	static final String P_price = "Price";	
	int count;
	static final String P_count = "Count";	
	String date;
	static final String P_date = "Date";	
	String provider;
	static final String P_provider = "Provider";
	public static final String providerDel = ",";
	public static final String areaDel = "\n";
	
	public String getBounds () { return ((lowerBound<10)?"0":"") + lowerBound + "-" + upperBound; }
	
	public Account () { }
	
	static boolean validToyCode(String code) {
        if ( code.length() != 6 )
        	return false;
		int i = 0, ndig = 0;
        for ( ; i < code.length(); i++ )
            if ( Character.isDigit(code.charAt(i)) )
                ndig++;
        return ( ndig == code.length() );
    }
	static boolean validLowerBound (int a) {
    	return a >= 0 && a <= 100;
    }
    static boolean validUpperBound (int upperB, int lowerB) {
    	return upperB >= 0 && upperB <= 100 && upperB >= lowerB;
    }
	static boolean validPrice (double p) {
		return p >= 0;
	}
	static boolean validCount (int c) {
		return c >= 0;
	}
    
	public String toString () {
		return new String(toyCode + areaDel + toyName + areaDel + lowerBound + "-" + upperBound + areaDel + price + areaDel
				+ count + areaDel + date + areaDel + provider);
	}
	
	
	
	public static boolean nextRead(Scanner in, PrintStream out) {
        return nextRead(P_toyCode, in, out);
    }	
    static boolean nextRead(final String prompt, Scanner in, PrintStream out) {
        out.print(prompt + ": ");
        return in.hasNextLine();
    }
    
    
    
    
    public static Account read (Scanner in, PrintStream out) throws IOException {
		Account a = new Account();
		a.toyCode = in.nextLine();
		if ( !Account.validToyCode(a.toyCode) )
			throw new IOException("Value task_13.Account.toyCode should have only 6 digits.");
		
		if ( !nextRead(P_toyName, in, out) )
			return null;
		a.toyName = in.nextLine();
		
		if ( !nextRead(P_lowerBound, in, out) )
			return null;
		a.lowerBound = Integer.parseInt(in.nextLine());
		if ( !Account.validLowerBound(a.lowerBound) )
			throw new IOException ("Invalid argument: task_13.Account.lowerBound = " + a.lowerBound);
		
		if ( !nextRead(P_upperBound, in, out) )
			return null;
		a.upperBound = Integer.parseInt(in.nextLine());
		if ( !Account.validUpperBound(a.upperBound, a.lowerBound) )
			throw new IOException ("Invalid argument: task_13.Account.upperBound = " + a.upperBound);
		
		if ( !nextRead(P_price, in, out) )
			return null;
		a.price = Double.parseDouble(in.nextLine());
		if ( !Account.validPrice(a.price) )
			throw new IOException ("Invalid argument: task_13.Account.price = " + a.price);
		
		if ( !nextRead(P_count, in, out) )
			return null;
		a.count = Integer.parseInt(in.nextLine());
		if ( !Account.validCount(a.count) )
			throw new IOException ("Invalid argument: task_13.Account.count = " + a.count);
		
		if ( !nextRead(P_date, in, out) )
			return null;
		a.date = in.nextLine();
		
		if ( !nextRead(P_provider, in, out) )
			return null;
		a.provider = in.nextLine();
		
		return a;
	}
	
}