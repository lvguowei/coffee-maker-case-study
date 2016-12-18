package com.guowei.lv;

import com.guowei.lv.component.CoffeeMakerAPI;

public class Main {

    public static void main(String[] args) {
	    CoffeeMakerAPI api = new M4CoffeeMakerApiImplementation();

        M4UserInterface ui = new M4UserInterface(api);
        M4HotWaterSource hws = new M4HotWaterSource(api);
        M4ContainmentVessel cv = new M4ContainmentVessel(api);

        ui.init(hws, cv);
        hws.init(ui, cv);
        cv.init(ui, hws);

        while (true) {
            ui.poll();
            hws.poll();
            cv.poll();
        }


    }
}
