package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PainelGrafico extends JPanel implements Runnable, KeyListener {


/* 
 * **********************************************************************************************************
 * ATRIBUTOS
 * **********************************************************************************************************
 */
		
		private static final long serialVersionUID = 1L;
		
		// Velocidade da cobra em milisegundos
		public static final int VELOCIDADE = 750000;
		
		// Direcao de inicio (direita)
		private boolean direita = true, esquerda = false, cima = false, baixo = false;
		
		
		// 'Thread' que sera startada 
		private Thread thread;
		// 'rastejando' que controla a execucao do jogo (sim ou nao)
		private boolean rastejando;
		
		//'corpocobra' e 'maca', eh objeto de cada segmento da cobra [recebe x,y e o tamanho do cubo] e a maca
		private PedacoCobra pedacoCobra;
		private Maca maca;
		//'cobra' eh uma lista de 'corpocobra', um array de pixels e suas coordenadas
		private ArrayList<PedacoCobra> cobra;
		private ArrayList<Maca> macas;
		
		
		private Random aleatorio;
		
		// Coordenada inicial e tamanho que a cobra tera de inicio
		private int coodX = 5, coodY = 10, tam = 3;
		private int rastejos = 0;
	
		
/* 
 * **********************************************************************************************************
 * CONSTRUTOR
 * **********************************************************************************************************
 */	
	
	public PainelGrafico () 
	{
		
		// Faz com que o componente tenha a capacidade de obter foco,
		//sem esse m√©todo os "KeyListeners" nao funcionam
		setFocusable(true);
		addKeyListener(this);
		
		cobra = new ArrayList<PedacoCobra>();
		macas = new ArrayList<Maca>();	
		
		aleatorio = new Random(); 
		
		iniciaJogo();
	}
	
/* 
 * **********************************************************************************************************
 * CONSTRUTOR
 * **********************************************************************************************************
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
	//Metodo  FINAL - que servira para parar o jogo, 
	//seta o running pra false e aguarda a thread ser finalizada

	public void sairJogo()
	{
		System.exit(1);
	}
	
	public void reiniciar()
	{
		coodX = 25;
		coodY = 25;
		tam = 3;
	}
	


	public void rasteja()
	{
		// Evitar que a cobra tenha tamanho = 0
		if(cobra.size() == 0)
		{
			pedacoCobra = new PedacoCobra(coodX, coodY, 10);
			cobra.add(pedacoCobra);
		}
		//incrementa o contador da thread
		rastejos++; 
		
		if(rastejos > VELOCIDADE)
		{
			// Baseado na direcao atual, 
			//altera a posicao que sera incrementada na matriz
			if(direita) 	{coodX++;}
			if(esquerda) 	{coodX--;}
			if(cima) 		{coodY--;}
			if(baixo) 		{coodY++;}
			
			// Zera o contador da thread
			rastejos = 0;
			
			// Adiciona mais um item a frente a cada rastejada que ela dÔøΩ
			pedacoCobra = new PedacoCobra(coodX, coodY, 10);
			cobra.add(pedacoCobra);
			
			if(cobra.size() > tam)
			{
				cobra.remove(0);
			}
			
			if(macas.size() == 0)
			{
				// Gera uma posicao aleatoria para gerar a maca a cada iteraccao
				int coodX = aleatorio.nextInt((55 - 5) + 1) + 5;
				int coodY = aleatorio.nextInt((60 - 10) + 1) + 10;
				
				// Cria a maca e adiciona ao vetor
				maca = new Maca(coodX, coodY, 10);
				macas.add(maca);
			}
			
			for(int i = 0; i < macas.size(); i++)
			{
				if((coodX == macas.get(i).getCoodX()) && (coodY == macas.get(i).getCoodY()))
				{
					tam++;
					macas.remove(i);
					i++;
				}
			}
			
			// COLISAO BORDA = Limite maximo que a cobra pode andar no frame,
			//como temos 500 pixels na horizontal e na vertical divididos por 10,
			//pode-se andar 50 quadrados na vertical e horizontal ao longo do mapa [0 atÔøΩ 49]
			if(coodX < 5 || coodX > 54 || coodY < 10 || coodY > 59)
			{		
				// Excluir cabeca da cobra para nao "estourar"
				cobra.remove((cobra.size()) - 1);
				
				String[] opcoes = {"Reiniciar", "Sair"};
				
				//int input = JOptionPane.showOptionDialog(null, "Continuar o game", "Game over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.CLOSED_OPTION,null,  "Sim", "Nao");
				int input = JOptionPane.showOptionDialog(null, "O que deseja? ", "Game Over!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);
				if(input == 0)
				{
					System.out.print("reinicia o jogo\n");
					reiniciar();
						
					
				}
				else{
					System.out.println("Sair do Jogo\n");
					sairJogo();
				}

		
			  //reiniciaJogo();
			}
		}
	}
	private ImageIcon Titulo_Imagem; //classe ImageIcon
	private ImageIcon CabecaDireita;
	private ImageIcon CabecaEsquerda;
	private ImageIcon CabecaCima;
	private ImageIcon CabecaBaixo;
	private ImageIcon Corpo;
	
	private Timer timer;
	private int delay=100;
	
	public void paint (Graphics grafico) {
		
		
		
		
		
		
		
		
		// Imagem do titulo
		Titulo_Imagem = new ImageIcon("Imagens/Titulo_Cobra.jpg"); //estanciando uma nova imagem, colocando como parametro o local da imagem
		Titulo_Imagem.paintIcon(this, grafico, 50, 50);	
		
		grafico.setColor(Color.DARK_GRAY);
		grafico.fillRect(0,0,50,100);
		grafico.setColor(Color.DARK_GRAY);
		grafico.fillRect(0,0,300,50);
		// Retangulo do titulo
		grafico.setColor(Color.BLACK);
		grafico.drawRect(50, 50, 500, 50);
		
		//Retangulo do jogo
		grafico.setColor(Color.black);
		grafico.drawRect(50, 100, 500, 500);
		grafico.fillRect(50, 100, 500, 500);
		
		// Desenhar Linhas Verticais
		for(int i=50;i<=550;i+=500/50){
			grafico.drawLine(i, 100, i, 600);
		
		}
		// Desenhar linhas Horizontais
		for(int i=100;i<=600;i+=500/50){
			grafico.drawLine(50, i, 550, i);
		}
		// Desenhar Cobra em si
		for(int i=0;i<cobra.size();i++)	{
			cobra.get(i).draw(grafico);
		}
		// Desenhar Macas
		for(int i=0;i<macas.size();i++)	{
			macas.get(i).draw(grafico);
		} 
		
		for(int a = 0; a< cobra.size();a++) {
			if (a == 0 && direita) {
				CabecaDireita = new ImageIcon("Imagens/CabeÁaDireita.jpg");
				CabecaDireita.paintIcon(this,grafico, coodX * PedacoCobra.largura , coodY*PedacoCobra.altura);	
			}
			if (a == 0 && esquerda) {
				CabecaEsquerda = new ImageIcon("Imagens/CabeÁaEsquerda.jpg");
				CabecaEsquerda.paintIcon(this,grafico, coodX * PedacoCobra.largura , coodY*PedacoCobra.altura);	
			}
			if (a == 0 && baixo) {
				CabecaBaixo = new ImageIcon("Imagens/CabeÁaBaixo.jpg");
				CabecaBaixo.paintIcon(this,grafico, coodX * PedacoCobra.largura , coodY*PedacoCobra.altura);	
			}
			if (a == 0 && cima) {
				CabecaBaixo = new ImageIcon("Imagens/CabeÁaCima.jpg");
				CabecaBaixo.paintIcon(this,grafico, coodX * PedacoCobra.largura , coodY*PedacoCobra.altura);	
			}

			
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
		
		// Pressionar tecla para DIREITA (padrao de inicio)
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
