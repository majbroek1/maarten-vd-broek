
public abstract class Huisdier {

	private String naam;
	
	public Huisdier(String naam) {
		this.naam = naam;
	}

	public String getNaam() {
		return naam;
	}
	
	public abstract void maakGeluid();
	
}
