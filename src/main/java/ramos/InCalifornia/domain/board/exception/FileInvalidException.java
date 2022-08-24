package ramos.InCalifornia.domain.board.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class FileInvalidException extends BusinessException {
    public FileInvalidException() {
        super(ErrorCode.INVALID_FILE_TYPE);
    }
}
