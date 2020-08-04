package model;

public class Disk {
	private String ime;
	private TipDiska tip;
	private int kapacitet;
	private String organizacija;
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
	
	public Disk(String ime, TipDiska tip, int kapacitet, String virtuelnaMasina, String organizacija) {
		super();
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.virtuelnaMasina = virtuelnaMasina;
		this.organizacija = organizacija;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.getIme().equals(((Disk)obj).getIme());
	}
	
}
