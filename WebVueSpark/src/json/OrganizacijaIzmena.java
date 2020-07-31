package json;

public class OrganizacijaIzmena {
	public String staro;
	public String ime;
	public String opis;
	public String logo;
	
	
	public OrganizacijaIzmena(String staro, String ime, String opis, String logo) {
		super();
		this.staro = staro;
		this.ime = ime;
		this.opis = opis;
		this.logo = logo;
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
	public OrganizacijaIzmena() {
	}
}
