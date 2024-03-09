package argus.repo.file;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Files;
import argus.exception.ArgusException;

@Repository
@Transactional
public class FileDaoImpl implements FileDao {

	@Autowired
	private EntityManager em;
	@Override
	public void saveAttachement(Files file) throws ArgusException {
		em.persist(file);

	}

	@Override
	public void updateAttachement(Files file) throws ArgusException {
		em.merge(file);

	}

	@Override
	public Files getAttachedFile(Long id) throws ArgusException {

		return em.find(Files.class, id);
	}

	@Override
	public int deleteAttachedFile(Long id) throws ArgusException
	{
		Query query = em.createQuery("UPDATE  Files  SET deleted = true WHERE id = "+id);
		return query.executeUpdate();
	}
}
