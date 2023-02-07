package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findById(int id);

    Optional<Users> findByEmail(String email);

    @Override
    void deleteById(Integer id);
}
