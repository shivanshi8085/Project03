package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.LessonDTO;
import in.co.rays.project_3.dto.LessonDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class LessonModelHibImp implements LessonModelInt{

	@Override
	public long add(LessonDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 LessonDTO existDto = null;
			
			Session session = HibDataSource.getSession();
			Transaction tx = null;
			try {

				tx = session.beginTransaction();

				session.save(dto);

				dto.getId();
				tx.commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				if (tx != null) {
					tx.rollback();

				}
				throw new ApplicationException("Exception in Lesson Add " + e.getMessage());
			} finally {
				session.close();
			}

		
		return dto.getId();
	}

	@Override
	public void delete(LessonDTO dto) throws ApplicationException {
		
		
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Lesson Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update(LessonDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		/*
		 * Transaction tx = null; LessonDTO exesistDto = findByLogin(dto.getLogin());
		 * 
		 * if (exesistDto != null && exesistDto.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("Login id already exist"); }
		 */
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Lesson update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public LessonDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		LessonDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (LessonDTO) session.get(LessonDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Lesson by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public LessonDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		LessonDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(LessonDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (LessonDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Lesson by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(LessonDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Lesson list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(LessonDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<LessonDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(LessonDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				
				  if (dto.getChapterNo() != null && dto.getChapterNo().length() > 0) {
				  criteria.add(Restrictions.like("chapterNo", dto.getChapterNo() + "%"));
				  }
				  if (dto.getSubject() != null && dto.getSubject().length() > 0) {
					  criteria.add(Restrictions.like("subject", dto.getSubject() + "%"));
					}
			   if (dto.getDate() != null && dto.getDate().getDate() > 0) {
					criteria.add(Restrictions.eq("date", dto.getDate()));
				}
				
				
			}
			
			System.out.println("searchcalll");
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<LessonDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Lesson search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(LessonDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(LessonDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
