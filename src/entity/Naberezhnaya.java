package entity;

public enum Naberezhnaya {
    Автобус_45 (31), Автобус_49 (51), Нет_необходимости (82);
    private final int id;

    Naberezhnaya(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
