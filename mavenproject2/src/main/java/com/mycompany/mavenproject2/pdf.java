/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.w3c.dom.Document;

/**
 *
 * @author greyp
 */
public class pdf {
    
        public static void main(String[] args) throws IOException, ParserConfigurationException {
        // load the PDF file using PDFBox
        PDDocument pdf = PDDocument.load(new java.io.File("C:/Users/greyp/OneDrive/Desktop/test.pdf"));
        // create the DOM parser
        PDFDomTree parser = new PDFDomTree();
       // parse the file and get the DOM Document
         Document dom = parser.createDOM(pdf);
         System.out.println(dom);
      
    }

}
