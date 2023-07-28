package com.v2java.dispatcher.task;

import com.v2java.dispatcher.dto.RpaCallbackDTO;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 滑动窗口计数统计任务失败率
 *
 * @author liaowenxing 2023/7/27
 **/
@Component
public class SlidingWindowCounter {

    @Autowired
    RedisTemplate redisTemplate;

    public final static String KEY_FAIL_RATIO_PREFIX = "fail:ratio:";

    public final static List<String> FAIL_CODES = Arrays.asList("1");

    public final static String FAIL_PREFIX = "fail";

    public final static String NORMAL_PREFIX = "normal";

    public final static double CLOSE_RATIO = 0.3;

    @Async
    public void update(RpaCallbackDTO callbackDTO) {
        String areaKey = KEY_FAIL_RATIO_PREFIX + callbackDTO.getArea();
        String prefix = FAIL_CODES.contains(callbackDTO.getResult())
                ? FAIL_PREFIX : NORMAL_PREFIX;
        String member = prefix + ":" + callbackDTO.getFlowNum();
        double score = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        redisTemplate.opsForZSet().add(areaKey, member, score);
    }

    @Scheduled(fixedRate = 5000)
    public void refresh() {
        //省略其他地区，仅使用一个举例
        List<String> areaList = Arrays.asList("110000");
        areaList.forEach(area -> refreshArea(area));
    }

    public void refreshArea(String area) {
        double beforeTimestamp = LocalDateTime.now().minusSeconds(30).toEpochSecond(ZoneOffset.UTC);
        String areaKey = KEY_FAIL_RATIO_PREFIX + area;
        redisTemplate.opsForZSet().removeRangeByScore(areaKey, 0, beforeTimestamp);
        Set<String> set = redisTemplate.opsForZSet()
                .rangeByScore(areaKey, beforeTimestamp, -1);
        long failCount = set.stream().filter(i -> i.startsWith(FAIL_PREFIX)).count();
        double failRatio = (double) failCount / set.size();
        String areaSwitch = "task:pause:" + area;
        if (failRatio > CLOSE_RATIO) {
            redisTemplate.opsForValue().set(areaSwitch, "on", 10, TimeUnit.SECONDS);
        } else {
            redisTemplate.delete(areaSwitch);
        }
    }
}
