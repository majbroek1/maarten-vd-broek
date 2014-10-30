
public abstract class AbstractHuisdier implements Huisdier {

	private String naam;
	
	public AbstractHuisdier(String naam) {
		this.naam = naam;
	}

	public String getNaam() {
		return naam;
	}
	
	public abstract void maakGeluid();
	
}
