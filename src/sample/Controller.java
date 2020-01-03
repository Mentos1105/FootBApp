package sample;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    public Connection mainConnection = null;
    @FXML
    private TableView tablePilkarze;
    @FXML
    private TableView tableKluby;
    @FXML
    private TableView tableGole;
    @FXML
    private TableView tableMecze;
    @FXML
    private TableView tableSedziowie;
    @FXML
    private TableView tableStadiony;
    @FXML
    private TableView tableTransfery;
    @FXML
    private TableView tableTrenerzy;
    @FXML
    private TableView tableWlasciciele;
    @FXML
    private TableView tableLigi;
    @FXML
    private TableColumn tableColumnWartosc;

    private boolean pilkarzeJuzWczytani = false;
    private boolean klubyJuzWczytane = false;
    private boolean ligiJuzWczytane = false;
    private boolean goleJuzWczytane = false;
    private boolean meczeJuzWczytane = false;
    private boolean sedziowieJuzWczytani = false;
    private boolean trenerzyJuzWczytani = false;
    private boolean stadionyJuzWczytane = false;
    private boolean transferyJuzWczytane = false;

    public TableView getTableKluby() {
        return tableKluby;
    }

    public TableView getTablePilkarze() {
        return tablePilkarze;
    }

    public TableView getTableGole() {
        return tableGole;
    }

    public TableView getTableMecze() {
        return tableMecze;
    }

    public TableView getTableSedziowie() {
        return tableSedziowie;
    }

    public TableView getTableStadiony() {
        return tableStadiony;
    }

    public TableView getTableTransfery() {
        return tableTransfery;
    }

    public TableView getTableTrenerzy() {
        return tableTrenerzy;
    }

    public TableView getTableWlasciciele() {
        return tableWlasciciele;
    }

    public TableView getTableLigi() {
        return tableLigi;
    }

    public void fillKluby() throws SQLException {

        if (klubyJuzWczytane) return;
        klubyJuzWczytane = true;
        String SQL = "SELECT * from KLUBY";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
                    while (rs.next()) {
                        //Iterate Row
                        Kluby klub = new Kluby(rs.getString("nazwa_klubu"), rs.getString("rok_zalozenia"), rs.getString("nazwa_ligi"));
                        //String rowdata[] = {klub.getNazwaKlubu(), klub.getRokZalozenia(), klub.getNazwaLigi()};
                        //System.out.println(Arrays.toString(rowdata));
                        tableKluby.getItems().add(klub);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();
    }

    public void openEditKlub(ActionEvent event) throws IOException {

        if(tableKluby.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/editKlub.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Edytuj");
            stage.setScene(new Scene((AnchorPane) loader.load()));
            EditKlubController editKlubController = loader.<EditKlubController>getController();

            editKlubController.connection = mainConnection;
            editKlubController.controller = this;

            Kluby klub = (Kluby) tableKluby.getSelectionModel().getSelectedItem();
            editKlubController.klub = klub;
            editKlubController.initializeOptions();

            editKlubController.textFieldClubName.setText(klub.getNazwaKlubu());
            editKlubController.oldName = klub.getNazwaKlubu();
            editKlubController.textFieldYear.setText(klub.getRokZalozenia());
            editKlubController.comboBoxLeague.setPromptText(klub.getNazwaLigi());

            stage.show();
        }
    }

    public void openInsertKlub(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/insertKlub.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Dodaj");
        stage.setScene(new Scene((AnchorPane) loader.load()));
        InsertKlubController insertKlubController = loader.<InsertKlubController>getController();

        insertKlubController.connection = mainConnection;
        insertKlubController.controller = this;
        insertKlubController.initializeOptions();
        //System.out.println(mainConnection);
        //System.out.println(insertKlubController.connection);

        stage.show();

        /*Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("insertKlub.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dodaj");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void openMoreKlub(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("scenes/moreKlub.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Real Madryt");
            stage.setScene(new Scene(root, 600, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillPilkarze() throws SQLException {

        if (pilkarzeJuzWczytani) return;
        pilkarzeJuzWczytani = true;

        String SQL = "SELECT * from PILKARZE";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
                    while (rs.next()) {

                        Pilkarze kopacz = new Pilkarze(rs.getString("id_pilkarza"), rs.getString("imie"),
                                rs.getString("nazwisko"), rs.getDate("data_urodzenia"), rs.getString("pozycja"),
                                rs.getDouble("wartosc_rynkowa"), rs.getDouble("pensja"), rs.getString("nazwa_klubu"));

                        tablePilkarze.getItems().add(kopacz);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();


        /*tablePilkarze.getItems().clear();
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        String SQL = "SELECT * from PILKARZE";
        //ResultSet
        ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
        try {
            /********************************
             * Data added to ObservableList *
             ********************************/
        /*
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate
                    String oneRow = rs.getString(i);
                    if (oneRow == null) oneRow = "null";
                    row.add(oneRow);
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
            tablePilkarze.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }*/
    }

    public void openEditPilkarz(ActionEvent event) throws IOException {

        if(tablePilkarze.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/editPilkarz.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Edytuj");
            stage.setScene(new Scene((AnchorPane) loader.load()));
            EditPilkarzController editPilkarzController = loader.<EditPilkarzController>getController();

            editPilkarzController.connection = mainConnection;
            editPilkarzController.controller = this;

            Pilkarze pilkarz = (Pilkarze) tablePilkarze.getSelectionModel().getSelectedItem();
            editPilkarzController.pilkarz = pilkarz;
            editPilkarzController.initializeOptions();

            editPilkarzController.textFieldImie.setText(pilkarz.getImie());
            editPilkarzController.textFieldNazwisko.setText(pilkarz.getNazwisko());
            editPilkarzController.comboBoxBYear.setPromptText(String.valueOf(pilkarz.getDataUrodzenia().getYear()+1900));
            editPilkarzController.comboBoxBMonth.setPromptText(String.valueOf(pilkarz.getDataUrodzenia().getMonth()+1));
            editPilkarzController.comboBoxBDay.setPromptText(String.valueOf(pilkarz.getDataUrodzenia().getDate()));
            editPilkarzController.comboBoxPos.setPromptText(pilkarz.getPozycja());
            editPilkarzController.textFieldWartosc.setText(String.valueOf(pilkarz.getWartoscRynkowa()));
            editPilkarzController.textFieldPensja.setText(String.valueOf(pilkarz.getPensja().toString()));
            editPilkarzController.comboBoxClub.setPromptText(pilkarz.getNazwaKlubu());

            stage.show();
        }
    }

    public void openInsertPilkarz(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/insertPilkarz.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Dodaj");
        stage.setScene(new Scene((AnchorPane) loader.load()));
        InsertPilkarzController insertPilkarzController = loader.<InsertPilkarzController>getController();

        insertPilkarzController.connection = mainConnection;
        insertPilkarzController.controller = this;
        insertPilkarzController.initializeOptions();

        stage.show();
    }

    public void fillMecze() throws SQLException {

        if (meczeJuzWczytane) return;
        meczeJuzWczytane = true;
        String SQL = "SELECT * from MECZE";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
                    while (rs.next()) {
                        //Iterate Row
                        Mecze mecz = new Mecze(rs.getString(1), rs.getDate(2), rs.getString(3),
                                rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                        tableMecze.getItems().add(mecz);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();
    }

    public void fillLigi() throws SQLException {

        if (ligiJuzWczytane) return;
        ligiJuzWczytane = true;
        String SQL = "SELECT * from LIGI";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
                    while (rs.next()) {
                        //Iterate Row
                        Ligi liga = new Ligi(rs.getString(1), rs.getString(2));
                        tableLigi.getItems().add(liga);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();
    }

    public void openEditLiga(ActionEvent event) throws IOException {

        if(tableLigi.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/editLiga.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Edytuj");
            stage.setScene(new Scene((AnchorPane) loader.load()));
            EditLigaController editLigaController = loader.<EditLigaController>getController();

            editLigaController.connection = mainConnection;
            editLigaController.controller = this;

            Ligi liga = (Ligi) tableLigi.getSelectionModel().getSelectedItem();
            editLigaController.liga = liga;

            editLigaController.textFieldName.setText(liga.getNazwaLigi());
            editLigaController.comboBoxKraj.setPromptText(liga.getKraj());

            stage.show();
        }
    }

    public void openInsertLiga(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/insertLiga.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Dodaj");
        stage.setScene(new Scene((AnchorPane) loader.load()));
        InsertLigaController insertLigaController = loader.<InsertLigaController>getController();

        insertLigaController.connection = mainConnection;
        insertLigaController.controller = this;

        stage.show();

    }

    public void fillSedziowie() throws SQLException {

        if (sedziowieJuzWczytani) return;
        sedziowieJuzWczytani = true;
        String SQL = "SELECT * from SEDZIOWIE";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
                    while (rs.next()) {
                        //Iterate Row
                        Sedziowie sedzia = new Sedziowie(rs.getString(1), rs.getString(2), rs.getString(3),
                                rs.getInt(4), rs.getString(5));
                        tableSedziowie.getItems().add(sedzia);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();
    }

    public void openEditSedzia(ActionEvent event) throws IOException {

        if (tableSedziowie.getSelectionModel().getSelectedItem() == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/editSedzia.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Edytuj");
        stage.setScene(new Scene((AnchorPane) loader.load()));
        EditSedziaController editSedziaController = loader.<EditSedziaController>getController();

        editSedziaController.connection = mainConnection;
        editSedziaController.controller = this;

        Sedziowie sedzia = (Sedziowie) tableSedziowie.getSelectionModel().getSelectedItem();
        editSedziaController.sedzia = sedzia;

        editSedziaController.textFieldImie.setText(sedzia.getImie());
        editSedziaController.textFieldNazwisko.setText(sedzia.getNazwisko());
        editSedziaController.textFieldWiek.setText(String.valueOf(sedzia.getWiek()));
        editSedziaController.comboBoxKraj.setPromptText(sedzia.getPochodzenie());

        stage.show();
    }

    public void openInsertSedzia(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/insertSedzia.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Dodaj");
        stage.setScene(new Scene((AnchorPane) loader.load()));
        InsertSedziaController insertSedziaController = loader.<InsertSedziaController>getController();

        insertSedziaController.connection = mainConnection;
        insertSedziaController.controller = this;

        stage.show();
    }

    public void fillGole() throws SQLException {

        if (goleJuzWczytane) return;
        goleJuzWczytane = true;
        String SQL = "SELECT * from GOLE";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
                    while (rs.next()) {
                        //Iterate Row
                        Gole gol = new Gole(rs.getString(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5), rs.getString(6));
                        tableGole.getItems().add(gol);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();
    }

    public void fillTrenerzy() throws SQLException {

        if (trenerzyJuzWczytani) return;
        trenerzyJuzWczytani = true;
        String SQL = "SELECT * from TRENERZY";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = mainConnection.createStatement().executeQuery(SQL);
                    while (rs.next()) {
                        Trenerzy trener = new Trenerzy(rs.getString(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5));
                        tableTrenerzy.getItems().add(trener);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();
    }

    public void openEditTrener(ActionEvent event) throws IOException {

        if (tableTrenerzy.getSelectionModel().getSelectedItem() == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/editTrener.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Edytuj");
        stage.setScene(new Scene((AnchorPane) loader.load()));
        EditTrenerController editTrenerController = loader.<EditTrenerController>getController();

        editTrenerController.connection = mainConnection;
        editTrenerController.controller = this;

        Trenerzy trener = (Trenerzy) tableTrenerzy.getSelectionModel().getSelectedItem();
        editTrenerController.trener = trener;

        editTrenerController.initializeOptions();
        editTrenerController.secondTF.setText(trener.getImie());
        editTrenerController.thirdTF.setText(trener.getNazwisko());
        editTrenerController.comboBoxKraj.setPromptText(trener.getPochodzenie());
        editTrenerController.comboBoxClub.setPromptText(trener.getNazwaKlubu());

        stage.show();
    }

    public void openInsertTrener(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/insertTrener.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Dodaj");
        stage.setScene(new Scene((AnchorPane) loader.load()));
        InsertTrenerController insertTrenerController = loader.<InsertTrenerController>getController();

        insertTrenerController.connection = mainConnection;
        insertTrenerController.controller = this;
        insertTrenerController.initializeOptions();

        stage.show();
    }

    public void addToTable(TableView tabela, Object byt) {
        tabela.getItems().add(byt);
    }

    public void removeFromTable(TableView tabela, Object byt) {
        tabela.getItems().remove(byt);
    }


    public void hideAll() {
        tablePilkarze.setVisible(false);
        tableKluby.setVisible(false);
    }
    public void initializeKluby() throws SQLException {

        TableColumn nameColumn = new TableColumn("Nazwa klubu");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaKlubu"));
        nameColumn.setPrefWidth(220.0);

        TableColumn yearColumn = new TableColumn("Rok zalozenia");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("rokZalozenia"));
        yearColumn.setPrefWidth(110.0);

        TableColumn ligueColumn = new TableColumn("Nazwa Ligi");
        ligueColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaLigi"));
        ligueColumn.setPrefWidth(290.0);

        tableKluby.getColumns().addAll(nameColumn, yearColumn, ligueColumn);

    }
    public void initializePilkarze() throws SQLException {
        TableColumn idColumn = new TableColumn("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idPilkarza"));
        idColumn.setPrefWidth(5.0);
        idColumn.setVisible(false);

        TableColumn nameColumn = new TableColumn("Imie");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nameColumn.setPrefWidth(100.0);

        TableColumn surnameColumn = new TableColumn("Nazwisko");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        surnameColumn.setPrefWidth(100.0);

        TableColumn birthDateColumn = new TableColumn("Data urodzenia");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dataUrodzenia"));
        birthDateColumn.setPrefWidth(100.0);

        TableColumn posColumn = new TableColumn("Pozycja");
        posColumn.setCellValueFactory(new PropertyValueFactory<>("pozycja"));
        posColumn.setPrefWidth(80.0);

        TableColumn priceColumn = new TableColumn("Wartość rynkowa");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("wartoscRynkowa"));
        priceColumn.setPrefWidth(100.0);

        TableColumn salaryColumn = new TableColumn("Pensja");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("pensja"));
        salaryColumn.setPrefWidth(80.0);

        TableColumn clubColumn = new TableColumn("Nazwa klubu");
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaKlubu"));
        clubColumn.setPrefWidth(100.0);

        tablePilkarze.getColumns().addAll(idColumn, nameColumn, surnameColumn, birthDateColumn, posColumn, priceColumn, salaryColumn, clubColumn);
    }
}
