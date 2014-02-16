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
		for(Rectangle rectangle : rectangles){
			binary.shiftLeft(32*5);
			System.out.println(rectangle.getBinary());
			binary.or(rectangle.getBinary());
		}
		return binary;
	}

	private class Rectangle {
		private int x, y, width, height;
		private Color color;

		public Rectangle(int x, int y, int width, int height, Color color) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.color = color;
		}

		public Rectangle(BigInteger binary) {
			// hex value discovered by trial and error, I don't know why it
			// works
			int argb = binary.and(BigInteger.valueOf(0x7FFFFFFF)).intValue();
			binary = binary.shiftRight(32);
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
			this.color = argbToColor(argb);
		}

		private Color argbToColor(int argb) {
			int a = (argb >> 24) & 0xFF;
			int r = (argb >> 16) & 0xFF;
			int g = (argb >> 8) & 0xFF;
			int b = argb & 0xFF;
			return new Color(r, g, b, a);
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

			binary = binary.shiftLeft(32);
			binary = binary.or(BigInteger.valueOf(color.getRGB()));

			return binary;
		}
	}
}
