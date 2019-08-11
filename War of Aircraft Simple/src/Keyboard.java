package game.device;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

public class Keyboard extends JPanel implements KeyListener, Runnable {
	private static final long serialVersionUID = -675120062196788831L;
	
	private Semaphore semp;
	
	boolean[] keyMap;
	
	public Keyboard(Semaphore semp, boolean[] keyMap) {
		this.semp = semp;
		this.keyMap = keyMap;
		Arrays.fill(keyMap, false);
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			semp.acquire();
			keyMap[e.getKeyCode()] = true;
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally {
			semp.release();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			semp.acquire();
			keyMap[e.getKeyCode()] = false;
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			semp.release();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
