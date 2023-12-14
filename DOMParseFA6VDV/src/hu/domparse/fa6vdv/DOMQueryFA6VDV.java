package hu.domparse.fa6vdv;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMQueryFA6VDV {


    public static void main(String[] args) {

        // Az XML dokumentum betöltése
        Document document = loadXMLDocument("C:\\Users\\z648505\\IdeaProjects\\DOMParseFA6VDV\\src\\hu\\domparse\\fa6vdv\\XMLFA6VDV.xml");

        // Példa lekérdezések
        printUserNames(document);
        printTotalAmountSpent(document);
        printMaxStockProduct(document);
        printRecentRegistrations(document);
        printOfflinePurchases(document);


        if (document != null) {
            // A dokumentum sikeresen betöltve
            // Itt folytathatod a további műveleteket
        } else {
            System.out.println("Hiba a dokumentum betöltésekor.");
        }

    }


    public static Document loadXMLDocument(String filePath) {
        try {
            // Létrehozunk egy DocumentBuilderFactory objektumot
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Létrehozunk egy DocumentBuilder objektumot a factory segítségével
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Betöltjük az XML dokumentumot
            File file = new File(filePath);
            Document document = builder.parse(file);

            // Opcionális: Normalizáljuk a dokumentumot
            document.getDocumentElement().normalize();

            // Visszaadjuk a betöltött dokumentumot
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





    // 1. Lekérdezés: Felhasználónevek kiírása
    private static void printUserNames(Document document) {
        NodeList users = document.getElementsByTagName("felhasznalo");
        for (int i = 0; i < users.getLength(); i++) {
            Node userNode = users.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                String userName = userElement.getElementsByTagName("felhasznalonev").item(0).getTextContent();
                System.out.println("Felhasználónév: " + userName);
            }
        }
    }

    // 2. Lekérdezés: Összesített összeg kiírása
    private static void printTotalAmountSpent(Document document) {
        NodeList payments = document.getElementsByTagName("fizetes");
        int totalAmount = 0;
        for (int i = 0; i < payments.getLength(); i++) {
            Node paymentNode = payments.item(i);
            if (paymentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element paymentElement = (Element) paymentNode;
                int amount = Integer.parseInt(paymentElement.getElementsByTagName("osszeg").item(0).getTextContent());
                totalAmount += amount;
            }
        }
        System.out.println("Összesített összeg: " + totalAmount);
    }

    // 3. Lekérdezés: Legnagyobb készletű termék nevének kiírása
    private static void printMaxStockProduct(Document document) {
        NodeList products = document.getElementsByTagName("termek");
        int maxStock = -1;
        String maxStockProductName = "";
        for (int i = 0; i < products.getLength(); i++) {
            Node productNode = products.item(i);
            if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                Element productElement = (Element) productNode;
                int stock = Integer.parseInt(productElement.getElementsByTagName("keszlet").item(0).getTextContent());
                if (stock > maxStock) {
                    maxStock = stock;
                    maxStockProductName = productElement.getElementsByTagName("termeknev").item(0).getTextContent();
                }
            }
        }
        System.out.println("Legnagyobb készletű termék neve: " + maxStockProductName);
    }

    // 4. Lekérdezés: Legutóbbi regisztrációk kiírása
    private static void printRecentRegistrations(Document document) {
        NodeList users = document.getElementsByTagName("felhasznalo");
        for (int i = 0; i < users.getLength(); i++) {
            Node userNode = users.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                String registrationDate = userElement.getElementsByTagName("regisztracio_datuma").item(0).getTextContent();
                System.out.println("Felhasználó regisztráció dátuma: " + registrationDate);
            }
        }
    }

    // 5. Lekérdezés:személyes vásárlások kiírása
    private static void printOfflinePurchases(Document document) {
        NodeList purchases = document.getElementsByTagName("vasarlas");
        for (int i = 0; i < purchases.getLength(); i++) {
            Node purchaseNode = purchases.item(i);
            if (purchaseNode.getNodeType() == Node.ELEMENT_NODE) {
                Element purchaseElement = (Element) purchaseNode;
                String offlinePurchase = purchaseElement.getElementsByTagName("szemelyes").item(0).getTextContent();
                if (offlinePurchase.equals("0")) {
                    System.out.println("szemelyes vásárlás");
                }
            }
        }
    }
}

