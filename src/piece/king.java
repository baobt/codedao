package piece;

import main.GamePannel;
import main.Type;

public class king extends Pieces {

    public king(int col, int color, int row) {
        // Pass a dummy image path to the super constructor
        super(col, color, row);  // Placeholder for imagePath, actual path set below
        type = Type.KING;
        // Now set the correct image based on the color
        if (color == GamePannel.WHITE) {
            image = getImage("/piece/w-king");  // Load white king image
        } else {
            image = getImage("/piece/b-king");  // Load black king image
        }
    }
    public boolean canMove( int targetCol, int targetRow){
        if(isWithinBoard(targetCol,targetRow)){
            if(Math.abs( targetCol - preCol )+ Math.abs( targetRow - preRow ) == 1 ||
                Math.abs(targetCol-preCol) * Math.abs(targetRow - preRow)==1){

                if(isValidSquare(targetCol,targetRow)){
                    return true;
                }

            }
            if(moved == false){
                if(targetCol == preCol+2 && targetRow ==preRow && pieceIsOnStraightLine(targetCol,targetRow)==false){
                    for(Pieces pieces:GamePannel.simPieces){
                        if(pieces.col == preCol+3 && pieces.row == preRow && pieces.moved == false){
                            GamePannel.castlingP = pieces;
                            return true;
                        }
                    }
                }
                if(targetCol == preCol-2 && targetRow ==preRow && pieceIsOnStraightLine(targetCol,targetRow)==false){
                        Pieces p[]= new Pieces[2];
                        for (Pieces pieces:GamePannel.simPieces){
                            if(pieces.col == preCol-3 && pieces.row == targetRow){
                                p[0] = pieces;
                            }
                            if(pieces.col == preCol-4 && pieces.row == targetRow){
                                p[1] = pieces;
                            }
                            if(p[0]==null && p[1]!=null && p[1].moved == false){
                                GamePannel.castlingP = p[1];
                                return true;
                            }
                        }
                }
            }
        }
        return false;
    }
}
