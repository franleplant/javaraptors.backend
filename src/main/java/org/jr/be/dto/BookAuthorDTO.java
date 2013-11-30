package org.jr.be.dto;

import org.jr.be.model.Author;


public class BookAuthorDTO {

	
	private long id;
	private String nick;
	
	
	public void toDto( Author author) {
		id = author.getId();
		nick = author.getNick();
	}
	
	public Author toEntity() {
		Author author = new Author();
		
		
		
		return author;
	}
}
