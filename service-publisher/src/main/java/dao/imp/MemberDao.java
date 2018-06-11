
package dao.imp;

import dao.BaseDao;
import dao.IMemberDao;
import org.hibernate.*;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import util.StringUtil;
import remote.vo.Admin;
import remote.vo.Member;
import remote.vo.Page;
import remote.vo.Student;

import java.util.ArrayList;
import java.util.List;

@Repository("memberDao")
public class MemberDao extends BaseDao implements IMemberDao {
    /*@Resource(name = "sessionFactory")
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);

    }*/

    public List<?> validateMember(Member member) {
        List<Object> list = new ArrayList<Object>();
        Member mem = (Member) getHibernateTemplate().get(Member.class, member.getMemberId());
        List list2 = getHibernateTemplate().find("from Member m where m.memberId=? and m.memPassword=?", new Integer(member.getMemberId()), new String(member.getMemPassword()));
        if (list2.size() == 0) {
            return list2;
        } else {
            Admin admin = mem.getAdmin();
            Student student = mem.getStudent();
            list.add(0, mem);
            if (null != admin) list.add(1, admin);
            if (null != student) list.add(1, student);
            return list;
        }
    }

    @Override
    public void memRegister(Member member) {
        getHibernateTemplate().save(member);
    }

    public void updateImg(Object obj) {
        if (obj instanceof Student) {
            Student stu = (Student) obj;
            getHibernateTemplate().update(stu);
        } else {
            Admin adm = (Admin) obj;
            getHibernateTemplate().update(adm);
        }
    }

    @Override
    public String refreshImgHeader(Object obj) {
        String newImgHeader = "";
        if (obj instanceof Student) {
            Student stu = (Student) obj;
            newImgHeader = getHibernateTemplate().get(Student.class, stu.getStuId()).getStuImg();
        } else {
            Admin ad = (Admin) obj;
            newImgHeader = getHibernateTemplate().get(Admin.class, ad.getAdminId()).getAdminImg();
        }
        return newImgHeader;
    }

    public List<Member> viewAllReg(Page page, String memName, String memberId) {
        StringBuffer hql = new StringBuffer("from Member m where m.isApproved=0");
        if (StringUtil.isNotEmpty(memName)) {
            hql.append(" and m.memName like :memName");
        }
        if (StringUtil.isNotEmpty(memberId)) {
            hql.append(" and m.memberId like :memberId");
        }
        List<Member> list = (List<Member>) getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    @Override
                    public Object doInHibernate(Session session) {
                        Query query = session.createQuery(hql.toString());
                        if (StringUtil.isNotEmpty(memName)) {
                            query.setString("memName", "%" + memName + "%");
                        }
                        if (StringUtil.isNotEmpty(memberId)) {
                            query.setString("memberId", "%" + memberId + "%");
                        }
                        query.setFirstResult(page.getStart());
                        query.setMaxResults(page.getLimit());
                        return query.list();
                    }
                }
        );
        return list;
    }

    public int countReg(String memName, String memberId) {
        StringBuffer hql = new StringBuffer("select count(*) from Member m where m.isApproved=0");
        if (StringUtil.isNotEmpty(memName)) {
            hql.append(" and m.memName like :memName");
        }
        if (StringUtil.isNotEmpty(memberId)) {
            hql.append(" and m.memberId like :memberId");
        }
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql.toString());
        if (StringUtil.isNotEmpty(memName)) {
            query.setString("memName", "%" + memName + "%");
        }
        if (StringUtil.isNotEmpty(memberId)) {
            query.setString("memberId", "%" + memberId + "%");
        }
        return Integer.parseInt(query.list().get(0).toString());
    }

    @Override
    public void updateRegAppro(Object memberId) {
        if (memberId instanceof String) {
            String memberIds = (String) memberId;
            String[] memIds = memberIds.split(",");
            for (String memberid : memIds) {
                Member mem = getHibernateTemplate().get(Member.class, Integer.parseInt(memberid));
                mem.setIsApproved(1);
                mem.setLevel(1);
                Student stu = new Student();
                stu.setStuName(mem.getMemName());
                stu.setMember(mem);
                stu.setStuNo(Integer.parseInt(memberid));
                stu.setStuId(Integer.parseInt(memberid));
                getHibernateTemplate().save(stu);
                mem.setStudent(stu);
                getHibernateTemplate().update(mem);
            }
        } else {
            Member mem = getHibernateTemplate().get(Member.class, (Integer) memberId);
            mem.setIsApproved(1);
            mem.setLevel(1);
            Student stu = new Student();
            stu.setStuName(mem.getMemName());
            stu.setMember(mem);
            stu.setStuNo((Integer) memberId);
            stu.setStuId((Integer) memberId);
            getHibernateTemplate().save(stu);
            mem.setStudent(stu);
            getHibernateTemplate().update(mem);
        }
    }

    public void editLoginRead(Student stu) {
        getHibernateTemplate().update(stu);
    }

    @Override
    public void updateMemberInfo(Object obj) {
        if (obj instanceof Student) {
            Student stu = (Student) obj;
            Member member = stu.getMember();
            member.setMemName(stu.getStuName());
            stu.setMember(member);
            getHibernateTemplate().update(stu);
            getHibernateTemplate().update(member);
        } else if (obj instanceof Admin) {
            Admin admin = (Admin) obj;
            Member mem = admin.getMember();
            mem.setMemName(admin.getAdminName());
            admin.setMember(mem);
            getHibernateTemplate().update(admin);
            getHibernateTemplate().update(mem);
        } else {
            Member m = (Member) obj;
            getHibernateTemplate().update(m);
        }
    }
}

