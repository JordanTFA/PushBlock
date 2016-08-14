package pushblock;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Board extends Canvas implements Runnable, KeyListener{
	
	public static void setupBoard(final int WIDTH, final int HEIGHT){
		
		final JFrame frame = new JFrame("PushBlock");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		
		KeyGetter.loadKeys();
		
		JMenuBar bar = new JMenuBar();
		bar.setBounds(0,0,WIDTH,25);
		
		JMenu file = new JMenu("File");
		file.setBounds(0,0,45,45);
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("New Game");
			}
		});
		
		JMenuItem options = new JMenuItem("Options");
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				Config.openConfig(frame);
			}
		});
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Closing...");
				System.exit(0);
			}
		});
		
		Board b = new Board();
		b.setBounds(0,25,WIDTH,HEIGHT-25);
		
		file.add(newGame);
		file.add(options);
		file.add(exit);
		
		frame.add(b);
		frame.add(bar);
		frame.setVisible(true);
		
		bar.add(file);
		b.start();
		
	}

	private void start() {
		
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		
	}
	
	@Override
	public void run() {

		Boolean isRunning = true;
		
		while(isRunning){
			update();
			BufferStrategy bs = getBufferStrategy();
			if(bs == null){
				createBufferStrategy(3);
				continue;
			}
			
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();
			render(g);
			bs.show();
		}
	}
	
	public void render(Graphics2D g){
		
		requestFocus();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", Font.PLAIN,20));
		g.drawString("Push Block",  150,  50);
		
	}
	
	public void init(){
		
		
		
		
	}
	
	public void update(){
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
