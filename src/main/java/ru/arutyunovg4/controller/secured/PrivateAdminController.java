package ru.arutyunovg4.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.arutyunovg4.entity.User;
import ru.arutyunovg4.entity.UserRole;
import ru.arutyunovg4.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PrivateAdminController {
    private final UserService userService;

    @Autowired
    public PrivateAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getManagementPage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("userName", user.getName());


        if (user.isSuperAdmin()) {
            List<User> candidatesToDelete = userService.findAllByRole(List.of(UserRole.ADMIN, UserRole.USER));
            List<User> candidatesToUpdate = candidatesToDelete.stream()
                    .filter(User::isSimpleUser)
                    .toList();
            model.addAttribute("candidatesToDelete", candidatesToDelete);
            model.addAttribute("candidatesToUpdate", candidatesToUpdate);
        } else {
            List<User> candidatesToDelete = userService.findAllByRole(List.of(UserRole.USER));
            model.addAttribute("candidatesToDelete", candidatesToDelete);
        }


        return "private/admin/management-page";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam int id) {
        Optional<User> userToBeDeletedOptional = userService.findById(id);
        if (userToBeDeletedOptional.isEmpty()) {
            return "redirect:/admin";
        }

        User userToBeDeleted = userToBeDeletedOptional.get();
        User currentUser = userService.getCurrentUser();

        if (userToBeDeleted.isSuperAdmin()) return "redirect:/admin";
        if (userToBeDeleted.isAdmin() && !currentUser.isSuperAdmin()) return "redirect:/admin";


        userService.deleteById(id);
        return "redirect:/admin";
    }

}
