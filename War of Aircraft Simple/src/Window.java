package game.device;

import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 4985134373616078745L;
	
	public Display display;
	public Keyboard keyboard;
	
	public Window(Display display, Keyboard keyboard) {
		this.display = display;
		this.keyboard = keyboard;
		
		this.addKeyListener(keyboard);
		
		this.add(display);
		this.setSize(1024, 640);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("War of Aircrafts Simple Version");

		this.setVisible(true);
	}
}
