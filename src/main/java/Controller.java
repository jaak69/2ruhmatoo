import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.*;

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
    public DatePicker lopuKuupaev;
    @FXML
    public DatePicker algusKuupaev;
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kas soovid töö lõpetada");
        alert.setHeaderText("");
        alert.setContentText("Lõpetamiseks vajuta OK!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
        } else {
         alert.close();
        }

    }
    @FXML
    public void kaivitaParingUuteAndmetega(ActionEvent actionEvent) {
    }
    @FXML
    public void salvestaCSVFail(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);


    }



    public void showPäevahind(String valitudRiik){
        if (valikuhind.isSelected()){
            valikuhind.setSelected(false);
        }

        algusKuupaev.setVisible(false);
        lopuKuupaev.setVisible(false);

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

        for (int i = 0; i < 32; i++) {

            Random rd = new Random(); // creating Random object
            tabelElektrihinnad.getItems().add(new Elektrihind("2021-05-01 10:00" + " " + i, rd.nextDouble()));

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        valitudRiik = (String) riikValik.getValue();

        showPäevahind(valitudRiik);

    }
}