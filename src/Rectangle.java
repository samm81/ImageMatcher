import java.awt.Color;
import java.awt.Graphics;
import java.math.BigInteger;

public class Rectangle {

	private byte x, y, xbar, ybar, a, r, g, b;

	private static final int varBitCount = 8;

	public static final int bitCount = 8 * varBitCount;

	public Rectangle(int x, int y, int xbar, int ybar, Color color) {
		this.x = (byte) x;
		this.y = (byte) y;
		this.xbar = (byte) xbar;
		this.ybar = (byte) ybar;
		this.a = (byte) color.getAlpha();
		this.r = (byte) color.getRed();
		this.g = (byte) color.getGreen();
		this.b = (byte) color.getBlue();
	}

	public Rectangle(BigInteger binary) {
		BigInteger ones = BigInteger.valueOf(0xFF);
		this.b = binary.and(ones).byteValue();
		binary = binary.shiftRight(varBitCount);
		this.g = binary.and(ones).byteValue();
		binary = binary.shiftRight(varBitCount);
		this.r = binary.and(ones).byteValue();
		binary = binary.shiftRight(varBitCount);
		this.a = binary.and(ones).byteValue();
		binary = binary.shiftRight(varBitCount);

		this.ybar = binary.and(ones).byteValue();
		binary = binary.shiftRight(varBitCount);
		this.xbar = binary.and(ones).byteValue();
		binary = binary.shiftRight(varBitCount);
		this.y = binary.and(ones).byteValue();
		binary = binary.shiftRight(varBitCount);
		this.x = binary.and(ones).byteValue();
	}

	public int getX() {
		return toInt(x);
	}

	public int getY() {
		return toInt(y);
	}

	public int getXbar() {
		return toInt(xbar);
	}

	public int getYbar() {
		return toInt(ybar);
	}

	public Color getColor() {
		return new Color(toInt(r), toInt(g), toInt(b), toInt(a));
	}

	public BigInteger getBinary() {
		BigInteger binary = BigInteger.ZERO;

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(x)));

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(y)));

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(xbar)));

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(ybar)));

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(a)));

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(r)));

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(g)));

		binary = binary.shiftLeft(varBitCount);
		binary = binary.or(BigInteger.valueOf(toInt(b)));

		return binary;
	}

	private int toInt(byte num) {
		return num & 0xFF;
	}

	public void drawSelf(Graphics g) {
		g.setColor(getColor());
		int x = getX();
		int y = getY();
		int xbar = getXbar();
		int ybar = getYbar();
		if (x > xbar) {
			int temp = x;
			x = xbar;
			xbar = temp;
		}
		if (y > ybar) {
			int temp = y;
			y = ybar;
			ybar = temp;
		}
		int width = xbar - x;
		int height = ybar - y;
		g.fillRect(x, y, width, height);
	}

	public void printSelf() {
		System.out.print("x: " + getX() + ", ");
		System.out.print("y: " + getY() + ", ");
		System.out.print("xbar: " + getXbar() + ", ");
		System.out.print("ybar: " + getYbar());
		System.out.println();
		System.out.print("a: " + getColor().getAlpha() + ", ");
		System.out.print("r: " + getColor().getRed() + ", ");
		System.out.print("g: " + getColor().getGreen() + ", ");
		System.out.print("b: " + getColor().getBlue());
		System.out.println();
	}

	// for testing
	public static void main(String[] args) {
		Rectangle one = new Rectangle(50, 60, 70, 80, new Color(90, 100, 110,
				120));
		BigInteger binary = one.getBinary();
		Rectangle two = new Rectangle(binary);
		one.printSelf();
		System.out.println();
		two.printSelf();
	}

}