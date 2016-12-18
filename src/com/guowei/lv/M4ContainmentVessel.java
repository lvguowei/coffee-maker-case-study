package com.guowei.lv;

import com.guowei.lv.component.CoffeeMakerAPI;
import com.guowei.lv.component.ContainmentVessel;
import com.guowei.lv.component.Pollable;

public class M4ContainmentVessel extends ContainmentVessel implements Pollable {

    private final CoffeeMakerAPI api;
    private int lastPotStatus;

    public M4ContainmentVessel(CoffeeMakerAPI api) {
        this.api = api;
        lastPotStatus = CoffeeMakerAPI.POT_EMPTY;
    }

    @Override
    public boolean isReady() {
        int plateStatus = api.getWarmerPlateStatus();
        return plateStatus == CoffeeMakerAPI.POT_EMPTY;
    }

    @Override
    public void poll() {
        int potStatus = api.getWarmerPlateStatus();
        if (potStatus != lastPotStatus) {
            if (isBrewing) {
                handleBrewingEvent(potStatus);
            } else if (!isComplete) {
                handleIncompleteEvent(potStatus);
            }
            lastPotStatus = potStatus;
        }
    }

    private void handleIncompleteEvent(int potStatus) {
        if (potStatus == CoffeeMakerAPI.POT_NOT_EMPTY) {
            api.setWarmerState(CoffeeMakerAPI.WARMER_ON);
        } else if (potStatus == CoffeeMakerAPI.WARMER_EMPTY) {
            api.setWarmerState(CoffeeMakerAPI.WARMER_OFF);
        } else {
            api.setWarmerState(CoffeeMakerAPI.WARMER_OFF);
            declareComplete();
        }
    }

    private void handleBrewingEvent(int potStatus) {
        if (potStatus == CoffeeMakerAPI.POT_NOT_EMPTY) {
            containerAvailable();
            api.setWarmerState(CoffeeMakerAPI.WARMER_ON);
        } else if (potStatus == CoffeeMakerAPI.WARMER_EMPTY) {
            containerUnavailable();
            api.setWarmerState(CoffeeMakerAPI.WARMER_OFF);
        } else {
            containerAvailable();
            api.setWarmerState(CoffeeMakerAPI.WARMER_OFF);
        }
    }
}
