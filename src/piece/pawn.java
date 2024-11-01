package piece;

import main.GamePannel;
import main.Type;

import javax.swing.*;

public class pawn extends Pieces {

    public pawn(int col, int color, int row) {
        // Pass a placeholder image path to the super constructor
        super(col, color, row);  // Placeholder for imagePath, actual path set below
        type = Type.PAWN;
        // Set the correct image based on the color
        if (color == GamePannel.WHITE) {
            image = getImage("/piece/w-pawn");  // Load white pawn image
        } else {
            image = getImage("/piece/b-pawn");  // Load black pawn image
        }
    }
    public boolean canMove(int targetCol, int targetRow) {
        // Kiểm tra nếu không có quân cờ nào chặn đường đi
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow))  {
            int moveValue;
            if(color == GamePannel.WHITE){
                moveValue = -1;
            }
            else {
                moveValue = 1;
            }

            hittingP = getHittingP(targetCol,targetRow);

            if(targetCol == preCol && targetRow == preRow + moveValue && hittingP == null){
                return true;

            }
            if(targetCol == preCol && targetRow == preRow +moveValue*2 && hittingP == null && moved == false &&
                pieceIsOnStraightLine(targetCol,targetRow)== false){
                return true;
            }
            if(Math.abs(targetCol - preCol)== 1 && targetRow == preRow + moveValue && hittingP != null &&
            hittingP.color != color){
                return true;
            }

            //En passant
            if(Math.abs(targetCol - preCol)== 1 && targetRow == preRow + moveValue){
                for(Pieces pieces: GamePannel.simPieces){
                    if(pieces.col == targetCol && pieces.row == preRow && pieces.twoStepped == true){
                        hittingP = pieces;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

