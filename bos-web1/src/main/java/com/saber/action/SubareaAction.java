package com.saber.action;

import com.saber.domain.Region;
import com.saber.domain.Subarea;
import com.saber.service.SubareaService;
import com.saber.utils.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>  {
    @Autowired
    private SubareaService subareaService;

    /**
     * 分区信息的添加
     */
    public String add(){
        subareaService.add(t);
        return "list";
    }

    /**
     * 分区分页查询
     */
    public String pageQuery(){
        DetachedCriteria dc =pageBean.getDetachedCriteria();
        //动态添加过滤条件
        String addresskey = t.getAddresskey();

        if(StringUtils.isNotBlank(addresskey)){
            //添加过滤条件，根据地址关键字模糊查询
            dc.add(Restrictions.like("addresskey","%"+addresskey+"%"));
        }
        Region region =t.getRegion();
        if(region !=null){
            String province =region.getProvince();
            String city = region.getCity();
            String district = region.getDistrict();

            //参数一：分区对象中关联的区域对象属性名称
            //参数二：别名，任意
            dc.createAlias("region","r");
            if (StringUtils.isNotBlank(province)){
                //  selet * from A a  , B b where a.id = b.id
                dc.add(Restrictions.like("r.province","%"+province+"%"));
            }
            if (StringUtils.isNotBlank(city)){
                //  selet * from A a  , B b where a.id = b.id
                dc.add(Restrictions.like("r.city","%"+city+"%"));
            }
            if (StringUtils.isNotBlank(district)){
                //  selet * from A a  , B b where a.id = b.id
                dc.add(Restrictions.like("r.district","%"+district+"%"));
            }
        }
        subareaService.pageQuery(pageBean);
        this.javaJson(pageBean,new String[]{"detachedCriteria","currentPage","pageSize","subareas","decidedzone"});
        return NONE;
    }

    /**
     * 分区数据导出功能
     * @throws IOException
     */
    public String exportXls() throws IOException {
        //	1：查询所有的分期数据
        List<Subarea> list =subareaService.finaAll();
        //2）：使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook =new HSSFWorkbook();
        //创建一个标签页
        HSSFSheet sheet =workbook.createSheet("分区数据");

        //创建标题行
        HSSFRow headRow =sheet.createRow(0);
        headRow.createCell(0).setCellValue("分区编号");
        headRow.createCell(1).setCellValue("开始编号");
        headRow.createCell(2).setCellValue("结束编号");
        headRow.createCell(3).setCellValue("位置信息");
        headRow.createCell(4).setCellValue("省市区");

        //添加数据到excel中
        for (Subarea subarea: list) {
            HSSFRow dataRow =sheet.createRow(sheet.getLastRowNum()+1);

            dataRow.createCell(0).setCellValue(subarea.getId());
            dataRow.createCell(1).setCellValue(subarea.getStartnum());
            dataRow.createCell(2).setCellValue(subarea.getEndnum());
            dataRow.createCell(3).setCellValue(subarea.getPosition());
            dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
        }
        //		3：使用输出流进行文件下载(一个流 ，两个头)
        String filename ="分区数据.xls";
        String contentType = ServletActionContext.getServletContext().getMimeType(filename);

        ServletActionContext.getResponse().setContentType(contentType);

        ServletOutputStream out =ServletActionContext.getResponse().getOutputStream();
        //获取客户端浏览器类型
        String header =ServletActionContext.getRequest().getHeader("User-Agent");

        filename = FileUtils.encodeDownloadFilename(filename,header);

        ServletActionContext.getResponse().setHeader("content-disposition","attachment;filename="+filename);

        workbook.write(out);
        return NONE;
    }

    /**
     * 查询所有未关联到定区的分区
     */
    public String listAjax(){
        List<Subarea> list =subareaService.findListNotAssociation();
        this.javaJson(list,new String[] {"decidedzone","region"});
        return NONE;
    }

    //属性驱动-接收定区id
    private String decidedzoneId;
    /**
     *	根据定区id查询关联的分区
     */
    public String findListByDecidedzoneId(){
        List<Subarea> list =subareaService.findListByDecidedzoneId(decidedzoneId);
        this.javaJson(list,new String[]{"decidedzone","subareas"});
        return NONE;
    }

    /**
     *图表显示，根据行政区来划分组
     */
    public String findSubareasGroupByProvince(){
        List<Object> list =subareaService.findSubareasGroupByProvince();

        this.javaJson(list,new String[]{});

        return NONE;
    }


    public String getDecidedzoneId() {
        return decidedzoneId;
    }
    public void setDecidedzoneId(String decidedzoneId) {
        this.decidedzoneId = decidedzoneId;
    }
}
