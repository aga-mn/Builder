/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projekt2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author niewega_local
 */
public class HtmlBuilder implements DocBuilder {

    private final Document doc;
    private final int[] chapters;
    
    public HtmlBuilder() { doc=new Document(); doc.initialize(); chapters=new int[4];}

    private boolean isAuthor=false;
    private boolean isTitle=false;
    private boolean isOpen=false;
    private boolean isBulletListOpen=false;
    //private int currentChapterNumber=0;
    private int currentChapterLevel=1;
    
    @Override
    public void addAuthor(String author) {
        if(!isTitle){throw new IllegalStateException("Add Title first!");}
        if (isAuthor){throw new IllegalStateException("Author already set");}
        else {this.doc.setAuthor("", author, "");
        this.isAuthor=true; } }

    @Override
    public void addTitle(String title) {
        if (isTitle){throw new IllegalStateException("Title already set");}
        else{ this.doc.setTitle("<title>", title, "</title>"); this.isTitle=true;   } }

    @Override
    public void addChapter(String chapter, int level) {
        //sprawdzam tytuł i autora
        if (!isTitle || !isAuthor) {throw new IllegalStateException("Add Title and Author first!");}        
        //zaczyna od pierwszego i otwiera dokument
        if (!isOpen && level !=1){throw new IllegalStateException("Start a Level 1 Chapter first!");}
        if (level==1 & !isOpen){isOpen=true;}
        //poziomy 1-4
        if (level<1 || level>4){throw new IllegalArgumentException("Incorrect Chapter Level!");}
        //poziomy po kolei - zawsze można do góry, a w dół tylko o jeden
        if (level>currentChapterLevel && level-currentChapterLevel>=2){throw new IllegalArgumentException("Incorrect subchapter order!");}
        //rozdział nie może być wewnątrz bullet listy
        if (isBulletListOpen){throw new IllegalStateException("Chapter cannot be inside Bullet List!");}
        
         //numerowanie rozdziałów i podrozdziałów
         String chapNum="";
         if(level==1){this.chapters[0]++; chapNum=Integer.toString(this.chapters[0]); for(int i=1;i<4;i++) this.chapters[i]=0;} 
         if(level==2){this.chapters[1]++; chapNum=Integer.toString(this.chapters[0])+"."+Integer.toString(this.chapters[1]);}
         if(level==3){this.chapters[2]++; chapNum=Integer.toString(this.chapters[0])+"."+Integer.toString(this.chapters[1])+"."+Integer.toString(this.chapters[2]);}
         if(level==4){this.chapters[3]++; chapNum=Integer.toString(this.chapters[0])+"."+Integer.toString(this.chapters[1])+"."+Integer.toString(this.chapters[2])+"."+Integer.toString(this.chapters[3]);}

        //formatowanie rozdziałów
        String chapNumFormat;
        if(level==1){chapNumFormat ="<h1>"+chapNum+". "; this.doc.setListItem(chapNumFormat, chapter, "</h1>");  this.currentChapterLevel=level;  }
        if(level==2){chapNumFormat ="<h2>"+chapNum+". "; this.doc.setListItem(chapNumFormat, chapter, "</h2>");  this.currentChapterLevel=level;  }
        if(level==3){chapNumFormat ="<h3>"+chapNum+". "; this.doc.setListItem(chapNumFormat, chapter, "</h3>");  this.currentChapterLevel=level;  }
        if(level==4){chapNumFormat ="<h4>"+chapNum+". "; this.doc.setListItem(chapNumFormat, chapter, "</h4>");  this.currentChapterLevel=level;  }
    }

    @Override
    public void addParagraph(String paragraph) {
    if (!isTitle || !isAuthor) {throw new IllegalStateException("Add Title and Author first!");}
    if(!isOpen){throw new IllegalStateException("Start a Chapter first!");}
    if (isBulletListOpen){throw new IllegalStateException("Paragraph cannot be inside Bullet List!");}
    this.doc.setListItem("<p>", paragraph, "</p>");  
    }

    @Override
    public void addBulletListItem(String item) {
    if (!isTitle || !isAuthor) {throw new IllegalStateException("Add Title and Author first!");}
    if(!isOpen){throw new IllegalStateException("Start a Chapter first!");}
    if (!isBulletListOpen){throw new IllegalStateException("Bullet List not open!");}
    this.doc.setListItem("<li>",item,"</li>");
    }

    @Override
    public void printDocument() {
        if(isBulletListOpen){closeBulletList();} //jeśli BL jest na końcu i nie została zamknięta (znacznik zamykający)
       System.out.println("<!DOCTYPE html>");
       System.out.println("<html>");
       System.out.println("<head>");
       System.out.println("<meta name=\"Author\" content="+this.doc.getAuthor().getContent()+  "\" />");
       System.out.println(this.doc.getTitle().getOpenMark()+this.doc.getTitle().getContent()+this.doc.getTitle().getCloseMark());
       System.out.println("</head>");  
       
       System.out.println("<body>");
       
       for(int i = 0; i < this.doc.getListSize(); i++) {   
        System.out.println(this.doc.getListItem(i).getOpenMark() + this.doc.getListItem(i).getContent()+ this.doc.getListItem(i).getCloseMark());        }  
       
       System.out.println("</body>");
       System.out.println("</html>");
     
    }

    @Override
    public void saveToFile() {        
        
   if(isBulletListOpen){closeBulletList();} //jeśli BL jest na końcu i nie została zamknięta (znacznik zamykający)
    
            try{
             
     File file = new File("c:\\"+this.doc.getTitle().getContent()+".html");
     if (!file.exists()) {file.createNewFile();}
 
     FileWriter fw = new FileWriter(file.getAbsoluteFile());
     BufferedWriter bw = new BufferedWriter(fw);
     
     bw.write("<!DOCTYPE html>"+"\r\n");
     bw.write("<html>"+"\r\n");
     bw.write("<head>"+"\r\n");
     bw.write("<meta name=\"Author\" content="+this.doc.getAuthor().getContent()+  "\" charset=iso-8859-2/>"+"\r\n");
     bw.write(this.doc.getTitle().getOpenMark()+this.doc.getTitle().getContent()+this.doc.getTitle().getCloseMark()+"\r\n");
     bw.write("</head>"+"\r\n");  
       
     bw.write("<body>"+"\r\n");
       
     for(int i = 0; i < this.doc.getListSize(); i++) {   
          bw.write(this.doc.getListItem(i).getOpenMark() + this.doc.getListItem(i).getContent()+ this.doc.getListItem(i).getCloseMark()+"\r\n");        }  
       
      bw.write("</body>");
      bw.write("</html>");
             
      bw.close();
    }
        catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public void openBulletList() {
        if(isBulletListOpen) {throw new IllegalStateException("Bullet List already started!");}
        this.doc.setListItem("<ul>", "", "");
        isBulletListOpen=true;
    }

    @Override
    public void closeBulletList() {
        if(!isBulletListOpen){throw new IllegalStateException("No active Bullet List!");}
        this.doc.setListItem("</ul>", "", "");
        isBulletListOpen=false;
    }
    
}
