package test;

import com.guowei.lv.M4ContainmentVessel;
import com.guowei.lv.M4HotWaterSource;
import com.guowei.lv.M4UserInterface;
import org.junit.Before;
import org.junit.Test;

public class TestCoffeeMaker {

    private M4UserInterface ui;
    private M4HotWaterSource hws;
    private M4ContainmentVessel cv;
    private CoffeeMakerStud api;

    @Before
    public void setup() {
        api = new CoffeeMakerStud();
        ui = new M4UserInterface(api);
        hws = new M4HotWaterSource(api);
        cv = new M4ContainmentVessel(api);
        ui.init(hws, cv);
        hws.init(ui, cv);
        cv.init(ui, hws);
    }

    private void poll() {
        ui.poll();
        hws.poll();
        cv.poll();
    }

    @Test
    public void testInitialConditions() throws Exception {
        poll();
        assert(!api.boilerOn);
        assert(!api.lightOn);
        assert (!api.plateOn);
        assert(api.valveClosed);
    }

    @Test
    public void testStartNoPot() throws Exception {
        poll();
        api.buttonPressed = true;
        api.potPresent = false;
        poll();
        assert(!api.boilerOn);
        assert(!api.lightOn);
        assert (!api.plateOn);
        assert(api.valveClosed);
    }

    @Test
    public void testStartNoWater() throws Exception {
        poll();
        api.buttonPressed = true;
        api.boilerEmpty = true;
        poll();
        assert(!api.boilerOn);
        assert(!api.lightOn);
        assert(!api.plateOn);
        assert(api.valveClosed);
    }

    @Test
    public void testGoodStart() throws Exception {
        normalStart();
        assert(api.boilerOn);
        assert(!api.lightOn);
        assert(!api.plateOn);
        assert(api.valveClosed);
    }

    private void normalStart() {
        poll();
        api.boilerEmpty = false;
        api.buttonPressed = true;
        poll();
    }

    @Test
    public void testStartedPotNotEmpty() throws Exception {
        normalStart();
        api.potNotEmpty = true;
        poll();
        assert(api.boilerOn);
        assert(!api.lightOn);
        assert(api.plateOn);
        assert(api.valveClosed);
    }

    @Test
    public void testPotRemovedAndReplacedWhileEmpty() throws Exception {
        normalStart();
        api.potPresent = false;
        poll();
        assert(!api.boilerOn);
        assert(!api.lightOn);
        assert(!api.plateOn);
        assert(!api.valveClosed);
        api.potPresent = true;
        poll();
        assert(api.boilerOn);
        assert(!api.lightOn);
        assert(!api.plateOn);
        assert(api.valveClosed);
    }

    @Test
    public void testPotRemovedWhileNotEmptyAndReplacedEmpty() throws Exception {
        normalStart();
        api.potPresent = false;
        poll();
        assert(!api.boilerOn);
        assert(!api.lightOn);
        assert(!api.plateOn);
        assert(!api.valveClosed);
        api.potPresent = true;
        api.potNotEmpty = false;
        poll();
        assert(api.boilerOn);
        assert(!api.lightOn);
        assert(!api.plateOn);
        assert(api.valveClosed);
    }

    @Test
    public void testPotRemovedWhileNotEmptyAndReplaceNotEmpty() throws Exception {
        normalFill();
        api.potPresent = false;
        poll();
        api.potPresent = true;
        poll();
        assert(api.boilerOn);
        assert(!api.lightOn);
        assert(api.plateOn);
        assert(api.valveClosed);
    }

    private void normalFill() {
        normalStart();
        api.potNotEmpty = true;
        poll();
    }

    @Test
    public void testBoilerEmptyPotNotEmpty() throws Exception {
        normalBrew();
        assert(!api.boilerOn);
        assert(api.lightOn);
        assert(api.plateOn);
        assert(api.valveClosed);
    }

    private void normalBrew() {
        normalFill();
        api.boilerEmpty = true;
        poll();
    }

    @Test
    public void testBoilerEmptiesWhilePotRemoved() throws Exception {
        normalFill();
        api.potPresent = false;
        poll();
        api.boilerEmpty = true;
        poll();
        assert(!api.boilerOn);
        assert(api.lightOn);
        assert(!api.plateOn);
        assert(api.valveClosed);
        api.potPresent = true;
        poll();
        assert(!api.boilerOn);
        assert(api.lightOn);
        assert(api.plateOn);
        assert(api.valveClosed);
    }

    @Test
    public void testEmptyPotReturnedAfter() throws Exception {
        normalBrew();
        api.potNotEmpty = false;
        poll();
        assert(!api.boilerOn);
        assert(!api.lightOn);
        assert(!api.plateOn);
        assert(api.valveClosed);
    }
}
