package com.step.lifehuck.mvp.models;

import android.util.Log;

import com.step.lifehuck.entities.Good;
import com.step.lifehuck.mvp.contracts.MainContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mamedov on 02.04.2018.
 */

public class MainModel implements MainContract.model {

    public Observable<List<Good>> getGoods() {
        return Observable.fromCallable(() -> {
            List<Good> goods = new LinkedList<>();
            try {
                Document doc = Jsoup.connect("http://price.ua/skidki-dnepropetrovsk-t2.html").get();
                Elements goodContainers = doc.select(".sale-l-i");
                for (Element goodContainer : goodContainers) {
                    String imgSrc = goodContainer.selectFirst(".sales-product-info-img-block img").attr("src");
                    String name = goodContainer.selectFirst(".sale-title a").html();
                    String desc = goodContainer.selectFirst(".sales-characteristics").text();
                    String price;
                    try {
                     price    = goodContainer.selectFirst(".sales-product-price").childNode(0).toString().replace("&nbsp;","").trim();
                    }catch (Exception e){
                        price="0";
                    }
                    String link = goodContainer.selectFirst(".active-go").attr("href");
                    goods.add(new Good(imgSrc, name, Double.parseDouble(price), desc, link));
                }
            } catch (Exception e) {
                Log.e("__","ERROR",e);
            }
            return goods;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
