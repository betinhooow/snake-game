package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PainelGrafico extends JPanel implements Runnable, KeyListener {


/* 
 * **********************************************************************************************************
 * ATRIBUTOS
 * **********************************************************************************************************
 */
		
		private static final long serialVersionUID = 1L;
		
		// Velocidade da cobra em milisegundos e tamanho padrao
		public static final int VELOCIDADE = 1000000;
		public static final int tamINICIAL = 5;
		public static final int MAXpontos = 500;
		
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
		
		
		// Objeto aleatorio que define o respawn da maca
		private Random aleatorio;
		
		// Coordenada inicial e tamanho que a cobra tera de inicio
		private int coodX = 5, coodY = 10, tam = tamINICIAL;
		
		private int rastejos = 0;
		
		// Pontos do jogador na partida
		public int pontos = 0;
		
		//Flag só permite acionar um novo movimento após a cobra se mover.
		public int flag = 0;
		// Nome do jogador
		public String name = "";
	
		// Atributos que definem as imagens,
		//utilizam classe ImageIcon
		private ImageIcon Titulo_Imagem;
		private ImageIcon CabecaDireita;
		private ImageIcon CabecaEsquerda;
		private ImageIcon CabecaCima;
		private ImageIcon CabecaBaixo;
		
/* 
 * **********************************************************************************************************
 * CONSTRUTOR
 * **********************************************************************************************************
 */	
	
	public PainelGrafico () 
	{
		executaSom("Sons/Menu.wav");
		
		// Faz com que o componente tenha a capacidade de obter foco,
		//sem esse metodo os "KeyListeners" nao funcionam
		setFocusable(true);
		addKeyListener(this);
		
		cobra = new ArrayList<PedacoCobra>();
		macas = new ArrayList<Maca>();	
	
		aleatorio = new Random(); 
		
		iniciaJogo();
	}
	
/* 
 * **********************************************************************************************************
 * METODOS
 * **********************************************************************************************************
 */	
	
	// Metodo INICIAL - inicia a thread de evento do jogo, 
	//seta run pra true e starta o evento
	public void iniciaJogo()
	{
		setRastejando(true);
		
		thread = new Thread(this);
		thread.start();
	}


	//Metodo  FINAL - que servira para parar o jogo, 
	//seta o running pra false e aguarda a thread ser finalizada
	//Metodo  FINAL - que servira para parar o jogo, 
	//seta o running pra false e aguarda a thread ser finalizada
	public void sairJogo()
	{
		JOptionPane.showMessageDialog(null, "Este jogo foi desenvolvido por: \n\n Arthur Guedes \n Kevin Barrios \n Matheus Bruder \n Roberto Nobre \n\n");
		System.exit(1);
	}
	
	public void reiniciar()
	{
		//LIMPA A ESTRUTURA DE DADOS
		cobra.clear(); 
		macas.clear();
		
		String[] opcoes = {"Reiniciar", "Sair"};
		
		int input = JOptionPane.showOptionDialog(null, "O que deseja? ", "Game Over!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);
		//IF - caso a opcao seja reiniciar
		if(input == 0)
		{
			setCoodX(25);
			setCoodY(25);
			setTamanho(tamINICIAL);
			setPontos(0);
			System.out.print("reinicia o jogo\n");			
		}
		
		// ELSE - caso a opcao seja sair do jogo
		else{
			System.out.println("Sair do Jogo\n");
			sairJogo();
		}	
	}

	public void rasteja()
	{
		// Evitar que a cobra tenha tamanho = 0
		if(cobra.size() == 0)
		{
			pedacoCobra = new PedacoCobra(getCoodX(), getCoodY(), 10);
			cobra.add(pedacoCobra);
		}
		//incrementa o contador da thread 
		setRastejos(getRastejos() + 1);
		
		if(getRastejos() > VELOCIDADE)
		{
			// Baseado na direcao atual, 
			//altera a posicao que sera incrementada
			if(isDireita()) 	{setCoodX(getCoodX() + 1);}
			if(isEsquerda()) 	{setCoodX(getCoodX() - 1);}
			if(isCima()) 		{setCoodY(getCoodY() - 1);}
			if(isBaixo()) 		{setCoodY(getCoodY() + 1);}
			
			// Zera o contador da thread
			setRastejos(0);
			
			// Adiciona mais um item a frente a cada rastejada que ela da
			pedacoCobra = new PedacoCobra(getCoodX(), getCoodY(), 10);
			cobra.add(pedacoCobra);
			flag = 0; //FLAG
			if(cobra.size() > getTamanho())
			{
				cobra.remove(0);
			}
			
			if(macas.size() == 0)
			{
				// Gera uma posicao aleatoria para gerar a maca a cada iteracao
				int macaCoodX = aleatorio.nextInt(49);
				// Deve ser '+5' devido ao inicio do painel do jogo
				int macaCoodY = aleatorio.nextInt(49)+5;
				
				// Cria a maca e adiciona ao vetor
				maca = new Maca(macaCoodX, macaCoodY, 10);
				macas.add(maca);
			}
			
			//Algoritmo responsavel por "comer a maca" e impedir que de respawn em cima da propria cobra
			for(int i = 0; i < macas.size(); i++)
			{
				if((getCoodX() == macas.get(i).getCoodX()) && (getCoodY() == macas.get(i).getCoodY()))
				{
					setTamanho(getTamanho() + 1);
					macas.remove(i);
					i++;
					executaSom("Sons/Comendo.wav");
					setPontos(getPontos() + 100);
					System.out.println(getPontos());
			 
				}
				
				// Condicao de final de jogo
				if((getPontos() >= MAXpontos)) 
				{//DEVE SER ALTERADO PARA 250 MIL PONTOS apenas para exibir para o professor
					executaSom("Sons/Ganhou.wav");
					JOptionPane.showMessageDialog(null, "Parabéns " + getName() + ", você ganhou!");
					sairJogo();
				}
			}
			
			// COLISAO PROPRIA COBRA - Caso a cobra bata nela mesma o jogo deve ser reiniciado
			for(int i = 0; i < cobra.size(); i++)
			{
				if((getCoodX() == cobra.get(i).getCoodX()) && (getCoodY() == cobra.get(i).getCoodY()))
				{
					if(i != cobra.size() - 1)
					{
						executaSom("Sons/Perdendo.wav");
						reiniciar();
						System.out.println("PERDEU");
						
					}
				}
			}
			// COLISAO BORDA = Limite maximo que a cobra pode andar no frame,
			//como temos 500 pixels na horizontal e na vertical divididos por 10,
			//pode-se andar 50 quadrados na vertical e horizontal ao longo do mapa
			if(getCoodX() < 0 || getCoodX() >= 50 || getCoodY() < 5 || getCoodY() >= 55)
			{	
				executaSom("Sons/Game_Over.wav");
				// Excluir cabeca da cobra para nao "estourar"
				cobra.remove((cobra.size()) - 1);
				
				// Reiniciar jogo
				reiniciar();
			}
		}
	}

	public void paint (Graphics grafico) {
		// Imagem do titulo
		Titulo_Imagem = new ImageIcon(getClass().getClassLoader().getResource("Imagens/Titulo_Cobra.jpg")); //instanciando uma nova imagem, colocando como parametro o local da imagem
		Titulo_Imagem.paintIcon(this, grafico, 0, 0);	
		
		int fontSize = 20;
	    grafico.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
	     
	    grafico.setColor(Color.red);
	    
		// Retangulo do titulo
		grafico.setColor(Color.BLACK);
		grafico.drawRect(0, 0, 500, 50);
		
		//Retangulo do jogo
		grafico.setColor(Color.black);
		grafico.drawRect(0, 50, 500, 500);
		grafico.fillRect(0, 50, 500, 500);
		
		
		// Desenhar Linhas Verticais
		for(int i=0;i<=500;i+=500/50){
			grafico.drawLine(i, 50, i, 550);
		
		}
		// Desenhar linhas Horizontais
		for(int i=50;i<=550;i+=500/50){
			grafico.drawLine(0, i, 500, i);
		}
		// Desenhar Cobra em si
		for(int i=0;i<cobra.size();i++)	{
			cobra.get(i).draw(grafico);
		}
		// Desenhar Macas
		for(int i=0;i<macas.size();i++)	{
			macas.get(i).draw(grafico);
		} 
		
		// Desenhar na tela o nome do jogador e o numero de pontos (atuais)
		grafico.setColor(Color.white);
		grafico.drawString("Jogador: " + getName() , 10, 30);
		grafico.drawString("Pontos:  " + Integer.toString(getPontos()), 350, 30);
		
		for(int a = 0; a < cobra.size(); a++) {
			if (a == 0 && isDireita()) {
				
				CabecaDireita = new ImageIcon(getClass().getClassLoader().getResource("Imagens/CabecaDireita.jpg"));
				CabecaDireita.paintIcon(this, grafico, getCoodX() * PedacoCobra.getLargura(), getCoodY() * PedacoCobra.getAltura());	
			}
			if (a == 0 && isEsquerda()) {
				CabecaEsquerda = new ImageIcon(getClass().getClassLoader().getResource("Imagens/CabecaEsquerda.jpg"));
				CabecaEsquerda.paintIcon(this, grafico, getCoodX() * PedacoCobra.getLargura(), getCoodY() * PedacoCobra.getAltura());	
			}
			if (a == 0 && isBaixo()) {
				CabecaBaixo = new ImageIcon(getClass().getClassLoader().getResource("Imagens/CabecaBaixo.jpg"));
				CabecaBaixo.paintIcon(this, grafico, getCoodX() * PedacoCobra.getLargura(), getCoodY() * PedacoCobra.getAltura());	
			}
			if (a == 0 && isCima()) {
				CabecaCima = new ImageIcon(getClass().getClassLoader().getResource("Imagens/CabecaCima.jpg"));
				CabecaCima.paintIcon(this, grafico, getCoodX() * PedacoCobra.getLargura(), getCoodY() * PedacoCobra.getAltura());	
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
		while(isRastejando())
		{
			// Garante que o usuÃ¡rio insira um nome
			if(getName() == null || getName().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Preencha seu nome, por favor!");
				setName(JOptionPane.showInputDialog("Seu nome?"));
				System.out.println(getName());
				continue;
			}
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
		if((tecla == KeyEvent.VK_RIGHT) && (!(isEsquerda())) && (flag !=1))
		{
			setDireita(true);
			setCima(false);
			setBaixo(false);
			flag = 1;
			
		}
		
		// Pressionar tecla para ESQUERDA
		if((tecla == KeyEvent.VK_LEFT) && (!(isDireita()))  && (flag !=1))
		{
			setEsquerda(true);
			setCima(false);
			setBaixo(false);
			flag = 1;
		}
		
		// Pressionar tecla para CIMA
		if((tecla == KeyEvent.VK_UP) && (!(isBaixo())) && (flag !=1))
		{
			setCima(true);
			setDireita(false);
			setEsquerda(false);
			flag = 1;
			
		}
		
		// Pressionar tecla para BAIXO
		if((tecla == KeyEvent.VK_DOWN) && (!(isCima())) && (flag !=1) )
		{
			setBaixo(true);
			setDireita(false);
			setEsquerda(false);
			flag = 1;
	
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
	
	//metodo de executar som
    public void executaSom(String caminho) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(caminho));
            Clip clip = AudioSystem.getClip(); //instanciando 
            clip.open(audioInputStream); //abrir o arquivo
            clip.start(); //executar o arquivo de som
        } catch (Exception ex) {
            System.out.println("Erro ao executar SOM!");
            ex.printStackTrace();
        }
    }

	
/* 
 * **********************************************************************************************************
 * METODOS ACESSORES
 * **********************************************************************************************************
 */	

    public boolean isRastejando() {
		return rastejando;
	}

	public void setRastejando(boolean rastejando) {
		this.rastejando = rastejando;
	}
	
	public int getRastejos() {
		return rastejos;
	}
	
	public void setRastejos(int rastejos) {
		this.rastejos = rastejos;
	}
	
	public int getCoodX() {
		return coodX;
	}

	public void setCoodX(int coodX) {
		this.coodX = coodX;
	}

	public int getCoodY() {
		return coodY;
	}

	public void setCoodY(int coodY) {
		this.coodY = coodY;
	}
	
	public boolean isDireita() {
		return direita;
	}

	public void setDireita(boolean direita) {
		this.direita = direita;
	}

	public boolean isEsquerda() {
		return esquerda;
	}

	public void setEsquerda(boolean esquerda) {
		this.esquerda = esquerda;
	}

	public boolean isCima() {
		return cima;
	}

	public void setCima(boolean cima) {
		this.cima = cima;
	}

	public boolean isBaixo() {
		return baixo;
	}

	public void setBaixo(boolean baixo) {
		this.baixo = baixo;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	public int getTamanho() {
		return tam;
	}

	public void setTamanho(int tam) {
		this.tam = tam;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}