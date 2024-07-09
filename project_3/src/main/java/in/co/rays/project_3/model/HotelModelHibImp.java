package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.HotelDTO;
import in.co.rays.project_3.dto.HotelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class HotelModelHibImp implements HotelModelInt{

	@Override
	public long add(HotelDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 HotelDTO existDto = null;
			
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
				throw new ApplicationException("Exception in Hotel Add " + e.getMessage());
			} finally {
				session.close();
			}

		
		return dto.getId();
	}

	@Override
	public void delete(HotelDTO dto) throws ApplicationException {
		
		
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
			throw new ApplicationException("Exception in Hotel Delete" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update(HotelDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		/*
		 * Transaction tx = null; HotelDTO exesistDto = findByLogin(dto.getLogin());
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
			throw new ApplicationException("Exception in Hotel update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public HotelDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		HotelDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (HotelDTO) session.get(HotelDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Hotel by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public HotelDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		HotelDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HotelDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (HotelDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Hotel by Login " + e.getMessage());

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
			Criteria criteria = session.createCriteria(HotelDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Hotel list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(HotelDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<HotelDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HotelDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				
				  if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				  criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				  }
				  if (dto.getAddress() != null && dto.getAddress().length() > 0) {
					  criteria.add(Restrictions.like("address", dto.getAddress() + "%"));
					}
			   if (dto.getDeliveryDate() != null && dto.getDeliveryDate().getDate() > 0) {
					criteria.add(Restrictions.eq("deliveryDate", dto.getDeliveryDate()));
				}
				
				
			}
			
			System.out.println("searchcalll");
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<HotelDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Hotel search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(HotelDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(HotelDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}
