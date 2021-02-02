/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

/**
 *
 * @author greyp
 */
public class SplitLib {
   
    
    private String method;
    
    SplitLib(){
        //default method
        this.method = "mammoth";
    }
    
    public static void main(String args[]) throws IOException, Exception{

        //PATH FOR FILE MANAGEMENT
        //input directory
        String InputDir = "C:/Users/greyp/OneDrive/Desktop/test/";
        //input file
        String filename = "test23.docx";
        //Output directory
        String OutputDir = help.create_directory("C:/Users/greyp/OneDrive/Desktop/", "Results");
        //file contaning the splitting rules
        String rulesFile ="rules.txt";
        //errors
        String [] errors = null;
        
        SplitLib test = new SplitLib();
        test.splitFile(InputDir+filename, OutputDir, rulesFile, errors);
    }
    /**
     * 
     * @param method 
     */
    public synchronized void selectConvertMethod(String method){
        this.method = method;
    }
    
    /**
     * 
     * @param input_file
     * @param outputDir
     * @param rules_file
     * @param errorMsg
     * @return 
     */
    public synchronized int splitFile(String input_file, String outputDir,
            String rules_file, String[] errorMsg) throws IOException{
           //A list to list the rules
           List<String> rules = new ArrayList<String>();
           //A BufferedReader to read the rules from rules file
           BufferedReader reader = new BufferedReader(new FileReader("C:/Users/greyp/OneDrive/Desktop/test/rules.txt"));
           
            //select method here
            //selectConvertMethod("method");

            //Convert word to html
            if(method.equals("mammoth"))
                MammothConvert(input_file, outputDir);
            else{
            }

            //read rules here
           try{
               String line;
            while((line=reader.readLine()) != null){
                rules.add(line);
            }
            reader.close();
           }catch(IOException ex){
           Logger.getLogger(SplitLib.class.getName()).log(Level.SEVERE, null, ex);
           }
           help.first_mark(outputDir+"/mammoth.html",outputDir);
           help.break_here(outputDir+"/mammoth.html",outputDir);
            //start spliting according the rules
           for(int i=0; i<rules.size(); i++){
           String ruletoapply = rules.get(i);
            //line starting with # is a comment
           if(ruletoapply.startsWith("#"))
               continue;
           else if(ruletoapply.equals("H1")){
                help.h_split(outputDir+"/mammoth.html",outputDir, 1);
           }else if(ruletoapply.equals("H2")){
                help.h_split(outputDir+"/mammoth.html",outputDir, 2);
           }else if(ruletoapply.equals("H3")){
                help.h_split(outputDir+"/mammoth.html",outputDir, 3);
           }else if(ruletoapply.equals("H4")){
                help.h_split(outputDir+"/mammoth.html",outputDir, 4);
           }else if(ruletoapply.equals("H5")){
                help.h_split(outputDir+"/mammoth.html",outputDir, 5);
           }else if(ruletoapply.equals("H6")){
                help.h_split(outputDir+"/mammoth.html",outputDir, 6);
           }else if(ruletoapply.startsWith("H1-contains=")){
                help.h_contains_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(12), 1);
            } else if(ruletoapply.contains("H2-contains=")) {
                help.h_contains_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(12), 2);
            }else if(ruletoapply.contains("H3-contains=")){
                help.h_contains_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(12), 3);
            }else if(ruletoapply.contains("H4-contains=")){
                help.h_contains_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(12), 4);
            }else if(ruletoapply.contains("H5-contains=")){
                help.h_contains_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(12), 5);
            }else if(ruletoapply.contains("H6-contains=")){
                help.h_contains_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(12), 6);
            }else if(ruletoapply.contains("H1-starts-with=")){
                help.h_starts_with_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(15), 1);
            }else if(ruletoapply.startsWith("H2-starts-with=")){
                help.h_starts_with_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(15), 2);
            }else if(ruletoapply.contains("H3-starts-with=")){
                help.h_starts_with_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(15), 3);
            }else if(ruletoapply.contains("H4-starts-with=")){
                help.h_starts_with_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(15), 4);
            }else if(ruletoapply.contains("H5-starts-with=")){
                help.h_starts_with_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(15), 5);
            }else if(ruletoapply.contains("H6-starts-with=")){
                help.h_starts_with_split(outputDir+"/mammoth.html",outputDir,ruletoapply.substring(15), 6);
            }else{
                System.out.println();
            }
           //help.cut_marked_spots(outputDir+"/mammoth.html", outputDir);
                }
           help.cut_marked_spots(outputDir+"/mammoth.html", outputDir);
        return 0;
        }

        /**
         * 
         * @param input_file
         * @param outputDir 
         */
        public synchronized void MammothConvert(String input_file, String outputDir){
            try {
                DocumentConverter converter = new DocumentConverter();
                Result<String> result = converter.convertToHtml(new File(input_file));
                String html = result.getValue(); // The generated HTML
                Set<String> warnings = result.getWarnings(); // Any warnings during conversion
                final File f = new File(outputDir,"mammoth.html");
                FileUtils.writeStringToFile(f, html, "UTF-8");
            }   catch (IOException ex) {
                Logger.getLogger(SplitLib.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("No such file or directory"); 
                //System.out.println(ex); 
            }
        }
        

    }

    

