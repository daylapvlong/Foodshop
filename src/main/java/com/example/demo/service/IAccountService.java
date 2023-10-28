package com.example.demo.service;

import com.example.demo.model.Account;

import java.util.List;

public interface IAccountService {

    List<Account> getAllAccountByRole(Integer roleId);

    Account getAccountById(Integer id);

    Account saveAccount(Account account);

    void deleteUser(int id);

    Account findAccountByEmailAndPassword(String username, String password);

    boolean updateAccount(int id, String fullName, String email, String phone, String address);

    Account findAccountByPhoneAndPassword(String username, String password);

    Account findAccountByUsernameAndPassword(String username, String password);

    boolean checkExistMail(String email);

    boolean checkExistPhoneNumber(String phoneNumber);
}
