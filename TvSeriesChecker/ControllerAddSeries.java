package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControllerAddSeries {
    @FXML
    private TextField field;

    private DBQueries q=new DBQueries();

    public void parseAndAddtoDataBase(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements dat = doc.select(".airdate");
        String title = doc.title();
        char[] arr = title.toCharArray();
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == '('){
                title = title.substring(0, i);
                break;
            }
        }
        for (Element e : dat) {
            String currentElement = e.text().replace(".", "").replace(" ", "-");
            SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy");
            try {
                Date date = format.parse(currentElement);
                String today = format.format(new Date());
                Date todayDate = format.parse(today);
                if (date.after(todayDate)) {
                    format=new SimpleDateFormat("E dd-MM-yyyy");
                    String thisDate=format.format(date);
                    try {
                        ResultSet rs= q.getSeriesFromNext(title,thisDate, q.getUserID());
                        if(!rs.next()) {
                            q.addSeriestoWhatsNext(title, thisDate, link);
                            field.setText("");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            }

        }

    }

    public void handleAddButton() throws IOException, SQLException {
        String link = field.getText();
        parseAndAddtoDataBase(link);
    }


}