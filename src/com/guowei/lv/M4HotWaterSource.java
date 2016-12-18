package com.guowei.lv;

import com.guowei.lv.component.CoffeeMakerAPI;
import com.guowei.lv.component.HotWaterSource;
import com.guowei.lv.component.Pollable;

public class M4HotWaterSource extends HotWaterSource implements Pollable {

    private final CoffeeMakerAPI api;

    public M4HotWaterSource(CoffeeMakerAPI api) {
        super();
        this.api = api;
    }

    @Override
    public boolean isReady() {
        int boilerStatus = api.getBoilerStatus();
        return boilerStatus == CoffeeMakerAPI.BOILER_NOT_EMPTY;
    }

    @Override
    public void pause() {
        api.setBoilerState(CoffeeMakerAPI.BOILER_OFF);
        api.setReliefValveState(CoffeeMakerAPI.VALVE_OPEN);
    }

    @Override
    public void resume() {
        api.setBoilerState(CoffeeMakerAPI.BOILER_ON);
        api.setReliefValveState(CoffeeMakerAPI.VALVE_CLOSED);
    }

    @Override
    public void startBrewing() {
        api.setReliefValveState(CoffeeMakerAPI.VALVE_CLOSED);
        api.setBoilerState(CoffeeMakerAPI.BOILER_ON);
    }


    @Override
    public void poll() {
        int boilerStatus = api.getBoilerStatus();
        if (isBrewing) {
            if (boilerStatus == CoffeeMakerAPI.BOILER_EMPTY) {
                api.setBoilerState(CoffeeMakerAPI.BOILER_OFF);
                api.setReliefValveState(CoffeeMakerAPI.VALVE_CLOSED);
                declareDone();
            }
        }
    }
}
