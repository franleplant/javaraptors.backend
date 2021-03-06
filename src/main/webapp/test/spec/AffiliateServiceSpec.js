/*
 * Note that this test suit is heavily couple with the ID of the entities, be carefull with that
 * 
 * MAKE SURE TO RESTART THE SERVER BEFORE RUNNING THIS TEST SUITE
 * 
 * 
 */




xdescribe("API: Affiliate", function() {

  describe('GET', function () {
	 it('Should give a correct reponse for /api/affiliate/1', function () {
		 var dfd, response, reponse_code;
		 
		 runs(function () {
			dfd = $.ajax({
					type: 'GET',
					url: '/javaraptors.backend/api/affiliate/1',
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
					url: '/javaraptors.backend/api/affiliate/1500',
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
					url: '/javaraptors.backend/api/affiliate/3',
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
	  
	  
	  
	  describe('create a new Affiliate with City, Prov and Country already created', function () {
		  var data = {
				    "reputation": 10.0,
				    "person": {
				        "name": "Soy un Nuevo Usuario",
				        "lastName": "Con Appleido totalmente nuevo",
				        "dni": "1234567",
				        "cuil": "1234566",
				        "img": null,
				        "birth": "1990-10-10",
				        "address": {
				            "street": "Avenida Del Juego de la Oca",
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
				            "email": "jesus@cielo.com",
				            "tel": 1234567,
				            "cel": 1234567,
				            "web": null
				        }
				    }
				};	
		  
		  it('should send a success JSON with status: ok', function () {
			  var dfd, response, response_code;
			  
			  
				 runs(function () {
					 
						//Create the new affiliate
						dfd = $.ajax({
								type: 'POST',
								url: '/javaraptors.backend/api/affiliate',
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
						url: '/javaraptors.backend/api/affiliate/4',
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
		    	  	
		    	
		    	expect(response.person.name).toBe(data.person.name);
		    	expect(response.person.lastName).toBe(data.person.lastName);
		    		    	
		    	expect(response.person.audit.createDate).not.toBe(undefined);
		    	expect(response.deleted).toBe(false);
		    	expect(response.type.name).toBe('affiliate');
		    	
		    });	
		    
		    
		  });
		  
	  });
	  
	  
	  
	  
	  
	  
	  describe('create a new Affiliate with a none existent City', function () {
		  var data = {
				    "reputation": 10.0,
				    "person": {
				        "name": "Soy un Nuevo Usuario",
				        "lastName": "Con Appleido totalmente nuevo",
				        "dni": "1234567",
				        "cuil": "1234566",
				        "img": null,
				        "birth": "1990-10-10",
				        "address": {
				            "street": "Avenida Del Juego de la Oca",
				            "number": 123,
				            "floor": null,
				            "department": null,
				            "city": {
				                "name": "Los Cardales",
				                "prov": {
				                	"id": 1,
				                    "name": "Buenos Aires",
				                    "country": {
				                        "id": 1,
				                        "name": "Argentina"
				                    }
				                },
				                "cp": "213"
				            }
				        },
				        "contact": {
				            "email": "jesus@cielo.com",
				            "tel": 1234567,
				            "cel": 1234567,
				            "web": null
				        }
				    }
				};	
		  
		  it('should send a success JSON with status: ok', function () {
			  var dfd, response, response_code;
			  
			  
				 runs(function () {
					 
						//Create the new affiliate
						dfd = $.ajax({
								type: 'POST',
								url: '/javaraptors.backend/api/affiliate',
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
						url: '/javaraptors.backend/api/affiliate/5',
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
		    	  	
		    	
		    	expect(response.person.name).toBe(data.person.name);
		    	expect(response.person.lastName).toBe(data.person.lastName);
		    	
		    	
		    	expect(response.person.address.city.id).not.toBe(undefined);
		    	expect(response.person.address.city.name).toBe(data.person.address.city.name);
		    	
		    	
		    	expect(response.person.address.city.prov.id).not.toBe(undefined);
		    	expect(response.person.address.city.prov.name).toBe(data.person.address.city.prov.name);
		    		    	
		    	expect(response.person.audit.createDate).not.toBe(undefined);
		    	expect(response.deleted).toBe(false);
		    	expect(response.type.name).toBe('affiliate');
		    	
		    });	
		    
		    
		  });
		  
	  });
	  
	  
	  
	  
	  
	  
  });
  
  
  
  
  
  
  
describe('POST: edit', function () {
	  
	  
	  
	  describe('edit an existing Affiliate with a new City', function () {
		  var data = {
				    "id": 1,
				    "reputation": 1.0,
				    "person": {
				        "name": "Jesus numoer 2",
				        "lastName": "DeLaFerrere",
				        "dni": "1234567",
				        "cuil": "1234566",
				        "img": null,
				        "birth": "1990-10-10",
				        "address": {
				            "street": "Avenida Del Juego de la Oca",
				            "number": 123,
				            "floor": null,
				            "department": null,
				            "city": {
				                "name": "Pilar",
				                "prov": {
				                    "id": 1,
				                    "name": "Buenos Aires",
				                    "country": {
				                        "id": 1,
				                        "name": "Argentina"
				                    }
				                },
				                "cp": "4321"
				            }
				        },
				        "contact": {
				            "email": "jesus@cielo.com",
				            "tel": 1234567,
				            "cel": 1234567,
				            "web": null
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
				        }
				    },
				    "type": {
				        "id": 3,
				        "name": "affiliate"
				    },
				    "suspensions": [],
				    "deleted": false
				};	
		  
		  it('should send a success JSON with status: ok', function () {
			  var dfd, response, response_code;
			  
			  
				 runs(function () {
					 
						//Edit the new affiliate
						dfd = $.ajax({
								type: 'POST',
								url: '/javaraptors.backend/api/affiliate/1',
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
						url: '/javaraptors.backend/api/affiliate/1',
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
		    	expect(response.person.name).toBe(data.person.name);
		    	expect(response.person.lastName).toBe(data.person.lastName);
		    	
		    	expect(response.reputation).toBe(data.reputation);
		    		    	
		    	expect(response.person.audit.editDate).not.toBe(undefined);
		    	expect(response.type.name).toBe('affiliate');
		    	
		    });	
		    
		    
		  });
		  
	  });
	  
	  
	  
	  
	  
	  

	  
	  
	  
	  
	  
	  
  });
  
  













describe('DELETE', function () {
	  
	  
	
		  
  it('should return a success JSON with status: ok', function () {
	  var dfd, response, response_code;
	  
	  
		 runs(function () {
			 
				//Delete affiliate
				dfd = $.ajax({
						type: 'DELETE',
						url: '/javaraptors.backend/api/affiliate/1',
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
				url: '/javaraptors.backend/api/affiliate/1',
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

	  
	
 
