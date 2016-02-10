package br.ufc.mdcc.nicempos.model.vo;

import java.io.Serializable;

public final class Input implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vote vote;
	private int currentVoteId;

	public Input() {
	}
	
	public Input (Vote vote, int curVoteId){
		this.vote = vote;
		this.currentVoteId = curVoteId;
	}
	
	public Vote getVote() {
		return this.vote;
	}
	
	public int getCurrentVoteId() {
		return this.currentVoteId;
	}
	
}
