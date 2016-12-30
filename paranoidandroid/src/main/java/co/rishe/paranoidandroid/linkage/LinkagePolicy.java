package co.rishe.paranoidandroid.linkage;

import java.util.Observer;

/**
 * Linkage Policy is strategy class to say when linked data is valid.
 * It also can ask for data
 */
public interface LinkagePolicy extends Observer {
    boolean valid();
    void start(Linkage linkage);
}
