package com.pequla;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.pequla.frame.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception ex) {
            System.err.println("Failed to initialize app");
        }
    }
}