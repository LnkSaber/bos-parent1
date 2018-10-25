package com.saber.action;

import com.saber.domain.Workordermanage;
import com.saber.service.WorkordermanageService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@Scope("prototype")
public class WorkodermnagageAction extends BaseAction<Workordermanage> {

    @Autowired
    private WorkordermanageService workordermanageService;

    public String add() throws IOException {
        String flag ="1";
        try {
            workordermanageService.add(t);
        } catch (Exception e) {
            e.printStackTrace();
            flag="0";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(flag);

        return NONE;
    }
}
