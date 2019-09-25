package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class PedacoCobra {
/* 
 * ************************************************************************************************************
 * ATRIBUTOS
 * ************************************************************************************************************
 */
	
	// Coordenadas do bloco, largura e altura do pixel do segmento do corpo
	private  int coodX, coodY;
	public static int largura;
	public static int altura;
	
	private ImageIcon imgCorpoCobra;
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
	public void draw(Graphics grafico)
	{
	
		grafico.setColor(Color.GREEN);
		
		// Determina a posição que cada 'corpocobra' será pintado
		grafico.fillRect(getCoodX() * getLargura(), getCoodY() * getAltura(), getLargura(), getAltura()); 
		
		setImgCorpoCobra(new ImageIcon(getClass().getClassLoader().getResource("Imagens/CorpoCobra.jpg")));
		getImgCorpoCobra().paintIcon(null, grafico, getCoodX() * getLargura(), getCoodY() * getAltura());
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

	public static int getLargura() {
		return largura;
	}

	public static void setLargura(int largura) {
		PedacoCobra.largura = largura;
	}

	public static int getAltura() {
		return altura;
	}

	public static void setAltura(int altura) {
		PedacoCobra.altura = altura;
	}
	
	public ImageIcon getImgCorpoCobra() {
		return imgCorpoCobra;
	}

	public void setImgCorpoCobra(ImageIcon imgCorpoCobra) {
		this.imgCorpoCobra = imgCorpoCobra;
	}
	
	
	
}
