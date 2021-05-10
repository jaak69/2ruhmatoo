import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private String valitudRiik;

    @FXML
    public ToggleButton paevahind;
    @FXML
    public ToggleButton valikuhind;
    @FXML
    public Button lopeta;
    @FXML
    public ComboBox riikValik;
    @FXML
    public DatePicker lõpuKuupäev;
    @FXML
    public DatePicker algusKuupäev;
    @FXML
    public Button paringUuteAndmetega;
    @FXML
    public Button SalvestaCsv;
    @FXML
    public TableView<Elektrihind> tabelElektrihinnad;
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
    public void lopetaProgramm(ActionEvent actionEvent) {
    }
    @FXML
    public void kaivitaParingUuteAndmetega(ActionEvent actionEvent) {
    }
    @FXML
    public void salvestaCSVFail(ActionEvent actionEvent) {
    }

    public void showPäevahind(String valitudRiik){
        if (valikuhind.isSelected()){
            valikuhind.setSelected(false);
        }
        paevahind.setSelected(true);

        List<Elektrihind> elektrihinnadTest = new ArrayList<>();

        elektrihinnadTest.add(new Elektrihind("2021-05-01 10:00",0.26));
        elektrihinnadTest.add(new Elektrihind("2021-05-01 11:00",0.28));

        tabelElektrihinnad.getItems().clear();
        tabelElektrihinnad.getColumns().clear();

        //tabeli päise defineerimine
        TableColumn<Elektrihind, String> kellaAegColumn = new TableColumn<>("Kellaeg");
        kellaAegColumn.setCellValueFactory(new PropertyValueFactory<>("aeg"));

        TableColumn<Elektrihind, String> kwhHind = new TableColumn<>("kWh hind");
        kwhHind.setCellValueFactory(new PropertyValueFactory<>("hind"));

        tabelElektrihinnad.getColumns().add(kellaAegColumn);
        tabelElektrihinnad.getColumns().add(kwhHind);

        tabelElektrihinnad.getItems().add(new Elektrihind("2021-05-01 10:00", 0.26));
        tabelElektrihinnad.getItems().add(new Elektrihind("2021-05-01 11:00", 0.26));


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        valitudRiik = (String) riikValik.getValue();

        showPäevahind(valitudRiik);

    }
}