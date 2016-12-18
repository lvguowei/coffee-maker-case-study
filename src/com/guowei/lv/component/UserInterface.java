package com.guowei.lv.component;

public abstract class UserInterface {

    private HotWaterSource hws;
    private ContainmentVessel cv;
    protected boolean isCompleted;

    public UserInterface() {
        isCompleted = true;
    }

    public void init(HotWaterSource hws, ContainmentVessel cv) {
        this.hws = hws;
        this.cv = cv;
    }

    protected void startBrewing() {
        if (hws.isReady() && cv.isReady()) {
            isCompleted = false;
            hws.start();
            cv.start();
        }
    }

    public void complete() {
        isCompleted = true;
        completeCycle();
    }

    public abstract void done();
    public abstract void completeCycle();


}
