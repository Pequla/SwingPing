package com.pequla;

import com.pequla.model.CachedData;
import com.pequla.model.Page;
import com.pequla.service.DataService;
import lombok.SneakyThrows;

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
    private Page<CachedData> rsp;

    public MainFrame() throws HeadlessException, IOException, InterruptedException {
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Swing Ping");
        this.rsp = DataService.getInstance().getCachedData(pageNumber, pageSize);
        JTable table = new JTable(new CustomDataModel(rsp.getContent()));
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
        this.add(controls);
        this.add(pane);
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent event) {
        if (prev.equals(event.getSource())) {
            if (rsp.getFirst()) return;
            pageNumber -= 1;
            rsp = DataService.getInstance().getCachedData(pageNumber, pageSize);
            current.setText(Integer.toString(pageNumber));
            return;
        }
        if (next.equals(event.getSource())) {
            if (rsp.getLast()) return;
            pageNumber += 1;
            rsp = DataService.getInstance().getCachedData(pageNumber, pageSize);
            current.setText(Integer.toString(pageNumber));
        }
    }
}
