<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%@ page import="uta.mav.appoint.login.Department"%>

<%
 ArrayList<Department> departments = (ArrayList<Department>)session.getAttribute("departments");
 ArrayList<String> degreeType = (ArrayList<String>)session.getAttribute("degreeType");
 ArrayList<String> major = (ArrayList<String>)session.getAttribute("major");
 String message = (String)session.getAttribute("message");
%>
<style>
.resize {
width: 60%;
}
.resize-body {
width: 80%;
}


</style>

<div class="container block">
<!-- Panel -->
<div class="panel panel-default resize center-block">
<!-- Default panel contents -->
	<div class="panel-heading text-center"><h1>Register Student</h1></div>
		<form action="register" method="post" name="register_form">
		<div class="panel-body center-block resize-body">
			
					<label for="message"><font color="#e67e22" size="4"><%=message%></font></label>
						<div class="">
							<div class="form-group">
				
								<input  type='hidden' id='submitted' name='submitted'/>
								<script>
								document.getElementById("submitted").value = true;
							</script>
							
					        <label for="drp_department"><font color="#0" size="4">Departments</font></label> 
							<br>
							<select onchange="change();" id="drp_department" name="drp_department" class="btn btn-default btn-lg dropdown-toggle">
								<%
								for (int i=0;i<departments.size();i++)
								{%>
									<option value=<%=i%> ><%=departments.get(i).getName()%></option>
							<%	}%>
							</select> 
							<br>
							
							<script> 
							function change()
							{
								document.getElementById("submitted").value = false;
								register_form.submit();
							}
							</script>
							
					        <label for="drp_degreeType"><font color="#0" size="4">Degree Type</font></label> 
							<br>
							<select id="drp_degreeType" name="drp_degreeType" class="btn btn-default btn-lg dropdown-toggle">
								<%
												for (int i=0, j=1;i<degreeType.size();i++, j*=2){
													
													%>
										<option value=<%=j%>><%=degreeType.get(i)%></option>
										<%	}%>
							</select> 
							<br>
					
					        <label for="drp_major"><font color="#0" size="4">Major</font></label> 
							<br>
							<select id="drp_major" name="drp_major" class="btn btn-default btn-lg dropdown-toggle">
									<%
												for (int i=0;i<major.size();i++){
													
													%>
										<option value=<%=i%>><%=major.get(i)%></option>
										<%	}%>
							</select> 
							<br>
							
					        <label for="drp_last_name_initial"><font color="#0" size="4">Last Name Initial</font></label> 
							<br>
							<select id="drp_last_name_initial" name="drp_last_name_initial" class="btn btn-default btn-lg dropdown-toggle">
								<% for (char letter='A';letter<'Z';letter++){ %>
										<option value=<%=letter%>><%=letter%></option>
								<%	}%>
							</select> 
							<br>
							
							<label for="student_Id"><font color="#0" size="4">Student ID</font></label> 
							<br>
							<input type="text" class="form-control" name=student_Id placeholder="1000xxxxxx or 6000xxxxxx">
							
							<label for="phone_num"><font color="#0" size="4">Phone Number</font></label> 
							<br>
							<input type="text" class="form-control" name=phone_num placeholder="xxx-xxx-xxxx">
							
							<label for="emailAddress"><font color="#0" size="4">Email Address</font></label> 
							<br>
							<input type="text" class="form-control" name=emailAddress placeholder="firstname.lastname@mavs.uta.edu"> 
						    <br>
						</div>
					</div>
				
				</div>
				<div  class="panel-footer text-center">
				    <input type="submit" class="btn-lg" value="Submit">
				</div>
			</form>
		</div>
	</div>
		
	

		<%@include file="templates/footer.jsp"%>