package game;

import java.awt.Color;

import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake Game - 1990s");
		PainelGrafico game = new PainelGrafico();
		
		frame.add(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		frame.setBackground(Color.DARK_GRAY);
		
		frame.pack();

		frame.setSize(507, 587);
		
		frame.setLocationRelativeTo(null);
	}

}
