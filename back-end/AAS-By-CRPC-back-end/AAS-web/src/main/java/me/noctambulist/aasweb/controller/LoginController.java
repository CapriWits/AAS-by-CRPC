package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.JsonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/18 16:55
 */
@RestController
@Slf4j
public class LoginController {

    @PostMapping("/login")
    public R login(@RequestBody LoginParam param) {
        ObjectNode node = JsonUtils.newObjectNode();
        node.put("role", 1);
        return R.success(ResultEnum.SUCCESS, node);
    }

    @Data
    public static class LoginParam {
        Long id;
        String password;
    }

}
