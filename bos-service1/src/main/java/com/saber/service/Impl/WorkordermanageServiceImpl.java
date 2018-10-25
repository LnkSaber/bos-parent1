package com.saber.service.Impl;

import com.saber.dao.WorkordermanageDao;
import com.saber.domain.Workordermanage;
import com.saber.service.WorkordermanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkordermanageServiceImpl implements WorkordermanageService {

    @Autowired
    private WorkordermanageDao workordermanageDao;
    @Override
    public void add(Workordermanage t) {
        workordermanageDao.save(t);
    }
}
