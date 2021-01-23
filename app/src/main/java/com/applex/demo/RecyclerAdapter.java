package com.applex.demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Object> mList;
    Context mcontext;
    private TemplateView adView;
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    int pos =0;

//    private TagAdapter.OnClickListener mListener;
//    private TagAdapter.OnLongClickListener Listener;
//
//    public interface OnClickListener {
//        void onClickListener(int position, String tag, String color);
//    }
//
//    public void onClickListener(TagAdapter.OnClickListener listener) {
//        mListener = listener;
//    }
//
//    public interface OnLongClickListener {
//        void onLongClickListener(int position, String tag, String color);
//    }
//
//    public void onLongClickListener(TagAdapter.OnLongClickListener onLongClickListener) {
//        Listener= onLongClickListener;
//    }


    public RecyclerAdapter( Context context) {
        this.mcontext=context;
        this.mList= new ArrayList<>();
    }

    public void setData(ArrayList<String> arrayList1, ArrayList<UnifiedNativeAd> arrayList2){
        Toast.makeText(mcontext, arrayList1.size()+" " +arrayList2.size(), Toast.LENGTH_SHORT).show();

        int i = 0, j=0;
        while(i<arrayList1.size() || j<arrayList2.size()){
            if(pos%3==0){
                this.mList.add(pos, arrayList2.get(j));
                j++;
                pos++;
                Toast.makeText(mcontext, mList.size()+" " +pos, Toast.LENGTH_SHORT).show();


            }
            else{
                this.mList.add(pos, arrayList1.get(i));
                i++;
                pos++;
                Toast.makeText(mcontext, mList.size()+" kk" +pos, Toast.LENGTH_SHORT).show();

            }
        }

//        this.mList.addAll(arrayList);
    }

    public void setAds(ArrayList<UnifiedNativeAd> arrayList){
        this.mList.addAll(arrayList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        Toast.makeText(mcontext, mList.size()+" ", Toast.LENGTH_SHORT).show();

        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                View unifiedNativeLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.ad_unified,
                        viewGroup, false);
                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case MENU_ITEM_VIEW_TYPE:
                // Fall through.
            default:
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_tag,viewGroup, false);
                return new ProgrammingViewHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder programmingViewHolder, int i) {
        int viewType = getItemViewType(i);

        if (viewType == 1) {
            UnifiedNativeAdViewHolder viewHolder = (UnifiedNativeAdViewHolder) programmingViewHolder;
            viewHolder.setUnifiedNativeAd((UnifiedNativeAd) mList.get(i));
        }

        else{
            String currentItem = (String) mList.get(i);
            ProgrammingViewHolder viewHolder = (ProgrammingViewHolder) programmingViewHolder;
            viewHolder.tag.setText(currentItem);
        }



    }

    @Override
    public int getItemCount() { return mList.size();}

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = mList.get(position);
        if (recyclerViewItem instanceof UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return MENU_ITEM_VIEW_TYPE;

    }


    public class ProgrammingViewHolder extends RecyclerView.ViewHolder{

        TextView tag;

        private ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag);
        }
    }

    //ViewHolder for Native Ad Data
    class UnifiedNativeAdViewHolder extends RecyclerView.ViewHolder {

        public TemplateView getAdView() {
            return adView;
        }

        UnifiedNativeAdViewHolder(View view) {
            super(view);
            adView = view.findViewById(R.id.native_ad);


//            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
//            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
//            adView.setBodyView(adView.findViewById(R.id.ad_body));
//            adView.setCallToActionView(adView.findViewById(R.id.ad_bt_visit));
//            adView.setIconView(adView.findViewById(R.id.ad_icon));
//            adView.setPriceView(adView.findViewById(R.id.ad_price));
//            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//            adView.setStoreView(adView.findViewById(R.id.ad_store));
//            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        }

        public  void setUnifiedNativeAd(UnifiedNativeAd ads){
            adView.setNativeAd(ads);

        }
    }

    private void populateNativeAdView(UnifiedNativeAd nativeAd,
                                      UnifiedNativeAdView adView) {
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }


}



