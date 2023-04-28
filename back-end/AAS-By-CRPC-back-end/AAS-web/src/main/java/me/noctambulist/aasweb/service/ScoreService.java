package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.entity.Score;
import me.noctambulist.aasweb.repository.IScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/28 11:03
 */
@Service
public class ScoreService {

    private final IScore iScore;

    @Autowired
    public ScoreService(IScore iScore) {
        this.iScore = iScore;
    }

    @Transactional
    public Score create(Score score) {
        return iScore.saveAndFlush(score);
    }

    @Transactional
    public Score update(Integer id, Score score) {
        Optional<Score> optionalScore = iScore.findById(id);
        optionalScore.orElseThrow(() -> new CustomException(ResultEnum.NOT_FOUND));
        score.setId(optionalScore.get().getId());
        return iScore.saveAndFlush(score);
    }

    public Score findById(Integer id) {
        return iScore.findById(id).orElse(null);
    }

}
