import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.Random;

public class Image {

	int width;
	int height;

	Rectangle[] rectangles;

	BufferedImage image;
	
	public static void main(String[] args) {
		Image image = new Image(500, 500, 20);
		Rectangle rect = image.new Rectangle(50, 50, 200, 300, new Color(200, 100, 50, 150));
		Rectangle newRect = image.new Rectangle(rect.getBinary());
		System.out.println(newRect.getColor().getRed() + "\t" + newRect.getColor().getGreen() + "\t" + newRect.getColor().getBlue() + "\t" + newRect.getColor().getAlpha());
		
	}

	public Image(int width, int height, int numRectangles) {
		this.width = width;
		this.height = height;
		rectangles = new Rectangle[numRectangles];
		for (int i = 0; i < numRectangles; i++) {
			rectangles[i] = generateRandomRectangle();
		}
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		generateImage();
	}

	public Image(int width, int height, int numRectangles, BigInteger binary) {
		this.width = width;
		this.height = height;
		rectangles = new Rectangle[numRectangles];
		for (int i = numRectangles - 1; i >= 0; i--) {
			BigInteger mask = BigInteger.valueOf(2).pow(Rectangle.binaryLength).subtract(BigInteger.valueOf(1));
			BigInteger rectangleBinary = binary.and(mask);
			binary = binary.shiftRight(Rectangle.binaryLength);
			rectangles[i] = new Rectangle(rectangleBinary);
		}
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		generateImage();
	}

	private void generateImage() {
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.width, this.height);
		for (Rectangle rectangle : rectangles) {
			g.setColor(rectangle.getColor());
			g.fillRect(rectangle.getX(), rectangle.getY(),
					rectangle.getWidth(), rectangle.getHeight());
		}
	}

	private Rectangle generateRandomRectangle() {
		Random r = new Random();
		int x = r.nextInt(width);
		int y = r.nextInt(height);
		int width = r.nextInt(this.width - x);
		int height = r.nextInt(this.height - y);
		Color color = generateRandomColor(r);
		return new Rectangle(x, y, width, height, color);
	}

	private Color generateRandomColor(Random r) {
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(),
				r.nextFloat());
	}

	public Rectangle[] getRectangles() {
		return rectangles;
	}

	public BufferedImage getImage() {
		return image;
	}

	public BigInteger getBinary() {
		BigInteger binary = BigInteger.ZERO;
		for (Rectangle rectangle : rectangles) {
			binary = binary.shiftLeft(Rectangle.binaryLength);
			binary = binary.or(rectangle.getBinary());
		}
		return binary;
	}

	public class Rectangle {
		private int x, y, width, height;
		private Color color;
		
		public static final int binaryLength = 32*5;

		public Rectangle(int x, int y, int width, int height, Color color) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.color = color;
		}

		public Rectangle(BigInteger binary) {
			int b = binary.and(BigInteger.valueOf(0xFF)).intValue();
			binary = binary.shiftRight(8);
			int g = binary.and(BigInteger.valueOf(0xFF)).intValue();
			binary = binary.shiftRight(8);
			int r = binary.and(BigInteger.valueOf(0xFF)).intValue();
			binary = binary.shiftRight(8);
			int a = binary.and(BigInteger.valueOf(0xFF)).intValue();
			binary = binary.shiftRight(8);

			int height = binary.and(BigInteger.valueOf(0xFFFF)).intValue();
			binary = binary.shiftRight(32);
			int width = binary.and(BigInteger.valueOf(0xFFFF)).intValue();
			binary = binary.shiftRight(32);
			int y = binary.and(BigInteger.valueOf(0xFFFF)).intValue();
			binary = binary.shiftRight(32);
			int x = binary.and(BigInteger.valueOf(0xFFFF)).intValue();

			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.color = new Color(r, g, b, a);
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public Color getColor() {
			return color;
		}

		public BigInteger getBinary() {
			BigInteger binary = BigInteger.ZERO;

			binary = binary.shiftLeft(32);
			binary = binary.or(BigInteger.valueOf(x));

			binary = binary.shiftLeft(32);
			binary = binary.or(BigInteger.valueOf(y));

			binary = binary.shiftLeft(32);
			binary = binary.or(BigInteger.valueOf(width));

			binary = binary.shiftLeft(32);
			binary = binary.or(BigInteger.valueOf(height));

			int a = color.getAlpha();
			int r = color.getRed();
			int g = color.getGreen();
			int b = color.getBlue();
			binary = binary.shiftLeft(8);
			binary = binary.or(BigInteger.valueOf(a));
			binary = binary.shiftLeft(8);
			binary = binary.or(BigInteger.valueOf(r));
			binary = binary.shiftLeft(8);
			binary = binary.or(BigInteger.valueOf(g));
			binary = binary.shiftLeft(8);
			binary = binary.or(BigInteger.valueOf(b));

			return binary;
		}
	}
}
