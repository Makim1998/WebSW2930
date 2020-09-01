package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import json.KorisnikIzmena;
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
		writeFile();
	}
	
	public void obrisiVM(String vm) {
		for(Organizacija o : organizacije) {
			o.getListaVM().remove(vm);	
		}
		writeFile();
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
	
	public void azurirajKorisnika(String stari, String novi, String org) {
		for(Organizacija o : organizacije) {
			int i = 0;
			if(org.equals(o.getIme())) {
				for(String s: o.getListaKorisnika()) {
					if(s.equals(stari)) {
						break;
					}
					i++;
				}
				if (i < o.getListaKorisnika().size()) {
					o.getListaKorisnika().set(i, novi);
				}
				else {
					o.getListaKorisnika().add(novi);

				}
			}
			
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
		if(!o.getLogo().equals("")) {
			izmenjen.setLogo(o.getLogo());
		}
		izmenjen.setOpis(o.getOpis());
		writeFile();
	}

	public Organizacije getAdminOrganizacija(String organizacija) {
		Organizacije org = new Organizacije();
		for(Organizacija o: organizacije) {
			if(o.getIme().equals(organizacija)) {
				org.organizacije.add(o);
			}
		}
		return org;
	}

	public void dodajKorisnika(Korisnik k) {
		for(Organizacija o : organizacije) {
			if(o.getIme().equals(k.getOrganizacija())){
				o.getListaKorisnika().add(k.getEmail());
			}
		}
		writeFile();
	}

	public void azurirajVm(VMIzmena vm) {
		for(Organizacija o : organizacije) {
			int i = 0;
			for(String v : o.getListaVM()) {
				if(v.equals(vm.staro)) {
					break;
				}
				i++;
			}
			if(i < o.getListaVM().size()) {
				o.getListaVM().set(i, vm.ime);
			}
			
		}
		writeFile();
		
	}

	
	
}
