import java.awt.Color;
import java.math.BigInteger;

public class Rectangle {
	private byte x, y, width, height, a, r, g, b;

	public static final int binaryLength = 8*8;

	public Rectangle(int x, int y, int width, int height, Color color) {
		this.x = (byte) x;
		this.y = (byte) y;
		this.width = (byte) width;
		this.height = (byte) height;
		this.a = (byte) color.getAlpha();
		this.r = (byte) color.getRed();
		this.g = (byte)	color.getGreen();
		this.b = (byte) color.getBlue();
	}

	public Rectangle(BigInteger binary) {
		this.b = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);
		this.g = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);
		this.r = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);
		this.a = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);

		this.height = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);
		this.width = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);
		this.y = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);
		this.x = binary.and(BigInteger.valueOf(0xFF)).byteValue();
	}

	public int getX() {
		return x & 0xFF;
	}

	public int getY() {
		return y & 0xFF;
	}

	public int getWidth() {
		return width & 0xFF;
	}

	public int getHeight() {
		return height & 0xFF;
	}

	public Color getColor() {
		return new Color(r & 0xFF, g & 0xFF, b & 0xFF, a & 0xFF);
	}

	public BigInteger getBinary() {
		BigInteger binary = BigInteger.ZERO;

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(x & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(y & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(width & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(height & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(a & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(r & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(g & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(b & 0xFF));

		return binary;
	}
	
	// for testing
	public static void main(String[] args) {
		Rectangle one = new Rectangle(50, 60, 70, 80, new Color(90, 100, 110, 120));
		BigInteger binary = one.getBinary();
		Rectangle two = new Rectangle(binary);
		System.out.println(two.getX() + " " + two.getY() + " " + two.getWidth() + " " + two.getHeight());
		System.out.println(two.r + " " + two.g + " " + two.b + " " + two.a);
		System.out.println(two.getColor().getRed() + " " + two.getColor().getGreen() + " " + two.getColor().getBlue() + " " + two.getColor().getAlpha());
	}
	
}