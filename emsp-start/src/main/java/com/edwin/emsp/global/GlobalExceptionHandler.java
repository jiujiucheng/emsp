package com.edwin.emsp.global;

import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.domain.model.message.Message;
import com.edwin.emsp.domain.model.message.RstStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

/**
 * @Author: jiucheng
 * @Description: 全局异常处理
 * @Date: 2025/4/12
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 业务 BizException 异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public Message<?> bindException(HttpServletRequest req, BizException e) {
        logger.warn("请求路径[{}]发生未知异常！原因是:", req.getRequestURI(), e);
        return Message.error(RstStatus.bizError, e);
    }

    /**
     * 拦截表单参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public Message<?> bindException(HttpServletRequest req, BindException e) {
        logger.warn("请求路径[{}]发生未知异常！原因是:", req.getRequestURI(), e);
        return Message.error(RstStatus.invalidParameter, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 拦截JSON参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Message<?> bindException(HttpServletRequest req, MethodArgumentNotValidException e) {
        logger.warn("请求路径[{}]发生未知异常！原因是:", req.getRequestURI(), e);
        return Message.error(RstStatus.invalidParameter, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Message<?> exceptionHandler(HttpServletRequest req, NullPointerException e) {
        logger.error("请求路径[{}]发生空指针异常！原因是:", req.getRequestURI(), e);
        return Message.error(RstStatus.nullPointerExceptionError);
    }

    /**
     * 类型
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Message<?> exceptionHandler(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
        logger.error("请求路径[{}]发生未知异常！原因是:", req.getRequestURI(), e);
        return Message.error(RstStatus.paramInValid.getCode(), RstStatus.paramInValid.getMsg() + "对应url" + req.getRequestURI());
    }

    /**
     * 处理其他异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Message<?> exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("请求路径[{}]发生未知异常！原因是:", req.getRequestURI(), e);
        return Message.error(RstStatus.serverError);
    }
}
