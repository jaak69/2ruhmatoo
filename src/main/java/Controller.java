import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    public Button jsam;
    @FXML
    private Label lblOutput;

    public void sayHello() {
        lblOutput.setText("Hello FXML!");
    }
}