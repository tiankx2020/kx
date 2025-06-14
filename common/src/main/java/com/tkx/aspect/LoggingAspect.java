package com.tkx.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author tkx
 * @Date 2024 12 28 09 27
 **/
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.tkx.controller.HealthCheck*.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethodFromJoinPoint(joinPoint);
        if(method!=null){
            GetMapping requestMapping = method.getAnnotation(GetMapping.class);
            if (requestMapping != null) {
                log.info("Request path: {}", String.join(", ", requestMapping.value()));
            }
        }
        return joinPoint.proceed();
    }

    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("After executing: " + joinPoint.getSignature());
    }

    private Method getMethodFromJoinPoint(ProceedingJoinPoint joinPoint) {
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();

            Class<?> targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == arguments.length) {
                        return method;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("Failed to find method from JoinPoint", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String s = convertDateToBinary("2080-02-29");
        System.out.println(s);
    }
    public static String convertDateToBinary(String date) {
        StringBuilder sb = new StringBuilder();
        String[] arr = date.split("-");
        for (String s : arr) {
            sb.append(tenToTwo(Integer.valueOf(s)));
            sb.append("-");
        }
        return sb.toString().substring(0,sb.length()-1);
    }

    public static String tenToTwo(int x){
        StringBuilder sb = new StringBuilder();
        if(x==0){
            sb.append(x);
        }else{
            while (x>0){
                sb.append(x%2);
                x/=2;
            }
        }

        return sb.reverse().toString();
    }

    public int maxConsecutive(int bottom, int top, int[] special) {
        int res = 0;
        Arrays.sort(special);
        for(int i=1;i<special.length;i++){
            res = Math.max(res,special[i]-special[i-1]-1);
        }
        res = Math.max(res,special[0]-bottom);
        res = Math.max(res,special[top-special.length-1]);
        return res;

    }

}
