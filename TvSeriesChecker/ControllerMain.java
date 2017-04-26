package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public class ControllerMain implements Initializable {
@FXML
private TableView<Series> tableView;
@FXML
private TableColumn<Series, String> title;
@FXML
private TableColumn<Series,String> date;

private DBQueries q=new DBQueries();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setCellValueFactory(new PropertyValueFactory<Series, String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Series, String>("date"));
        try {
            addToHaventSeen();
            tableView.getItems().setAll(getSeries());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void addSeriesButton(ActionEvent action) throws IOException {
    Parent parent= FXMLLoader.load(getClass().getResource("FXML/AddSeries.fxml"));
    Stage stage=new Stage();
    Scene scene=new Scene(parent);
    stage.setScene(scene);
    stage.setTitle("Add new TV Series");
    stage.show();
    }

    public void deleteButton(ActionEvent action){
    ObservableList<Series> allSeries, selectedSeries;
    allSeries=tableView.getItems();
    Series series=tableView.getSelectionModel().getSelectedItem();
        try {
            q.deleteItem(series.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectedSeries=tableView.getSelectionModel().getSelectedItems();
    selectedSeries.forEach(allSeries::remove);
    }

    public void whatsNextButton(ActionEvent action) throws IOException {
    Parent parent=FXMLLoader.load(getClass().getResource("FXML/WhenNext.fxml"));
    Stage stage=new Stage();
    Scene scene=new Scene(parent);
    stage.setTitle("What's next?");
    stage.setScene(scene);
    stage.show();

    }




    public ObservableList<Series> getSeries() throws SQLException {
    ObservableList<Series> series= FXCollections.observableArrayList();
            ResultSet r= q.fillHaventWatch();
        while(r.next()) {
            series.add(new Series(r.getString("title"), r.getString("date"), r.getInt("DeleteID")));
        }
        return series;
        }

        public void addToHaventSeen() throws SQLException, ParseException {
            ArrayList<String> listTitle=new ArrayList();
            ArrayList<String> listDate=new ArrayList();
            SimpleDateFormat format= new SimpleDateFormat("E dd-MM-yyyy");
            String today= format.format(new Date());
            Date todayDate=format.parse(today);
            ResultSet r=q.getSeriesFromNext();
        while(r.next()){
            Date seriesDate=format.parse(r.getString("date"));
            if(todayDate.equals(seriesDate)){
                String thisDate=format.format(seriesDate);
                listTitle.add(r.getString("title"));
                listDate.add(thisDate);
            }
        }
    for(int i=0; i<listTitle.size();++i){
            String title=listTitle.get(i);
            String date=listDate.get(i);
            ResultSet re=q.getSeriesFromHaventSeen(title,date);
            if(!re.next())
                q.addToHaventSeen(listTitle.get(i),listDate.get(i));
    }
    }

}
