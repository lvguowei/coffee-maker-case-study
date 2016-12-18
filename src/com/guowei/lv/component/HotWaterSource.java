package com.guowei.lv.component;

public abstract class HotWaterSource {

    private UserInterface ui;
    private ContainmentVessel cv;
    protected boolean isBrewing;

    public HotWaterSource() {
        isBrewing = false;
    }

    public void init(UserInterface ui, ContainmentVessel cv) {
        this.ui = ui;
        this.cv = cv;
    }

    public void start() {
        isBrewing = true;
        startBrewing();
    }

    public void done() {
        isBrewing = false;
    }

    protected void declareDone() {
        ui.done();
        cv.done();
        isBrewing = false;
    }

    public abstract void startBrewing();
    public abstract boolean isReady();
    public abstract void pause();
    public abstract void resume();

}
