package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AtmDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class AtmModelHibImp implements AtmModelInt {

	@Override
	public long add(AtmDTO dto) throws ApplicationException, DuplicateRecordException {

		AtmDTO existDto = null;

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
	public void delete(AtmDTO dto) throws ApplicationException {

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
	public void update(AtmDTO dto) throws ApplicationException, DuplicateRecordException {

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
	public AtmDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		AtmDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (AtmDTO) session.get(AtmDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public AtmDTO findByLogin(String login) throws ApplicationException {

		Session session = null;
		AtmDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AtmDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (AtmDTO) list.get(0);
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
			Criteria criteria = session.createCriteria(AtmDTO.class);
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
	public List search(AtmDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<AtmDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AtmDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getLocation() != null && dto.getLocation().length() > 0) {
					criteria.add(Restrictions.like("location", dto.getLocation() + "%"));
				}
				if (dto.getCash_available() > 0) {
					criteria.add(Restrictions.eq("cash_available", dto.getCash_available()));
				}

				if (dto.getLast_restocked_date() != null && dto.getLast_restocked_date().getDate() > 0) {
					criteria.add(Restrictions.eq("last_restocked_date", dto.getLast_restocked_date()));
				}
				if (dto.getRemark() != null && dto.getRemark().length() > 0) {
					criteria.add(Restrictions.like("remark", dto.getRemark() + "%"));
				}

			}

			System.out.println("searchcalll");
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<AtmDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Order search");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(AtmDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(AtmDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
