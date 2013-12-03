package org.jr.be.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jr.be.model.Editorial;
import org.jr.be.model.Contact;
import org.jr.be.model.Book;

import javax.persistence.EntityManager;


public class EditorialDTO {

	
	//Fields from entity:
	
	private Long id;
	private String legal_name;
	private String type;
	private String name;
	private String cuit_cuil;
    private AddressDTO address;
    private Long tel;
	private String email;
	private String web;
	private AuditDTO audit;
	private Set<EditorialBookDTO> books = new HashSet<EditorialBookDTO>();
        
    public void toDTO(Editorial editorial, EntityManager em){
                
                id =                 editorial.getId();
                legal_name =         editorial.getLegal_name();
                type =        		 editorial.getType().getName();
                name =               editorial.getName();
                cuit_cuil =          editorial.getCuit_cuil();
                tel =                editorial.getContact().getTel();
                email =              editorial.getContact().getEmail();
                web =        		 editorial.getContact().getWeb();
              
                AuditDTO auditDTO = new AuditDTO();
                auditDTO.toDTO(editorial.getAudit());
                audit =   auditDTO;
                
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.toDTO(  editorial.getAddress()  );
                address = addressDTO;
                
                List<Book> books_entities = em.createQuery(
                        "from Book as c where c.editorial = ?1 and c.deleted = false", Book.class)
                        .setParameter(1, editorial)
                        .getResultList();
                
                if (!books_entities.isEmpty()) {
              
               EditorialBookDTO book_dto;
               
               for (Book book : books_entities) {
                               book_dto = new EditorialBookDTO();         
                               book_dto.toDTO(book);
                               books.add( book_dto );
               }
               
               }
                
                
    }
    
    public Editorial toEntity() {
        
    	Editorial editorial = new Editorial();
        
        // If id is not null
        if (  id > 0  ) {
                editorial.setId(  id  );
        }
            
       
        //editorial.getType().getName(); should be added but will be discontinued
        editorial.setName(name);
        editorial.setCuit_cuil(cuit_cuil);
        editorial.setLegal_name(legal_name);
        
        Contact contact = new Contact();
        contact.setTel(tel);
        contact.setEmail(email);
        contact.setWeb(web);
        
        editorial.setContact(contact);
        editorial.setAddress(address.toEntity());
		//editorial.setAudit(audit.toEntity());
        
		editorial.setDeleted(false);
        
        return editorial;
}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLegal_name() {
		return legal_name;
	}

	public void setLegal_name(String legal_name) {
		this.legal_name = legal_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuit_cuil() {
		return cuit_cuil;
	}

	public void setCuit_cuil(String cuit_cuil) {
		this.cuit_cuil = cuit_cuil;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public Long getTel() {
		return tel;
	}

	public void setTel(Long tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public AuditDTO getAudit() {
		return audit;
	}

	public void setAudit(AuditDTO audit) {
		this.audit = audit;
	}

	public Set<EditorialBookDTO> getBooks() {
		return books;
	}

	public void setBooks(Set<EditorialBookDTO> books) {
		this.books = books;
	};

        

     
        

        
        
        
        
}
