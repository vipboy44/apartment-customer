const id_ACA = document.getElementById('id_Apartment').textContent;

const years_char2 = (function(){
	 let currentYear = new Date().getFullYear(), years = [] , startYear = 2019;	
	 while( startYear <= currentYear ) {
	        years.push(startYear++);
	    }  
 return years;
})();
//get ví trí index của năm hiện tại trong mảng các năm bắt đầu từ năm 2019
let index_char2 = years_char2.indexOf(new Date().getFullYear());


let canvarChar = (water, electricity, year) => {
	var options = {
		exportEnabled: true,
		animationEnabled: true,
		title:{
			text: "Biểu đồ đường"
		},
		/*subtitles: [{
			text: "Theo dõi lượng tiêu thụ điện nước hàng tháng"
		}],*/
		/*axisX: {
			title: "Năm 2019"
		},*/
		axisY: { 
			title: "m3",
			titleFontColor: "#4F81BC",
			lineColor: "#4F81BC",
			labelFontColor: "#4F81BC",
			tickColor: "#4F81BC",
			includeZero: false
		},
		axisY2: {
			title: "KWh",
			titleFontColor: "#C0504E",
			lineColor: "#C0504E",
			labelFontColor: "#C0504E",
			tickColor: "#C0504E",
			includeZero: false
		},
		toolTip: {
			shared: true
		},
		legend: {
			cursor: "pointer",
			itemclick: toggleDataSeries
		},
		data: [{
			type: "spline",
			name: "Số nước",
			showInLegend: true,
			xValueFormatString: "MM YYYY",
			yValueFormatString: "#,##0 m3", //value
			dataPoints: [
				{ x: new Date(year, 0, 1),  y: water[0] },
				{ x: new Date(year, 1, 1), y: water[1] },
				{ x: new Date(year, 2, 1), y: water[2]},
				{ x: new Date(year, 3, 1),  y: water[3] },
				{ x: new Date(year, 4, 1),  y: water[4] },
				{ x: new Date(year, 5, 1),  y: water[5] },
				{ x: new Date(year, 6, 1), y: water[6] },
				{ x: new Date(year, 7, 1), y: water[7] },
				{ x: new Date(year, 8, 1),  y: water[8] },
				{ x: new Date(year, 9, 1),  y: water[9] },
				{ x: new Date(year, 10, 1),  y: water[10] },
				{ x: new Date(year, 11, 1), y: water[11] }
			]
		},
		{
			type: "spline",
			name: "Số điện",
			axisYType: "secondary",
			showInLegend: true,
			xValueFormatString: "MM YYYY",
			yValueFormatString: "#,##0 KWh",
			dataPoints: [
				{ x: new Date(year, 0, 1),  y: electricity[0] },
				{ x: new Date(year, 1, 1), y: electricity[1] },
				{ x: new Date(year, 2, 1), y: electricity[2] },
				{ x: new Date(year, 3, 1),  y: electricity[3] },
				{ x: new Date(year, 4, 1),  y: electricity[4] },
				{ x: new Date(year, 5, 1),  y: electricity[5] },
				{ x: new Date(year, 6, 1), y: electricity[6] },
				{ x: new Date(year, 7, 1), y: electricity[7] },
				{ x: new Date(year, 8, 1),  y: electricity[8] },
				{ x: new Date(year, 9, 1),  y: electricity[9] },
				{ x: new Date(year, 10, 1),  y: electricity[10] },
				{ x: new Date(year, 11, 1), y: electricity[11] }
			]
		}]
	};
	
	$("#chartContainer").CanvasJSChart(options);
	
	function toggleDataSeries(e) {
		if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
			e.dataSeries.visible = false;
		} else {
			e.dataSeries.visible = true;
		}
		e.chart.render();
	}

}


let getConnection2 =  (function(path,fnc){
	  $.ajax({
		  url : URL + path,
		  type : 'GET',
		  dataType : 'json', 
	      success : function(result) {       
	    	  fnc(result.waterNumbers , result.electricityNumbers, years_char2[index_char2]);
		   },
	      error : function(error) {
	    	  console.log(error); 
		   } 
	 });
});

getConnection2(`api/statistical/number?id=${id_ACA}&&year=${new Date().getFullYear()}`, canvarChar);

document.getElementById('previous_canvasjs').addEventListener('click', () => {	
	index_char2 === 0 ?  index_char2 = 0 : index_char2--;
	document.getElementById('title-canvasjs').textContent = `(Số điện, nước hàng tháng - ${years_char2[index_char2]})`;
	getConnection2(`api/statistical/number?id=${id_ACA}&&year=${years_char2[index_char2]}`,canvarChar);
});

document.getElementById('next_canvasjs').addEventListener('click', () => {	
	index_char2 === (years.length -1) ?  index_char2 = years.length -1 : index_char2++;
	document.getElementById('title-canvasjs').textContent = `(Số điện, nước hàng tháng - ${years_char2[index_char2]})`;
	getConnection2(`api/statistical/number?id=${id_ACA}&&year=${years_char2[index_char2]}`,canvarChar);	
});

