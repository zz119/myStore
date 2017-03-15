package cn.dahe.model;

import javax.persistence.*;

/**
 * Created by 冯源 on 2017/3/16.
 */
@Table(name = "t_client_order_time")
@Entity
public class ClientOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //所属订单
    @Column(name = "client_order_id")
    private int clientOrderId;
    //预定数量
    @Column(name = "order_num")
    private int orderNum;
    //商品编码
    @Column(name = "goods_no")
    private String goodsNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(int clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }
}