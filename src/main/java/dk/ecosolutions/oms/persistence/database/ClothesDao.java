package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Clothes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClothesDao implements Dao<Clothes> {
    public Clothes get(int id) throws SQLException{
        return null;
    }


    public List<Clothes> all() {
        try{
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clothes");
            List<Clothes> clothes = new ArrayList<Clothes>();
            while (rs.next()){
                Clothes clothes1 = extractClothes(rs);
                clothes.add(clothes1);
            }
            connection.close();
            return clothes;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public void save(Clothes clothes)  {

    }


    public void update(Clothes clothes)  {

    }


    public void delete(Clothes clothes) {

    }
private Clothes extractClothes(ResultSet rs){
    try{
        Clothes clothes = new Clothes();
        clothes.setId(rs.getInt("id"));
        clothes.setName(rs.getString("name"));
        return clothes;
    }catch (Exception e){

    }
    return null;
}
}
