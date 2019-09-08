package game;

import java.awt.Color;
import java.awt.Graphics;

//a cobra é construida por uma lista (array) de instancias dessa classe
public class PedacoCobra {
	
	//coordenadas do bloco, largura e altura do pixel do segmento dela
	private int coordX, coordY, width, height;
	
	//construtor que cria o segmento da cobra
	public PedacoCobra(int coordX, int coordY, int size) {
		this.coordX = coordX;
		this.coordY = coordY;
		width = size;
		height = size;
	}
	
	//to do
	public void rasteja() {
		
	}
	
	//quando instanciado um novo segmento, chamamos esse metodo para
	//colorir e preencher o retangulo criado, para depois adicionarmos a lista da cobra
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(coordX * width, coordY * height, width, height);
	}

	//getters and setters, pois sao att privados e usaremos esses atributos em outra classe
	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
}
