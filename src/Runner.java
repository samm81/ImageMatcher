import java.io.IOException;
import java.math.BigInteger;

public class Runner {

	static int numRectangles = 32;
	
	public static void main(String args[]) throws InterruptedException, IOException{
		/*
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
		*/
		
		System.out.println(Long.MAX_VALUE + "\n");
		
		int x = 312;
		int y = 456;
		int w = 346;
		int h = 134;
		int rgb = (20 << 24) | (123 << 16) | (45 << 8) | (202);
		
		System.out.println(rgb + "\n");
		
		BigInteger binary = BigInteger.ZERO;
		binary = binary.shiftLeft(32);
		binary = binary.or(BigInteger.valueOf(x));
		System.out.println(binary);
		binary = binary.shiftLeft(32);
		binary = binary.or(BigInteger.valueOf(y));
		System.out.println(binary);
		binary = binary.shiftLeft(32);
		binary = binary.or(BigInteger.valueOf(w));
		System.out.println(binary);
		binary = binary.shiftLeft(32);
		binary = binary.or(BigInteger.valueOf(h));
		System.out.println(binary);
		binary = binary.shiftLeft(32);
		binary = binary.or(BigInteger.valueOf(rgb));
		System.out.println(binary + "\n");
		
		System.out.println(binary.and(BigInteger.valueOf(0x7FFFFFFF)));
		binary = binary.shiftRight(32);
		System.out.println(binary.and(BigInteger.valueOf(0xFFFF)));
		binary = binary.shiftRight(32);
		System.out.println(binary.and(BigInteger.valueOf(0xFFFF)));
		binary = binary.shiftRight(32);
		System.out.println(binary.and(BigInteger.valueOf(0xFFFF)));
		binary = binary.shiftRight(32);
		System.out.println(binary.and(BigInteger.valueOf(0xFFFF)));
		binary = binary.shiftRight(32);
		
	}
	
}