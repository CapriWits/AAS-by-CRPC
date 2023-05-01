package me.noctambulist.aasweb.controller;

import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.model.ClassSchedule;
import me.noctambulist.aasweb.service.ClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 23:14
 */

@RestController
@RequestMapping("/class_schedule")
@Slf4j
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;

    @Autowired
    public ClassScheduleController(ClassScheduleService classScheduleService) {
        this.classScheduleService = classScheduleService;
    }

    // =========================================================================================

    @PostMapping("/find_all")
    @ResponseBody
    public R findAll() {
        List<ClassSchedule> classSchedules = classScheduleService.findAll();
        return R.success(JsonUtils.newObjectNode().set("class_schedules", JsonUtils.objectToJsonNode(classSchedules)));
    }

}
