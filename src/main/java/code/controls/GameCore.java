package code.controls;

import lombok.Getter;
import code.board.Board;
import code.board.Direction;
import code.board.TileState;
import code.board.Tile;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameCore extends UniversalAdapter{

    private static final String BUTTON_CHECK = "CHECK WIN";
    private static final String BUTTON_RESTART = "RESTART";
    public static final int INITIAL_BOARD_SIZE = 8;
    private final JFrame frame;
    private Board currentBoard;
    @Getter
    private JLabel gameInfoLabel;
    private int currentBoardSize;
    private int levelNumber;

    public GameCore(JFrame frame) {
        this.frame = frame;
        this.currentBoardSize = INITIAL_BOARD_SIZE;
        this.levelNumber = 1;
        this.initializeNewBoard(this.currentBoardSize);
        this.frame.add(this.currentBoard);
        this.gameInfoLabel = new JLabel();
        this.updateGameInfoLabel();
    }

    private void initializeNewBoard(int dimension) {
        this.currentBoard = new Board(dimension);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
    }

    private void updateGameInfoLabel() {
        this.gameInfoLabel.setText("   CURRENT BOARD SIZE: " + this.currentBoardSize + "     |     LEVEL: " + this.levelNumber);
    }

    private void gameRestart() {
        this.frame.remove(this.currentBoard);
        this.initializeNewBoard(this.currentBoardSize);
        this.frame.add(this.currentBoard);
        this.updateGameInfoLabel();
        this.frame.revalidate();
        this.frame.repaint();
        this.frame.setFocusable(true);
        this.frame.requestFocus();
    }

    public void checkWin() {
        Tile currentTile = null;

        for (int i = 0; i < this.currentBoardSize; i++)
            for (int j = 0; j < this.currentBoardSize; j++){
                if(this.currentBoard.getBoard()[i][j].getTileState().equals(TileState.START)) {
                    currentTile = this.currentBoard.getBoard()[i][j];
                    break;
                }
            }

        Direction currentDirection = currentTile.getExitDirectionOne();
        Tile nextTile = null;
        ArrayList<Tile> visitedTiles = new ArrayList<>();

        while(true) {
            visitedTiles.add(currentTile);

            if(currentDirection == Direction.UP) {
                if(this.getFirstCoordinate(currentTile)-1 < 0) {
                    nextTile.setIsCorrect(true);
                    this.currentBoard.repaint();
                    return;
                }
                nextTile = this.currentBoard.getBoard()[this.getFirstCoordinate(currentTile)-1][this.getSecondCoordinate(currentTile)];

                if(nextTile.getExitDirectionOne() == Direction.DOWN) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionTwo();
                }
                else if(nextTile.getExitDirectionTwo() == Direction.DOWN) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionOne();
                }
                else {
                    this.currentBoard.repaint();
                    return;
                }
            }
            else if(currentDirection == Direction.RIGHT) {
                if(this.getSecondCoordinate(currentTile)+1 > this.currentBoardSize-1) {
                    currentTile.setIsCorrect(true);
                    this.currentBoard.repaint();
                    return;
                }
                nextTile = this.currentBoard.getBoard()[this.getFirstCoordinate(currentTile)][this.getSecondCoordinate(currentTile)+1];

                if(nextTile.getExitDirectionOne() == Direction.LEFT) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionTwo();
                }
                else if(nextTile.getExitDirectionTwo() == Direction.LEFT) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionOne();
                }
                else {
                    this.currentBoard.repaint();
                    return;
                }
            }
            else if(currentDirection == Direction.DOWN) {
                if(this.getFirstCoordinate(currentTile)+1 > this.currentBoardSize-1) {
                    currentTile.setIsCorrect(true);
                    this.currentBoard.repaint();
                    return;
                }
                nextTile = this.currentBoard.getBoard()[this.getFirstCoordinate(currentTile)+1][this.getSecondCoordinate(currentTile)];

                if(nextTile.getExitDirectionOne() == Direction.UP) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionTwo();
                }
                else if(nextTile.getExitDirectionTwo() == Direction.UP) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionOne();
                }
                else {
                    this.currentBoard.repaint();
                    return;
                }
            }
            else if(currentDirection == Direction.LEFT) {
                if(this.getSecondCoordinate(currentTile)-1 < 0) {
                    currentTile.setIsCorrect(true);
                    this.currentBoard.repaint();
                    return;
                }
                nextTile = this.currentBoard.getBoard()[this.getFirstCoordinate(currentTile)][this.getSecondCoordinate(currentTile)-1];

                if(nextTile.getExitDirectionOne() == Direction.RIGHT) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionTwo();
                }
                else if(nextTile.getExitDirectionTwo() == Direction.RIGHT) {
                    nextTile.setIsCorrect(true);
                    currentDirection = nextTile.getExitDirectionOne();
                }
                else {
                    this.currentBoard.repaint();
                    return;
                }
            }

            if(visitedTiles.contains(nextTile)) {
                nextTile.setIsCorrect(false);
                currentTile.setIsCorrect(true);
                this.currentBoard.repaint();
                return;
            }

            if(nextTile.getTileState().equals(TileState.END)) {
                this.levelNumber++;
                this.updateGameInfoLabel();
                this.gameRestart();
                break;
            }

            currentTile = nextTile;
        }
    }

    public int getFirstCoordinate(Tile tile) {
        for(int i=0; i<this.currentBoardSize; i++)
            for(int j=0; j<this.currentBoardSize; j++) {
                if(this.currentBoard.getBoard()[i][j] == tile) return i;
            }
        return 0;
    }

    public int getSecondCoordinate(Tile tile) {
        for(int i=0; i<this.currentBoardSize; i++)
            for(int j=0; j<this.currentBoardSize; j++) {
                if(this.currentBoard.getBoard()[i][j] == tile) return j;
            }
        return 0;
    }

    public Direction changeDirection(Direction direction) {

        switch (direction) {
            case UP:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.UP;
                break;
        }

        return direction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case (BUTTON_RESTART):
                levelNumber = 1;
                this.gameRestart();
                break;
            case (BUTTON_CHECK):
                this.checkWin();
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            this.currentBoard.repaint();
            return;
        }
        ((Tile) current).setHighlight(true);
        this.currentBoard.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile) || ((Tile)current).getTileState().equals(TileState.EMPTY)) {
            return;
        }
        if (((Tile) current).getTileState().equals(TileState.STRAIGHT) || ((Tile) current).getTileState().equals(TileState.CURVED)) {

            ((Tile) current).setExitDirectionOne(changeDirection(((Tile) current).getExitDirectionOne()));
            ((Tile) current).setExitDirectionTwo(changeDirection(((Tile) current).getExitDirectionTwo()));

            ((Tile) current).setHighlight(true);
            this.currentBoard.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(!((JSlider) e.getSource()).getValueIsAdjusting()) {
            this.currentBoardSize = ((JSlider) e.getSource()).getValue();
            this.updateGameInfoLabel();
            this.gameRestart();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                levelNumber = 1;
                this.gameRestart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.frame.dispose();
                break;
            case KeyEvent.VK_ENTER:
                this.checkWin();
                break;
        }
    }

}
