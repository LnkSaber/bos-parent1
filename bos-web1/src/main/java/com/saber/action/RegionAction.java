package com.saber.action;

import com.saber.domain.Region;
import com.saber.service.RegionService;
import com.saber.utils.PinYin4jUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
    @Autowired
    private RegionService regionService;
    private File regionFile;
    public File getRegionFile() {
        return regionFile;
    }
    public void setRegionFile(File regionFile) {
        this.regionFile = regionFile;
    }

    /**
     * 文件上传，解析Excel文件，并保存
     * @throws IOException
     * @throws FileNotFoundException
     */
    public String improxXls() throws IOException {
        List<Region> regionList =new ArrayList<Region>();

        //使用POI解析Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));

        //根据名称获得指定Sheet对象
        HSSFSheet hssfSheet =workbook.getSheet("Sheet1");

        for(Row row :hssfSheet){
            int rowNum =row.getRowNum();
            if (rowNum == 0){
                continue;
            }
            //  0=> id , 1 => province , 2 =>city , 3 => district,  4=>postcode
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

            //首字母大写 : 简码：HBSJZQX(河北石家庄桥西)
            //获得简称:河北石家庄桥西
            String info =province.substring(0,province.length()-1)+city.substring(0,city.length()-1)+district.substring(0,district.length()-1);
            //获得首字母大写:HBSJZQX
            String [] headByString = PinYin4jUtils.getHeadByString(info);
            String shortCode = StringUtils.join(headByString);

            //城市编码  ---》》shijiazhuang  shijiazhuang
            String cityCode =PinYin4jUtils.hanziToPinyin(city.substring(0,city.length()-1),"");

         Region region = new Region(id,province,city,district,postcode,shortCode,cityCode,null);
            //不建议这样用  使用批量保存
            //service.save();
            regionList.add(region);
        }
        regionService.save(regionList);
        return NONE;
    }

    public String pageQuery(){
        regionService.pageQuery(pageBean);
        //将java对象转为json过程中，因为对象之间有互相引用关系，会发生死循环 ，所以过滤掉subareas属性
        this.javaJson(pageBean,new String[]{"detachedCriteria","currentPage","pageSize","subareas"});
        return NONE;
    }

    //分区管理分区：通过异步查询向后台，传输的q值
    private String q;
    public String getQ() {
        return q;
    }
    public void setQ(String q) {
        this.q = q;
    }

    public String listAjax(){
        List<Region> list =null;
        if (StringUtils.isNotBlank(q)){
            list =regionService.findListByQ(q);
        }
        else {
            list =regionService.findAll();
        }

        this.javaJson(list,new String[]{"subareas"});
        return NONE;
    }


}
