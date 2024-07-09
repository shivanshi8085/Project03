


<%@page import="in.co.rays.project_3.controller.AtmCtl"%>
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
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Atm view</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="<%=ORSView.APP_CONTEXT%>/js/utilities.js"></script>
<script type="text/javascript">
	function validateMobileNo(event) {
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '')
		if (input.value.length > 0 && input.value[0] <= '5') {
			input.value = '';
		}
	}
</script>

<script>
	function validateNumberKey(event, validationMessageId) {
		// Allow only digits, minus sign, and dot for latitude and longitude input
		if (!/[0-9\.\-]/.test(event.key)) {
			// Show validation message
			var validationMsg = document.getElementById(validationMessageId);
			validationMsg.style.display = 'inline';
			event.preventDefault(); // Prevent typing the invalid character
		} else {
			// Hide validation message if input is valid
			var validationMsg = document.getElementById(validationMessageId);
			validationMsg.style.display = 'none';
		}
	}
</script>

<script type="text/javascript">
	function numberLength(input) {
		if (input.value.length > 10) {
			input.value = input.value.slice(0, 10);
		}
	}
</script>
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
	background-image: linear-gradient(to bottom right, purple, white);
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
		<form action="<%=ORSView.ATM_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AtmDTO"
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
							<h3 class="text-center default-text text-primary">Update Atm</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Atm</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
								<%
									HashMap map = (HashMap) request.getAttribute("locationn");
								%>
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

								<span class="pl-sm-5"><b> Remark</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-list grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<textarea name="remark" placeholder="Enter Remark"
											class="form-control"
											oninput="handleLetterInput(this, 'remarkError', 200)"
											onblur="validateLetterInput(this, 'remarkError', 200)"
											rows="5" cols="5"><%=DataUtility.getStringData(dto.getRemark())%></textarea>

									</div>
								</div>

								<font color="red" class="pl-sm-5" id="remarkError"> <%=ServletUtility.getErrorMessage("remark", request)%></font></br>


								<span class="pl-sm-5"><b>Cash_Available</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-venus-mars grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control"
											name="cash_available" placeholder=" Enter Cash_Available"
											oninput="handleDoubleInput(this, 'cash_availableError', 6)"
											onblur="validateDoubleInput(this, 'cash_availableError', 6)"
											oninput="numberLength(this)" step="any"
											value="<%=DataUtility.getDoublee(dto.getCash_available())%>"
											onkeypress="validateNumberKey(event, 'cash_available-validation-message')">

									</div>
									<span id="cash_available-validation-message"
										style="display: none; color: red;">Only number are
										allowed </span>
								</div>
								<font color="red" class="pl-sm-5" id="cash_availableError">
									<%=ServletUtility.getErrorMessage("cash_available", request)%></font></br>


								<span class="pl-sm-5"><b>Location</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-address-card grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<%=HTMLUtility.getList("location", String.valueOf(dto.getLocation()), map)%>


									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("location", request)%></font></br>





								<span class="pl-sm-5"><b>Last_restocked_date</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" id="datepicker" name="last_restocked_date"
											class="form-control" placeholder="Enter Last_restocked_date"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getLast_restocked_date())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("last_restocked_date", request)%></font></br>







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
										value="<%=AtmCtl.OP_UPDATE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=AtmCtl.OP_CANCEL%>">

								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=AtmCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=AtmCtl.OP_RESET%>">
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


