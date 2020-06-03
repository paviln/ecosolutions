package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Clothe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClothesDao implements Dao<Clothe> {
    public Clothe get(int id) throws SQLException{
        return null;
    }


    public List<Clothe> all() {
        try{
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clothes");
            List<Clothe> clothes = new ArrayList<Clothe>();
            while (rs.next()){
                Clothe clothe = extractClothes(rs);
                clothes.add(clothe);
            }
            connection.close();
            return clothes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void save(Clothe clothe)  {

    }


    public void update(Clothe clothe)  {

    }


    public void delete(Clothe clothe) {

    }
private Clothe extractClothes(ResultSet rs){
    try{
        Clothe clothe = new Clothe();
        clothe.setId(rs.getInt("id"));
        clothe.setName(rs.getString("name"));
        return clothe;
    }catch (Exception e){

    }
    return null;
}
}
