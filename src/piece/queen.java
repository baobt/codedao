package piece;

import main.GamePannel;
import main.Type;

public class queen extends Pieces {

    public queen(int col, int color, int row) {
        // Pass a placeholder image path to the super constructor
        super(col, color, row);  // Placeholder for imagePath, actual path set below
        type = Type.QUEEN;
        // Set the correct image based on the color
        if (color == GamePannel.WHITE) {
            image = getImage("/piece/w-queen");  // Load white queen image
        } else {
            image = getImage("/piece/b-queen");  // Load black queen image
        }
    }


    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard( targetCol,targetRow ) && isSameSquare ( targetCol, targetRow )==false){
            if(targetCol == preCol || targetRow == preRow){
                if(isValidSquare(targetCol,targetRow) && pieceIsOnStraightLine(targetCol,targetRow) ==false){
                    return true;
                }

            }
            if (Math.abs(targetCol-preCol)== Math.abs(targetRow - preRow)){
                 if(isValidSquare(targetCol,targetRow)&& pieceIsOnDiagonalLine(targetCol,targetRow)== false){

                return true;
            }

        }
        }
        return false;
    }
}

