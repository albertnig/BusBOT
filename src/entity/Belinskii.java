package entity;

public enum Belinskii {
    Автобус_29 (41), Автобус_62 (45), Нет_необходимости (47);
    private final int id;

    Belinskii(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
