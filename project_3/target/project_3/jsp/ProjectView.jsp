



<%@page import="in.co.rays.project_3.controller.ProjectCtl"%>
<%@page import="java.util.List"%>

<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project view</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
	background-image: linear-gradient(to bottom right, purple, black);
	background-repeat: no repeat;
	background-size: 100%;
	padding-bottom: 11px;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/download (3).jpeg');
	background-size: cover;
	padding-top: 6%;
}
</style>

</head>
<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.PROJECT_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ProjectDTO"
				scope="request"></jsp:useBean>
			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null && id > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update
								Project</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Project</h3>
							<%
								}
							%>
							<!--Body-->
							<%-- <div>
								<%
									List list = (List) request.getAttribute("product");
								%> --%>

								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">

							</div>

							<div class="md-form">

								<span class="pl-sm-5"><b> Name</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="name"
											placeholder="Enter Name"
											value="<%=DataUtility.getStringData(dto.getName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("name", request)%></font></br>
								
								<span class="pl-sm-5"><b>Category</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-circle grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<%-- <input type="text" class="form-control" name="category"
											placeholder=" Enter Category"
											value="<%=DataUtility.getStringData(dto.getCategory())%>">
									 --%>
									
									<%
											HashMap map = new HashMap();
											map.put("static", "static");
 											map.put("dynamic", "dynamic");

											String htmlList = HTMLUtility.getList("category", dto.getCategory(), map);
										%>
										<%=htmlList%>
										
										</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("category", request)%></font></br>
								
								<span class="pl-sm-5"><b>Version</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-address-card grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="version"
											placeholder=" Enter Version"
											value="<%=DataUtility.getStringData(dto.getVersion())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("version", request)%></font></br>
								
								
			
								
								<span class="pl-sm-5"><b>OpenDate</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" id="datepicker" name="openDate"
											class="form-control" placeholder="OpenDate" readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getOpenDate())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("openDate", request)%></font></br>
								
								
									
									
								
								
								
								
<%-- 									<%=HTMLUtility.getList("product",String.valueOf(dto.getProduct()),list) %>
 --%>								
								<%-- 
								<%
								if (dto.getId()==null||id<=0) {
								%> --%>





																<%
									if (dto.getId() != null && id > 0) {
								%>

								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=ProjectCtl.OP_UPDATE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=ProjectCtl.OP_CANCEL%>">

								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=ProjectCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=ProjectCtl.OP_RESET%>">
								</div>

							</div>
							<%
								}
							%>
						</div>
					</div>
		</form>
		</main>
		<div class="col-md-4 mb-4"></div>

	</div>

</body>
<%@include file="FooterView.jsp"%>

</body>
</html>


