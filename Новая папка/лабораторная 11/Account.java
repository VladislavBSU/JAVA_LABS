package task_13;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String toyCode;
	static final String P_toyCode = "ToyCode";	
	String toyName;
	static final String P_toyName = "ToyName";	
	int lowerBound;
	static final String P_lowerBound = "LowerBound";	
	int upperBound;
	static final String P_upperBound = "UpperBound";	
	double price;
	static final String P_price = "Price";	
	int count;
	static final String P_count = "Count";
	String date;
	static final String P_date = "Date";	
	String provider;
	static final String P_provider = "Providers";
	public static final String providerDel = ",";
	public static final String areaDel = "\n";
	
	public String getBounds () { return ((lowerBound<10)?"0":"") + lowerBound + "-" + ((upperBound<10)?"0":"") + upperBound; }
	
	public Account () { }
	
	static boolean validToyCode(String code) {
        if ( code.length() != 6 )
        	return false;
        if ( code.charAt(0) == '0' )
        	return false;
		try {
			Integer.parseInt(code);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	static boolean validLowerBound (int a) {
    	return a >= 0 && a <= 100;
    }
    static boolean validUpperBound (int upperB, int lowerB) {
    	return upperB >= 0 && upperB <= 100 && upperB >= lowerB;
    }
	static boolean validBounds (String bounds) {
		int idx = bounds.indexOf('-');
		if ( idx == bounds.lastIndexOf('-') && idx == 2 ) {
			try {
				int l = Integer.parseInt(bounds.substring(0, 2));
				int u = Integer.parseInt(bounds.substring(3));
				return Account.validLowerBound(l) && Account.validUpperBound(u, l);
			} catch (NumberFormatException e) {}
		}
		return false;
	}
    static boolean validPrice (double p) {
		return p >= 0;
	}
	static boolean validCount (int c) {
		return c >= 0;
	}
    static boolean validDate (String date) {
    	if ( date == null )
    		throw new NullPointerException();
    	try {
    		Calendar calendar = new GregorianCalendar();
        	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    		sdf.setLenient(false);
    		calendar.setTime(sdf.parse(date));
    		if ( calendar.after(Calendar.getInstance()) )
    			return false;
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }
	
    public static Account getAccountByArgs (String... args) {
    	if ( args.length != 8 )
    		throw new IllegalArgumentException("Illegal number of arguments.");
    	Account acc = new Account();
    	if ( !Account.validToyCode(args[0]) )
			throw new IllegalArgumentException("Value task_13.Account.toyCode should have only 6 digits.");
		acc.toyCode = args[0];
		acc.toyName = args[1];
		acc.lowerBound = Integer.parseInt(args[2]);
		if ( !Account.validLowerBound(acc.lowerBound) )
			throw new IllegalArgumentException ("Invalid argument: task_13.Account.lowerBound = " + acc.lowerBound);
		acc.upperBound = Integer.parseInt(args[3]);
		if ( !Account.validUpperBound(acc.upperBound, acc.lowerBound) )
			throw new IllegalArgumentException ("Invalid argument: task_13.Account.upperBound = " + acc.upperBound);
		acc.price = Double.parseDouble(args[4]);
		if ( !Account.validPrice(acc.price) )
			throw new IllegalArgumentException ("Invalid argument: task_13.Account.price = " + acc.price);
		acc.count = Integer.parseInt(args[5]);
		if ( !Account.validCount(acc.count) )
			throw new IllegalArgumentException ("Invalid argument: task_13.Account.count = " + acc.count);
		if ( !Account.validDate(args[6]) )
			throw new IllegalArgumentException ("Invalid argument: task_13.Account.date = " + args[6]);
		acc.date = args[6];
		acc.provider = args[7];		
		return acc;
    }
    
	public String toString () {
		return new String(toyCode + areaDel + toyName + areaDel + getBounds() + areaDel + price + areaDel
				+ count + areaDel + date + areaDel + provider);
	}
	
	
}
