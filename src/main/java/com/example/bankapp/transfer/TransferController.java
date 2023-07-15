package com.example.bankapp.transfer;

import com.example.bankapp.user.User;
import com.example.bankapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final UserService userService;
    private final TransferService transferService;


    @GetMapping("/transfer")
    public String transfer(){
        return "transfer";
    }

    @PostMapping("/make-transaction")
    public String transfer(TransferData transferData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        transferData.setFromEmail(name);

        TransferRequest transferRequest = getTransferRequest(transferData);
        transferService.makeTransfer(transferRequest);

        return "redirect:/";
    }

    private TransferRequest getTransferRequest(TransferData transferData) {
        User fromAppUser = userService.findAppUserByEmail(transferData.getFromEmail());

        User toAppUser = userService.findAppUserByEmail(transferData.getToEmail());

        return new TransferRequest(
                transferData.getAmount(),
                fromAppUser.getAccount(),
                toAppUser.getAccount(),
                transferData.getDescription()
        );
    }
}
