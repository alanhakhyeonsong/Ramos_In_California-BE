package ramos.InCalifornia.domain.board.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class NotWriterException extends BusinessException {

    public NotWriterException() {
        super(ErrorCode.NOT_WRITER);
    }
}
