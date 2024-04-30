package spring.log.web.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import spring.log.trace.core.LogTrace;
import spring.log.trace.core.TraceStatus;

@Aspect
@Component
@Profile("dev")
@RequiredArgsConstructor
public class LogAspect {
    private final LogTrace logTrace;

    @Pointcut("execution(* spring.log.web.domain..*Controller*.*(..))")
    public void allRepository(){};

    @Pointcut("execution(* spring.log.web.domain..*Service*.*(..))")
    public void allService(){};

    @Pointcut("execution(* spring.log.web.domain..*Repository*.*(..))")
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
