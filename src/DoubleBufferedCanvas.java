import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * @author Sam Maynard
 * Abstract class to deal with making a Double Buffered Canvas.
 *
 */
@SuppressWarnings("serial")
abstract class DoubleBufferedCanvas extends Canvas implements Runnable{

	protected Thread thread;

	protected int fps;
	private int pauseTime;

	protected Image second;
	
	private FPSCounter fpsCounter;

	/**
	 * constructor.
	 * @param fps the frames per second for which the canvas is to run at
	 */
	public DoubleBufferedCanvas(int fps) {
		super();
		this.fps = fps;
		if(fps == 0)
			this.pauseTime = 0;
		else
			this.pauseTime = (int)(1000f/(float)fps);
		
		fpsCounter = new FPSCounter();

		thread = new Thread(this);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		if(second == null)
			second = createImage(this.getWidth(), this.getHeight());
		
		Graphics g2nd = second.getGraphics();
		g2nd.clearRect(0, 0, second.getWidth(null), second.getHeight(null));
		fpsCounter.paintSelf(g2nd, this.getWidth() - 20, 10);
		draw(g2nd);
		g.drawImage(second, 0, 0, null);
	}

	/**
	 * actually draws the image
	 * @param g graphics to draw with
	 */
	abstract void draw(Graphics g);

	@Override
	public void run() {
		while(Thread.currentThread() == thread){
			updateVars();
			repaint();
			if(pauseTime > 0){
				try {
					Thread.sleep(pauseTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * starts the canvas animation
	 */
	public void start() {
		thread.start();
	}

	/**
	 * for any global variable updating that may need to be done
	 */
	abstract protected void updateVars();
	
	private class FPSCounter {

		long lastTime;
		
		int frames = 0;
		int f = 0;
		
		float updatesPerSecond = 12f;
		
		public FPSCounter() {
			lastTime = System.currentTimeMillis();
		}

		public void paintSelf(Graphics g, int x, int y){
			tick();
			
			g.setFont(new Font("Courier New", Font.BOLD, 26));
			g.setColor(Color.RED);
			g.drawString("" + f, x, y);
		}

		private void tick() {
			long time = System.currentTimeMillis();

			frames++;
			long diff = time - lastTime;

			if(diff > 1000f/updatesPerSecond){
				f = (int) (frames * updatesPerSecond);
				
				lastTime = time;
				frames = 0;
			}
		}

	}


}
