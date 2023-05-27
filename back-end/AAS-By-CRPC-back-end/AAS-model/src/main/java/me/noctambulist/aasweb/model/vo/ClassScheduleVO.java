package me.noctambulist.aasweb.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/16 15:45
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@Slf4j
public class ClassScheduleVO {
    @JsonProperty("key")
    public String key;
    @JsonProperty("time")
    public String time;
    @JsonProperty("Monday")
    public String Monday;
    @JsonProperty("Tuesday")
    public String Tuesday;
    @JsonProperty("Wednesday")
    public String Wednesday;
    @JsonProperty("Thursday")
    public String Thursday;
    @JsonProperty("Friday")
    public String Friday;
    @JsonProperty("Saturday")
    public String Saturday;
    @JsonProperty("Sunday")
    public String Sunday;

    public static final Map<String, String> weeksMap = new HashMap<String, String>() {{
        put("1", "Monday");
        put("2", "Tuesday");
        put("3", "Wednesday");
        put("4", "Thursday");
        put("5", "Friday");
        put("6", "Saturday");
        put("7", "Sunday");
    }};

    public static final Map<String, String> timesMap = new HashMap<String, String>() {{
        put("1", "第一节(08:00-08:45)");
        put("2", "第二节(08:50-09:35)");
        put("3", "第三节(09:50-10:35)");
        put("4", "第四节(10:40-11:25)");
        put("5", "第五节(11:30-12:15)");
        put("6", "第六节(14:30-15:15)");
        put("7", "第七节(15:20-16:05)");
        put("8", "第八节(16:20-17:05)");
        put("9", "第九节(17:10-17:55)");
        put("10", "第十节(19:00-19:45)");
        put("11", "第十一节(19:50-20:35)");
        put("12", "第十二节(20:40-21:25)");
    }};

    /**
     * To generate class schedule for student
     *
     * @param sectionIdList sectionId list
     * @param sectionNums   sectionNum list
     * @param weeks         week list
     * @param nameQueue     course name list
     * @return ClassScheduleVO array, which size equals to 12
     */
    public static ClassScheduleVO[] getInstance(Queue<String[]> sectionIdQueue, Queue<String[]> sectionNumQueue,
                                                Queue<String[]> weekQueue, Queue<String> nameQueue) {
        int x = sectionIdQueue.size(), y = sectionNumQueue.size(), z = weekQueue.size();
        if (x != y || y != z) {
            log.error("sectionIdQueue:[{}] or sectionNumQueue:[{}] or weekQueue:[{}] size is not equals", x, y, z);
            throw new RuntimeException("sectionIdQueue, sectionNumQueue, weekQueue size is not equals");
        }
        List<ClassScheduleVO> res = new ArrayList<>(12);
        for (int i = 1; i <= 12; i++) {
            res.add(ClassScheduleVO.builder().key(String.valueOf(i)).time(timesMap.get(String.valueOf(i))).build());
        }
        while (!nameQueue.isEmpty()) {
            String courseName = nameQueue.poll();
            String[] sectionIds = sectionIdQueue.poll(), sectionNums = sectionNumQueue.poll(), weeks = weekQueue.poll();
            if (ObjectUtils.isEmpty(sectionIds) || ObjectUtils.isEmpty(sectionNums) || ObjectUtils.isEmpty(weeks)) {
                throw new RuntimeException("sectionId or sectionNum or weeks is null");
            }
            x = sectionIds.length;
            y = sectionNums.length;
            z = weeks.length;
            if (x != y || y != z) {
                log.error("sectionIds:[{}] or sectionNums:[{}] or weeks:[{}] length is not equals", x, y, z);
                throw new RuntimeException("sectionIds, sectionNums, weeks length is not equals");
            }
            for (int i = 0; i < x; i++) {
                String sectionNum = sectionNums[i], week = weeks[i], methodName = "set" + weeksMap.get(week);
                int sectionId = Integer.parseInt(sectionIds[i]), n = Integer.parseInt(sectionNum);
                for (int t = 0; t < n; t++) {
                    final int sectionKey = sectionId + t;
                    if (sectionKey < 0 || sectionKey > 12) {
                        break;
                    }
                    res.stream().filter(v -> v.getKey().equals(String.valueOf(sectionKey))).forEach(v -> {
                        try {
                            Method method = v.getClass().getDeclaredMethod(methodName, String.class);
                            method.invoke(v, courseName);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }
        return res.toArray(new ClassScheduleVO[0]);
    }

}