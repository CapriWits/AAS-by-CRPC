package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.model.mongo.Schedule;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/29 23:03
 */
@Service
public class MongoScheduleService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoScheduleService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    public Schedule create(Schedule schedule) {
        return mongoTemplate.insert(schedule);
    }

    @Transactional
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Schedule.class);
    }

    public List<Schedule> findAll() {
        return mongoTemplate.findAll(Schedule.class);
    }

}
