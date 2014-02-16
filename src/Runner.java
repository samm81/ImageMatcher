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
		System.out.println();
		
		System.out.println(image.getBinary());
		
	}
	
}