package ru.arutyunovg4.controller.secured;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivateAdminController {

    @GetMapping("/admin")
    public String getManagementPage(){
        return  "private/admin/managment-page";
    }
}
