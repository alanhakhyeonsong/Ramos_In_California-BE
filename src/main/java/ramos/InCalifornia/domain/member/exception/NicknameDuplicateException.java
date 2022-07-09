package ramos.InCalifornia.domain.member.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class NicknameDuplicateException extends BusinessException {

    public NicknameDuplicateException() {
        super(ErrorCode.NICKNAME_ALREADY_EXISTS);
    }
}
