package ru.job4j.accidents.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request, Model model) {
        model.addAttribute("error", "Объявление не найдено.");
        log.warn("Requested resource was not found. Method: {}. URI: {}. Query: {}. Message: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                e.getMessage());
        return "error/404";
    }

    @ExceptionHandler(SecurityException.class)
    public String handleSecurityException(SecurityException e, HttpServletRequest request, Model model) {
        model.addAttribute("error", "У вас нет прав для выполнения этого действия.");
        log.warn("Access denied. Method: {}. URI: {}. Query: {}. Message: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                e.getMessage());
        return "error/403";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request, Model model) {
        model.addAttribute("error", "Во время обработки запроса произошла ошибка.");
        log.error("Unexpected application error. Method: {}. URI: {}. Query: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                e);
        return "error/500";
    }
}
