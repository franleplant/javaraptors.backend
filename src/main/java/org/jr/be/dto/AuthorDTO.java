package org.jr.be.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jr.be.model.Author;
import org.jr.be.model.Book;

import javax.persistence.EntityManager;


public class AuthorDTO {

       
        
        private Long id;
        private String type;
        private String nick;
        private Date birth;
        private String isni;
        private AuditDTO audit;
        private String comments;
        private Set<AuthorBookDTO> books = new HashSet<AuthorBookDTO>();
        
    public void toDTO(Author author, EntityManager em){
                
                id = author.getId();
                type =  author.getType().getName();
                nick = author.getNick();
                
                //Casting date to fit into birth with correct format
             
                birth = author.getBirth();
                
                isni = author.getIsni();
                isni = author.getComments();
              
                AuditDTO auditDTO = new AuditDTO();
                auditDTO.toDTO(author.getAudit());
                audit = auditDTO;
                
                List<Book> books_entities = em.createQuery(
                        "from Book as c where c.author = ?1 and c.deleted = false", Book.class)
                        .setParameter(1, author)
                        .getResultList();
                
                if (!books_entities.isEmpty()) {
              
               AuthorBookDTO book_dto;
               
               for (Book book : books_entities) {
                               book_dto = new AuthorBookDTO();
                               book_dto.toDTO(book);
                               books.add( book_dto );
               }
               
               }
                
                
    }
    
    public Author toEntity() {
        
    	Author author = new Author();
        
        // If id is not null
        if ( id > 0 ) {
        	author.setId( id );
        }
         
        author.getType().setName(type);
        author.setNick(nick);
        author.setBirth(birth);
        author.setIsni(isni);
        author.setComments(comments);
        author.setDeleted(false);
        
        return author;
}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getIsni() {
		return isni;
	}

	public void setIsni(String isni) {
		this.isni = isni;
	}

	public AuditDTO getAudit() {
		return audit;
	}

	public void setAudit(AuditDTO audit) {
		this.audit = audit;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<AuthorBookDTO> getBooks() {
		return books;
	}

	public void setBooks(Set<AuthorBookDTO> books) {
		this.books = books;
	}

    
       
     
        

        
        
        
        
}