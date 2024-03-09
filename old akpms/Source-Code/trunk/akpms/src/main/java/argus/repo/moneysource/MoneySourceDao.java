package argus.repo.moneysource;

import java.util.List;

import argus.domain.MoneySource;

public interface MoneySourceDao {

	List<MoneySource> findAll(boolean activeOnly);

}
