package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PainelGrafico extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	//tamanho do painel, e velocidade da cobra em miliseconds
	public static final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5);
	public static final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5);
	public static final int VELOCIDADE = 250000;
	
	//thread que sera startada e running que controla a execucao do jogo
	private Thread thread;
	private boolean running;
	
	//pedacobra, é cada segmento da cobra, recebe x,y e o tamanho do cubo
	//cobra é uma lista dos pedacoes, uma array de pixels e suas coordenadas
	private PedacoCobra pc;
	private ArrayList<PedacoCobra> cobra;
	
	//direcao de inicio que a cobra seguira
	private boolean direita = true, esquerda = false, cima = false, baixo = false;
	
	//coordenada inicial e tamanho que a cobra tera de inicio
	private int coordX = 10, coordY = 10, size = 5;
	private int rastejos = 0;
	
	//seta o tamanho do painel com as dimensoes, cria a cobra que é feita de uma lista cubos de 10pxls
	//e inicia o jogo
	public PainelGrafico() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		cobra = new ArrayList<PedacoCobra>();
		iniciaJogo();
	}
	
	private void rasteja(){
		//evitar que a cobra nunca tenha tamanho 0
		if(cobra.size() == 0) {
			pc = new PedacoCobra(coordX, coordY, 10);
			cobra.add(pc);
		}
		
		//incrementa o contador da thread
		rastejos++;
		
		if(rastejos == VELOCIDADE) {
			//baseado na direcao atual, altera a posicao que sera incrementada
			//na matriz
			if(direita) coordX++;
			if(esquerda) coordX--;
			if(cima) coordY--;
			if(baixo) coordY++;
			
			//zera o contador da thread
			rastejos = 0;
			
			//adiciona mais um item a frente a cada rastejada que ela da
			pc = new PedacoCobra(coordX, coordY, 10);
			cobra.add(pc);
			
			//remove o ultimo elemento do array para ela rastejar e evitar que ultrapasse
			//seu tamanho atual
			if(cobra.size() > size) {
				cobra.remove(0);
			}
		}
	}
	
	//metodo que inicia a thread de evento do jogo, seta run pra true
	//e starta o evento
	private void iniciaJogo() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//metodo que servira para parar o jogo, seta o running pra false
	//e aguarda a thread ser finalizada
	private void paraJogo() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//metodo que é chamado no momento em que se cria a interface visual (setVisible(true))
	//tudo que aparece no painel, é renderizado aqui, o fundo, a cobra e a fruta;
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
		
		for(int i = 0; i < cobra.size(); i++) {
			cobra.get(i).draw(g);
		}
		
	}

	//metodo sobrescrito da interface Runnable, é obrigatoria a implementação dela
	//quando a classe sera executada por meio de uma thread
	@Override
	public void run() {
		//enquanto estiver rodando chama a classe que usa o contador da thread
		//incrementa a direcao que ela esta andando e reprocessa o grafico
		while(running) {
			rasteja();
			//chama o paint definido novamente, ao inves de criar uma nova configuracao de grafico
			//altera a existente
			repaint();
		}
		
	}

}
