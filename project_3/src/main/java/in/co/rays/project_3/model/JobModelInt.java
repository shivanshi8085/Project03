package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface JobModelInt {
	
	public long add(JobDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(JobDTO dto)throws ApplicationException;
	public void update(JobDTO dto)throws ApplicationException,DuplicateRecordException;
	public JobDTO findByPK(long pk)throws ApplicationException;
	public JobDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(JobDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(JobDTO dto)throws ApplicationException;
	public List getRoles(JobDTO dto)throws ApplicationException;
	

}
