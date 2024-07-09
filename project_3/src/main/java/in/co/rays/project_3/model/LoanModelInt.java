package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.LoanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface LoanModelInt {
	
	
	public long add(LoanDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(LoanDTO dto)throws ApplicationException;
	public void update(LoanDTO dto)throws ApplicationException,DuplicateRecordException;
	public LoanDTO findByPK(long pk)throws ApplicationException;
	public LoanDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(LoanDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(LoanDTO dto)throws ApplicationException;
	public List getRoles(LoanDTO dto)throws ApplicationException;
	


}
