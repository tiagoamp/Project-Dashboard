// Load the Visualization API and the piechart package. 
google.load('visualization', '1.0', {'packages':['corechart']}); 
// Set a callback to run when the Google Visualization API is loaded. 
google.setOnLoadCallback(drawChart); 
// Callback that creates and populates a data table, instantiates the pie chart, passes in the data and draws it. 
function drawChart() { 
// Create the data table. 
var data = google.visualization.arrayToDataTable([ 
['Status', 'Itens'], 
['TO DO',3], 
['DOING',1], 
['DONE', 1], 
['TESTS', 1], 
['BLOCKED', 1], 
]); 
// Create the data table. 
var data3 = google.visualization.arrayToDataTable([ 
['Status', 'Itens'], 
['DONE', 1], 
['NOT READY', 6], 
]); 
// Create the data table. 
var data4 = google.visualization.arrayToDataTable([ 
['Status', 'Itens'], 
['DONE', 20], 
['NOT READY', 190.0], 
]); 
// Create the data table. 
var data2 = new google.visualization.DataTable(); 
data2.addColumn('number', 'Dias'); 
data2.addColumn('number', 'Planejado'); 
data2.addColumn('number', 'Executado'); 
data2.addRows([ 
[0,210.00,210.00], <!-- Jan 1, 2016--> 
[1,208.71,210.00], <!-- Jan 2, 2016--> 
[2,207.42,210.00], <!-- Jan 3, 2016--> 
[3,206.13,210.00], <!-- Jan 4, 2016--> 
[4,204.84,210.00], <!-- Jan 5, 2016--> 
[5,203.55,210.00], <!-- Jan 6, 2016--> 
[6,202.26,210.00], <!-- Jan 7, 2016--> 
[7,200.97,210.00], <!-- Jan 8, 2016--> 
[8,199.68,210.00], <!-- Jan 9, 2016--> 
[9,198.39,210.00], <!-- Jan 10, 2016--> 
[10,197.10,210.00], <!-- Jan 11, 2016--> 
[11,195.81,210.00], <!-- Jan 12, 2016--> 
[12,194.52,210.00], <!-- Jan 13, 2016--> 
[13,193.23,210.00], <!-- Jan 14, 2016--> 
[14,191.94,20.00], <!-- Jan 15, 2016--> 
[15,190.65,210.00], <!-- Jan 16, 2016--> 
[16,189.35,210.00], <!-- Jan 17, 2016--> 
[17,188.06,210.00], <!-- Jan 18, 2016--> 
[18,186.77,210.00], <!-- Jan 19, 2016--> 
[19,185.48,210.00], <!-- Jan 20, 2016--> 
[20,184.19,210.00], <!-- Jan 21, 2016--> 
[21,182.90,210.00], <!-- Jan 22, 2016--> 
[22,181.61,210.00], <!-- Jan 23, 2016--> 
[23,180.32,210.00], <!-- Jan 24, 2016--> 
[24,179.03,210.00], <!-- Jan 25, 2016--> 
[25,177.74,210.00], <!-- Jan 26, 2016--> 
[26,176.45,210.00], <!-- Jan 27, 2016--> 
[27,175.16,210.00], <!-- Jan 28, 2016--> 
[28,173.87,210.00], <!-- Jan 29, 2016--> 
[29,172.58,210.00], <!-- Jan 30, 2016--> 
[30,171.29,210.00], <!-- Jan 31, 2016--> 
[31,170.00,210.00], <!-- Feb 1, 2016--> 
[32,165.52,210.00], <!-- Feb 2, 2016--> 
[33,161.03,210.00], <!-- Feb 3, 2016--> 
[34,156.55,210.00], <!-- Feb 4, 2016--> 
[35,152.07,210.00], <!-- Feb 5, 2016--> 
[36,147.59,210.00], <!-- Feb 6, 2016--> 
[37,143.10,210.00], <!-- Feb 7, 2016--> 
[38,138.62,210.00], <!-- Feb 8, 2016--> 
[39,134.14,210.00], <!-- Feb 9, 2016--> 
[40,129.66,210.00], <!-- Feb 10, 2016--> 
[41,125.17,210.00], <!-- Feb 11, 2016--> 
[42,120.69,210.00], <!-- Feb 12, 2016--> 
[43,116.21,210.00], <!-- Feb 13, 2016--> 
[44,111.72,210.00], <!-- Feb 14, 2016--> 
[45,107.24,210.00], <!-- Feb 15, 2016--> 
[46,102.76,210.00], <!-- Feb 16, 2016--> 
[47,98.28,210.00], <!-- Feb 17, 2016--> 
[48,93.79,210.00], <!-- Feb 18, 2016--> 
[49,89.31,210.00], <!-- Feb 19, 2016--> 
[50,84.83,210.00], <!-- Feb 20, 2016--> 
[51,80.34,210.00], <!-- Feb 21, 2016--> 
[52,75.86,210.00], <!-- Feb 22, 2016--> 
[53,71.38,210.00], <!-- Feb 23, 2016--> 
[54,66.90,210.00], <!-- Feb 24, 2016--> 
[55,62.41,210.00], <!-- Feb 25, 2016--> 
[56,57.93,210.00], <!-- Feb 26, 2016--> 
[57,53.45,210.00], <!-- Feb 27, 2016--> 
[58,48.97,210.00], <!-- Feb 28, 2016--> 
[59,44.48,210.00], <!-- Feb 29, 2016--> 
[60,40.00,210.00], <!-- Mar 1, 2016--> 
[61,38.71,210.00], <!-- Mar 2, 2016--> 
[62,37.42,210.00], <!-- Mar 3, 2016--> 
[63,36.13,210.00], <!-- Mar 4, 2016--> 
[64,34.84,210.00], <!-- Mar 5, 2016--> 
[65,33.55,210.00], <!-- Mar 6, 2016--> 
[66,32.26,210.00], <!-- Mar 7, 2016--> 
[67,30.97,210.00], <!-- Mar 8, 2016--> 
[68,29.68,210.00], <!-- Mar 9, 2016--> 
[69,28.39,210.00], <!-- Mar 10, 2016--> 
[70,27.10,210.00], <!-- Mar 11, 2016--> 
[71,25.81,210.00], <!-- Mar 12, 2016--> 
[72,24.52,210.00], <!-- Mar 13, 2016--> 
[73,23.23,210.00], <!-- Mar 14, 2016--> 
[74,21.94,210.00], <!-- Mar 15, 2016--> 
[75,20.65,210.00], <!-- Mar 16, 2016--> 
[76,19.35,210.00], <!-- Mar 17, 2016--> 
[77,18.06,210.00], <!-- Mar 18, 2016--> 
[78,16.77,210.00], <!-- Mar 19, 2016--> 
[79,15.48,210.00], <!-- Mar 20, 2016--> 
[80,14.19,210.00], <!-- Mar 21, 2016--> 
[81,12.90,210.00], <!-- Mar 22, 2016--> 
[82,11.61,210.00], <!-- Mar 23, 2016--> 
[83,10.32,210.00], <!-- Mar 24, 2016--> 
[84,9.03,210.00], <!-- Mar 25, 2016--> 
[85,7.74,210.00], <!-- Mar 26, 2016--> 
[86,6.45,210.00], <!-- Mar 27, 2016--> 
[87,5.16,210.00], <!-- Mar 28, 2016--> 
[88,3.87,210.00], <!-- Mar 29, 2016--> 
[89,2.58,210.00], <!-- Mar 30, 2016--> 
[90,1.29,210.00], <!-- Mar 31, 2016--> 
[91,0.00,0.00], <!-- Apr 1, 2016--> 
]);
// Set chart options 
var options = {'title':'Itens per status', 
'width':300, 
'height':180}; 
// Set chart options 
var options3 = {'title':'Work Done (itens)', 
'width':300, 
'height':180}; 
// Set chart options 
var options4 = {'title':'Work Done (points)', 
'width':300, 
'height':180}; 
var options2 = {'title':'Burndown (ptos de função X qtd dias)', 
'width':700, 
'height':400}; 
// Instantiate and draw our chart, passing in some options. 
var chart = new google.visualization.PieChart(document.getElementById('chart_div')); 
chart.draw(data, options); 
var chart3 = new google.visualization.PieChart(document.getElementById('chart_div3')); 
chart3.draw(data3, options3); 
var chart4 = new google.visualization.PieChart(document.getElementById('chart_div4')); 
chart4.draw(data4, options4); 
var chart2 = new google.visualization.LineChart(document.getElementById('chart_div2')); 
chart2.draw(data2, options2); 
} 
