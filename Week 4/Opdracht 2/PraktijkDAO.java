package fysioSysteem.dataStorage;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fysioSysteem.domain.Praktijk;

/**
 * @author Bob
 *
 */
public class PraktijkDAO {

	private static final String FILE_XML = "Data/praktijken.xml";
	private static final String FILE_XSD = "Data/praktijken.xsd";

	public static Praktijk getPraktijk(int id) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(PraktijkDAO.FILE_XML,
				PraktijkDAO.FILE_XSD);

		Praktijk praktijk = null;
		if (document != null) {
			NodeList list = document.getElementsByTagName("praktijk");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == id) {
						String naam = child.getElementsByTagName("naam")
								.item(0).getTextContent();
						String adres = child.getElementsByTagName("adres")
								.item(0).getTextContent();
						String postcode = child
								.getElementsByTagName("postcode").item(0)
								.getTextContent();
						String plaats = child.getElementsByTagName("plaats")
								.item(0).getTextContent();
						String iban = child.getElementsByTagName("iban")
								.item(0).getTextContent();
						String telNr = child.getElementsByTagName("telnr")
								.item(0).getTextContent();
						String email = child.getElementsByTagName("email")
								.item(0).getTextContent();
						String website = child.getElementsByTagName("website")
								.item(0).getTextContent();

						praktijk = new Praktijk(id, naam, adres, postcode,
								plaats, iban, telNr, email, website);
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (praktijk == null)
			System.out.println("Praktijk niet gevonden");

		return praktijk;
	}

	public static void setPraktijk(Praktijk praktijk) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(PraktijkDAO.FILE_XML,
				PraktijkDAO.FILE_XSD);

		boolean edited = false;

		if (document != null) {
			NodeList list = document.getElementsByTagName("praktijk");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == praktijk.getId()) {
						Node naam = child.getElementsByTagName("naam").item(0)
								.getFirstChild();
						Node adres = child.getElementsByTagName("adres")
								.item(0).getFirstChild();
						Node postcode = child.getElementsByTagName("postcode")
								.item(0).getFirstChild();
						Node plaats = child.getElementsByTagName("plaats")
								.item(0).getFirstChild();
						Node iban = child.getElementsByTagName("iban").item(0)
								.getFirstChild();
						Node telNr = child.getElementsByTagName("telnr")
								.item(0).getFirstChild();
						Node email = child.getElementsByTagName("email")
								.item(0).getFirstChild();
						Node website = child.getElementsByTagName("website")
								.item(0).getFirstChild();

						naam.setNodeValue(praktijk.getNaam());
						adres.setNodeValue(praktijk.getAdres());
						postcode.setNodeValue(praktijk.getPostcode());
						plaats.setNodeValue(praktijk.getPlaats());
						iban.setNodeValue(praktijk.getIban());
						telNr.setNodeValue(praktijk.getTelNr());
						email.setNodeValue(praktijk.getEmail());
						website.setNodeValue(praktijk.getWebsite());

						edited = true;
					}
				}
			}

			domdocument.writeDocument(PraktijkDAO.FILE_XML,
					PraktijkDAO.FILE_XSD, document);
		} else
			System.out.println("XML document is null");

		if (!edited)
			System.out.println("Praktijk niet gevonden");
	}

	public static ArrayList<Praktijk> getPraktijken() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(PraktijkDAO.FILE_XML,
				PraktijkDAO.FILE_XSD);

		ArrayList<Praktijk> praktijken = new ArrayList<Praktijk>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("praktijk");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					int id = Integer.parseInt(child.getAttribute("id"));
					String naam = child.getElementsByTagName("naam").item(0)
							.getTextContent();
					String adres = child.getElementsByTagName("adres").item(0)
							.getTextContent();
					String postcode = child.getElementsByTagName("postcode")
							.item(0).getTextContent();
					String plaats = child.getElementsByTagName("plaats")
							.item(0).getTextContent();
					String iban = child.getElementsByTagName("iban").item(0)
							.getTextContent();
					String telNr = child.getElementsByTagName("telnr").item(0)
							.getTextContent();
					String email = child.getElementsByTagName("email").item(0)
							.getTextContent();
					String website = child.getElementsByTagName("website")
							.item(0).getTextContent();

					praktijken.add(new Praktijk(id, naam, adres, postcode,
							plaats, iban, telNr, email, website));
				}
			}
		} else
			System.out.println("XML document is null");

		if (praktijken.size() < 1)
			System.out.println("Geen praktijken gevonden");

		return praktijken;
	}

}
