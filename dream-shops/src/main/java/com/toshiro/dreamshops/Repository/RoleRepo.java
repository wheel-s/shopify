package com.toshiro.dreamshops.Repository;

import com.toshiro.dreamshops.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

  Role findByName(String name);
}
