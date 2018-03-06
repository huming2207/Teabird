package controller;

import helper.TeabirdLogFormatter;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import twitter4j.FilterQuery;
import twitter4j.auth.AccessToken;
import worker.TeabirdApplication;
import worker.Worker;

import java.io.File;
import java.util.Arrays;
import java.util.logging.*;
import java.util.prefs.Preferences;

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

    private Preferences prefs;

    private Worker worker;

    public HomeController()
    {
        prefs = Preferences.userRoot().node("Teabird");
    }

    @FXML
    private void initialize()
    {
        // Initialize logger
        logger = Logger.getLogger("com.jacksonhu.teabird");
        logger.setUseParentHandlers(false);


        logger.addHandler(new Handler()
        {
            @Override
            public void publish(LogRecord record)
            {
                Formatter formatter = new TeabirdLogFormatter();
                String optString = formatter.format(record);
                Platform.runLater(() -> outputTextArea.appendText(optString));
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
        languageTextField.setText(prefs.get("langs", ""));
        keywordTextArea.setText(prefs.get("keywords",""));
        outputPathTextField.setText(prefs.get("optPath", ""));
        userIdTextArea.setText(prefs.get("userIds", ""));
        strictFilterCheckbox.setSelected(prefs.getBoolean("strictFilter", false));
        consumerTokenTextfield.setText(prefs.get("consumerToken", ""));
        consumerSecretTextfield.setText(prefs.get("consumerSecret", ""));
        accessTokenTextfield.setText(prefs.get("accessToken", ""));
        accessSecretTextfield.setText(prefs.get("accessSecret", ""));
    }

    @FXML
    private void handleApiSettingButton()
    {
        prefs.put("consumerToken", consumerTokenTextfield.getText());
        prefs.put("consumerSecret", consumerSecretTextfield.getText());
        prefs.put("accessToken", accessTokenTextfield.getText());
        prefs.put("accessSecret", accessSecretTextfield.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("API settings saved");
        alert.setHeaderText(null);
        alert.setContentText("Settings have been saved!");
        alert.show();
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
    private void handleStartStopToggleButton()
    {
        // If the button is toggled, then we start a new thread.
        // Otherwise, we stop that thread (may not work).
        if(startStopToggleButton.isSelected()) {
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
            worker = new Worker(filterQuery, outputPathTextField.getText(), logger);
            worker.login(prefs.get("consumerToken", ""),
                    prefs.get("consumerSecret", ""),
                    prefs.get("accessToken", ""),
                    prefs.get("accessSecret", ""));
            worker.createStream();
            startStopToggleButton.setText("Press again to stop...");

        } else {
            worker.killStream();
            startStopToggleButton.setText("Start");
        }
    }



    @FXML
    private void handleCrawlerSettingButton()
    {
        prefs.put("langs", languageTextField.getText());
        prefs.put("keywords", keywordTextArea.getText());
        prefs.put("optPath", outputPathTextField.getText());
        prefs.putBoolean("strictFilter", strictFilterCheckbox.isSelected());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Crawler settings saved");
        alert.setHeaderText(null);
        alert.setContentText("Settings have been saved!");
        alert.show();
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
