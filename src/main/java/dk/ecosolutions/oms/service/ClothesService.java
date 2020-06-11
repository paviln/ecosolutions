package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Clothe;
import dk.ecosolutions.oms.persistence.database.ClothesDao;

import java.util.List;

/**
 * Clothe service.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */

public class ClothesService {
    public static List<Clothe> allClothes() {
        ClothesDao clothesDao = new ClothesDao();
        return clothesDao.all();
    }
}
