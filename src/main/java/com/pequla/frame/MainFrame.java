package com.pequla.frame;

import com.pequla.model.CustomDataModel;
import com.pequla.model.rest.CachedData;
import com.pequla.model.rest.Page;
import com.pequla.service.DataService;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener, ListSelectionListener {

    private final JButton first;
    private final JButton prev;
    private JLabel current;
    private final JButton next;
    private final JButton last;
    private JScrollPane pane;
    private JTable table;
    private CustomDataModel tableModel;
    private Integer pageNumber = 0;
    private JLabel info;
    private Page<CachedData> rsp;

    public MainFrame() throws HeadlessException, IOException, InterruptedException {
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setResizable(false);
        this.setTitle("Swing Ping - Data Browser");
        this.setIconImage(ImageIO.read(ClassLoader.getSystemResource("img/icon.png")));
        createTable();

        this.first = new JButton("First");
        first.addActionListener(this);
        this.prev = new JButton("Previous");
        prev.addActionListener(this);
        this.next = new JButton("Next");
        next.addActionListener(this);
        this.last = new JButton("Last");
        last.addActionListener(this);
        buttonControl();

        JPanel controls = new JPanel(new FlowLayout());
        controls.add(first);
        controls.add(prev);
        controls.add(current);
        controls.add(next);
        controls.add(last);
        this.add(controls);
        this.add(info);
        this.add(pane);
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent event) {
        if (first.equals(event.getSource())) {
            if (rsp.getFirst()) return;
            pageNumber = 0;
            createTable();
            buttonControl();
            return;
        }
        if (prev.equals(event.getSource())) {
            if (rsp.getFirst()) return;
            pageNumber -= 1;
            createTable();
            buttonControl();
            return;
        }
        if (next.equals(event.getSource())) {
            if (rsp.getLast()) return;
            pageNumber += 1;
            createTable();
            buttonControl();
            return;
        }
        if (last.equals(event.getSource())) {
            if (rsp.getLast()) return;
            pageNumber = rsp.getTotalPages() - 1;
            createTable();
            buttonControl();
        }
    }

    private void buttonControl() {
        if (rsp.getFirst()) {
            first.setEnabled(false);
            prev.setEnabled(false);
            next.setEnabled(true);
            last.setEnabled(true);
            next.requestFocus();
        } else if (rsp.getLast()) {
            first.setEnabled(true);
            prev.setEnabled(true);
            next.setEnabled(false);
            last.setEnabled(false);
            prev.requestFocus();
        } else {
            first.setEnabled(true);
            prev.setEnabled(true);
            next.setEnabled(true);
            last.setEnabled(true);
        }
    }

    private void createTable() throws IOException, InterruptedException {
        rsp = DataService.getInstance().getCachedData(pageNumber, 12);
        if (tableModel == null)
            tableModel = new CustomDataModel(rsp.getContent());
        else
            tableModel.setBody(rsp.getContent());

        if (current == null)
            current = new JLabel(Integer.toString(pageNumber));
        else
            current.setText(Integer.toString(pageNumber));

        if (table == null) {
            table = new JTable(tableModel);
            table.getTableHeader().setReorderingAllowed(false);
            table.getSelectionModel().addListSelectionListener(this);
        } else {
            table.setModel(tableModel);
            SwingUtilities.updateComponentTreeUI(table);
        }

        if (pane == null) {
            pane = new JScrollPane(table);
            pane.setPreferredSize(this.getSize());
        }

        if (info == null) {
            info = new JLabel();
            info.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        info.setText(String.format("Page %s out of %s - Showing %s out of %s elements",
                rsp.getPageable().getPageNumber(), rsp.getTotalPages() - 1, rsp.getSize(), rsp.getTotalElements()));
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
            try {
                Integer id = (Integer) table.getValueAt(table.getSelectedRow(), CustomDataModel.ID_COL);
                CachedData data = DataService.getInstance().getCachedDataForId(id);
                PlayerFrame playerFrame = new PlayerFrame(data);
                playerFrame.setLocationRelativeTo(this);
                playerFrame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,e.getMessage(), e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
