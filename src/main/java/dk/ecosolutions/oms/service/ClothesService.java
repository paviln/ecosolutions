package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Clothe;
import dk.ecosolutions.oms.persistence.database.ClothesDao;

import java.util.List;

public class ClothesService {
    public static void addClothes(Clothe clothe) {
        ClothesDao clothesDao = new ClothesDao();
       clothesDao.save(clothe);
    }
    public static List<Clothe> allClothes(){
        ClothesDao clothesDao = new ClothesDao();
        return clothesDao.all();
    }
}
