/*
 * Note that this test suit is heavily couple with the ID of the entities, be carefull with that
 * 
 * MAKE SURE TO RESTART THE SERVER BEFORE RUNNING THIS TEST SUITE
 * 
 * 
 */




describe("API: Book", function() {

  describe('GET', function () {
	 it('Should give a correct reponse for /api/book/1', function () {
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
		    }, "The Ajax request has not returned", 2000);
		 
		 
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
		    }, "The Ajax request has not returned", 2000);
		 
		 
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
		  }, 'The Ajax request has not returned', 2000);
		 
		 
	    runs(function() {
	    	expect(response_code).toBe(404);
	     }); 		 
	 });
  });
  
  
  
  
  
  
  describe('POST: creation', function () {
	  
	  
	  
	  describe('create a new Book', function () {
		  var data = {
				    "isbn": "1111111",
				    "title": "Harry Potter y El Prisionero de Askaban",
				    "authors": [{
				        "id": 1,
				        "nick": "Jk Rowling",
				        "birth": "1956-10-10",
				        "isni": null,
				        "audit": {
				            "createUser": {
				                "id": 1,
				                "password": "hola",
				                "rol": {
				                    "id": 1,
				                    "name": "admin",
				                    "comments": null
				                },
				                "person": {
				                    "name": "Claudia",
				                    "lastName": "Guzman",
				                    "dni": "12345678",
				                    "cuil": "12345567",
				                    "img": null,
				                    "birth": "1988-10-10",
				                    "address": {
				                        "street": "Avenida Siempre Viva",
				                        "number": 123,
				                        "floor": null,
				                        "department": null,
				                        "city": {
				                            "id": 1,
				                            "name": "Campana",
				                            "prov": {
				                                "id": 1,
				                                "name": "Buenos Aires",
				                                "country": {
				                                    "id": 1,
				                                    "name": "Argentina"
				                                }
				                            },
				                            "cp": "1234"
				                        }
				                    },
				                    "contact": {
				                        "email": "clau@guzman.com",
				                        "tel": 1234567,
				                        "cel": 1234567,
				                        "web": null
				                    },
				                    "audit": {
				                        "createUser": null,
				                        "createDate": "2013-10-10",
				                        "editUser": null,
				                        "editDate": null,
				                        "deleteUser": null,
				                        "deleteDate": null
				                    }
				                },
				                "type": {
				                    "id": 4,
				                    "name": "user"
				                },
				                "deleted": false
				            },
				            "createDate": "2013-10-10",
				            "editUser": null,
				            "editDate": null,
				            "deleteUser": null,
				            "deleteDate": null
				        },
				        "comments": null,
				        "country": {
				            "id": 2,
				            "name": "Gran BretaÃ±a"
				        },
				        "type": {
				            "id": 8,
				            "name": "author"
				        },
				        "deleted": false
				    }],
				    "genres": [{
				        "id": 1,
				        "name": "fantasÃ­a",
				        "comments": null,
				        "type": {
				            "id": 5,
				            "name": "genre"
				        },
				        "deleted": false
				    }],
				    "editionNumber": null,
				    "editionCountry": {
				        "id": 1,
				        "name": "Argentina"
				    },
				    "editorial": {
				        "id": 1,
				        "legal_name": "Salamandra SA",
				        "name": "Salamandra",
				        "address": {
				            "street": "Cordoba",
				            "number": 123,
				            "floor": null,
				            "department": null,
				            "city": {
				                "id": 1,
				                "name": "Campana",
				                "prov": {
				                    "id": 1,
				                    "name": "Buenos Aires",
				                    "country": {
				                        "id": 1,
				                        "name": "Argentina"
				                    }
				                },
				                "cp": "1234"
				            }
				        },
				        "contact": {
				            "email": "tu@salamandra.com.ar",
				            "tel": 1234567,
				            "cel": null,
				            "web": null
				        },
				        "comments": null,
				        "type": {
				            "id": 7,
				            "name": "editorial"
				        },
				        "audit": {
				            "createUser": {
				                "id": 1,
				                "password": "hola",
				                "rol": {
				                    "id": 1,
				                    "name": "admin",
				                    "comments": null
				                },
				                "person": {
				                    "name": "Claudia",
				                    "lastName": "Guzman",
				                    "dni": "12345678",
				                    "cuil": "12345567",
				                    "img": null,
				                    "birth": "1988-10-10",
				                    "address": {
				                        "street": "Avenida Siempre Viva",
				                        "number": 123,
				                        "floor": null,
				                        "department": null,
				                        "city": {
				                            "id": 1,
				                            "name": "Campana",
				                            "prov": {
				                                "id": 1,
				                                "name": "Buenos Aires",
				                                "country": {
				                                    "id": 1,
				                                    "name": "Argentina"
				                                }
				                            },
				                            "cp": "1234"
				                        }
				                    },
				                    "contact": {
				                        "email": "clau@guzman.com",
				                        "tel": 1234567,
				                        "cel": 1234567,
				                        "web": null
				                    },
				                    "audit": {
				                        "createUser": null,
				                        "createDate": "2013-10-10",
				                        "editUser": null,
				                        "editDate": null,
				                        "deleteUser": null,
				                        "deleteDate": null
				                    }
				                },
				                "type": {
				                    "id": 4,
				                    "name": "user"
				                },
				                "deleted": false
				            },
				            "createDate": "2013-10-10",
				            "editUser": null,
				            "editDate": null,
				            "deleteUser": null,
				            "deleteDate": null
				        },
				        "deleted": false
				    },
				    "img": null,
				    "summary": null,
				    "lang": "EspaÃ±ol",
				    "val": 7.0,
				    "price": "10.2",
				    "comments": null
				};	
		  
		  it('should send a success JSON with status: ok', function () {
			  var dfd, response, response_code;
			  
			  
				 runs(function () {
					 
						//Create the new affiliate
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
				    }, "The Ajax request has not returned", 2000);
				 
				 
				    runs(function() {
				    	expect(response_code).toBe(200);
				    	
				    	expect(response.status).toBe('ok');
				    	
				      });	
				 
				 
		  });
		  
		  it('should create the entity in the DB', function () {
			 var dfd, response, reponse_code;
			 

			 
			 runs(function () {
				//Get it, and see if everything is correct
				dfd = $.ajax({
						type: 'GET',
						url: '/javaraptors.backend/api/book/3',
						contentType: 'application/json'
					});
				
				
				dfd.done(function (data, textStatus, jqXHR) {
					response = data;
					response_code = jqXHR.status;				
				});
			});
				
				
	 
			 
			waitsFor(function() {
		      return dfd.state() === 'resolved';
		    }, "The Ajax request has not returned", 2000);
				 
				 
		    runs(function() {
		    	expect(response_code).toBe(200);
		    	  	
		    	
		    	expect(response.title).toBe(data.title);
		    	expect(response.isbn).toBe(data.isbn);
		    		    	
		    	expect(response.audit.createDate).not.toBe(undefined);
		    	expect(response.deleted).toBe(false);
		    	expect(response.type.name).toBe('book');
		    	
		    });	
		    
		    
		  });
		  
	  });
	  
	  
	 
	  
	  
	  
	  
	  
	  
  });
  
  
  
  
  
  
  
describe('POST: edit', function () {
	  
	  
	  
	  describe('edit an existing Book', function () {
		  var data = {
				    "id": 1,
				    "isbn": "1234556678",
				    "title": "SOY UN LIBRO SUPER MODIFICADO",
				    "authors": [{
				        "id": 1,
				        "nick": "Jk Rowling",
				        "birth": "1956-10-10",
				        "isni": null,
				        "audit": {
				            "createUser": {
				                "id": 1,
				                "password": "hola",
				                "rol": {
				                    "id": 1,
				                    "name": "admin",
				                    "comments": null
				                },
				                "person": {
				                    "name": "Claudia",
				                    "lastName": "Guzman",
				                    "dni": "12345678",
				                    "cuil": "12345567",
				                    "img": null,
				                    "birth": "1988-10-10",
				                    "address": {
				                        "street": "Avenida Siempre Viva",
				                        "number": 123,
				                        "floor": null,
				                        "department": null,
				                        "city": {
				                            "id": 1,
				                            "name": "Campana",
				                            "prov": {
				                                "id": 1,
				                                "name": "Buenos Aires",
				                                "country": {
				                                    "id": 1,
				                                    "name": "Argentina"
				                                }
				                            },
				                            "cp": "1234"
				                        }
				                    },
				                    "contact": {
				                        "email": "clau@guzman.com",
				                        "tel": 1234567,
				                        "cel": 1234567,
				                        "web": null
				                    },
				                    "audit": {
				                        "createUser": null,
				                        "createDate": "2013-10-10",
				                        "editUser": null,
				                        "editDate": null,
				                        "deleteUser": null,
				                        "deleteDate": null
				                    }
				                },
				                "type": {
				                    "id": 4,
				                    "name": "user"
				                },
				                "deleted": false
				            },
				            "createDate": "2013-10-10",
				            "editUser": null,
				            "editDate": null,
				            "deleteUser": null,
				            "deleteDate": null
				        },
				        "comments": null,
				        "country": {
				            "id": 2,
				            "name": "Gran BretaÃ±a"
				        },
				        "type": {
				            "id": 8,
				            "name": "author"
				        },
				        "deleted": false
				    }],
				    "genres": [{
				        "id": 1,
				        "name": "fantasÃ­a",
				        "comments": null,
				        "type": {
				            "id": 5,
				            "name": "genre"
				        },
				        "deleted": false
				    }],
				    "editionNumber": null,
				    "editionCountry": {
				        "id": 1,
				        "name": "Argentina"
				    },
				    "editorial": {
				        "id": 1,
				        "legal_name": "Salamandra SA",
				        "name": "Salamandra",
				        "address": {
				            "street": "Cordoba",
				            "number": 123,
				            "floor": null,
				            "department": null,
				            "city": {
				                "id": 1,
				                "name": "Campana",
				                "prov": {
				                    "id": 1,
				                    "name": "Buenos Aires",
				                    "country": {
				                        "id": 1,
				                        "name": "Argentina"
				                    }
				                },
				                "cp": "1234"
				            }
				        },
				        "contact": {
				            "email": "tu@salamandra.com.ar",
				            "tel": 1234567,
				            "cel": null,
				            "web": null
				        },
				        "comments": null,
				        "type": {
				            "id": 7,
				            "name": "editorial"
				        },
				        "audit": {
				            "createUser": {
				                "id": 1,
				                "password": "hola",
				                "rol": {
				                    "id": 1,
				                    "name": "admin",
				                    "comments": null
				                },
				                "person": {
				                    "name": "Claudia",
				                    "lastName": "Guzman",
				                    "dni": "12345678",
				                    "cuil": "12345567",
				                    "img": null,
				                    "birth": "1988-10-10",
				                    "address": {
				                        "street": "Avenida Siempre Viva",
				                        "number": 123,
				                        "floor": null,
				                        "department": null,
				                        "city": {
				                            "id": 1,
				                            "name": "Campana",
				                            "prov": {
				                                "id": 1,
				                                "name": "Buenos Aires",
				                                "country": {
				                                    "id": 1,
				                                    "name": "Argentina"
				                                }
				                            },
				                            "cp": "1234"
				                        }
				                    },
				                    "contact": {
				                        "email": "clau@guzman.com",
				                        "tel": 1234567,
				                        "cel": 1234567,
				                        "web": null
				                    },
				                    "audit": {
				                        "createUser": null,
				                        "createDate": "2013-10-10",
				                        "editUser": null,
				                        "editDate": null,
				                        "deleteUser": null,
				                        "deleteDate": null
				                    }
				                },
				                "type": {
				                    "id": 4,
				                    "name": "user"
				                },
				                "deleted": false
				            },
				            "createDate": "2013-10-10",
				            "editUser": null,
				            "editDate": null,
				            "deleteUser": null,
				            "deleteDate": null
				        },
				        "deleted": false
				    },
				    "img": null,
				    "summary": null,
				    "lang": "EspaÃ±ol",
				    "val": 7.0,
				    "price": "10.2",
				    "comments": "SOY UN LIBRO NUEVO LA LA LA LA LA LAAA",
				    "audit": {
				        "createUser": {
				            "id": 1,
				            "password": "hola",
				            "rol": {
				                "id": 1,
				                "name": "admin",
				                "comments": null
				            },
				            "person": {
				                "name": "Claudia",
				                "lastName": "Guzman",
				                "dni": "12345678",
				                "cuil": "12345567",
				                "img": null,
				                "birth": "1988-10-10",
				                "address": {
				                    "street": "Avenida Siempre Viva",
				                    "number": 123,
				                    "floor": null,
				                    "department": null,
				                    "city": {
				                        "id": 1,
				                        "name": "Campana",
				                        "prov": {
				                            "id": 1,
				                            "name": "Buenos Aires",
				                            "country": {
				                                "id": 1,
				                                "name": "Argentina"
				                            }
				                        },
				                        "cp": "1234"
				                    }
				                },
				                "contact": {
				                    "email": "clau@guzman.com",
				                    "tel": 1234567,
				                    "cel": 1234567,
				                    "web": null
				                },
				                "audit": {
				                    "createUser": null,
				                    "createDate": "2013-10-10",
				                    "editUser": null,
				                    "editDate": null,
				                    "deleteUser": null,
				                    "deleteDate": null
				                }
				            },
				            "type": {
				                "id": 4,
				                "name": "user"
				            },
				            "deleted": false
				        },
				        "createDate": "2013-10-10",
				        "editUser": null,
				        "editDate": null,
				        "deleteUser": null,
				        "deleteDate": null
				    },
				    "type": {
				        "id": 1,
				        "name": "book"
				    },
				    "deleted": false
				};	
		  
		  it('should send a success JSON with status: ok', function () {
			  var dfd, response, response_code;
			  
			  
				 runs(function () {
					 
						//Edit the new affiliate
						dfd = $.ajax({
								type: 'POST',
								url: '/javaraptors.backend/api/book/1',
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
				    }, "The Ajax request has not returned", 2000);
				 
				 
				    runs(function() {
				    	expect(response_code).toBe(200);
				    	
				    	expect(response.status).toBe('ok');
				    	
				      });	
				 
				 
		  });
		  
		  it('should reflect the changes in the DB', function () {
			 var dfd, response, reponse_code;
			 

			 
			 runs(function () {
				//Get it, and see if everything is correct
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
		    }, "The Ajax request has not returned", 2000);
				 
				 
		    runs(function() {
		    	expect(response_code).toBe(200);
		    	  	
		    	
		    	expect(response.id).toBe(data.id);
		    	expect(response.title).toBe(data.title);
		    	expect(response.isbn).toBe(data.isbn);
		    	
		    	expect(response.comments).toBe(data.comments);
		    	
		    	
		    		    	
		    	expect(response.audit.editDate).not.toBe(undefined);
		    	expect(response.type.name).toBe('book');
		    	
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
		    }, "The Ajax request has not returned", 2000);
		 
		 
		    runs(function() {
		    	expect(response_code).toBe(200);
		    	
		    	expect(response.status).toBe('ok');
		    	
		      });	
		 
		 
  });
  
  it('should reflect the changes in the DB', function () {
	 var dfd, response, reponse_code;
	 

	 
	 var dfd, reponse_code;
	 
	 runs(function () {
		dfd = $.ajax({
				type: 'GET',
				url: '/javaraptors.backend/api/book/1',
				contentType: 'application/json'				
			});
					
		dfd.fail(function( jqXHR, textStatus, errorThrown ) {
			response_code = jqXHR.status;
			
		});
	 });
	 
	 waitsFor(function() {
	      return dfd.state() === 'rejected';
	  }, 'The Ajax request has not returned', 2000);
	 
	 
    runs(function() {
    	expect(response_code).toBe(404);
     }); 
    
    
  });
		  
	  
	  
	  
	  
	  
	  

	  
	  
	  
	  
	  
	  
});
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
});

	  
	
 
