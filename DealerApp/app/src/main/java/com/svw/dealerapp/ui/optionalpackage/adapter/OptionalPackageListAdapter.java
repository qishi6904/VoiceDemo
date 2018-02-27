package com.svw.dealerapp.ui.optionalpackage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.OptionalPackageListItem;
import com.svw.dealerapp.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 11/30/2017.
 */

public class OptionalPackageListAdapter extends BaseRecyclerViewAdapter<OptionalPackageEntity.OptionListBean> {

    private ArrayList<OptionalPackageEntity.OptionListBean> selectData = new ArrayList<>();

    public OptionalPackageListAdapter(Context context, List<OptionalPackageEntity.OptionListBean> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_optional_package_list, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder viewHolder = (Holder) holder;
        final OptionalPackageEntity.OptionListBean itemEntity = dataList.get(position);

        if(null != itemEntity.getList() && itemEntity.getList().size() > 0) {
            viewHolder.optionalPackageView.showOptionalItem();
        }else {
            viewHolder.optionalPackageView.hideOptionalItem();
        }
        if(null!=itemEntity&&null!=itemEntity.getList()){
            int optionChildCount = Math.max(itemEntity.getList().size(), viewHolder.optionalPackageView.getOptionChildCount());
            for(int i = 0; i < optionChildCount; i++) {
                if(i < itemEntity.getList().size()) {
                    viewHolder.optionalPackageView.addOptionalItem(i, itemEntity.getList().get(i).getOptionNameCn(),
                            itemEntity.getList().get(i).isSelect());
                }else {
                    viewHolder.optionalPackageView.hideOptionChildView(i);
                }
            }
        }
        viewHolder.optionalPackageView.setSelect(itemEntity.isSelect());
        viewHolder.optionalPackageView.setItemText(itemEntity.getOptionNameCn());

        if(position == 0){
            viewHolder.itemView.setPadding(DensityUtil.dp2px(context, 32), DensityUtil.dp2px(context, 20),
                    DensityUtil.dp2px(context, 32), DensityUtil.dp2px(context, 10));
        }else if (position == dataList.size() - 1){
            viewHolder.itemView.setPadding(DensityUtil.dp2px(context, 32), DensityUtil.dp2px(context, 10),
                    DensityUtil.dp2px(context, 32), DensityUtil.dp2px(context, 20));
        }else {
            viewHolder.itemView.setPadding(DensityUtil.dp2px(context, 32), DensityUtil.dp2px(context, 10),
                    DensityUtil.dp2px(context, 32), DensityUtil.dp2px(context, 10));
        }

        //父类点击事件
        viewHolder.optionalPackageView.setOnItemSelectListener(new OptionalPackageListItem.OnItemSelectListener() {
            @Override
            public void onOptionalItemSelect(boolean isSelect) {
                itemEntity.setSelect(isSelect);
                if(isSelect){ // 选中选装包父类，克隆一个对应的对象，清除列表，添加到选中的集合中
                    OptionalPackageEntity.OptionListBean optionListBean = OptionalPackageEntity.OptionListBean.cloneWithoutList(itemEntity);
                    optionListBean.getList().clear();
                    selectData.add(optionListBean);
                }else { // 取消选中选装包父类，从选中的集合中删除
                    removeByCode(itemEntity.getOptionCode());
                    for (int i = 0; i < itemEntity.getList().size(); i++) {
                        itemEntity.getList().get(i).setSelect(false);
                    }
                }

            }
        });

        //子类点击事件
        viewHolder.optionalPackageView.setOnOptionalSelectListener(new OptionalPackageListItem.OnOptionalSelectListener() {
            @Override
            public void onOptionalItemSelect(int index, boolean isSelect) {
                if(isSelect) {
                    addSelectOptional(itemEntity, index);
                    itemEntity.setSelect(isSelect);
                }else {
                    removeOptional(itemEntity, index);
                }
                for (int i = 0; i < itemEntity.getList().size(); i++) {
                    itemEntity.getList().get(i).setSelect(false);
                }
                itemEntity.getList().get(index).setSelect(isSelect);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        View itemView;
        OptionalPackageListItem optionalPackageView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            optionalPackageView = (OptionalPackageListItem) itemView.findViewById(R.id.opli);
        }
    }

    /**
     * 从已选择的集中删除
     * @param code
     */
    private void removeByCode(String code){
        for(int i = 0; i < selectData.size(); i++) {
            if(!TextUtils.isEmpty(code) && code.equals(selectData.get(i).getOptionCode())){
                selectData.remove(i);
                break;
            }
        }
    }

    /**
     * 生成一个选中的选装包对象
     * @param optionListBean
     * @return
     */
    private OptionalPackageEntity.OptionListBean generateSelectEntity(OptionalPackageEntity.OptionListBean optionListBean)  {
        OptionalPackageEntity.OptionListBean selectEntity = null;
        for(int i = 0; i < selectData.size(); i++) {
            if(!TextUtils.isEmpty(optionListBean.getOptionCode()) &&
                    optionListBean.getOptionCode().equals(selectData.get(i).getOptionCode())) {
                selectEntity = selectData.get(i);
                break;
            }
        }
        if(null == selectEntity) {
            selectEntity = OptionalPackageEntity.OptionListBean.cloneWithoutList(optionListBean);
            selectData.add(selectEntity);
        }
        return selectEntity;
    }

    /**
     * 添加子类选装包
     * @param optionListBean
     * @param selectIndex
     */
    private void addSelectOptional(OptionalPackageEntity.OptionListBean optionListBean, int selectIndex) {
        OptionalPackageEntity.OptionListBean selectEntity = generateSelectEntity(optionListBean);
        if(null != selectEntity) {
            selectEntity.getList().clear();
            selectEntity.getList().add(optionListBean.getList().get(selectIndex));
        }
    }

    /**
     * 删除子类选装包
     * @param optionListBean
     * @param selectIndex
     */
    private void removeOptional(OptionalPackageEntity.OptionListBean optionListBean, int selectIndex) {
        OptionalPackageEntity.OptionListBean selectEntity = generateSelectEntity(optionListBean);
        if(null != selectEntity) {
            selectEntity.getList().clear();
        }
    }

    /**
     * 获取选中的数据
     * @return
     */
    public ArrayList<OptionalPackageEntity.OptionListBean> getSelectData(){
        return selectData;
    }

    /**
     * 添加上次选中的数据
     * @param selectList
     */
    public void addDefaultSelectData(List<OptionalPackageEntity.OptionListBean> selectList){
        selectData.addAll(selectList);
    }
}
