package code.board;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.util.Random;

public class Board extends JPanel {

    @Getter
    private Tile[][] board;
    private final ArrayList<Tile> visitedTiles;
    private Tile currentTile;
    private final ArrayList<Tile> correctPath;
    private final Random rand;

    public Board(int dimension) {
        visitedTiles = new ArrayList<>();
        correctPath = new ArrayList<>();
        rand = new Random();
        this.generateBoard(dimension);
        this.setBackground(Color.lightGray);
    }

    private void generateBoard(int dimension) {
        this.initializeBoard(dimension);

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if(this.board[i][j].getTileState().equals(TileState.START)){
                    currentTile = this.board[i][j];
                }
            }

        generatePath();
        generatePipes();
        generatePipesDirections();
    }

    private void initializeBoard(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[i][j] = new Tile();
                this.add(this.board[i][j]);
            }
        }

        int randNumber1 = rand.nextInt(4);
        int randNumber2 = rand.nextInt(dimension);

        switch(randNumber1) {
            case 0:
                this.board[0][randNumber2].setTileState(TileState.START);
                this.board[dimension-1][dimension-1-randNumber2].setTileState(TileState.END);
                break;
            case 1:
                this.board[dimension-1][randNumber2].setTileState(TileState.START);
                this.board[0][dimension-1-randNumber2].setTileState(TileState.END);
                break;
            case 2:
                this.board[randNumber2][0].setTileState(TileState.START);
                this.board[dimension-1-randNumber2][dimension-1].setTileState(TileState.END);
                break;
            case 3:
                this.board[randNumber2][dimension-1].setTileState(TileState.START);
                this.board[dimension-1-randNumber2][0].setTileState(TileState.END);
                break;
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)  {
                if (i != 0) {
                    this.board[i][j].addNeighbour(Direction.UP, this.board[i-1][j]);
                }
                if (i != dimension - 1) {
                    this.board[i][j].addNeighbour(Direction.DOWN, this.board[i+1][j]);
                }
                if (j != 0) {
                    this.board[i][j].addNeighbour(Direction.LEFT, this.board[i][j-1]);
                }
                if (j != dimension - 1) {
                    this.board[i][j].addNeighbour(Direction.RIGHT, this.board[i][j+1]);
                }
            }
        }
    }

    private void generatePath() {

        if(!visitedTiles.contains(currentTile)) visitedTiles.add(currentTile);

        if(currentTile.getTileState().equals(TileState.END)) {
            correctPath.add(currentTile);
            return;
        }

        ArrayList<Tile> allUnvisitedNeigbours = currentTile.getAllNeighbours();
        allUnvisitedNeigbours.removeIf(visitedTiles::contains);

        if(allUnvisitedNeigbours.size() == 0) {
            correctPath.remove(correctPath.get(correctPath.size()-1));
            currentTile = correctPath.get(correctPath.size()-1);
        }
        else {
            if(!correctPath.contains(currentTile)) correctPath.add(currentTile);
            Collections.shuffle(allUnvisitedNeigbours);
            currentTile = allUnvisitedNeigbours.get(0);
        }
        generatePath();
    }

    private void generatePipes() {

        Direction prevDir = null;
        Direction nextDir = null;

        for(int i=1; i<correctPath.size()-1; i++) {
            currentTile = correctPath.get(i);

            for (Map.Entry entry : currentTile.getNeighbours().entrySet()) {
                if(entry.getValue() == correctPath.get(i-1)) prevDir = (Direction) entry.getKey();
                if(entry.getValue() == correctPath.get(i+1)) nextDir = (Direction) entry.getKey();
            }

            if((prevDir == Direction.LEFT || prevDir == Direction.RIGHT) && (nextDir == Direction.LEFT || nextDir == Direction.RIGHT)) {
                currentTile.setTileState(TileState.STRAIGHT);
            }
            else if((prevDir == Direction.UP || prevDir == Direction.DOWN) && (nextDir == Direction.DOWN || nextDir == Direction.UP)) {
                currentTile.setTileState(TileState.STRAIGHT);
            }
            else if((prevDir == Direction.UP || prevDir == Direction.RIGHT) && (nextDir == Direction.UP || nextDir == Direction.RIGHT)) {
                currentTile.setTileState(TileState.CURVED);
            }
            else if((prevDir == Direction.RIGHT || prevDir == Direction.DOWN) && (nextDir == Direction.RIGHT || nextDir == Direction.DOWN)) {
                currentTile.setTileState(TileState.CURVED);
            }
            else if((prevDir == Direction.DOWN || prevDir == Direction.LEFT) && (nextDir == Direction.DOWN || nextDir == Direction.LEFT)) {
                currentTile.setTileState(TileState.CURVED);
            }
            else if((prevDir == Direction.UP || prevDir == Direction.LEFT) && (nextDir == Direction.UP || nextDir == Direction.LEFT)) {
                currentTile.setTileState(TileState.CURVED);
            }
        }

        currentTile = correctPath.get(0);
        for (Map.Entry entry : currentTile.getNeighbours().entrySet()) {
            if(entry.getValue() == correctPath.get(1)) nextDir = (Direction) entry.getKey();
        }
        currentTile.setExitDirectionOne(nextDir);
        currentTile.setExitDirectionTwo(null);

        currentTile = correctPath.get(correctPath.size()-1);
        for (Map.Entry entry : currentTile.getNeighbours().entrySet()) {
            if(entry.getValue() == correctPath.get(correctPath.size()-2)) prevDir = (Direction) entry.getKey();
        }
        currentTile.setExitDirectionOne(prevDir);
        currentTile.setExitDirectionTwo(null);
    }

    public void generatePipesDirections() {
        Tile currentTile;
        int randNumber;

        for(int i=1; i<correctPath.size()-1; i++) {
            currentTile = correctPath.get(i);
            randNumber = rand.nextInt(4);

            if(currentTile.getTileState().equals(TileState.STRAIGHT)) {
                switch (randNumber%2) {
                    case 0:
                        currentTile.setExitDirectionOne(Direction.LEFT);
                        currentTile.setExitDirectionTwo(Direction.RIGHT);
                        break;
                    case 1:
                        currentTile.setExitDirectionOne(Direction.UP);
                        currentTile.setExitDirectionTwo(Direction.DOWN);
                        break;
                }
            }
            else if(currentTile.getTileState().equals(TileState.CURVED)) {
                switch (randNumber) {
                    case 0:
                        currentTile.setExitDirectionOne(Direction.UP);
                        currentTile.setExitDirectionTwo(Direction.RIGHT);
                        break;
                    case 1:
                        currentTile.setExitDirectionOne(Direction.DOWN);
                        currentTile.setExitDirectionTwo(Direction.RIGHT);
                        break;
                    case 2:
                        currentTile.setExitDirectionOne(Direction.DOWN);
                        currentTile.setExitDirectionTwo(Direction.LEFT);
                        break;
                    case 3:
                        currentTile.setExitDirectionOne(Direction.UP);
                        currentTile.setExitDirectionTwo(Direction.LEFT);
                        break;
                }
            }
        }
    }

}
