package model;
import java.io.FileReader;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
public class Fajl {
	private static Gson g = new GsonBuilder().setPrettyPrinting().create();
	
	public static void main(String[] args) {
		//KategorijaVM k1 = new KategorijaVM("A1",8,64,4);
		//KategorijaVM k2 = new KategorijaVM("A2",8,32,4);
		//KategorijaVM k3 = new KategorijaVM("A3",4,16,4);
		String path = System.getProperty("user.dir");
		System.out.println(path);
		Organizacija o1 = new Organizacija("Amazon", "Americka multinacionalna tehnološka kompanija bazirana u Sijetlu", "a");
		Organizacija o2 = new Organizacija("Google", "Americka javna korporacija, specijalizovana za internet pretragu i reklamiranje na internetu", "a");
		
		//Korisnik k1 = new Korisnik("ana@gmail.com","ana","Ana","Mijatovic","Intel",Uloga.KORISNIK);
		//Korisnik k2 = new Korisnik("marko@gmail.com","ana","Ana","Mijatovic","Nvidia",Uloga.ADMIN);
		ArrayList<Organizacija> organizacije = new ArrayList<Organizacija>();
		//organizacije.add(o3);
		organizacije.add(o1);
		organizacije.add(o2);
		String dir =  System.getProperty("user.dir");
		System.out.println(dir + "\\resources\\korisnici.txt");
		try {
			FileWriter fw = new FileWriter("organizacije.txt");
			String data = g.toJson(organizacije);
			System.out.println(data);
			
			System.out.println(data);
			fw.write(data);
			fw.close();
			//JsonReader jr = new JsonReader( new FileReader("data.txt"));
			//ArrayList<Korisnik> orisnici = g.fromJson(jr, new TypeToken<ArrayList<Korisnik>>(){}.getType());
			//for(Korisnik ko:orisnici) {
			//	System.out.println(ko);
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
