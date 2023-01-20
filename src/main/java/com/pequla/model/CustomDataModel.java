package com.pequla.model;

import com.pequla.model.rest.CachedData;
import lombok.Setter;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Setter
public class CustomDataModel implements TableModel {

    private final List<String> head;
    private List<CachedData> body;

    public static final Integer ID_COL = 0;
    public static final Integer NAME_COL = 1;
    public static final Integer TAG_COL = 2;
    public static final Integer CREATED_COL = 3;

    public CustomDataModel(List<CachedData> body) {
        this.head = Arrays.asList("ID", "NAME", "TAG", "CREATED AT");
        this.body = body;
    }

    @Override
    public int getRowCount() {
        return body.size();
    }

    @Override
    public int getColumnCount() {
        return head.size();
    }

    @Override
    public String getColumnName(int i) {
        return head.get(i);
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return head.get(i).getClass();
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {
        CachedData data = body.get(row);
        if (col == ID_COL) return data.getId();
        if (col == NAME_COL) return data.getName();
        if (col == TAG_COL) return data.getTag();
        if (col == CREATED_COL) {
            return data.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return "NULL";
    }

    @Override
    public void setValueAt(Object o, int row, int col) {

    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {

    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {

    }
}
