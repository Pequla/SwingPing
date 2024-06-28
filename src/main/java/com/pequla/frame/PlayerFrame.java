package com.pequla.frame;

import com.pequla.model.rest.CachedData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class PlayerFrame extends JDialog {

    public PlayerFrame(CachedData data) throws HeadlessException, IOException {
        this.setTitle(data.getName());
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setIconImage(ImageIO.read(new URL(data.getAvatar())));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(400, 500);
        this.setModal(true);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createLeftAlignedLabel("ID: " + data.getId()));
        panel.add(createLeftAlignedLabel("Minecraft: " + data.getName()));
        panel.add(createLeftAlignedLabel("Discord: " + data.getTag()));
        panel.add(createLeftAlignedLabel("Linked At: " + data.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        this.add(panel);

        this.pack();
    }

    private JLabel createLeftAlignedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to the left
        label.setFont(new Font("Consolas", Font.PLAIN, 16));
        return label;
    }
}
