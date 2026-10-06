package ru.arutyunovg4.controller.secured;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.arutyunovg4.entity.RecordStatus;
import ru.arutyunovg4.entity.User;
import ru.arutyunovg4.entity.dto.RecordsContainerDto;
import ru.arutyunovg4.service.RecordService;
import ru.arutyunovg4.service.UserService;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {

    private final UserService userService;
    private final RecordService recordService;

    @Autowired
    public PrivateAccountController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
    }

    @GetMapping
    public String getMainPage(Model model, @RequestParam(name = "filter", required = false) String filterMode) {

        User user = userService.getCurrentUser();
        RecordsContainerDto container = recordService.findAllRecords(filterMode);
        model.addAttribute("userName", user.getName());
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        return "private/account-page";
    }


    @PostMapping(value = "/add-record")
    public String addRecord(@RequestParam String title) {
        recordService.saveRecord(title);
        return "redirect:/account";
    }

    @PostMapping(value = "/make-record-done")
    public String makeRecordDone(@RequestParam int id,
                                 @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }

    @PostMapping(value = "/delete-record")
    public String deleteRecord(@RequestParam int id,
                               @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }


}
