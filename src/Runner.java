import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Random;
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

			//System.out.println(scoredImages);
			
			Image president = scoredImages.firstEntry().getValue();
			Image vicePres = scoredImages.tailMap(scoredImages.firstKey(), false).firstEntry().getValue();
			
			Random r = new Random();
			int index1 = r.nextInt(numGenes - 4) + 2;
			int index2 = r.nextInt(numGenes - 4) + 2;
			while(index2 == index1)
				index2 = r.nextInt(numGenes - 4) + 2;
			
			Image rand1 = (Image) ((Entry)(scoredImages.entrySet().toArray()[index1])).getValue();
			Image rand2 = (Image) ((Entry)(scoredImages.entrySet().toArray()[index2])).getValue();
			//System.out.println(president);
			//System.out.println(vicePres);
			
			long trash = scoredImages.headMap(scoredImages.lastKey(), false).lastKey();
			long scum = scoredImages.lastKey();
			
			//System.out.println(trash);
			//System.out.println(scum);
			
			scoredImages.remove(trash);
			scoredImages.remove(scum);
			
			//System.out.println(scoredImages);
			
			Image child1 = new Image(width, height, numRectangles, breeder.breed(president.getBinary(), vicePres.getBinary()));
			Image child2 = new Image(width, height, numRectangles, breeder.breed(rand1.getBinary(), rand2.getBinary()));

			//System.out.println(child1);
			//System.out.println(child2);
			
			long child1Score = scorer.score(child1.getBufferedImage());
			long child2Score = scorer.score(child2.getBufferedImage());
			
			while(scoredImages.containsKey(child1Score)){
				child1Score += 1;
			}
			scoredImages.put(child1Score, child1);
			
			while(scoredImages.containsKey(child2Score)){
				child2Score += 1;
			}
			scoredImages.put(child2Score, child2);
			
			//System.out.println(scoredImages);
			
			//System.out.println();
			//System.out.println();
			
			Thread.sleep(200);
			
		}

	}

}