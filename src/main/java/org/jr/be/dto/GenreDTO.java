package org.jr.be.dto;


import org.jr.be.model.Genre;

public class GenreDTO {
	
	private long id;
	
	private String name;
	
	public void toDTO(  Genre genre  ){
		name = genre.getName();
		id = genre.getId();
		
	}
	
	public Genre toEntity() {
		Genre genre = new Genre();		

		genre.setName(name);
		genre.setDeleted(false);
				
		return genre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
