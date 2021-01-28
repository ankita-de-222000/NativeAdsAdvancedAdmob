package com.applex.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> list;
    ArrayList<UnifiedNativeAd> ads;
    RecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    private Button refreshButton;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        recyclerView = findViewById(R.id.recycler);

//        createlist();
//        createAdsList();

        MobileAds.initialize(this, initializationStatus -> {

        });

        refreshButton = findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(view -> {
            refreshAd();
        });

        refreshAd();


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

    @Override
    protected void onDestroy() {
        if(nativeAd != null)
            nativeAd.destroy();
        super.onDestroy();
    }

    private void refreshAd() {
        refreshButton.setEnabled(false);

        AdLoader.Builder builder = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110");
        builder.forNativeAd(Ad -> {
//            if(nativeAd != null)
            nativeAd = Ad;
            CardView cardView = findViewById(R.id.ad_container);
            NativeAdView nativeAdView = (NativeAdView) getLayoutInflater().inflate(R.layout.native_ad_layout, null);
            cardView.removeAllViews();
            cardView.addView(nativeAdView);
            populateAd(nativeAd, nativeAdView);
            refreshButton.setEnabled(true);
        });

        AdLoader adLoader = builder.withAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Toast.makeText(MainActivity.this, "Failed to load Ad", Toast.LENGTH_SHORT).show();
                refreshButton.setEnabled(true);
                super.onAdFailedToLoad(loadAdError);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }

    private void populateAd(NativeAd nativeAd, NativeAdView adView) {

        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setStarRatingView(adView.findViewById(R.id.rating_bar));
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setCallToActionView(adView.findViewById(R.id.ad_action_button));
        adView.setIconView(adView.findViewById(R.id.ad_icon));

        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        ((TextView)adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if(nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView)adView.getBodyView()).setText(nativeAd.getBody());
            adView.getBodyView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView)adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar)adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView)adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            ((Button)adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            adView.getBodyView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
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