package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.WishDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface WishModelInt {
	
	public long add(WishDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(WishDTO dto)throws ApplicationException;
	public void update(WishDTO dto)throws ApplicationException,DuplicateRecordException;
	public WishDTO findByPK(long pk)throws ApplicationException;
	public WishDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(WishDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(WishDTO dto)throws ApplicationException;
	public List getRoles(WishDTO dto)throws ApplicationException;
	


}
