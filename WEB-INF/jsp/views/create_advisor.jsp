<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%@ page import="uta.mav.appoint.login.Department"%>

<%   ArrayList<Department> departments = (ArrayList<Department>)session.getAttribute("departments"); %>
<style>
.resize {
width: 60%;
}
.resize-body {
width: 80%;
}


</style>
<div class="container">
<!-- Panel -->
<div class="panel panel-default resize center-block">
<!-- Default panel contents -->
<form action="create_advisor" method="post" name="advisor_form" onsubmit="return false;">
<div class="panel-heading text-center"><h1>Create New Advisor</h1></div>
<div class="panel-body resize-body center-block">


	
		<div class="form-group">
		
			<label for="drp_department"><font color="#0" size="4">Departments</font></label> 
			<br>
			<select id="drp_department" name="drp_department" class="btn btn-default btn-lg dropdown-toggle">
				<%
				for (int i=0;i<departments.size();i++)
				{%>
					<option value=<%=i%> ><%=departments.get(i).getName()%></option>
			<%	}%>
			</select> 
			<br>
					
			<label for="emailAddress"><font color="#0" size="4">Email
					Address</font></label><br> <input type="text" style="width: 350px;"
				class="form-control" id="emailAddress" placeholder="">
			<label for="pname"><font color="#0" size="4">Display
					Name</font></label><br> <input type="text" style="width: 350px;"
				class="form-control" id="pname" placeholder="">

			<label for="isLead"><font color="#0">Lead Advisor</font></label><br>
			<select id="isLead" class="btn btn-default btn-lg dropdown-toggle">
				<option value=1>True</option>
				<option value=0>False</option>
			</select> <br>
		</div>

		</div>
		<div class= "panel-footer text-center">
      	<input onclick="javascript:FormSubmit();" type="submit" class="btn-lg" value="Submit">
      	</div>
	</form>

	<label id="result"><font color="#0" size="4"></font></label>

</div>
</div>

<script> function FormSubmit(){
									var email = document.getElementById("emailAddress").value;
									var pname = document.getElementById("pname").value;
									var isLead = document.getElementById("isLead").value;
									var drp_department = document.getElementById("drp_department").value;
									var params = ('emailAddress='+email+'&pname='+pname+'&isLead='+isLead+'&drp_department='+drp_department);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											document.getElementById("result").innerHTML = xmlhttp.responseText;	
										}
									}
									xmlhttp.open("POST","create_advisor",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
									document.getElementById("result").innerHTML = "Attempting to create new Advisor...";
								}
								</script>
<%@include file="templates/footer.jsp"%>