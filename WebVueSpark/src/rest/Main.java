package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;

import model.Korisnici;
import model.Korisnik;

public class Main {
	public static Korisnici korisnici;
	private static Gson g = new Gson();

	public static void main(String[] args) throws IOException {
		port(8080);
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		String path = new File("./static").getCanonicalPath();
		//System.out.println(path + "\\korisnici.txt");
		
		korisnici = new Korisnici(path + "\\korisnici.txt");
		
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
			return g.toJson(k);
		});

	}

}
