package fysioSysteem.dataStorage;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fysioSysteem.domain.Fysiotherapeut;
import fysioSysteem.domain.Medewerker;
import fysioSysteem.domain.Secretaresse;
import fysioSysteem.domain.Status;

/**
 * @author Bob
 *
 */
public class MedewerkerDAO {

	private static final String FILE_XML = "Data/medewerkers.xml";
	private static final String FILE_XSD = "Data/medewerkers.xsd";

	public static Medewerker getMedewerker(int id) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(MedewerkerDAO.FILE_XML,
				MedewerkerDAO.FILE_XSD);

		Medewerker medewerker = null;
		if (document != null) {
			NodeList list = document.getElementsByTagName("medewerker");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == id) {
						String naam = child.getElementsByTagName("naam")
								.item(0).getTextContent();
						String wachtwoord = child
								.getElementsByTagName("wachtwoord").item(0)
								.getTextContent();
						Status status = Status.valueOf(child
								.getElementsByTagName("status").item(0)
								.getTextContent());
						medewerker = new Medewerker(id, naam, wachtwoord,
								status);
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (medewerker == null)
			System.out.println("Medewerker niet gevonden");

		return medewerker;
	}

	public static Fysiotherapeut getFysio(int id) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(MedewerkerDAO.FILE_XML,
				MedewerkerDAO.FILE_XSD);

		Fysiotherapeut fysio = null;
		if (document != null) {
			NodeList list = document.getElementsByTagName("medewerker");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == id) {
						String naam = child.getElementsByTagName("naam")
								.item(0).getTextContent();
						String wachtwoord = child
								.getElementsByTagName("wachtwoord").item(0)
								.getTextContent();
						Status status = Status.valueOf(child
								.getElementsByTagName("status").item(0)
								.getTextContent());
						fysio = new Fysiotherapeut(id, naam, wachtwoord, status);
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (fysio == null)
			System.out.println("Medewerker niet gevonden");

		return fysio;
	}

	public static void addMedewerker(Medewerker medewerker) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(MedewerkerDAO.FILE_XML,
				MedewerkerDAO.FILE_XSD);

		if (getMedewerker(medewerker.getId()) == null) {
			Node rootElement = document.getElementsByTagName("medewerkers")
					.item(0);

			Element newMedewerker = document.createElement("medewerker");
			newMedewerker.setAttribute("id",
					Integer.toString(medewerker.getId()));
			rootElement.appendChild(newMedewerker);

			Element naam = document.createElement("naam");
			naam.appendChild(document.createTextNode(medewerker.getNaam()));
			newMedewerker.appendChild(naam);

			Element wachtwoord = document.createElement("wachtwoord");
			wachtwoord.appendChild(document.createTextNode(medewerker
					.getWachtwoord()));
			newMedewerker.appendChild(wachtwoord);

			Element status = document.createElement("status");
			status.appendChild(document.createTextNode(medewerker.getStatus()
					.toString()));
			newMedewerker.appendChild(status);

			Element functie = document.createElement("functie");
			if (medewerker instanceof Fysiotherapeut)
				functie.appendChild(document.createTextNode("fysiotherapeut"));
			else
				functie.appendChild(document.createTextNode("secretaresse"));

			newMedewerker.appendChild(functie);

			domdocument.writeDocument(MedewerkerDAO.FILE_XML,
					MedewerkerDAO.FILE_XSD, document);
		} else {
			System.out.println("Medewerker bestaat al");
		}
	}

	public static void setMedewerker(Medewerker medewerker) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(MedewerkerDAO.FILE_XML,
				MedewerkerDAO.FILE_XSD);

		boolean edited = false;

		if (document != null) {
			NodeList list = document.getElementsByTagName("medewerker");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == medewerker.getId()) {
						Node naam = child.getElementsByTagName("naam").item(0)
								.getFirstChild();
						Node wachtwoord = child
								.getElementsByTagName("wachtwoord").item(0)
								.getFirstChild();
						Node status = child.getElementsByTagName("status")
								.item(0).getFirstChild();

						naam.setNodeValue(medewerker.getNaam());
						wachtwoord.setNodeValue(medewerker.getWachtwoord());
						status.setNodeValue(medewerker.getStatus().toString());
						edited = true;
					}
				}
			}

			domdocument.writeDocument(MedewerkerDAO.FILE_XML,
					MedewerkerDAO.FILE_XSD, document);
		} else
			System.out.println("XML document is null");

		if (!edited)
			System.out.println("Medewerker niet gevonden");
	}

	public static ArrayList<Medewerker> getMedewerkers() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(MedewerkerDAO.FILE_XML,
				MedewerkerDAO.FILE_XSD);

		ArrayList<Medewerker> medewerkers = new ArrayList<Medewerker>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("medewerker");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;

					int id = Integer.parseInt(child.getAttribute("id"));
					String naam = child.getElementsByTagName("naam").item(0)
							.getTextContent();
					String wachtwoord = child
							.getElementsByTagName("wachtwoord").item(0)
							.getTextContent();
					Status status = Status.valueOf(child
							.getElementsByTagName("status").item(0)
							.getTextContent());
					medewerkers
							.add(new Medewerker(id, naam, wachtwoord, status));
				}
			}
		} else
			System.out.println("XML document is null");

		if (medewerkers.size() < 1)
			System.out.println("Medewerker niet gevonden");

		return medewerkers;
	}

	public static ArrayList<Fysiotherapeut> getFysiotherapeuten() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(MedewerkerDAO.FILE_XML,
				MedewerkerDAO.FILE_XSD);

		ArrayList<Fysiotherapeut> therapeuten = new ArrayList<Fysiotherapeut>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("medewerker");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String functie = child.getElementsByTagName("functie")
							.item(0).getTextContent();
					if (functie.equals("fysiotherapeut")) {
						int id = Integer.parseInt(child.getAttribute("id"));
						String naam = child.getElementsByTagName("naam")
								.item(0).getTextContent();
						String wachtwoord = child
								.getElementsByTagName("wachtwoord").item(0)
								.getTextContent();
						Status status = Status.valueOf(child
								.getElementsByTagName("status").item(0)
								.getTextContent());
						therapeuten.add(new Fysiotherapeut(id, naam,
								wachtwoord, status));
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (therapeuten.size() < 1)
			System.out.println("Medewerker niet gevonden");

		return therapeuten;
	}

	public static ArrayList<Secretaresse> getSecretaressen() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(MedewerkerDAO.FILE_XML,
				MedewerkerDAO.FILE_XSD);

		ArrayList<Secretaresse> secretaressen = new ArrayList<Secretaresse>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("medewerker");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String functie = child.getElementsByTagName("secretaresse")
							.item(0).getTextContent();
					if (functie.equals("fysiotherapeut")) {
						int id = Integer.parseInt(child
								.getElementsByTagName("id").item(0)
								.getTextContent());
						String naam = child.getElementsByTagName("naam")
								.item(0).getTextContent();
						String wachtwoord = child
								.getElementsByTagName("wachtwoord").item(0)
								.getTextContent();
						Status status = Status.valueOf(child
								.getElementsByTagName("status").item(0)
								.getTextContent());
						secretaressen.add(new Secretaresse(id, naam,
								wachtwoord, status));
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (secretaressen.size() < 1)
			System.out.println("Medewerker niet gevonden");

		return secretaressen;
	}

}
