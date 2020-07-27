package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Kopija {

	public static void main(String[] args) throws IOException {
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream("C:\\Users\\Lenovo\\Documents\\default.png");
	        os = new FileOutputStream("C:\\Users\\Lenovo\\Documents\\kopija.png");
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	    System.out.println(("\\0").contains("\\"));

	}

}
