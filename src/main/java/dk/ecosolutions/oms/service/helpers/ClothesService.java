package dk.ecosolutions.oms.service.helpers;

import dk.ecosolutions.oms.domain.Clothes;
import dk.ecosolutions.oms.persistence.database.ClothesDao;

import java.util.List;

public class ClothesService {
    public static void addClothes(Clothes clothes) {
        ClothesDao clothesDao = new ClothesDao();
       clothesDao.save(clothes);
    }
    public static List<Clothes> allClothes(){
        ClothesDao clothesDao = new ClothesDao();
        return clothesDao.all();
    }
}
