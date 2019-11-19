package tower;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
* menu game
 */

public class Frame extends JFrame {
	public static String Titel = "khanh's Tower Defense";
	/*
	tao khung vao tran game
	 */
	public static Dimension Size1 = new Dimension(1280, 720);
	public static Dimension Size2 = new Dimension(2560, 1440);
	public static boolean BigRes;
	
	public static int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	public Frame() {
		
		
			if(a.Fullscreen) {
				setUndecorated(true);
				setSize(ScreenWidth, ScreenHeight);
			} else {
				setSize(Size1);	
			}

		
		setTitle(Titel);
		setResizable(false);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init();
	}
	
	public void init() {
		setLayout(new GridLayout(1, 1, 0, 0));
		
		Screen screen = new Screen(this);
		add(screen);
			
	}
	
	public static void main() {
		Frame frame = new Frame();
		frame.setAlwaysOnTop(true);
		
			if(ScreenHeight >= Size1.getHeight() && ScreenWidth >= Size1.getWidth()) {
				if(a.Fullscreen) {
					Size1 = new Dimension(ScreenWidth, ScreenHeight);	
				}
				frame.setVisible(true);	
			} else {
				JOptionPane.showMessageDialog(frame, "Error!", Titel, JOptionPane.ERROR_MESSAGE);
				System.exit(100);
			}	
		}
	}

