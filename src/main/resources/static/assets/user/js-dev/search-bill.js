(function(){
	let date_bill = document.getElementById('date_bill').textContent;
	let id = document.getElementById('id_Apartment').textContent;
	let yyyy = date_bill.slice(0, 4);
	let mm = date_bill.substr(5, 2);
	  $.ajax({
		  url : URL + `api/bill/${id}?month=${mm}&&year=${yyyy}`,
		  type : 'GET',
		  dataType : 'json', 
	      success : function(result) {      	 
	    	  	let paid = result.paid? "(Đã thanh toán)" : "(Chưa thanh toán)";
	    	  	 document.getElementById("title-bill").innerHTML =  `Hóa đơn tháng ${getMonthYear(result.apartmentIndex.date)} `;
	    	  	 document.getElementById("paid-title").innerHTML = paid;	 
		    	 document.getElementById("bill-detail").innerHTML =  viewDetail(result); 	     
		   },
	      error : function(error) {    
    		  document.getElementById("notfound").hidden = false;
    		  document.getElementById("title-bill").innerHTML =  `Hóa đơn`;
	    	  console.log(error); 
		   } 
	 }); 
	
})();

let getMonthYear = (data) => {
	var date = new Date(data);
	var month = date.getMonth() + 1; 
	var year = date.getFullYear();
	 
	return  month + "/" + year;
}
