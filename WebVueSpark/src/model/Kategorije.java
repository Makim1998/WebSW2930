package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import json.KategorijaIzmena;

public class Kategorije {
	public ArrayList<KategorijaVM> kategorije;
	public String path;
	private static Gson g = new Gson();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<KategorijaVM> getKategorije() {
		readFile();
		return this.kategorije;

	}

	public void setKategorije(ArrayList<KategorijaVM> kategorije) {
		this.kategorije = kategorije;
	}
	
	public Kategorije() {
		
	}
	
	public Kategorije(String path) {
		kategorije = new  ArrayList<KategorijaVM>();
		this.path = path;
		readFile();
	}
	
	
	
	private void readFile() {
		try {
			JsonReader reader = new JsonReader(new FileReader(this.path));
			this.setKategorije(g.fromJson(reader, new TypeToken<ArrayList<KategorijaVM>>(){}.getType()));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeFile()  {
		FileWriter fw;
		try {
			fw = new FileWriter(this.path);
			String data = g.toJson(kategorije);
			System.out.println(data);
			System.out.println(data);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public KategorijaVM getKategorija(String ime) {
		for(KategorijaVM k : kategorije) {
			if(k.getIme().equals(ime)) {
				return k;
			}
		}
		return null;
	}
	
	public void dodaj(KategorijaVM k) {
		this.kategorije.add(k);
		writeFile();
	}
	
	public void obrisi(String  ime) {
		KategorijaVM zaBrisanje = getKategorija(ime);
		this.kategorije.remove(zaBrisanje);
		writeFile();
	}
	
	public boolean izmena(KategorijaIzmena k) {
		if(k.staro.equals(k.ime)) {
			if(getKategorija(k.getIme()) != null) {
				System.out.println("Isto ime kao i pre");
				KategorijaVM izmenjena =  getKategorija(k.getIme());
				izmenjena.setBrojJezgara(k.broj);
				izmenjena.setGPU(k.gpu);
				izmenjena.setRAM(k.ram);
				writeFile();
				return true;
			};
		}
		else {
			KategorijaVM zaIzmenu = getKategorija(k.staro);
			if(getKategorija(k.getIme()) != null) {
				System.out.println("Novo ime nevalidno");
				return false;
			}
			else{
				System.out.println("Novo ime validno");
				KategorijaVM izmenjena =  getKategorija(zaIzmenu.getIme());
				izmenjena.setIme(k.ime);
				izmenjena.setBrojJezgara(k.broj);
				izmenjena.setGPU(k.gpu);
				izmenjena.setRAM(k.ram);
				writeFile();
				return true;
			}
		}
		return false;
	}
}
