package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.WishDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class WishModelHibImp implements WishModelInt{
	
	@Override
	public long add(WishDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 WishDTO existDto = null;
			
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
	public void delete(WishDTO dto) throws ApplicationException {
		
		
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
	public void update(WishDTO dto) throws ApplicationException, DuplicateRecordException {

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
	public WishDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		WishDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (WishDTO) session.get(WishDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public WishDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		WishDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(WishDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (WishDTO) list.get(0);
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
			Criteria criteria = session.createCriteria(WishDTO.class);
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
	public List search(WishDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<WishDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(WishDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getProduct() != null && dto.getProduct().length() > 0) {
					criteria.add(Restrictions.like("product", dto.getProduct() + "%"));
				}
				 if (dto.getRemark() != null && dto.getRemark().length() > 0) {
						criteria.add(Restrictions.like("remark", dto.getRemark() + "%"));
					}
				
				 if (dto.getDate() != null && dto.getDate().getTime() > 0) {
						criteria.add(Restrictions.eq("date", dto.getDate()));
					}
					 if (dto.getUserName() != null && dto.getUserName().length() > 0) {
							criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
						}
			   
			  
			}
			
			System.out.println("searchcalll");
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<WishDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Order search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(WishDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(WishDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	






}
