package com.example.rxandroid_rxjava_tutorial.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rxandroid_rxjava_tutorial.R;
import com.example.rxandroid_rxjava_tutorial.adapter.DataAdapter;
import com.example.rxandroid_rxjava_tutorial.api.ApiService;
import com.example.rxandroid_rxjava_tutorial.model.ObjectData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnCallApi;
    private RecyclerView rcvData;

    private DataAdapter mDataAdapter;
    private List<ObjectData> mListData;

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvData.addItemDecoration(dividerItemDecoration);

        mListData = new ArrayList<>();

        btnCallApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService.apiService.callApi(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<ObjectData>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                mDisposable = d;
                            }

                            @Override
                            public void onNext(@NonNull List<ObjectData> objectData) {
                                mListData = objectData;
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Toast.makeText(MainActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(MainActivity.this, "Call Api Success", Toast.LENGTH_SHORT).show();
                                mDataAdapter = new DataAdapter(mListData);
                                rcvData.setAdapter(mDataAdapter);
                            }
                        });
            }
        });
    }

//    private void onClickCallApi() {
//        mDataAdapter = new DataAdapter(getListData());
//        rcvData.setAdapter(mDataAdapter);
//    }
//
//    private List<ObjectData> getListData() {
//        List<ObjectData> list = new ArrayList<>();
//
//        list.add(new ObjectData(1, "Name 1", 10));
//
//        return list;
//    }

    private void mapping() {
        btnCallApi = findViewById(R.id.home_btn_call_api);
        rcvData = findViewById(R.id.home_rcv_data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}