package com.example.myapplication.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;
import com.example.myapplication.models.InstructionItem;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<InstructionItem> introScreensList;

    public IntroViewPagerAdapter(Context context, List<InstructionItem> introScreensList){
        this.context = context;
        this.introScreensList = introScreensList;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen       = inflater.inflate(R.layout.instruction_screen_layout,null);

        ImageView imgView       = layoutScreen.findViewById(R.id.intro_animation);
        TextView introTitle     = layoutScreen.findViewById(R.id.intro_title);
        TextView introSubTitle  = layoutScreen.findViewById(R.id.intro_subtitle);

        introTitle.setText(introScreensList.get(position).getInstructionTitle());
        introSubTitle.setText(introScreensList.get(position).getInstructionDescription());
        imgView.setImageResource(introScreensList.get(position).getIntroImg());


        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return introScreensList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
