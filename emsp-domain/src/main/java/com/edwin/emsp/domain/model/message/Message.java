package com.edwin.emsp.domain.model.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static com.edwin.emsp.domain.model.message.RstStatus.invalidParameter;


/**
 * @Author: jiucheng
 * @Description: Message
 * @Date: 2025/4/12
 */
@Setter
@Getter
@NoArgsConstructor
public class Message<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    private Message(RstStatus status) {
        this(status, null, null);
    }

    private Message(RstStatus status, T data) {
        this(status, null, data);
    }

    private Message(RstStatus status, String msg, T data) {
        if (status != null) {
            this.code = status.getCode();
            this.msg = status.getDesc();
        }
        if (StringUtils.isNotBlank(msg)) {
            this.msg = msg;
        }
        if (data != null) {
            this.data = data;
        }
    }

    private Message(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Message<T> ok() {
        return new Message<>(RstStatus.ok);
    }

    public static <T> Message<T> ok(T data) {
        return new Message<>(RstStatus.ok, data);
    }

    /*
     * !!error方法只在ExceptionHandler中使用，其它位置请直接抛出异常
     */
    public static <T> Message<T> error(RstStatus status) {
        return new Message<>(status);
    }

    public static <T> Message<T> error(RstStatus status, String msg) {
        return new Message<>(status, msg, null);
    }

    public static <T> Message<T> error(int code, String msg) {
        return new Message<>(code, msg, null);
    }

    public static <T> Message<T> error(int code, String msg, T data) {
        return new Message<>(code, msg, data);
    }

    public static <T> Message<T> error(RstStatus status, String msg, T data) {
        return new Message<>(status, msg, data);
    }

    public static <T> Message<T> error(RstStatus status, Throwable ex) {
        return new Message<>(status, ex.getMessage(), null);
    }

    public static Message error(String defaultMessage) {
        return new Message(invalidParameter, defaultMessage, "");
    }


}
