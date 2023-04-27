package final_thread_task;

class Family {
    private String name; // twoLetters “aa”, “ab”, …, “zz” – for example, up to 100 names
    private String travelTo; // 4 cities – “Kalush”, “Kosiv”, “Galych”, “Kolomiya”
    private int count; // family members count, from 1 to 4 members

    public Family () {
    }

    public Family(String name, String travelTo, int count) {
        this.name = name;
        this.travelTo = travelTo;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTravelTo() {
        return travelTo;
    }

    public void setTravelTo(String travelTo) {
        this.travelTo = travelTo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Family{" +
                "name='" + name + '\'' +
                ", travelTo='" + travelTo + '\'' +
                ", count=" + count +
                '}';
    }
}