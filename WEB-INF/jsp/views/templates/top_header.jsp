<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>MavAppoint</title>

<meta name="description"
	content="Full view calendar component for twitter bootstrap with year, month, week, day views.">
<meta name="keywords"
	content="jQuery,Bootstrap,Calendar,HTML,CSS,JavaScript,responsive,month,week,year,day">
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE">
<meta charset="UTF-8">

<link rel="stylesheet"
	href="components/bootstrap3/css/bootstrap.css">
<link rel="stylesheet" href="components/bootstrap3/css/bootstrap-datetimepicker.min.css">
<link href="components/mavappoint.css" rel="stylesheet"/>
<link rel="stylesheet" href="css/fullcalendar.css">
<link rel="icon" href="img/mavlogo.gif" type="image/x-icon">

<script type="text/javascript" src="components/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="components/underscore/underscore-min.js"></script>
<script type="text/javascript"
	src="components/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="components/jstimezonedetect/jstz.min.js"></script>
<script type="text/javascript" src="js/lib/moment.min.js"></script>
<script type="text/javascript" src="js/fullcalendar.js"></script>
<script type="text/javascript"
	src="components/bootstrap3/js/bootstrap-datetimepicker.min.js"></script>
</head>
<%String load = new String();
if (request.getRequestURI().contains("assignstudents")){
	load = "assignStudents()";
}else{
	load = "";
}%>
<body onload="<%=load%>" >
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div id="inversenavbar" class="container-fluid"
			style="background-color: #104E8B;">
			<div class="navbar-header">
				<a class="navbar-brand" href="index"> <b> <font
						style="color: #e67e22" size="6"> MavAppoint </font></b></a>
			</div>
