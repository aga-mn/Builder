/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projekt2;

import java.util.ArrayList;

/**
 *
 * @author niewega_local
 */
public class Document {
    
    private DocItem title;
    private DocItem author;
    private ArrayList<DocItem>contents;
                   
    public void setTitle(String openMark, String title, String closeMark)
    {this.title=new DocItem();
     this.title.setOpenMark(openMark);
     this.title.setContent(title);
     this.title.setCloseMark(closeMark);}
    
    public void setAuthor(String openMark, String author, String closeMark)
    {this.author=new DocItem();
     this.author.setOpenMark(openMark);
     this.author.setContent(author);
     this.author.setCloseMark(closeMark);}
    
    public void setListItem(String openMark, String item, String closeMark)
    {  DocItem newItem=new DocItem();
        newItem.setOpenMark(openMark);
        newItem.setContent(item);
        newItem.setCloseMark(closeMark);
        this.contents.add(newItem);}
   
    public DocItem getTitle(){return this.title;}
    public DocItem getAuthor(){return this.author;}
    public DocItem getListItem(int n) {return this.contents.get(n);}
    public int getListSize(){return this.contents.size();}
    
    public void initialize() { this.contents=new ArrayList<>(); }
    
 }
    
