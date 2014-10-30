import java.util.ArrayList;


public class Dierenwinkel {

	private ArrayList<AbstractHuisdier> lijst;
	
	public Dierenwinkel() {
		lijst = new ArrayList<AbstractHuisdier>();
	}

	public void voegDierToe(AbstractHuisdier dier) {
		lijst.add(dier);
	}
	
	public void printOverzicht() {
		for (Huisdier huisdier : lijst)
			System.out.println("> " + huisdier.getNaam() + "\n");
	}
	
}
