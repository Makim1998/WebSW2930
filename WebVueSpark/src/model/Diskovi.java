package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import json.DiskIzmena;
import json.OrganizacijaIzmena;
import json.VMIzmena;

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
	public Disk getDisk(String ime) {
		// TODO Auto-generated method stub
		for(Disk d : diskovi) {
			if(d.getIme().equals(ime)) {
				return d;
			}
		}
		return null;
	}
	
	public Diskovi getOrganizacijaDiskovi(String organizacija) {
		Diskovi adminovi = new Diskovi();
		adminovi.diskovi = new ArrayList<Disk>();
		System.out.println("diskovi");
		for(Disk d:this.diskovi) {
			if(d.getOrganizacija().equals(organizacija)) {
				System.out.println(d.getIme());
				adminovi.diskovi.add(d);
			}
		}
		return adminovi;
	}
	
	public void dodaj(Disk d) {
		diskovi.add(d);
		writeFile();
	}
	
	public void izmeni(DiskIzmena d) {
		// TODO Auto-generated method stub
		Disk zaIzmenu = getDisk(d.staro);
		zaIzmenu.setIme(d.ime);
		zaIzmenu.setTip(d.tip);
		zaIzmenu.setVirtuelnaMasina(d.virtuelnaMasina);
		zaIzmenu.setKapacitet(d.kapacitet);
		writeFile();

	}
	public void obrisi(String ime) {
		// TODO Auto-generated method stub
		Disk zaBrisnje = getDisk(ime);
		diskovi.remove(zaBrisnje);
		writeFile();
		
	}
	public void azurirajOrganizacije(OrganizacijaIzmena o) {
		for(Disk d : diskovi) {
			if(d.getOrganizacija().equals(o.staro)) {
				d.setOrganizacija(o.ime);
			}
		}
		writeFile();
		
	}
	public Diskovi getAdminDisk(String organizacija) {
		Diskovi zaAdmina = new Diskovi();
		zaAdmina.diskovi = new ArrayList<Disk>();
		for(Disk d: diskovi) {
			if(d.getOrganizacija().equals(organizacija)) {
				zaAdmina.diskovi.add(d);
			}
		}
		return zaAdmina;
	}
	public void azurirajVm(VMIzmena vm) {
		for(Disk d: diskovi) {
			if(vm.diskovi.contains(d.getIme())) {
				d.setVirtuelnaMasina(vm.ime);
			}
			else if(d.getVirtuelnaMasina().equals(vm.staro)) {
				d.setVirtuelnaMasina("");
			}
		}
	
	writeFile();
	}	
	
	
	
}
