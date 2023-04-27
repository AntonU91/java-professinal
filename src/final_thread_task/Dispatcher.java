package final_thread_task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dispatcher {
    public static void main(String[] args) {
        String[] city = {"Kalush", "Kosiv", "Galych", "Kolomiya"};
        Plane plane1 = new Plane(new ArrayList<>(), 1);
        Plane plane2 = new Plane(new ArrayList<>(), 2);
        Plane plane3 = new Plane(new ArrayList<>(), 3);
        Plane plane4 = new Plane(new ArrayList<>(), 4);

        List<Plane> planes = new ArrayList<>();
        planes.add(plane1);
        planes.add(plane2);
        planes.add(plane3);
        planes.add(plane4);

//        Map<Family, String> kalushDirection = Controller.createFamilyMapDueToDestinaion(planes, City.KALUSH.getName());
//        Map<Family, String> kosivDirection = Controller.createFamilyMapDueToDestinaion(planes, City.KOSIV.getName());
//        Map<Family, String> galychDirection = Controller.createFamilyMapDueToDestinaion(planes, City.GALYCH.getName());
//        Map<Family, String> kolomiyaDirection = Controller.createFamilyMapDueToDestinaion(planes, City.KOLOMIYA.getName());

    }
}

class Controller {

//    public static Map<Family, String> createFamilyMapDueToDestinaion(List<? extends Plane> planes) {
//        Map<Family, String> familyMapDueToDestination = new HashMap<>();
//
//        for (Plane plane : planes) {
//            for (Family family : plane.getFamilies()) {
//                if (family.getTravelTo().equals(city)) {
//                    familyListDueToDestination.add(family);
//                }
//            }
//        }
//        return familyListDueToDestination;
//    }

    public Map<Family, Integer> dividedOnGroupsDueToMembersCount(List<Family> families) {
        Map<Family, Integer> familyGroupsDueToMembersCount = new HashMap<>();

        for (Family family : families) {
            int count = family.getCount();
            familyGroupsDueToMembersCount.put(family, count);
        }
        return familyGroupsDueToMembersCount;
    }

    public void distributeToTheBus(Map<Family, Integer> familyGroupsDueToMembersCount) {
        Bus bus = BusGenerator.createBus();

    }

}
