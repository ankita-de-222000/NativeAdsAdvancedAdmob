package com.applex.demo;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;

public class AdsManager {
    Context context;

    public AdsManager(Context context) {
        this.context =context;
    }

    public void createUnifiedAds(int num, String unitID, AdUnifiedListening listening){

        AdLoader.Builder builder = new AdLoader.Builder(context, unitID);

        builder.forUnifiedNativeAd(listening);
        builder.withAdListener(listening);
        AdLoader adLoad = builder.build();
        adLoad.loadAds(new AdRequest.Builder().build(), num);
        listening.setAdLoader(adLoad);
    }
}
