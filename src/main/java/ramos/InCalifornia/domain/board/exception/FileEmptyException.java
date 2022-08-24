package ramos.InCalifornia.domain.board.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class FileEmptyException extends BusinessException {
    public FileEmptyException() {
        super(ErrorCode.FILE_IS_EMPTY);
    }
}
