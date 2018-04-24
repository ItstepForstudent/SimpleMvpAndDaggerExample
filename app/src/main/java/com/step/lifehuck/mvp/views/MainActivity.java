package com.step.lifehuck.mvp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.step.lifehuck.R;
import com.step.lifehuck.di.BaseApp;
import com.step.lifehuck.entities.Good;
import com.step.lifehuck.mvp.contracts.MainContract;
import com.step.lifehuck.utils.adapters.GoodsRVAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements MainContract.view {
    @Inject MainContract.presenter presenter;
    RecyclerView goodsRecyclerView;
    GoodsRVAdapter goodsRVAdapter;
    ProgressBar progressBar;
    Button upadateBtn;


    void initRecycler(){
        goodsRVAdapter = new GoodsRVAdapter();
        goodsRecyclerView = findViewById(R.id.rvLifeHucks);
        goodsRecyclerView.setAdapter(goodsRVAdapter);
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApp.get(this).getInjector().inject(this);
        upadateBtn = findViewById(R.id.btnUpd);

        progressBar = findViewById(R.id.indicator);
        progressBar.setVisibility(View.GONE);

        initRecycler();


        presenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
    }

    @Override
    public void showGoods(List<Good> goods) {
        goodsRVAdapter.setGoods(goods);
    }

    @Override
    public void showIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        goodsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideIndicetor() {
        progressBar.setVisibility(View.GONE);
        goodsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public Observable<Integer> onUpdateClick() {
        PublishSubject<Integer> subject = PublishSubject.create();
        upadateBtn.setOnClickListener(view->subject.onNext(view.getId()));
        return subject;
    }

    @Override
    public Observable<String> onSelectView() {
        return goodsRVAdapter.onItemClick();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
