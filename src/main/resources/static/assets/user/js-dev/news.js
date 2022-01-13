let getConnection =  (function(path,fnc){
	  $.ajax({
		  url : URL + path,
		  type : 'GET',
		  dataType : 'json', 
	      success : function(result) {   
		     fnc(result);	  
		   },
	      error : function(error) {
	    	  console.log(error); 
		   } 
	 });
  });

let viewResult = (function(notifications){
	 let html  = ''
		 notifications.forEach(notification => 
	    html += `
			 <a href="${URL}trang-chu/tin-tuc/${notification.id}" class="col-lg-2" style="margin:15px 0; padding: 0;">		 
	          <img style="height:230px; width:100%;" src="${notification.image}" alt="Loading..." class="img-thumbnail">      
	          </a>   
	          <a  class="col-lg-4" href="${URL}trang-chu/tin-tuc/${notification.id}" style="margin:15px 0; padding-left: 10px; " >
	          <h4 class="h5 text-black mb-3" ><strong>${notification.title}</strong></h4>
	            <span class="d-block text-secondary small text-uppercase"><i class="far fa-clock"></i> <i>${notification.date}</i></span>
	            <p style="font-size: 14px;">${notification.description}</p>          
	          </a>        	
	    	` 	    
	 );
	 document.getElementById('content').innerHTML = html;
 });
 
let setUpPage = function(data) {
 	$('#pagination-demo').twbsPagination('destroy'); 	      
     $('#pagination-demo').twbsPagination({
         totalPages: data.totalPages,
         startPage: data.number + 1,
         visiblePages: 3,
         hideOnlyOnePage: true,
     }).on('page', function(event, page) {  // do pagination trong jpa bắt đâu từ page = 0, nên page truyền vao -1
    	 let params = getValue();	 
         getConnection(`api/notification?page=${page-1}&&size=${params.size}&&title=${params.title}&&sortType=${params.sortType}`, fillAll);   
     });
 };
 
 let fillAll = (function(data){
	 viewResult(data.content);
	 
	 setUpPage(data);
 });
 
 let getValue = (function(){
		return {
			'size': document.getElementById('size').value,
			'sortType': document.getElementById('sort-type').value,
			'title': document.getElementById('search').value
		}
	});
 
 getConnection('api/notification', fillAll);
 
//change size
 document.getElementById('size').addEventListener('change', function(){
	 let params = getValue();
     getConnection(`api/notification?page=0&&size=${params.size}&&title=${params.title}&&sortType=${params.sortType}`, fillAll);
 });
   
//change sort
 document.getElementById('sort-type').addEventListener('change', function(){
	 let params = getValue();
     getConnection(`api/notification?page=0&&size=${params.size}&&title=${params.title}&&sortType=${params.sortType}`, fillAll);
 });
 
 // Search
let node = document.getElementById('search');	
	node.addEventListener("keyup", function(event) {
	    if (event.key === "Enter") {
	     let params = getValue();
	     getConnection(`api/notification?page=0&&size=${params.size}&&title=${params.title}&&sortType=${params.sortType}`, fillAll);
	    }
});
 


