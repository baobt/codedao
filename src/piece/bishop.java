package piece;

import main.GamePannel;
import main.Type;

public class bishop extends Pieces {

    public bishop(int col, int color, int row) {
        // Pass a placeholder image path to the super constructor
        super(col, color,row);
        type = Type.BISHOP;
        // Set the correct image based on the color
        if (color == GamePannel.WHITE) {
            image = getImage("/piece/w-bishop");  // Load white bishop image
        } else {
            image = getImage("/piece/b-bishop");  // Load black bishop image
        }
    }
    public boolean canMove ( int targetCol, int targetRow){
        if( isWithinBoard(targetCol,targetRow)&& isSameSquare(targetCol,targetRow)==false){

            if(Math.abs(targetCol - preCol)==Math.abs(targetRow-preRow)){
                if(isValidSquare(targetCol,targetRow) && pieceIsOnDiagonalLine(targetCol,targetRow)==false){
                    return true;

                }
            }
        }

        return false;
    }
}

