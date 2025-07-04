package com.toshiro.dreamshops.Service.Role;

import com.toshiro.dreamshops.Model.Role;

public interface iRoleService {

    Role getRoleById(Long id);
    Role addRole(Role role);
    Role getRoleByName(String name);
    Role updateRole(Role role, Long id);
    void deleteRoleById(Long id);
}
