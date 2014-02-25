import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class ImageMatcher implements Runnable {

	static final int size = 256;
	
	BufferedImage basePicture;
	
	Scorer scorer;
	Breeder breeder;
	
	Display[] displays;
	
	TreeMap<Long, Image> scoredImages;
	
	private Thread thread;
	
	Display scoreFrame;
	BufferedImage scoreImg;
	Graphics scoreGraphics;
	
	int iteration;
	
	//asdfghjkl
	
	public ImageMatcher(String filename, int numRectangles, int numGenes) throws Exception{
		basePicture = readPicture(filename);
		
		Display basePictureFrame = new Display(size,size);
		basePictureFrame.updateImage(basePicture);

		scorer = new Scorer(basePicture);

		displays = new Display[numRectangles];
		scoredImages = new TreeMap<Long, Image>();
		for(int i=0;i<numGenes;i++){
			displays[i] = new Display(size, size);

			Image image = new Image(numRectangles);
			BufferedImage img = image.getBufferedImage();
			long score = scorer.score(img);
			scoredImages.put(score, image);
		}

		breeder = new Breeder();
		
		scoreFrame = new Display(size, size);
		scoreImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		scoreGraphics = scoreImg.getGraphics();
		
		iteration = 0;
		
		thread = new Thread(this);
	}
	
	private static BufferedImage readPicture(String filename) throws Exception {
		BufferedImage pic = ImageIO.read(new File(filename));
		if(pic.getWidth() != 256 || pic.getHeight() != 256){
			throw new Exception("Image height and width must be exactly 256 pixels");
		}
		return pic;
	}

	public void start() {
		thread.start();
	}
	
	@Override
	public void run() {
		while(Thread.currentThread() == thread){
			updateDisplays();
			killWorst();
			breedRandomTwo();
			mutateOne();
			
			drawScore(scoreGraphics, iteration, scoredImages.firstKey());

			iteration++;
		}
	}
	
	private void updateDisplays() {
		int i = 0;
		for(Image image : scoredImages.values()){
			displays[i].updateImage(image.getBufferedImage());
			i++;
		}
	}
	
	private void killWorst() {
		long trash = scoredImages.headMap(scoredImages.lastKey(), false).lastKey();
		long scum = scoredImages.lastKey();
		
		scoredImages.remove(trash);
		scoredImages.remove(scum);
	}
	
	private void breedRandomTwo() {
		Random r = new Random();
		int index1 = r.nextInt(scoredImages.size());
		int index2 = r.nextInt(scoredImages.size());
		while(index2 == index1)
			index2 = r.nextInt(scoredImages.size());
		
		@SuppressWarnings("unchecked")
		Image rand1 = (Image) ((Entry<Long, Image>)(scoredImages.entrySet().toArray()[index1])).getValue();
		@SuppressWarnings("unchecked")
		Image rand2 = (Image) ((Entry<Long, Image>)(scoredImages.entrySet().toArray()[index2])).getValue();
		
		Image child1 = new Image(breeder.breed(rand1.getBinary(), rand2.getBinary()));
		Image child2 = new Image(breeder.breed(rand2.getBinary(), rand1.getBinary()));

		long child1Score = scorer.score(child1.getBufferedImage());
		long child2Score = scorer.score(child2.getBufferedImage());
		
		while(scoredImages.containsKey(child1Score))
			child1Score++;
		scoredImages.put(child1Score, child1);
		
		while(scoredImages.containsKey(child2Score))
			child2Score++;
		scoredImages.put(child2Score, child2);
	}
	
	private void mutateOne() {
		Random r = new Random();
		int mutateIndex = r.nextInt(scoredImages.size());
		long mutateeKey = (long) scoredImages.keySet().toArray()[mutateIndex];
		@SuppressWarnings("unchecked")
		Image mutatee = (Image) ((Entry<Long, Image>)(scoredImages.entrySet().toArray()[mutateIndex])).getValue();
		
		scoredImages.remove(mutateeKey);
		
		mutatee = new Image(breeder.mutate(mutatee.getBinary()));
		long mutateeScore = scorer.score(mutatee.getBufferedImage());
		while(scoredImages.containsKey(mutateeScore))
			mutateeScore++;
		
		scoredImages.put(mutateeScore, mutatee);
	}

	private void drawScore(Graphics g, int iteration, long bestScore) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, size, size);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.drawString("Iteration: ", 15, 30);
		
		g.setFont(new Font("Times New Roman", Font.BOLD, 50));
		g.setColor(Color.BLUE);
		g.drawString(""+iteration, 30, 90);
		
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		g.drawString("Best score: ", 15, 135);
		
		g.setFont(new Font("Times New Roman", Font.BOLD, 50));
		g.setColor(Color.RED);
		g.drawString(""+bestScore, 30, 195);
		
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		g.drawString("click on an image to save it", 10, 225);
		g.drawString("as image.png (will overwrite)", 10, 245);
		
		scoreFrame.updateImage(scoreImg);
	}

}