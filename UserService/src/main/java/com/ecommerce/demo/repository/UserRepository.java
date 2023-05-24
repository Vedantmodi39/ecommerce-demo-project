package com.ecommerce.demo.repository;

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

    @Query("select u from Users u full join u.userPayments userPayments full join u.userAddresses ua where userPayments.id = ?1 and u.id = ?2 and ua.id =?3")
    Users findByUserPaymentsIdAndId(int id, int id1 , int id2);

//    @Query("select u from Users u inner join u.userAddress userAddress where userAddress.id = ?1 and u.id = ?2")
//    Users findByUserAddressAndId(int id,int id1);
//
//    @Query("select u from Users u where userAddress.id = ?1 and userPayment.id = ?2 and u.id= ?3")
}
