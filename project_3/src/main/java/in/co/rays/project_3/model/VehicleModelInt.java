package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.VehicleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface VehicleModelInt {
	
	public long add(VehicleDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(VehicleDTO dto)throws ApplicationException;
	public void update(VehicleDTO dto)throws ApplicationException,DuplicateRecordException;
	public VehicleDTO findByPK(long pk)throws ApplicationException;
	public VehicleDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(VehicleDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(VehicleDTO dto)throws ApplicationException;
	public List getRoles(VehicleDTO dto)throws ApplicationException;
	



}
