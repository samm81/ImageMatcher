import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Image {

	final int size = 256;

	Rectangle[] rectangles;

	BufferedImage bufferedImage;

	Color backgroundColor = Color.WHITE;

	public Image(int numRectangles) {
		this(generateRandomBinary(numRectangles * Rectangle.bitCount));
		/*
		rectangles = new Rectangle[numRectangles];
		for (int i = 0; i < numRectangles; i++) {
			rectangles[i] = generateRandomRectangle();
		}
		bufferedImage = new BufferedImage(size, size,
				BufferedImage.TYPE_INT_ARGB);
		generateImage();
		*/
	}
	
	private static BigInteger generateRandomBinary(int numBits) {
		Random r = new Random();
		BigInteger binary = BigInteger.ZERO;
		for(int i=0;i<numBits;i++){
			BigInteger bit = BigInteger.valueOf(r.nextInt(2));
			binary = binary.shiftLeft(1);
			binary = binary.or(bit);
		}
		return binary;
	}

	public Image(BigInteger binary) {
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		BigInteger mask = BigInteger.ONE.shiftLeft(Rectangle.bitCount)
				.subtract(BigInteger.valueOf(1));

		while (!binary.equals(BigInteger.ZERO)) {
			BigInteger rectangleBinary = binary.and(mask);
			rectangles.add(new Rectangle(rectangleBinary));

			binary = binary.shiftRight(Rectangle.bitCount);
		}

		this.rectangles = new Rectangle[rectangles.size()];
		for (int i = 0; i < rectangles.size(); i++) {
			this.rectangles[i] = rectangles.get(rectangles.size() - i - 1);
		}
		bufferedImage = new BufferedImage(size, size,
				BufferedImage.TYPE_INT_ARGB);
		generateImage();
	}

	private void generateImage() {
		Graphics g = bufferedImage.getGraphics();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.size, this.size);
		for (Rectangle rectangle : rectangles) {
			rectangle.drawSelf(g);
		}
	}

	private Rectangle generateRandomRectangle() {
		Random r = new Random();
		int x = r.nextInt(this.size);
		int y = r.nextInt(this.size);
		int xbar = r.nextInt(this.size);
		int ybar = r.nextInt(this.size);
		Color color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(),
				r.nextFloat());
		return new Rectangle(x, y, xbar, ybar, color);
	}

	public Rectangle[] getRectangles() {
		return rectangles;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public BigInteger getBinary() {
		BigInteger binary = BigInteger.ZERO;
		for (Rectangle rectangle : rectangles) {
			binary = binary.shiftLeft(Rectangle.bitCount);
			binary = binary.or(rectangle.getBinary());
		}
		return binary;
	}

	public void printSelf() {
		System.out.println("size: " + size);
		for (int i = 0; i < rectangles.length; i++) {
			System.out.println("rectangle " + i);
			rectangles[i].printSelf();
		}
	}

	// for testing
	public static void main(String[] args) {
		Display d1 = new Display(256, 256);
		Display d2 = new Display(256, 256);

		Image image1 = new Image(20);
		d1.updateImage(image1.getBufferedImage());

		Image image2 = new Image(image1.getBinary());
		d2.updateImage(image2.getBufferedImage());

		image1.printSelf();
		System.out.println();
		image2.printSelf();
	}

}