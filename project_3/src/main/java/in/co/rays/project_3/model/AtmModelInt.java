package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AtmDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface AtmModelInt {
	
	public long add(AtmDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(AtmDTO dto)throws ApplicationException;
	public void update(AtmDTO dto)throws ApplicationException,DuplicateRecordException;
	public AtmDTO findByPK(long pk)throws ApplicationException;
	public AtmDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(AtmDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(AtmDTO dto)throws ApplicationException;
	public List getRoles(AtmDTO dto)throws ApplicationException;
	

}
