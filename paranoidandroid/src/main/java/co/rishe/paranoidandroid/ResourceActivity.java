package co.rishe.paranoidandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;


public abstract class ResourceActivity<ModelView extends ViewModel, DataBinding extends ViewDataBinding> extends AppCompatActivity {

    private int layout;

    protected ModelView modelView;
    protected Class<ModelView> modelViewClass;
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
            modelViewClass = (Class<ModelView>) ta.view_model();
            dataBindingClass = (Class<DataBinding>) ta.data_binding();

        } else {
            throw new ExceptionInInitializerError("Annotate me");
        }
        dataBinding = DataBindingUtil.setContentView(this, layout);
        try {
            modelView = modelViewClass.getConstructor(ResourceActivity.class).newInstance(this);
            dataBindingClass.getMethod("setViewModel", modelViewClass).invoke(dataBinding, modelView);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modelView.destroy();
    }

    public abstract void onCompleted();
}
