package cn.dahe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 购物卡
 * Created by fy on 2017/1/12.
 */
@Table(name = "t_shopping_card")
@Entity
public class ShoppingCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //购物卡名称
    private String name;
    //是否有使用期限 0 没有 1 有
    private int due;
    //有效天数
    private int dueDays;
    //购买商品范围
    @Column(name = "categories_ids")
    private String categoriesIds;
    //所属分店
    @Column(name = "store_id")
    private int storeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDue() {
        return due;
    }

    public void setDue(int due) {
        this.due = due;
    }

    public int getDueDays() {
        return dueDays;
    }

    public void setDueDays(int dueDays) {
        this.dueDays = dueDays;
    }

    public String getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(String categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
