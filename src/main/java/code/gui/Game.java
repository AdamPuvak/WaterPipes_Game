package code.gui;

import code.controls.GameCore;

import javax.swing.*;
import java.awt.*;

public class Game {

    private static final String BUTTON_CHECK = "CHECK WIN";
    private static final String BUTTON_RESTART = "RESTART";

    public Game() {
        JFrame frame = new JFrame("Pipes!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(710,820);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        GameCore core = new GameCore(frame);
        frame.addKeyListener(core);

        JButton buttonCheck = new JButton(BUTTON_CHECK);
        buttonCheck.addActionListener(core);
        buttonCheck.setFocusable(false);

        JButton buttonRestart = new JButton(BUTTON_RESTART);
        buttonRestart.addActionListener(core);
        buttonRestart.setFocusable(false);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 10, 8);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(core);
        slider.setFocusable(false);

        JPanel menu = new JPanel();
        menu.setBackground(Color.LIGHT_GRAY);
        menu.setLayout(new GridLayout(2, 2));
        menu.add(buttonCheck);
        menu.add(buttonRestart);
        menu.add(core.getGameInfoLabel());
        menu.add(slider);
        frame.add(menu, BorderLayout.PAGE_START);

        frame.setVisible(true);
    }

}
