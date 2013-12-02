package org.jr.be.dto;

import org.jr.be.model.Editorial;


public class EditorialDTO {

	
	//Fields from entity:
	
	private Long id;
	private String legal_name;
	private String type;
	private String name;
	private String cuit;
    private AddressDTO address;
    private ContactDTO contact;
    private AuditDTO audit;
        
    public void toDTO(Editorial editorial){
                
                id =                 editorial.getId();
                legal_name =         editorial.getLegal_name();
                type =        		 editorial.getType().getName();
                name =               editorial.getName();
                //cuit =               editorial.getCuit();
              
                AuditDTO auditDTO = new AuditDTO();
                auditDTO.toDTO(editorial.getAudit());
                audit =   auditDTO;
                
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.toDTO(  editorial.getAddress()  );
                address = addressDTO;
                
    }
    
    public Editorial toEntity() {
        
    	Editorial editorial = new Editorial();
        
        // If id is not null
        if (  id > 0  ) {
                editorial.setId(  id  );
        }
            
        editorial.setLegal_name(legal_name);
        //editorial.getType().getName(); should be added but will be discontinued
        editorial.setName(name);
        editorial.setName(cuit);
        
		editorial.setAddress(address.toEntity());
		//editorial.setAudit(audit.toEntity());
		editorial.setDeleted(false);
        
        return editorial;
};

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

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	public AuditDTO getAudit() {
		return audit;
	}

	public void setAudit(AuditDTO audit) {
		this.audit = audit;
	}
        
        

     
        

        
        
        
        
}
