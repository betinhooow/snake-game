package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("SNAKE GAME");
		PainelGrafico game = new PainelGrafico();
		
		frame.add(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		// Ordem entre 'pack' e 'setLocationRelativeTo' é importante!
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		frame.setLayout(new BorderLayout(5, 5)); // Pra que serve?
		
	}

}
