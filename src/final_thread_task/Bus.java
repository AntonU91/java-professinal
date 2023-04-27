package final_thread_task;

class Bus {
    private int passengersCount; // 6 or 7 or 8
    private String driveTo; // 4 cities – “Kalush”, “Kosiv”, “Galych”, “Kolomiya”

    public Bus(int passengersCount, String driveTo) {
        this.passengersCount = passengersCount;
        this.driveTo = driveTo;
    }

    public Bus() {
    }

    public int getPassengersCount() {
        return passengersCount;
    }

    public void setPassengersCount(int passengersCount) {
        this.passengersCount = passengersCount;
    }

    public String getDriveTo() {
        return driveTo;
    }

    public void setDriveTo(String driveTo) {
        this.driveTo = driveTo;
    }
}
