import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private String valitudRiik;

    @FXML
    private ToggleButton paevahind;
    @FXML
    private ToggleButton valikuhind;
    @FXML
    private Button lopeta;
    @FXML
    private ComboBox riikValik;
    @FXML
    private DatePicker lopuKuupaev;
    @FXML
    private DatePicker algusKuupaev;
    @FXML
    private Button paringUuteAndmetega;
    @FXML
    private Button SalvestaCsv;
    @FXML
    private TableView<Elektrihind> tabelElektrihinnad;
    @FXML
    private TableView tabelElektrihinnadPeriood;
    @FXML
    private TableColumn tabelKuupäev;
    @FXML
    private TableColumn tabelKõrgeimHind;
    @FXML
    private TableColumn tabelMadalaimHind;
    @FXML
    private TableColumn tabelKeskmineHind;
    @FXML
    private TextField elektrihindMaxPeriood;
    @FXML
    private TextField elektrihimdMinPeriood;
    @FXML
    private TextField elektrihindKeskminePeriood;
    @FXML
    private ComboBox valiKuu;
    @FXML
    private ComboBox valiAasta;

    @FXML
    private Label lblOutput;

    @FXML
    public void päevahindKuva(ActionEvent actionEvent) {
        try {
            showPäevahind((String)riikValik.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void valikuhindKuva(ActionEvent actionEvent) {
        showValitudPeriood((String)riikValik.getValue(),(String)valiKuu.getValue(),(String)valiAasta.getValue());
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

        if (paevahind.isSelected()){
            try {
                showPäevahind((String) riikValik.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    public void salvestaCSVFail(ActionEvent actionEvent)  {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(tabelElektrihinnad.getScene().getWindow());

        try {
            writeCSV(tabelElektrihinnad,file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showPäevahind(String valitudRiik) throws IOException, ParseException {
        //seadista vahemiku valik mitteaktiivseks, kui on aktiivne
        if (valikuhind.isSelected()){
            valikuhind.setSelected(false);
        }

        if (tabelElektrihinnadPeriood.isVisible()){
            tabelElektrihinnadPeriood.setVisible(false);
        }

        //päevavaliku menüünupp aktiivseks
        paevahind.setSelected(true);

        //peida kuupäeva valikud
        valiKuu.setVisible(false);
        valiAasta.setVisible(false);

        //Tõmba eleringist hetkest kuni 24H homseni
        Paevahind elektriHinnad = new Paevahind(valitudRiik.toLowerCase(Locale.ROOT));
        List<Elektrihind> elekter24h = elektriHinnad.getPäevaHinnad();

        //Tühjenda tabel
        tabelElektrihinnad.getItems().clear();
        tabelElektrihinnad.getColumns().clear();

        //tabeli päise defineerimine
        TableColumn<Elektrihind, String> kellaAegColumn = new TableColumn<>("Kellaeg");
        kellaAegColumn.setCellValueFactory(new PropertyValueFactory<>("aeg"));

        TableColumn<Elektrihind, String> kwhHind = new TableColumn<>("kWh hind");
        kwhHind.setCellValueFactory(new PropertyValueFactory<>("hind"));

        tabelElektrihinnad.getColumns().add(kellaAegColumn);
        tabelElektrihinnad.getColumns().add(kwhHind);

        //Andmete lisamine tabelisse
        for (Elektrihind tund : elekter24h) {
            tabelElektrihinnad.getItems().add(tund);
        }

        //Ülemises ringid paika
        elektrihindMaxPeriood.setText(String.valueOf(elektriHinnad.maksimaalneHind().getHind()));
        elektrihimdMinPeriood.setText(String.valueOf(elektriHinnad.minimaalneHind().getHind()));
        elektrihindKeskminePeriood.setText(String.valueOf(elektriHinnad.keskmineHind()));
    }

    public void showValitudPeriood(String vRiik, String vKuu, String vAasta){

        //seadista vahemiku valik aktiivseks, kui on aktiivne
        if (paevahind.isSelected()){
            paevahind.setSelected(false);
        }

        //päevavaliku menüünupp aktiivseks
        valikuhind.setSelected(true);

        valiKuu.setVisible(true);
        valiAasta.setVisible(true);

        //Tõmba eleringist hetkest kuni 24H homseni
         PerioodiHind elektriHinnad = new PerioodiHind(valitudRiik.toLowerCase(Locale.ROOT),Integer.parseInt(vKuu),
                 Integer.parseInt(vAasta));
        try {
            List<ElektriHindPaev> elekter24h = elektriHinnad.getPerioodiHinnad();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Tühjenda tabel
        tabelElektrihinnad.getItems().clear();
        tabelElektrihinnad.getColumns().clear();

        //tabeli päise defineerimine
        TableColumn<ElektriHindPaev, String> kuupaevColumn = new TableColumn<>("Kuupäev");
        kuupaevColumn.setCellValueFactory(new PropertyValueFactory<>("aeg"));

        TableColumn<ElektriHindPaev, String> minHindColumn = new TableColumn<>("MinHind");
        minHindColumn.setCellValueFactory(new PropertyValueFactory<>("minHind"));

        TableColumn<ElektriHindPaev, String> maxHindColumn = new TableColumn<>("MinHind");
        maxHindColumn.setCellValueFactory(new PropertyValueFactory<>("maxHind"));

        TableColumn<ElektriHindPaev, String> keskmineHindColumn = new TableColumn<>("MinHind");
        keskmineHindColumn.setCellValueFactory(new PropertyValueFactory<>("hind"));

//        tabelElektrihinnad.getColumns().add(kuupaevColumn);
//        tabelElektrihinnad.getColumns().add(minHindColumn);
//        tabelElektrihinnad.getColumns().add(minHindColumn);


    }

    public void writeCSV(TableView<Elektrihind> tabelElektrihinnad, File file) throws Exception {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            ObservableList<Elektrihind> cells = tabelElektrihinnad.getItems();

            for (Elektrihind hind : cells) {
                writer.write(hind.toString());
            }

            Alert salvestatud = new Alert(Alert.AlertType.INFORMATION);
            salvestatud.setHeaderText("Fail salvestatud!");
            salvestatud.show();

        } catch (Exception ex) {
            Alert salvestatud = new Alert(Alert.AlertType.WARNING);
            salvestatud.setHeaderText("Faili salvestamine ebaõnnestus!");
            salvestatud.show();
        }
        finally {
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        valiKuu.getSelectionModel().select(Month.from(LocalDate.now()).getDisplayName(TextStyle.FULL_STANDALONE,
                new Locale("et","EE")).toUpperCase());

        valitudRiik = (String) riikValik.getValue();

        try {
            showPäevahind(valitudRiik);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}