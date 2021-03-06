package org.jr.be.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.jr.be.dto.LocationDTO;
import org.jr.be.model.Author;
import org.jr.be.model.Book;
import org.jr.be.model.Copy;
import org.jr.be.model.Country;
import org.jr.be.model.Editorial;
import org.jr.be.model.Genre;
import org.jr.be.model.LendType;
import org.jr.be.model.Location;
import org.jr.be.model.User;
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
    		
            if (  !book.isDeleted()  ) {
    	        // Transfer all the data into the DTO
                bookDTO.toDTO(  book, entityManager  );
    	        resultsDTO.add(bookDTO);
            }
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
    	
    	if (dto.getEditionCountry() == null) {
    		return null;
    	};
    	
    	try {
    		
        	country = em.createQuery(
    	     	    "from Country as c where c.name = ?1", Country.class)
    	     	    .setParameter(1, dto.getEditionCountry())
    	     	    .getSingleResult();  
    	} catch (NoResultException e) {
    		
    		country = new Country();
    		country.setName(  dto.getEditionCountry()  );
    		em.persist(country);
    	}
 
    	return country;
    }
    
       
    
    public Editorial fetchCreateEditorial(EntityManager em, BookEditorialDTO editorialDTO) {
    	
    	Editorial editorial = new Editorial();
    	
    	
    	
    	try {
    		
    		
        	editorial = em.createQuery(
    	     	    "from Editorial as e where e.name = ?1 and  e.deleted = false", Editorial.class)
    	     	    .setParameter(1, editorialDTO.getName())
    	     	    .getSingleResult();  
        	
 
      
    	} catch (NoResultException e) {
    		
    		
			editorial = editorialDTO.toEntity();
			em.persist(  editorial  );

    	}  	
    	
		
		return editorial;
    }
    
    public Author fetchCreateAuthor(EntityManager em, BookAuthorDTO authorDTO) {
    	Author author = new Author();
    	
    	
    	
    	try {
    		
    		
        	author = em.createQuery(
    	     	    "from Author as a where a.nick = ?1 and  a.deleted = false", Author.class)
    	     	    .setParameter(1, authorDTO.getNick())
    	     	    .getSingleResult();  
        	
 
      
    	} catch (NoResultException e) {
    		
    		
			author = authorDTO.toEntity();					
			em.persist(author);	

    	}  	
    	
		
		
    	return author;
    }
    
    public Genre fetchCreateGenre(  EntityManager em, GenreDTO genreDTO) {
    	Genre genre = new Genre();
    	

    	
    	try {
    		
    		
        	genre = em.createQuery(
    	     	    "from Genre as g where g.name = ?1 and g.deleted = false", Genre.class)
    	     	    .setParameter(1, genreDTO.getName())
    	     	    .getSingleResult();  
        	
 
      
    	} catch (NoResultException e) {
    		
    		
    		genre = genreDTO.toEntity();
    		em.persist(genre);

    	}
    	
    	return genre;
    }
    
    public Location fetchCreateLocation (  EntityManager em, LocationDTO locationDTO) {
    	//COPY's LOCATION
    	Location location = new Location();
    	

    	try {
    		
    		
        	location = em.createQuery(
    	     	    "from Location as l where l.shelves = ?1 and l.shelf = ?2 and  l.deleted = false", Location.class)
    	     	    .setParameter(1, locationDTO.getShelves())
    	     	    .setParameter(2, locationDTO.getShelf())
    	     	    .getSingleResult();  
        	
 
      
    	} catch (NoResultException e) {
    		
    		
    		location = locationDTO.toEntity();
			em.persist(location);	

    	}
    	
    	
		
		
		return location;
    }
    
       
    
    public void createCopy( EntityManager em, BookCopyDTO copyDTO, Book book) {
		
    		
		
		Copy copy = copyDTO.toEntity();
		
    	//COPY's LOCATION
    	copy.setLocation(  fetchCreateLocation(em, copyDTO.getLocation())	  );
		
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
    	
    	//Simplify this untill it really gets fucked
    	User user = entityManager.find(User.class, (long) 1  );
    	book.getAudit().setCreateUser(user);
    	
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
	     	    "from Book as b where b.title = ?1 and b.editorial = ?2 and b.deleted = false", Book.class)
	     	    .setParameter(1, book.getTitle())
	     	    .setParameter(2, book.getEditorial())
	     	    .getSingleResult();
    	
    	
    	
    	
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
    public JsonResponseMsg edit(BookDTO dto, @PathParam("id") Long id) throws Exception {
    	
    	u.begin();
    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
    	
    	Book book = dto.toEntity(entityManager);
    	book.setDeleted(false);
    	
		
		Book existing_book = entityManager.find(Book.class, book.getId()  );
		
		//Problems with the ISBN
		System.out.println("DB book");
		System.out.println(existing_book.getIsbn());
		System.out.println("DTO ported book");
		System.out.println(book.getIsbn());
		book.setAudit(  existing_book.getAudit()  );
    	book.getAudit().setEditDate( new Date()  );
    	//Simplify this untill it really gets fucked
    	User user = entityManager.find(User.class, (long) 1  );
    	book.getAudit().setEditUser(user);
    	
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
		
		//Fetch the edited entity (yes, again)
		book = entityManager.find(Book.class, book.getId()  );
		
			
		
		//Fetch all book's not deleted copies
        List<Copy> copies = entityManager.createQuery(
        	    "FROM Copy as c WHERE c.book = ?1 and c.deleted = false", Copy.class)
        	    .setParameter(1, book )
        	    .getResultList();	
        
        List<BookCopyDTO> new_copies = new ArrayList<BookCopyDTO>();
        List<BookCopyDTO> existing_copies = new ArrayList<BookCopyDTO>();
        
        // Divide the copies in the DTO in two groups
        // existing ones and new ones
        Iterator<BookCopyDTO> c_iterator = dto.getCopies().iterator();
        while (  c_iterator.hasNext()  ) {
        	BookCopyDTO c = c_iterator.next();
        	
        	if (  c.getId() == 0  ) {
        		new_copies.add(  c  );
        		
        	} else {
        		existing_copies.add(  c  );
        	}
        	
        	//Remove the element
        	c_iterator.remove();
        }
        

        

        	
        // Deal with the existing copies
        Iterator<Copy> copy_iterator = copies.iterator();
        while ( copy_iterator.hasNext()  ) {
        	
        	Copy copy = copy_iterator.next();
        	
        	for ( BookCopyDTO existing_copy : existing_copies){
        		if ( copy.getId() == existing_copy.getId()  ) {
        			
        			Copy edited_copy = new Copy();

        			//State, comments, deleted, editionYear
        			edited_copy = existing_copy.toEntity();
        			
        			//ID
        			edited_copy.setId(  copy.getId()  );
        			
        			//Audit
        			edited_copy.setAudit( copy.getAudit() );
        			//Assume it has been modified
        			edited_copy.getAudit().setEditDate(  new Date()  );
        			//copy.getAudit().setEditUser(editUser);
        			
        			
        			//Location
        			edited_copy.setLocation(  fetchCreateLocation(entityManager, existing_copy.getLocation())  );
        			
        			//Book
        			edited_copy.setBook(book);

        			//LendTypes
        			for (String lendType_name : existing_copy.getLendTypes() ) {
    		    	    LendType lendType_entity = entityManager.createQuery(
    		    	     	    "from LendType as t where t.name = ?1", LendType.class)
    		    	     	    .setParameter(1, lendType_name)
    		    	     	    .getSingleResult();
    		   
    					edited_copy.addLendType(lendType_entity);
        			}

        			
        			//Delete the copy from the copies list
        			copy_iterator.remove();
        			
        			
        			//Merge the edited copy
        			entityManager.merge( edited_copy );
        		}
        	}
        }
        
        //The rest of the copies should be removed
        for (Copy copy : copies){
        	copy.setDeleted(true);
			copy.getAudit().setEditDate(  new Date()  );
			//copy.getAudit().setEditUser(editUser);
        	entityManager.persist(copy);
        }
        

        
        //Deal with the new copies
		for (  BookCopyDTO copyDTO : new_copies  ) {
			// Persist the new copy
			createCopy(entityManager, copyDTO, book);		
		}
         	
    	
    	
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