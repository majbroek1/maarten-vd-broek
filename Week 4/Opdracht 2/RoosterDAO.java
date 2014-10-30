/**
 * 
 */
package fysioSysteem.dataStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fysioSysteem.domain.Rooster;

/**
 * @author Bob
 *
 */
public class RoosterDAO {

	private static final String FILE_XML = "Data/roosters.xml";
	private static final String FILE_XSD = "Data/roosters.xsd";

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

	public static Rooster getRooster(int id) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(RoosterDAO.FILE_XML,
				RoosterDAO.FILE_XSD);

		Rooster rooster = null;
		if (document != null) {
			NodeList list = document.getElementsByTagName("rooster");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == id) {
						Date start = null;
						Date eind = null;
						try {
							start = FORMAT.parse(child
									.getElementsByTagName("start").item(0)
									.getTextContent());
							eind = FORMAT.parse(child
									.getElementsByTagName("eind").item(0)
									.getTextContent());
						} catch (Exception e) {
							System.out.println("Kan datum niet parsen");
							e.printStackTrace();
						}
						int fysioId = Integer.parseInt(child
								.getElementsByTagName("fysioId").item(0)
								.getTextContent());
						rooster = new Rooster(id, start, eind,
								MedewerkerDAO.getFysio(fysioId));
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (rooster == null)
			System.out.println("rooster niet gevonden");

		return rooster;
	}

	public static void setRooster(Rooster rooster) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(RoosterDAO.FILE_XML,
				RoosterDAO.FILE_XSD);

		boolean edited = false;

		if (document != null) {
			NodeList list = document.getElementsByTagName("rooster");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == rooster.getId()) {
						Node start = child.getElementsByTagName("start")
								.item(0).getFirstChild();
						Node eind = child.getElementsByTagName("eind").item(0)
								.getFirstChild();
						Node fysioId = child.getElementsByTagName("fysioId")
								.item(0).getFirstChild();

						start.setNodeValue(FORMAT.format(rooster.getStart()));
						eind.setNodeValue(FORMAT.format(rooster.getEind()));
						fysioId.setNodeValue(Integer.toString(rooster
								.getFysiotherapeut().getId()));
						edited = true;
					}
				}
			}

			domdocument.writeDocument(RoosterDAO.FILE_XML, RoosterDAO.FILE_XSD,
					document);
		} else
			System.out.println("XML document is null");

		if (!edited)
			System.out.println("rooster niet gevonden");
	}

	public static void addRooster(Rooster rooster) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(RoosterDAO.FILE_XML,
				RoosterDAO.FILE_XSD);

		if (getRooster(rooster.getId()) == null) {
			Node rootElement = document.getElementsByTagName("roosters")
					.item(0);

			Element newrooster = document.createElement("rooster");
			newrooster.setAttribute("id", Integer.toString(rooster.getId()));
			rootElement.appendChild(newrooster);

			Element start = document.createElement("start");
			start.appendChild(document.createTextNode(FORMAT.format(rooster
					.getStart())));
			newrooster.appendChild(start);

			Element eind = document.createElement("eind");
			eind.appendChild(document.createTextNode(FORMAT.format(rooster
					.getEind())));
			newrooster.appendChild(eind);

			Element fysioId = document.createElement("fysioId");
			fysioId.appendChild(document.createTextNode(Integer
					.toString(rooster.getFysiotherapeut().getId())));
			newrooster.appendChild(fysioId);

			domdocument.writeDocument(RoosterDAO.FILE_XML, RoosterDAO.FILE_XSD,
					document);
		} else {
			System.out.println("Rooster bestaat al");
		}
	}

	public static void removeRooster(Rooster rooster) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(RoosterDAO.FILE_XML,
				RoosterDAO.FILE_XSD);

		if (document != null) {
			NodeList list = document.getElementsByTagName("rooster");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == rooster.getId()) {
						child.getParentNode().removeChild(child);
						
						domdocument.writeDocument(RoosterDAO.FILE_XML,
								RoosterDAO.FILE_XSD, document);
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (rooster == null)
			System.out.println("Rooster niet gevonden");
	}

	public static ArrayList<Rooster> getRoosters() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(RoosterDAO.FILE_XML,
				RoosterDAO.FILE_XSD);

		ArrayList<Rooster> roosters = new ArrayList<Rooster>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("rooster");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					int _id = Integer.parseInt(child.getAttribute("id"));
					Date start = null;
					Date eind = null;
					try {
						start = FORMAT.parse(child
								.getElementsByTagName("start").item(0)
								.getTextContent());
						eind = FORMAT.parse(child.getElementsByTagName("eind")
								.item(0).getTextContent());
					} catch (Exception e) {
						System.out.println("Kan datum niet parsen");
						e.printStackTrace();
					}
					int fysioId = Integer.parseInt(child
							.getElementsByTagName("fysioId").item(0)
							.getTextContent());
					roosters.add(new Rooster(_id, start, eind, MedewerkerDAO
							.getFysio(fysioId)));
				}
			}
		} else
			System.out.println("XML document is null");

		if (roosters.size() < 1)
			System.out.println("Geen roosters gevonden");

		return roosters;
	}

}
