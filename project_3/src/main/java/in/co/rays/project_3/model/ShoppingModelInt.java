package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ShoppingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ShoppingModelInt {
	
	
	public long add(ShoppingDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(ShoppingDTO dto)throws ApplicationException;
	public void update(ShoppingDTO dto)throws ApplicationException,DuplicateRecordException;
	public ShoppingDTO findByPK(long pk)throws ApplicationException;
	public ShoppingDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(ShoppingDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(ShoppingDTO dto)throws ApplicationException;
	public List getRoles(ShoppingDTO dto)throws ApplicationException;
	

}
