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
    private TableView<ElektriHindPaev> tabelElektrihinnadPeriood;
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
        try {
            showValitudPeriood((String)riikValik.getValue(),(String)valiKuu.getValue(),(String)valiAasta.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        } else if (valikuhind.isSelected()){
            try {
                showValitudPeriood((String)riikValik.getValue(),(String)valiKuu.getValue(),(String)valiAasta.getValue());
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

        tabelElektrihinnad.setVisible(true);

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

    public void showValitudPeriood(String vRiik, String vKuu, String vAasta) throws IOException, ParseException {
        //Kuude nimetused numbriteks

        Map<String,Integer> kuudNumbriteks = new HashMap<>();
        kuudNumbriteks.put("JAANUAR",1);
        kuudNumbriteks.put("VEEBRUAR",2);
        kuudNumbriteks.put("MÄRTS",3);
        kuudNumbriteks.put("APRILL",4);
        kuudNumbriteks.put("MAI",5);
        kuudNumbriteks.put("JUUNI",6);
        kuudNumbriteks.put("JUULI",7);
        kuudNumbriteks.put("AUGUST",8);
        kuudNumbriteks.put("SEPTEMBER",9);
        kuudNumbriteks.put("OKTOOBER",10);
        kuudNumbriteks.put("NOVEMBER",11);
        kuudNumbriteks.put("DETSEMBER",12);


        //seadista vahemiku valik aktiivseks, kui on aktiivne
        if (paevahind.isSelected()) {
            paevahind.setSelected(false);
        }

        //päevavaliku menüünupp aktiivseks
        valikuhind.setSelected(true);

        valiKuu.setVisible(true);
        valiAasta.setVisible(true);

        //deaktiveeri päevahinnatabel ja aktiveeri perioodi tabel
        if (tabelElektrihinnad.isVisible()) {
            tabelElektrihinnad.setVisible(false);
        }

        if (!tabelElektrihinnadPeriood.isVisible()) {
            tabelElektrihinnadPeriood.setVisible(true);
        }

        //Tõmba eleringist hetkest kuni 24H homseni
        PerioodiHind elekterPeriood = new PerioodiHind(valitudRiik.toLowerCase(Locale.ROOT), kuudNumbriteks.get(vKuu),
                Integer.parseInt(vAasta));
        List<ElektriHindPaev> elekter = elekterPeriood.getPerioodiHinnad();

        //Tühjenda tabel
        tabelElektrihinnadPeriood.getItems().clear();
        tabelElektrihinnadPeriood.getColumns().clear();

        //tabeli päise defineerimine
        TableColumn<ElektriHindPaev, String> kuupaevColumn = new TableColumn<>("Kuupäev");
        kuupaevColumn.setCellValueFactory(new PropertyValueFactory<>("aeg"));

        TableColumn<ElektriHindPaev, String> minHindColumn = new TableColumn<>("MinHind");
        minHindColumn.setCellValueFactory(new PropertyValueFactory<>("minHind"));

        TableColumn<ElektriHindPaev, String> maxHindColumn = new TableColumn<>("MaxHind");
        maxHindColumn.setCellValueFactory(new PropertyValueFactory<>("maxHind"));

        TableColumn<ElektriHindPaev, String> keskmineHindColumn = new TableColumn<>("KeskmineHind");
        keskmineHindColumn.setCellValueFactory(new PropertyValueFactory<>("hind"));

        tabelElektrihinnadPeriood.getColumns().add(kuupaevColumn);
        tabelElektrihinnadPeriood.getColumns().add(minHindColumn);
        tabelElektrihinnadPeriood.getColumns().add(maxHindColumn);
        tabelElektrihinnadPeriood.getColumns().add(keskmineHindColumn);

        //Andmete lisamine tabelisse
        for (ElektriHindPaev paev : elekter) {
            tabelElektrihinnadPeriood.getItems().add(paev);
        }

        //Ülemises ringid paika
        elektrihindMaxPeriood.setText(String.valueOf(elekterPeriood.getPerioodiMaksimaalneHind().getHind()));
        elektrihimdMinPeriood.setText(String.valueOf(elekterPeriood.getPerioodiMiniimaalneHind().getHind()));
        elektrihindKeskminePeriood.setText(String.valueOf(elekterPeriood.getPerioodiKeskmineHind()));


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