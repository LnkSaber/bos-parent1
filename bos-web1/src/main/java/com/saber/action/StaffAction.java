package com.saber.action;

import com.saber.domain.PageBean;
import com.saber.domain.Staff;
import com.saber.service.StaffService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;


@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
    private static final long serialVersionUID = 1L;

    @Autowired
    private StaffService staffService;

    /**
     * 取派员新增
     * @return
     */
    public String add(){
        staffService.save(t);
        return "list";
    }


    /**
     * 分页查询
     * @return
     * @throws IOException
     */
    public String pageQuery() throws IOException {
       staffService.pageQuery(pageBean);
       this.javaJson(pageBean,new String[]{"detachedCriteria","currentPage","pageSize","decidedzones"});
        return NONE;
    }

    //批量删除所需要的id号
    private String ids;
    public String getIds() {
        return ids;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 批量删除
     * @return
     */
    @RequiresPermissions("staff-delete")
    public String deleteBach(){
        staffService.deleteBach(ids);
        return "list";
    }
    /**
     * 修改取派员信息
     * @return
     */
    public String edit(){
        //先查询数据库 ，根据id查询原始数据
        Staff staff =staffService.findById(t.getId());
        //使用页面提交过来的数据进行覆盖
        staff.setName(t.getName());
        staff.setTelephone(t.getTelephone());
        staff.setHaspda(t.getHaspda());
        staff.setStandard(t.getStandard());
        staff.setStation(t.getStation());

        staffService.update(staff);
        return "list";
    }

    /**
     * 查询所有未删除的取派员
     */
    public String listAjax(){
        //1.调用service查询数据
        List <Staff> list =staffService.findListNotDelete();
        //转json
        this.javaJson(list,new String[] {"decidedzones"});
        return NONE;
    }
}
