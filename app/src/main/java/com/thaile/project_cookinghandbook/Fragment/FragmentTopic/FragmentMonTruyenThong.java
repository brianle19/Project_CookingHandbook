package com.thaile.project_cookinghandbook.Fragment.FragmentTopic;

import com.thaile.project_cookinghandbook.Adapter.RecyclerViewAdapter;
import com.thaile.project_cookinghandbook.Fragment.FragmentBase;

/**
 * Created by Thai Le on 9/12/2016.
 */
public class FragmentMonTruyenThong extends FragmentBase {
    public static final String ROOT_NAME = "MonAnTruyenThong";
    @Override
    public void initData(String tableName) {
        tableName = ROOT_NAME;
        super.initData(tableName);
    }

    @Override
    public RecyclerViewAdapter getAdapter() {
        return super.getAdapter();
    }

}
