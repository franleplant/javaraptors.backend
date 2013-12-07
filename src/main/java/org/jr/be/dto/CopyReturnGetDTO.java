package org.jr.be.dto;

import java.util.HashSet;
import java.util.Set;

import org.jr.be.model.Author;
import org.jr.be.model.Genre;
import org.jr.be.model.Lend;

public class CopyReturnGetDTO {
	
	private long copy_id;
	private long book_id;
	private String title;
	private String editionNumber;
	private String editionCountry;
	private String lendType;	
	private String lend_comments;
	
	
	private BookEditorialDTO editorial = new BookEditorialDTO();;
	private LocationDTO location = new LocationDTO();
	
	private Set<CopyLendAuthorDTO> authors = new HashSet<CopyLendAuthorDTO>();
	private Set<GenreDTO> genres = new HashSet<GenreDTO>();


	private CopyReturnDateDTO date = new CopyReturnDateDTO();	
	private CopyReturnAffiliateDTO affiliate = new CopyReturnAffiliateDTO();
	
	
	
	public void toDTO( Lend lend ) {
		copy_id = lend.getCopy().getId();
		book_id = lend.getCopy().getBook().getId();
		title = lend.getCopy().getBook().getTitle();
		editionNumber = lend.getCopy().getBook().getEditionNumber();
		editionCountry = lend.getCopy().getBook().getEditionCountry().getName();
		lendType = lend.getLendType().getName();
		lend_comments = lend.getComments();
		
		
		editorial.toDTO( lend.getCopy().getBook().getEditorial() );
		location.toDTO(  lend.getCopy().getLocation() );
		
		

		
		CopyLendAuthorDTO authorDTO;
		for ( Author author : lend.getCopy().getBook().getAuthors() ) {
			authorDTO = new CopyLendAuthorDTO();
			authorDTO.toDTO(  author  );
			authors.add(  authorDTO  );
		}
		
		
		GenreDTO genreDTO;
		for ( Genre genre : lend.getCopy().getBook().getGenres() ) {
			genreDTO = new GenreDTO();
			genreDTO.toDTO(  genre  );
			genres.add(  genreDTO  );
		}
		
		
		date.toDTO(  lend  );
		
		affiliate.toDTO(  lend.getAffiliate()  );
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



	public String getLendType() {
		return lendType;
	}



	public void setLendType(String lendType) {
		this.lendType = lendType;
	}



	public String getLend_comments() {
		return lend_comments;
	}



	public void setLend_comments(String lend_comments) {
		this.lend_comments = lend_comments;
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



	public CopyReturnDateDTO getDate() {
		return date;
	}



	public void setDate(CopyReturnDateDTO date) {
		this.date = date;
	}



	public CopyReturnAffiliateDTO getAffiliate() {
		return affiliate;
	}



	public void setAffiliate(CopyReturnAffiliateDTO affiliate) {
		this.affiliate = affiliate;
	}
}
