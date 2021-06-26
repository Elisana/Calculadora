package com.gmail.elisana.favarin.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.elisana.favarin.calc.modelo.Memoria;
import com.gmail.elisana.favarin.calc.modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador{
	
	private final JLabel label;

	//cria construtor
	public Display(){
		//o Display precisa se registrar para ser notificado pelo observador
		Memoria.getInstancia().adicionarObservador(this);
		
		setBackground(new Color(46,49,50));
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		label.setForeground(Color.WHITE); //define a cor da fonte
		label.setFont(new Font("courier",Font.PLAIN, 30)); //define a fonte
		
		//gerenciador de leiaute para alinhar o texto do display a direita
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 20));
		add(label);
	}

	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);		
	}
	
	
}
