package com.revamp.springbatch.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.revamp.springdal.repo)")
    public void springDalRepo(){}

    @Pointcut("execution(* com.revamp.springmongo.service)")
    public void springMongoService(){}

    @Pointcut("execution(* com.revamp.springmongo.controller)")
    public void springMongoController(){}

    @Pointcut("execution(* com.revamp.springbatch.service)")
    public void springBatchService(){}

    @Pointcut("execution(* com.revamp.springbatch.controller)")
    public void springBatchController(){}

    @Around("springDalRepo() && springMongoService() && springMongoController && springBatchService() && springBatchController")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        final StopWatch stopWatch = new StopWatch();

        //calculate method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        LOGGER.info("REVAMP - Execution time of "
                + methodSignature.getDeclaringType().getSimpleName() // Class Name
                + "." + methodSignature.getName() + " " // Method Name
                + ":: " + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }
}
