package main;

import piece.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class GamePannel extends JPanel implements Runnable{

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    public static ArrayList<Pieces>pieces = new ArrayList<>();
    public static ArrayList<Pieces>simPieces = new ArrayList<>();
    ArrayList<Pieces> PromoPieces = new ArrayList<>();
    Pieces activeP;
    public static Pieces castlingP;
    public static final int WHITE = 0;
    public static final int BlACK = 1;
    int currentColor = WHITE;

    boolean canMove;
    boolean validSquare;
    boolean promotion;

    public GamePannel(){

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.black);

        setPieces();
        testPromotion();
        copyPieces(pieces,simPieces);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
    }

    private void update(){

        if(promotion){
            promoting();

        }else {
            if (mouse.pressed){
                if(activeP == null){
                    for(Pieces piece : simPieces){
                        if(piece.color == currentColor &&
                                piece.col == mouse.x/board.SQUARE_SIZE &&
                                piece.row == mouse.y/board.SQUARE_SIZE){
                            activeP = piece;
                        }
                    }
                }
                else {
                    simulate();
                }

            }
            if(mouse.pressed == false){
                if(activeP != null){
                    if(validSquare){

                        copyPieces(simPieces, pieces);
                        activeP.updatePosition();
                        if(castlingP !=null){
                            castlingP.updatePosition();
                        }
                        if(canPromote()){
                            promotion = true;
                        }else {
                            changePlayer();
                        }

                    }
                    else {
                        copyPieces(pieces,simPieces);
                        activeP.resetPosition();
                        activeP = null;
                    }

                }
            }
        }


    }

    private void simulate() {

        canMove = false;
        validSquare=false;

        copyPieces(pieces,simPieces);

        if(castlingP != null){
            castlingP.col = castlingP.preCol;
            castlingP.x = castlingP.getX(castlingP.col);
            castlingP = null;
        }

        activeP.x=mouse.x - Board.HALF_SQUARE_SIZE;
        activeP.y=mouse.y - Board.HALF_SQUARE_SIZE;
        activeP.col = activeP.getCol(activeP.x);
        activeP.row = activeP.getRow(activeP.y);

        if(activeP.canMove(activeP.col, activeP.row)){
             canMove=true;

             if(activeP.hittingP !=null){
                 simPieces.remove(activeP.hittingP.getIndex());
             }
             checkCastling();
             validSquare=true;
        }
    }

    public void setPieces(){
        // White pieces
        pieces.add(new pawn(0, WHITE, 6));
        pieces.add(new pawn(1, WHITE, 6));
        pieces.add(new pawn(2, WHITE, 6));
        pieces.add(new pawn(3, WHITE, 6));
        pieces.add(new pawn(4, WHITE, 6));
        pieces.add(new pawn(5, WHITE, 6));
        pieces.add(new pawn(6, WHITE, 6));
        pieces.add(new pawn(7, WHITE, 6));
        pieces.add(new Rook(0, WHITE, 7));
        pieces.add(new Rook(7, WHITE, 7));
        pieces.add(new knight(1, WHITE, 7));
        pieces.add(new knight(6, WHITE, 7));
        pieces.add(new bishop(2, WHITE, 7));
        pieces.add(new bishop(5, WHITE, 7));
        pieces.add(new queen(3, WHITE, 7));
        pieces.add(new king(4, WHITE, 7));


        // Black pieces
        pieces.add(new pawn(0, BlACK, 1));
        pieces.add(new pawn(1, BlACK, 1));
        pieces.add(new pawn(2, BlACK, 1));
        pieces.add(new pawn(3, BlACK, 1));
        pieces.add(new pawn(4, BlACK, 1));
        pieces.add(new pawn(5, BlACK, 1));
        pieces.add(new pawn(6, BlACK, 1));
        pieces.add(new pawn(7, BlACK, 1));
        pieces.add(new Rook(0,BlACK, 0));
        pieces.add(new Rook(7, BlACK, 0));
        pieces.add(new knight(1, BlACK, 0));
        pieces.add(new knight(6, BlACK, 0));
        pieces.add(new bishop(2, BlACK, 0));
        pieces.add(new bishop(5, BlACK, 0));
        pieces.add(new queen(3, BlACK, 0));
        pieces.add(new king(4, BlACK, 0));
    }
    public void testPromotion(){
//        pieces.add(new pawn(WHITE,0,3));
//        pieces.add(new pawn(BlACK,5,6));
    }
    private void copyPieces(ArrayList<Pieces>source,ArrayList<Pieces>target){

        target.clear();
        for(int i = 0; i < source.size();i++){
            target.add(source.get(i));
        }
    }
    public void checkCastling(){
        if(castlingP !=null){
            if(castlingP.col == 0){
                castlingP.col +=3;
            }
            else if(castlingP.col == 7){
                castlingP.col -=2;
            }
            castlingP.x = castlingP.getX(castlingP.col);
        }
    }
    public void changePlayer(){
        if (currentColor ==WHITE){
            currentColor = BlACK;
            for(Pieces pieces: pieces){
                if(pieces.color == BlACK){
                    pieces.twoStepped = false;
                }
            }
        }
        else {
            currentColor = WHITE;

            for(Pieces pieces: pieces){
                if(pieces.color == WHITE){
                    pieces.twoStepped = false;
                }
            }
        }
        activeP = null;
    }
    public boolean canPromote(){
        if(activeP.type == Type.PAWN){
             if(currentColor == WHITE && activeP.row == 0 || currentColor == BlACK && activeP.row == 7){
                 PromoPieces.clear();
                 PromoPieces.add(new Rook(currentColor,9,2));
                 PromoPieces.add(new knight(currentColor,9,3));
                 PromoPieces.add(new bishop(currentColor ,9,4));
                 PromoPieces.add(new queen(currentColor,9,5));
                 return true;
             }
        }

        return false;
    }
    private void promoting(){
        if(mouse.pressed){
            for(Pieces pieces:PromoPieces){
                if(pieces.col == mouse.x/Board.SQUARE_SIZE && pieces.row == mouse.y/Board.SQUARE_SIZE){
                    switch (pieces.type){
                        case ROOK: simPieces.add(new Rook(currentColor, activeP.col,activeP.row));break;
                        case KNIGHT:simPieces.add(new knight(currentColor,activeP.col,activeP.row));break;
                        case BISHOP:simPieces.add(new bishop(currentColor,activeP.col,activeP.row));break;
                        case QUEEN:simPieces.add(new queen(currentColor,activeP.col,activeP.row));break;
                        default:break;
                    }
                    simPieces.remove(activeP.getIndex());
                    copyPieces(simPieces,pieces);
                    activeP = null;
                    promotion = false;
                    changePlayer();
                }
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Vẽ bàn cờ
        board.draw(g2);

        // Tạo một bản sao của danh sách simPieces để tránh ConcurrentModificationException
        List<Pieces> piecesCopy = new ArrayList<>(simPieces);

        // Lặp qua bản sao để vẽ các quân cờ
        for (Pieces p : piecesCopy) {
            p.draw(g2);
        }

        // Nếu có quân cờ đang hoạt động, hiển thị di chuyển hợp lệ
        if (activeP != null) {
            if (canMove) {
                g2.setColor(Color.white);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE,
                        Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            // Vẽ quân cờ đang hoạt động
            activeP.draw(g2);
        }
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Book Antiqua",Font.PLAIN,40));
        g2.setColor(Color.white);

        if(promotion){
            g2.drawString("Promote to",840,150);
            for(Pieces pieces:PromoPieces){
                g2.drawImage(pieces.image, pieces.getX(pieces.col), pieces.getY(pieces.row),
                        Board.SQUARE_SIZE,Board.SQUARE_SIZE, null );
            }
        }
        else {
        }
        if(currentColor == WHITE){
            g2.drawString("While's Turn", 800,620);

        }
        else{
            g2.drawString("Black's Turn",800,200);
        }
        }


    public void launchgame(){
    gameThread = new Thread(this);
    gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

       while(gameThread !=null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;

            lastTime = currentTime;

            if(delta>=1){
                update();
                repaint();
                delta--;
            }

        }
    }

}
