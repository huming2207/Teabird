package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import twitter4j.FilterQuery;
import worker.SettingLoader;
import worker.TeabirdApplication;
import worker.Worker;

import java.io.File;
import java.util.Arrays;
import java.util.logging.*;

public class HomeController
{
    private TeabirdApplication teabirdApplication;

    @FXML
    private TextField consumerTokenTextfield;

    @FXML
    private TextField consumerSecretTextfield;

    @FXML
    private TextField accessTokenTextfield;

    @FXML
    private TextField accessSecretTextfield;

    @FXML
    private TextArea keywordTextArea;

    @FXML
    private TextArea userIdTextArea;

    @FXML
    private CheckBox strictFilterCheckbox;

    @FXML
    private TextField languageTextField;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button apiSettingSaveButton;

    @FXML
    private ToggleButton startStopToggleButton;

    @FXML
    private Button setOutputButton;

    @FXML
    private Button saveSettingsButton;

    @FXML
    private TextField outputPathTextField;

    private Task<String> workerTask = null;

    private Thread workerThread = null;

    private Logger logger;

    public HomeController()
    {

    }

    @FXML
    private void initialize()
    {
        // Register the worker task when app starts
        setWorkerTask();

        // Initialize logger
        logger = Logger.getLogger("com.jacksonhu.teabird");
        logger.setUseParentHandlers(false);

        Formatter formatter = new SimpleFormatter();
        logger.addHandler(new Handler()
        {
            @Override
            public void publish(LogRecord record)
            {
                Platform.runLater(() -> outputTextArea.appendText(formatter.format(record)));
            }

            @Override
            public void flush()
            {

            }

            @Override
            public void close() throws SecurityException
            {

            }
        });

        // Load settings
        languageTextField.setText(SettingLoader.getLanguages());
        keywordTextArea.setText(SettingLoader.getKeywords());
        outputPathTextField.setText(SettingLoader.getOptPaths());
        userIdTextArea.setText(SettingLoader.getUsers());
        strictFilterCheckbox.setSelected(SettingLoader.getStrictFilterMode());
        consumerTokenTextfield.setText(SettingLoader.getConsumerToken());
        consumerSecretTextfield.setText(SettingLoader.getConsumerTokenSecret());
        accessTokenTextfield.setText(SettingLoader.getAccessTokenStr());
        accessSecretTextfield.setText(SettingLoader.getAccessSecretStr());
    }

    @FXML
    private void handleApiSettingButton()
    {
        SettingLoader.writeApiSettings(consumerTokenTextfield.getText(),
                consumerSecretTextfield.getText(),
                accessTokenTextfield.getText(),
                accessSecretTextfield.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings saved");
        alert.setHeaderText(null);
        alert.setContentText("Settings have been saved!");
        alert.show();
    }

    @FXML
    private void handleStartStopToggleButton()
    {
        // If the button is toggled, then we start a new thread.
        // Otherwise, we stop that thread (may not work).
        if(startStopToggleButton.isSelected()) {
            outputTextArea.textProperty().bind(workerTask.messageProperty());
            workerThread = new Thread(workerTask);
            workerThread.start();
        } else {
            workerThread.interrupt(); // TODO: add a "signal" to the thread instead.
        }
    }

    @FXML
    private void handleSetOutputButton()
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pick a folder for output...");

        File file = directoryChooser.showDialog(new Stage());
        outputPathTextField.setText(file.getPath());
    }

    @FXML
    private void handleCrawlerSettingButton()
    {
        SettingLoader.writeCrawlerSettings(keywordTextArea.getText(),
                userIdTextArea.getText(),
                strictFilterCheckbox.isSelected(),
                languageTextField.getText(),
                outputPathTextField.getText());
    }

    private void setWorkerTask()
    {
        this.workerTask = new Task<String>()
        {
            @Override
            protected String call() throws Exception
            {
                String splitRegex = "[,，]"; // ...just in case someone uses Chinese (CJK?) comma char here
                FilterQuery filterQuery = new FilterQuery();
                filterQuery.filterLevel(strictFilterCheckbox.isSelected() ? "medium" : "low");
                filterQuery.track(keywordTextArea.getText().split(splitRegex));
                filterQuery.language(languageTextField.getText().split(splitRegex));

                if(!userIdTextArea.getText().isEmpty())
                {
                    // Convert the text from the text area to long integer array
                    long userIdArray[] = Arrays.stream(
                            userIdTextArea.getText().split(splitRegex)) // split text area's string to string array
                            .mapToLong(Long::parseLong) // map to integer
                            .toArray(); // Convert to array

                    filterQuery.follow(userIdArray);
                }

                // Worker initialization
                Worker worker = new Worker(filterQuery, outputPathTextField.getText(), logger);
                worker.login(SettingLoader.getAccessToken(),
                        SettingLoader.getConsumerToken(),
                        SettingLoader.getConsumerTokenSecret());
                worker.createStream();

                return "";
            }
        };
    }

    public void setMainApp(TeabirdApplication teabirdApplication)
    {
        this.teabirdApplication = teabirdApplication;
    }

    public void appendDebugText(String text)
    {
        Platform.runLater(() -> outputTextArea.appendText(text));
    }

    public Logger getLogger()
    {
        return logger;
    }


}
