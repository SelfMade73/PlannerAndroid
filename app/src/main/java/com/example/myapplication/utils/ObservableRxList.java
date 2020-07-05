package com.example.myapplication.utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ObservableRxList<T>  {
    protected final List<T> list;
    private final PublishSubject<List<T>> subject;

    public ObservableRxList (){
        this.list = new ArrayList<>();
        this.subject = PublishSubject.create();
    }

    public Observable<List<T>> getObservable(){
        return subject;
    }

    public List<T> getCurrentList(){
        return this.list;
    }

    public void add(T value ){
        list.add(value);
        subject.onNext(list);
    }

    public T get(int index){
        this.subject.onNext(list);
        return this.list.get(index);
    }

    public void setList(List<T> collection){
        this.list.clear();
        this.list.addAll(collection);
        this.subject.onNext(list);
    }

    public void addAll ( List<T> collection){
        this.list.addAll(collection);
        this.subject.onNext(list);
    }

    public void remove(int  index){
        this.list.remove(index);
        this.subject.onNext(list);
    }
    public void notifyAndUpdate(){
        this.subject.onNext(list);
    }

    public int size() {
        return this.list.size();
    }
}
