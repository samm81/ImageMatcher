import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class Runner {

	static int numRectangles = 32;
	static int numGenes = 10;

	public static void main(String args[]) throws InterruptedException, IOException{
		BufferedImage pic = ImageIO.read(new File("pic2.jpg"));
		int width = pic.getWidth();
		int height = pic.getHeight();
		Display f = new Display(width, height);
		f.updateImage(pic);

		Scorer scorer = new Scorer(pic);

		Display[] displays = new Display[numRectangles];
		TreeMap<Long, Image> scoredImages = new TreeMap<Long, Image>();
		for(int i=0;i<numGenes;i++){
			displays[i] = new Display(width, height);

			Image image = new Image(width, height, numRectangles);
			BufferedImage img = image.getBufferedImage();
			long score = scorer.score(img);

			scoredImages.put(score, image);
		}

		Breeder breeder = new Breeder();
		
		while(true){
			System.out.println("looping");
			int i = 0;
			for(Image image : scoredImages.values()){
				displays[i].updateImage(image.getBufferedImage());
				i++;
			}

			Image president = scoredImages.firstEntry().getValue();
			Image vicePres = scoredImages.tailMap(scoredImages.firstKey(), false).firstEntry().getValue();
			
			long trash = scoredImages.headMap(scoredImages.lastKey(), false).lastKey();
			long scum = scoredImages.lastKey();
			
			scoredImages.remove(trash);
			scoredImages.remove(scum);
			
			Image child1 = new Image(width, height, numRectangles, breeder.breed(president.getBinary(), vicePres.getBinary()));
			Image child2 = new Image(width, height, numRectangles, breeder.breed(vicePres.getBinary(), president.getBinary()));
			
			scoredImages.put(scorer.score(child1.getBufferedImage()), child1);
			scoredImages.put(scorer.score(child2.getBufferedImage()), child2);
			
		}

	}

}