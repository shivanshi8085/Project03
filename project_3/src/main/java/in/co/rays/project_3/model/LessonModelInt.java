package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.LessonDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface LessonModelInt {
	
	public long add(LessonDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(LessonDTO dto)throws ApplicationException;
	public void update(LessonDTO dto)throws ApplicationException,DuplicateRecordException;
	public LessonDTO findByPK(long pk)throws ApplicationException;
	public LessonDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(LessonDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(LessonDTO dto)throws ApplicationException;
	public List getRoles(LessonDTO dto)throws ApplicationException;
	


}
