<%@page import="in.co.rays.project_3.controller.IssueCtl"%>
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
<title>Issue view</title>
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
		<form action="<%=ORSView.ISSUE_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.IssueDTO"
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
								Issue</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Issue</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
								

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

								<span class="pl-sm-5"><b> AssinTo</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="assinTo"
											placeholder="Enter AssinTo"
											value="<%=DataUtility.getStringData(dto.getAssinTo())%>">
											
											
									</div>
								</div>
								
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("assinTo", request)%></font></br>
								
								<span class="pl-sm-5"><b>Title</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-circle grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="title"
											placeholder=" Enter Title"
											value="<%=DataUtility.getStringData(dto.getTitle())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("title", request)%></font></br>
								
								<span class="pl-sm-5"><b>Status</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-address-card grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<%-- <input type="text" class="form-control" name="status"
											placeholder=" Enter Status"
											value="<%=DataUtility.getStringData(dto.getStatus())%>"> --%>
											
											<%
											HashMap map = new HashMap();
											map.put("Open", "Open");
											map.put("In Progress", "In Progress");
											map.put("Hold", "Hold");
											map.put("Resolve", "Resolve");
											map.put("Close", "Close");

											String htmlList = HTMLUtility.getList("status", dto.getStatus(), map);
										%>
										<%=htmlList%>
											
											
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%></font></br>
								
								
			<span class="pl-sm-5"><b>Description</b><span style="color:red;">*</span></span>
								</br> <div class="col-sm-12">
                <div class="input-group">
                <div class="input-group-prepend">
                <div class="input-group-text"><i class="fa fa-list grey-text" style="font-size: 1rem;"></i> </div>
        </div>
       <textarea name="description" placeholder="Enter description" class="form-control"
		 rows="5" cols="5"><%=DataUtility.getStringData(dto.getDescription())%></textarea>

      </div>
    </div>
								
								<span class="pl-sm-5"><b>OpenDate</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="date" name="openDate"
											class="form-control" placeholder="OpenDate" 
											value="<%=DataUtility.getDateString(dto.getOpenDate())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("openDate", request)%></font></br>
								
								
									
									
								
								
								
								
								
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
										value="<%=IssueCtl.OP_UPDATE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=IssueCtl.OP_CANCEL%>">

								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=IssueCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=IssueCtl.OP_RESET%>">
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


