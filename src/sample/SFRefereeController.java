package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLRecoverableException;

public class SFRefereeController {

    public Connection connection;
    public InsertMeczController insertMeczController;
    public EditMeczController editMeczController;
    public String operation;
    @FXML
    public TextField textFieldNazwisko;
    @FXML
    public TextField textFieldKraj;
    @FXML
    public TableView tableSearch;

    public void fetchInitialData() {

        tableSearch.getItems().clear();
        String SQL = "SELECT * from SEDZIOWIE ORDER BY NAZWISKO";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet rs = connection.createStatement().executeQuery(SQL);
                    while (rs.next()) {
                        Sedziowie sedzia = new Sedziowie(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                        tableSearch.getItems().add(sedzia);
                    }
                    rs.close();
                } catch (SQLRecoverableException e) {

                    insertMeczController.controller.showConnectionLostDialogAndExitApp();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on Building Data");
                }
            }
        };
        new Thread(r).start();
    }

    public void search() {

        //boolean[] found = new boolean[1];
        String nazwisko = textFieldNazwisko.getText();
        String kraj = textFieldKraj.getText();
        if (nazwisko == null || nazwisko.equals("")) {nazwisko = "#123456789";}
        if (kraj == null || kraj.equals("")) {kraj = "#123456789";}

        tableSearch.getItems().clear();

        String SQL = "SELECT * from SEDZIOWIE where POCHODZENIE like '%" + kraj.replaceAll("'", "''") +
                "%' OR nazwisko like '%" + nazwisko.replaceAll("'", "''") + "%' ORDER BY NAZWISKO";
        //found[0] = false;
        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            while (rs.next()) {
                Sedziowie sedzia = new Sedziowie(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                tableSearch.getItems().add(sedzia);
                //found[0] = true;
            }
            rs.close();
        } catch (SQLRecoverableException e) {
            if (operation.equals("Edycja")) {
                editMeczController.controller.showConnectionLostDialogAndExitApp();
            }
            else {
                insertMeczController.controller.showConnectionLostDialogAndExitApp();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        //if (!found[0]) {
        tableSearch.setPlaceholder(new Label("Nie znaleziono pasujących sędziów"));
        //}
    }

    public void saveSelected(ActionEvent event) {

        if(tableSearch.getSelectionModel().getSelectedItem() == null) return;

        Sedziowie sedzia = (Sedziowie) tableSearch.getSelectionModel().getSelectedItem();
        if (operation.equals("Edycja")) {
            editMeczController.sedziaId = sedzia.getIdSedziego();
            editMeczController.textFieldSedzia.setText(sedzia.getImie() + " " + sedzia.getNazwisko());
        }
        else {
            insertMeczController.sedziaId = sedzia.getIdSedziego();
            insertMeczController.textFieldSedzia.setText(sedzia.getImie() + " " + sedzia.getNazwisko());
        }
        tableSearch.getItems().clear();

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
