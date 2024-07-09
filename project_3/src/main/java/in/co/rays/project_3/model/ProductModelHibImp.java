package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ProductModelHibImp implements ProductModelInt{

	@Override
	public long add(ProductDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 ProductDTO existDto = null;
			
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
	public void delete(ProductDTO dto) throws ApplicationException {
		
		
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
	public void update(ProductDTO dto) throws ApplicationException, DuplicateRecordException {

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
	public ProductDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		ProductDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (ProductDTO) session.get(ProductDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public ProductDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		ProductDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ProductDTO) list.get(0);
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
		return list(0,0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductDTO.class);
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
	public List search(ProductDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<ProductDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				
				  if (dto.getQuality() != null && dto.getQuality().length() > 0) {
					  criteria.add(Restrictions.like("quality", dto.getQuality() + "%"));
					}
				  
				  if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					  criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
					  }

			   if (dto.getPurchesDate() != null && dto.getPurchesDate().getDate() > 0) {
					criteria.add(Restrictions.eq("purchesDate", dto.getPurchesDate()));
				}
				
				
			}
			
			System.out.println("searchcalll");
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<ProductDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Order search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(ProductDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(ProductDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
