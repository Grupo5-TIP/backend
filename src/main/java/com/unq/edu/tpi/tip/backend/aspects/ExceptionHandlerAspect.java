package com.unq.edu.tpi.tip.backend.aspects;

import com.unq.edu.tpi.tip.backend.exceptions.OrderEmptyException;
import com.unq.edu.tpi.tip.backend.exceptions.TableDoesNotHaveOrders;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class ExceptionHandlerAspect{

	@Around("@annotation(ExceptionAspect)")
	private Object aroundHandlerError(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		}catch (TableDoesNotHaveOrders ex){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}catch (OrderEmptyException ex){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage() , ex);
		}
	}
}