package entity;

public enum Location {
    ул_Белинского(431), пл_Восстания_работа(451), Комб_Здоровье(467), пл_Восстания_дом (475), ул_Набережная(482);
    private final int id;

    Location(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
