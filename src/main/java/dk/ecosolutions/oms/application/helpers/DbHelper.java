package dk.ecosolutions.oms.application.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbHelper {
    public static void close(Statement stmt) {
        try {
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement ps) {
        try {
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
