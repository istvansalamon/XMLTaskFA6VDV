package hu.domparse.fa6vdv;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DOMModifyFa6vdv {

    public static void main(String[] args) {
        try {
            // XML fájl elérési útvonala
            String xmlFile = "C:\\Users\\z648505\\IdeaProjects\\DOMParseFA6VDV\\src\\hu\\domparse\\fa6vdv\\XMLFA6VDV.xml";

            // XML fájl beolvasása és DOM objektum létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFile));

            // Módosítások végrehajtása
            performModifications(document);

            // Módosított dokumentum kiírása a konzolra
            printDocument(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void performModifications(Document document) {

        //  Módosítás: rendelés összegének növelése
        modifyOrderAmount(document, "R1", 35000);

        //  Módosítás: felhasználó nevének megváltoztatása
        modifyUserName(document, "F1", "Szabolcs Lantos");

        //  Módosítás: termék árának csökkentése
        modifyProductPrice(document, "P2", 2000);

        //  Módosítás: fizetési dátum átírása
        modifyTransactionDate(document, "T1", "2023-08-05");

        //  Módosítás: készlet változtatás
        modifyProductStock(document, "P1", 51);
    }

    //  Módosítás: rendelés összegének növelése
    private static void modifyOrderAmount(Document document, String orderID, int amountIncrease) {
        NodeList rendelesNodeList = document.getElementsByTagName("rendeles");
        // végig megyünk NodeList-en
        for (int i = 0; i < rendelesNodeList.getLength(); i++) {
            Node rendelesNode = rendelesNodeList.item(i);

            // Ellenőrzés, hogy a NodeList aktuális eleme egy ELEMENT_NODE típusú elem-e
            if (rendelesNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rendelesElement = (Element) rendelesNode;
                String currentOrderID = rendelesElement.getAttribute("rendelesid");
                // Ellenőrzés, hogy az aktuális orderid azonosítója megegyezik-e a keresettel
                if (currentOrderID.equals(orderID)) {
                    int currentAmount = Integer.parseInt(rendelesElement.getElementsByTagName("osszeg").item(0).getTextContent());
                    int newAmount = currentAmount + amountIncrease;
                    //módosítás végrehajtása
                    rendelesElement.getElementsByTagName("osszeg").item(0).setTextContent(Integer.toString(newAmount));
                    break;
                }
            }
        }
    }
    //  Módosítás: felhasználó nevének megváltoztatása
    private static void modifyUserName(Document document, String userID, String newName){
        //NodeList létrehozása
        NodeList felhasznaloNodeList = document.getElementsByTagName("felhasznalo");

            for (int i = 0; i < felhasznaloNodeList.getLength(); i++) {
                Node felhasznaloNode = felhasznaloNodeList.item(i);

                if (felhasznaloNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element felhasznaloElement = (Element) felhasznaloNode;
                    String currentUserID = felhasznaloElement.getAttribute("felhasznaloid");

                    if (currentUserID.equals(userID)) {
                        felhasznaloElement.getElementsByTagName("felhasznalonev").item(0).setTextContent(newName);
                        break;
                    }
                }
            }
        }

    //  Módosítás: termék árának csökkentése
        private static void modifyProductPrice(Document document, String productID, int priceDecrease) {
        NodeList termekNodeList = document.getElementsByTagName("termek");

        for (int i = 0; i < termekNodeList.getLength(); i++) {
            Node termekNode = termekNodeList.item(i);

            if (termekNode.getNodeType() == Node.ELEMENT_NODE) {
                Element termekElement = (Element) termekNode;
                String currentProductID = termekElement.getAttribute("termekid");

                if (currentProductID.equals(productID)) {
                    int currentPrice = Integer.parseInt(termekElement.getElementsByTagName("ar").item(0).getTextContent());
                    int newPrice = Math.max(0, currentPrice - priceDecrease);
                    termekElement.getElementsByTagName("ar").item(0).setTextContent(Integer.toString(newPrice));
                    break;
                }
            }
        }
    }

    //  Módosítás: fizetési dátum átírása
    private static void modifyTransactionDate(Document document, String transactionID, String newDate) {
        // Fizetési tranzakciók NodeList létrehozása
        NodeList fizetesNodeList = document.getElementsByTagName("fizetes");
        // végig megyünk NodeList-en
        for (int i = 0; i < fizetesNodeList.getLength(); i++) {

            Node fizetesNode = fizetesNodeList.item(i);
            // Ellenőrzés, hogy a NodeList aktuális eleme egy ELEMENT_NODE típusú elem-e
            if (fizetesNode.getNodeType() == Node.ELEMENT_NODE) {
                // Az aktuális elem konvertálása Element típusúvá
                Element fizetesElement = (Element) fizetesNode;
                // Az aktuális fizetési tranzakcióhoz tartozó azonosító lekérése
                String currentTransactionID = fizetesElement.getAttribute("fizetesid");
                // Ellenőrzés, hogy az aktuális fizetési tranzakció azonosítója megegyezik-e a keresettel
                if (currentTransactionID.equals(transactionID)) {
                    // A fizetési tranzakció dátum elemének lekérése és a módosítás végrehajtása
                    fizetesElement.getElementsByTagName("tranzakcio_datuma").item(0).setTextContent(newDate);
                    break;
                }
            }
        }
    }

    //  Módosítás: készlet változtatá
    private static void modifyProductStock(Document document, String productID, int stockChange) {
        NodeList termekNodeList = document.getElementsByTagName("termek");

        for (int i = 0; i < termekNodeList.getLength(); i++) {
            Node termekNode = termekNodeList.item(i);

            if (termekNode.getNodeType() == Node.ELEMENT_NODE) {
                Element termekElement = (Element) termekNode;
                String currentProductID = termekElement.getAttribute("termekid");

                if (currentProductID.equals(productID)) {
                    int currentStock = Integer.parseInt(termekElement.getElementsByTagName("keszlet").item(0).getTextContent());
                    int newStock =  currentStock + stockChange;
                    termekElement.getElementsByTagName("keszlet").item(0).setTextContent(Integer.toString(newStock));
                    break;
                }
            }
        }
    }


    private static void printDocument(Document document) {
        try {
            // Kiíratás a konzolra
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(System.out));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
