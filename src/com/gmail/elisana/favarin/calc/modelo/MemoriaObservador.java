package com.gmail.elisana.favarin.calc.modelo;

@FunctionalInterface
public interface MemoriaObservador {
	//todo método dentro de interface é public
	void valorAlterado(String novoValor);
}
