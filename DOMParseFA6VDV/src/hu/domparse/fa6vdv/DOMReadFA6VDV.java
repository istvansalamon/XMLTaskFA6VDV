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
            //processElement(document);
            //document.getDocumentElement().normalize();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processDocument(Document document) {
        // Az összes elem feldolgozása
        NodeList rootNodes = document.getChildNodes();
        for (int i = 0; i < rootNodes.getLength(); i++) {
            Node rootNode = rootNodes.item(i);
            if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
                // Az összes gyerekelem feldolgozása
                processElement(rootNode);
            }
        }
    }

    private static void processElement(Node element) {


        if (element.getNodeType() == Node.ELEMENT_NODE) {
            //Elem neve
            System.out.println("Elem: " + element.getNodeName());

            // Elem attribútumai
            NamedNodeMap keys = element.getAttributes();
            for (int i = 0; i < keys.getLength(); i++) {
                Node attribute = keys.item(i);
                System.out.println("kulcs: " + attribute.getNodeName() + " = " + attribute.getNodeValue());
            }

            // Elem tartalma
            NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    // feldolgozás a gyerekelemeknél
                    processElement(child);
                } else if (child.getNodeType() == Node.TEXT_NODE && !child.getNodeValue().trim().isEmpty()) {
                    //tartalom kiírása
                    System.out.println("tartalom: " + child.getNodeValue().trim());
                }
            }

            System.out.println();// Üres sor elvalasztáshoz
        }
    }
}
