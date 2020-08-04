package json;

import model.TipDiska;

public class DiskIzmena {
	public String staro;
	public String ime;
	public TipDiska tip;
	public int kapacitet;
	public String organizacija;
	public String virtuelnaMasina;
	
	
	public DiskIzmena() {
		super();
	}


	public DiskIzmena(String staro, String ime, TipDiska tip, int kapacitet, String organizacija,
			String virtuelnaMasina) {
		super();
		this.staro = staro;
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.organizacija = organizacija;
		this.virtuelnaMasina = virtuelnaMasina;
	}


	public String getStaro() {
		return staro;
	}


	public void setStaro(String staro) {
		this.staro = staro;
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public TipDiska getTip() {
		return tip;
	}


	public void setTip(TipDiska tip) {
		this.tip = tip;
	}


	public int getKapacitet() {
		return kapacitet;
	}


	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}


	public String getOrganizacija() {
		return organizacija;
	}


	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}


	public String getVirtuelnaMasina() {
		return virtuelnaMasina;
	}


	public void setVirtuelnaMasina(String virtuelnaMasina) {
		this.virtuelnaMasina = virtuelnaMasina;
	}
	
	
	
	
}
