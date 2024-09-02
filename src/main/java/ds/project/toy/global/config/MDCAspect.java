package ds.project.toy.global.config;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class MDCAspect {

    @Pointcut("execution(* ds.project.toy.api..controller..*(..))")
    public void controllerAdvice() {
    }

    @Before("controllerAdvice()")
    public void requestLoggingBefore() {

        // MDC TraceId 세팅
        MDC.put("traceId", UUID.randomUUID().toString());
        MDC.put("url",getCurrentRequestUrl());
    }

    @AfterReturning(pointcut = "controllerAdvice()")
    public void requestLoggingAfter() {
        // MDC Clear
        MDC.clear();
    }

    private String getCurrentRequestUrl() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
            .getRequest().getRequestURI();
    }
}
