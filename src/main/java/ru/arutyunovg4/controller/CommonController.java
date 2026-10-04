package ru.arutyunovg4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.arutyunovg4.entity.RecordStatus;
import ru.arutyunovg4.entity.dto.RecordsContainerDto;
import ru.arutyunovg4.service.RecordService;

@Controller
public class CommonController {

    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }



    @RequestMapping("/")
    public String redirectToMainPage() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public ModelAndView getMainPage(@RequestParam(name = "filter", required = false) String filterMode) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main-page");

        RecordsContainerDto container = recordService.findAllRecords(filterMode);

        modelAndView.getModelMap()
                .addAttribute("records", container.getRecords());
        modelAndView.getModelMap()
                .addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        modelAndView.getModelMap()
                .addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());

        return modelAndView;
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam String title) {
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam int id,
                                 @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode :"");
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(@RequestParam int id,
                               @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode :"");
    }


}
