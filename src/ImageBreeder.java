import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class ImageBreeder {

	private BufferedImage baseImg;
	private BufferedImage bestCandidate;

	public ImageBreeder(BufferedImage baseImg){
		this.baseImg = baseImg;
	}
	
	public BufferedImage getBestCandidate(){
		return bestCandidate;
	}

	public long getScore(BufferedImage img){
		long score = compareImageToBase(img);
		return score;
	}

	private long compareImageToBase(BufferedImage img) {

		int rows = baseImg.getHeight();
		int cols = baseImg.getWidth();

		long score = 0;

		for(int row=0;row<rows;row++){
			for(int col=0;col<cols;col++){
				score += Math.abs(baseImg.getRGB(col, row) - img.getRGB(col, row));
			}
		}

		return score;
	}
}
