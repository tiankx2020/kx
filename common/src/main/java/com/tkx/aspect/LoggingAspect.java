package com.tkx.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

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
}
