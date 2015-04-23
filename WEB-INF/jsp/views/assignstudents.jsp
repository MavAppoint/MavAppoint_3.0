<%@ page import="java.util.ArrayList" %>
<%@ page  import= "uta.mav.appoint.login.AdvisorUser" %>
<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />

<% ArrayList<AdvisorUser> deptAdvisors = new ArrayList<AdvisorUser>();
           deptAdvisors = (ArrayList<AdvisorUser>)session.getAttribute("deptAdvisors");%>
<div class="container">
<!-- Panel -->
<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading"><h1>Assign Students To Advisors</h1></div>
  <div class="panel-body">
  	<h2>Ranges - Uses the first letter of the last name</h2>
  	<h4>Low - low end of range </h4>
    <h4>High - High end of the range </h4>
  </div>
  <!-- Table -->
   <script>
  
   function myFunction() {
	   lowRange(); 
	}
   
   function lowRange(){
	   var selects = document.getElementsByTagName("select"), len = selects.length, i;
	   for (i = 0; i < len; i++) {
		    if (selects[i].id == "lowRange") {
		        selects[i].value = selects[i].title;
		    }else if (selects[i].id == "highRange") {
		        selects[i].value = selects[i].title;
		    }
   	}
   }
   
   </script>
    <form method= "post">
      <table class="table">
        <thead>
          <tr>
            <th>Advisor</th>
            <th>Low</th>
            <th>High</th>
            <th>Degree</th>
            <th>Major</th>
          </tr>
        </thead>
        <tbody>
       
         <% if (deptAdvisors != null){
        	  
        	  for(int i = 0; i < deptAdvisors.size(); i++){ %>
        		 <tr>
        		 <td><%= deptAdvisors.get(i).getPname()%></td>
        		 <td><select id="lowRange" title ="<%= deptAdvisors.get(i).getNameLow() %>" class="btn btn-default dropdown-toggle  pull-left" data-toggle="dropdown">
					<option value ="A" >A</option>
					<option value ="B" >B</option>
					<option value ="C" >C</option>
					<option value ="D" >D</option>
					<option value ="E" >E</option>
					<option value ="F" >F</option>
					<option value ="G" >G</option>
					<option value ="H" >H</option>
					<option value ="I" >I</option>
					<option value ="J" >J</option>
					<option value ="K" >K</option>
					<option value ="L" >L</option>
					<option value ="M" >M</option>
					<option value ="N" >N</option>
					<option value ="O" >O</option>
					<option value ="P" >P</option>
					<option value ="Q" >Q</option>
					<option value ="R" >R</option>
					<option value ="S" >S</option>
					<option value ="T" >T</option>
					<option value ="U" >U</option>
					<option value ="V" >V</option>
					<option value ="W" >W</option>
					<option value ="X" >X</option>
					<option value ="Y" >Y</option>
					<option value ="Z" >Z</option></select></td>
        		 <td><select id="highRange" title ="<%= deptAdvisors.get(i).getNameHigh() %>" class="btn btn-default dropdown-toggle  pull-left" data-toggle="dropdown">
					<option value ="A" >A</option>
					<option value ="B" >B</option>
					<option value ="C" >C</option>
					<option value ="D" >D</option>
					<option value ="E" >E</option>
					<option value ="F" >F</option>
					<option value ="G" >G</option>
					<option value ="H" >H</option>
					<option value ="I" >I</option>
					<option value ="J" >J</option>
					<option value ="K" >K</option>
					<option value ="L" >L</option>
					<option value ="M" >M</option>
					<option value ="N" >N</option>
					<option value ="O" >O</option>
					<option value ="P" >P</option>
					<option value ="Q" >Q</option>
					<option value ="R" >R</option>
					<option value ="S" >S</option>
					<option value ="T" >T</option>
					<option value ="U" >U</option>
					<option value ="V" >V</option>
					<option value ="W" >W</option>
					<option value ="X" >X</option>
					<option value ="Y" >Y</option>
					<option value ="Z" >Z</option></select></td>
        		 <td><select id="degree" title ="" class="btn btn-default dropdown-toggle  pull-left" data-toggle="dropdown">
		    		 <option value ="all" >All</option>
		    		 <option value ="undergrad" >Undergrad</option>
		    		 <option value ="graduate" >Graduate</option>
		    		 <option value ="doctorate" >Doctorate</option></select></td>
        		 <td><select multiple id="majors" title ="" class="btn btn-default dropdown-toggle  pull-left" data-toggle="dropdown">
		    		 <% for(int m = 0; m < deptAdvisors.get(i).getMajors().size(); m++) {%>
		    		 <option value ="<%=deptAdvisors.get(i).getMajors().get(m) %>" > <%=deptAdvisors.get(i).getMajors().get(m) %></option>
		    		 <%} %></select></td>
        		 </tr> 
        	<%   }
        
        }
          else{ %> 
          <tr><th>Something is Wrong<th></tr>
         <% }%>
        
        </tbody>
      </table>
      <div class="panel-footer text-right"><input type="submit" class="btn-lg" value="Submit"></div>
       
      </form>
    </div>
   
   </div>
   
    
<%@include file="templates/footer.jsp"%>