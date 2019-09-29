package org.cay.play.systemlog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.cay.play.util.JsonUtils;
import org.cay.play.util.NetWorkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Aspect
@Component
@SuppressWarnings("all")
public class SystemLogAspect {

    //注入Service用于把日志保存数据库，实际项目入库采用队列做异步
    @Autowired
    private SysLogService sysLogService;
    //本地异常日志记录对象

    //Service层切点
    @Pointcut("@annotation(org.cay.play.systemlog.SystemServiceLog)")
    public void serviceAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(org.cay.play.systemlog.SystemControllerLog)")
    public void controllerAspect() {
    }


    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = NetWorkUtils.getIpAddress(request);
        HttpSession session = request.getSession();
        //读取session中的用户
        // User user = (User) session.getAttribute("user");


        try {
            //获取用户请求方法的参数并序列化为JSON格式字符串
            Object[] args = joinPoint.getArgs();
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add(JsonUtils.getJsonStringFromObject(o));
            }

            //*========控制台输出=========*//
            log.debug("==============前置通知开始==============");
            log.debug("请求方法" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()));
            log.debug("方法描述：" + getControllerMethodDescription(joinPoint));
//            log.debug("请求人：" + user.getUsername());
            log.debug("请求ip：" + ip);
            log.debug("请求参数:" + list.toString());

            //*========数据库日志=========*//
            SysLogSRM sysLogBO = new SysLogSRM();
            sysLogBO.setIp(ip);
            sysLogBO.setClassName(joinPoint.getTarget().getClass().getName());
            sysLogBO.setMethodName(joinPoint.getSignature().getName());
            sysLogBO.setParams(list.toString());
            sysLogBO.setRemark(getControllerMethodDescription(joinPoint));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sysLogBO.setCreateDate(dateFormat.format(new Date()));
            //保存数据库
            sysLogService.save(sysLogBO);

        } catch (Exception e) {
            //记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息：{}", e.getMessage());
        }
    }

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
//        User user = (User) session.getAttribute("user");
        //获取请求ip
        String ip = NetWorkUtils.getIpAddress(request);

        try {
            //获取用户请求方法的参数并序列化为JSON格式字符串
            Object[] args = joinPoint.getArgs();
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add(JsonUtils.getJsonStringFromObject(o));
            }

            /*========控制台输出=========*/
            System.out.println("=====异常通知开始=====");
            System.out.println("异常代码:" + e.getClass().getName());
            System.out.println("异常信息:" + e.getMessage());
            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            System.out.println("方法描述:" + getServiceMethodDescription(joinPoint));
//            System.out.println("请求人:" + user.getUsername());
            System.out.println("请求IP:" + ip);
            System.out.println("请求参数:" + list.toString());

            //*========数据库日志=========*//
            SysLogSRM sysLogBO = new SysLogSRM();
            sysLogBO.setIp(ip);
            sysLogBO.setClassName(joinPoint.getTarget().getClass().getName());
            sysLogBO.setMethodName(joinPoint.getSignature().getName());
            sysLogBO.setParams(list.toString());
            sysLogBO.setRemark(getServiceMethodDescription(joinPoint));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sysLogBO.setCreateDate(dateFormat.format(new Date()));
            //保存数据库
            sysLogService.save(sysLogBO);
        } catch (Exception ex) {
            //记录本地异常日志
            log.error("==异常通知异常==");
            log.error("异常信息:{}", ex.getMessage());
        }
    }


    public String getServiceMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    public String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}