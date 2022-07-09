package ramos.InCalifornia.domain.auth.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class MemberAlreadyExistException extends BusinessException {

    public MemberAlreadyExistException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}
