package com.edwin.emsp.model.vo.message;

import com.edwin.emsp.common.util.MessageUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RstStatus {

	ok(1, "ok", "response.status.ok.desc"),
	bizError(101, "biz error", "response.status.biz.desc"),
	missParameter(102, "miss required parameter", "response.status.missParameter.desc"),
	invalidParameter(103, "parameter value invalid", "response.status.invalidParameter.desc"),
	noDataFound(104, "no data found", "response.status.noDataFound.desc"),
	dataExist(105, "data exist", "response.status.dataExist.desc"),
	connectTimeOut(106, "connect timed out", "response.status.connectTimeOut.desc"),
	serverError(389, "system error", "response.status.serverError.desc"),
	nullPointerExceptionError(416, "npe", "response.status.nullPointerExceptionError.desc"),
	paramInValid(429, "param invalid", "response.status.paramInValid.desc"),
	forbidden(403,"access denied","response.forbidden.desc");

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

	public String getDesc() {
		return MessageUtils.message(this.desc, (Object) null);
	}
}
