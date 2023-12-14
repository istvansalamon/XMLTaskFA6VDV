package hu.domparse.fa6vdv;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DOMWriteFA6VDV {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        // XML dokumentum létrehozása
        Document document = createXMLDocument();

        // Adatok hozzáadása a dokumentumhoz (a példa adatok alapján)
        addDataToDocument(document);

        // XML fájlba írás
        writeXMLDocument(document, "C:\\Users\\z648505\\IdeaProjects\\DOMParseFA6VDV\\src\\hu\\domparse\\fa6vdv\\XMLFA6VDV_1.xml");
    }

    private static void writeXMLDocument(Document document, String filePath) {
        try {
        // Létrehoz egy TransformerFactory-t
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        // Létrehoz egy Transformer objektumot
        Transformer transformer = transformerFactory.newTransformer();

        // Beállítja a kimeneti fájl karakterkódolását
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        // Beállítja a kimeneti XML fájl tartalmazzon behúzást
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Létrehoz egy DOMSource-t a dokumentumhoz
        DOMSource source = new DOMSource(document);

        // Létrehoz egy StreamResult-t a kimeneti fájlhoz
        StreamResult result = new StreamResult(new File(filePath));

        // Végrehajtja a transzformációt és kiírja az XML-t a fájlba
        transformer.transform(source, result);

        System.out.println("Az XML dokumentum kiírása sikeres volt.");
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    private static Document createXMLDocument() throws ParserConfigurationException {
        // XML dokumentum létrehozása
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        // Gyökérelem létrehozása
        Element onlinevasarlas = document.createElement("onlinevasarlas");
        onlinevasarlas.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        onlinevasarlas.setAttribute("xsi:noNamespaceSchemaLocation", "xdmschema_FA6vdv.xsd");
        document.appendChild(onlinevasarlas);

        return document;
    }

    private static void addDataToDocument(Document document) {
        // adatok hozzáadása a dokumentumhoz
        Element rendeles1 = createRendelesElement(document, "R1", "RVK1", "2023-01-01", "1328", "Budapest", "Kossuth utca", "12", "14990");
        Element rendeles2 = createRendelesElement(document, "R2", "RVK2", "2023-01-01", "3300", "Eger", "Dobó", "56", "8990");

        Element felhasznalo1 = createFelhasznaloElement(document, "F1", "szabi", "szabi.lantos@freemail.com", "2022-12-02");
        Element felhasznalo2 = createFelhasznaloElement(document, "F2", "evike", "evike@citromail.com", "2016-04-15");

        Element profil1 = createProfilElement(document, "P1", "John Doe", "06204455788", "2023-04-22");
        Element profil2 = createProfilElement(document, "P2", "Jane Doe", "06301455789", "2023-02-01");

        Element termek1 = createTermekElement(document, "T1", "Laptop", "250000", "20", "TVK1");
        Element termek2 = createTermekElement(document, "T2", "Mobil telefon", "80000", "50","TVK2");


        // Gyökérelem kiválasztása
        Element onlinevasarlas = document.getDocumentElement();

        // elemek hozzáadása a gyökérelemhez
        onlinevasarlas.appendChild(rendeles1);
        onlinevasarlas.appendChild(rendeles2);
        onlinevasarlas.appendChild(felhasznalo1);
        onlinevasarlas.appendChild(felhasznalo2);
        onlinevasarlas.appendChild(profil1);
        onlinevasarlas.appendChild(profil2);
        onlinevasarlas.appendChild(termek1);
        onlinevasarlas.appendChild(termek2);

    }

    // Az alábbiakban olyan függvények találhatók, amelyek létrehozzák a különböző típusú elemeket (rendelés, felhasználó, stb.).

    private static Element createRendelesElement(Document document, String rendelesid, String RVK, String rendelesDatum, String irsz, String varos, String utca, String hsz, String osszeg) {
        // Létrehoz egy "rendeles" elemet
        Element rendeles = document.createElement("rendeles");

        // Beállítja az "rendelesid" attribútumot
        rendeles.setAttribute("rendelesid", rendelesid);

        // Beállítja az "R-V_K" attribútumot
        rendeles.setAttribute("R-V_K", RVK);

        // Létrehoz és hozzáadja a "rendeles_datuma" elemet
        Element rendelesDatumElem = document.createElement("rendeles_datuma");
        rendelesDatumElem.appendChild(document.createTextNode(rendelesDatum));
        rendeles.appendChild(rendelesDatumElem);

        // Létrehozza a "szallitasi_cim" elemet és hozzáadja az alárendelt elemeket
        Element szallitasiCim = document.createElement("szallitasi_cim");
        szallitasiCim.appendChild(createElement(document, "irsz", irsz));
        szallitasiCim.appendChild(createElement(document, "varos", varos));
        szallitasiCim.appendChild(createElement(document, "utca", utca));
        szallitasiCim.appendChild(createElement(document, "hsz", hsz));
        rendeles.appendChild(szallitasiCim);

        // Létrehoz és hozzáadja az "osszeg" elemet
        Element osszegElem = document.createElement("osszeg");
        osszegElem.appendChild(document.createTextNode(osszeg));
        rendeles.appendChild(osszegElem);

        // Visszaadja az elkészült "rendeles" elemet
        return rendeles;
    }

    // Segédfüggvény az egyszerű XML elemek létrehozásához
    private static Element createElement(Document document, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.appendChild(document.createTextNode(textContent));
        return element;
    }
    private static Element createFelhasznaloElement(Document document, String felhasznaloid, String felhasznalonev, String email, String regisztracioDatum) {
        // Létrehoz egy "felhasznalo" elemet
        Element felhasznalo = document.createElement("felhasznalo");

        // Beállítja a "felhasznaloid" attribútumot
        felhasznalo.setAttribute("felhasznaloid", felhasznaloid);

        // Létrehoz és hozzáadja a "felhasznalonev" elemet
        Element felhasznalonevElem = document.createElement("felhasznalonev");
        felhasznalonevElem.appendChild(document.createTextNode(felhasznalonev));
        felhasznalo.appendChild(felhasznalonevElem);

        // Létrehoz és hozzáadja az "email" elemet
        Element emailElem = document.createElement("email");
        emailElem.appendChild(document.createTextNode(email));
        felhasznalo.appendChild(emailElem);

        // Létrehoz és hozzáadja a "regisztracio_datuma" elemet
        Element regisztracioDatumElem = document.createElement("regisztracio_datuma");
        regisztracioDatumElem.appendChild(document.createTextNode(regisztracioDatum));
        felhasznalo.appendChild(regisztracioDatumElem);

        // Visszaadja az elkészült "felhasznalo" elemet
        return felhasznalo;
    }

    private static Element createProfilElement(Document document, String profilid, String nev, String telefonszam, String utolso_belepes_datuma) {
        // Létrehozza a "profil" elemet
        Element profil = document.createElement("profil");
        profil.setAttribute("profilid", profilid);

        // Létrehozza és hozzáadja a "nev" elemet
        Element nevElement = document.createElement("nev");
        nevElement.appendChild(document.createTextNode(nev));
        profil.appendChild(nevElement);

        // Létrehozza és hozzáadja a "telefonszam" elemet
        Element telefonszamElement = document.createElement("telefonszam");
        telefonszamElement.appendChild(document.createTextNode(telefonszam));
        profil.appendChild(telefonszamElement);

        // Létrehozza és hozzáadja az "utolso_belepes_datuma" elemet
        Element utolsoBelepesElement = document.createElement("utolso_belepes_datuma");
        utolsoBelepesElement.appendChild(document.createTextNode(utolso_belepes_datuma));
        profil.appendChild(utolsoBelepesElement);

        // Visszaadja a létrehozott "profil" elemet
        return profil;
    }

    private static Element createTermekElement(Document document, String termekid, String termeknev, String ar, String keszlet, String tVK) {
        Element termek = document.createElement("termek");
        termek.setAttribute("termekid", termekid);
        termek.setAttribute("T-V_K", tVK);

        Element termeknevElem = createElement(document, "termeknev", termeknev);
        Element arElem = createElement(document, "ar", ar);
        Element keszletElem = createElement(document, "keszlet", keszlet);

        termek.appendChild(termeknevElem);
        termek.appendChild(arElem);
        termek.appendChild(keszletElem);

        return termek;
    }




}
