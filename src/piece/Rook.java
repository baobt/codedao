package piece;

import main.GamePannel;
import main.Type;

public class Rook extends Pieces {

    public Rook(int col, int color, int row) {

        super(col, color, row);
        type = Type.ROOK;

        if (color == GamePannel.WHITE) {
            image = getImage("/piece/w-rook");
        } else {
            image = getImage("/piece/b-rook");
        }
    }
    public boolean canMove(int targetCol, int targetRow) {
        // Kiểm tra nếu không có quân cờ nào chặn đường đi
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow))  {
            if (targetCol == preCol || targetRow == preRow) {
                if (isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }

}

