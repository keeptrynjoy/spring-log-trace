package com.example.logtrace.aop;

import com.example.logtrace.log.LogTrace;
import com.example.logtrace.log.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogTrace logTrace;

    @Pointcut("execution(* com.example.logtrace.domain..*Service*.*(..))")
    public void allService(){};

    @Pointcut("execution(* com.example.logtrace.domain..*Controller*.*(..))")
    public void allRepository(){};

    @Pointcut("execution(* com.example.logtrace.domain..*Repository*.*(..))")
    public void allController(){};

    @Around("allService() || allController() || allRepository()")
    public Object logTrace(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;

        try{
            status = logTrace.begin(joinPoint.getSignature().toShortString());
            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;
        }catch (Throwable e){
            e.fillInStackTrace();
            logTrace.exception(status, e);
            throw e;
        }
    }
}
