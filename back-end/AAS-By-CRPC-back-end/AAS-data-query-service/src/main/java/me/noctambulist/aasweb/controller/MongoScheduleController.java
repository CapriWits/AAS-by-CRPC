package me.noctambulist.aasweb.controller;

import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.model.mongo.Schedule;
import me.noctambulist.aasweb.service.MongoScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/29 23:01
 */
@RestController
@RequestMapping("/mongo/schedule")
@Slf4j
public class MongoScheduleController {

    private final MongoScheduleService mongoScheduleService;

    @Autowired
    public MongoScheduleController(MongoScheduleService mongoScheduleService) {
        this.mongoScheduleService = mongoScheduleService;
    }

    // =========================================================================================

    @PostMapping("/find_all")
    @ResponseBody
    public R findAll() {
        List<Schedule> schedules = mongoScheduleService.findAll();
        return R.success(JsonUtils.newObjectNode().set("schedule_info", JsonUtils.objectToJsonNode(schedules)));
    }

}
