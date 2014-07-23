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
public class Projekt2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
     DocBuilder hb = new HtmlBuilder();
     DocBuilder tb = new TxtBuilder();
     DocBuilder lb =  new LaTexBuilder();
     DocWriter writerH = new DocWriter(hb);
     DocWriter writerT = new DocWriter(tb);
     DocWriter writerL = new DocWriter(lb);
     writerH.constructDocument();
     writerT.constructDocument();
     writerL.constructDocument();
     hb.printDocument();
     tb.printDocument();
     lb.printDocument();
     tb.saveToFile();
     hb.saveToFile();
     lb.saveToFile();
    }
    
}
