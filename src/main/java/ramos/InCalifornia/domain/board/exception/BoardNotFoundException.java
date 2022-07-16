package ramos.InCalifornia.domain.board.exception;

import ramos.InCalifornia.global.error.ErrorCode;
import ramos.InCalifornia.global.error.exception.BusinessException;

public class BoardNotFoundException extends BusinessException {

    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
