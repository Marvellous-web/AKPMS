package argus.repo.file;

import argus.domain.Files;
import argus.exception.ArgusException;

public interface FileDao {

	public void saveAttachement(Files file) throws ArgusException ;

	public void updateAttachement(Files file) throws ArgusException;

	public Files getAttachedFile(Long id) throws ArgusException;

	public int deleteAttachedFile(Long id) throws ArgusException;

}
