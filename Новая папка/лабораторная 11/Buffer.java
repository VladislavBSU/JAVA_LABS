package task_13;

import java.io.*;
import java.util.zip.*;

public class Buffer {
	static final String zipEntryName = "z";
	
	static byte[] toByteArray (Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try ( ObjectOutputStream oos = new ObjectOutputStream(baos) ) {
			oos.writeObject(obj);
			oos.flush();
			return baos.toByteArray();
		}
	}
	static byte[] toZipByteArray (Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try ( ZipOutputStream zos = new ZipOutputStream(baos) ) {
			zos.putNextEntry(new ZipEntry(zipEntryName));
			zos.setLevel( ZipOutputStream.DEFLATED );
			try ( ObjectOutputStream oos = new ObjectOutputStream(zos) ) {
				oos.writeObject(obj);
				oos.flush();
				zos.closeEntry();
				zos.flush();
				return baos.toByteArray();
			}
		}
	}
	static Object fromByteArray (byte[] arr) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(arr);
		try ( ObjectInputStream ois = new ObjectInputStream(bais) ) {
			return ois.readObject();
		}
	}
	static Object fromZipByteArray (byte[] arr) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(arr);
		try ( ZipInputStream zis = new ZipInputStream(bais) ) {
			ZipEntry zEntry = zis.getNextEntry();
            if ( !zEntry.getName().equals(zipEntryName) )
                throw new IOException("Invalid block format");
			try ( ObjectInputStream ois = new ObjectInputStream(zis) ) {
				return ois.readObject();
			}
		}
	}
	
	public static long writeObject (RandomAccessFile file, Serializable obj, boolean zipped) throws IOException {
		long result = file.length();
		file.seek(result);
		byte[] arr;
		if (zipped) {
			arr = toZipByteArray(obj);
			file.writeByte(1);
		}
		else {
			arr = toByteArray(obj);
			file.writeByte(0);
		}
		file.writeInt(arr.length);
		file.write(arr);
		file.setLength(file.getFilePointer());
		return result;
	}
	public static Object readObject (RandomAccessFile file, long pos, boolean[] wasZipped) throws IOException, ClassNotFoundException {
		file.seek(pos);
		byte zipped = file.readByte();
		int length = file.readInt();
		byte[] arr = new byte[length];
		file.read(arr);
		if ( wasZipped != null )
            wasZipped[0] = (zipped != 0);
        if ( zipped == 0 )
            return fromByteArray(arr);
        else if ( zipped == 1 )
            return fromZipByteArray(arr);
        else
            throw new IOException("Invalid block format");
	}
	
}
