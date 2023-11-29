package com.github.lark.markdown.controller;

import com.github.lark.markdown.Constants;
import com.github.lark.markdown.utils.FxUtil;
import com.github.lark.markdown.utils.PropertiesUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: xy-code
 * @Description: 设置页面的Controller，对应的ui：set.fxml
 * @Date: 2023-10-29 16:27
 **/
public class SetController {

    @FXML
    private TextField waitTimeTextView;

    @FXML
    private Button setSaveButton;

    @FXML
    private Button selectFileButton;

    @FXML
    private TextField rollIntervalTextView;

    @FXML
    private Label fileLabel;

    private Stage setStage;

    public void initialize(Stage setStage) {
        this.setStage = setStage;
        try {
            Properties configProperties = PropertiesUtil.loadProperties(FxUtil.getResourcesPath(Constants.CONTENT_FILE_NAME));
            String browserRollIntervalMill = configProperties.getProperty(Constants.BROWSER_ROLL_INTERVAL_MILL_KEY);
            String browserWaiteTimeSecond = configProperties.getProperty(Constants.BROWSER_WAITE_TIME_SECOND_KEY);
            String markdownSavePath = configProperties.getProperty(Constants.MARKDOWN_SAVE_PATH_KEY);
            waitTimeTextView.setText(browserWaiteTimeSecond);
            rollIntervalTextView.setText(browserRollIntervalMill);
            fileLabel.setText(markdownSavePath);
        } catch (IOException e) {
            throw new IllegalStateException("failed to load config properties");
        }
    }

    @FXML
    private void selectFileButtonClicked() {
        // 创建目录选择器
        DirectoryChooser directoryChooser = new DirectoryChooser();
        // 设置初始目录
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // 显示目录选择器对话框
        File selectedDirectory = directoryChooser.showDialog(setStage);
        if (selectedDirectory != null && selectedDirectory.exists()) {
            String absolutePath = selectedDirectory.getAbsolutePath();
            fileLabel.setText(absolutePath);
            Tooltip contentTip = new Tooltip(absolutePath);
            Tooltip.install(fileLabel, contentTip);
        }
    }

    @FXML
    private void setSaveButtonClicked() throws IOException {
        String browserWaiteTimeSecond = waitTimeTextView.getText();
        if(!checkBrowserWaiteTime(browserWaiteTimeSecond)) {
            FxUtil.alertInfo(setStage,"提示","缓冲时间必须是10-100的整数");
            return;
        }
        String browserRollIntervalMill = rollIntervalTextView.getText();
        if(!checkBrowserRollInterval(browserRollIntervalMill)) {
            FxUtil.alertInfo(setStage,"提示","滚动间隔必须是20-200的整数");
            return;
        }
        String markdownSavePath = fileLabel.getText();
        if(markdownSavePath == null || markdownSavePath.length() == 0) {
            FxUtil.alertInfo(setStage,"提示","请选择保存目录");
            return;
        }
        Properties props = new Properties();
        props.setProperty(Constants.BROWSER_WAITE_TIME_SECOND_KEY,browserWaiteTimeSecond);
        props.setProperty(Constants.BROWSER_ROLL_INTERVAL_MILL_KEY,browserRollIntervalMill);
        props.setProperty(Constants.MARKDOWN_SAVE_PATH_KEY,markdownSavePath);
        PropertiesUtil.storeProperties(props,FxUtil.getResourcesPath(Constants.CONTENT_FILE_NAME));
        setStage.close();
    }

    private boolean checkBrowserWaiteTime(String browserWaiteTimeSecond) {
        try {
            Integer value = Integer.parseInt(browserWaiteTimeSecond);
            final int browserWaiteTimeMin = 10;
            final int browserWaiteTimeMax = 100;
            if(value < browserWaiteTimeMin || value > browserWaiteTimeMax) {
                return false;
            }
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean checkBrowserRollInterval(String browserRollIntervalMill) {
        try {
            Integer value = Integer.parseInt(browserRollIntervalMill);
            final int browserRollIntervalMin = 20;
            final int browserRollIntervalMax = 200;
            if(value < browserRollIntervalMin || value > browserRollIntervalMax) {
                return false;
            }
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}


