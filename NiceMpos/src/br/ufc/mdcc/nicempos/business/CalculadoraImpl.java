package br.ufc.mdcc.nicempos.business;

import br.ufc.mdcc.nicempos.model.vo.Input;

public final class CalculadoraImpl implements ICalculadora {

	public int calculatesClassificationPlace (Input input) {
		System.out.println("Calculating votes..");
		
		int nVotesRating1 = input.getVote().getNVotesRating1();
		int nVotesRating2 = input.getVote().getNVotesRating2();
		int nVotesRating3 = input.getVote().getNVotesRating3();
		int nVotesRating4 = input.getVote().getNVotesRating4();
		int nVotesRating5 = input.getVote().getNVotesRating5();
		
		// Adding current vote
		switch(input.getCurrentVoteId()){
			case 1: nVotesRating1++; break;
			case 2: nVotesRating2++; break;
			case 3: nVotesRating3++; break;
			case 4: nVotesRating4++; break;
			case 5: nVotesRating5++; break;
		}
		
		// Verify the biggest amount of votes
		
		int biggest = -1; // highest number of votes
		int biggestId = 0; // classification with the highest number of votes
		
		if (nVotesRating1 >= biggest) {biggest = nVotesRating1; biggestId = 1;}
		if (nVotesRating2 >= biggest) {biggest = nVotesRating2; biggestId = 2;}
		if (nVotesRating3 >= biggest) {biggest = nVotesRating3; biggestId = 3;}
		if (nVotesRating4 >= biggest) {biggest = nVotesRating4; biggestId = 4;}
		if (nVotesRating5 >= biggest) {biggest = nVotesRating5; biggestId = 5;}
		
		System.out.println("Returning classification of the place: " + biggestId);
		
		return biggestId;
	}
	
	public int chosenAnswerTwo(Input input) {
		return 2;
	}
	
	public int chosenAnswerThree(Input input) {
		return 3;
	}
	
	public int chosenAnswerFour(Input input) {
		return 4;
	}
	
	public int chosenAnswerFive(Input input) {
		return 5;
	}
	
}
