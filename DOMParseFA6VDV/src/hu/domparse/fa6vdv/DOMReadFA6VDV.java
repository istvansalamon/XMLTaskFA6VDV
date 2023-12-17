package hu.domparse.fa6vdv;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class DOMReadFA6VDV {

    public static void main(String[] args) {
        try {
            // XML fájl elérési útvonala
            String xmlfile = "C:\\Users\\z648505\\IdeaProjects\\DOMParseFA6VDV\\src\\hu\\domparse\\fa6vdv\\XMLFA6VDV.xml";

            // XML fájl beolvasása és DOM objektum létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlfile));

            // Dokumentum feldolgozása
            processDocument(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processDocument(Document document) {
        // Dokumentum kiírása a konzolra
        printNode(document.getDocumentElement(), 0);
    }

    private static void printNode(Node node, int indentLevel) {
        // Behúzás hozzáadása
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("  ");
        }

        // Az elem neve
        System.out.print("<" + node.getNodeName());

        // Az elem attribútumai
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                System.out.print(" " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
            }
        }

        // Az elem tartalma
        NodeList children = node.getChildNodes();
        if (children.getLength() > 0) {
            System.out.println(">");
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    printNode(child, indentLevel + 1);
                } else if (child.getNodeType() == Node.TEXT_NODE && !child.getNodeValue().trim().isEmpty()) {
                    // Szöveges tartalom kiírása, ha nem üres
                    for (int j = 0; j < indentLevel + 1; j++) {
                        System.out.print("  ");
                    }
                    System.out.println(child.getNodeValue().trim());
                }
            }
            // Behúzás a zárótagelemhez
            for (int i = 0; i < indentLevel; i++) {
                System.out.print("  ");
            }
            System.out.println("</" + node.getNodeName() + ">");
        } else {
            // Nincs gyerek, zárást kiírjuk
            System.out.println("/>");
        }
    }





}
