package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.service.ScoreService;
import me.noctambulist.aasweb.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/28 16:00
 */
@RestController
@RequestMapping("/score")
@Slf4j
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    // =========================================================================================

    @PostMapping("/find_score_by_id")
    @ResponseBody
    public R findScoreById(@RequestBody @Validated final FindScoreByIdParam param) {
        Score score = scoreService.findById(param.id);
        if (score == null) {
            throw new CustomException(ResultEnum.NOT_FOUND);
        }
        return R.success(JsonUtils.newObjectNode().set("score", JsonUtils.objectToJsonNode(score)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class FindScoreByIdParam {
        @NotNull(message = "成绩 id 不能为空")
        Integer id;
    }

    // =========================================================================================

    @PostMapping("/insert_score")
    @ResponseBody
    public R insertScore(@RequestBody @Validated final InsertScoreParam param) {
        Score score = Score.builder().score(param.score).build();
        Score res = scoreService.create(score);
        return R.success(JsonUtils.newObjectNode().set("score", JsonUtils.objectToJsonNode(res)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertScoreParam {
        Double score = 0d;
    }

    // =========================================================================================

    @PostMapping("/update_score")
    @ResponseBody
    public R updateScore(@RequestBody @Validated final UpdateScoreParam param) {
        Score score = Score.builder().id(param.id).score(param.score).build();
        Score updatedScore = scoreService.update(param.id, score);
        return R.success(JsonUtils.newObjectNode().set("score", JsonUtils.objectToJsonNode(updatedScore)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class UpdateScoreParam {
        @NotNull(message = "成绩表 id 不能为空")
        Integer id;
        @NotNull(message = "成绩不能为空")
        Double score = 0d;
    }

}
