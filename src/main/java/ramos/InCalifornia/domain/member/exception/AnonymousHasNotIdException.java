package ramos.InCalifornia.domain.member.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class AnonymousHasNotIdException extends BusinessException {

    public AnonymousHasNotIdException() {
        super(ErrorCode.ANONYMOUS_HAS_NOT_ID);
    }
}
