package com.github.lark.markdown.utils;

import cn.hutool.log.Log;
import com.github.lark.markdown.Constants;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * @Author: xy-code
 * @Description: JavaFx相关的工具类
 * @Date: 2023-10-29 16:27
 **/
public class FxUtil {

    private FxUtil() {
    }


    public static void loadCss(Scene scene) throws MalformedURLException {
        String cssResource = Constants.FILE_PREFIX + FxUtil.getResourcesPath(Constants.CSS_FILE_NAME);
        scene.getStylesheets().add(cssResource);
    }


    public static Alert alertInfo(Stage stage, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.initOwner(stage);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return alert;
    }

    /**
     * 弹出一个通用的确定对话框
     *
     * @param stage   所在窗口
     * @param title   对话框标题
     * @param message 对话框信息
     */
    public static Alert alertConfirmDialog(Stage stage, String title, String message, Runnable confirmCallback, Runnable cancelCallback) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            if (confirmCallback != null) {
                confirmCallback.run();
            }
        } else {
            if (cancelCallback != null) {
                cancelCallback.run();
            }
        }
        return alert;
    }


    /**
     * Install the keyboard handler:
     * + CTRL + C = copy to clipboard
     * + CTRL + V = paste to clipboard
     *
     * @param table
     */
    public static void installCopyPasteHandler(TableView<?> table) {

        // install copy/paste keyboard handler
        table.setOnKeyPressed(new TableKeyEventHandler());

    }

    /**
     * Copy/Paste keyboard event handler.
     * The handler uses the keyEvent's source for the clipboard data. The source must be of type TableView.
     */
    public static class TableKeyEventHandler implements EventHandler<KeyEvent> {

        KeyCodeCombination copyKeyCodeCompination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

        @Override
        public void handle(final KeyEvent keyEvent) {

            if (copyKeyCodeCompination.match(keyEvent)) {

                if (keyEvent.getSource() instanceof TableView) {

                    // copy to clipboard
                    copySelectionToClipboard((TableView<?>) keyEvent.getSource());

                    // event is handled, consume it
                    keyEvent.consume();

                }

            }

        }

    }

    /**
     * Get table selection and copy it to the clipboard.
     *
     * @param table
     */
    public static void copySelectionToClipboard(TableView<?> table) {

        StringBuilder clipboardString = new StringBuilder();

        ObservableList<TablePosition> positionList = table.getSelectionModel().getSelectedCells();

        int prevRow = -1;

        for (TablePosition position : positionList) {

            int row = position.getRow();
            int col = position.getColumn();

            Object cell = (Object) table.getColumns().get(col).getCellData(row);

            // null-check: provide empty string for nulls
            if (cell == null) {
                cell = "";
            }

            // determine whether we advance in a row (tab) or a column
            // (newline).
            if (prevRow == row) {

                clipboardString.append('\t');

            } else if (prevRow != -1) {

                clipboardString.append('\n');

            }

            // create string from cell
            String text = cell.toString();

            // add new item to clipboard
            clipboardString.append(text);

            // remember previous
            prevRow = row;
        }

        // create clipboard content
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString.toString());

        // set clipboard content
        Clipboard.getSystemClipboard().setContent(clipboardContent);

    }

    public static String getResourcesPath(String resource) {
        String path = FxUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int lastIndexOf = path.lastIndexOf("/");
        return path.substring(0, lastIndexOf + 1) + resource;
    }


    public static URL getResourcesUrl(String resource) throws MalformedURLException {
        return new File(getResourcesPath(resource)).toURL();
    }
}
