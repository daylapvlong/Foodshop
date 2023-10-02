package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByEmailAndPassword(String username, String password);
    Account findAccountByPhoneAndPassword(String username, String password);
}
