package com.gmail.elisana.favarin.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando {
		ZERAR, SINAL, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA;	
	};
	
	private static final Memoria instancia = new Memoria();
	private final List<MemoriaObservador> observadores = new ArrayList<MemoriaObservador>();
	
	private TipoComando ultimaOperacao = null;
	private boolean substituir = false; 
	private String textoAtual = "";
	private String textoBuffer = "";
	
	//construtor da classe privado. Assim, não permite a classe ter mais de uma instancia
	private Memoria() {
		
	}

	public static Memoria getInstancia() {
		return instancia;
	}

	//metodo que registra todos os observadores
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}
	
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	public void processarComando(String texto) {
		
		TipoComando tipoComando = detectarTipoComando(texto);
		//System.out.println(tipoComando);
		
		if(tipoComando == null) {
			return;
		}else if(tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;			
		}else if(tipoComando == TipoComando.SINAL) {
			textoAtual = textoAtual.contains("-") ? textoAtual.replace("-", ""): "-"+textoAtual;			
		}else if(tipoComando == TipoComando.NUMERO
				|| tipoComando == TipoComando.VIRGULA) {
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
		}else {
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;			
		}
				
		//notificar todos os observadores. 
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}		
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",","."));
		double numeroAtual = Double.parseDouble(textoAtual.replace(",","."));
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		}else if(ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		}else if(ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		}else if(ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		}
		
		String texto = Double.toString(resultado).replace(".",",");
		//verifica se termina com ",0". Se sim, é um valor inteiro
		boolean inteiro = texto.endsWith(",0");
		return inteiro ? texto.replace(",0", "") : texto;
	}

	private TipoComando detectarTipoComando(String texto) {
		//se o usuário está digitando apenas zero, retorna nulo
		if(textoAtual.isEmpty() && texto == "0") {
			return null;
		}
		
		//verifica se o texto digitado pode ser convertido para número 
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {			
			//quando não for número...
			if("AC".equals(texto)) {
				return TipoComando.ZERAR;
			}else if("$".equals(texto)) {
				return TipoComando.SINAL;				
			}else if("/".equals(texto)) {
				return TipoComando.DIV;				
			}else if("*".equals(texto)) {
				return TipoComando.MULT;				
			}else if("+".equals(texto)) {
				return TipoComando.SOMA;				
			}else if("-".equals(texto)) {
				return TipoComando.SUB;				
			}else if("=".equals(texto)) {
				return TipoComando.IGUAL;				
			}else if(",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;				
			}
		}		
		return null;
	}

}
