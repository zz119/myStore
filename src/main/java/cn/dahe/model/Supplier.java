package cn.dahe.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 供货商
 * Created by fy on 2016/12/29.
 */
@Table(name = "t_supplier")
@Entity
public class Supplier {
    @Id
    @GeneratedValue
    private int id;
    //是否启用 0 禁用  1 启用
    private int status;
    //供货商名称
    private String name;
    //联系人
    private String contacts;
    //联系电话
    private String phone;
    //联系邮箱
    private String email;
    //供货商编号
    @Column(name = "supplier_no")
    private String supplierNo;
    //配送费返点
    @Column(name = "packing_fee_point")
    private String packingFeePoint;
    //固定返利点
    @Column(name = "rebate_point")
    private String rebatePoint;
    //地址
    private String addr;
    //备注
    private String description;
    //是否授权  0  没有授权  1  授权
    private int isAuthorize;
    //供货商对应的商品
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Goods> goods = new HashSet<>();
    //拼音
    private String pinyin;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getPackingFeePoint() {
        return packingFeePoint;
    }

    public void setPackingFeePoint(String packingFeePoint) {
        this.packingFeePoint = packingFeePoint;
    }

    public String getRebatePoint() {
        return rebatePoint;
    }

    public void setRebatePoint(String rebatePoint) {
        this.rebatePoint = rebatePoint;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsAuthorize() {
        return isAuthorize;
    }

    public void setIsAuthorize(int isAuthorize) {
        this.isAuthorize = isAuthorize;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
