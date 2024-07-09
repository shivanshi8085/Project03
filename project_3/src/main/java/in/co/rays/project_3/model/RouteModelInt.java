package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.RouteDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface RouteModelInt {
	
	public long add(RouteDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(RouteDTO dto)throws ApplicationException;
	public void update(RouteDTO dto)throws ApplicationException,DuplicateRecordException;
	public RouteDTO findByPK(long pk)throws ApplicationException;
	public RouteDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(RouteDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(RouteDTO dto)throws ApplicationException;
	public List getRoles(RouteDTO dto)throws ApplicationException;
	


}
