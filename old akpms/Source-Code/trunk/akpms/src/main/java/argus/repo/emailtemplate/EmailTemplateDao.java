package argus.repo.emailtemplate;

import java.util.List;
import java.util.Map;

import argus.domain.EmailTemplate;

public interface EmailTemplateDao
{
    /**
	 * 
	 * @param id
	 * @return
	 */
	EmailTemplate findById(Long id);

	/**
	 * 
	 * @param name
	 * @return
	 */
	EmailTemplate findByName(String name);

    /**
	 * 
	 * @param orderClauses
	 *            key=(orderBy,sortBy,offset,limit)
	 * @param whereClauses
	 * @return
	 */
	List<EmailTemplate> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses);

    /**
	 * 
	 * @param EmailTemplate
	 */
	void addEmailTemplate(EmailTemplate emailTemplate);

    /**
	 * 
	 * @param EmailTemplate
	 */
	void updateEmailTemplate(EmailTemplate emailTemplate);

    /**
	 * 
	 * @param id
	 * @param status
	 */
	int changeStatus(long id, boolean status) throws Exception;

    /**
	 * 
	 * @param conditionMap
	 * @return
	 */
	int totalRecord(Map<String, String> conditionMap);

    /**
     * this function will return only that email templates which have subscription_email =1
     * @return
     */
	List<EmailTemplate> findEmailTemplatesForSubscription();
}
