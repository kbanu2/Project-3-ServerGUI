import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Controller implements Initializable{
    @FXML
    public BorderPane initRoot;
    @FXML
    public BorderPane serverRoot;
    @FXML
    public ListView<String> eventLogs;
    @FXML
    public TextField portTextField;
    @FXML
    public Button initButton;
    @FXML
    public Label warningLabel;
    public int port;
    public static Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void createServer() throws IOException {
        if (!getPort())
            return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("serverFXML.fxml"));
        Parent root = loader.load();
        server = new Server(loader.getController(), port);

        Scene scene = new Scene(root, 750, 750);
        Stage stage = (Stage) initRoot.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Server Logs");
    }

    public void accept(String event){
        Platform.runLater(()->eventLogs.getItems().add(event));
    }

    private boolean getPort(){
        if (portTextField.getText().isEmpty()) {
            warningLabel.setVisible(true);
            return false;
        }
        port = Integer.parseInt(portTextField.getText());
        return true;
    }
}
