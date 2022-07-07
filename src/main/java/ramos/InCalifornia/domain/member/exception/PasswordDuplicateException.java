package ramos.InCalifornia.domain.member.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class PasswordDuplicateException extends BusinessException {

    public PasswordDuplicateException() {
        super(ErrorCode.PASSWORD_DUPLICATED);
    }
}
