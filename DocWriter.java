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
public class DocWriter {
    
    private final DocBuilder db;
 
    DocWriter(DocBuilder docBuilder) {db=docBuilder;}
    
    public void constructDocument()
    {
    
    this.db.addTitle("Big Bang");
    this.db.addAuthor("Agata");
   //this.db.addAuthor("Drugi autor");
   // this.db.addTitle("Drugi tytuł?");
    this.db.addChapter("How do we know the Big Bang actually happened?", 1);
    this.db.addChapter("Where did we come from?", 2);
    this.db.addParagraph("Most scientists think that everything that we know and experience around us began at a moment known as the Big Bang, 14 billion years ago. But how can we have any clue about something that supposedly happened so long ago?");
    this.db.addParagraph("From speeding galaxies to ancient gas clouds, there is evidence that we can detect today – the remnants of the Big Bang, that tell a clear story about the origins of our Universe.");
    this.db.addChapter("We can see the universe expanding", 2);
    this.db.addChapter("Galaxies are moving away from us", 3);
    this.db.addChapter("So what caused the Big Bang?", 3);
    this.db.openBulletList();
    this.db.addBulletListItem("Explosion from a black hole");
    this.db.addBulletListItem("Cycles of death and rebirth");
    this.db.addBulletListItem("Bubbles in a parent multiverse");
    this.db.addBulletListItem("There is no cause");
    this.db.closeBulletList();
    this.db.addParagraph("Some at speeds of hundreds of thousands of kilometres every second. If most galaxies are moving away from us, it means that the Universe is expanding. If the Universe is expanding, then in the past it must have been much smaller. Go back far enough, and there was a moment when all the matter in the Universe was packed into a point and expanded outwards. That moment was the Big Bang.");
    this.db.addChapter("Why galaxies look redder than they should", 4);
    this.db.addParagraph("The tell-tale signature of a galaxy speeding away from us can be detected in the same way that we can tell when a police car passes us with its siren on. When that happens, the siren sounds lower pitched to us, because the waves are stretched. Light is also made up of waves, so the same is true of very fast-moving objects like galaxies. If a galaxy is moving away from us, the light waves are stretched. That makes the light seem redder. The faster the galaxy is moving, the redder the light.");
    this.db.addChapter("Capturing the afterglow of the Big Bang", 1);
    this.db.addChapter("X-rays", 2);
    this.db.addChapter("Cosmic Microwave Background", 2);
    this.db.addParagraph("After the Big Bang, the whole Universe was flooded with incredibly bright light. As the Universe has expanded, that light has stretched into microwaves. A microwave telescope can see this ancient light from the very beginning of the Universe. In fact, a view through a microwave telescope shows the whole sky filled with a glow, day and night. This glow is called the Cosmic Microwave Background.");
    this.db.addChapter("How starlight differs from microwaves", 3);
    this.db.addChapter("We can peer back in time",2);
    this.db.addParagraph("As Big Bang theory predicts, these ancient gas clouds are made of very different stuff to the modern Universe. Most of the chemical elements in the modern Universe are made inside stars. Because the gas clouds come from a time before stars, they consist almost entirely of the most basic elements, hydrogen and helium.");
    }
    
}
