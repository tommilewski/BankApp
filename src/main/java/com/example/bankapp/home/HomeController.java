package com.example.bankapp.home;

import com.example.bankapp.transfer.Transfer;
import com.example.bankapp.transfer.TransferService;
import com.example.bankapp.user.User;
import com.example.bankapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final TransferService transferService;

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        if (!name.equals("anonymousUser")){
            UserDetails userDetails = userService.loadUserByUsername(name);
            User user = (User) userDetails;

            List<Transfer> allByFromAccount = transferService.findAllByFromAccount(user.getAccount());
            List<Transfer> allByToAccount = transferService.findAllByToAccount(user.getAccount());

            List<Transfer> allTransfers = new ArrayList<>();
            allTransfers.addAll(allByFromAccount);
            allTransfers.addAll(allByToAccount);
            allTransfers.sort(Comparator.comparing(Transfer::getTime).reversed());

            model.addAttribute("user", user);
            model.addAttribute("transfers", allTransfers);
        }

        model.addAttribute("isAuthenticated", name);
        return "index";
    }
}
