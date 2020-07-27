package model;

public class Disk {
	
	private String ime;
	private TipDiska tip;
	private int kapacitet;
	private String virtuelnaMasina;
	
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
	public String getVirtuelnaMasina() {
		return virtuelnaMasina;
	}
	public void setVirtuelnaMasina(String virtuelnaMasina) {
		this.virtuelnaMasina = virtuelnaMasina;
	}
	
	public Disk(String ime, TipDiska tip, int kapacitet, String virtuelnaMasina) {
		super();
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.virtuelnaMasina = virtuelnaMasina;
	}
	
}
