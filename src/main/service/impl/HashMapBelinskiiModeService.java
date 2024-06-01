package main.service.impl;

import entity.Belinskii;
import main.service.BelinskiiModeService;

import java.util.HashMap;
import java.util.Map;

public class HashMapBelinskiiModeService implements BelinskiiModeService {
    private final Map<Long, Belinskii> originalBelinskii = new HashMap<>();
    public HashMapBelinskiiModeService(){
        System.out.println("HASHMAP MODE is created");
    }
    @Override
    public Belinskii getOriginalBelinskii(long chatId) {
        return originalBelinskii.getOrDefault(chatId, Belinskii.Автобус_62);
    }

    @Override
    public void setOriginalBelinskii(long chatId, Belinskii belinskii) {
        originalBelinskii.put(chatId, belinskii);
    }
}
