package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class PainelGrafico extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5);
	public static final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5);
	
	public PainelGrafico() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	//metodo que é chamado no momento em que se cria a interface visual (setVisible(true))
	public void paint(Graphics g) {
		//constroi a estrutura da matriz de acordo com W e H do painel
		for(int i = 0; i < WIDTH/10; i++) {
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
		}
		
		for(int i = 0; i < HEIGHT/10; i++) {
			g.drawLine(0, i * 10, WIDTH, i * 10);
		}

		//seta a cor do painel, e preenche os retangulos da matriz
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
