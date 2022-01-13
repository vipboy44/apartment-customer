let id = document.getElementById('id-news').textContent;

(function () {
    $.ajax({
        type: 'GET',
        url: URL + `api/notification/${id}`,
        dataType: 'json',
        success: function (result) {
        	console.log(result);
            document.getElementById('notification').innerHTML = titleHTML(result) +  result.content
            				+ `<p class="text-right"><span style="color:#95a5a6">
            				<strong><span style="font-size:12px">Người đăng: ${result.employee.username}</span></strong>
            				</span></p>`;
        },
        error: function (error) {
          
        }
    })
})()


let titleHTML = (notification) => {		
	return	`
			<p style="text-align:center"><strong><span style="color:#c0392b"><span style="font-size:28px">${notification.title}</span></span></strong></p>
			<p><span style="color:#95a5a6"><strong><span style="font-size:12px">${notification.date}</span></strong></span></p>	
			<hr>
		`
}

