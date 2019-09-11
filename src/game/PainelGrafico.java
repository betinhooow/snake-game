package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;




public class PainelGrafico extends JPanel implements Runnable, KeyListener {
/* 
 * ************************************************************************************************************
 * ATRIBUTOS
 * ************************************************************************************************************
 */
	
	private static final long serialVersionUID = 1L;
	
	// Tamanho do painel
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	// Velocidade da cobra em milisegundos
	public static final int VELOCIDADE = 1000000;
	
	// Direcao de inicio (direita)
	private boolean direita = true, esquerda = false, cima = false, baixo = false;
	
	
	// 'Thread' que sera startada 
	private Thread thread;
	// 'rastejando' que controla a execucao do jogo (sim ou nao)
	private boolean rastejando;
	
	//'corpocobra', é objeto de cada segmento da cobra [recebe x,y e o tamanho do cubo]
	private PedacoCobra pedacoCobra;
	//'cobra' é uma lista de 'corpocobra', um array de pixels e suas coordenadas
	private ArrayList<PedacoCobra> cobra;
	
	// Coordenada inicial e tamanho que a cobra tera de inicio
	private int X = 10, Y = 10, size = 3;
	private int rastejos = 0;
	
/* 
 * ************************************************************************************************************
 * CONSTRUTOR
 * ************************************************************************************************************
 */
	
	public PainelGrafico()
	{
		setFocusable(true); //NAO ENTENDI
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		
		cobra = new ArrayList<PedacoCobra>();
		iniciaJogo();
	}
	
/* 
 * ************************************************************************************************************
 * MÉTODOS
 * ************************************************************************************************************
 */	
	
	// Metodo INICIAL - inicia a thread de evento do jogo, 
	//seta run pra true e starta o evento
	public void iniciaJogo()
	{
		rastejando = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//Metodo  FINAL - que servira para parar o jogo, 
	//seta o running pra false e aguarda a thread ser finalizada
	public void paraJogo()
	{
		rastejando = false;
		// TRY-CATCH = Sugestão do IDE (não sei pqe)!
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rasteja()
	{
		// Evitar que a cobra tenha tamanho = 0
		if(cobra.size() == 0)
		{
			pedacoCobra = new PedacoCobra(X, Y, 10);
			cobra.add(pedacoCobra);
		}
		//incrementa o contador da thread
		rastejos++; 
		
		
		
		if(rastejos > VELOCIDADE)
		{
			// Baseado na direcao atual, 
			//altera a posicao que sera incrementada na matriz
			if(direita) 	{X++;}
			if(esquerda) 	{X--;}
			if(cima) 		{Y--;}
			if(baixo) 		{Y++;}
			
			// Zera o contador da thread
			rastejos = 0;
			
			// Adiciona mais um item a frente a cada rastejada que ela dá
			pedacoCobra = new PedacoCobra(X, Y, 10);
			cobra.add(pedacoCobra);
			
			if(cobra.size() > size)
			{
				cobra.remove(0);
			}
		}
	}
	
	// Metodo que é chamado no momento em que se cria a interface visual (setVisible(true))
	//tudo que aparece no painel, é renderizado aqui, o fundo, a cobra e a maça!
	public void paint(Graphics g)
	{
//		g.clearRect(0, 0, WIDTH, HEIGHT); para que serve?
		
		// Define a cor do painel
		g.setColor(Color.BLACK);
		// Preenche os retangulos da matriz
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i = 0; i < WIDTH; i+=WIDTH/50)
		{
			g.drawLine(i, 0, i, HEIGHT);
		}
		
		for(int i = 0; i < HEIGHT; i+=HEIGHT/50)
		{
			g.drawLine(0, i, WIDTH, i);
		}
		for(int i = 0; i < cobra.size() ; i++)
		{
			cobra.get(i).draw(g);
		}
	}



	@Override
	/*
	 * Metodo sobrescrito da interface Runnable!!!
	 * OBS -> Obrigatoria a implementacao dela quando a classe sera executada por meio de uma thread
	 */
	public void run() {
		// TODO Auto-generated method stub
		while(rastejando)
		{
			// 	Enquanto estiver rodando chama a classe que usa o contador da thread
			//incrementa a direcao que ela esta andando e reprocessa o grafico
			rasteja();
			
			// 	'repaint()' - Chama o paint definido novamente, 
			//ao inves de criar uma nova configuracao de grafico altera a existente
			repaint();
			
		}
	}

	@Override
	// Proveniente da interface KeyListener
	public void keyPressed(KeyEvent evento) {
		// TODO Auto-generated method stub
		int tecla = evento.getKeyCode();
		
		// Pressionar tecla para DIREITA (padrão de inicio)
		if((tecla == KeyEvent.VK_RIGHT) && (!esquerda))
		{
			direita = true;
			cima = false;
			baixo = false;
		}
		
		// Pressionar tecla para ESQUERDA
		if((tecla == KeyEvent.VK_LEFT) && (!direita))
		{
			esquerda = true;
			cima = false;
			baixo = false;
		}
		
		// Pressionar tecla para CIMA
		if((tecla == KeyEvent.VK_UP) && (!baixo))
		{
			cima = true;
			direita = false;
			esquerda = false;
		}
		
		// Pressionar tecla para BAIXO
		if((tecla == KeyEvent.VK_DOWN) && (!cima))
		{
			baixo = true;
			direita = false;
			esquerda = false;
		}
	}

	@Override
	// Proveniente da interface KeyListener
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	// Proveniente da interface KeyListener
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
