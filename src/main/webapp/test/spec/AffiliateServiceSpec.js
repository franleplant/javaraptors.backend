describe("API: Affiliate", function() {

  describe('GET', function () {
	 it('Should give a correct reponse for /api/affiliate/1', function () {
		 var dfd, response, reponse_code;
		 
		 runs(function () {
			dfd = $.ajax({
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
	 
	 it('Should throw a 404 when the entity of the given id does not exist');
	 it('Should throw a 404 when the entity of the given id is logically deleted');
  });

 
});