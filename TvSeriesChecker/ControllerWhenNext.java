package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public class ControllerWhenNext implements Initializable {

    @FXML
    TableView<Series> tableView;
    @FXML
    TableColumn<Series,String> title;
    @FXML
    TableColumn<Series,String> date;
    private  DBQueries q=new DBQueries();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    title.setCellValueFactory(new PropertyValueFactory<Series, String>("title"));
    date.setCellValueFactory(new PropertyValueFactory<Series, String>("date"));
        try {
            tableView.getItems().setAll(getSeries());
        } catch (SQLException  | ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Series> getSeries() throws SQLException, ParseException, IOException {
        ArrayList<String> deleteDates=new ArrayList();
        ArrayList<String> links=new ArrayList<>();
        ResultSet r=q.getSeriesFromNext();
        ObservableList<Series> series= FXCollections.observableArrayList();
        String today= new SimpleDateFormat("E dd-MM-yyyy").format(new Date());
        SimpleDateFormat format= new SimpleDateFormat("E dd-MM-yyyy");
        Date todayDate=format.parse(today);
        while(r.next()){
            Date seriesDate=format.parse(r.getString("date"));
            if(!(todayDate.after(seriesDate) || todayDate.equals(seriesDate)))
            series.add(new Series(r.getString("title"), r.getString("date"),0));
            else{
                deleteDates.add(format.format(seriesDate));
                links.add(r.getString("link"));
            }

        }
        for(int i=0; i<deleteDates.size();++i){
            q.deleteSeriesFromNext(deleteDates.get(i));
            checkWhenIsNextEpisode(links.get(i));
        }

        return series;

    }

    private void checkWhenIsNextEpisode(String link) throws IOException {
       ControllerAddSeries controllerAddSeries=new ControllerAddSeries();
       controllerAddSeries.parseAndAddtoDataBase(link);
    }


}
