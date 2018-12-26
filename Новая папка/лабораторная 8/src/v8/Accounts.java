package v8;

import java.io.*;
import java.util.*;

public class Accounts {

	static final String filename = "Accounts.dat";
	static final String filenameBak = "Accounts.~dat";
    static final String idxname     = "Accounts.idx";
    static final String idxnameBak  = "Accounts.~idx";
    
    private static String encoding = "Cp866";
    private static PrintStream accOut = System.out;
	
	static Account readAccount (Scanner in) throws IOException {
		return Account.nextRead(in, accOut) ? Account.read(in, accOut) : null;
	}
	
	private static void deleteBackup () {
        new File(filenameBak).delete();
        new File(idxnameBak).delete();
    }
	static void deleteFile () {
		deleteBackup();
		new File(filename).delete();
        new File(idxname).delete();
		System.out.println("Files " + filename + " and " + idxname + " deleted successfully.");
	}
	private static void backup () {
        deleteBackup();
        new File(filename).renameTo(new File(filenameBak));
        new File(idxname).renameTo(new File(idxnameBak));
    }
	static boolean deleteFile (String[] args) throws ClassNotFoundException, IOException, KeyNotUniqueException {
        //-dk {d|p|b} key      - clear data by key
        if ( args.length != 3 ) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        long[] poss = null;
        try ( Index idx = Index.load(idxname) ) {
            IndexBase pidx = indexByArg(args[1], idx);
            if ( pidx == null ) {
                return false;
            }
            if ( !pidx.contains(args[2]) ) {
                System.err.println("Key not found: " + args[2]);
                return false;				
            }
            poss = pidx.get(args[2]);
        }
        backup();
        Arrays.sort(poss);
        try ( Index idx = Index.load(idxname); RandomAccessFile fileBak = new RandomAccessFile(filenameBak, "rw"); RandomAccessFile file = new RandomAccessFile(filename, "rw") ) {
            boolean[] wasZipped = new boolean[] { false };
            long pos;
            while ( (pos = fileBak.getFilePointer()) < fileBak.length() ) {
                Account acc = (Account) Buffer.readObject(fileBak, pos, wasZipped);
                if ( Arrays.binarySearch(poss, pos) < 0 ) {
                    long ptr = Buffer.writeObject(file, acc, wasZipped[0]);
                    idx.put(acc, ptr);
                }
             }
         }
         return true;
    }
	static void appendFile (String[] args, boolean zipped) throws FileNotFoundException, IOException, ClassNotFoundException, KeyNotUniqueException {
        if ( args.length >= 2 ) {
            FileInputStream stdin = new FileInputStream(args[1]);
            System.setIn(stdin);
            if ( args.length == 3 ) {
                encoding = args[2];
            }
            // hide output:
            accOut = new PrintStream("nul");
        }
        appendFile(zipped);
    }
	static void appendFile (boolean zipped) throws FileNotFoundException, IOException, ClassNotFoundException, KeyNotUniqueException {
        Scanner fin = new Scanner(System.in, encoding);
        accOut.println("Enter account data: ");
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
            while (true) {
                Account acc = readAccount(fin);
                if ( acc == null )
                    break;
                idx.test(acc);
                long pos = Buffer.writeObject(raf, acc, zipped);
                idx.put(acc, pos);
            }
        }
    }
	private static void printRecord (RandomAccessFile raf, long pos) throws ClassNotFoundException, IOException {
        boolean[] wasZipped = new boolean[] { false };
        Account acc = (Account) Buffer.readObject(raf, pos, wasZipped);
        if ( wasZipped[0] )
            System.out.print(" compressed");
        System.out.println(" record at position " + pos + ": \n" + acc);	
    }	
    private static void printRecord (RandomAccessFile raf, String key, IndexBase pidx) throws ClassNotFoundException, IOException {
        long[] poss = pidx.get(key);
        for (long pos: poss) {
            System.out.print("*** Key: " + key + " points to");
            printRecord(raf, pos);
        }
    }    
    static void printFile () throws FileNotFoundException, IOException, ClassNotFoundException {
    	System.out.println("Data from " + filename + ":\n");
    	long pos;
        int rec = 0;
        try ( RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
        	if ( raf.length() == 0 )
				System.out.println("...");
        	else {
	        	while ( (pos = raf.getFilePointer()) < raf.length() ) {
	                System.out.print("#" + (++rec));
	                printRecord(raf, pos);
	            }
	            System.out.flush();
        	}
        	System.out.println("\nFile " + filename + " has no more data.");
        }
    }
    private static IndexBase indexByArg (String arg, Index idx) {
        IndexBase pidx = null;
        if ( arg.equals("d") )
            pidx = idx.dates;
        else if ( arg.equals("p") )
            pidx = idx.providers;
        else if ( arg.equals("b") )
            pidx = idx.bounds;
        else
            System.err.println("Invalid index specified: " + arg);
        return pidx;
    }
    static boolean printFile (String[] args, boolean reverse) throws ClassNotFoundException, IOException {
        if ( args.length != 2 ) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
            IndexBase pidx = indexByArg(args[1], idx);
            if ( pidx == null )
                return false;
            String[] keys = pidx.getKeys( reverse ? new KeyCompReverse() : new KeyComp() );
            for (String key: keys)
                printRecord(raf, key, pidx);
        }
        return true;
    }
    static boolean findByKey (String[] args) throws ClassNotFoundException, IOException {
        if ( args.length != 3 ) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
            IndexBase pidx = indexByArg(args[1], idx);
            if ( !pidx.contains(args[2]) ) {
                System.err.println("Key not found: " + args[2]);
                return false;				
            }
            printRecord(raf, args[2], pidx);
        }
        return true;
    }
    static boolean findByKey (String[] args, Comparator<String> comp) throws ClassNotFoundException, IOException {
        if ( args.length != 3 ) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
            IndexBase pidx = indexByArg(args[1], idx);
            if ( !pidx.contains(args[2]) ) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            String[] keys = pidx.getKeys(comp);
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if ( key.equals(args[2]) )
                    break;
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }
        	
	public static void main(String[] args) {
		try {
			if ( args.length >= 1 )
				if ( args[0].equals("-?") || args[0].equals("-h") ) {
                    System.out.println("Syntax:\n" +
                        "\t-a  [file [encoding]] - append data\n" +
                        "\t-az [file [encoding]] - append data, compress every record\n" +
                        "\t-d                    - clear all data\n" +
                        "\t-dk  {d|p|b} key      - clear data by key\n" +
                        "\t-p                    - print data unsorted\n" +
                        "\t-ps  {d|p|b}          - print data sorted by dates/providers/bounds\n" +
                        "\t-psr {d|p|b}          - print data reverse sorted by dates/providers/bounds\n" +
                        "\t-f   {d|p|b} key      - find record by key\n" +
                        "\t-fr  {d|p|b} key      - find records > key\n" +
                        "\t-fl  {d|p|b} key      - find records < key\n" +
                        "\t-?, -h                - command line syntax\n");
                }
                else if ( args[0].equals("-a") ) // Append file with new object from System.in // -a [file [encoding]]
                    appendFile(args, false);
                else if ( args[0].equals("-az") ) // Append file with compressed new object from System.in // -az [file [encoding]]
                    appendFile(args, true);
                else if ( args[0].equals("-p") ) // Prints data file
                    printFile();
                else if ( args[0].equals("-ps") ) { // Prints data file sorted by key
                    if ( !printFile(args, false) )
                        System.exit(1);
                }
                else if ( args[0].equals("-psr") ) { // Prints data file reverse-sorted by key
                    if ( !printFile(args, true) )
                        System.exit(1);
                }
                else if ( args[0].equals("-d") ) { // delete files
                    if ( args.length != 1 ) {
                        System.err.println("Invalid number of arguments");
                        System.exit(1);
                    }
                    deleteFile();
                }
                else if ( args[0].equals("-dk") ) { // Delete records by key
                    if ( !deleteFile(args) )
                        System.exit(1);
                }
                else if ( args[0].equals("-f") ) { // Find record(s) by key
                    if ( !findByKey(args) )
                        System.exit(1);
                }
                else if ( args[0].equals("-fr") ) { // Find record(s) by key large then key 
                    if ( !findByKey(args, new KeyCompReverse()) )
                        System.exit(1);
                }
                else if ( args[0].equals("-fl") ) { // Find record(s) by key less then key
                    if ( !findByKey(args, new KeyComp()) )
                        System.exit(1);
                }
                else {
                    System.err.println("Option isn't realized: " + args[0]);
                    System.exit(1);
                }
			else
				System.out.println("Accounts.java: nothing to do! Enter -? for options");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("\nAccounts.java finished...");
	}
	
}