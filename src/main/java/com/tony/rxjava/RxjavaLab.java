package com.tony.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxjavaLab {
    public static void main(String[] args) {

        //Observable.just("Hello World!").subscribe(System.out::println);

        System.out.println( Thread.currentThread());

        Observable.just("Hello World!!!")
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println(Thread.currentThread() + " onSubscribe() - subscribe on ");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(Thread.currentThread() + " onNext() - " + s + " ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError() - " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(Thread.currentThread() + " onComplete() - ");
                    }
                });

        System.out.println("=================Middle===================");

/*        Observable.just(null)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("subscribe on " + Thread.currentThread());
                    }

                    @Override
                    public void onNext(Object s) {
                        System.out.println(s + " " + Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage() + Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete() " + Thread.currentThread());
                    }
                });*/
    }
}
