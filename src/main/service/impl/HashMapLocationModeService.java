package main.service.impl;

import entity.Location;
import main.service.LocationModeService;

import java.util.HashMap;
import java.util.Map;

public class HashMapLocationModeService implements LocationModeService {
    private final Map<Long, Location> originalLocation = new HashMap<>();
    public HashMapLocationModeService(){
        System.out.println("HASHMAP MODE is created");
    }
    @Override
    public Location getOriginalLocation(long chatId) {
        return originalLocation.getOrDefault(chatId, Location.Комб_Здоровье);
    }

    @Override
    public void setOriginalLocation(long chatId, Location location) {
originalLocation.put(chatId, location);
    }
}
