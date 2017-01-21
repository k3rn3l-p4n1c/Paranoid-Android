package co.rishe.paranoidandroid.linkage;

import android.util.Log;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Update data each 5 seconds
 */
public class PeriodicLinkage implements LinkagePolicy {
    private boolean isValid;

    @Override
    public boolean valid() {
        return isValid;
    }


    @Override
    public void start(final Linkage linkage) {
        this.isValid = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                linkage.fetch();
            }

        }, 0, 5000);
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.e("PeriodicLinage", "UPDATE!!!!!!!!!");
        isValid = true;
    }
}
