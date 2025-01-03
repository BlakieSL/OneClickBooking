package source.code.oneclickbooking.advice

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Aspect
class LoggingAroundAdvice {
    @Pointcut("execution(* source.code.oneclickbooking.controller..*(..))")
    fun loggingExecution() {
    }

    @Around(value = "loggingExecution()")
    fun loggingAroundAdvice(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        LOGGER.info("> Before Executing Method: " +
                "${proceedingJoinPoint.signature.declaringTypeName}.${proceedingJoinPoint.signature.name}"
        )

        val startTime = System.currentTimeMillis();
        val returnValue = proceedingJoinPoint.proceed();
        val endTime = System.currentTimeMillis();

        LOGGER.info("> After Executing Method:  " +
                "${proceedingJoinPoint.signature.declaringTypeName}.${proceedingJoinPoint.signature.name}," +
                " With Duration: ${endTime - startTime}ms"
        )

        return returnValue;
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(LoggingAroundAdvice::class.java);
    }
}