package dao.imp;

import dao.BaseDao;
import dao.IAdminOpDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import util.StringUtil;
import remote.vo.*;

import java.util.*;

@Repository("adminOpDao")
public class AdminOpDao extends BaseDao implements IAdminOpDao {
    @Override
    public List<Notice> viewAllNotice(Page page, Notice no, String noticeId, Date s_pbBeginTime, Date s_pbEndTime) {
        StringBuffer hql = new StringBuffer("from Notice n where 1=1 ");
        List queryList = (List) getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session) {
                        if (StringUtil.isNotEmpty(noticeId)) {
                            hql.append(" and n.noticeId like :noticeId ");
                        }
                        if (StringUtil.isNotEmpty(no.getNoticeTitle())) {
                            hql.append(" and n.noticeTitle like :noticeTitle ");
                        }
                        if (StringUtil.isNotEmpty(no.getAdmin().getAdminName())) {
                            hql.append(" and n.admin.adminName like :adminName ");
                        }
                        if (null != s_pbBeginTime) {
                            hql.append(" and to_days(n.noticeTime) >= to_days(:s_pbBeginTime)");
                        }
                        if (null != s_pbEndTime) {
                            hql.append(" and to_days(n.noticeTime) <= to_days(:s_pbEndTime)");
                        }
                        Query query = session.createQuery(hql.toString());
                        if (StringUtil.isNotEmpty(noticeId)) {
                            query.setInteger("noticeId", Integer.parseInt(noticeId));
                        }
                        if (StringUtil.isNotEmpty(no.getNoticeTitle())) {
                            query.setString("noticeTitle", "%" + no.getNoticeTitle() + "%");
                        }
                        if (StringUtil.isNotEmpty(no.getAdmin().getAdminName())) {
                            query.setString("adminName", "%" + no.getAdmin().getAdminName() + "%");
                        }
                        if (null != s_pbBeginTime) {
                            query.setDate("s_pbBeginTime", s_pbBeginTime);
                        }
                        if (null != s_pbEndTime) {
                            query.setDate("s_pbEndTime", s_pbEndTime);
                        }
                        query.setFirstResult(page.getStart());
                        query.setMaxResults(page.getLimit());
                        return query.list();
                    }
                }
        );
        return queryList;
    }

    public int countNotice(Notice no, String noticeId, Date s_pbBeginTime, Date s_pbEndTime) {
        StringBuffer hql = new StringBuffer("select count(*) from Notice n where 1=1 ");
        if (StringUtil.isNotEmpty(noticeId)) {
            hql.append(" and n.noticeId like :noticeId ");
        }
        if (StringUtil.isNotEmpty(no.getNoticeTitle())) {
            hql.append(" and n.noticeTitle like :noticeTitle ");
        }
        if (StringUtil.isNotEmpty(no.getAdmin().getAdminName())) {
            hql.append(" and n.admin.adminName like :adminName ");
        }
        if (null != s_pbBeginTime) {
            hql.append(" and to_days(n.noticeTime) >= to_days(:s_pbBeginTime)");
        }
        if (null != s_pbEndTime) {
            hql.append(" and to_days(n.noticeTime) <= to_days(:s_pbEndTime)");
        }
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql.toString());
        if (StringUtil.isNotEmpty(noticeId)) {
            query.setInteger("noticeId", Integer.parseInt(noticeId));
        }
        if (StringUtil.isNotEmpty(no.getNoticeTitle())) {
            query.setString("noticeTitle", "%" + no.getNoticeTitle() + "%");
        }
        if (StringUtil.isNotEmpty(no.getAdmin().getAdminName())) {
            query.setString("adminName", "%" + no.getAdmin().getAdminName() + "%");
        }
        if (null != s_pbBeginTime) {
            query.setDate("s_pbBeginTime", s_pbBeginTime);
        }
        if (null != s_pbEndTime) {
            query.setDate("s_pbEndTime", s_pbEndTime);
        }
        return Integer.parseInt(query.list().get(0).toString());
    }

    @Override
    public void delNoticeById(String ids) {
        String[] strIds = ids.split(",");
        for (String id : strIds) {
            Notice no = getHibernateTemplate().get(Notice.class, Integer.parseInt(id));
            Admin ad = no.getAdmin();
            if (ad != null)
                no.getAdmin().getNotices().remove(no);
            getHibernateTemplate().delete(no);
        }
    }

    @Override
    public void saveNotice(Notice notice) {
        if (notice.getNoticeId() != 0) {
            Admin ad = getHibernateTemplate().get(Notice.class, notice.getNoticeId()).getAdmin();
            notice.setAdmin(ad);
            getHibernateTemplate().merge(notice);
        } else getHibernateTemplate().merge(notice);
    }

    @Override
    public List<Object[]> allSignUp(Page page, Exam exam, String s_stuName, String s_sId, String isPrint) {
        List list = (List) getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session) {
                        StringBuffer hql = new StringBuffer("from SignUpStatus s,Exam e,Student st where s.exam.exam_id =e.exam_id and s.student.stuId=st.stuId");
                        if (StringUtil.isNotEmpty(isPrint)) {
                            hql.append(" and s.appro_stat=1");
                        }
                        if (StringUtil.isNotEmpty(s_sId)) {
                            hql.append(" and s.sId like :sId");
                        }
                        if (StringUtil.isNotEmpty(exam.getExamName())) {
                            hql.append(" and e.examName like :examName");
                        }
                        if (null != exam.getExamBeginTime()) {
                            hql.append(" and to_days(e.examBeginTime) >=to_days(:examBeginTime)");
                        }
                        if (exam.getExamEndTime() != null) {
                            hql.append(" and to_days(e.examEndTime) <= to_days(:examEndTime)");
                        }
                        if (StringUtil.isNotEmpty(s_stuName)) {
                            hql.append(" and st.stuName like :stuName");
                        }
                        Query query = session.createQuery(hql.toString());
                        if (StringUtil.isNotEmpty(s_sId)) {
                            query.setString("sId", "%" + Integer.parseInt(s_sId.trim()) + "%");
                        }
                        if (StringUtil.isNotEmpty(exam.getExamName())) {
                            query.setString("examName", "%" + exam.getExamName() + "%");
                        }
                        if (null != exam.getExamBeginTime()) {
                            query.setDate("examBeginTime", exam.getExamBeginTime());
                        }
                        if (null != exam.getExamEndTime()) {
                            query.setDate("examEndTime", exam.getExamEndTime());
                        }
                        if (StringUtil.isNotEmpty(s_stuName)) {
                            query.setString("stuName", "%" + s_stuName + "%");
                        }
                        query.setFirstResult(page.getStart());
                        query.setMaxResults(page.getLimit());
                        return query.list();
                    }
                }
        );
        return list;
    }

    @Override
    public int countSignUp(Page page, Exam exam, String s_stuName, String s_sId, String isPrint) {
        StringBuffer hql = new StringBuffer("select count(*) from SignUpStatus s,Exam e,Student st where s.exam.exam_id =e.exam_id and s.student.stuId=st.stuId");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        if (StringUtil.isNotEmpty(isPrint)) {
            hql.append(" and s.appro_stat=1");
        }
        if (StringUtil.isNotEmpty(s_sId)) {
            hql.append(" and s.sId like :sId");
        }
        if (StringUtil.isNotEmpty(exam.getExamName())) {
            hql.append(" and e.examName like :examName");
        }
        if (null != exam.getExamBeginTime()) {
            hql.append(" and to_days(e.examBeginTime) >=to_days(:examBeginTime)");
        }
        if (exam.getExamEndTime() != null) {
            hql.append(" and to_days(e.examEndTime) <= to_days(:examEndTime)");
        }
        if (StringUtil.isNotEmpty(s_stuName)) {
            hql.append(" and st.stuName like :stuName");
        }
        Query query = session.createQuery(hql.toString());
        if (StringUtil.isNotEmpty(s_sId)) {
            query.setString("sId", "%" + Integer.parseInt(s_sId.trim()) + "%");
        }
        if (StringUtil.isNotEmpty(exam.getExamName())) {
            query.setString("examName", "%" + exam.getExamName() + "%");
        }
        if (null != exam.getExamBeginTime()) {
            query.setDate("examBeginTime", exam.getExamBeginTime());
        }
        if (null != exam.getExamEndTime()) {
            query.setDate("examEndTime", exam.getExamEndTime());
        }
        if (StringUtil.isNotEmpty(s_stuName)) {
            query.setString("stuName", "%" + s_stuName + "%");
        }
        return Integer.parseInt(query.list().get(0).toString());

    }

    @Override
    public int approSignUp(String signUpId, int approVersion) {
        int flag = -1;
        if (StringUtil.isNotEmpty(signUpId)) {
            SignUpStatus sgs = getHibernateTemplate().get(SignUpStatus.class, Integer.parseInt(signUpId.trim()));
            if (approVersion == 0) {
                sgs.setAppro_stat(1);
                flag = 0;
            } else {
                sgs.setAppro_stat(0);
                flag = 1;
            }
            getHibernateTemplate().update(sgs);
        }
        return flag;
    }

    @Override
    public void saveCerti(int saveSId, String certificateId) {
        SignUpStatus signUpStatus = getHibernateTemplate().get(SignUpStatus.class, saveSId);
        Student s = signUpStatus.getStudent();
        if (!StringUtil.isNotEmpty(s.getCertificateId())) {
            s.setCertificateId(certificateId);
            getHibernateTemplate().update(s);
        }
    }

}
