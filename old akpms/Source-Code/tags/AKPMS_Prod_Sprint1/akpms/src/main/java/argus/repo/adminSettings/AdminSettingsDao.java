/**
 *
 */
package argus.repo.adminSettings;

import argus.domain.AdminSettings;
import argus.exception.ArgusException;

/**
 * @author sumit.v
 *
 */
public interface AdminSettingsDao {

	AdminSettings getAdminSettings() throws ArgusException;

	void updateAdminSettings(AdminSettings adminSetting)
			throws ArgusException;

}
