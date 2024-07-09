package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.VehicleTrackingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface VehicleTrackingModelInt {
	
	public long add(VehicleTrackingDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(VehicleTrackingDTO dto)throws ApplicationException;
	public void update(VehicleTrackingDTO dto)throws ApplicationException,DuplicateRecordException;
	public VehicleTrackingDTO findByPK(long pk)throws ApplicationException;
	public VehicleTrackingDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(VehicleTrackingDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(VehicleTrackingDTO dto)throws ApplicationException;
	public List getRoles(VehicleTrackingDTO dto)throws ApplicationException;
	



}
