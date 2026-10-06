package ru.arutyunovg4.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.arutyunovg4.entity.User;
import ru.arutyunovg4.service.UserService;

@Controller
public class PrivateAdminController {
    private final UserService userService;

    @Autowired
    public PrivateAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getManagementPage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("userName", user.getName());
        return "private/admin/managment-page";
    }
}
