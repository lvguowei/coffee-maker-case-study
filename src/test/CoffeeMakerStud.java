package test;

import com.guowei.lv.component.CoffeeMakerAPI;

public class CoffeeMakerStud implements CoffeeMakerAPI {

    public boolean buttonPressed;
    public boolean lightOn;
    public boolean boilerOn;
    public boolean valveClosed;
    public boolean plateOn;
    public boolean boilerEmpty;
    public boolean potPresent;
    public boolean potNotEmpty;

    public CoffeeMakerStud() {
        buttonPressed = false;
        lightOn = false;
        boilerOn = false;
        valveClosed = true;
        plateOn = false;
        boilerEmpty = true;
        potPresent = true;
        potNotEmpty = false;
    }

    @Override
    public int getWarmerPlateStatus() {
        if (!potPresent) {
            return WARMER_EMPTY;
        } else if (potNotEmpty) {
            return POT_NOT_EMPTY;
        } else {
            return POT_EMPTY;
        }
    }

    @Override
    public int getBoilerStatus() {
        return boilerEmpty ? BOILER_EMPTY : BOILER_NOT_EMPTY;
    }

    @Override
    public int getBrewButtonStatus() {
        if (buttonPressed) {
            buttonPressed = false;
            return BREW_BUTTON_PUSHED;
        } else {
            return BREW_BUTTON_NOT_PUSHED;
        }
    }

    @Override
    public void setBoilerState(int boilerStatus) {
        boilerOn = boilerStatus == BOILER_ON;
    }

    @Override
    public void setWarmerState(int warmerState) {
        plateOn = warmerState == WARMER_ON;
    }

    @Override
    public void setIndicatorState(int indicatorState) {
        lightOn = indicatorState == INDICATOR_ON;
    }

    @Override
    public void setReliefValveState(int reliefValveState) {
        valveClosed = reliefValveState == VALVE_CLOSED;
    }
}
