package argus.repo.permission;

import java.util.List;

import argus.domain.Permission;

public interface PermissionDao
{
	Permission findById(String id);

	Permission findByName(String name);

	List<Permission> findAll(String orderBy);

	void addPermission(Permission insurance);

	List<Permission> getPermissionByIds(List<String> ids)
			throws Exception;
}
