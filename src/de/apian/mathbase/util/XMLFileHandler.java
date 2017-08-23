/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Utility-Klasse zum Laden, Bearbeiten und Speichern von XML-Dateien
 * <p>
 * Jedes Objekt dieser Klasse bearbeitet immer eine einzige XML-Datei und jede XML-Datei sollte nur von einem
 * {@code XMLFileHandler} -Objekt bearbeitet werden.
 * <p>
 * Die Dateien werden zuerst mit Hilfe der DOM-API eingelesen und dann als Nodelist zurückgegeben,
 * um die Bearbeitung der Datei für andere Klassen möglichst einfach zu gestalten.
 *
 * @author Benedikt Mödl
 * @version 1.0
 * @see <a href="https://www.tutorialspoint.com/java_xml/java_dom_parser.htm">Java DOM Parser</a>
 * @since 1.0
 */
public class XMLFileHandler {

    /**
     * Dass {@code Document} -Objekt der eingelesenen XML-Datei.
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/org/w3c/dom/Document.html">Document</a>
     * @since 1.0
     */
    private Document doc;

    /**
     * Der Konstruktor des FileHandlers
     *
     * @param filePath Pfad der zu ladenden Datei relativ zum Arbeitsverzeichnis
     * @throws IOException wenn die Datei nicht geladen oder geparst werden konnte
     * @since 1.0
     */
    public XMLFileHandler(String filePath) throws IOException {
        //Erzeugt eine Factory, die den Builder erzeugt
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        dFactory.setIgnoringElementContentWhitespace(true);

        //Erzeugt den Builder, der anschließend die Datei einliest
        try {
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            doc = builder.parse(filePath);
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            throw new IOException("Fehler beim Parsen der Datei");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Methode, die das Document Objekt in einer Datei speichert
     *
     * @param targetPath Pfad der Zieldatei
     * @throws IOException wenn das Speichern fehlgeschlagen ist
     * @since 1.0
     */
    public void saveDocToXml(String targetPath) throws IOException {
        //Erzeut eine Quelle aus dem Document, ein Ziel aus dem Zielpfad und einen Transformer, der die XML-Datei schlussendlich erzeugt
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(targetPath));
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = tFactory.newTransformer();
            //Parameter des Transformers werden so gesetzt, dass die XML-Datei später auch lesbar ist
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Fehler beim Speichern der Datei \"" + targetPath + "\"");
        }
    }

    /**
     * Getter-Methode für das {@code Document} -Objekt
     *
     * @return Das {@code Document} -Objekt
     * @since 1.0
     */
    public Document getDoc() {
        return doc;
    }
}