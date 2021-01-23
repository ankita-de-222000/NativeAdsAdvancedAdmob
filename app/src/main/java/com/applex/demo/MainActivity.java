package com.applex.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> list;
    ArrayList<UnifiedNativeAd> ads;
    RecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        createlist();
        createAdsList();


//
//        AdLoader adLoader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-3940256099942544/2247696110")
//                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//                    @Override
//                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                        // Show the ad.
//                        Toast.makeText(getApplicationContext(), "Ad loaded", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .withAdListener(new AdListener() {
//                    @Override
//                    public void onAdFailedToLoad(LoadAdError adError) {
//                        // Handle the failure by logging, altering the UI, and so on.
//                        Toast.makeText(getApplicationContext(), adError.toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//                .withNativeAdOptions(new NativeAdOptions.Builder()
//                        // Methods in the NativeAdOptions.Builder class can be
//                        // used here to specify individual options settings.
//                        .build())
//                .build();
//        adLoader.loadAd(new AdRequest.Builder().build());



    }

    private  void createlist(){
        list = new ArrayList<>();
        list.add("Element 1");
        list.add("Element 2");
        list.add("Element 3");
        list.add("Element 4");
        list.add("Element 5");
        list.add("Element 6");
    }

    private  void createAdsList(){
        ads = new ArrayList<>();
        AdsManager adsManager = new AdsManager(MainActivity.this);

        adsManager.createUnifiedAds(2, "ca-app-pub-3940256099942544/2247696110", new AdUnifiedListening() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                ads.add(unifiedNativeAd);

                if(!getAdLoader().isLoading()){

                    linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    adapter  = new RecyclerAdapter(MainActivity.this);
                    adapter.setData(list, ads);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
//                    adapter.setAds(ads);
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    Toast.makeText(getApplicationContext(), "ads set"+ ads.get(1).getBody().toString() , Toast.LENGTH_SHORT).show();


                }
            }
        });

    }
}