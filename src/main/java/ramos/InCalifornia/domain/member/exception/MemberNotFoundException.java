package ramos.InCalifornia.domain.member.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
