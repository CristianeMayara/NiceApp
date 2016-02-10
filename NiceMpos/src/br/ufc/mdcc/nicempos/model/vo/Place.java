package br.ufc.mdcc.nicempos.model.vo;

import java.io.Serializable;

public class Place implements Serializable {

	protected int id;
	protected String name;
	protected double lat;
	protected double lon;
	protected int imageId;
	
	public Place (String name, double lat, double lon, int id){
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.imageId = id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setLat(){
		this.lat = lat;
	}
	
	public double getLat(){
		return this.lat;
	}
	
	public void setLon(double lon){
		this.lon = lon;
	}
	
	public double getLon(){
		return this.lon;
	}
	
	public void setImageId(int imageId){
		this.imageId = imageId;
	}
	
	public int getImageId(){
		return this.imageId;
	}
	
}
