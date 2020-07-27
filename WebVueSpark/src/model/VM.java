package model;

import java.util.ArrayList;

public class VM {
	private String ime;
	private String organizacija;
	private String kategorija;
	private int brojJezgara;
	private int RAM;
	private int GPU;
	private ArrayList<String> diskovi = new ArrayList<String>();
	private ArrayList<String> aktivnosti = new ArrayList<String>();	//datum kada je upaljena ugasena
	
	public VM() {
	}

	public VM(String ime, String kategorija, ArrayList<String> diskovi, ArrayList<String> aktivnosti) {
		super();
		this.ime = ime;
		this.kategorija = kategorija;
		this.diskovi = diskovi;
		this.aktivnosti = aktivnosti;
	}
	public VM(String ime, String kategorija, ArrayList<String> diskovi) {
		super();
		this.ime = ime;
		this.kategorija = kategorija;
		this.diskovi = diskovi;
		this.aktivnosti = new ArrayList<String>();
	}
	
	public void dodajDisk(String disk) {
		this.diskovi.add(disk);
	}
	
	public void promeniStanje() {
		String poslednja = this.aktivnosti.get((this.aktivnosti.size() - 1));
		String sve[] = poslednja.split("-");
		System.out.println("proslo stanje = " + sve[1]);
		if(sve[1].equals("off")) {
			this.aktivnosti.add(System.currentTimeMillis()+"-on");
		}else {
			this.aktivnosti.add(System.currentTimeMillis()+"-off");	
		}
	}


	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getOrganizacija() {
		return organizacija;
	}

	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}
	
	public String getKategorija() {
		return kategorija;
	}

	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}

	public int getBrojJezgara() {
		return brojJezgara;
	}

	public void setBrojJezgara(int brojJezgara) {
		this.brojJezgara = brojJezgara;
	}

	public int getRAM() {
		return RAM;
	}

	public void setRAM(int rAM) {
		RAM = rAM;
	}

	public int getGPU() {
		return GPU;
	}

	public void setGPU(int gPU) {
		GPU = gPU;
	}

	public ArrayList<String> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(ArrayList<String> diskovi) {
		this.diskovi = diskovi;
	}

	public ArrayList<String> getAktivnosti() {
		return aktivnosti;
	}

	public void setAktivnosti(ArrayList<String> aktivnosti) {
		this.aktivnosti = aktivnosti;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(!(arg0 instanceof VM)) {
			return false;
		}
		VM v2 = (VM) arg0;
		return this.getIme().equals(v2.getIme());
	}
	@Override
	public String toString() {
		return "VirtualMachine [ime=" + ime + ", kategorija=" + kategorija + ", diskovi=" + diskovi + ", aktivnosti="
				+ aktivnosti + "]";
	}
}
