/*
 * Note that this test suit is heavily couple with the ID of the entities, be carefull with that
 * 
 * MAKE SURE TO RESTART THE SERVER BEFORE RUNNING THIS TEST SUITE
 * 
 * 
 */


var TIME_OUT = 8000;

describe("API: Book", function() {

	describe('GET', function () {
		it('Should give a correct Book jSON for /api/book/1', function () {
			var dfd, response, reponse_code;
		 
			runs(function () {
				dfd = $.ajax({
					type: 'GET',
					url: '/javaraptors.backend/api/book/1',
					contentType: 'application/json'				
				});

				dfd.done(function (data, textStatus, jqXHR) {
					response = data;
					response_code = jqXHR.status;
				});
			});
		 
			waitsFor(function() {
				return dfd.state() === 'resolved';
			}, "The Ajax request has not returned", TIME_OUT);
		 
		 
			runs(function() {
				expect(response_code).toBe(200);
				expect(response.id).toBe(1);
				expect(response.name).not.toBe(null);
				expect(response.lastName).not.toBe(null);
			});
		});

		it('Should throw a 404 when the entity of the given id does not exist', function () {
			var dfd, reponse_code;
		 
			runs(function () {
				dfd = $.ajax({
					type: 'GET',
					url: '/javaraptors.backend/api/book/1500',
					contentType: 'application/json'				
				});
			
				dfd.fail(function( jqXHR, textStatus, errorThrown ) {
					response_code = jqXHR.status;	
				});
			});
		 
			waitsFor(function() {
				return dfd.state() === 'rejected';
			}, "The Ajax request has not returned", TIME_OUT);
		 
		 
			runs(function() {
				expect(response_code).toBe(404);
			});  
		});
	 
		it('Should throw a 404 when the entity of the given id is logically deleted', function () {
			var dfd, reponse_code;
		 
			runs(function () {
				dfd = $.ajax({
					type: 'GET',
					url: '/javaraptors.backend/api/book/2',
					contentType: 'application/json'				
				});
					
				dfd.fail(function( jqXHR, textStatus, errorThrown ) {
					response_code = jqXHR.status;
				});
			});
		 
			waitsFor(function() {
				return dfd.state() === 'rejected';
			}, 'The Ajax request has not returned', TIME_OUT);
		 
		 
			runs(function() {
				expect(response_code).toBe(404);
			}); 		 
		});
	});
  
  
	describe('Search', function () {
		it('Should give a correct Book jSON for /api/book?q=harry', function () {
			var dfd, response, reponse_code;

			runs(function () {
				dfd = $.ajax({
					type: 'GET',
					url: '/javaraptors.backend/api/book?q=harry',
					contentType: 'application/json'				
				});

				dfd.done(function (data, textStatus, jqXHR) {
					response = data.results;
					response_code = jqXHR.status;
				});
			});

			waitsFor(function() {
				return dfd.state() === 'resolved';
			}, "The Ajax request has not returned", TIME_OUT);
			 
			 
		    runs(function() {
		    	expect(response_code).toBe(200);
		    	expect(response[0].id).toBe(1);
		    	expect(response[0].name).not.toBe(null);
		    	expect(response[0].lastName).not.toBe(null);
			});
		});
	});
  
	var new_book_id;
  
  
	describe('POST', function () {

		//Generate a ramdon ISBN so the tests can be run several times
		var data = {
		    "isbn": Math.floor(  Math.random() * 100000000  ) + '',
		    "type": "book",
		    "title": Math.floor(  Math.random() * 10000  ) + 'NUEVO',
		    "editionNumber": null,
		    "editionCountry": "Argentina",
		    "summary": null,
		    "img": null,
		    "lang": "EspaÃ±ol",
		    "val": 7.0,
		    "price": "10.2",
		    "comments": null,
		    "authors": [{
		        "nick": "Jk Rowling"
		      }, {
		        "nick": "Soy un NUEVO autor"
		      }],
		    "genres": [{
		        "name": "fantasia"
		    
		      }, {
		        "name": "NUEVO GENERO"
		      }],
		    "editorial": {
		        "name": "NUEVA EDITORIAL"
		    },
		    "copies": [{
		        "state": "bueno, un poco desgastado",
		        "editionYear": 2010,
		        "comments": null,
		        "lendTypes": ["local"],
		        "location": {
		            "id": 1,
		            "shelves": "A",
		            "shelf": "2"
		        }
		    }, {
		        "state": "nuevo",
		        "editionYear": 2010,
		        "comments": null,
		        "lendTypes": ["local", "foreign"],
		        "location": {
		            "id": 1,
		            "shelves": "A",
		            "shelf": "2"
		        }
		    }]
		};

		describe('Creation', function () {
			 
			
			it('should send a success JSON with status: ok', function () {
				var dfd, response, response_code;
			  
			  
				runs(function () {
					 
					//Create the new book
					dfd = $.ajax({
						type: 'POST',
						url: '/javaraptors.backend/api/book',
						contentType: 'application/json',
						data: JSON.stringify(data)
					});
						
					dfd.done(function (data, textStatus, jqXHR) {
						response = data;
						response_code = jqXHR.status;
					});
				});
				 
				waitsFor(function() {
				    return dfd.state() === 'resolved';
				}, "The Ajax request has not returned", TIME_OUT);
				 
				 
			    runs(function() {
			    	expect(response_code).toBe(200);			    	
			    	expect(response.status).toBe('ok');	
				});		 
			});
			  
			it('should make the newly created book available', function () {
				var dfd, response, reponse_code;
				 
				runs(function () {
					dfd = $.ajax({
						type: 'GET',
						url: '/javaraptors.backend/api/book?q=nuevo',
						contentType: 'application/json'				
					});
					
					dfd.done(function (data, textStatus, jqXHR) {
						response = data.results;
						response_code = jqXHR.status;
					});
				});
				 
				waitsFor(function() {
				    return dfd.state() === 'resolved';
				}, "The Ajax request has not returned", TIME_OUT);
				 
				 
			    runs(function() {
					expect(response_code).toBe(200);
					expect(response.length).toBeGreaterThan(0);
					expect(response[0].id).toBeTruthy();
					expect(response[0].title).toBeTruthy();
					expect(response[0].authors[0].id).toBeTruthy();
					expect(response[0].genres[0].id).toBeTruthy();
					expect(response[0].editorial.id).toBeTruthy();
					expect(response[0].copies[0].id).toBeTruthy();
					expect(response[0].audit).toBeTruthy();
			    	
			    	//Save the newly created book id to test the edit functionality
			    	new_book_id = response[0].id;
			    	console.log('The test books id is:', new_book_id);
			    });
			});
		});
	 
	 
		describe('Edition', function () {
			 

			 
			it('should send a success JSON with status: ok', function () {
				var dfd, response, response_code;


				//change the data
				data.id = new_book_id;
				data.title += 'editado';
				data.genres.pop();
				  
				  
				runs(function () {
					 
					//Edit the new book
					dfd = $.ajax({
						type: 'POST',
						url: '/javaraptors.backend/api/book/' + new_book_id,
						contentType: 'application/json',
						data: JSON.stringify(data)
					});
						
					dfd.done(function (data, textStatus, jqXHR) {
						response = data;
						response_code = jqXHR.status;
					});
				});
				 
				waitsFor(function() {
				    return dfd.state() === 'resolved';
				}, "The Ajax request has not returned", TIME_OUT);		 
	
		 

				runs(function() {
					expect(response_code).toBe(200);
					expect(response.status).toBe('ok');
			    });
			});

			it('should make the newly edited book available', function () {
				var dfd, response, reponse_code;
				 
				runs(function () {
					dfd = $.ajax({
						type: 'GET',
						url: '/javaraptors.backend/api/book?q=editado',
						contentType: 'application/json'				
					});
					
					dfd.done(function (data, textStatus, jqXHR) {
						response = data.results;
						response_code = jqXHR.status;
					});
				 });
				 
				waitsFor(function() {
				    return dfd.state() === 'resolved';
				}, "The Ajax request has not returned", TIME_OUT);
				 
				 
			    runs(function() {
					expect(response_code).toBe(200);
					expect(response.length).toBeGreaterThan(0);
					expect(response[0].id).toBeTruthy();
					expect(response[0].title).toBeTruthy();
					expect(response[0].authors[0].id).toBeTruthy();
					expect(response[0].genres[0].id).toBeTruthy();
					expect(response[0].editorial.id).toBeTruthy();
					expect(response[0].copies[0].id).toBeTruthy();
					expect(response[0].audit).toBeTruthy();
			    });
		  	});
		});
	});





	describe('DELETE', function () {
	  
		it('should return a success JSON with status: ok', function () {
			var dfd, response, response_code;
	  
	  
			runs(function () {
			 
				//Delete book
				dfd = $.ajax({
					type: 'DELETE',
					url: '/javaraptors.backend/api/book/' + new_book_id,
					contentType: 'application/json'
				});
				
				dfd.done(function (data, textStatus, jqXHR) {
					response = data;
					response_code = jqXHR.status;
				});
			});
		 
			waitsFor(function() {
				return dfd.state() === 'resolved';
			}, "The Ajax request has not returned", TIME_OUT);
		 
		 
		    runs(function() {
		    	expect(response_code).toBe(200);	    	
		    	expect(response.status).toBe('ok');	    	
		     });	
		});
  
		it('should reflect the changes in the DB and throw a 404 when geting that book', function () {
			var dfd, reponse_code;
	 
			runs(function () {
				dfd = $.ajax({
					type: 'GET',
					url: '/javaraptors.backend/api/book/' + new_book_id,
					contentType: 'application/json'				
				});
					
				dfd.fail(function( jqXHR, textStatus, errorThrown ) {
					response_code = jqXHR.status;
				});
			});
	 
			waitsFor(function() {
				return dfd.state() === 'rejected';
			}, 'The Ajax request has not returned', TIME_OUT);
	 
	 
			runs(function() {
				expect(response_code).toBe(404);
			}); 
  		});	  
	});

});

	  
	
 
