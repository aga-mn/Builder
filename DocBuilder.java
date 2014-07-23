/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projekt2;

/**
 *
 * @author niewega_local
 */
public interface DocBuilder {
    
    /**
     * 
     * @param author 
     * Autor dodawany na 
     */
    public void addAuthor(String author);
    public void addTitle(String title);
    public void addChapter(String chapter, int level);
    public void addParagraph(String paragraph);
    public void openBulletList();
    public void addBulletListItem(String item);
    public void closeBulletList();
    public void printDocument();
    public void saveToFile();
    
}
