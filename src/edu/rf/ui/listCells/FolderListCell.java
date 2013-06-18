package edu.rf.ui.listCells;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 *
 */
public class FolderListCell extends ListCell<String> {

    HBox cell = new HBox();
    Button showFeedsButton = new Button("+");
    Label folderName = new Label();
    String lastItem;

    public FolderListCell() {
        super();
        cell.getChildren().addAll(showFeedsButton, folderName);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            folderName.setText(item != null ? item : "<null>");
            setGraphic(cell);
        }
    }


}
