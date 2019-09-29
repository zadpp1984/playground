package org.cay.play.systemlog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 为日志系统准备
 */
@Slf4j
@Service
public class SysLogService {
    public boolean save(SysLogSRM sysLogBO) {
//        try {
//            log.info(JsonUtils.getJsonStringFromObject(sysLogBO));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return true;
    }
}
