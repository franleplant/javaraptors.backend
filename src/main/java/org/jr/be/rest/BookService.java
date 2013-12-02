package org.jr.be.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jr.be.dto.BookAuthorDTO;
import org.jr.be.dto.BookCopyDTO;
import org.jr.be.dto.BookDTO;
import org.jr.be.dto.BookEditorialDTO;
import org.jr.be.dto.BookSearchDTO;
import org.jr.be.dto.GenreDTO;
import org.jr.be.model.Author;
import org.jr.be.model.Book;
import org.jr.be.model.Copy;
import org.jr.be.model.Country;
import org.jr.be.model.Editorial;
import org.jr.be.model.Genre;
import org.jr.be.model.LendType;
import org.jr.be.model.Location;
import org.jr.be.util.JsonResponseMsg;

@Path("/book")
public class BookService {
           
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
        
    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BookSearchDTO search(@QueryParam("q") String query) {
    	
    	String q = '%' + query + '%';
    	
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	
        // Search by title
        List<Book> results = entityManager.createQuery(
        	    "FROM Book as b WHERE upper(b.title) LIKE ?1", Book.class)
        	    .setParameter(1, q.toUpperCase() )
        	    .getResultList();	
    	
    	
    	
    	
    	List<BookDTO> resultsDTO = new ArrayList<BookDTO>();
    	BookDTO bookDTO;
    	
    	//Create the dto
    	for (Book book : results){
    		bookDTO = new BookDTO();
    		
            if (  book.isDeleted()  ) {
            	break;
            }    

            

	        // Transfer all the data into the DTO
            bookDTO.toDTO(  book, entityManager  );
	        resultsDTO.add(bookDTO);
    	}
    	
    	
    	
    	entityManager.close();
    	
    	
    	
    	BookSearchDTO response = new BookSearchDTO(); 	
    	response.setPage_number(1);
    	response.setPage_total(1);
    	response.setResults(resultsDTO);
    	
    	return response;
    }
    
    
    
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookDTO getOne(@PathParam("id") Long id) {
            
    	Book book = null;
    	BookDTO dto = new BookDTO();
            
        EntityManager entityManager = entityManagerFactory.createEntityManager();             

        try {
        	book = entityManager.find(Book.class, id);
        	
        	//Has it found any entity?
        	book.getId();
        		
            
            dto.toDTO(  book, entityManager  );
        	
        	
        } catch(NullPointerException ex) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
        } finally {
        	entityManager.close();
        }      
        
       
        if (  book.isDeleted()  ) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        

        
        
        return dto;      
    }
        
    
    
    public Country fetchCreateCountry( EntityManager em, BookDTO dto) {
    	Country country;
    	
    	try {
    		
        	country = em.createQuery(
    	     	    "from Country as c where c.name = ?1", Country.class)
    	     	    .setParameter(1, dto.getEditionCountry())
    	     	    .getSingleResult();  
    	} catch (NoResultException e) {
    		
    		country = new Country();
    		country.setName(  dto.getEditionCountry()  );
    	}
 
    	return country;
    }
    
       
    
    public Editorial fetchCreateEditorial(EntityManager em, BookEditorialDTO editorialDTO) {
    	
    	Editorial editorial = new Editorial();
    	
		if ( editorialDTO.getId() != 0) {
			
			try {
				editorial = em.find(Editorial.class, editorialDTO.getId());
				
			} catch(NoResultException ex) {
				//Something really bad happened
			}

		} else {
			editorial = editorialDTO.toEntity();
			
		}
		
		return editorial;
    }
    
    public Author fetchCreateAuthor(EntityManager em, BookAuthorDTO authorDTO) {
    	Author author = new Author();
    	
    	// Check if the author exist
		if ( authorDTO.getId() != 0) {
			
			try {
				
				author = em.find(Author.class, authorDTO.getId());
				
			} catch(NoResultException ex) {
			
					//Something really bad happened
			}

		} else {
        	// Author does not exist, prepare for the cascade creation
			author = authorDTO.toEntity();
						
			em.persist(author);			
		}
    	
    	
    	return author;
    }
    
    public Genre fetchCreateGenre(  EntityManager em, GenreDTO genreDTO) {
    	Genre genre = new Genre();
    	
    	if ( genreDTO.getId() != 0) {
    	
			try {
				genre = em.find(Genre.class, genreDTO.getId());
		    		    	
			} catch(NoResultException ex) {
		    	// Something really bad happened
			}
		
    	} else {
			genre = genreDTO.toEntity();
			em.persist(genre);	
		}
    	    	
    	return genre;
    }
    
    public void createCopy( EntityManager em, BookCopyDTO copyDTO, Book book) {
		
    	
    	//COPY's LOCATION
    	Location location = new Location();
    	
    	//If the location exist then fetch it
		if ( copyDTO.getLocation().getId() != 0) {
			
			try {
				
				location = em.find(Location.class, copyDTO.getLocation().getId()  );

			} catch(NoResultException ex) {
			
					//Something really bad happened
			}

		} else {
			// The location does not exist, create one
			location = copyDTO.getLocation().toEntity();
			em.persist(location);			
		}	
		
		
		
		Copy copy = copyDTO.toEntity();
		copy.setLocation(  location  );
		copy.setBook(book);
		
		copy.getAudit().setCreateDate(  new Date()  );
		//copy.getAudit().setCreateUser( sesion user);
		
		for (String lendType_name : copyDTO.getLendTypes() ) {
	        try {
	    	    LendType lendType_entity = em.createQuery(
	    	     	    "from LendType as t where t.name = ?1", LendType.class)
	    	     	    .setParameter(1, lendType_name)
	    	     	    .getSingleResult();
	   
				copy.addLendType(lendType_entity);
	 	
	        } catch(NoResultException ex) {
	        	
	        	//If this happens, something very messy is happening
	        	//Ignoring for now
	        } 	

		}
		
		
		em.persist(copy);
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg create(BookDTO dto) throws Exception {
     	
    	
    	u.begin();
    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
    	
    	
    	Book book = dto.toEntity(entityManager);
    	book.getAudit().setCreateDate(  new Date()  );
    	// When login is done do this:
    	//audit.setCreateUser(createUser);
    	book.setDeleted(false);
    	
    	book.setEditionCountry(  fetchCreateCountry(entityManager, dto)  );
    	
    	book.setEditorial(  fetchCreateEditorial(entityManager, dto.getEditorial())  );
		
		
		for (  BookAuthorDTO authorDTO : dto.getAuthors()  ){
			book.addAuthor(  fetchCreateAuthor(entityManager, authorDTO)  );
		}
		
		for (  GenreDTO genreDTO : dto.getGenres()  ) {
			book.addGenre (  fetchCreateGenre(entityManager, genreDTO)  );
		}
				 
		

			

    	
    	
    	entityManager.merge(book);
    	entityManager.flush();
    	
    	
    	Book created_book = entityManager.createQuery(
	     	    "from Book as b where b.title = ?1 and b.editorial = ?2", Book.class)
	     	    .setParameter(1, book.getTitle())
	     	    .setParameter(2, book.getEditorial())
	     	    .getSingleResult();
    	System.out.println(  created_book.getId()  );
    	
    	
    	
    	
		for (BookCopyDTO copyDTO : dto.getCopies()) {
			// Persist the new copy
			createCopy(entityManager, copyDTO, created_book);		
		}
    	
		
		
    	
    	u.commit();
        entityManager.close();
        
    	return new JsonResponseMsg("ok", "oohhh yeeeeaaaaaaaah!");
    };
        
        
        @POST
        @Path("/{id:[0-9][0-9]*}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg edit(Book book, @PathParam("id") Long id) throws Exception {
        	
        	
        	book.setDeleted(false);
      
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	book.getAudit().setEditDate(date);
        	
        	// When login is done do this:
        	//affiliate.getPerson().getAudit().setEditUser();
        	
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	entityManager.merge(book);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "oohhh yeeeeaaaaaaaah!");
        };
        
        
        @DELETE
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg delete(@PathParam("id") Long id) throws Exception {
        	
        	
        	Book book = null;     
	        
        	u.begin();
	        EntityManager entityManager = entityManagerFactory.createEntityManager();             
	        
	        book = entityManager.find(Book.class, id);
	        
	        book.setDeleted(true);
	        
	        book.getAudit().setDeleteDate(new Date());
	        
	        //Finish this when loggin is working
	        //book.getAudit().setDeleteUser(deleteDate);
        	
        	entityManager.merge(book);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "oohhh yeeeeaaaaaaaah!");
        };
        
}