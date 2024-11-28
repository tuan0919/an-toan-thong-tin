package View.Component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.function.BiConsumer;

public class MatrixTable_View extends JTable {
    private int minCellSize = 50;
    private Integer[][] data;
    private int viewPortSize = 300;
    private int cellSize;
    private DefaultTableModel model;
    private DefaultTableCellRenderer cellRender;
    private TableModelListener tableModelListener;

    public MatrixTable_View (Integer[][] data) {
        this();
        loadModel(data);
    }

    public MatrixTable_View () {
        this.model = new DefaultTableModel();
    }

    public int getMinCellSize() {
        return minCellSize;
    }

    public void setMinCellSize(int minCellSize) {
        this.minCellSize = minCellSize;
    }

    public int getViewPortSize() {
        return viewPortSize;
    }

    public void setViewPortSize(int viewPortSize) {
        this.viewPortSize = viewPortSize;
    }

    public Integer[][] getData() {
        return data;
    }

    public int getCellSize() {
        return cellSize;
    }

    @Override
    public DefaultTableModel getModel() {
        return model;
    }

    public DefaultTableCellRenderer getCellRender() {
        return cellRender;
    }

    public void addTableModelChangeListener(BiConsumer<TableModelEvent, Integer[][]> callback) {
        model.addTableModelListener(e -> {
            System.out.println("addTableModelChangeListener");
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                if (row == -1 || col == -1) {
                    return;
                }
                Integer newValue = Integer.valueOf(model.getValueAt(row, col).toString());
                if (this.data[row][col] != newValue) {
                    this.data[row][col] = newValue;
                    callback.accept(e, data);
                }
            }
        });
    }

    public void loadModel(Integer[][] data) {
        this.data = data;
        var column = new String[data.length];
        model.setDataVector(data, column);
        super.setModel(model);
        super.setTableHeader(null);
        initCellRender();
        calcSize();
    }

    public void calcSize() {
        cellSize = viewPortSize / data.length < minCellSize ? minCellSize : viewPortSize / data.length;
        super.setRowHeight(cellSize);
        for (int i = 0; i < super.getColumnCount(); i++) {
            TableColumn column = super.getColumnModel().getColumn(i);
            column.setPreferredWidth(cellSize);
        }
    }

    public void initCellRender() {
        if (cellRender == null) {
            cellRender = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa ngang
                    setVerticalAlignment(SwingConstants.CENTER);   // Căn giữa dọc
                    setBorder(new LineBorder(Color.BLACK, 1));     // Viền đen bao quanh mỗi ô
                    return cell;
                }
            };
        }
        for (int i = 0; i < this.getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(cellRender);
        }
    }
}
