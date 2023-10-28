package com.example.demo.controller.admin;

import com.example.demo.model.Account;
import com.example.demo.service.servicesImp.AccountService;
import com.example.demo.service.servicesImp.RoleService;
import com.example.demo.utils.Validate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminUserController {
    @Autowired
    AccountService accountService;
    @Autowired
    private int adminRoleId;
    @Autowired
    RoleService roleService;

    @GetMapping("/admin/account")
    public String accountList(Model model){
        List<Account> accountList = accountService.getAllAccountByRole(2);
        model.addAttribute("data", accountList);
        return "admin/accountList";
    }

    @GetMapping("/admin/employee")
    public String employeeList(Model model){
        List<Account> employeeList = accountService.getAllAccountByRole(3);
        model.addAttribute("data", employeeList);
        return "/admin/employeeList";
    }

    @GetMapping("/admin/deleteAccount")
    public String deleteAccount(@RequestParam int id) {
        accountService.deleteUser(id);
        return "redirect:/admin/account";
    }

    @GetMapping("/admin/deleteEmployee")
    public String deleteEmployee(@RequestParam int id) {
        accountService.deleteUser(id);
        return "redirect:/admin/employee";
    }

    @GetMapping("/admin/addAccount")
    public String addAccount(Model model){
        model.addAttribute("roleList",roleService.getAllRole());
        return "admin/addAccount";
    }

    @PostMapping("/admin/addUser")
    public String addUser(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam int roleId,
            Model model) {

        model.addAttribute("fullName", fullName);
        model.addAttribute("phone", phone);
        model.addAttribute("password", password);
        model.addAttribute("email", email);
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleList",roleService.getAllRole());

        if(!Validate.validFullname(fullName)) {
            model.addAttribute("error", "Invalid Full Name!");
            return "admin/addAccount";
        }

        if(email.isEmpty() && phone.isEmpty()) {
            model.addAttribute("error", "Please input email or phone number!");
            return "admin/addAccount";
        }

        //check email
        if(!email.isEmpty() && !Validate.validEmail(email)) {
            model.addAttribute("error", "Email is invalid!");
            return "admin/addAccount";
        }

        if(!email.isEmpty() && accountService.checkExistMail(email)) {
            model.addAttribute("error", "Email already exist!");
            return "admin/addAccount";
        }

        //check phone
        if(!phone.isEmpty() && !Validate.validPhoneNumber(phone)) {
            model.addAttribute("error", "Phone is invalid!");
            return "admin/addAccount";
        }

        if(!phone.isEmpty() && accountService.checkExistPhoneNumber(phone)) {
            model.addAttribute("error", "Phone already exist!");
            return "admin/addAccount";
        }

        if(password.isEmpty()) {
            model.addAttribute("error", "Please input password!");
            return "admin/addAccount";
        }

        Account account = new Account();
        account.setName(fullName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setRole(roleId);
        account.setPassword(password);

        int newUserId =  accountService.saveAccount(account).getId();
        return "redirect:./updateAccount?id=" + newUserId;
    }

    @GetMapping("/admin/updateAccount")
    public String updateAccount(Model model, @RequestParam int id){
        Account account = accountService.getAccountById(id);
        model.addAttribute("user", account);
        model.addAttribute("roleList",roleService.getAllRole());
        return "admin/updateAccount";
    }

    @PostMapping("/admin/updateAccount")
    public String updateUser(
            HttpSession session,
            @RequestParam int id,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            Model model) {
        Account accountUpdate = accountService.getAccountById(id);
        model.addAttribute("user", accountUpdate);
        model.addAttribute("roleList",roleService.getAllRole());

        //check validate before update
        String msg = checkValidateUpdateUser(email, phone, accountUpdate);
        if (msg != null) {
            model.addAttribute("errorMsg", msg);
        } else {
            //update
            boolean ans = accountService.updateAccount(id, fullName, email, phone ,address);

            if (ans) model.addAttribute("msg", "Update success");
            else model.addAttribute("errorMsg", "Update failed");
        }

        return "/admin/updateAccount";
    }

    private String checkValidateUpdateUser(String email, String phone, Account user) {
        email = email.trim();
        phone = phone.trim();

        if (email.isEmpty() && phone.isEmpty()) return "Please input email and phone number";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.equals(user.getEmail()) && !email.isEmpty() && accountService.checkExistMail(email)) return "Email existed!";
        if (!phone.equals(user.getPhone()) && !phone.isEmpty() && accountService.checkExistPhoneNumber(phone)) return "Phone number existed!";

        return null;
    }
}
