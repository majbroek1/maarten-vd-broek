/**
 * 
 */
package fysioSysteem.dataStorage;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fysioSysteem.domain.BehandelCode;

/**
 * @author Bob
 *
 */
public class BehandelCodeDAO {

	private static final String FILE_XML = "Data/behandelCodes.xml";
	private static final String FILE_XSD = "Data/behandelCodes.xsd";

	public static BehandelCode getBehandelCode(int code) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(BehandelCodeDAO.FILE_XML,
				BehandelCodeDAO.FILE_XSD);

		BehandelCode behandelCode = null;
		if (document != null) {
			NodeList list = document.getElementsByTagName("behandelCode");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _code = child.getAttribute("code");
					if (Integer.parseInt(_code) == code) {
						String naam = child
								.getElementsByTagName("behandelingNaam")
								.item(0).getTextContent();
						int aantalSessies = Integer.parseInt(child
								.getElementsByTagName("aantalSessies").item(0)
								.getTextContent());
						Double sessieDuur = Double.parseDouble(child
								.getElementsByTagName("sessieDuur").item(0)
								.getTextContent());
						Double tariefBehandeling = Double.parseDouble(child
								.getElementsByTagName("tariefBehandeling")
								.item(0).getTextContent());
						behandelCode = new BehandelCode(code, aantalSessies,
								naam, sessieDuur, tariefBehandeling);
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (behandelCode == null)
			System.out.println("behandelCode niet gevonden");

		return behandelCode;
	}

	public static void setBehandeling(BehandelCode behandelCode) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(BehandelCodeDAO.FILE_XML,
				BehandelCodeDAO.FILE_XSD);

		boolean edited = false;

		if (document != null) {
			NodeList list = document.getElementsByTagName("behandelCode");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					String _code = child.getAttribute("code");
					if (Integer.parseInt(_code) == behandelCode.getCode()) {
						Node naam = child
								.getElementsByTagName("behandelingNaam")
								.item(0).getFirstChild();
						Node aantalSessies = child
								.getElementsByTagName("aantalSessies").item(0)
								.getFirstChild();
						Node sessieDuur = child
								.getElementsByTagName("sessieDuur").item(0)
								.getFirstChild();
						Node tariefBehandeling = child
								.getElementsByTagName("tariefBehandeling")
								.item(0).getFirstChild();

						naam.setNodeValue(behandelCode.getBehandelingNaam());
						aantalSessies.setNodeValue(Double.toString(behandelCode
								.getAantalSessies()));
						sessieDuur.setNodeValue(Double.toString(behandelCode
								.getSessieDuur()));
						tariefBehandeling.setNodeValue(Double
								.toString(behandelCode.getTariefBehandeling()));
						edited = true;
					}
				}
			}

			domdocument.writeDocument(BehandelCodeDAO.FILE_XML,
					BehandelCodeDAO.FILE_XSD, document);
		} else
			System.out.println("XML document is null");

		if (!edited)
			System.out.println("behandelCode niet gevonden");
	}

	public static void addBehandeling(BehandelCode behandelCode) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(BehandelCodeDAO.FILE_XML,
				BehandelCodeDAO.FILE_XSD);

		if (getBehandelCode(behandelCode.getCode()) == null) {
			Node rootElement = document.getElementsByTagName("behandelCodes")
					.item(0);

			Element newbehandelCode = document.createElement("behandelCode");
			newbehandelCode.setAttribute("code",
					Integer.toString(behandelCode.getCode()));
			rootElement.appendChild(newbehandelCode);

			Element behandelingNaam = document.createElement("behandelingNaam");
			behandelingNaam.appendChild(document.createTextNode(behandelCode
					.getBehandelingNaam()));
			newbehandelCode.appendChild(behandelingNaam);

			Element aantalSessies = document.createElement("aantalSessies");
			aantalSessies.appendChild(document.createTextNode(Integer
					.toString(behandelCode.getAantalSessies())));
			newbehandelCode.appendChild(aantalSessies);

			Element sessieDuur = document.createElement("sessieDuur");
			sessieDuur.appendChild(document.createTextNode(Double
					.toString(behandelCode.getSessieDuur())));
			newbehandelCode.appendChild(sessieDuur);

			Element tariefBehandeling = document
					.createElement("tariefBehandeling");
			tariefBehandeling.appendChild(document.createTextNode(Double
					.toString(behandelCode.getTariefBehandeling())));
			newbehandelCode.appendChild(tariefBehandeling);

			domdocument.writeDocument(BehandelCodeDAO.FILE_XML,
					BehandelCodeDAO.FILE_XSD, document);
		} else {
			System.out.println("behandelCode bestaat al");
		}
	}

	public static ArrayList<BehandelCode> getBehandelingen() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(BehandelCodeDAO.FILE_XML,
				BehandelCodeDAO.FILE_XSD);

		ArrayList<BehandelCode> behandelCodes = new ArrayList<BehandelCode>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("behandelCode");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					int code = Integer.parseInt(child.getAttribute("code"));
					String naam = child.getElementsByTagName("behandelingNaam")
							.item(0).getTextContent();
					int aantalSessies = Integer.parseInt(child
							.getElementsByTagName("aantalSessies").item(0)
							.getTextContent());
					Double sessieDuur = Double.parseDouble(child
							.getElementsByTagName("sessieDuur").item(0)
							.getTextContent());
					Double tariefBehandeling = Double.parseDouble(child
							.getElementsByTagName("tariefBehandeling").item(0)
							.getTextContent());
					behandelCodes.add(new BehandelCode(code, aantalSessies,
							naam, sessieDuur, tariefBehandeling));
				}
			}
		} else
			System.out.println("XML document is null");

		if (behandelCodes.size() < 1)
			System.out.println("Geen behandelCodes gevonden");

		return behandelCodes;
	}

}
