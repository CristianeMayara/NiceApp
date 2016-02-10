package br.ufc.mdcc.nicempos.model.vo;

import java.io.Serializable;

public class Vote implements Serializable {

	protected int placeId;
	protected int nVotesRating1;
	protected int nVotesRating2;
	protected int nVotesRating3;
	protected int nVotesRating4;
	protected int nVotesRating5;
	protected int nVotesPlace;
	
	public Vote (int placeId, int n1, int n2, int n3, int n4, int n5, int n){
		this.placeId = placeId;
		this.nVotesRating1 = n1;
		this.nVotesRating2 = n2;
		this.nVotesRating3 = n3;
		this.nVotesRating4 = n4;
		this.nVotesRating5 = n5;
		this.nVotesPlace = n;
	}
	
	public void setPlaceId(int placeId){
		this.placeId = placeId;
	}
	public int getPlaceId(){
		return this.placeId;
	}
	
	public void setNVotesRating1(int n){
		this.nVotesRating1 = n;
	}
	public void setNVotesRating2(int n){
		this.nVotesRating2 = n;
	}
	public void setNVotesRating3(int n){
		this.nVotesRating3 = n;
	}
	public void setNVotesRating4(int n){
		this.nVotesRating4 = n;
	}
	public void setNVotesRating5(int n){
		this.nVotesRating5 = n;
	}
	public void setNVotesPlace(int n){
		this.nVotesPlace = n;
	}
	
	public int getNVotesRating1(){
		return this.nVotesRating1;
	}
	public int getNVotesRating2(){
		return this.nVotesRating2;
	}
	public int getNVotesRating3(){
		return this.nVotesRating3;
	}
	public int getNVotesRating4(){
		return this.nVotesRating4;
	}
	public int getNVotesRating5(){
		return this.nVotesRating5;
	}
	public int getNVotesPlace(){
		return this.nVotesPlace;
	}
}
