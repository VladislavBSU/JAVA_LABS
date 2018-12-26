package task_13;

import java.io.*;
import java.util.*;

public class Accounts {

	static final String filename = "Accounts.dat";
	static final String filenameBak = "Accounts.~dat";
    static final String idxname     = "Accounts.idx";
    static final String idxnameBak  = "Accounts.~idx";
    
    private static IndexBase indexByArg (String arg, Index idx) {
        IndexBase pidx = null;
        if ( arg.equals("d") )
            pidx = idx.dates;
        else if ( arg.equals("p") )
            pidx = idx.providers;
        else if ( arg.equals("b") )
            pidx = idx.bounds;
        else
            throw new IllegalArgumentException ("Invalid index specified: " + arg);
        return pidx;
    }
	
	private static void deleteBackup () {
        new File(filenameBak).delete();
        new File(idxnameBak).delete();
    }
	static void deleteFile () {
		deleteBackup();
		new File(filename).delete();
        new File(idxname).delete();
	}
	private static void backup () {
        deleteBackup();
        new File(filename).renameTo(new File(filenameBak));
        new File(idxname).renameTo(new File(idxnameBak));
    }
	static boolean deleteFile (String[] args) throws ClassNotFoundException, IOException, KeyNotUniqueException {
        //args = {d|p|b} key
        if ( args.length != 2 )
            throw new IllegalArgumentException ("Invalid number of arguments");
        long[] poss = null;
        try ( Index idx = Index.load(idxname) ) {
            IndexBase pidx = indexByArg(args[0], idx);
            if ( !pidx.contains(args[1]) )
                return false;
            poss = pidx.get(args[1]);
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
	static void appendFile (String fromFile, boolean zipped) throws ClassNotFoundException, IOException, KeyNotUniqueException {
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw"); RandomAccessFile from = new RandomAccessFile(fromFile, "r") ) {
            if ( from.length() == 0 )
            	return;
        	long pos;
            while ( (pos = from.getFilePointer()) < from.length() ) {
                Account acc = (Account) Buffer.readObject(from, pos, new boolean[] {false});
                if ( acc == null )
                    break;
                idx.test(acc);
                long pos2 = Buffer.writeObject(raf, acc, zipped);
                idx.put(acc, pos2);
            }
        }
    }	
	static void appendFile (Account acc, boolean zipped) throws ClassNotFoundException, IOException, KeyNotUniqueException {
        if ( acc == null )
        	return;
		try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
        	idx.test(acc);
            long pos = Buffer.writeObject(raf, acc, zipped);
            idx.put(acc, pos);
        }
    }
	static void saveTo (String toFile) throws IOException, ClassNotFoundException {
		try ( RandomAccessFile raf = new RandomAccessFile(toFile, "rw"); RandomAccessFile from = new RandomAccessFile(filename, "r") ) {
			if ( from.length() == 0 )
            	return;
        	long pos;
			while ( (pos = from.getFilePointer()) < from.length() ) {
                Account acc = (Account) Buffer.readObject(from, pos, new boolean[] {false});
                if ( acc == null )
                    break;
                Buffer.writeObject(raf, acc, false);
            }
        }
	}
	private static String printRecord (RandomAccessFile raf, long pos) throws ClassNotFoundException, IOException {
        String res = "";
		boolean[] wasZipped = new boolean[] { false };
        Account acc = (Account) Buffer.readObject(raf, pos, wasZipped);
        if ( wasZipped[0] )
            res += " compressed";
        res += " record at position " + pos + ":\n" + acc;
        return res;
    }
	static String printFile () throws FileNotFoundException, IOException, ClassNotFoundException {
    	String result = "";
    	long pos;
        int rec = 0;
        try ( RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
        	if ( raf.length() == 0 )
				result += "...";
        	else {
	        	while ( (pos = raf.getFilePointer()) < raf.length() ) {
	                result += "#" + (++rec);
	                result += printRecord(raf, pos);
	                result += "\n\n";
	            }
        	}
        }
        return result;
    }
	private static String printRecord (RandomAccessFile raf, String key, IndexBase pidx) throws ClassNotFoundException, IOException {
        String res = "";
    	long[] poss = pidx.get(key);
        for (long pos: poss) {
            res += "*** Key: " + key + " points to";
            res += printRecord(raf, pos);
        }
        res += "\n";
        return res;
    }    
    static String printFile (String[] args, boolean reverse) throws ClassNotFoundException, IOException {
        //args = {b|p|d}
    	String result = "";
    	if ( args.length != 1 )
            throw new IllegalArgumentException("Invalid number of arguments");
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
            if ( raf.length() == 0 )
            	result += "...";
            else {
	        	IndexBase pidx = indexByArg(args[0], idx);
	            String[] keys = pidx.getKeys( reverse ? new KeyCompReverse() : new KeyComp() );
	            for (String key: keys)
	                result += printRecord(raf, key, pidx);
            }
        }
        return result;
    }
    static String findByKey (String[] args) throws ClassNotFoundException, IOException {
        String res = "";
    	if ( args.length != 2 )
            throw new IllegalArgumentException("Invalid number of arguments");
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
            IndexBase pidx = indexByArg(args[0], idx);
            if ( pidx.contains(args[1]) )
            	res += printRecord(raf, args[1], pidx);
            else
            	res += "Key not found: " + args[1];
        }
        return res;
    }
    static String findByKey (String[] args, Comparator<String> comp) throws ClassNotFoundException, IOException {
        //args {b|d|p} key
    	String res = "";
    	if ( args.length != 2 )
    		throw new IllegalArgumentException("Invalid number of arguments");
        try ( Index idx = Index.load(idxname); RandomAccessFile raf = new RandomAccessFile(filename, "rw") ) {
            IndexBase pidx = indexByArg(args[0], idx);
            if ( !pidx.contains(args[1]) ) {
                res += "Key not found: " + args[1];
                return res;
            }
            String[] keys = pidx.getKeys(comp);
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if ( key.equals(args[1]) )
                    break;
                res += printRecord(raf, key, pidx);
            }
        }
        return res;
    }
    
}
