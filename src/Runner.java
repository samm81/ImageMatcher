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
		f.updateImage(pic);
		Display f2 = new Display(width, height);
		f2.updateImage(img);
		
		Image image2 = new Image(width, height, numRectangles, image.getBinary());
		BufferedImage img2 = image2.getImage();
		
		Display f3 = new Display(width, height);
		f3.updateImage(img2);
		
		Scorer scorer = new Scorer(pic);
		
		System.out.println(scorer.score(img));
		System.out.println(scorer.score(img2));
		
		System.out.println("\n" + image.getBinary());
		
	}
	
}