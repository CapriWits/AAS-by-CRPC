package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.entity.Semester;
import me.noctambulist.aasweb.repository.ISemester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/26 22:36
 */
@Service
public class SemesterService {

    private final ISemester iSemester;

    @Autowired
    public SemesterService(ISemester iSemester) {
        this.iSemester = iSemester;
    }

    @Transactional
    public Semester create(Semester semester) {
        return iSemester.saveAndFlush(semester);
    }

    public void deleteById(Integer id) {
        iSemester.deleteById(id);
    }

    public List<Semester> findAll() {
        return iSemester.findAll();
    }

}
