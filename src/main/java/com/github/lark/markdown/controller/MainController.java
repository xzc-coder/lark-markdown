package com.github.lark.markdown.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.MarkdownHandler;
import com.github.lark.markdown.enums.MarkdownItemStatus;
import com.github.lark.markdown.model.MarkdownUrlItem;
import com.github.lark.markdown.utils.FxUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;


/**
 * @Author: xy-code
 * @Description: 主页面的Controller，对应的ui：main.fxml
 * @Date: 2023-10-29 16:27
 **/
public class MainController {

    private static final Log log = Log.get();
    @FXML
    private HBox closeHbox;
    @FXML
    private HBox minimizeHbox;
    @FXML
    private HBox setHbox;
    @FXML
    private TextField urlTextField;
    @FXML
    private TableView<MarkdownUrlItem> markdownUrlTableView;
    @FXML
    private TableColumn<MarkdownUrlItem, Number> tabColSerialNumber;
    @FXML
    private TableColumn<MarkdownUrlItem, String> tabColUrl;
    @FXML
    private TableColumn<MarkdownUrlItem, String> tabColTitle;
    @FXML
    private TableColumn<MarkdownUrlItem, String> tabColStatus;
    @FXML
    private TableColumn<MarkdownUrlItem, String> tabColProgress;
    //    @FXML
//    private TableColumn<MarkdownUrlItem, Number> tabColOperate;
    private Double oldScreenX;
    private Double oldScreenY;
    private Stage stage;
    /**
     * markdown处理器
     */
    private MarkdownHandler markdownHandler = new MarkdownHandler(this);

    /**
     * 初始化
     * @param stage
     */
    public void initialize(Stage stage) {
        this.stage = stage;
        Tooltip closeTip = new Tooltip("关闭");
        Tooltip.install(closeHbox, closeTip);
        Tooltip minimizeTip = new Tooltip("最小化");
        Tooltip.install(minimizeHbox, minimizeTip);
        Tooltip setTip = new Tooltip("设置");
        Tooltip.install(setHbox, setTip);
        initTable();
    }


    @FXML
    private void paneMoveMouseDragged(MouseEvent mouseEvent) {
        if (oldScreenX != null && oldScreenY != null) {
            stage.setX(stage.getX() + (mouseEvent.getScreenX() - oldScreenX));
            stage.setY(stage.getY() + (mouseEvent.getScreenY() - oldScreenY));
        }
        oldScreenX = mouseEvent.getScreenX();
        oldScreenY = mouseEvent.getScreenY();
    }

    @FXML
    private void paneMoveMouseReleased(MouseEvent mouseEvent) {
        oldScreenX = null;
        oldScreenY = null;
    }


    @FXML
    private void closeBoxMouseEntered() {
        closeHbox.getStyleClass().remove("white-bg");
        closeHbox.getStyleClass().add("red-bg");
    }

    @FXML
    private void closeBoxMouseExited() {
        closeHbox.getStyleClass().remove("red-bg");
        closeHbox.getStyleClass().add("white-bg");
    }

    @FXML
    private void closeBoxMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            FxUtil.alertConfirmDialog(stage, "提示", "确认退出?", Platform::exit, null);
        }
    }

    @FXML
    private void minimizeBoxMouseEntered() {
        minimizeHbox.getStyleClass().remove("white-bg");
        minimizeHbox.getStyleClass().add("grey-bg");
    }

    @FXML
    private void minimizeBoxMouseExited() {
        minimizeHbox.getStyleClass().remove("grey-bg");
        minimizeHbox.getStyleClass().add("white-bg");
    }

    @FXML
    private void minimizeBoxMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            stage.setIconified(true);
        }
    }

    @FXML
    private void setBoxMouseEntered() {
        setHbox.getStyleClass().remove("white-bg");
        setHbox.getStyleClass().add("grey-bg");
    }

    @FXML
    private void setBoxMouseExited() {
        setHbox.getStyleClass().remove("grey-bg");
        setHbox.getStyleClass().add("white-bg");
    }

    @FXML
    private void setBoxMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            URL resource = FxUtil.getResourcesUrl(Constants.SET_UI_FILE_NAME);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(resource);
            Pane root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage setStage = new Stage();
            SetController setController = fxmlLoader.getController();
            setController.initialize(setStage);
            setStage.setScene(scene);
            setStage.setTitle("设置");
            setStage.resizableProperty().setValue(Boolean.FALSE);
            setStage.setResizable(false);
            setStage.initModality(Modality.APPLICATION_MODAL);
            setStage.getIcons().add(new Image(Constants.SET_ICON_FILE_NAME));
            setStage.show();
            setStage.setX(stage.getX() + (stage.getWidth() - setStage.getWidth()) / 2);
            setStage.setY(stage.getY() + (stage.getHeight() - setStage.getHeight()) / 2);
        }
    }


    @FXML
    private void addButtonMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            tableAddUrl();
        }
    }

    @FXML
    private void urlTextFieldKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER) {
            tableAddUrl();
        }
    }


    private void tableAddUrl() {
        String url = urlTextField.getText();
        if (StrUtil.isNotBlank(url)) {
            url = StrUtil.trim(url);
            final String regex = "^http[s]?://(?=.*yuque).*$";
            if (Pattern.compile(regex).matcher(url).matches()) {
                urlTextField.setText(null);
                MarkdownUrlItem markdownUrlItem = new MarkdownUrlItem();
                markdownUrlItem.setSerialNumber(markdownHandler.produceSerialNumber());
                markdownUrlItem.setUrl(url);
                markdownUrlItem.setProgress(0);
                markdownUrlItem.setStatus(MarkdownItemStatus.WAIT.getStatus());
                markdownUrlTableView.getItems().add(markdownUrlItem);
                log.info("添加一个语雀文章url解析：{}",markdownUrlItem.getUrl());
                markdownHandler.addTask(markdownUrlItem);
            } else {
                FxUtil.alertInfo(stage, "提示", "请填写正确的语雀文章url");
            }
        }
    }

    private Callback<TableColumn<MarkdownUrlItem, String>, TableCell<MarkdownUrlItem, String>> tableColumnContentTipCallback() {

        return param -> {
            TableCell<MarkdownUrlItem, String> tableCell = new TableCell<MarkdownUrlItem, String>() {

                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setText(value);
                    }
                }
            };
            tableCell.setOnMouseEntered(event -> {
                String text = tableCell.getText();
                if (StrUtil.isNotBlank(text)) {
                    Tooltip contentTip = new Tooltip(text);
                    Tooltip.install(tableCell, contentTip);
                }
            });
            return tableCell;
        };
    }

    private Callback<TableColumn<MarkdownUrlItem, Number>, TableCell<MarkdownUrlItem, Number>> tableColumnOperateCallback() {

        return param -> {
            TableCell<MarkdownUrlItem, Number> tableCell = new TableCell<MarkdownUrlItem, Number>() {

                @Override
                protected void updateItem(Number value, boolean empty) {
                    super.updateItem(value, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button cancelButton = new Button("取消");
                        cancelButton.setPrefWidth(50);
                        cancelButton.setOpacity(0.8);
                        cancelButton.setOnAction(event -> {
                            MarkdownUrlItem markdownUrlItem = getTableView().getItems().get(getIndex());
                            //todo 从数据源中删除
                        });
                        setText(null);
                        setGraphic(cancelButton);
                    }
                }
            };
            tableCell.setOnMouseEntered(event -> {
                String text = tableCell.getText();
                if (StrUtil.isNotBlank(text)) {
                    Tooltip contentTip = new Tooltip(text);
                    Tooltip.install(tableCell, contentTip);
                }
            });
            return tableCell;
        };
    }

    private void initTable() {
        tabColSerialNumber.setCellValueFactory((item -> new SimpleIntegerProperty(item.getValue().getSerialNumber())));
        tabColUrl.setCellValueFactory((item -> new SimpleStringProperty(item.getValue().getUrl())));
        tabColUrl.setCellFactory(tableColumnContentTipCallback());

        tabColTitle.setCellValueFactory((item -> new SimpleStringProperty(item.getValue().getTitle())));
        tabColTitle.setCellFactory(tableColumnContentTipCallback());

        tabColStatus.setCellValueFactory((item -> {
            Integer status = item.getValue().getStatus();
            MarkdownItemStatus markdownItemStatus = MarkdownItemStatus.findStatus(status);
            String content;
            if (markdownItemStatus == MarkdownItemStatus.ERROR) {
                content = "错误:" + item.getValue().getErrorMessage();
            } else {
                content = markdownItemStatus.getStatusDesc();
            }
            return new SimpleStringProperty(content);
        }));
        tabColStatus.setCellFactory(tableColumnContentTipCallback());
        tabColProgress.setCellValueFactory((item -> new SimpleStringProperty(item.getValue().getProgress() + "%")));
//        tabColOperate.setCellValueFactory((item -> new SimpleIntegerProperty(item.getValue().getSerialNumber())));
//        tabColOperate.setCellFactory(tableColumnOperateCallback());
        FxUtil.installCopyPasteHandler(markdownUrlTableView);
    }


    public void tableRefresh() {
        markdownUrlTableView.refresh();
    }



}
