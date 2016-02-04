package br.ufc.mdcc.nicempos;

import java.io.Serializable;

public final class Input implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int placeId, voteId;

	public Input() {
	}
	
	public Input(int placeId, int voteId) {
		this.placeId = placeId;
		this.voteId = voteId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public int getVoteId() {
		return voteId;
	}
}
