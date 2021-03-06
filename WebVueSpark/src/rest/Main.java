package rest;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import static spark.Spark.staticFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import json.DiskIzmena;
import json.KategorijaIzmena;
import json.KorisnikIzmena;
import json.OrganizacijaIzmena;
import json.VMIzmena;
import model.Disk;
import model.Diskovi;
import model.KategorijaVM;
import model.Kategorije;
import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.Uloga;
import model.VM;
import model.VMe;
import spark.utils.IOUtils;

public class Main {
	public static Korisnici korisnici;
	public static Organizacije organizacije;
	public static Kategorije kategorije;
	public static Diskovi diskovi;
	public static VMe vme;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

	
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
		diskovi = new Diskovi(path + "\\diskovi.txt");
		vme = new VMe(path + "\\vm.txt");
		
		get("/test", (req, res) -> {
			return "Works";
		});
		
		post("rest/login/:username/:password",(req,res) ->{
			res.type("application/json");
			System.out.println("login");
			if (ulogovan != null) {
				return("redirekt");
			}
			System.out.println(req.params(":username"));
			System.out.println(req.params(":password"));
			Korisnik k =korisnici.validiraj(req.params(":username"), req.params(":password"));
			System.out.println(k);
			if(k == null) {
				halt(400, "Pogresni korisnicko ime i lozinka!");
			}
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
			//korisnici = new Korisnici(path + "\\korisnici.txt");
			Korisnici korisniciZaUlogovanog;
			if (ulogovan.uloga == Uloga.ADMIN) {
				korisniciZaUlogovanog = korisnici.getAdminKorisnici(ulogovan.organizacija);
			}
			else {
				korisniciZaUlogovanog = korisnici;
			}
			for (Korisnik k: korisnici.korisnici) {
				System.out.println(k);
			}
			
			return g.toJson(korisniciZaUlogovanog);
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
				organizacije.dodajKorisnika(k);
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
			if (k.email.equals(ulogovan.email)) {
				System.out.println("Ne mozete obrisati Vas!");
				halt(400, "Ne mozete obrisati Vas!");
			}
			else  {
				korisnici.obrisi(k.email);
				organizacije.obrisiKorisnika(k.email);
			}
			return ("OK");
		});
		
		put("rest/profil",(req,res) ->{
			res.type("application/json");
			System.out.println("izmena profila");
			String payload = req.body();
			System.out.println(payload);
			KorisnikIzmena k = g.fromJson(payload, KorisnikIzmena.class);
			if(k == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			System.out.println(k.ime);
			System.out.println("za izmenu");
			if(ulogovan == null) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			
			if (korisnici.getKorisnik(k.stara) == null) {
				System.out.println("Ne postoji korisnik!");
				halt(400, "Ne postoji korisnik!");
			}
			else  {
				korisnici.izmeniProfil(k);
				if(ulogovan.uloga !=Uloga.SUPER_ADMIN) {
					organizacije.azurirajKorisnika(k.stara, k.email,ulogovan.organizacija);
				}
			}
			return (g.toJson(k));
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
		
		get("rest/getKategorija/:ime", (req,res) ->{
			res.type("application/json");
			System.out.println("kategorije");
			System.out.println(req.params(":ime"));
			return g.toJson(kategorije.getKategorija(req.params(":ime")));
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
				boolean uspeh = kategorije.izmena(k);
				if(!uspeh) {
					halt(400, "Novo ime nije validno!");
				}
			}
			vme.azurirajKategoriju(k);
			return ("OK");
		});
		
		get("rest/obrisiKategoriju/:ime",(req,res) ->{
			res.type("application/json");
			System.out.println("brisanje kategorije");
			System.out.println(req.params(":ime"));
			KategorijaVM k = kategorije.getKategorija(req.params(":ime"));
			System.out.println(k);
			boolean imaVm = false;
			imaVm = vme.proveriKategoriju(req.params(":ime"));
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (k == null) {
				System.out.println("Ne postoji korisnik!");
				halt(400, "Ne postoji korisnik!");
			}
			if (imaVm) {
				System.out.println("Ne mozete obrisati jer ima virualnu masinu!");
				halt(400, "Ne mozete obrisati jer ima virualnu masinu!");
			}
			else  {
				kategorije.obrisi(req.params(":ime"));
			}
			return ("OK");
		});
		
		get("rest/getSveOrganizacije", (req,res) ->{
			res.type("application/json");
			//organizacije = new Organizacije(path + "\\organizacije.txt");
			Organizacije organizacijeZaUlogovanog;
			if (ulogovan.uloga == Uloga.ADMIN) {
				organizacijeZaUlogovanog = organizacije.getAdminOrganizacija(ulogovan.organizacija);
			}
			else {
				organizacijeZaUlogovanog = organizacije;
			}
			for (Organizacija k: organizacije.organizacije) {
				System.out.println(k);
			}
			return g.toJson(organizacijeZaUlogovanog);
		});
		
		post("rest/addOrganizacija",(req,res) ->{
			res.type("application/json");
			String payload = req.body();
			Organizacija o = g.fromJson(payload, Organizacija.class);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if(o == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			if (organizacije.getOrganizacija(o.getIme()) != null) {
				System.out.println("Ime je zauzeto!");
				halt(400, "Ime je zauzeto!");
			}
			else  {
				organizacije.dodaj(o);
			}
			return ("OK");
		});
		
		post("rest/izmeniOrganizacija", (req,res) ->{
			res.type("application/json");
			String payload = req.body();
			System.out.println(payload);
			OrganizacijaIzmena o = g.fromJson(payload, OrganizacijaIzmena.class);
			System.out.println(o);
			if (o == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			String staro = o.getStaro();
			System.out.println("za izmenu");
			System.out.println(staro);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (organizacije.getOrganizacija(staro) == null) {
				System.out.println("Ne postoji organizacija!");
				halt(400, "Ne postoji organizacija!");
			}
			if ((!staro.equals(o.ime)) && organizacije.getOrganizacija(o.getIme()) != null) {
				System.out.println("Ime vec postoji!");
				halt(400, "Ime vec postoji!");
			}
			else  {
				organizacije.izmeni(o);
				korisnici.azurirajOrganizacije(o);
				diskovi.azurirajOrganizacije(o);
				vme.azurirajOrganizacije(o);
			}
			return ("OK");
		});
		
		get("rest/getSviDiskovi", (req,res) ->{
			res.type("application/json");
			System.out.println("diskovi");
			Diskovi diskoviZaUlogovanog;
			if (ulogovan.uloga == Uloga.SUPER_ADMIN) {
				diskoviZaUlogovanog = diskovi;
			}
			else {
				diskoviZaUlogovanog = diskovi.getAdminDisk(ulogovan.organizacija);

			}
			for (Disk d: diskoviZaUlogovanog.diskovi) {
				System.out.println(d);
			}
			//organizacije = new Organizacije(path + "\\organizacije.txt");
			return g.toJson(diskoviZaUlogovanog);
		});
		
		get("rest/getOrganizacijaDiskovi", (req,res) ->{
			res.type("application/json");
			System.out.println("diskovi");
			Diskovi zaOrg = diskovi.getOrganizacijaDiskovi(ulogovan.organizacija);
			//organizacije = new Organizacije(path + "\\organizacije.txt");
			return g.toJson(zaOrg);
		});
		
		post("rest/addDisk",(req,res) ->{
			res.type("application/json");
			String payload = req.body();
			Disk d = g.fromJson(payload, Disk.class);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if(d == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			if (diskovi.getDisk(d.getIme()) != null) {
				System.out.println("Ime je zauzeto!");
				halt(400, "Ime je zauzeto!");
			}
			else  {
				diskovi.dodaj(d);
			}
			return ("OK");
		});
		
		post("rest/izmeniDisk", (req,res) ->{
			res.type("application/json");
			String payload = req.body();
			System.out.println(payload);
			DiskIzmena d = g.fromJson(payload, DiskIzmena.class);
			System.out.println(d);
			if (d == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			String staro = d.getStaro();
			System.out.println("za izmenu");
			System.out.println(staro);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (diskovi.getDisk(staro) == null) {
				System.out.println("Ne postoji disk!");
				halt(400, "Ne postoji disk!");
			}
			if ((!d.getIme().equals(d.getStaro())) && diskovi.getDisk(d.getIme()) != null) {
				System.out.println("Ime vec postoji!");
				halt(400, "Ime vec postoji!");
			}
			else  {
				diskovi.izmeni(d);
				vme.azurirajDisk(d);
		
			}
			return ("OK");
		});
		
		get("rest/obrisiDisk/:ime",(req,res) ->{
			res.type("application/json");
			System.out.println("brisanje diska");
			System.out.println(req.params(":ime"));
			Disk d = diskovi.getDisk(req.params(":ime"));
			System.out.println(d);
			if(ulogovan == null || ulogovan.uloga != Uloga.SUPER_ADMIN) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (d == null) {
				System.out.println("Ne postoji disk!");
				halt(400, "Ne postoji disk!");
			}
			else  {
				diskovi.obrisi(req.params(":ime"));
				vme.obrisiDisk(req.params(":ime"));
			}
			return ("OK");
		});
		
		post("/api/upload", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(path + "\\slike\\"));
            Part filePart = req.raw().getPart("myfile");
            System.out.println("upload");
            try (InputStream inputStream = filePart.getInputStream()) {
                OutputStream outputStream = new FileOutputStream(path + "\\slike\\" + filePart.getSubmittedFileName());
                IOUtils.copy(inputStream, outputStream);
                outputStream.close();
            }

            return "File uploaded and saved.";
        });
		
		post("/api/uploadIzmena", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(path + "\\slike\\"));
            Part filePart = req.raw().getPart("myfileIzmena");
            System.out.println("upload");
            try (InputStream inputStream = filePart.getInputStream()) {
                OutputStream outputStream = new FileOutputStream(path + "\\slike\\" + filePart.getSubmittedFileName());
                IOUtils.copy(inputStream, outputStream);
                outputStream.close();
            }

            return "File uploaded and saved.";
        });
		
		get("rest/getSveVM", (req,res) ->{
			res.type("application/json");
			System.out.println("vm");
			VMe vmzaUlogovanog;
			//organizacije = new Organizacije(path + "\\organizacije.txt");
			if(ulogovan.uloga == Uloga.ADMIN || ulogovan.uloga == Uloga.KORISNIK  ) {
				vmzaUlogovanog = vme.getOrganizationVM(ulogovan.organizacija);
			}
			else {
				vmzaUlogovanog = vme;
			}
			
			return g.toJson(vmzaUlogovanog);
		});
		
		post("rest/addVM",(req,res) ->{
			res.type("application/json");
			String payload = req.body();
			VM vm = g.fromJson(payload, VM.class);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if(vm == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			if (vme.getVM(vm.getIme()) != null) {
				System.out.println("Ime je zauzeto!");
				halt(400, "Ime je zauzeto!");
			}
			else  {
				vme.dodaj(vm);
			}
			return ("OK");
		});
		
		post("rest/izmeniVM", (req,res) ->{
			res.type("application/json");
			String payload = req.body();
			System.out.println(payload);
			VMIzmena vm = g.fromJson(payload, VMIzmena.class);
			System.out.println(vm);
			if (vm == null) {
				System.out.println("Nije validan zahtev!");
				halt(400, "Nije validan zahtev!");
			}
			String staro = vm.staro;
			System.out.println("za izmenu");
			System.out.println(staro);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (vme.getVM(staro) == null) {
				System.out.println("Ne postoji vm!");
				halt(400, "Ne postoji virtuelna masina!");
			}
			if ((!vm.staro.equals(vm.ime)) && vme.getVM(vm.ime) != null) {
				System.out.println("Ime vec postoji!");
				halt(400, "Ime vec postoji!");
			}
			else  {
				for (String s: vm.aktivnosti) {
					System.out.println(s);
				}
				vme.izmeni(vm);
				organizacije.azurirajVm(vm);
				diskovi.azurirajVm(vm);
			}
			return ("OK");
		});
		get("rest/obrisiVM/:ime",(req,res) ->{
			res.type("application/json");
			System.out.println("brisanje kategorije");
			System.out.println(req.params(":ime"));
			VM v = vme.getVM(req.params(":ime"));
			System.out.println(v);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (v == null) {
				System.out.println("Ne postoji virtuelna masina!");
				halt(400, "Ne postoji virtuelna masina!");
			}
			else  {
				vme.obrisi(req.params(":ime"));
				organizacije.obrisiVM(req.params(":ime"));
			}
			return ("OK");
		});
		
		get("rest/upaliVM/:ime",(req,res) ->{
			res.type("application/json");
			System.out.println("upali vm");
			System.out.println(req.params(":ime"));
			VM v = vme.getVM(req.params(":ime"));
			System.out.println(v);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (v == null) {
				System.out.println("Ne postoji virtuelna masina!");
				halt(400, "Ne postoji virtuelna masina!");
			}
			else  {
				vme.upali(req.params(":ime"));
			}
			return (g.toJson(v));
		});
		
		get("rest/ugasiVM/:ime",(req,res) ->{
			res.type("application/json");
			System.out.println("ugasi vm");
			System.out.println(req.params(":ime"));
			VM v = vme.getVM(req.params(":ime"));
			System.out.println(v);
			if(ulogovan == null || ulogovan.uloga == Uloga.KORISNIK) {
				System.out.println("Forbiden!");
				halt(403, "Nemate pravo pristupa!");
			}
			if (v == null) {
				System.out.println("Ne postoji virtuelna masina!");
				halt(400, "Ne postoji virtuelna masina!");
			}
			else  {
				vme.ugasi(req.params(":ime"));
			}
			return (g.toJson(v));
		});
	}

}
