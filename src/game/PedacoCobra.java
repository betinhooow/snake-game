package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class PedacoCobra {
/* 
 * ************************************************************************************************************
 * ATRIBUTOS
 * ************************************************************************************************************
 */
	
	// Coordenadas do bloco, largura e altura do pixel do segmento do corpo
	private  int coodX;
	int coodY;
	public static int largura;
	public static int altura;

/* 
 * ************************************************************************************************************
 * CONSTRUTOR
 * ************************************************************************************************************
 */
	
	// Cria o segmento da cobra
	public PedacoCobra(int coodX, int coodY, int areaPedacoCobra)
	{
		this.coodX = coodX;
		this.coodY = coodY;
		largura = areaPedacoCobra;
		altura = areaPedacoCobra;
	}
/* 
 * ************************************************************************************************************
 * MÉTODOS
 * ************************************************************************************************************
 */
	
	// 	Quando instanciado um novo segmento, esse metodo é chamado para colorir e preencher o retangulo criado,
    //para depois adicionarmos a lista da cobra
	public void draw(Graphics g)
	{
	
		g.setColor(Color.GREEN);
		
		
		// Determina a posição que cada 'corpocobra' será pintado
		g.fillRect(coodX * largura, coodY * altura, largura, altura); 
	}
	
/* 
 * ************************************************************************************************************
 * MÉTODOS ACESSORES
 * ************************************************************************************************************
 */	
	// Atributos privados necessarios em outras classes
	public int getCoodX() {
		return coodX;
	}
	public void setCoodX(int x) {
		coodX = x;
	}
	public int getCoodY() {
		return coodY;
	}
	public void setCoodY(int y) {
		coodY = y;
	}
	
	
	
	
}
