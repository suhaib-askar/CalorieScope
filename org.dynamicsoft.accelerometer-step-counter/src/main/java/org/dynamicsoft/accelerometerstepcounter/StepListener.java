package org.dynamicsoft.accelerometerstepcounter;

/**
 * Created by karan on 9/3/18.
 */

//Will listen to step alerts
public interface StepListener {
    public void step(long timeNs);
}