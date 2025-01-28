package code.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Tile extends JPanel {

    @Getter
    private Map<Direction, Tile> neighbours;
    @Getter @Setter
    private TileState tileState;
    @Setter
    private boolean highlight;
    @Setter
    private boolean isCorrect;
    @Getter @Setter
    private Direction exitDirectionOne;
    @Getter @Setter
    private Direction exitDirectionTwo;

    public Tile() {
        this.neighbours = new HashMap<>();
        this.tileState = TileState.EMPTY;
        this.isCorrect = false;
        Color c1 = new Color(175, 206, 132);
        this.setBackground(c1);
    }

    public void setIsCorrect(boolean b) {
        this.isCorrect = b;
    }

    public void addNeighbour(Direction direction, Tile neighbour){
        this.neighbours.put(direction, neighbour);
    }

    public ArrayList<Tile> getAllNeighbours() {
        return new ArrayList<>(this.neighbours.values());
    }

    public void drawDirection(Direction direction, Graphics g) {

        ((Graphics2D) g).setStroke(new BasicStroke(3));

        if(this.isCorrect || this.tileState.equals(TileState.START)) g.setColor(Color.BLUE);
        else g.setColor(Color.GRAY);

        if(direction == Direction.UP) {
            g.drawRect((int)(0 + this.getWidth() * 0.35), 0, (int)(this.getWidth() * 0.3), (int)(this.getHeight() * 0.64));
            g.fillRect((int)(0 + this.getWidth() * 0.35), 0, (int)(this.getWidth() * 0.3), (int)(this.getHeight() * 0.64));
        }
        else if(direction == Direction.RIGHT) {
            g.drawRect((int)(0 + this.getWidth() * 0.35), (int)(this.getHeight() * 0.35), (int)(this.getWidth() * 0.65), (int)(this.getHeight() * 0.3));
            g.fillRect((int)(0 + this.getWidth() * 0.35), (int)(this.getHeight() * 0.35), (int)(this.getWidth() * 0.65), (int)(this.getHeight() * 0.3));
        }
        else if(direction == Direction.DOWN) {
            g.drawRect((int)(0 + this.getWidth() * 0.35), (int)(this.getHeight() * 0.35), (int)(this.getWidth() * 0.3), (int)(this.getHeight() * 0.64));
            g.fillRect((int)(0 + this.getWidth() * 0.35), (int)(this.getHeight() * 0.35), (int)(this.getWidth() * 0.3), (int)(this.getHeight() * 0.64));
        }
        else if(direction == Direction.LEFT) {
            g.drawRect(0, (int)(this.getHeight() * 0.35), (int)(this.getWidth() * 0.64), (int)(this.getHeight() * 0.3));
            g.fillRect(0, (int)(this.getHeight() * 0.35), (int)(this.getWidth() * 0.64), (int)(this.getHeight() * 0.3));
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);

        if(this.tileState.equals(TileState.START)) this.setBackground(Color.CYAN);
        else if(this.tileState.equals(TileState.END)) this.setBackground(Color.RED);

        drawDirection(this.exitDirectionOne, g);
        drawDirection(this.exitDirectionTwo, g);
        this.isCorrect = false;

        if (this.highlight) {
            g.setColor(Color.GREEN);
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            this.highlight = false;
        } else {
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(2));
        }
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }

}
