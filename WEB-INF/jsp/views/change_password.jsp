<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%  String message = (String)session.getAttribute("message");%>

<div class="container">
	<form action="#" method="post">
		<div class="row">
		<div class="container">
<!-- Panel -->

<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading"><h1>Change Password</h1></div>
  <label for="message"><font color="#0" size="4"><%=message%></label> <br>
  <div class="panel-body">
			<div class="col-md-4 col-lg-4">
				<div class="form-group"> 
				
					<label for="currentpassword"><font color="#0" size="4">Current Password</label> 
					<br>
					<input type="password" class="form-control" name=currentpassword>
					
					<label for="password"><font color="#0" size="4">New Password (Length 8+ Required)</label> 
					<br>
					<input type="password" class="form-control" name=password>
					
					<label for="repeatPassword"><font color="#0" size="4">Repeat Password</label> 
					<br>
					<input type="password" class="form-control" name=repeatpassword>
				</div>
			</div>
		</div>
		<div class="panel-footer text-center">
      	<input type="submit" class="btn-lg" value="Submit">
      </div>
		</div>
		</div>
		</div>
		</p>
<%@include file="templates/footer.jsp"%>