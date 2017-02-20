package rx.learn.allen.allenlearnrx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    Integer[] items = {0, 1, 2, 3, 4};
    String[] strings = {"S", "M", "L"};
    Movie movie = new Movie("Hello Bro");
    ImageView img;
    TextView tv;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        tv = (TextView) findViewById(R.id.tv);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getANumberObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String string) {
                        Log.i("Operator thread", Thread.currentThread().getName());
                        return string;
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("Subscriber thread", Thread.currentThread().getName());
                        tv.setText(s);
                        Log.e("Subscriver thread", "not crashed");
                    }
                });

    }

    private Observable<String> getANumberObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                Log.i("Observable thread", Thread.currentThread().getName());
                return Observable.just("Hello");
            }
        });
    }

    public static void log(String content) {
        Log.d("RxJava", content);
    }
}
