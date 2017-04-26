package sample;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQueries {
    private final static DBconnector db=new DBconnector();
    private ResultSet result;
    private static int userID;

    public int  getUserID() {

        return userID;
    }

    public void setUserID(int userID) {

        this.userID = userID;
    }



    public boolean loginChecker(String login, String password) throws SQLException {
        String query="Select * from Users";
        result= db.getState().executeQuery(query);
        while(result.next()){
            if(result.getString("login").equals(login)){
                if(result.getString("password").equals(password)) {
                    setUserID(result.getInt("id"));
                    return true;
                }
            }

        }
        return false;
    }
    //POPULATING TABLEVIEW
    public ResultSet fillHaventWatch() throws SQLException {
    String query="SELECT * FROM HaventSeen WHERE UserID=" + getUserID();
      return db.getState().executeQuery(query);
    }

    //DELETING ITEM FROM TABLEVIEW IN MAIN WINDOW(Selected by user)
    public void deleteItem(int id) throws SQLException {
        String sqlDelete = "DELETE FROM HaventSeen WHERE DeleteID = '" + id + "'";
        db.getState().executeUpdate(sqlDelete);
    }
    //INSERTING INTO TABLE WHATSNEXT(Series which are picked by user)
    public void addSeriestoWhatsNext(String title, String date, String link) throws SQLException {

        String sqlInsert="INSERT INTO WhatsNext (`id`, `title`, `date`, `link`) VALUES ('"+getUserID()+"', '"+title+"', '"+date+"', '"+link+"')";
        db.getState().executeUpdate(sqlInsert);
    }
    //
    public ResultSet getSeriesFromNext() throws SQLException {
        String sqlQuery="SELECT * FROM WhatsNext WHERE id='"+getUserID()+"'";
        return db.getState().executeQuery(sqlQuery);
    }
    public ResultSet getSeriesFromHaventSeen(String title, String date) throws SQLException {
        String sqlQuery="SELECT * FROM HaventSeen WHERE title='"+title+"' AND date='"+date+"'";
        return db.getState().executeQuery(sqlQuery);
    }
    public ResultSet getSeriesFromNext(String title, String date, int id) throws SQLException {
        String sqlQuery="SELECT * FROM WhatsNext WHERE id='"+id+"' AND title='"+title+"' AND date='"+date+"'";
        return db.getState().executeQuery(sqlQuery);
    }
    public void deleteSeriesFromNext(String date) throws SQLException {
        String sqlDelete="DELETE FROM WhatsNext WHERE date='"+date+"'";
        db.getState().executeUpdate(sqlDelete);
    }
    public void addToHaventSeen(String title, String date) throws SQLException {
        String sqlInsert="INSERT INTO HaventSeen (UserID, title, date) VALUES('"+getUserID()+"', '"+title+"','"+date+"')";
        db.getState().executeUpdate(sqlInsert);
    }

}
