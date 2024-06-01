package main.service;

import entity.Naberezhnaya;
import main.service.impl.HashMapNaberezhnayaModeService;

public interface NaberezhnayaModeService {
    static NaberezhnayaModeService getInstance() {
        return new HashMapNaberezhnayaModeService();
    }

    Naberezhnaya getOriginalNaberezhnaya(long chatId);

    void setOriginalNaberezhnaya(long chatId, Naberezhnaya naberezhnaya);
}
