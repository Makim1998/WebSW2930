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
import json.KorisnikLog;
import json.OrganizacijaIzmena;

public class Korisnici {
	public ArrayList<Korisnik> korisnici;
	public String path;
	private static Gson g = new Gson();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
	public Korisnici() {
		
	}
	public Korisnici(String path) {
		this.path = path;
		korisnici = new ArrayList<Korisnik>();
		readFile();
	}
	
	private void readFile() {
		try {
			JsonReader reader = new JsonReader(new FileReader(this.path));
			this.setKorisnici(g.fromJson(reader, new TypeToken<ArrayList<Korisnik>>(){}.getType()));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeFile()  {
		FileWriter fw;
		try {
			fw = new FileWriter(this.path);
			String data = g.toJson(korisnici);
			System.out.println(data);
			//System.out.println(data);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Korisnik getKorisnik(String username) {
		for(Korisnik k : korisnici) {
			if(k.email.equals(username)) {
				return k;
			}
		}
		return null;
	}
	
	public Korisnici getAdminKorisnici(String organizacija) {
		Korisnici adminovi = new Korisnici();
		adminovi.korisnici = new ArrayList<Korisnik>();
		for(Korisnik k:this.korisnici) {
			if(k.organizacija.equals(organizacija)) {
				adminovi.korisnici.add(k);
			}
		}
		return adminovi;
	}
	
	public Korisnik validiraj(String user, String password) {
		for(Korisnik k: korisnici) {
			if(k.getEmail().equals(user) && k.getLozinka().equals(password)) {
				return k;
			}
		}
		return null;
	}
	
	public void dodaj(Korisnik k) {
		this.korisnici.add(k);
		writeFile();
	}
	
	public void obrisi(String  email) {
		Korisnik zaBrisanje = getKorisnik(email);
		this.korisnici.remove(zaBrisanje);
		writeFile();
	}
	
	public void izmeni(Korisnik k) {
		Korisnik izmenjen =  getKorisnik(k.email);
		izmenjen.lozinka = k.lozinka;
		izmenjen.uloga = k.uloga;
		izmenjen.prezime = k.prezime;
		izmenjen.ime = k.ime;
		writeFile();
	}
	
	public void azurirajOrganizacije(OrganizacijaIzmena o) {
		for(Korisnik k : korisnici) {
			if(k.organizacija.equals(o.staro)) {
				k.setOrganizacija(o.ime);
			}
		}
		writeFile();
	}

	public void izmeniProfil(KorisnikIzmena k) {
		Korisnik izmenjen =  getKorisnik(k.stara);
		izmenjen.lozinka = k.lozinka;
		izmenjen.prezime = k.prezime;
		izmenjen.ime = k.ime;
		izmenjen.email = k.email;
		writeFile();
		
	}
}
