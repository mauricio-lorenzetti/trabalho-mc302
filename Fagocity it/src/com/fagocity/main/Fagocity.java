package com.fagocity.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class Fagocity extends Canvas implements Runnable {
	private static final long serialVersionUID = 2929390961970147223L;
	
	/* Tamanho da janela do jogo */
	public static final int LARGURA = 800, ALTURA = 600;
	
	private Thread thread;
	private  boolean rodando = false;
	private Handler handler;
	
	
	public Fagocity() {
		/* Cria o handler */
		handler = new Handler();
		
		/* Cria o objeto que recebe as informa��es do teclado */
		this.addKeyListener(new Input(handler));
		
		/* Cria a janela */
		new Janela(ALTURA, LARGURA, "Fagocity It!", this);
		
		/* Cria o jogador e o coloca na tela */
		handler.addObjeto(new Jogador(LARGURA/2 -32/2, ALTURA/2 -32/2, ID.Jogador ));
		handler.addObjeto(new Inimigo(LARGURA/2 -32/2, ALTURA/2 -32/2, ID.Inimigo ));

		
	}
	
	/**********/
	/* Thread */
	/**********/
	/* M�todo que inicia o thread */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		rodando = true;
	}
	
	/* M�todo que interrompe o thread */
	public synchronized void stop() {
		try {
			thread.join();
			rodando = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* M�todo com as a��es de atualizar, renderizar
	 * e mostrar FPS realizadas pelo thread */
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(rodando){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(rodando)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	/* Chama m�todo para atualizar informa��es de objetos */
	private void tick() {
		handler.tick();
	}
	
	/* M�todo que renderiza */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if( bs == null ) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, LARGURA, ALTURA);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		
		new Fagocity();

	}

}