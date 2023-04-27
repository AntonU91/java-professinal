package final_thread_task;

import java.util.List;

class Plane {
    private final List<Family> families; // exactly 100 family members
    private int id; // exactly 1, 2, 3

    public Plane(List<Family> families, int id) {
        this.families = FamilyListGenerator.createFamily(40);
        this.id = id;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public int getId() {
        return id;
    }
}
