package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.helpers.DbHelper;
import dk.ecosolutions.oms.domain.Clothe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clothe model implementation of DAO interface.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class ClothesDao implements Dao<Clothe> {
    private Connection con;
    private Statement stmt;
    private PreparedStatement ps;
    private ResultSet rs;

    public Clothe get(int id) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("SELECT * FROM clothes WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Clothe clothe = extractClothes(rs);

                return clothe;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(rs);
            DbHelper.close(ps);
            DbHelper.close(con);
        }

        return null;
    }

    public List<Clothe> all() {
        try {
            con = Database.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM clothes");
            List<Clothe> clothes = new ArrayList<Clothe>();
            while (rs.next()) {
                Clothe clothe = extractClothes(rs);
                clothes.add(clothe);
            }

            return clothes;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(rs);
            DbHelper.close(stmt);
            DbHelper.close(con);
        }

        return null;
    }

    public void save(Clothe clothe) {

    }

    public void update(Clothe clothe) {

    }

    public void delete(Clothe clothe) {

    }

    private Clothe extractClothes(ResultSet rs) {
        try {
            Clothe clothe = new Clothe();
            clothe.setId(rs.getInt("id"));
            clothe.setName(rs.getString("name"));

            return clothe;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}