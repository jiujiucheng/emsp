package com.edwin.emsp.model.vo.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RstStatus {

	ok(1, "ok", "恭喜你，操作成功"),
	bizError(101, "biz error", null),
	missParameter(102, "miss required parameter", "缺少必填参数"),
	invalidParameter(103, "parameter value invalid", "参数不合法"),
	noDataFound(104, "no data found", "对不起，没有找到相关数据"),
	dataExist(105, "data exist", "任务已提交成功，请勿重复提交！"),
	connectTimeOut(106, "connect timed out", "连接超时"),
	serverError(389, "system error", "系统异常，请联系管理员"),
	nullPointerExceptionError(416, "npe", "空指针异常"),
	paramInValid(429, "param invalid", "参数异常，请仔细检查参数");

	private final int code;
	private final String msg;
	private final String desc;


    public static RstStatus getByCode(int code) {
        for (RstStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
