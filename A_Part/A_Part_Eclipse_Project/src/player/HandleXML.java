package player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
* This class is handling an XML Page 
* @author Christos Markou 
* @version 1
*/

public class HandleXML {
/**
* This class is getting an XML page from last.fm servise and according to the switch mode 
 returns Artist Bio or else a URL to the Album Picture. 
*/
	public String getXML(String path, String Switch) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			// System.out.println(Switch);

			// System.out.println(doc.getElementsByTagName("lfm").item(0).getTextContent());
			if (Switch == "Author") {
				// System.out.println(" in");

				System.out.println(doc.getElementsByTagName("content").item(0)
						.getTextContent());
				return doc.getElementsByTagName("content").item(0)
						.getTextContent();
			} else if (Switch == "Album") {
				System.out.println("Album:"
						+ doc.getElementsByTagName("name").item(0)
								.getTextContent());
				System.out.println("Picture URL:"
						+ doc.getElementsByTagName("image").item(1)
								.getTextContent());
				return doc.getElementsByTagName("image").item(1)
						.getTextContent();
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Not supported for this Composer";
		

	}

}
