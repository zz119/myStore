package cn.dahe.service.impl;

import cn.dahe.dao.ICategoriesDao;
import cn.dahe.dao.IGoodsDao;
import cn.dahe.dao.IGoodsTagsDao;
import cn.dahe.dao.IGoodsUnitDao;
import cn.dahe.dao.ISmallTicketDao;
import cn.dahe.dao.IStockDao;
import cn.dahe.dao.IStoreDao;
import cn.dahe.dao.ISupplierDao;
import cn.dahe.dto.GoodsDto;
import cn.dahe.dto.GoodsDtoSimple;
import cn.dahe.dto.Pager;
import cn.dahe.model.BaseException;
import cn.dahe.model.Categories;
import cn.dahe.model.Goods;
import cn.dahe.model.GoodsTags;
import cn.dahe.model.GoodsUnit;
import cn.dahe.model.SmallTicket;
import cn.dahe.model.Stock;
import cn.dahe.model.Supplier;
import cn.dahe.service.IGoodsService;
import cn.dahe.util.DateUtil;
import cn.dahe.util.PoiUtils;
import cn.dahe.util.ResourcesUtils;
import cn.dahe.util.StringUtil;
import cn.dahe.util.UploadsUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by fy on 2017/1/13.
 */
@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService{
    private static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Resource
    private IGoodsDao goodsDao;
    @Resource
    private IStoreDao storeDao;
    @Resource
    private ICategoriesDao categoriesDao;
    @Resource
    private IGoodsUnitDao goodsUnitDao;
    @Resource
    private IStockDao stockDao;
    @Resource
    private ISmallTicketDao smallTicketDao;
    @Resource
    private IGoodsTagsDao goodsTagsDao;
    @Resource
    private ISupplierDao supplierDao;

    @Override
    public boolean add(Goods t) {
        Goods goods = goodsDao.findByGoodsNo(t.getGoodsNo());
        if(goods == null) {
            goodsDao.add(t);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(GoodsDto goodsDto, int storeId) {
        Goods goods = formatGoodsDtoToGoods(goodsDto, storeId);
        return add(goods);
    }

    @Override
    public void del(int id) {
        goodsDao.delete(id);
    }

    @Override
    public void update(Goods t) {
        goodsDao.update(t);
    }

    @Override
    public void update(GoodsDto goodsDto, int storeId) {
        Goods goods = formatGoodsDtoToGoods(goodsDto, storeId);
        goodsDao.update(goods);
    }

    @Override
    public Goods get(int id) {
        return goodsDao.get(id);
    }

    @Override
    public Goods load(int id) {
        return goodsDao.load(id);
    }

    @Override
    public Pager<GoodsDto> goodsList(String aDataSet, int storeId) {
        int start = 0;// 起始
        int pageSize = 20;// size
        int status = 1, categories = -1, supplier = -1, tags = -1;
        String goodsInfo = "";
        try{
            JSONArray json = JSONArray.parseArray(aDataSet);
            int len = json.size();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject = (JSONObject) json.get(i);
                if (jsonObject.get("name").equals("iDisplayStart")) {
                    start = (Integer) jsonObject.get("value");
                } else if (jsonObject.get("name").equals("iDisplayLength")) {
                    pageSize = (Integer) jsonObject.get("value");
                } else if (jsonObject.get("name").equals("goodsInfo")) {
                    goodsInfo = jsonObject.get("value").toString();
                } else if (jsonObject.get("name").equals("categoriesId")) {
                    categories = Integer.parseInt(jsonObject.get("value").toString());
                } else if (jsonObject.get("name").equals("supplierId")) {
                    supplier = Integer.parseInt(jsonObject.get("value").toString());
                } else if (jsonObject.get("name").equals("goodsTags")) {
                    tags = Integer.parseInt(jsonObject.get("value").toString());
                } else if (jsonObject.get("name").equals("status")) {
                    status = Integer.parseInt(jsonObject.get("value").toString());
                }
            }
            Pager<Object> params = new Pager<>();
            params.setStatus(status);
            params.setOrderColumn("goods.id");
            params.setOrderDir("desc");
            params.setIntParam1(categories);
            params.setIntParam2(supplier);
            params.setIntParam3(tags);
            params.setIntParam4(storeId);
            params.setStringParam1(goodsInfo);
            Pager<Goods> goods_pager = goodsDao.findByParam(start, pageSize, params);
            Pager<GoodsDto> goods_dto_pager = new Pager<>();
            goods_dto_pager.setiTotalDisplayRecords(goods_pager.getiTotalDisplayRecords());
            goods_dto_pager.setiTotalRecords(goods_pager.getiTotalRecords());
            List<Goods> goodsList = goods_pager.getAaData();
            List<GoodsDto> goodsDtoList = new ArrayList<>(goodsList.size());
            for(Goods goods : goodsList){
                goodsDtoList.add(formatGoodsToGoodsDto(goods));
            }
            goods_dto_pager.setAaData(goodsDtoList);
            return goods_dto_pager;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GoodsDtoSimple> goodsListByCategories(int categories) {
        Pager<Object> params = new Pager<>();
        params.setIntParam2(categories);
        List<Goods> goodsList = goodsDao.findByParam(params);
        List<GoodsDtoSimple> goodsDtoSimpleList = new ArrayList<>();
        for(Goods goods : goodsList){
            goodsDtoSimpleList.add(new GoodsDtoSimple(goods));
        }
        return goodsDtoSimpleList;
    }

    @Override
    public void goodsSort(String ids) {
        String[] idsArr = ids.split(",");
        for(int i = 0, len = idsArr.length; i < len; i++){
            Goods goods = get(Integer.parseInt(idsArr[i]));
            goods.setSeq(i);
            update(goods);
        }
    }

    @Override
    public void goodsCopy(int storeId, String ids) {
        String[] idsArr = ids.split(",");
        for(int i = 0, len = idsArr.length; i < len; i++){
            Goods copyGoods = new Goods();
            Goods goods = get(Integer.parseInt(idsArr[i]));
            try {
                BeanUtils.copyProperties(copyGoods, goods);
                copyGoods.setStoreId(storeId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            add(copyGoods);
        }
    }

    @Override
    public Map<String, Object> importGoodsExcel(MultipartFile file, int storeId, int isCreateNewCategories, int isCreateNewUnit) {
        String filePath = ResourcesUtils.getFilePath();
        Map<String, Object> map = new HashMap<>();
        Goods goods = null;
        filePath = filePath + "/商品导入模板(餐饮).xls";
        try{
            InputStream inputStream = new FileInputStream(filePath);
            //InputStream inputStream = file.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
            for(int numSheet = 0, len = hssfWorkbook.getNumberOfSheets(); numSheet < len; numSheet++){
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        goods = new Goods();
                        HSSFCell goodsNo = hssfRow.getCell(2);
                        Goods g = goodsDao.findByGoodsNo(PoiUtils.getValue(goodsNo));
                        if(g == null){
                            HSSFCell name = hssfRow.getCell(0);
                            HSSFCell categoriesName = hssfRow.getCell(1);
                            HSSFCell mainUnit = hssfRow.getCell(3);
                            HSSFCell stock = hssfRow.getCell(4);
                            HSSFCell bid = hssfRow.getCell(5);
                            HSSFCell price = hssfRow.getCell(6);
                            HSSFCell tradePrice = hssfRow.getCell(7);
                            HSSFCell vipPrice = hssfRow.getCell(8);
                            HSSFCell isVipSet = hssfRow.getCell(9);
                            HSSFCell isScore = hssfRow.getCell(10);
                            HSSFCell stockUp = hssfRow.getCell(11);
                            HSSFCell stockDown = hssfRow.getCell(12);
                            HSSFCell isPrint = hssfRow.getCell(13);
                            HSSFCell supplier = hssfRow.getCell(14);
                            HSSFCell productionDate = hssfRow.getCell(15);
                            HSSFCell shelfLife = hssfRow.getCell(16);
                            HSSFCell pinyin = hssfRow.getCell(17);
                            HSSFCell status = hssfRow.getCell(18);
                            HSSFCell description = hssfRow.getCell(19);
                            String c_name = PoiUtils.getValue(categoriesName);
                            Categories c = categoriesDao.findByName(c_name, storeId);
                            if(c == null && isCreateNewCategories == 1){
                                c.setName(c_name);
                                c.setStoreId(storeId);
                                categoriesDao.add(c);
                            }else{
                                throw new BaseException("导入商品失败， 以下商品分类缺失： " + c_name);
                            }
                            String u_name = PoiUtils.getValue(mainUnit);
                            GoodsUnit goodsUnit = goodsUnitDao.findByName(u_name, storeId);
                            if(goodsUnit == null && isCreateNewUnit == 1){
                                goodsUnit.setName(u_name);
                                goodsUnit.setStoreId(storeId);
                                goodsUnitDao.add(goodsUnit);
                            }else{
                                throw new BaseException("导入商品失败， 以下商品单位缺失： " + u_name);
                            }
                            goods.setPinyin(PoiUtils.getValue(pinyin));
                            goods.setName(PoiUtils.getValue(name));
                            goods.setStatus(Integer.valueOf(PoiUtils.getValue(status)));
                            goods.setBid(Integer.valueOf(PoiUtils.getValue(bid)));
                            goods.setDescription(PoiUtils.getValue(description));
                            goods.setShelfLife(Integer.valueOf(PoiUtils.getValue(shelfLife)));
                            goods.setPrice(Integer.parseInt(PoiUtils.getValue(price)));
                            goods.setPrint(Integer.parseInt(PoiUtils.getValue(isPrint)));
                            goods.setScore(Integer.parseInt(PoiUtils.getValue(isScore)));
                            goods.setVipSet(Integer.parseInt(PoiUtils.getValue(isVipSet)));
                            goods.setStockDown(Integer.parseInt(PoiUtils.getValue(stockDown)));
                            goods.setStockUp(Integer.parseInt(PoiUtils.getValue(stockUp)));
                            goods.setVipPrice(Integer.parseInt(PoiUtils.getValue(vipPrice)));
                            goods.setTradePrice(Integer.parseInt(PoiUtils.getValue(tradePrice)));
                            goods.setProductionDate(DateUtil.format(PoiUtils.getValue(productionDate), "yyyy-MM-dd HH:mm:ss"));
                            /*Stock s  = new Stock();
                            s.setStoreId(storeId);
                            s.setGoodNum(Integer.parseInt(PoiUtils.getValue(stock)));
                            goods.setStock(s);*/
                            //goods.setSupplier(s);

                            goodsDao.add(goods);
                        }
                    }
                }
                map.put("success", true);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("error", e.getMessage());
        }
        return map;
    }

    @Override
    public GoodsDto formatGoodsToGoodsDto(Goods goods){
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setId(goods.getId());
        goodsDto.setName(goods.getName());
        goodsDto.setBid(goods.getBid());
        goodsDto.setPinyin(goods.getPinyin());
        goodsDto.setVipSet(goods.getVipSet());
        goodsDto.setStatus(goods.getStatus());
        goodsDto.setProductionDate(DateUtil.format(new Date(), "yyy-MM-dd"));
        Stock stock = goods.getStock();
        String goodsNum = "";
        if(stock != null){
            goodsNum = Long.toString(stock.getGoodNum());
        }
        goodsDto.setStock(goodsNum);
        goodsDto.setShelfLife (Integer.toString(goods.getShelfLife()));
        goodsDto.setTradePrice(Integer.toString(goods.getTradePrice()));
        goodsDto.setPrice(goods.getPrice());
        goodsDto.setCategoriesId(goods.getCategoriesId());
        goodsDto.setCategoriesName(goods.getCategoriesName());
        goodsDto.setMainUnit(goods.getMainUnitId());
        goodsDto.setMainUnitName(goods.getMainUnitName());
        goodsDto.setSupplierName(goods.getSupplierName());
        goodsDto.setStockDown(Integer.toString(goods.getStockDown()));
        goodsDto.setStockUp(Integer.toString(goods.getStockUp()));
        goodsDto.setDescription(goods.getDescription());
        goodsDto.setSupplierId(Integer.toString(goods.getSupplierId()));
        goodsDto.setVipPrice(goods.getVipPrice());
        goodsDto.setProductionDate(DateUtil.format(goods.getProductionDate(), "yyyy-MM-dd"));
        goodsDto.setGoodsNo(goods.getGoodsNo());
        goodsDto.setGoodsImg(goods.getImgUrl());
        Set<SmallTicket> smallTicketSet = goods.getSmallTicketSet();
        Set<GoodsTags> goodsTagsSet = goods.getGoodsTagsSet();
        if(smallTicketSet != null && smallTicketSet.size() > 0){
            StringBuffer stsb = new StringBuffer();
            smallTicketSet.forEach(smallTicket -> stsb.append(smallTicket.getId() + ","));
            stsb.deleteCharAt(stsb.length() - 1);
            goodsDto.setSmallTickets(stsb.toString());
        }else{
            goodsDto.setSmallTickets("0");
        }

        if(goodsTagsSet != null && goodsTagsSet.size() > 0){
            StringBuffer gtsb = new StringBuffer();
            StringBuffer gtsb_name = new StringBuffer();
            goodsTagsSet.forEach(goodsTags -> {
                gtsb.append(goodsTags.getId() + ",");
                gtsb_name.append(goodsTags.getName() + ",");
            });
            gtsb.deleteCharAt(gtsb.length() - 1);
            gtsb_name.deleteCharAt(gtsb_name.length() - 1);
            goodsDto.setGoodsTagss(gtsb.toString());
            goodsDto.setGoodsTagsName(gtsb_name.toString());
        }else{
            goodsDto.setGoodsTagss("0");
        }
        Date pro_date = goods.getProductionDate();
        int shelfLife = goods.getShelfLife();
        if(pro_date != null && shelfLife != 0){
            Calendar c = Calendar.getInstance();
            c.setTime(pro_date);
            c.add(Calendar.DATE, shelfLife);
            Date overdueTime = c.getTime();
            goodsDto.setOverdueTime(DateUtil.format(overdueTime, "yyyy-MM-dd"));
            Date nowDate = new Date();
            if(nowDate.after(overdueTime)){
                long time = nowDate.getTime() - overdueTime.getTime();
                goodsDto.setOverdueDay((int)time/(24*60*60*1000));
            }
        }
        return goodsDto;
    }

    private Goods formatGoodsDtoToGoods(GoodsDto goodsDto, int storeId){
        Goods goods = new Goods();
        goods.setId(goodsDto.getId());
        goods.setPrice(goodsDto.getPrice());
        Stock stock = new Stock();
        stock.setGoodNum(Long.parseLong(goodsDto.getStock()));
        goods.setStock(stock);
        goods.setCategoriesId(goodsDto.getCategoriesId());
        Categories c = categoriesDao.get(goodsDto.getCategoriesId());
        goods.setCategoriesName(c.getName());
        goods.setProductionDate(
                StringUtils.isNotBlank(goodsDto.getProductionDate())
                        ? DateUtil.format(goodsDto.getProductionDate(), "yyyy-MM-dd")
                        : new Date());
        goods.setBid(goodsDto.getBid());
        goods.setDescription(goodsDto.getDescription());
        goods.setGoodsNo(goodsDto.getGoodsNo());
        goods.setTradePrice(StringUtil.formatStr2Int(goodsDto.getTradePrice()));
        goods.setImgUrl(goodsDto.getGoodsImg());
        goods.setVipSet(goodsDto.getVipSet());
        goods.setStoreId(storeId);
        int supplierId = StringUtil.formatStr2Int(goodsDto.getSupplierId());
        Supplier supplier = supplierDao.get(storeId);
        if(supplier != null){
            goods.setSupplierName(supplier.getName());
        }
        goods.setSupplierId(supplierId);
        goods.setVipPrice(goodsDto.getVipPrice());
        goods.setName(goodsDto.getName());
        goods.setStockDown(StringUtil.formatStr2Int(goodsDto.getStockDown()));
        goods.setStockUp(StringUtil.formatStr2Int(goodsDto.getStockUp()));
        goods.setPinyin(goodsDto.getPinyin());
        goods.setShelfLife(StringUtil.formatStr2Int(goodsDto.getShelfLife()));
        goods.setStatus(goodsDto.getStatus());
        goods.setMainUnitId(goodsDto.getMainUnit());
        GoodsUnit mainUnit = goodsUnitDao.get(goodsDto.getMainUnit());
        if(mainUnit != null){
            goods.setMainUnitName(mainUnit.getName());
        }
        String smallTicketsStr = goodsDto.getSmallTickets();
        String goodsTagsStr = goodsDto.getGoodsTagss();
        //小票
        if(StringUtils.isNotBlank(smallTicketsStr)){
            String[] smallticketIds = smallTicketsStr.split(",");
            Set<SmallTicket> smallTicketSet = new HashSet<>();
            List<String> smallticketIdsList = Arrays.asList(smallticketIds);
            smallticketIdsList.forEach(str -> {
                SmallTicket smallTicket = smallTicketDao.get(Integer.parseInt(str));
                smallTicketSet.add(smallTicket);
            });
            goods.setSmallTicketSet(smallTicketSet);
        }

        if(StringUtils.isNotBlank(goodsTagsStr)){
            String[] goodsTagsIds = goodsTagsStr.split(",");
            Set<GoodsTags> goodsTagsSet = new HashSet<>();
            List<String> goodsTagsList = Arrays.asList(goodsTagsIds);
            goodsTagsList.forEach(str -> {
                GoodsTags goodsTags = goodsTagsDao.get(Integer.parseInt(str));
                goodsTagsSet.add(goodsTags);
            });
            goods.setGoodsTagsSet(goodsTagsSet);
        }
        return goods;
    }

    @Override
    public String upload(MultipartFile file) {
        String dateStr = DateUtil.format(new Date(), "yyyy-MM-dd").replace("-", "/");
        String fileName = UploadsUtils.changeFileName(file.getOriginalFilename());
        String path = ResourcesUtils.getFilePath() + dateStr;
        String saveUrl = ResourcesUtils.getFileUrl() + dateStr + "/" + fileName;
        String filePath =  path + "/" + fileName;
        //判断文件夹是否存在
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            logger.info("--- filePath : "+filePath+" ---");
            UploadsUtils.upload(file, filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return saveUrl;
    }

    @Override
    public List<Goods> findAll(int storeId) {
        return goodsDao.findAll(storeId);
    }
}
