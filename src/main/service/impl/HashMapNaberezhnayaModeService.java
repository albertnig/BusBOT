package main.service.impl;

import entity.Naberezhnaya;
import main.service.NaberezhnayaModeService;

import java.util.HashMap;
import java.util.Map;

public class HashMapNaberezhnayaModeService implements NaberezhnayaModeService {

    private final Map<Long, Naberezhnaya> originalNaberezhnaya = new HashMap<>();
    public HashMapNaberezhnayaModeService(){
        System.out.println("HASHMAP MODE is created");
    }
    @Override
    public Naberezhnaya getOriginalNaberezhnaya(long chatId) {
        return originalNaberezhnaya.getOrDefault(chatId, Naberezhnaya.Автобус_49);
    }

    @Override
    public void setOriginalNaberezhnaya(long chatId, Naberezhnaya naberezhnaya) {
        originalNaberezhnaya.put(chatId, naberezhnaya);
    }
}
