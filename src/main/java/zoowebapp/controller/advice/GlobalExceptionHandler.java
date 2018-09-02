package zoowebapp.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception e){
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", request.getRequestURL());
        mav.addObject("httpMethod", request.getMethod());
        mav.addObject("exception", e);
        mav.addObject("stackTrace", e.getStackTrace());
        mav.setViewName(ERROR_VIEW);

        return mav;
    }
}