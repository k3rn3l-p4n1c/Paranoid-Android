package co.rishe.paranoidandroid.mvvm.recyclerview.adapter.binder;

public interface ItemBinder<T>
{
      int getLayoutRes(T model);
      int getBindingVariable(T model);
}
