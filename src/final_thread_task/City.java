package final_thread_task;

public enum City {
    KALUSH("Kalush"),
    KOSIV("Kosiv"),
    GALYCH("Galych"),
    KOLOMIYA("Kolomiya");

    private String name;

    City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
