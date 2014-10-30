/**
 * 
 */
package fysioSysteem.dataStorage;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fysioSysteem.domain.Diagnose;

/**
 * @author Bob
 *
 */
public class DiagnoseDAO {

	private static final String FILE_XML = "Data/diagnoses.xml";
	private static final String FILE_XSD = "Data/diagnoses.xsd";

	public static Diagnose getDiagnose(int diagnoseCode) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(DiagnoseDAO.FILE_XML,
				DiagnoseDAO.FILE_XSD);

		Diagnose diagnose = null;
		if (document != null) {
			NodeList list = document.getElementsByTagName("diagnose");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					int code = Integer.parseInt(child.getAttribute("code"));
					if (code == diagnoseCode) {
						String omschrijving = child
								.getElementsByTagName("omschrijving").item(0)
								.getTextContent();
						String klantBsn = child
								.getElementsByTagName("klantBsn").item(0)
								.getTextContent();
						diagnose = new Diagnose(diagnoseCode, omschrijving,
								KlantDAO.getKlant(klantBsn));
					}
				}
			}
		} else
			System.out.println("XML document is null");

		if (diagnose == null)
			System.out.println("Diagnose niet gevonden");

		return diagnose;
	}

	public static void setDiagnose(Diagnose diagnose) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(DiagnoseDAO.FILE_XML,
				DiagnoseDAO.FILE_XSD);

		boolean edited = false;

		if (document != null) {
			NodeList list = document.getElementsByTagName("diagnose");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					int code = Integer.parseInt(child.getAttribute("code"));
					if (code == diagnose.getCode()) {
						Node omschrijving = child
								.getElementsByTagName("omschrijving").item(0)
								.getFirstChild();

						Node klantBsn = child.getElementsByTagName("klantBsn")
								.item(0).getFirstChild();

						omschrijving.setNodeValue(diagnose.getOmschrijving());
						klantBsn.setNodeValue(diagnose.getKlant().getBsn());
					}
				}
			}

			domdocument.writeDocument(DiagnoseDAO.FILE_XML,
					DiagnoseDAO.FILE_XSD, document);
		} else
			System.out.println("XML document is null");

		if (!edited)
			System.out.println("Diagnose niet gevonden");
	}

	public static void addDiagnose(Diagnose diagnose) {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(DiagnoseDAO.FILE_XML,
				DiagnoseDAO.FILE_XSD);

		if (getDiagnose(diagnose.getCode()) == null) {
			Node rootElement = document.getElementsByTagName("diagnoses").item(
					0);

			Element newdiagnose = document.createElement("diagnose");
			newdiagnose.setAttribute("code",
					Integer.toString(diagnose.getCode()));
			rootElement.appendChild(newdiagnose);

			Element omschrijving = document.createElement("omschrijving");
			omschrijving.appendChild(document.createTextNode(diagnose
					.getOmschrijving()));
			newdiagnose.appendChild(omschrijving);

			Element klantBsn = document.createElement("klantBsn");
			klantBsn.appendChild(document.createTextNode(diagnose.getKlant()
					.getBsn()));
			newdiagnose.appendChild(klantBsn);

			domdocument.writeDocument(DiagnoseDAO.FILE_XML,
					DiagnoseDAO.FILE_XSD, document);
		} else {
			System.out.println("diagnose bestaat al");
		}
	}

	public static ArrayList<Diagnose> getDiagnoses() {
		XmlDOMDocument domdocument = new XmlDOMDocument();
		Document document = domdocument.getDocument(DiagnoseDAO.FILE_XML,
				DiagnoseDAO.FILE_XSD);

		ArrayList<Diagnose> diagnoses = new ArrayList<Diagnose>();
		if (document != null) {
			NodeList list = document.getElementsByTagName("diagnose");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					Element child = (Element) node;
					int code = Integer.parseInt(child.getAttribute("code"));
					String omschrijving = child
							.getElementsByTagName("omschrijving").item(0)
							.getTextContent();
					String klantBsn = child.getElementsByTagName("klantBsn")
							.item(0).getTextContent();
					diagnoses.add(new Diagnose(code, omschrijving, KlantDAO
							.getKlant(klantBsn)));
				}
			}
		} else
			System.out.println("XML document is null");

		if (diagnoses.size() < 1)
			System.out.println("Geen diagnoses gevonden");

		return diagnoses;
	}

}
