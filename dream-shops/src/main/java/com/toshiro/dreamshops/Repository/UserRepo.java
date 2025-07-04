package com.toshiro.dreamshops.Repository;


import com.toshiro.dreamshops.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

    boolean existsByEmail(String email);


    User findByEmail(String email);
}
