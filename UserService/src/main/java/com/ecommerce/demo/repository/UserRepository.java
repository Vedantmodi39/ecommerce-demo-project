package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.UserPayment;
import com.ecommerce.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findById(int id);

    Optional<Users> findByEmail(String email);

    @Override
    void deleteById(Integer id);

    @Query("select u from Users u inner join u.userPayments userPayments where userPayments.id = ?1 and u.id = ?2")
    Users findByUserPaymentsIdAndId(int id, int id1);

}
