package com.example.demo.controller.admin;

import com.example.demo.model.Account;
import com.example.demo.service.servicesImp.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminAccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/admin/account")
    public String accountList(Model model){
        List<Account> accountList = accountService.getAllAccount();
        model.addAttribute("data", accountList);
        return "admin/accountList";
    }
}
