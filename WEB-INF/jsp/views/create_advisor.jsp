<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%@ page import="uta.mav.appoint.login.Department"%>

<%   ArrayList<Department> departments = (ArrayList<Department>)session.getAttribute("departments"); %>
<div class="container">
<div class="container">
<!-- Panel -->
<div class="panel panel-default">
<!-- Default panel contents -->
<div class="panel-heading"><h1>Create New Advisor</h1></div>
<div class="panel-body">


	<form action="create_advisor" method="post" name="advisor_form" onsubmit="return false;">
		<div class="form-group">
		
			<label for="drp_department"><font color="#0" size="4">Departments</label> 
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
					Address</label><br> <input type="text" style="width: 350px;"
				class="form-control" id="emailAddress" placeholder="">
			<label for="pname"><font color="#0" size="4">Display
					Name</label><br> <input type="text" style="width: 350px;"
				class="form-control" id="pname" placeholder="">

			<label for="isLead"><font color="#0">Lead Advisor</label><br>
			<select id="isLead" class="btn btn-default btn-lg dropdown-toggle">
				<option value=1>True</option>
				<option value=0>False</option>
			</select> <br>
		</div>

		<br> <input type="submit" value="submit"
			onclick="javascript:FormSubmit();">
	</form>
	<br>
	<label id="result"><font color="#0" size="4"></font></label>
</div>
</div>
</div>
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