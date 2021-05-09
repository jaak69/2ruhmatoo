import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private String valitudRiik;

    @FXML
    public ToggleButton paevahind;
    @FXML
    public ToggleButton valikuhind;
    @FXML
    public Button lõpeta;
    @FXML
    public ComboBox riikValik;
    @FXML
    public DatePicker lõpuKuupäev;
    @FXML
    public DatePicker algusKuupäev;
    @FXML
    public Button päringUuteAndmetega;
    @FXML
    public Button SalvestaCsv;
    @FXML
    public TableView tabelElektrihinnad;
    @FXML
    public TableColumn tabelKuupäev;
    @FXML
    public TableColumn tabelKõrgeimHind;
    @FXML
    public TableColumn tabelMadalaimHind;
    @FXML
    public TableColumn tabelKeskmineHind;
    @FXML
    public TextField elektrihindMaxPeriood;
    @FXML
    public TextField elektrihimdMinPeriood;
    @FXML
    public TextField elektrihindKeskminePeriood;

    @FXML
    private Label lblOutput;
    @FXML
    public void sayHello() {
        lblOutput.setText("Hello FXML!");
    }
    @FXML
    public void päevahindKuva(ActionEvent actionEvent) {
    }
    @FXML
    public void valikuhindKuva(ActionEvent actionEvent) {
    }
    @FXML
    public void lõpetaProgramm(ActionEvent actionEvent) {
    }
    @FXML
    public void käivitaPäringUuteAndmetega(ActionEvent actionEvent) {
    }
    @FXML
    public void salvestaCSVFail(ActionEvent actionEvent) {
    }

    public void showPäevahind(String valitudRiik){
        if (valikuhind.isSelected()){
            valikuhind.setSelected(false);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        valitudRiik = (String) riikValik.getValue();

        showPäevahind(valitudRiik);

    }
}