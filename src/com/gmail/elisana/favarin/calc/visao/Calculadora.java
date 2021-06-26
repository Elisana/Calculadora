package com.gmail.elisana.favarin.calc.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame{

	//construtor
	public Calculadora() {
		
		organizarLayout();
		
		setSize(232,322); //seta tamanho da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //termina a aplicação ao fechar a janela
		setLocationRelativeTo(null);//centraliza a janela
		setVisible(true);
	}
	
	private void organizarLayout() {
		setLayout(new BorderLayout());
		
		Display display = new Display();
		display.setPreferredSize(new Dimension(233,60));
		add(display, BorderLayout.NORTH);//coloca o display no norte da tela
		
		Teclado teclado = new Teclado();
		add(teclado, BorderLayout.CENTER);//coloca o teclado no centro da tela
		
	}

	public static void main(String[] args) {
		//chama o construtor
		new Calculadora();
	}

}
