package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Maca {
	
	// CLASSE MACA DEVE SER IDENTICA A CLASSE PEDACOCORPO!
	
/* 
 * ************************************************************************************************************
 * ATRIBUTOS
 * ************************************************************************************************************
 */
		
		// Coordenadas do bloco, largura e altura do pixel do segmento do corpo
		private int coodX, coodY, largura, altura;
		
		private ImageIcon imgMaca;
		
/* 
 * ************************************************************************************************************
 * CONSTRUTOR
 * ************************************************************************************************************
 */
		
		// Cria o segmento da cobra
		public Maca(int coodX, int coodY, int areaPedacoCobra)
		{
			this.coodX = coodX;
			this.coodY = coodY;
			largura = areaPedacoCobra;
			altura = areaPedacoCobra;
		}
/* 
 * ************************************************************************************************************
 * METODOS
 * ************************************************************************************************************
 */		
		// 	Quando instanciado um novo segmento, esse metodo é chamado para colorir e preencher o retangulo criado,
	    //para depois adicionarmos a lista de macas
		public void draw(Graphics grafico)
		{
			grafico.setColor(Color.RED);
			// Determina a posição que cada 'corpocobra' será pintado
			grafico.fillRect(getCoodX() * getLargura(), getCoodY() * getAltura(), getLargura(), getAltura()); 
			
			setImgMaca(new ImageIcon("Imagens/Maca.png"));
			getImgMaca().paintIcon(null, grafico, getCoodX() * getLargura(), getCoodY() * getAltura());
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
		public int getLargura() {
			return largura;
		}
		public void setLargura(int largura) {
			this.largura = largura;
		}
		public int getAltura() {
			return altura;
		}
		public void setAltura(int altura) {
			this.altura = altura;
		}
		
		public ImageIcon getImgMaca() {
			return imgMaca;
		}
		public void setImgMaca(ImageIcon imgMaca) {
			this.imgMaca = imgMaca;
		}
}
