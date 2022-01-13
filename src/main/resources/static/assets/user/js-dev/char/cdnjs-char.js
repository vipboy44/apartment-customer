const id_AC = document.getElementById('id_Apartment').textContent;
const years = (function(){
				 let currentYear = new Date().getFullYear(), years = [] , startYear = 2019;	
				 while( startYear <= currentYear ) {
				        years.push(startYear++);
				    }  
			  return years;
			})();
//get ví trí index của năm hiện tại trong mảng các năm bắt đầu từ năm 2019
let index = years.indexOf(new Date().getFullYear()); 
let myChart;

//Biều đồ cột
let cdnjsChar = (datas) => {
	var ctx = document.getElementById("myChart").getContext('2d');
	 myChart = new Chart(ctx, {
	type: 'bar',
	data: {
	labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
	datasets: [{
	label: 'vnd',
	data: datas,
	backgroundColor: [
	'rgba(255, 99, 132, 0.2)',
	'rgba(54, 162, 235, 0.2)',
	'rgba(255, 206, 86, 0.2)',
	'rgba(75, 192, 192, 0.2)',
	'rgba(153, 102, 255, 0.2)',
	'rgba(255, 159, 64, 0.2)',
	'rgba(255, 99, 132, 0.2)',
	'rgba(54, 162, 235, 0.2)',
	'rgba(255, 206, 86, 0.2)',
	'rgba(75, 192, 192, 0.2)',
	'rgba(153, 102, 255, 0.2)',
	'rgba(255, 159, 64, 0.2)'
	],
	borderColor: [
	'rgba(255,99,132,1)',
	'rgba(54, 162, 235, 1)',
	'rgba(255, 206, 86, 1)',
	'rgba(75, 192, 192, 1)',
	'rgba(153, 102, 255, 1)',
	'rgba(255, 159, 64, 1)',
	'rgba(255,99,132,1)',
	'rgba(54, 162, 235, 1)',
	'rgba(255, 206, 86, 1)',
	'rgba(75, 192, 192, 1)',
	'rgba(153, 102, 255, 1)',
	'rgba(255, 159, 64, 1)'
	],
	borderWidth: 1
	}]
	},
	options: {
	scales: {
	yAxes: [{
	ticks: {
	beginAtZero: true
	}
	}]
	}
	}
	});
	
}

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

// Biểu đồ tồng tiền hóa đơn hàng tháng trong năm hiện tại
getConnection(`api/statistical/price?id=${id_AC}&&year=${new Date().getFullYear()}`,cdnjsChar);


document.getElementById('previous_cdnjs').addEventListener('click', () => {	
	index === 0 ?  index = 0 : index--;
	myChart.destroy();
	document.getElementById('title-myChart').textContent = `(Hóa đơn hàng tháng - ${years[index]} )`;
	getConnection(`api/statistical/price?id=${id_AC}&&year=${years[index]}`,cdnjsChar);
	
});

document.getElementById('next_cdnjs').addEventListener('click', () => {	
	index === (years.length -1) ?  index = years.length -1 : index++;
	myChart.destroy();
	document.getElementById('title-myChart').textContent = `(Hóa đơn hàng tháng - ${years[index]} )`;
	getConnection(`api/statistical/price?id=${id_AC}&&year=${years[index]}`,cdnjsChar);
	
});




