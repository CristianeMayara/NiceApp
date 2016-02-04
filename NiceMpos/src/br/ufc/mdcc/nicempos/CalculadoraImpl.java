package br.ufc.mdcc.nicempos;

import android.util.Log;

public final class CalculadoraImpl implements ICalculadora {
	
	int nVotesTotal;

	public int chosenAnswerOne(Input input) {
		Log.i("debuggerNice","resposta 1 computada!");
		
		input.getPlaceId();
		input.getVoteId();
		// vai somar 1 voto na valiavel
		// vai retornar o idRating que tem mais votos do lugar
		// consuta no banco
		return 1;
	}
	
	public int chosenAnswerTwo(Input input) {
		Log.i("debuggerNice","resposta 2 computada!");
		return 2;
	}
	
	public int chosenAnswerThree(Input input) {
		Log.i("debuggerNice","resposta 3 computada!");
		return 3;
	}
	
	public int chosenAnswerFour(Input input) {
		Log.i("debuggerNice","resposta 4 computada!");
		return 4;
	}
	
	public int chosenAnswerFive(Input input) {
		Log.i("debuggerNice","resposta 5 computada!");
		return 5;
	}
	
}
