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
public class LaTexBuilder implements DocBuilder {
      private final Document doc;
    
    public LaTexBuilder() { doc=new Document(); doc.initialize(); }

    private boolean isAuthor=false;
    private boolean isTitle=false;
    private boolean isOpen=false;
    private boolean isBulletListOpen=false;
    //private Integer currentChapterNumber=0;
    private int currentChapterLevel=1;
    
    @Override
    public void addAuthor(String author) {
        if(!isTitle){throw new IllegalStateException("Add Title first!");}
        if (isAuthor){throw new IllegalStateException("Author already set");}
        else {this.doc.setAuthor("\\author{", author, "}");
        this.isAuthor=true; } }
      
    @Override
    public void addTitle(String title) {
         if (isTitle){throw new IllegalStateException("Title already set");}
         else {this.doc.setTitle("\\title{", title, "}");
        this.isTitle=true;    } }
    
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
        
        //formatowanie rozdziałów
        if(level==1){this.doc.setListItem("\\section{", chapter, "}");  this.currentChapterLevel=level;  }
        if(level==2){this.doc.setListItem("\\subsection{", chapter, "}");  this.currentChapterLevel=level;  }
        if(level==3){this.doc.setListItem("\\subsubsection{", chapter, "}");  this.currentChapterLevel=level;  }
        if(level==4){this.doc.setListItem("\\paragraph{", chapter, "}");  this.currentChapterLevel=level;  }
    }
    
    @Override
    public void addParagraph(String paragraph) {
        if (!isTitle || !isAuthor) {throw new IllegalStateException("Add Title and Author first!");}
        if(!isOpen){throw new IllegalStateException("Start a Chapter first!");}    
        //else {String indentation = ""; for (int i=0; i<currentChapterLevel; i++){ indentation += "  ";}
        this.doc.setListItem("", paragraph, "");     }
    
    @Override
    public void addBulletListItem(String item) {
          if (!isTitle || !isAuthor) {throw new IllegalStateException("Add Title and Author first!");}
          if(!isOpen){throw new IllegalStateException("Start a Chapter first!");}
          if (!isBulletListOpen){throw new IllegalStateException("Bullet List not open!");}
          this.doc.setListItem("\\item{",item,"}");
    }

    @Override
    public void printDocument() {
       
        if(isBulletListOpen){closeBulletList();} //jeśli BL jest na końcu i nie została zamknięta
        System.out.println("\\documentclass[12pt]{article}");
        System.out.println("\\usepackage{polski}");
        System.out.println("\\usepackage[utf8x]{inputenc}");
        System.out.println("\\usepackage{listings}");
        System.out.println("\\usepackage{enumerate}");
        System.out.println(this.doc.getAuthor().getOpenMark()+this.doc.getAuthor().getContent()+this.doc.getAuthor().getCloseMark());
        System.out.println(this.doc.getTitle().getOpenMark()+this.doc.getTitle().getContent()+this.doc.getTitle().getCloseMark());
        
        System.out.println("\\begin{document}");
            for(int i = 0; i < this.doc.getListSize(); i++) {   
                System.out.print(this.doc.getListItem(i).getOpenMark()+this.doc.getListItem(i).getContent()+this.doc.getListItem(i).getCloseMark()+ "\n");    }
        System.out.println("\\end{document}");
        
        }  

    @Override
    public void saveToFile() {
        
         if(isBulletListOpen){closeBulletList();} //jeśli BL jest na końcu i nie została zamknięta
        
try{
             
     File file = new File("c:\\"+"LT"+this.doc.getTitle().getContent()+".txt");
     if (!file.exists()) {file.createNewFile();}
 
     FileWriter fw = new FileWriter(file.getAbsoluteFile());
     BufferedWriter bw = new BufferedWriter(fw);
     
        bw.write("\\documentclass[12pt]{article}"+"\r\n");
        bw.write("\\usepackage{polski}"+"\r\n");
        bw.write("\\usepackage[utf8x]{inputenc}"+"\r\n");
        bw.write("\\usepackage{listings}"+"\r\n");
        bw.write("\\usepackage{enumerate}"+"\r\n");
        bw.write(this.doc.getAuthor().getOpenMark()+this.doc.getAuthor().getContent()+this.doc.getAuthor().getCloseMark());
        bw.write(this.doc.getTitle().getOpenMark()+this.doc.getTitle().getContent()+this.doc.getTitle().getCloseMark());
        
        bw.write("\\begin{document}");
            for(int i = 0; i < this.doc.getListSize(); i++) {   
                bw.write(this.doc.getListItem(i).getOpenMark()+this.doc.getListItem(i).getContent()+this.doc.getListItem(i).getCloseMark()+"\r\n");    }
        bw.write("\\end{document}"); 
             
      bw.close();
    }
        catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public void openBulletList() {
        if(isBulletListOpen) {throw new IllegalStateException("Bullet List already started!");}
        this.doc.setListItem("\\begin{enumerate}", "", "");
        isBulletListOpen=true;
    }

    @Override
    public void closeBulletList() {
        if(!isBulletListOpen){throw new IllegalStateException("No active Bullet List!");}
        this.doc.setListItem("\\end{enumerate}", "", "");
        isBulletListOpen=false;
    }
    
}
