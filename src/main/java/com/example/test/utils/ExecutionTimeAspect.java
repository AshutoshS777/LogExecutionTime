/**
 * 
 */
package com.example.test.utils;

/**
 * @author Ashutosh
 *
 */
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExecutionTimeAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTimeAspect.class);

	/**
	 * Called for method annotated with @ExecutionTimeAspect
	 *
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Around(value = "execution(@com.example.test.utils.LogExecutionTime * *(..)) && @annotation(logExecutionTime)")
	public Object logExecutionTime(final ProceedingJoinPoint joinPoint, final LogExecutionTime logExecutionTime)
			throws Throwable {
		long startTime = System.currentTimeMillis();
		TimeUnit timeUnit = logExecutionTime.timeUnit();
		LOGGER.info("Started execution of method : {}.{}() at time {} ",
				joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), startTime);
		Object value = null;
		try {
			value = joinPoint.proceed();
		} finally {
			long endTime = System.currentTimeMillis();
			LOGGER.info("Completed execution of method : {}.{}() at time {}",
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), endTime);
			long diffInMillis = endTime - startTime;
			long diff = timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
			LOGGER.info("Total execution time of method : {}.{}() in {} : {} ",
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
					timeUnit.name(), diff);
		}
		return value;
	}
}