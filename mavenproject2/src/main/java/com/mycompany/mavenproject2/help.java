/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
/**
 *
 * @author greyp
 */
public class help {
    public static void h_split(String htmlFile, String results_dir, int j) throws IOException{
        mark_cut_spots(htmlFile,results_dir, j);
    }
    public static void h_contains_split(String htmlFile, String results_dir, String containedText, int j) throws IOException{
        mark_cut_spots_contains(htmlFile, results_dir, containedText,j);
    }
    public static void h_starts_with_split(String htmlFile, String results_dir, String containedText, int j) throws IOException{
        mark_cut_spots_starts(htmlFile, results_dir, containedText,j);
    }
    
    public static String create_directory(String where, String name){
      try {
        Path path = Paths.get(where+"/"+name);
        Files.createDirectories(path);
        System.out.println("Directory is created!");
        return path.toString();
      } catch (IOException e) {
        System.err.println("Failed to create directory!" + e.getMessage());
      }
    return null;
      }      
  
     public static void mark_cut_spots(String htmlfile,String results_dir, int h_size) throws IOException{ 
        File input = new File(htmlfile);
        Document document = Jsoup.parse(input,"UTF-8");
        Elements body = document.getAllElements();
        for(Element ref : body){
            if(ref.outerHtml().startsWith("<h"+h_size)){
                ref.before("<!--start of mark here-->");
            }
        }
        FileWriter myWriter = new FileWriter(results_dir+"/"+"mammoth"+".html", false);
        myWriter.write(document.html());
        myWriter.close();
     }
     
    public static void cut_marked_spots(String htmlfile,String results_dir) throws IOException{
        String title = null;
        int flag=0;
        File input = new File(htmlfile);
        Document document = Jsoup.parse(input,"UTF-8");
        List<List<Node>> articles = new ArrayList<List<Node>>();
        List<Node> currentArticle = new ArrayList<Node>();
        String co;
        for(Node node : document.getElementsByTag("body").get(0).childNodes()){
            if(node.nodeName().equals("#comment")){
                co = node.toString();
                //System.out.println(co.toString());
                //if(co.toString().contains("<!--no break here-->"))
               // else if(co.toString().contains("<!--start of mark here-->")){
                currentArticle = new ArrayList<Node>();
                articles.add(currentArticle);
            }
                currentArticle.add(node);
           // }
            }
        int i=0;
        FileWriter content;
         content = new FileWriter(results_dir+"/"+"contents.txt");
        for(List<Node> article : articles){
            FileWriter myWriter = null;
            myWriter = new FileWriter(results_dir+"/"+"cut"+i+".html");
            for(Node node : article){
               myWriter.write(node.outerHtml());
            }  
            myWriter.close();
            if(flag==1){
            content.write("\n"+title+"cut"+i+".html");
            }
            else
            content.write("\n"+"section"+i+"   "+"cut"+i+".html");
           i++; 
        }
        content.close();
    }  
    public static void mark_cut_spots_contains(String htmlfile,String results_dir, String containedText, int j) throws IOException{
        File input = new File(htmlfile);
        Document document = Jsoup.parse(input,"UTF-8");
        Elements body = document.getAllElements();
        for(Element ref : body){
            if(ref.ownText().contains(containedText) && ref.outerHtml().startsWith("<h"+j)){
                ref.before("<!--start of mark here-->");
            }
        }
        FileWriter myWriter = new FileWriter(results_dir+"/"+"mammoth"+".html", false);
        myWriter.write(document.html());
        myWriter.close();
     }
      public static void mark_cut_spots_starts(String htmlfile,String results_dir, String containedText, int j) throws IOException{
        File input = new File(htmlfile);
        Document document = Jsoup.parse(input,"UTF-8");
        Elements body = document.getAllElements();
        for(Element ref : body){
            if(ref.ownText().startsWith(containedText) && ref.outerHtml().startsWith("<h"+j)){
                ref.before("<!--start of mark here-->");
            }
        }
        FileWriter myWriter = new FileWriter(results_dir+"/"+"mammoth"+".html", false);
        myWriter.write(document.html());
        myWriter.close();
        
     }
    public static void first_mark(String htmlfile, String results_dir) throws IOException{
        File input = new File(htmlfile);
        Document document = Jsoup.parse(input,"UTF-8");
        Element first = document.getElementsByTag("p").get(0);
        first.before("<!--start of mark here-->");
        FileWriter myWriter = new FileWriter(results_dir+"/"+"mammoth"+".html", false);
        myWriter.write(document.html());
        myWriter.close();
    }
    public static void break_here(String htmlfile, String results_dir) throws IOException{
        File input = new File(htmlfile);
        Document document = Jsoup.parse(input,"UTF-8");
        Elements body = document.getAllElements();
        for(Element ref : body){
            if(ref.ownText().contains("@break-here@Section-title")){
                ref.before("<!--start of mark here-->");
            }else if (ref.ownText().contains("@no-break-here"))
                ref.before("<!--no break here-->");
        }
        FileWriter myWriter = new FileWriter(results_dir+"/"+"mammoth"+".html", false);
        myWriter.write(document.html());
        myWriter.close();

    }
    
}
