package game;

import java.awt.Color;

import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Jogo da Cobra");
		PainelGrafico game = new PainelGrafico();
		
		frame.add(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		frame.setBackground(Color.DARK_GRAY);
		
		frame.pack();

		
		frame.setBounds(0,0,507,587);
		
		frame.setLocationRelativeTo(null);
	}

}
