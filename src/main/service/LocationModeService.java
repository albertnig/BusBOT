package main.service;

import entity.Location;
import main.service.impl.HashMapLocationModeService;

public interface LocationModeService {
    static LocationModeService getInstance() {
        return  new HashMapLocationModeService();
    }
    Location getOriginalLocation(long chatId);
    void setOriginalLocation(long chatId, Location location);
}
