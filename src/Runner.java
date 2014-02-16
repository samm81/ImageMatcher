import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import javax.imageio.ImageIO;

public class Runner {

	static int numRectangles = 32;
	
	public static void main(String args[]) throws InterruptedException, IOException{
		BufferedImage pic = ImageIO.read(new File("pic2.jpg"));
		int width = pic.getWidth();
		int height = pic.getHeight();

		Image parent1 = new Image(width, height, numRectangles);
		BufferedImage img = parent1.getImage();
		Image parent2 = new Image(width, height, numRectangles);
		BufferedImage img2 = parent2.getImage();
		
		Display f = new Display(width, height);
		f.updateImage(pic);
		Display f2 = new Display(width, height);
		f2.updateImage(img);
		Display f3 = new Display(width, height);
		f3.updateImage(img2);
		
		Breeder breeder = new Breeder();
		Image child = new Image(width, height, numRectangles, breeder.breed(parent1.getBinary(), parent2.getBinary()));
		BufferedImage img3 = child.getImage();
		
		Display f4 = new Display(width, height);
		f4.updateImage(img3);
		
		Scorer scorer = new Scorer(pic);
		
		System.out.println(scorer.score(img));
		System.out.println(scorer.score(img2));
		System.out.println(scorer.score(img3));
		
		System.out.println("\n" + parent1.getBinary());
		
	}
	
}