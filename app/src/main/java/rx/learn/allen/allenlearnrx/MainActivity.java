package rx.learn.allen.allenlearnrx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;


public class MainActivity extends AppCompatActivity {
    Integer[] items = {0, 1, 2, 3, 4};
    String[] strings = {"S", "M", "L"};
    Movie movie = new Movie("Hello Bro");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tryFrom();
//        tryJust();
//        tryDefer();
//        tryInterval();
        tryCreate();
    }


    private void tryFrom() {
        Observable.from(items).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("OnCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("On Error");
            }

            @Override
            public void onNext(Integer integer) {
                log("onNext: " + String.valueOf(integer));
//                if (integer == 3) unsubscribe();
            }
        });
    }


    private void tryJust() {
        Observable.just("S", "M", "L").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("OnCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("On Error");
            }

            @Override
            public void onNext(String string) {
                log("onNext: " + string);
            }
        });
//        Observable.just(strings).subscribe(new Subscriber<String[]>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String[] strings) {
//                log("on Next");
//            }
//        });
    }

    public void tryDefer() {
        Observable<Movie> observable = Observable.just(movie);
        Observable<Movie> observable1 = Observable.defer(new Func0<Observable<Movie>>() {
            @Override
            public Observable<Movie> call() {
                return Observable.just(movie);
            }
        });
        movie = new Movie("Hello SML");
        observable.subscribe(new Subscriber<Movie>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Movie movie1) {
                log(movie1.getTitle());
            }
        });
    }

    public void tryInterval() {
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                log(String.valueOf(aLong));
                if (aLong == 10) unsubscribe();
            }
        });
    }

    private void tryCreate() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("S");
                subscriber.onNext("M");
                subscriber.onNext("L");
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("On Complete");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                log("On next: "+ s);
            }
        });
    }

    public static void log(String content) {
        Log.d("RxJava", content);
    }
}
