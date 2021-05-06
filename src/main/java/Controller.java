import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML
    public Button paevahind;
    public Button valikuhind;
    public Button lõpeta;
    public ComboBox riikValik;
    public DatePicker lõpuKuupäev;
    public DatePicker algusKuupäev;
    public Button päringUuteAndmetega;
    public Button SalvestaCsv;
    public TableColumn tabelKuupäev;
    public TableColumn tabelKõrgeimHind;
    public TableColumn tabelMadalaimHind;
    public TableColumn tabelKeskmineHind;
    public TextField elektrihindMaxPeriood;
    public TextField elektrihimdMinPeriood;
    public TextField elektrihindKeskminePeriood;
    @FXML
    private Label lblOutput;

    public void sayHello() {
        lblOutput.setText("Hello FXML!");
    }

    public void päevahindKuva(ActionEvent actionEvent) {
    }

    public void valikuhindKuva(ActionEvent actionEvent) {
    }

    public void lõpetaProgramm(ActionEvent actionEvent) {
    }

    public void käivitaPäringUuteAndmetega(ActionEvent actionEvent) {
    }

    public void salvestaCSVFail(ActionEvent actionEvent) {
    }
}