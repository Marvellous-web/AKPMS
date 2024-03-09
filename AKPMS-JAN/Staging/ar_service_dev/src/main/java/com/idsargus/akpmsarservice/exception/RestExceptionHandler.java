package com.idsargus.akpmsarservice.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.idsargus.akpmsarservice.util.NoOpEntity;



@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private NoOpEntity noOpEntity;

	
	//	@ExceptionHandler(TransactionSystemException.class)
//	protected ResponseEntity<List<String>> handleTransactionException(TransactionSystemException ex) throws Throwable {
//		Throwable cause = ex.getCause();
//		if (!(cause instanceof RollbackException))
//			throw cause;
//		if (!(cause.getCause() instanceof ConstraintViolationException))
//			throw cause.getCause();
//		ConstraintViolationException validationException = (ConstraintViolationException) cause.getCause();
//		List<String> messages = validationException.getConstraintViolations().stream()
//				.map(ConstraintViolation::getMessage).collect(Collectors.toList());
//		return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(TransactionSystemException.class)
	protected ResponseEntity<List<String>> handleTransactionException(TransactionSystemException ex) throws Throwable {
		Throwable cause = ex.getCause();

		if (!(cause instanceof RollbackException))
			throw cause;

		if (!(cause.getCause() instanceof ConstraintViolationException))
			throw cause.getCause();

		ConstraintViolationException validationException = (ConstraintViolationException) cause.getCause();
		List<String> messages = validationException.getConstraintViolations().stream()
				.map(ConstraintViolation::getMessage).collect(Collectors.toList());

		return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	 @ExceptionHandler(value = {ResourceNotFoundException.class}) public
	 ResponseEntity<NoOpEntity>
	 resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
	 noOpEntity.setId(0); 
	 
	 return new ResponseEntity<NoOpEntity>(noOpEntity,HttpStatus.OK); }
}
