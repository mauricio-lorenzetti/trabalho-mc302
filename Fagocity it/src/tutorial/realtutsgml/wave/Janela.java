package tutorial.realtutsgml.wave;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Janela extends Canvas {

	private static final long serialVersionUID = -7847704263629313879L;
	
	/* Método que configura a janela do jogo */
	public Janela (int altura, int largura, String titulo, Fagocity jogo) {
		JFrame frame = new JFrame(titulo);
		
		/* Atributos da janela */
		frame.setPreferredSize(new Dimension(largura, altura));
		frame.setMaximumSize(new Dimension(largura, altura));
		frame.setMinimumSize(new Dimension(largura, altura));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//botao de fechar encerra o jogo
		frame.setResizable(false);//janela tem tamanho unico
		frame.setLocationRelativeTo(null);//janela centralizada
		frame.add(jogo);
		frame.setVisible(true);
		jogo.start();
	}

}