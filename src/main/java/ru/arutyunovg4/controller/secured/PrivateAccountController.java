package ru.arutyunovg4.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.arutyunovg4.entity.RecordStatus;
import ru.arutyunovg4.entity.dto.RecordsContainerDto;
import ru.arutyunovg4.service.RecordService;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {

    private final RecordService recordService;

    @Autowired
    public PrivateAccountController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping()
    public ModelAndView getMainPage(@RequestParam(name = "filter", required = false) String filterMode) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("private/account-page");

        RecordsContainerDto container = recordService.findAllRecords(filterMode);

        modelAndView.getModelMap()
                .addAttribute("records", container.getRecords());
        modelAndView.getModelMap()
                .addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        modelAndView.getModelMap()
                .addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());

        return modelAndView;
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
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode :"");
    }

    @PostMapping(value = "/delete-record")
    public String deleteRecord(@RequestParam int id,
                               @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode :"");
    }


}
