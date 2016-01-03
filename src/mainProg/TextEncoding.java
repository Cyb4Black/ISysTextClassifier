package mainProg;

import java.io.*;
import java.nio.charset.Charset;

public class TextEncoding {

	public static void main(String[] args) {
		InputStream is;
		try {
			is = new FileInputStream("textSources/Nachrichten/Training/Das Duell.txt");
			InputStreamReader isr = new InputStreamReader(is, Charset.forName("Windows-1252"));
			BufferedReader reader = new BufferedReader(isr);
			String line = "";
			System.out.println(isr.getEncoding());
			while(((line = reader.readLine()) != null)){
				System.out.println(line);
			}
			reader.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
