package com.tsarcevic.weatherappjava.base;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    protected BaseViewModel(){
        compositeDisposable = new CompositeDisposable();
    }

    protected void addSubscription(Disposable subscription){
        compositeDisposable.add(subscription);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
