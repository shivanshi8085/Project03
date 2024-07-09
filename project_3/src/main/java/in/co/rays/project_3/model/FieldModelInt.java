package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.FieldDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface FieldModelInt {
	
	public long add(FieldDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(FieldDTO dto)throws ApplicationException;
	public void update(FieldDTO dto)throws ApplicationException,DuplicateRecordException;
	public FieldDTO findByPK(long pk)throws ApplicationException;
	public FieldDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(FieldDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(FieldDTO dto)throws ApplicationException;
	public List getRoles(FieldDTO dto)throws ApplicationException;


}
