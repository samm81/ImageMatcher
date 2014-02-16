import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Runner {

	static int numRectangles = 32;
	
	public static void main(String args[]) throws InterruptedException, IOException{
		BufferedImage pic = ImageIO.read(new File("pic.jpg"));
		int width = pic.getWidth();
		int height = pic.getHeight();

		Image image = new Image(width, height, numRectangles);
		BufferedImage img = image.getImage();
		
		Display f = new Display(width, height);
		f.updateImage(img);
		Display f2 = new Display(width, height);
		f2.updateImage(pic);
		
		Scorer scorer = new Scorer(img);
		
		System.out.println(scorer.score(pic));
		System.out.println(scorer.score(img));

		/*
		FaceMakerFrame fmf = new FaceMakerFrame(width, height);
		FaceMakerFrame fmf2 = new FaceMakerFrame(width, height);
		FaceMaker gene1 = new FaceMaker(fmf, POLYGONS, SIDES);
		FaceMaker gene2 = new FaceMaker(gene1);

		int counter = 0;
		int lc = 0;
		while(true){
			counter++;
			
			BufferedImage gene1Img = gene1.makeFace();
			gene2.mutate();
			BufferedImage gene2Img = gene2.makeFace();
			
			long score1 = breeder.getScore(gene1Img);
			long score2 = breeder.getScore(gene2Img);
			
			//gene2.printSelf();
			System.out.println(counter+"\t"+gene2.leeway+"\t"+score1+"\t("+score2+")");
			
			if(score2 < score1){
				gene1 = new FaceMaker(gene2);
				gene2.resetLeeway();
				lc = 0;
			}else{
				gene2 = new FaceMaker(gene1);
				lc++;
			}
			
			if(lc == 10 * gene2.leeway){
				gene2.increaseLeeway();
				lc = 0;
			}
			
			
			fmf.updateFrame(gene1Img);
			fmf.repaint();
			fmf2.updateFrame(gene2Img);
			fmf2.repaint();
		}
		*/
	}
	
}