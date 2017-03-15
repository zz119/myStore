package cn.dahe.service;

import cn.dahe.dto.ClientOrderDto;
import cn.dahe.dto.Pager;
import cn.dahe.model.ClientOrder;

import java.util.List;

/**
 * Created by 冯源 on 2017/3/16.
 */
public interface IClientOrderService {
    int add(ClientOrder clientOrder);
    void add(ClientOrderDto t);
    void del(int id);
    void update(ClientOrder t);
    ClientOrder get(int id);
    ClientOrder load(int id);

    /**
     * 根据参数查询
     * @param aDataSet
     * @param storeId
     * @return
     */
    Pager<ClientOrder> findByParams(String aDataSet, int storeId);

    /**
     * 根据vip编号查询
     * @param vipNo
     * @return
     */
    List<ClientOrder> findByVipNo(String vipNo);

    ClientOrder findByClientOrderNo(String clientOrderNo);

    List<ClientOrder> findByStoreId(int storeId);
}