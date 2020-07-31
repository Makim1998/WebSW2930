package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class Diskovi {
	public ArrayList<Disk> diskovi;
	public String path;
	private static Gson g = new Gson();
	
	public ArrayList<Disk> getDiskovi() {
		return diskovi;
	}
	public void setDiskovi(ArrayList<Disk> diskovi) {
		this.diskovi = diskovi;
	}
	public Diskovi() {
		super();
	}
	
	public Diskovi(String path) {
		diskovi = new ArrayList<Disk>();
		this.path = path;
		readFile();
		
	}
	
	private void readFile() {
		try {
			JsonReader reader = new JsonReader(new FileReader(this.path));
			this.setDiskovi(g.fromJson(reader, new TypeToken<ArrayList<Disk>>(){}.getType()));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeFile()  {
		FileWriter fw;
		try {
			fw = new FileWriter(this.path);
			String data = g.toJson(diskovi);
			System.out.println(data);
			System.out.println(data);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
