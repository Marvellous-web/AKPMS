package argus.repo.adminincome;

import java.util.List;

import argus.domain.AdminIncome;

public interface AdminIncomeDao {

	List<AdminIncome> findAll(boolean activeOnly);

}
