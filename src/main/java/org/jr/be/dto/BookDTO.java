package org.jr.be.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.jr.be.model.Book;
import org.jr.be.model.Author;
import org.jr.be.model.Genre;
import org.jr.be.model.Copy;



public class BookDTO {
	
	private long id;
	private String isbn;
	private String type;
	private String title;
	private String editionNumber;
	private String editionCountry;	
	private String summary;	
	private String img;
	private String lang;
	private double val;
	private String price;
	private String comments;
	
	private Set<BookAuthorDTO> authors = new HashSet<BookAuthorDTO>();
	private Set<GenreDTO> genres = new HashSet<GenreDTO>();	
	private BookEditorialDTO editorial;
	private Set<BookCopyDTO> copies = new HashSet<BookCopyDTO>();
	
	private AuditDTO audit;
	

	public void toDTO(Book book, EntityManager em) {
		id = book.getId();
		isbn = book.getIsbn();
		type = book.getType().getName();
		title = book.getTitle();
		editionNumber = book.getEditionNumber();
		editionCountry = book.getEditionCountry().getName();
		summary = book.getSummary();
		img = book.getImg();
		lang = book.getLang();
		val = book.getVal();
		price = book.getPrice();
		comments = book.getComments();
		
    	

		Set<Author> author_entities = book.getAuthors();
		BookAuthorDTO author_dto;
		for (Author a : author_entities) {
			author_dto = new BookAuthorDTO();
			author_dto.toDto(  a  );
			authors.add(  author_dto  );
		}
		
		Set<Genre> genre_entities = book.getGenres();
		GenreDTO genre_dto;
		for (Genre g : genre_entities){
			genre_dto = new GenreDTO();
			genre_dto.toDTO(  g  );
			genres.add(  genre_dto  );
		}
		
		BookEditorialDTO editorial_dto = new BookEditorialDTO();		
		editorial_dto.toDTO(  book.getEditorial()  );
		editorial = editorial_dto;
		
    	
	    List<Copy> copies_entities = em.createQuery(
	     	    "from Copy as c where c.book = ?1 and c.deleted = false", Copy.class)
	     	    .setParameter(1, book)
	     	    .getResultList();
	    
	    BookCopyDTO copy_dto;	     
	 	for (Copy copy : copies_entities) {
	 		copy_dto = new BookCopyDTO();  	
	 		copy_dto.toDTO(copy, em);
	        copies.add(  copy_dto  );
	 	}
		
		
		AuditDTO audit_dto = new AuditDTO();
		audit_dto.toDTO(  book.getAudit()  );
		audit = audit_dto;
	}
	
	public Book toEntity() {
		Book book = new Book();
		
		
		
		return book;
	}
	
	
	
	
	
	

}
