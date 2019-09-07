package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		JFrame frame = new JFrame("SNAKE");
		
		frame.setContentPane(new PainelGrafico());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		frame.setLayout(new BorderLayout(5, 5));
	}

}
