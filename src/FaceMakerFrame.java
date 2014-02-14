import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;


public class FaceMakerFrame extends JFrame{

	BufferedImage bi;
	
	public FaceMakerFrame(int width, int height){
		setBounds(150, 150, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void updateFrame(BufferedImage bi){
		this.bi = bi;
	}
	
	public void paint(Graphics g){
		g.drawImage(bi, 0, 0, this);
	}
	
}
