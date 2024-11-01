package piece;

import main.GamePannel;
import main.Type;

public class knight extends Pieces {

    public knight(int col, int color, int row) {
        // Pass a placeholder image path to the super constructor
        super(col, color, row);  // Placeholder for imagePath, actual path set below
        type = Type.KNIGHT;
        // Set the correct image based on the color
        if (color == GamePannel.WHITE) {
            image = getImage("/piece/w-knight");  // Load white knight image
        } else {
            image = getImage("/piece/b-knight");  // Load black knight image
        }
    }
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol,targetRow)){
            if(Math.abs(targetCol-preCol)*Math.abs(targetRow-preCol)==2){
                if(isValidSquare(targetCol,targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
