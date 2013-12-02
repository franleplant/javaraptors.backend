package org.jr.be.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.jr.be.model.Author;
import org.jr.be.model.Book;
import org.jr.be.model.Copy;
import org.jr.be.model.Genre;



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
		type = "book";
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
	
	public Book toEntity( EntityManager em) {
		Book book = new Book();
		
		book.setId(id);
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setEditionNumber(editionNumber);
		book.setSummary(summary);
		book.setImg(img);
		book.setLang(lang);
		book.setVal(val);
		book.setPrice(price);
		book.setComments(comments);
		
    			
		return book;
	}
	


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<BookAuthorDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<BookAuthorDTO> authors) {
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

	public Set<BookCopyDTO> getCopies() {
		return copies;
	}

	public void setCopies(Set<BookCopyDTO> copies) {
		this.copies = copies;
	}

	public AuditDTO getAudit() {
		return audit;
	}

	public void setAudit(AuditDTO audit) {
		this.audit = audit;
	}
	
	
	
	
	
	

}
