package com.saber.service;

import com.saber.domain.Noticebill;

public interface NoticebillService {
    /**
     * 保存一个业务通知单，并尝试自动分单
     * @param t
     */
    void save(Noticebill t);
}
