<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%  String message = (String)session.getAttribute("message");%>

<div class="container">
	<form action="#" method="post">
		<div class="row">
			<label for="message"><font color="#e67e22" size="4"><%=message%></label>
			<div class="col-md-4 col-lg-4">
				<div class="form-group"> 
				
					<label for="currentpassword"><font color="#e67e22" size="4">Current Password</label> 
					<br>
					<input type="password" class="form-control" name=currentpassword>
					
					<label for="password"><font color="#e67e22" size="4">New Password</label> 
					<br>
					<input type="password" class="form-control" name=password>
					
					<label for="repeatPassword"><font color="#e67e22" size="4">Repeat Password</label> 
					<br>
					<input type="password" class="form-control" name=repeatpassword>
				</div>
			</div>
		</div>
		<button type="submit" class="btn btn-primary">Submit</button>
		</p>
<%@include file="templates/footer.jsp"%>