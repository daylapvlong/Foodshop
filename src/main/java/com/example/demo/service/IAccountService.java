package com.example.demo.service;

import com.example.demo.model.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAllAccount();

    Account getAccountById(Integer id);

    Account saveAccount(Account account);

    Account findAccountByEmailAndPassword(String username, String password);

    Account findAccountByPhoneAndPassword(String username, String password);

    Account findAccountByUsernameAndPassword(String username, String password);

    boolean checkExistMail(String email);

    boolean checkExistPhoneNumber(String phoneNumber);
}
