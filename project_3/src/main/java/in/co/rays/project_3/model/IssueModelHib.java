package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


import in.co.rays.project_3.dto.IssueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class IssueModelHib implements IssueModelInt{

	@Override
	public long add(IssueDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 IssueDTO existDto = null;
			
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
				throw new ApplicationException("Exception in Order Add " + e.getMessage());
			} finally {
				session.close();
			}

		
		return dto.getId();
	}

	@Override
	public void delete(IssueDTO dto) throws ApplicationException {
		
		
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
			throw new ApplicationException("Exception in Order Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update(IssueDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		
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
			throw new ApplicationException("Exception in Order update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public IssueDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		IssueDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (IssueDTO) session.get(IssueDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public IssueDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		IssueDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(IssueDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (IssueDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Order by Login " + e.getMessage());

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
			Criteria criteria = session.createCriteria(IssueDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Order list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(IssueDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<IssueDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(IssueDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getAssinTo() != null && dto.getAssinTo().length() > 0) {
					criteria.add(Restrictions.like("assinTo", dto.getAssinTo() + "%"));
				}
				
				  if (dto.getTitle() != null && dto.getTitle().length() > 0) {
					  criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
					}
				  
				  if (dto.getDescription() != null && dto.getDescription().length() > 0) {
					  criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
					  }

			   if (dto.getOpenDate() != null && dto.getOpenDate().getDate() > 0) {
					criteria.add(Restrictions.eq("openDate", dto.getOpenDate()));
					System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
				}
			   
			   if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}
				
				
			}
			
			System.out.println("searchcalll");
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<IssueDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Order search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(IssueDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(IssueDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
