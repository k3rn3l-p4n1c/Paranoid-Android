package co.rishe.paranoidandroid;

import android.databinding.ViewDataBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Bardia on 12/17/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceObserver {
    int layout();
    Class<? extends ViewModel> view_model();
    Class<? extends ViewDataBinding> data_binding();
}
