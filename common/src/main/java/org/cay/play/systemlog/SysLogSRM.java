package org.cay.play.systemlog;

import lombok.Data;

@Data
public class SysLogSRM {

    private String ip;

    private String className;

    private String methodName;

    private String params;

    private Long executeTime;

    private String remark;

    private String createDate;

}
