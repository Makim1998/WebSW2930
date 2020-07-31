package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import json.OrganizacijaIzmena;
import json.VMIzmena;

public class Organizacije {
	public ArrayList<Organizacija> organizacije;
	public String path;
	private static Gson g = new Gson();


	public ArrayList<Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(ArrayList<Organizacija> organizacije) {
		this.organizacije = organizacije;
	}
	public Organizacije() {
		organizacije = new ArrayList<Organizacija>();
	}
	
	public Organizacije(String path) {
		this.path = path;
		readFile();
	}
	
	public Organizacija getOrganizacija(String ime) {
		for(Organizacija o : organizacije) {
			if(o.getIme().equals(ime)) {
				return o;
			}
		}
		return null;
	}
	
	public void obrisiKorisnika(String email) {
		for(Organizacija o : organizacije) {
			o.getListaKorisnika().remove(email);	
		}
	}
	
	public void obrisiVM(String vm) {
		for(Organizacija o : organizacije) {
			o.getListaVM().remove(vm);	
		}
	}
	
	public void azurirajVM(VMIzmena vm) {
		for(Organizacija o : organizacije) {
			int i = 0;
			for(String s: o.getListaVM()) {
				if(s.equals(vm.staro)) {
					break;
				}
				i++;
			}
			o.getListaVM().set(i, vm.ime);
		}
		writeFile();
	}
	
	public void azurirajKorisnika(String stari, String novi) {
		for(Organizacija o : organizacije) {
			int i = 0;
			for(String s: o.getListaKorisnika()) {
				if(s.equals(stari)) {
					break;
				}
				i++;
			}
			o.getListaVM().set(i, novi);
		}
		writeFile();
	}

	public void readFile() {
		try {
			JsonReader reader = new JsonReader(new FileReader(this.path));
			this.setOrganizacije(g.fromJson(reader, new TypeToken<ArrayList<Organizacija>>(){}.getType()));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeFile()  {
		FileWriter fw;
		try {
			fw = new FileWriter(this.path);
			String data = g.toJson(organizacije);
			System.out.println(data);
			//System.out.println(data);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dodaj(Organizacija o) {
		this.organizacije.add(o);
		writeFile();
	}
	
	
	public void izmeni(OrganizacijaIzmena o) {
		Organizacija izmenjen =  getOrganizacija(o.getStaro());
		izmenjen.setIme(o.getIme());
		izmenjen.setLogo(o.getLogo());
		izmenjen.setOpis(o.getOpis());
		writeFile();
	}
	
}
