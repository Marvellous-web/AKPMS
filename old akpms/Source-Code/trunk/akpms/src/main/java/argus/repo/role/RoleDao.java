package argus.repo.role;

import java.util.List;

import argus.domain.Role;

public interface RoleDao {
	Role findById(Long id);

	Role findByName(String name);

	List<Role> findAll(String orderBy);

	void addRole(Role role);
}
