import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class FaceMaker {

	public ArrayList<Color> colorsArr = new ArrayList<Color>();
	private ArrayList<int[]> points = new ArrayList<int[]>();
	private int numShapes;
	private int numSides;
	private int width;
	private int height;
	public static int leeway = 3;

	public FaceMaker(Display fmf, int numShapes, int numSides){
		this.numShapes = numShapes;
		this.numSides = numSides;
		width = fmf.getWidth();
		height = fmf.getHeight();

		Random r = new Random();
		for(int i=0;i<numShapes;i++){
			//Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), 128);
			Color c = Color.BLACK;
			colorsArr.add(c);
			
			for(int j=0;j<numSides;j++){
				int[] p = {r.nextInt(width), r.nextInt(height)};
				points.add(p);
			}

		}
	}

	public FaceMaker(FaceMaker fm){
		this.colorsArr = (ArrayList<Color>) fm.colorsArr.clone();
		this.points = (ArrayList<int[]>) fm.points.clone();
		this.numShapes = fm.numShapes;
		this.numSides = fm.numSides;
		this.width = fm.width;
		this.height = fm.height;
	}

	public FaceMaker(ArrayList<Color> colorsArr,
			ArrayList<int[]> points,
			int numShapes,
			int numSides,
			int width,
			int height){

		this.colorsArr = colorsArr;
		this.points = points;
		this.numShapes = numShapes;
		this.numSides = numSides;
		this.width = width;
		this.height = height;

	}

	public BufferedImage makeFace(){		
		BufferedImage face = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics g = face.getGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		for(int i=0;i<numShapes;i++){
			Color c = colorsArr.get(i);
			g.setColor(c);
			//if(i==0) System.out.println(colorsArr.get(i));

			int[] xPoints = new int[numSides];
			int[] yPoints = new int[numSides];
			for(int j=0;j<numSides;j++){
				xPoints[j] = points.get(i*numSides+j)[0];
				yPoints[j] = points.get(i*numSides+j)[1];
			}

			g.fillPolygon(xPoints, yPoints, numSides);
		}

		return face;
	}

	public void mutate(){
		Random r = new Random();
		for(int i=0;i<numShapes;i++){
			Color c = colorsArr.get(i);

			int red   = c.getRed();
			int green = c.getGreen();
			int blue  = c.getBlue();

			red   += r.nextInt(leeway) - leeway/2;
			if(red > 255)
				red = 255;
			if(red < 0)
				red = 0;

			green += r.nextInt(leeway) - leeway/2;
			if(green > 255)
				green = 255;
			if(green < 0)
				green = 0;

			blue  += r.nextInt(leeway) - leeway/2;
			if(blue > 255)
				blue = 255;
			if(blue < 0)
				blue = 0;

			//System.out.println(red+" "+green+" "+blue);
			c = new Color(red,green,blue,128);
			colorsArr.set(i, c);
			
			//if(i==0) System.out.println(colorsArr.get(i));
		}
		for(int i=0;i<numShapes;i++){
			for(int j=0;j<numSides;j++){
				int index = i*numSides + j;
				int[] point = points.get(index);
				int x = point[0];
				int y = point[1];
				x += r.nextInt(leeway) - leeway/2;
				if(x < 0)
					x = 0;
				if(x > width)
					x = width;
				y += r.nextInt(leeway) - leeway/2;
				if(y < 0)
					y = 0;
				if(y > width)
					y = height;
				point[0] = x;
				point[1] = y;
				points.set(index, point);
			}
		}
	}

	public void increaseLeeway(){
		this.leeway = this.leeway + 2;
	}

	public void resetLeeway(){
		this.leeway = 3;
	}

	public void printSelf(){
		System.out.println(colorsArr);
		System.out.println("numShapes: " + numShapes + "\tnumSides: " + numSides);
		System.out.print("{{");
		for(int i=0;i<points.size();i++){
			int[] point = points.get(i);
			int x = point[0];
			int y = point[1];
			System.out.print(x+","+y);
			if(i%numSides == numSides-1)
				System.out.print("} {");
			else
				System.out.print(" ");
		}
		System.out.println("}}");
	}
}
