package main.service;

import entity.Belinskii;
import main.service.impl.HashMapBelinskiiModeService;

public interface BelinskiiModeService {
    static BelinskiiModeService getInstance() {
        return new HashMapBelinskiiModeService();
    }

    Belinskii getOriginalBelinskii(long chatId);

    void setOriginalBelinskii(long chatId, Belinskii belinskii);
}
