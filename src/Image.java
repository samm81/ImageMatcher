import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat());
	}
	
	public Rectangle[] getRectangles() {
		return rectangles;
	}

	public BufferedImage getImage() {
		return image;
	}

	public class Rectangle {
		private int x, y, width, height;
		private Color color;

		public Rectangle(int x, int y, int width, int height, Color color) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.color = color;
		}
		
		public Rectangle(long binary){
			int rgb = (int) (binary & 0xFFFFFFFF);
			binary = binary >> 32;
			int height = (int) (binary & 0xFF);
			binary = binary >> 8;
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
		
		public long getBinary() {
			long binary = 0;
			binary = binary << 8;
			binary += x;
			binary = binary << 8;
			binary += y;
			binary = binary << 8;
			binary += width;
			binary = binary << 8;
			binary += height;
			binary = binary << 32;
			binary += color.getRGB();
			return binary;
		}
	}
}
