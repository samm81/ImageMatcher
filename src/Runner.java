import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class Runner {

	static final int size = 256;
	
	static int numRectangles = 32;
	static int numGenes = 5;
	
	static String filename = "apple.png";

	public static void main(String args[]) throws Exception{
		
		BufferedImage pic = ImageIO.read(new File(filename));
		int width = pic.getWidth();
		int height = pic.getHeight();
		
		if(width != 256 || height != 256){
			throw new Exception("Image height and width must be exactly 256 pixels");
		}
		
		Display f = new Display(size,size);
		f.updateImage(pic);

		Scorer scorer = new Scorer(pic);

		Display[] displays = new Display[numRectangles];
		TreeMap<Long, Image> scoredImages = new TreeMap<Long, Image>();
		for(int i=0;i<numGenes;i++){
			displays[i] = new Display(size, size);

			Image image = new Image(numRectangles);
			BufferedImage img = image.getBufferedImage();
			long score = scorer.score(img);

			scoredImages.put(score, image);
		}

		Breeder breeder = new Breeder();
		
		Display scoreFrame = new Display(size, size);
		BufferedImage scoreImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics scoreGraphics = scoreImg.getGraphics();
		
		int iteration = 0;
		while(true){
			drawScore(scoreGraphics, iteration, scoredImages.firstKey());
			scoreFrame.updateImage(scoreImg);
			System.out.println("Iteration: " + iteration + "\tBest Score: " + scoredImages.firstKey());
			
			int i = 0;
			for(Image image : scoredImages.values()){
				displays[i].updateImage(image.getBufferedImage());
				i++;
			}

			//System.out.println(scoredImages);
			
			//Image president = scoredImages.firstEntry().getValue();
			//Image vicePres = scoredImages.tailMap(scoredImages.firstKey(), false).firstEntry().getValue();
			
			Random r = new Random();
			int index1 = r.nextInt(numGenes - 2);
			int index2 = r.nextInt(numGenes - 2);
			while(index2 == index1)
				index2 = r.nextInt(numGenes - 2);
			
			@SuppressWarnings("unchecked")
			Image rand1 = (Image) ((Entry<Long, Image>)(scoredImages.entrySet().toArray()[index1])).getValue();
			@SuppressWarnings("unchecked")
			Image rand2 = (Image) ((Entry<Long, Image>)(scoredImages.entrySet().toArray()[index2])).getValue();
			//System.out.println(president);
			//System.out.println(vicePres);
			
			long trash = scoredImages.headMap(scoredImages.lastKey(), false).lastKey();
			long scum = scoredImages.lastKey();
			
			//System.out.println(trash);
			//System.out.println(scum);
			
			scoredImages.remove(trash);
			scoredImages.remove(scum);
			
			//System.out.println(scoredImages);
			
			Image child1 = new Image(breeder.breed(rand1.getBinary(), rand2.getBinary()));
			Image child2 = new Image(breeder.breed(rand2.getBinary(), rand1.getBinary()));

			//System.out.println(child1);
			//System.out.println(child2);
			
			long child1Score = scorer.score(child1.getBufferedImage());
			long child2Score = scorer.score(child2.getBufferedImage());
			
			while(scoredImages.containsKey(child1Score))
				child1Score++;
			scoredImages.put(child1Score, child1);
			
			while(scoredImages.containsKey(child2Score))
				child2Score++;
			
			
			scoredImages.put(child2Score, child2);
			
			int mutateIndex = r.nextInt(numGenes);
			long mutateeKey = (long) scoredImages.keySet().toArray()[mutateIndex];
			@SuppressWarnings("unchecked")
			Image mutatee = (Image) ((Entry<Long, Image>)(scoredImages.entrySet().toArray()[mutateIndex])).getValue();
			
			scoredImages.remove(mutateeKey);
			
			mutatee = new Image(breeder.mutate(mutatee.getBinary()));
			long mutateeScore = scorer.score(mutatee.getBufferedImage());
			while(scoredImages.containsKey(mutateeScore))
				mutateeScore++;
			
			scoredImages.put(mutateeScore, mutatee);
			
			//System.out.println(scoredImages);
			
			//System.out.println();
			//System.out.println();
			iteration++;
		}

	}

	private static void drawScore(Graphics g, int iteration, long bestScore) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, size, size);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.drawString("Iteration: ", 15, 30);
		
		g.setFont(new Font("Times New Roman", Font.BOLD, 50));
		g.setColor(Color.BLUE);
		g.drawString(""+iteration, 30, 90);
		
		g.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.drawString("Best score: ", 15, 135);
		
		g.setFont(new Font("Times New Roman", Font.BOLD, 50));
		g.setColor(Color.RED);
		g.drawString(""+bestScore, 30, 195);
		
	}

}