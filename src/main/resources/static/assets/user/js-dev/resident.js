const id_Apartm = document.getElementById('id_Apartment').textContent;

(function () {
    $.ajax({
        type: 'GET',
        url: URL + `api/reisident/${id_Apartm}`,
        dataType: 'json',
        success: function (result) {
        	viewResult2(result);
        	   	
        },
        error: function (error) {
        	console.log(error);
        }
    })
})();

let viewResult2 = (function(residents){
	 let html = '';
		 residents.forEach(resident => 
		  html += `
				<div class="col-md-3">
					<div class="card resident-hover" >
						<img class="card-img-top" src="assets/user/img/${resident.gender? 'male.png': 'female.png'}">
						<div class="card-body">
							<h4 class="card-title">${resident.fullname}</h4>
							<p class="card-text">${resident.job}</p>
							<a href="#" class="btn btn-outline-secondary">Xem chi tiết</a>
						</div>
					</div>
				</div>
 	   `
		 );
		 
 document.getElementById('residents').innerHTML = html;
});