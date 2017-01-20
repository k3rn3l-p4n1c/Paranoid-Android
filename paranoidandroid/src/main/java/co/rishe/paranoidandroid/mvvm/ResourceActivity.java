package co.rishe.paranoidandroid.mvvm;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;


public abstract class ResourceActivity<ViewModel extends co.rishe.paranoidandroid.mvvm.ViewModel, DataBinding extends ViewDataBinding> extends AppCompatActivity {

    private int layout;

    protected ViewModel viewModel;
    protected Class<ViewModel> modelViewClass;
    protected DataBinding dataBinding;
    protected Class<DataBinding> dataBindingClass;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("#############\n#############\n#############\n#############\n#############\n#############\n");

        if (this.getClass().isAnnotationPresent(ResourceObserver.class))
        {
            ResourceObserver ta = this.getClass().getAnnotation(ResourceObserver.class);
            layout = ta.layout();
            modelViewClass = (Class<ViewModel>) ta.view_model();
            dataBindingClass = (Class<DataBinding>) ta.data_binding();

        } else {
            throw new ExceptionInInitializerError("Annotate me");
        }
        dataBinding = DataBindingUtil.setContentView(this, layout);
        try {
            viewModel = modelViewClass.getConstructor(ResourceActivity.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            dataBindingClass.getMethod("setViewModel", modelViewClass).invoke(dataBinding, viewModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    public abstract void onCompleted();
}
