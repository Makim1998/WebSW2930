package rest;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.halt;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;

import json.KategorijaIzmena;
import model.KategorijaVM;
import model.Kategorije;
import model.Korisnici;
import model.Korisnik;
import model.Organizacije;
import model.Uloga;

public class Main {
	public static Korisnici korisnici;
	public static Organizacije organizacije;
	public static Kategorije kategorije;
	private static Gson g = new Gson();
	public static Korisnik ulogovan;

	public static void main(String[] args) throws IOException {
		port(8080);
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		String path = new File("./static").getCanonicalPath();
		//System.out.println(path + "\\korisnici.txt");
		
		korisnici = new Korisnici(path + "\\korisnici.txt");
		organizacije = new Organizacije(path + "\\organizacije.txt");
		kategorije = new Kategorije(path + "\\kategorije.txt");

		
		get("/test", (req, res) -> {
			return "Works";
		});
		
		post("rest/login/:username/:password",(req,res) ->{
			res.type("application/json");
			System.out.println("login");
			System.out.println(req.params(":username"));
			System.out.println(req.params(":password"));
			Korisnik k =korisnici.validiraj(req.params(":username"), req.params(":password"));
			System.out.println(k);
			ulogovan = k;
			return g.toJson(k);
		});
		
		get("rest/odjava", (req,res) ->{
			ulogovan = null;
			return("OK");
		});

		get("rest/login/getUser", (req,res) ->{
			res.type("application/json");
			return g.toJson(ulogovan);
		});
		
		get("rest/getSviKorisnici", (req,res) ->{
			res.type("application/json");
			korisnici = new Korisnici(path + "\\korisnici.txt");
			for (Korisnik k: korisnici.korisnici) {
				System.out.println(k);
			}
			return g.toJson(korisnici);
		});
		
		post("rest/addKorisnik",(req,res) ->{
			res.type("application/json");
			String payload = req.body();
			Korisnik k = g.fromJson(payload, Korisnik.class);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if(k == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			if (korisnici.getKorisnik(k.email) != null) {
				System.out.println("Email je zauzet!");
				halt(400, "Email je zauzet!");
			}
			else  {
				korisnici.dodaj(k);
			}
			return ("OK");
		});
		
		post("rest/izmeniKorisnik", (req,res) ->{
			res.type("application/json");
			String payload = req.body();
			System.out.println(payload);
			Korisnik k = g.fromJson(payload, Korisnik.class);
			System.out.println(k);
			System.out.println("za izmenu");
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if(k == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			if (korisnici.getKorisnik(k.email) == null) {
				System.out.println("Ne postoji korisnik!");
				halt(400, "Ne postoji korisnik!");
			}
			else  {
				korisnici.izmeni(k);
			}
			return ("OK");
		});
		
		get("rest/obrisiKorisnika/:email",(req,res) ->{
			res.type("application/json");
			System.out.println("brisanje korisnika");
			System.out.println(req.params(":email"));
			Korisnik k = korisnici.getKorisnik(req.params(":email"));
			System.out.println(k);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (korisnici.getKorisnik(k.email) == null) {
				System.out.println("Ne postoji korisnik!");
				halt(400, "Ne postoji korisnik!");
			}
			else  {
				korisnici.obrisi(k.email);
			}
			return ("OK");
		});
		
		get("rest/getSveKategorije", (req,res) ->{
			res.type("application/json");
			System.out.println("kategorije");
			for(KategorijaVM k : kategorije.getKategorije()) {
				System.out.println(k.getIme());
			}
			//organizacije = new Organizacije(path + "\\organizacije.txt");
			return g.toJson(kategorije);
		});
		
		post("rest/addKategorija",(req,res) ->{
			res.type("application/json");
			String payload = req.body();
			KategorijaVM k = g.fromJson(payload, KategorijaVM.class);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if(k == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			if (kategorije.getKategorija(k.getIme()) != null) {
				System.out.println("Ime je zauzeto!");
				halt(400, "Ime je zauzeto!");
			}
			else  {
				kategorije.dodaj(k);
			}
			return ("OK");
		});
		
		post("rest/izmeniKategorija", (req,res) ->{
			res.type("application/json");
			String payload = req.body();
			System.out.println(payload);
			KategorijaIzmena k = g.fromJson(payload, KategorijaIzmena.class);
			System.out.println(k);
			if (k == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			String staro = k.getStaro();
			System.out.println("za izmenu");
			System.out.println(staro);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (kategorije.getKategorija(staro) == null) {
				System.out.println("Ne postoji kategorija!");
				halt(400, "Ne postoji kategorija!");
			}
			else  {
				kategorije.izmena(k);
			}
			return ("OK");
		});
		
		get("rest/obrisiKategoriju/:ime",(req,res) ->{
			res.type("application/json");
			System.out.println("brisanje kategorije");
			System.out.println(req.params(":ime"));
			KategorijaVM k = kategorije.getKategorija(req.params(":ime"));
			System.out.println(k);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (k == null) {
				System.out.println("Ne postoji korisnik!");
				halt(400, "Ne postoji korisnik!");
			}
			else  {
				kategorije.obrisi(req.params(":ime"));
			}
			return ("OK");
		});
		get("rest/getSveOrganizacije", (req,res) ->{
			res.type("application/json");
			//organizacije = new Organizacije(path + "\\organizacije.txt");
			return g.toJson(organizacije);
		});
	}

}
