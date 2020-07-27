package model;

import java.util.ArrayList;

public class Organizacija {
	
	private String ime;
	private String opis;
	private String logo;
	private ArrayList<String> listaKorisnika;
	private ArrayList<String> listaVM;
	
	public Organizacija() {
		
	}
	public Organizacija(String ime, String opis, String logo) {
		super();
		this.ime = ime;
		this.opis = opis;
		this.logo = logo;
	}
	public ArrayList<String> getListaVM() {
		return listaVM;
	}
	public void setListaVM(ArrayList<String> listaVM) {
		this.listaVM = listaVM;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public ArrayList<String> getListaKorisnika() {
		return listaKorisnika;
	}
	public void setListaKorisnika(ArrayList<String> listaKorisnika) {
		this.listaKorisnika = listaKorisnika;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Organizacija))
			return false;
		Organizacija org = (Organizacija)arg0;
		return this.ime.equals(org.ime);
	}
	
	@Override
	public String toString() {
		return "Organizacija: [ime=" + this.ime + ", opis=" + this.opis
				+ ", logo" + this.logo + "]";
	}


}
