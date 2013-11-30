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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
