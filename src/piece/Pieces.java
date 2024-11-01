package piece;

import main.Board;
import main.GamePannel;
import main.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;

public class Pieces extends ArrayList<Pieces> {

    public Type type;
    public BufferedImage image;
    public int x,y;
    public int col,row,preCol,preRow;
    public int color;
    public Pieces hittingP;
    public boolean moved,twoStepped;

    public Pieces(int col,int color, int row){
        this.col = col;
        this.row = row;
        this.color = color;
       x = getX(col);
       y = getY(row);

       preCol = col;
       preRow = row;
    }
    public BufferedImage getImage(String imagePath){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        }catch (IOException e){
            e.printStackTrace();

        }
        return image;
    }
    public int getX (int col){
        return col * Board.SQUARE_SIZE;
    }
    public int getY(int row){
        return row * Board.SQUARE_SIZE;
    }

    public int getCol(int x){
        return (x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }
    public int getRow(int y){
        return (y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }
    public int getIndex(){
         for(int index = 0; index<GamePannel.simPieces.size();index++){
             if(GamePannel.simPieces.get(index)==this){
                 return index;
             }
         }
         return 0;
    }
    public void updatePosition(){

        if(type == Type.PAWN){
            if(Math.abs(row-preRow)==2){
                twoStepped = true;
            }
        }

        x = getX(col);
        y = getY(row);
        preRow = getRow(y);
        preCol = getCol(x);
        moved = true;
    }
   public boolean canMove(int targetCol, int targetRow){
        return false;
   }
   public boolean isWithinBoard(int targetCol, int targetRow){
        if(targetCol>= 0 && targetRow<= 7 && targetRow>=0 && targetRow<=7){
            return true;
        }
        return false;
   }
   public void resetPosition(){
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
   }
   public boolean isSameSquare(int targetCol, int targetRow){
        if(targetCol == preCol && targetRow == preRow){
            return true;
        }
        return false;
   }
   public Pieces getHittingP(int targetCol, int targteRow){
        for (Pieces pieces: GamePannel.simPieces){
            if (pieces.col ==  targetCol && pieces.row == targteRow && pieces != this){
                return pieces;
            }
        }
        return null;
   }

   public boolean isValidSquare(int targetCol, int targetRow){
        hittingP = getHittingP(targetCol,targetRow);
        if (hittingP == null){
            return true;
        }
        else {
            if(hittingP.color != color){
                return true;
            }else{
                 hittingP = null;
            }
        }
        return false;
   }

    public boolean pieceIsOnStraightLine(int targetCol, int targetRow) {
        // Move left
        for (int c = preCol - 1; c > targetCol; c--) {  // Chỉ kiểm tra đến trước targetCol
            for (Pieces pieces : GamePannel.simPieces) {
                if (pieces.col == c && pieces.row == targetRow) {
                    hittingP = pieces;
                    return true;
                }
            }
        }
        // Move right
        for (int c = preCol + 1; c < targetCol; c++) {  // Chỉ kiểm tra đến trước targetCol
            for (Pieces pieces : GamePannel.simPieces) {
                if (pieces.col == c && pieces.row == targetRow) {
                    hittingP = pieces;
                    return true;
                }
            }
        }
        // Move up
        for (int r = preRow - 1; r > targetRow; r--) {  // Chỉ kiểm tra đến trước targetRow
            for (Pieces pieces : GamePannel.simPieces) {
                if (pieces.col == targetCol && pieces.row == r) {
                    hittingP = pieces;
                    return true;
                }
            }
        }
        // Move down
        for (int r = preRow + 1; r < targetRow; r++) {  // Chỉ kiểm tra đến trước targetRow
            for (Pieces pieces : GamePannel.simPieces) {
                if (pieces.col == targetCol && pieces.row == r) {
                    hittingP = pieces;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow){

        if(targetRow<preRow){
            //up left
            for (int c = preCol-1; c>targetCol;c--){
                int diff = Math.abs(c-preCol);
                for(Pieces pieces:GamePannel.simPieces){
                    if(pieces.col == c && pieces.row == preRow - diff){
                        hittingP = pieces;
                        return true;
                    }
                }
            }
            //up right
            for (int c = preCol+1; c<targetCol;c++){
                int diff = Math.abs(c-preCol);
                for(Pieces pieces:GamePannel.simPieces){
                    if(pieces.col == c && pieces.row == preRow - diff){
                        hittingP = pieces;
                        return true;
                    }
                }
            }
        }

        if (targetRow>preRow){
            //down left
            for (int c = preCol-1; c>targetCol;c--){
                int diff = Math.abs(c-preCol);
                for(Pieces pieces:GamePannel.simPieces){
                    if(pieces.col == c && pieces.row == preRow + diff){
                        hittingP = pieces;
                        return true;
                    }
                }
            }
            //down right
            for (int c = preCol+1; c<targetCol;c++){
                int diff = Math.abs(c-preCol);
                for(Pieces pieces:GamePannel.simPieces){
                    if(pieces.col == c && pieces.row == preRow + diff){
                        hittingP = pieces;
                        return true;
                    }
                }
            }
        }


        return false;
    }
    public void draw(Graphics2D g2){
         g2.drawImage(image,x,y,Board.SQUARE_SIZE,Board.SQUARE_SIZE,null);
    }
}
