package com.caiyi.dailywork.Rx;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.telecom.Call;
import android.util.Log;

import com.squareup.okhttp.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ren ZeQiang
 * @since 2017/7/11
 */
public class ObserverHelper {

    private static final String TAG = "ObserverHelper";

    private static StringBuilder mRxOperatorsText = new StringBuilder();


    Observer<String> observer = new Observer<String>() {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Log.i(TAG, "onSubscribe");
        }

        @Override
        public void onNext(@NonNull String s) {
            Log.i(TAG, "onNext" + s);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.i(TAG, "onError!");
        }

        @Override
        public void onComplete() {
            Log.i(TAG, "onComplete");
        }
    };

    public static void aaa() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "This is result :" + integer ;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String str) throws Exception {
                Log.e(TAG, "accept: " + str + "\n");
            }
        });

    }

    public static void bbb() {
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "zip : accept : " + s + "\n");
            }
        });
    }


    private static ObservableSource<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("A");
                    Log.e(TAG, "String emit : A \n");
                    e.onNext("B");
                    Log.e(TAG, "String emit : B \n");
                    e.onNext("C");
                    Log.e(TAG, "String emit : C \n");
                }
            }
        });
    }

    private static ObservableSource<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    Log.e(TAG, "Integer emit : 1");
                    e.onNext(2);
                    Log.e(TAG, "Integer emit : 2");
                    e.onNext(3);
                    Log.e(TAG, "Integer emit : 3");
                    e.onNext(4);
                    Log.e(TAG, "Integer emit : 4");
                    e.onNext(5);
                    Log.e(TAG, "Integer emit : 5");
                }
            }
        });
    }


    public static void ccc() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1" + "\n");
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                mRxOperatorsText.append("Observable emit 2" + "\n");
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                mRxOperatorsText.append("Observable emit 3" + "\n");
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                mRxOperatorsText.append("Observable emit 4" + "\n");
                Log.e(TAG, "Observable emit 4" + "\n" );
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n" );
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                mRxOperatorsText.append("onNext : value : " + integer + "\n");
                Log.e(TAG, "onNext : value : " + integer + "\n" );
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                    mRxOperatorsText.append("onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                    Log.e(TAG, "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mRxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                mRxOperatorsText.append("onComplete" + "\n");
                Log.e(TAG, "onComplete" + "\n" );
            }
        });
    }

    public static void ddd() {
        Observable.concat(Observable.just(1,2,3), Observable.just(4,5,6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "concat: " + integer + "\n" );
                    }
                });
    }

    public static void eee() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value" + i);
                    int delayTime = (int) (1 + Math.random() * 10);
                    return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
                }
                return null;
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                    }
                });

    }

}
