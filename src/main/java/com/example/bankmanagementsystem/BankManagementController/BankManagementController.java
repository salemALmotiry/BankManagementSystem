package com.example.bankmanagementsystem.BankManagementController;

import com.example.bankmanagementsystem.ApiResponse.ApiResponse;
import com.example.bankmanagementsystem.Model.BankManagement;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/BankManagementSystem")
public class BankManagementController {

    ArrayList<BankManagement> accounts = new ArrayList<BankManagement>();

    @GetMapping("/get")
    public ArrayList<BankManagement> getAccounts() {
        return accounts;
    }

    @PostMapping("/add")
    public ApiResponse addAccount(@RequestBody BankManagement account) {

        for (BankManagement ac : accounts) {
            if (ac.getID() == account.getID()) {
                return new ApiResponse("account already exists");
            }
        }
        accounts.add(account);

        return new ApiResponse("account added successfully");
    }

    @PutMapping("/update/{index}")
    public ApiResponse updateAccount(@PathVariable int index ,@RequestBody BankManagement account) {
       if (index > accounts.size() || index < 0)  {
           return new ApiResponse("account not found");
       }
        accounts.set(index, account);
        return new ApiResponse("account updated successfully");
    }

    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteAccount(@PathVariable int index) {
        if (index > accounts.size() || index < 0)  {
            return new ApiResponse("account not found");
        }
        accounts.remove(index);
        return new ApiResponse("account deleted successfully");
    }

    @PutMapping("/deposit/{index}/{amount}")
    public ApiResponse deposit(@PathVariable int index,@PathVariable double amount) {

        if (index > accounts.size() || index < 0)  {
            return new ApiResponse("account not found");
        }
        if (amount <= 0)  {
            return new ApiResponse("amount not valid");
        }

        BankManagement ac = accounts.get(index);
        ac.setBalance(ac.getBalance() + amount);
        return new ApiResponse("account updated successfully");
    }



    @PutMapping("/withdraw/{index}/{amount}")
    public ApiResponse withdraw(@PathVariable int index,@PathVariable double amount) {
        if (index > accounts.size() || index < 0)  {
            return new ApiResponse("account not found");
        }
        if (amount <= 0)  {
            return new ApiResponse("amount not valid");
        }
        if (amount > accounts.get(index).getBalance())  {
            return new ApiResponse("account not enough");
        }
        BankManagement ac = accounts.get(index);
        ac.setBalance(ac.getBalance() - amount);
        return new ApiResponse("account updated successfully");

    }




}
