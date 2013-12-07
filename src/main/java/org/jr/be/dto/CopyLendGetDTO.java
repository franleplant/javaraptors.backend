package org.jr.be.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jr.be.model.Author;
import org.jr.be.model.Copy;
import org.jr.be.model.Genre;
import org.jr.be.model.LendType;

public class CopyLendGetDTO {
	
	private long copy_id;
	private long book_id;
	private String title;
	private String editionNumber;
	private String editionCountry;
	
	private Set<CopyLendAuthorDTO> authors = new HashSet<CopyLendAuthorDTO>();
	private Set<GenreDTO> genres = new HashSet<GenreDTO>();
	private BookEditorialDTO editorial = new BookEditorialDTO();;
	private LocationDTO location = new LocationDTO();
	private CopyLendDateDTO date = new CopyLendDateDTO();
	private Set<String> lendTypes = new HashSet<String>();
	
	
	public void toDTO( Copy copy) {
		copy_id = copy.getId();
		book_id = copy.getBook().getId();
		title = copy.getBook().getTitle();
		editionNumber = copy.getBook().getEditionNumber();
		editionCountry = copy.getBook().getEditionCountry().getName();
		
		editorial.toDTO( copy.getBook().getEditorial() );
		location.toDTO(  copy.getLocation() );
		date.setToday( new Date() );
		
		
		Set<LendType> lt_entities = copy.getLendTypes();
		for ( LendType lt : lt_entities){
			lendTypes.add(  lt.getName()  );
		}
		
		CopyLendAuthorDTO authorDTO;
		for ( Author author : copy.getBook().getAuthors() ) {
			authorDTO = new CopyLendAuthorDTO();
			authorDTO.toDTO(  author  );
			authors.add(  authorDTO  );
		}
		
		
		GenreDTO genreDTO;
		for ( Genre genre : copy.getBook().getGenres() ) {
			genreDTO = new GenreDTO();
			genreDTO.toDTO(  genre  );
			genres.add(  genreDTO  );
		}
	}
	
	
	public long getCopy_id() {
		return copy_id;
	}
	public void setCopy_id(long copy_id) {
		this.copy_id = copy_id;
	}
	public long getBook_id() {
		return book_id;
	}
	public void setBook_id(long book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEditionNumber() {
		return editionNumber;
	}
	public void setEditionNumber(String editionNumber) {
		this.editionNumber = editionNumber;
	}
	public String getEditionCountry() {
		return editionCountry;
	}
	public void setEditionCountry(String editionCountry) {
		this.editionCountry = editionCountry;
	}
	public Set<CopyLendAuthorDTO> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<CopyLendAuthorDTO> authors) {
		this.authors = authors;
	}
	public Set<GenreDTO> getGenres() {
		return genres;
	}
	public void setGenres(Set<GenreDTO> genres) {
		this.genres = genres;
	}
	public BookEditorialDTO getEditorial() {
		return editorial;
	}
	public void setEditorial(BookEditorialDTO editorial) {
		this.editorial = editorial;
	}
	public LocationDTO getLocation() {
		return location;
	}
	public void setLocation(LocationDTO location) {
		this.location = location;
	}
	public CopyLendDateDTO getDate() {
		return date;
	}
	public void setDate(CopyLendDateDTO date) {
		this.date = date;
	}


	public Set<String> getLendTypes() {
		return lendTypes;
	}


	public void setLendTypes(Set<String> lendTypes) {
		this.lendTypes = lendTypes;
	}

}
