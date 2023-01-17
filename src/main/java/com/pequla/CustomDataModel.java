package com.pequla;

import com.pequla.model.CachedData;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomDataModel implements TableModel {

    private final List<String> head;
    private final List<CachedData> body;

    private static final Integer ID_COL = 0;
    private static final Integer NAME_COL = 1;
    private static final Integer UUID_COL = 2;
    private static final Integer DISCORD_COL = 3;
    private static final Integer TAG_COL = 4;
    private static final Integer AVATAR_COL = 5;
    private static final Integer GUILD_COL = 6;
    private static final Integer CREATED_COL = 7;

    public CustomDataModel(List<CachedData> body) throws IOException, InterruptedException {
        this.head = Arrays.asList("ID", "NAME", "UUID", "DISCORD ID", "TAG", "AVATAR", "GUILD ID", "CREATED AT");
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
        if (col == UUID_COL) return data.getUuid();
        if (col == DISCORD_COL) return data.getDiscordId();
        if (col == TAG_COL) return data.getTag();
        if (col == AVATAR_COL) return data.getAvatar();
        if (col == GUILD_COL) return data.getGuildId();
        if (col == CREATED_COL) return data.getCreatedAt();
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
