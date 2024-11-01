package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("simple chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePannel gp = new GamePannel();
        window.add(gp);
        window.pack();

        window.setResizable(false);
        window.setVisible(true);

        gp.launchgame();
    }
}