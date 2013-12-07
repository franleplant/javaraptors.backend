package org.jr.be.dto;

import java.util.HashSet;
import java.util.Set;

import org.jr.be.model.Author;
import org.jr.be.model.Book;

public class ReportTopBookDTO {
	
	private long id;
	private String title;
	private String editionNumber;
	private BookEditorialDTO editorial = new BookEditorialDTO();
	private long lend_number;
	private Set<BookAuthorDTO> authors = new HashSet<BookAuthorDTO>();
	
	
	public void toDTO( Book book ) {
        
        id =   book.getId();
        title = book.getTitle();
        editionNumber = book.getEditionNumber();
        editorial.toDTO(book.getEditorial());
        
        
		Set<Author> author_entities = book.getAuthors();
		BookAuthorDTO author_dto;
		for (Author a : author_entities) {
			author_dto = new BookAuthorDTO();
			author_dto.toDto(  a  );
			authors.add(  author_dto  );
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public BookEditorialDTO getEditorial() {
		return editorial;
	}
	public void setEditorial(BookEditorialDTO editorial) {
		this.editorial = editorial;
	}
	public Set<BookAuthorDTO> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<BookAuthorDTO> authors) {
		this.authors = authors;
	}
	public long getLend_number() {
		return lend_number;
	}
	public void setLend_number(long lend_number) {
		this.lend_number = lend_number;
	}
	
	
}
