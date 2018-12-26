package task_13;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Maine {

	public static void main(String[] args) throws IOException {
		File f = new File("data2.txt");
		f.createNewFile();
		PrintWriter pw = new PrintWriter(f);
		for (int i = 0; i < 40; ++i) {
			int num = new Random().nextInt(3) + 2;
			ArrayList<Character> list = new ArrayList<Character>();
			list.add('a'); list.add('b'); list.add('c'); list.add('e'); list.add('f'); list.add('g');
			for (int j = 0; j < num - 1; ++j) {
				char ch = list.get(new Random().nextInt(list.size()));
				pw.print( ch + " " );
				list.remove(list.indexOf(ch));
			}
			pw.println( list.get(new Random().nextInt(list.size())) );
		}
		pw.flush();
		//System.out.println("done");
	}

}
