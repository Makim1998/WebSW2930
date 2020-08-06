package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import json.VMIzmena;

public class VMe {
	public ArrayList<VM> virtualneMasine;
	public String path;
	private static Gson g = new Gson();

	
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
		writeFile();
		
	}

	public void izmeni(VMIzmena v) {
		
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
}
