package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.model.ClassSchedule;
import me.noctambulist.aasweb.repository.IClassSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 23:08
 */
@Service
public class ClassScheduleService {

    private final IClassSchedule iClassSchedule;

    @Autowired
    public ClassScheduleService(IClassSchedule iClassSchedule) {
        this.iClassSchedule = iClassSchedule;
    }

    @Transactional
    public ClassSchedule create(ClassSchedule classSchedule) {
        return iClassSchedule.saveAndFlush(classSchedule);
    }

    public ClassSchedule findById(Integer id) {
        return iClassSchedule.findById(id).orElse(null);
    }

    public List<ClassSchedule> findAll() {
        return iClassSchedule.findAll();
    }

}
