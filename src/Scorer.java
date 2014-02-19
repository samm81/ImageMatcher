import java.awt.image.BufferedImage;


public class Scorer {

	private int[][][] rgbs;

	public Scorer(BufferedImage base){
		rgbs = new int[base.getHeight()][base.getWidth()][3];
		for(int row=0;row<base.getHeight();row++){
			for(int col=0;col<base.getWidth();col++){
				int rgb = base.getRGB(col, row);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = rgb & 0xFF;
				rgbs[row][col][0] = r;
				rgbs[row][col][1] = g;
				rgbs[row][col][2] = b;
			}
		}
	}
	
	public long score(BufferedImage img) {
		long score = 0;
		for(int row=0;row<img.getHeight();row++){
			for(int col=0;col<img.getWidth();col++){
				int rgb = img.getRGB(col, row);
				int colors[] = new int[3];
				colors[0] = (rgb >> 16) & 0xFF;
				colors[1] = (rgb >> 8) & 0x00FF;
				colors[2] = rgb & 0xFF;
				for(int i=0;i<3;i++){
					//score += Math.pow(rgbs[row][col][i] - colors[i], 2);
					score += Math.abs(rgbs[row][col][i] - colors[i]);
				}
			}
		}
		return score;
	}
}
