package com.saber.service;

import com.saber.domain.Decidedzone;
import com.saber.domain.PageBean;

public interface DecidedzoneService {
    /**
     * 添加定区
     * @param t
     * @param subareaid
     */
    void save(Decidedzone t, String[] subareaid);

    void pageQuery(PageBean pageBean);
}
