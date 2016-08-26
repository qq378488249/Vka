package cc.chenghong.vka.entity;


import java.util.ArrayList;
import java.util.List;

import cc.chenghong.vka.adapter.LvAdapter;


/** 分页工具类
 * Created by hcl on 2016/3/16.
 */

public class Page<T> {
    public List<T> list = new ArrayList<T>();//数据集合
    public LvAdapter<T> adapter;//适配器
    public int pageIndex;//页数
    public int limit;//每页个数

    public Page(int pageIndex, int limit) {
        this.pageIndex = pageIndex;
        this.limit = limit;
    }

    /**
     * 默认第零页，一页10条数据
     */
    public Page() {
        this.pageIndex = 0;
        this.limit = 10;
    }
    /**
     * 默认第零页，一页10条数据
     */
    public Page(int limit) {
        this.pageIndex = 0;
        this.limit = limit;
    }

    /**
     * 下一页
     */
    public int nextPage(){
        return pageIndex++;
    }

    /**
     * 第一页
     * @return
     */
    public int firstPage(){
        return pageIndex=0;
    }

    /**
     * 是否是第一页
     * @return
     */
    public boolean isFirstPage(){
        return pageIndex == 0;
    }

    public int getLimit() {
        return limit;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public static Page New() {
        return new Page();
    }

    /**
     * 刷新适配器
     */
    public void notifyDataSetChanged(){
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    public int size() {
        if (list != null) {
            return list.size();
        }
        return -1;
    }

    /**
     * 添加一个列表数据
     * @param t
     */
    public void add(T t){
        adapter.add(t);
    }

    /**
     * 清除所有列表数据
     */
    public void clear(){
        adapter.clear();
    }

    /**
     * 显示新的列表
     * @param newList
     */
    public void showNewList(List<T> newList){
        adapter.showNewList(newList);
    }

    /**
     * 添加列表
     * @param list
     */
    public void addAll(List<T> list){
        adapter.addAll(list);
    }

    public T get(int index){
        return  list.get(index);
    }

}
