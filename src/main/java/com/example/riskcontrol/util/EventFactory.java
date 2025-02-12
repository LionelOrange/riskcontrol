package com.example.riskcontrol.util;

import com.alibaba.fastjson.JSON;
import com.example.riskcontrol.model.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Component
public class EventFactory {

    private static Logger logger = LoggerFactory.getLogger(EventFactory.class);


    public static Event build(String json) {
        String scene = JSON.parseObject(json).getString("scene");
        if (StringUtils.isEmpty(scene)) {
            logger.error("scene参数错误，" + json);
            throw new RCRuntimeException("scene参数确实，" + json);
        }
        EnumScene enumScene = EnumScene.valueOf(scene.toUpperCase());
        if (enumScene == null) {
            logger.error("json格式错误，" + json);
            throw new RCRuntimeException("json格式错误，" + json);
        }

        Event event = null;
        if (EnumScene.LOGIN.equals(enumScene)) {
            event = JSON.parseObject(json, LoginEvent.class);
        } else if (EnumScene.ORDER.equals(enumScene)) {
            event = JSON.parseObject(json, OrderEvent.class);
        }

        check(event);

        return event;
    }


    private static void check(Event event) {
        if (event == null) {
            throw new RCRuntimeException("事件错误");
        }
        if (event.getOperateTime() == null) {
            event.setOperateTime(new Date());
        }
        if (StringUtils.isEmpty(event.getId())) {
            event.setId(UUID.randomUUID().toString());
        }
        //设置手机号段，截取手机号前七位
        if (StringUtils.isBlank(event.getMobileSeg()) && StringUtils.isNotBlank(event.getMobile())){
            event.setMobileSeg(event.getMobile().substring(0,7));
        }
        // TODO  扩展维度信息
    }

}
