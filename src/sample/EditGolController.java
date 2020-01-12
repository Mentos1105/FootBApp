package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditGolController {

    public Connection connection;
    public Controller controller;
    public Gole gol;
    public Mecze mecz;
    public String idPilkarza;

    @FXML
    public TextField textFieldPilkarz;
    @FXML
    public TextField textFieldMecz;
    @FXML
    public TextField textFieldMinuta;
    @FXML
    public RadioButton radioButtonGospodarze;
    @FXML
    public RadioButton radioButtonGoscie;
    @FXML
    public CheckBox checkBoxSamobojczy;
    @FXML
    public Label labelWarning;

    public void editHandler(ActionEvent event) throws SQLException {

        String min = textFieldMinuta.getText();

        Integer minuta = Integer.parseInt(min);

        Integer czySamobojczy;
        if (checkBoxSamobojczy.isSelected()) {
            czySamobojczy = 1;
        } else {
            czySamobojczy = 0;
        }

        Integer dlaGospodarzy;
        if (radioButtonGospodarze.isSelected()) {
            dlaGospodarzy = 1;
        } else {
            dlaGospodarzy = 0;
        }
        String okolicznosci = null;
        if (czySamobojczy == 0) {
            okolicznosci = "Nie";
        } else {
            okolicznosci = "Tak";
        }


        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE GOLE SET MECZ_ID = " + mecz.getMeczId() + ", ID_PILKARZA = " + idPilkarza + ", MINUTA = " +
                    minuta + ", CZY_SAMOBOJCZY = " + czySamobojczy + ", CZY_DLA_GOSPODARZY = " + dlaGospodarzy + " WHERE GOL_ID = " + gol.getGolId());

            ResultSet rs = statement.executeQuery("select imie || ' ' || nazwisko from PILKARZE where ID_PILKARZA = " + idPilkarza);
            rs.next();
            String pilkarz = rs.getString(1);
            controller.removeFromTable(controller.getTableGole(), gol);
            Gole nowyGol = new Gole(gol.getGolId(), mecz.getMeczId(), idPilkarza, minuta, czySamobojczy, dlaGospodarzy, pilkarz,
                    okolicznosci, mecz.getGospodarze(), mecz.getGoscie(), mecz.getData());
            controller.addToTable(controller.getTableGole(), nowyGol);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    public void deleteHandler(ActionEvent event) throws SQLException {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM GOLE WHERE GOL_ID = " + gol.getGolId());
            controller.removeFromTable(controller.getTableGole(), gol);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    public void hideWindow(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void findPilkarz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/searchForPlayer.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene((AnchorPane) loader.load()));
        stage.show();

        SFPlayerController sfPlayerController = loader.<SFPlayerController>getController();
        sfPlayerController.connection = connection;
        sfPlayerController.editGolController = this;
        sfPlayerController.opcja = "edycjaGol";
        sfPlayerController.fetchInitialData();
    }

    public void findMecz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/searchForGame.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene((AnchorPane) loader.load()));
        stage.show();

        SFGameController sfGameController = loader.<SFGameController>getController();
        sfGameController.connection = connection;
        sfGameController.editGolController = this;
        sfGameController.operation = "Edycja";
    }

}
