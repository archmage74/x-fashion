package com.xfashion.server.statistic;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.SizeStatisticDTO;

@PersistenceCapable
public class SizeStatistic {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String size;
	
	@Persistent
	private Integer pieces;

	public Key getKey() {
		return key;
	}
	
	public SizeStatistic(String size) {
		this.size = size;
		this.pieces = 0;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	public SizeStatisticDTO createDTO() {
		SizeStatisticDTO dto = new SizeStatisticDTO();
		dto.setSize(size);
		dto.setPieces(pieces);
		return dto;
	}
	
}
