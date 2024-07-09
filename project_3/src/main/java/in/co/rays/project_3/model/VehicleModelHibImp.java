package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.VehicleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class VehicleModelHibImp implements VehicleModelInt{
	
	
	@Override
	public long add(VehicleDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 VehicleDTO existDto = null;
			
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
	public void delete(VehicleDTO dto) throws ApplicationException {
		
		
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
	public void update(VehicleDTO dto) throws ApplicationException, DuplicateRecordException {

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
	public VehicleDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		VehicleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (VehicleDTO) session.get(VehicleDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public VehicleDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		VehicleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(VehicleDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (VehicleDTO) list.get(0);
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
			Criteria criteria = session.createCriteria(VehicleDTO.class);
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
	public List search(VehicleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<VehicleDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(VehicleDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getColour() != null && dto.getColour().length() > 0) {
					criteria.add(Restrictions.like("colour", dto.getColour() + "%"));
				}
				if ( dto.getInsuranceAmount() > 0) {
					criteria.add(Restrictions.eq("insuranceAmount", dto.getInsuranceAmount()));
				}
				if (dto.getNumber() != null && dto.getNumber().length() > 0) {
					criteria.add(Restrictions.like("number", dto.getNumber() + "%"));
				}


				
				 
				 if (dto.getPurchaseDate() != null && dto.getPurchaseDate().getTime() > 0) {
					criteria.add(Restrictions.eq("purchaseDate", dto.getPurchaseDate()));
				}
			   

			   
			  
			}
			
			System.out.println("searchcalll");
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<VehicleDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Order search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(VehicleDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(VehicleDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
