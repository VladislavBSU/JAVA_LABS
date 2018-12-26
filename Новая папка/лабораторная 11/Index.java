package task_13;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;

class KeyComp implements Comparator <String> {
	public int compare (String s1, String s2) {
		if ( Account.validDate(s1) && Account.validDate(s2) ) {
			Calendar calendar1 = new GregorianCalendar(), calendar2 = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    		sdf.setLenient(false);
    		try {
				calendar1.setTime(sdf.parse(s1));
				calendar2.setTime(sdf.parse(s2));
	    		return calendar1.before(calendar2) ? -1 : (calendar1.after(calendar2) ? 1 : 0);
			} catch (ParseException e) {}
		}
		return s1.compareTo(s2);
	}
}
class KeyCompReverse implements Comparator <String> {
	public int compare (String s1, String s2) {
		if ( Account.validDate(s1) && Account.validDate(s2) ) {
			Calendar calendar1 = new GregorianCalendar(), calendar2 = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    		sdf.setLenient(false);
    		try {
				calendar1.setTime(sdf.parse(s1));
				calendar2.setTime(sdf.parse(s2));
	    		return calendar2.before(calendar1) ? -1 : (calendar2.after(calendar1) ? 1 : 0);
			} catch (ParseException e) {}
		}
		return s2.compareTo(s1);
	}
}

interface IndexBase {
	String[] getKeys (Comparator <String> comp);
	void put (String key, long value);
	boolean contains (String key);
	long[] get (String key);
}

class IndexOneToOne implements Serializable, IndexBase {
	private static final long serialVersionUID = 1L;
	
	private TreeMap <String, Long> map;
	public IndexOneToOne () {
		map = new TreeMap <String, Long>();
	}
	public String[] getKeys (Comparator <String> comp) {
		String[] result = map.keySet().toArray(new String[0]);
		Arrays.sort(result, comp);
		return result;
	}
	public void put (String key, long value) {
		map.put(key, new Long(value));
	}
	public boolean contains (String key) {
		return map.containsKey(key);
	}
	public long[] get (String key) {
		long pos = map.get(key).longValue();
		return new long[] { pos };
	}
}

class IndexOneToN implements Serializable, IndexBase {
	private static final long serialVersionUID = 1L;
	
	private TreeMap <String, long[]> map;
	public IndexOneToN () {
		map = new TreeMap <String, long[]>();
	}
	public String[] getKeys (Comparator <String> comp) {
		String[] result = map.keySet().toArray(new String[0]);
		Arrays.sort(result, comp);
		return result;
	}
	public void put (String key, long value) {
		long[] arr = map.get(key);
		arr = (arr != null) ? Index.InsertValue(arr, value) : new long[] { value };
		map.put(key, arr);
	}
	public boolean contains (String key) {
		return map.containsKey(key);
	}
	public long[] get (String key) {
		return map.get(key);
	}
	public void put (String keys, String dels, long value) {
		StringTokenizer st = new StringTokenizer (keys, dels);
		int num = st.countTokens();
		for (int i = 0; i < num; ++i) {
			String key = st.nextToken();
			key.trim();
			put(key, value);
		}
	}
}

class KeyNotUniqueException extends Exception {
	private static final long serialVersionUID = 1L;

	public KeyNotUniqueException (String key) {
		super(new String("Key is not unique: " + key));
	}
}

public class Index implements Serializable, Closeable {
	
	private static final long serialVersionUID = 1L;

	public static long[] InsertValue (long[] arr, long value) {
		int length = (arr == null) ? 0 : arr.length;
		long[] result = new long[length + 1];
		result = Arrays.copyOf(arr, length+1);
		result[length] = value;
		return result;
	}
	
	IndexOneToOne dates;
	IndexOneToN providers;
	IndexOneToOne bounds;
	
	public void test (Account acc) throws KeyNotUniqueException {
		assert(acc!=null);
		if (dates.contains(acc.date))
			throw new KeyNotUniqueException(acc.date);
		if (bounds.contains(acc.date))
			throw new KeyNotUniqueException(acc.date);
	}
	public void put (Account acc, long value) throws KeyNotUniqueException {
		test(acc);
		dates.put(acc.date, value);
		providers.put(acc.provider, Account.providerDel, value);
		bounds.put(acc.getBounds(), value);
	}
	
	transient String filename = "";
	
	public Index () {
		dates = new IndexOneToOne();
		providers = new IndexOneToN();
		bounds = new IndexOneToOne();
	}
	
	public static Index load (String fname) throws IOException, ClassNotFoundException {
		Index obj = null;
		try {
			FileInputStream file = new FileInputStream(fname);
			try ( ZipInputStream zis = new ZipInputStream(file) ) {
				ZipEntry ze = zis.getNextEntry();
				if ( !ze.getName().equals(Buffer.zipEntryName) )
                    throw new IOException("Invalid block format");
                try ( ObjectInputStream ois = new ObjectInputStream(zis) ) {
                    obj = (Index)ois.readObject();
                }
			}
		}
		catch (FileNotFoundException e) {
			obj = new Index();
		}
		if ( obj != null )
			obj.save(fname);
		return obj;
	}
	public void save (String name) {
        filename = name;
    }
	public void saveAs (String fname) throws IOException {
		FileOutputStream file = new FileOutputStream(fname);
		try ( ZipOutputStream zos = new ZipOutputStream(file) ) {
            zos.putNextEntry(new ZipEntry(Buffer.zipEntryName));
            zos.setLevel(ZipOutputStream.DEFLATED);
            try ( ObjectOutputStream oos = new ObjectOutputStream(zos) ) {
                oos.writeObject(this);
                oos.flush();
                zos.closeEntry();
                zos.flush();
            }
        }
	}
	public void close () throws IOException {
        saveAs(filename);
    }
	
}
