package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VehicleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.VehicleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;


@WebServlet(name = "VehicleCtl", urlPatterns = { "/ctl/VehicleCtl" })

public class VehicleCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("MP04CC5326", "MP04CC5326");
		map.put("MP09HK1238", "MP09HK1238");
		map.put("MP17SD8203", "MP17SD8203");
		map.put("MP11CC6126", "MP11CC6126");
		map.put("MP13CC5922", "MP13CC5922");
		map.put("MP02SD9409", "MP02SD9409");
		map.put("MP10CC0043", "MP10CC0043");

		
		
		request.setAttribute("numberr", map);
		
		
		

		
		 	}
	
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("number"))) {
			request.setAttribute("number", PropertyReader.getValue("error.require", "number"));
			pass = false;
		}

		
			

		if (DataValidator.isNull(request.getParameter("purchaseDate"))) {
			request.setAttribute("purchaseDate", PropertyReader.getValue("error.require", " purchaseDate"));

			pass = false;
		

		}
		if (DataValidator.isNull(request.getParameter("insuranceAmount"))) {
			request.setAttribute("insuranceAmount", PropertyReader.getValue("error.require", "insuranceAmount"));
			pass = false;
		}

		

				  
		
		if (DataValidator.isNull(request.getParameter("colour"))) {
			request.setAttribute("colour", PropertyReader.getValue("error.require", "colour"));
			pass = false;
		}

		

		 

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		VehicleDTO dto = new VehicleDTO();


		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setColour(DataUtility.getString(request.getParameter("colour")));
		dto.setInsuranceAmount(DataUtility.getInt(request.getParameter("insuranceAmount")));
		dto.setNumber(DataUtility.getString(request.getParameter("number")));

        dto.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));



		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		VehicleModelInt model = ModelFactory.getInstance().getVehicleModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			VehicleDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		VehicleModelInt model = ModelFactory.getInstance().getVehicleModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			VehicleDTO dto = (VehicleDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			VehicleDTO dto = (VehicleDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VEHICLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.VEHICLE_VIEW;
	}


}
