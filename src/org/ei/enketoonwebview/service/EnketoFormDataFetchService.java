package org.ei.enketoonwebview.service;

import android.util.Pair;
import org.ei.enketoonwebview.agent.HttpAgent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;

public class EnketoFormDataFetchService {
    private HttpAgent httpAgent;

    public EnketoFormDataFetchService(HttpAgent httpAgent) {
        this.httpAgent = httpAgent;
    }

    public Pair<String, String> fetch(String url) {
        InputStream inputStream = httpAgent.fetch(url);
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList nodes = root.getChildNodes();

            String model = nodeToString(nodes.item(0));
            String form = nodeToString(nodes.item(1));
            return new Pair<String, String>(model, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String nodeToString(Node node) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(writer));
        return writer.getBuffer().toString().replaceAll("\n|\r|\t", "");
    }
}
