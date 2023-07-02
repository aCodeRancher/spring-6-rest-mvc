package guru.springframework.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Created by jt, Spring Framework Guru.
 */
@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception){
         ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
         if (exception.getCause().getCause() instanceof ConstraintViolationException){
             ConstraintViolationException ve = (ConstraintViolationException) exception.getCause().getCause();
             AtomicReference<Map<String, String>> mapAtomicReference = new AtomicReference<>();
             mapAtomicReference.set(new HashMap<String, String>());
             List errors = ve.getConstraintViolations().stream()
                        .map(constraintViolation -> {
                           //  Map<String, String> errMap = new HashMap<>();
                             mapAtomicReference.get().put(constraintViolation.getPropertyPath().toString(),
                                         constraintViolation.getMessage());
                         return mapAtomicReference.get();
                     }).collect(Collectors.toList());
              return responseEntity.body(errors);
         }
        return responseEntity.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){

        List errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorList);
    }
}
