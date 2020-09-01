package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import json.DiskIzmena;
import json.KategorijaIzmena;
import json.OrganizacijaIzmena;
import json.VMIzmena;

public class VMe {
	public ArrayList<VM> virtualneMasine;
	public String path;
	private static Gson g = new Gson();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

	
	public ArrayList<VM> getVirtualneMasine() {
		return virtualneMasine;
	}

	public void setVirtualneMasine(ArrayList<VM> virtualneMasine) {
		this.virtualneMasine = virtualneMasine;
	}

	private void readFile() {
		try {
			JsonReader reader = new JsonReader(new FileReader(this.path));
			this.setVirtualneMasine(g.fromJson(reader, new TypeToken<ArrayList<VM>>(){}.getType()));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeFile(){
		FileWriter fw;
		try {
			fw = new FileWriter(this.path);
			String data = g.toJson(virtualneMasine);
			System.out.println(data);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public VMe() {
		
	}
	public VMe(String path) {
		this.virtualneMasine = new ArrayList<VM>();
		this.path = path;
		readFile();
	}
	
	

	public VM getVM(String id) {
		VM vmret = null;
		for(VM vm : virtualneMasine) {
			if(vm.getIme().equals(id)) {
				vmret = vm;
			}
		}
		
		return vmret;
	}

	public VMe getOrganizationVM(String organizacija) {
		VMe organizacijskevm = new VMe();
		organizacijskevm.virtualneMasine = new ArrayList<VM>();
		for(VM v : virtualneMasine) {
			if(v.getOrganizacija().equals(organizacija)) {
				organizacijskevm.virtualneMasine.add(v);
			}
		}
		return organizacijskevm;
	}

	public void dodaj(VM vm) {
		this.virtualneMasine.add(vm);
		String aktivnost = sdf.format(System.currentTimeMillis()) + "-iskljucena";
		vm.getAktivnosti().add(aktivnost);
		writeFile();
		
	}

	public void izmeni(VMIzmena v) {
		VM zaMenjanje = getVM(v.staro);
		zaMenjanje.setIme(v.ime);
		zaMenjanje.setKategorija(v.kategorija);
		zaMenjanje.setDiskovi(v.diskovi);
		zaMenjanje.setAktivnosti(v.aktivnosti);
		zaMenjanje.setBrojJezgara(v.brojJezgara);
		zaMenjanje.setRAM(v.RAM);
		zaMenjanje.setGPU(v.GPU);
		zaMenjanje.setOrganizacija(v.organizacija);
		writeFile();
	}
	public void obrisi(String ime) {
		VM zaBrisanje = getVM(ime);
		this.virtualneMasine.remove(zaBrisanje);
		writeFile();
	}

	public boolean proveriKategoriju(String ime) {
		for(VM v: virtualneMasine) {
			if(v.getKategorija().equals(ime)) {
				return true;
			}
		}
		return false;
	}

	public void azurirajKategoriju(KategorijaIzmena k) {
		for(VM v: virtualneMasine) {
			if(v.getKategorija().equals(k.staro)) {
				v.setKategorija(k.ime);
				v.setBrojJezgara(k.broj);
				v.setGPU(k.gpu);
				v.setRAM(k.ram);
			}
		}
		writeFile();
	}

	public void azurirajOrganizacije(OrganizacijaIzmena o) {
		for(VM v : virtualneMasine) {
			if(v.getOrganizacija().equals(o.staro)) {
				v.setOrganizacija(o.ime);
			}
		}
		writeFile();
		
	}

	public void azurirajDisk(DiskIzmena d) {
		for(VM v : virtualneMasine) {
			int i = 0;
			for(String disk : v.getDiskovi()) {
				if(disk.equals(d.staro)) {
					break;
				}
				i++;
			}
			if(!v.getIme().equals(d.virtuelnaMasina)) {
				v.getDiskovi().remove(d.staro);
				System.out.println("remove disk");
				continue;
			}
			if(i < v.getDiskovi().size()) {
				v.getDiskovi().set(i, d.ime);
			}
			else {
				v.getDiskovi().add(d.ime);
				System.out.println("add disk");

			}
			
		}
		writeFile();
		
	}

	public void obrisiDisk(String d) {
		for(VM v : virtualneMasine) {
			boolean postoji = false;
			for(String disk : v.getDiskovi()) {
				if(disk.equals(d)) {
					postoji = true;
					break;
				}
			}
			if(postoji) {
				v.getDiskovi().remove(d);
			}
			
		}
		writeFile();
		
	}
	
	public void upali(String ime) {
		for (VM v: virtualneMasine) {
			if(v.getIme().equals(ime)) {
				Date now = new Date(System.currentTimeMillis());
				String aktivnost = sdf.format(now) + "-ukljucena";
				v.getAktivnosti().add(aktivnost);
			}
		}
		writeFile();
	}
	public void ugasi(String ime) {
		for (VM v: virtualneMasine) {
			if(v.getIme().equals(ime)) {
				Date now = new Date(System.currentTimeMillis());
				String aktivnost = sdf.format(now) + "-iskljucena";
				v.getAktivnosti().add(aktivnost);
			}
		}
		writeFile();
	}
}
