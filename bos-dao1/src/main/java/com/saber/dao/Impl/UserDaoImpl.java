package com.saber.dao.Impl;

import com.saber.dao.UserDao;
import com.saber.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public User finUserBy(User model) {
        System.out.println("UserDaoImpl ‘s 密码"+model.getPassword());

        String hql = "FROM User  WHERE username = ? and password = ?";
        //查询数据库用户信息
        List<User> list= (List<User>) this.getHibernateTemplate().find(hql,model.getUsername(),model.getPassword());
        if(list !=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
    /**
     * 密码修改
     */
    @Override
    public void executeUpdate(String id, String password) {
        Session session=this.getSessionFactory().getCurrentSession();

        String hql="UPDATE User set password = :pwd where id = :id";
        Query query =session.createQuery(hql);

        query.setParameter("pwd",password);
        query.setParameter("id",id);
        int i=query.executeUpdate();
        System.out.println(i);
    }

    @Override
    public User finUserByUsername(String username) {

        String hql ="FROM User where  username = ?";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql,username);

        if(list !=null && list.size()> 0){
            return list.get(0);
        }

        return null;
    }


}
