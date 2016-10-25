package com.thaile.project_cookinghandbook.Adapter;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentDoUong;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMeVaBe;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMeoNhaBep;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonAnVat;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonBanh;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonChay;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonDiemTam;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonHangNgay;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonTruyenThong;
import com.thaile.project_cookinghandbook.R;


/**
 * Created by Thai Le on 9/12/2016.
 */
public class MyFragPagerAdapter extends FragmentStatePagerAdapter{
    private static final int NUM_PRO = 9;
    private Fragment f = null;
    public MyFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTabLayout(TabLayout tab){
        tab.getTabAt(0).setIcon(R.mipmap.ic_chef);
        tab.getTabAt(1).setIcon(R.mipmap.ic_recipe);
        tab.getTabAt(2).setIcon(R.mipmap.ic_veget);
        tab.getTabAt(3).setIcon(R.mipmap.ic_traditional);
        tab.getTabAt(4).setIcon(R.mipmap.ic_cookie);
        tab.getTabAt(5).setIcon(R.mipmap.ic_drink);
        tab.getTabAt(6).setIcon(R.mipmap.ic_foodchildren);
        tab.getTabAt(7).setIcon(R.mipmap.ic_homemade);
        tab.getTabAt(8).setIcon(R.mipmap.ic_recipe);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Món Ăn Hàng Ngày";
        } else if (position == 1){
            return "Món Ăn Điểm Tâm";
        } else if (position == 2){
            return "Món Ăn Chay";
        } else if (position == 3){
            return "Món Ăn Truyền Thống";
        } else if (position == 4){
            return "Các Món Bánh";
        } else if (position == 5){
            return "Đồ Uống";
        } else if (position == 6){
            return "Món Ăn Cho Mẹ Và Bé";
        } else if (position == 7){
            return "Món Ăn Vặt";
        } else if (position == 8){
            return "Mẹo Hay Nhà Bếp";
        }
        return super.getPageTitle(position);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            f = new FragmentMonHangNgay();
        } else if (position == 1){
            f = new FragmentMonDiemTam();
        } else if (position == 2){
            f = new FragmentMonChay();
        } else if (position == 3){
            f = new FragmentMonTruyenThong();
        } else if (position == 4){
            f = new FragmentMonBanh();
        } else if (position == 5){
            f = new FragmentDoUong();
        } else if (position == 6){
            f = new FragmentMeVaBe();
        } else if (position == 7){
            f = new FragmentMonAnVat();
        } else if (position == 8){
            f = new FragmentMeoNhaBep();
        }
        return f;
    }

    public Fragment getF() {
        return f;
    }


    @Override
    public int getCount() {
        return NUM_PRO;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
