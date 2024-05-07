package jsr268gp.sampleclient;

import java.sql.*;

public class SearchResults {
    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    private ResultSet resultSet ;
    private boolean found ;


}