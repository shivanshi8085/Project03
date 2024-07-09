package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DoctorDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DoctorModelHibImp implements DoctorModelInt{
	
	@Override
	public long add(DoctorDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 DoctorDTO existDto = null;
			
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
	public void delete(DoctorDTO dto) throws ApplicationException {
		
		
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
	public void update(DoctorDTO dto) throws ApplicationException, DuplicateRecordException {

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
	public DoctorDTO findByPK(long pk) throws ApplicationException {
		
		Session session = null;
		DoctorDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (DoctorDTO) session.get(DoctorDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}


		return dto;
	}

	@Override
	public DoctorDTO findByLogin(String login) throws ApplicationException {
		
		
		Session session = null;
		DoctorDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (DoctorDTO) list.get(0);
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
			Criteria criteria = session.createCriteria(DoctorDTO.class);
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
	public List search(DoctorDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		ArrayList<DoctorDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
								
				 if (dto.getDob() != null && dto.getDob().getTime() > 0) {
						criteria.add(Restrictions.eq("dob", dto.getDob()));
					}
					 if (dto.getMobile() != null && dto.getMobile().length() > 0) {
							criteria.add(Restrictions.like("mobile", dto.getMobile() + "%"));
						}
					 
					 if (dto.getExperties() != null && dto.getExperties().length() > 0) {
							criteria.add(Restrictions.like("experties", dto.getExperties() + "%"));
						}
			   
			  
			}
			
			System.out.println("searchcalll");
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<DoctorDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Order search");
		} finally {
			session.close();
		}


		
		return list;
	}

	@Override
	public List search(DoctorDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRoles(DoctorDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	








}
