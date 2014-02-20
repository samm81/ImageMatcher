import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.Random;

public class Image {

	int width;
	int height;

	Rectangle[] rectangles;

	BufferedImage bufferedImage;
	
	Color backgroundColor = Color.WHITE;

	public Image(int width, int height, int numRectangles) {
		this.width = width;
		this.height = height;
		rectangles = new Rectangle[numRectangles];
		for (int i = 0; i < numRectangles; i++) {
			rectangles[i] = generateRandomRectangle();
		}
		bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		generateImage();
	}

	public Image(int width, int height, int numRectangles, BigInteger binary) {
		this.width = width;
		this.height = height;
		rectangles = new Rectangle[numRectangles];
		for (int i = numRectangles - 1; i >= 0; i--) {
			BigInteger mask = BigInteger.valueOf(2).pow(Rectangle.binaryLength)
					.subtract(BigInteger.valueOf(1));
			BigInteger rectangleBinary = binary.and(mask);
			binary = binary.shiftRight(Rectangle.binaryLength);
			rectangles[i] = new Rectangle(rectangleBinary);
		}
		bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		generateImage();
	}

	private void generateImage() {
		Graphics g = bufferedImage.getGraphics();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.width, this.height);
		for (Rectangle rectangle : rectangles) {
			rectangle.drawSelf(g);
		}
	}

	private Rectangle generateRandomRectangle() {
		Random r = new Random();
		int x = r.nextInt(this.width);
		int y = r.nextInt(this.height);
		int xbar = r.nextInt(this.width);
		int ybar = r.nextInt(this.height);
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
			binary = binary.shiftLeft(Rectangle.binaryLength);
			binary = binary.or(rectangle.getBinary());
		}
		return binary;
	}

	public void printSelf() {
		System.out.println("width: " + width + ", height: " + height);
		for (int i = 0; i < rectangles.length; i++) {
			System.out.println("rectangle " + i);
			rectangles[i].printSelf();
		}
	}

	// for testing
	public static void main(String[] args) {
		Display d1 = new Display(200, 200);
		Display d2 = new Display(200, 200);

		Image image1 = new Image(200, 200, 20);
		d1.updateImage(image1.getBufferedImage());

		Image image2 = new Image(200, 200, 20, image1.getBinary());
		d2.updateImage(image2.getBufferedImage());

		image1.printSelf();
		System.out.println();
		image2.printSelf();
	}

}