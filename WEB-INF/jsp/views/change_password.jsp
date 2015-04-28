<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%  String message = (String)session.getAttribute("message");%>


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
<form action="#" method="post">
  <!-- Default panel contents -->
  <div class="panel-heading text-center"><h1>Change Password</h1></div>
  <label style="text-align: center" for="message"><font color="#0" size="4"><%=message%></font></label> <br>
  <div class="panel-body resize-body center-block">
  		
				<div class="form-group"> 
				
					<label for="currentpassword"><font color="#0" size="4">Current Password</font></label> 
					<br>
					<input type="password" class="form-control" name=currentpassword>
					
					<label for="password"><font color="#0" size="4">New Password (Length 8+ Required)</font></label> 
					<br>
					<input type="password" class="form-control" name=password>
					
					<label for="repeatPassword"><font color="#0" size="4">Repeat Password</font></label> 
					<br>
					<input type="password" class="form-control" name=repeatpassword>
				</div>
			
		</div>
		<div class="panel-footer text-center">
      	<input type="submit" class="btn-lg" value="Submit">
      </div>
      </form>
		</div>
		</div>


<%@include file="templates/footer.jsp"%>