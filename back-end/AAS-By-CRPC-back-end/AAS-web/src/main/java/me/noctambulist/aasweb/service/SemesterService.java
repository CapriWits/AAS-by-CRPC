package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.common.constant.RedisConstants;
import me.noctambulist.aasweb.repository.ISemester;
import me.noctambulist.aasweb.model.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public SemesterService(ISemester iSemester, StringRedisTemplate redisTemplate) {
        this.iSemester = iSemester;
        this.redisTemplate = redisTemplate;
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

    public void setCurrentSemester(String json) {
        redisTemplate.opsForValue().set(RedisConstants.CURRENT_SEMESTER, json);
    }

    public String getCurrentSemester() {
        return redisTemplate.opsForValue().get(RedisConstants.CURRENT_SEMESTER);
    }

}
