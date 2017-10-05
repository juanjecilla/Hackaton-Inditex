package com.monoloco.zaraqueue.utils;

import com.monoloco.zaraqueue.base.BaseUtils;
import com.monoloco.zaraqueue.model.Clothes;
import com.monoloco.zaraqueue.model.QueueUser;

import java.util.HashMap;

/**
 * Created by root on 5/10/17.
 */

public class FirebaseMappingUtils extends BaseUtils {

    public static HashMap<String, Object> toMap(QueueUser queueUser){
        HashMap<String, Object> user = new HashMap<>();
        HashMap<String, Object> clothes = new HashMap<>();

        user.put("name", queueUser.getName());
        user.put("birthdate", queueUser.getBirthdate());
        user.put("gender", queueUser.getGender());

        for (Clothes item: queueUser.getClothes()) {
            clothes.put("id", item.getId());
            clothes.put("type", item.getType());
        }

        user.put("clothes", clothes);
        user.put("company", queueUser.isCompany());
        user.put("estimatedTime", queueUser.getEstimatedTime());

        return user;
    }

    public static HashMap<String, Object> toMap(Clothes clothes){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", clothes.getId());
        map.put("type", clothes.getType());

        return map;
    }
}
