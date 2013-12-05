package org.jr.be.dto;

import org.jr.be.model.Book;

public class AuthorBookDTO {
        
private Long id;
private String title;


public void toDTO(Book book){
    
    id = book.getId();
    title = book.getTitle();
    
}

//toEntity() method is not needed.

public Long getId() {
        return id;
}
public void setId(Long id) {
        this.id = id;
}
public String getTitle() {
        return title;
}
public void setTitle(String title) {
        this.title = title;
}




}

