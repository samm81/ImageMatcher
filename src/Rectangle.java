import java.awt.Color;
import java.math.BigInteger;

public class Rectangle {
	private byte x, y, xbar, ybar, a, r, g, b;

	public static final int binaryLength = 8*8;

	public Rectangle(int x, int y, int xbar, int ybar, Color color) {
		this.x = (byte) x;
		this.y = (byte) y;
		this.xbar = (byte) xbar;
		this.ybar = (byte) ybar;
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

		this.ybar = binary.and(BigInteger.valueOf(0xFF)).byteValue();
		binary = binary.shiftRight(8);
		this.xbar = binary.and(BigInteger.valueOf(0xFF)).byteValue();
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

	public int getXbar() {
		return xbar & 0xFF;
	}

	public int getYbar() {
		return ybar & 0xFF;
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
		binary = binary.or(BigInteger.valueOf(xbar & 0xFF));

		binary = binary.shiftLeft(8);
		binary = binary.or(BigInteger.valueOf(ybar & 0xFF));

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
		Rectangle one = new Rectangle(50, 60, 70, 80, new Color(90, 100, 110, 120));
		BigInteger binary = one.getBinary();
		Rectangle two = new Rectangle(binary);
		one.printSelf();
		System.out.println();
		two.printSelf();
	}
	
}