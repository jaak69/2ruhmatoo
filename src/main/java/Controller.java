import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label lblOutput;

    public void sayHello(ActionEvent actionEvent) {
        lblOutput.setText("Hello FXML!");
    }
}