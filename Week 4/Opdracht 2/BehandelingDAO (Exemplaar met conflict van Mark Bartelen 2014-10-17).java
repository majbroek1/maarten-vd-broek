/**
 * 
 */
package fysioSysteem.dataStorage;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fysioSysteem.domain.Behandeling;

/**
 * @author Bob
 *
 */
public class BehandelingDAO {

	public static Behandeling getBehandeling(int id) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument("behandelingen.xml",
				"behandelingen.xsd");

		Behandeling behandeling = null;
		if (document != null) {
			NodeList list = document.getElementsByTagName("behandeling");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == id) {
						String status = child.getElementsByTagName("status")
								.item(0).getTextContent();
						int behandelCode = Integer.parseInt(child
								.getElementsByTagName("behandelCode").item(0)
								.getTextContent());
						String klantBsn = child
								.getElementsByTagName("klantBsn").item(0)
								.getTextContent();
						behandeling = new Behandeling(id, status,
								KlantDAO.getKlant(klantBsn),
								BehandelCodeDAO.getBehandelCode(behandelCode),
								AfspraakDAO.getAfspraken(id));
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (behandeling == null)
			System.out.println("Behandeling niet gevonden");

		return behandeling;
	}

	public static void setBehandeling(Behandeling behandeling) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument("behandelingen.xml",
				"behandelingen.xsd");

		boolean edited = false;

		if (document != null) {
			NodeList list = document.getElementsByTagName("behandeling");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _id = child.getAttribute("id");
					if (Integer.parseInt(_id) == behandeling.getId()) {
						Node status = child.getElementsByTagName("status")
								.item(0).getFirstChild();

						status.setNodeValue(behandeling.getStatus());
						edited = true;
					}
				}
			}

			domdocument.writeDocument("behandelingen.xml", "behandelingen.xsd",
					document);
		} else
			System.out.println("XML document is null");

		if (!edited)
			System.out.println("Behandeling niet gevonden");
	}

	public static void addBehandeling(Behandeling behandeling) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument("behandelingen.xml",
				"behandelingen.xsd");

		if (getBehandeling(behandeling.getId()) == null) {
			Node rootElement = document.getElementsByTagName("behandelingen")
					.item(0);

			Element newbehandeling = document.createElement("behandelCode");
			newbehandeling.setAttribute("id",
					Integer.toString(behandeling.getId()));
			rootElement.appendChild(newbehandeling);

			Element klant = document.createElement("klantBsn");
			klant.appendChild(document.createTextNode(behandeling.getKlant()
					.getBsn()));
			newbehandeling.appendChild(klant);

			Element behandelCode = document.createElement("behandelcode");
			behandelCode.appendChild(document.createTextNode(Integer
					.toString(behandeling.getBehandelCode().getCode())));
			newbehandeling.appendChild(behandelCode);
			
			domdocument.writeDocument("behandelingen.xml", "behandelingen.xsd",
					document);
		} else {
			System.out.println("behandelCode bestaat al");
		}
	}

	public static ArrayList<Behandeling> getBehandelingen() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument("behandelingen.xml",
				"behandelingen.xsd");

		ArrayList<Behandeling> behandelingen = new ArrayList<Behandeling>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("behandeling");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					int id = Integer.parseInt(child.getAttribute("id"));
					String status = child.getElementsByTagName("status")
							.item(0).getTextContent();
					int behandelCode = Integer.parseInt(child
							.getElementsByTagName("behandelCode").item(0)
							.getTextContent());
					String klantBsn = child.getElementsByTagName("klantBsn")
							.item(0).getTextContent();
					behandelingen.add(new Behandeling(id, status, KlantDAO
							.getKlant(klantBsn), BehandelCodeDAO
							.getBehandelCode(behandelCode), AfspraakDAO
							.getAfspraken(id)));
				}
			}
		} else
			System.out.println("XML document is null");

		if (behandelingen.size() < 1)
			System.out.println("Geen behandelingen gevonden");

		return behandelingen;
	}

}
