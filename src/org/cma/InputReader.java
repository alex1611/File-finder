package org.cma;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class InputReader{
    private final static String INPUT_FILE_NAME = "resources/input.xml";
    private static String startDir;
    private static String outFileName;
    public static void openDocument(){
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(INPUT_FILE_NAME);
            startDir = document.getElementsByTagName("inputFolderName").item(0).getTextContent();
            outFileName = document.getElementsByTagName("outputFileName").item(0).getTextContent();
        } catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }

    }

    public static String getOutFileName() {
        return outFileName;
    }

    public static String getStartDir() {
        return startDir;
    }
}
