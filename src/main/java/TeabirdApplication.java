import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.cli.*;

import java.io.IOException;

public class TeabirdApplication extends Application
{
    private Stage primaryStage;

    public static void main(String[] args) throws ParseException
    {
        Options cmdOptions = new Options();

        cmdOptions.addOption(
                "ng",
                "no-gui",
                false,
                "Disable GUI (may be used for headless servers)");

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(cmdOptions, args);

        if(!commandLine.hasOption("no-gui")) {
            Application.launch(TeabirdApplication.class, args);
        } else {
            System.out.println("Headless mode started, GUI will not show up.");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Teabird");
        initRootLayout();
    }

    private void initRootLayout()
    {
        try {

            // Load layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TeabirdApplication.class.getResource("fxml/Home.fxml"));

            System.out.println("Main fxml path: " + TeabirdApplication.class.getResource("fxml/Home.fxml"));

            // Show the scene from the layout
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
