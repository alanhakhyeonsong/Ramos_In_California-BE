package ramos.InCalifornia.domain.auth.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class IdPasswordMismatchException extends BusinessException {
    public IdPasswordMismatchException() {
        super(ErrorCode.ID_PASSWORD_IS_WRONG);
    }
}
