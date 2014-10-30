import java.util.ArrayList;


public class Dierenwinkel {

	private ArrayList<Huisdier> lijst;
	
	public Dierenwinkel() {
		lijst = new ArrayList<Huisdier>();
	}

	public void voegDierToe(Huisdier dier) {
		lijst.add(dier);
	}
	
	public void printOverzicht() {
		for (Huisdier huisdier : lijst)
			System.out.println("> " + huisdier.getNaam() + "\n");
	}
	
}
