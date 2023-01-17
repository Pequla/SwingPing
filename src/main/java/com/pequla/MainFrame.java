package com.pequla;

import com.pequla.service.DataService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {

    private JButton prev;
    private JLabel current;
    private JButton next;

    private Integer pageNumber = 0;
    private Integer pageSize = 30;

    public MainFrame() throws HeadlessException, IOException, InterruptedException {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Swing Ping");

        JTable table = new JTable(new CustomDataModel(DataService.getInstance().getCachedData(pageNumber, pageSize).getContent()));
        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(this.getSize());

        this.prev = new JButton("Previous");
        prev.addActionListener(this);
        this.current = new JLabel(Integer.toString(pageNumber));
        this.next = new JButton("Next");
        next.addActionListener(this);

        JPanel controls = new JPanel(new FlowLayout());
        controls.add(prev);
        controls.add(current);
        controls.add(next);
        controls.add(pane);
        this.add(controls);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (prev.equals(event.getSource())) {
            JOptionPane.showMessageDialog(this, "Prev button pressed");
            return;
        }
        if (next.equals(event.getSource())) {
            JOptionPane.showMessageDialog(this, "Next button pressed");
        }
    }
}
