package co.rishe.paranoidandroid;

import java.util.Observable;

/**
 * Lazy Linkage policy will fetch data first time you need
 */
public class LazyLinkage implements LinkagePolicy {
    private boolean isValid;

    @Override
    public boolean valid() {
        return isValid;
    }


    @Override
    public void start(Linkage linkage) {
        this.isValid = false;
    }

    @Override
    public void update(Observable observable, Object o) {
        isValid = true;
    }
}
