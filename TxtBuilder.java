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
public class TxtBuilder implements DocBuilder{
    
    private final Document doc;
    private final int[] chapters;
    
    public TxtBuilder() { doc=new Document(); doc.initialize(); chapters=new int[4];}

    private boolean isAuthor=false;
    private boolean isTitle=false;
    private boolean isOpen=false;
    private int currentChapterLevel=0;
            
    @Override
    public void addAuthor(String author) {
        if(!isTitle){throw new IllegalStateException("Add Title first!");}
        if (isAuthor){throw new IllegalStateException("Author already set");}
        else {this.doc.setAuthor("Autor: ", author, "");
        this.isAuthor=true; } }
      
    @Override
    public void addTitle(String title) {
         if (isTitle){throw new IllegalStateException("Title already set");}
         else {this.doc.setTitle("Tytuł: ", title, "");
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
               
        //numerowanie rozdziałów i podrozdziałów
         String chapNum="";
         if(level==1){this.chapters[0]++; chapNum=Integer.toString(this.chapters[0]); for(int i=1;i<4;i++) this.chapters[i]=0;} 
         if(level==2){this.chapters[1]++; chapNum=Integer.toString(this.chapters[0])+"."+Integer.toString(this.chapters[1]);}
         if(level==3){this.chapters[2]++; chapNum=Integer.toString(this.chapters[0])+"."+Integer.toString(this.chapters[1])+"."+Integer.toString(this.chapters[2]);}
         if(level==4){this.chapters[3]++; chapNum=Integer.toString(this.chapters[0])+"."+Integer.toString(this.chapters[1])+"."+Integer.toString(this.chapters[2])+"."+Integer.toString(this.chapters[3]);}
        
        //formatowanie podrozdziałów
         String chapNumFormat;
        if (level==1) { chapNumFormat= "\r\nRozdział "+chapNum+" ";  this.doc.setListItem(chapNumFormat, chapter, "");
        this.currentChapterLevel=level; }
        
        else {String indentation = ""; for (int i=0; i<level; i++){ indentation += "  ";}
        chapNumFormat=indentation+chapNum+" ";
        this.doc.setListItem(chapNumFormat, chapter, "");
        this.currentChapterLevel=level;
        }
    }
    @Override
    public void addParagraph(String paragraph) {
        if (!isTitle || !isAuthor) {throw new IllegalStateException("Add Title and Author first!");}
        if(!isOpen){throw new IllegalStateException("Start a Chapter first!");}    
        else {String indentation = ""; for (int i=0; i<currentChapterLevel; i++){ indentation += "  ";}
        this.doc.setListItem(indentation, paragraph, "");     }
    }

    @Override
    public void addBulletListItem(String item) {
        if (!isTitle || !isAuthor) {throw new IllegalStateException("Add Title and Author first!");}
        if(!isOpen){throw new IllegalStateException("Start a Chapter first!");}
        else {String result = ""; for (int i=0; i<currentChapterLevel; i++){ result += "  ";}
        result+="*";
        this.doc.setListItem(result,item,"");    }
    }

    @Override
    public void printDocument() {
       
        System.out.println(this.doc.getAuthor().getOpenMark()+this.doc.getAuthor().getContent());
        System.out.println(this.doc.getTitle().getOpenMark()+this.doc.getTitle().getContent());
                
          for(int i = 0; i < this.doc.getListSize(); i++) {   
          System.out.print(this.doc.getListItem(i).getOpenMark()+this.doc.getListItem(i).getContent()+ "\n");    }  
        }  

    @Override
    public void saveToFile() {
        
try{
             
     File file = new File("c:\\"+this.doc.getTitle().getContent()+".txt");
     if (!file.exists()) {file.createNewFile();}
 
     FileWriter fw = new FileWriter(file.getAbsoluteFile());
     BufferedWriter bw = new BufferedWriter(fw);
     
     bw.write(this.doc.getAuthor().getOpenMark()+this.doc.getAuthor().getContent()+"\r\n");
     bw.write(this.doc.getTitle().getOpenMark()+this.doc.getTitle().getContent()+"\r\n");
                
      for(int i = 0; i < this.doc.getListSize(); i++) {   
      bw.write(this.doc.getListItem(i).getOpenMark()+this.doc.getListItem(i).getContent()+ "\r\n");    } 
             
      bw.close();
    }
        catch (IOException e) {e.printStackTrace();}
}

    @Override
    public void openBulletList() {}

    @Override
    public void closeBulletList() {}
        
    }
    

