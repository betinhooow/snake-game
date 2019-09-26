package game;

/*
 * ****************************************************
 * 	Projeto - Snake Game
 * ****************************************************
 * 
 * 	Desenvolvedores:
 *		
 *		- Arthur Guedes
 *		- Kevin Barrios
 *		- Matheus Bruder
 *		- Roberto Nobre
 *
 *
 *
 * 		Tal projeto é responsável por compor parte da
 *	nota da disciplina SI400, Programação Orientada a
 *	Objetos II, da Faculdade de Tecnologia da Unicamp.
 *
 *
 * 	GitHub: https://github.com/RobertoNobre/snake-game
 * 
 */

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

<<<<<<< HEAD
		
		frame.setBounds(0,0,507,590);
=======
		frame.setSize(507, 587);
>>>>>>> a98e01a713f863e4c03b8dac0b1b9334406b3fb7
		
		frame.setLocationRelativeTo(null);
	}

}
