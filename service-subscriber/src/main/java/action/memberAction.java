
package action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import service.IMemberService;
import util.StringUtil;
import remote.vo.Admin;
import remote.vo.Member;
import remote.vo.Page;
import remote.vo.Student;
import java.util.Map;
import java.util.UUID;

@Controller("memberAction")
@Scope("prototype")
public class memberAction extends ActionSupport {
   // @Resource
    private static IMemberService memberService = null;
    private Member member;
    private File file;
    private String fileFileName;
    private String fileContentType;
    private int id;
    private JSONObject jb;
    private String code;
    private String version;
    private int page;
    private int limit;
    private int memberId;
    private String memberIds;
    private String s_memberId;
    private String s_memName;
    private String loginRead;
    private String editInfoRead, edit_memberName, edit_address, edit_phone, edit_idCard;
    private int edit_memberId, edit_sex;
    private String password, newPassword, updateVersion;

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEditInfoRead() {
        return editInfoRead;
    }

    public void setEditInfoRead(String editInfoRead) {
        this.editInfoRead = editInfoRead;
    }


    public String getEdit_memberName() {
        return edit_memberName;
    }

    public void setEdit_memberName(String edit_memberName) {
        this.edit_memberName = edit_memberName;
    }

    public int getEdit_memberId() {
        return edit_memberId;
    }

    public void setEdit_memberId(int edit_memberId) {
        this.edit_memberId = edit_memberId;
    }

    public String getEdit_phone() {
        return edit_phone;
    }

    public void setEdit_phone(String edit_phone) {
        this.edit_phone = edit_phone;
    }

    public String getEdit_idCard() {
        return edit_idCard;
    }

    public void setEdit_idCard(String edit_idCard) {
        this.edit_idCard = edit_idCard;
    }

    public int getEdit_sex() {
        return edit_sex;
    }

    public void setEdit_sex(int edit_sex) {
        this.edit_sex = edit_sex;
    }

    public String getEdit_address() {
        return edit_address;
    }

    public void setEdit_address(String edit_address) {
        this.edit_address = edit_address;
    }

    public String getLoginRead() {
        return loginRead;
    }

    public void setLoginRead(String loginRead) {
        this.loginRead = loginRead;
    }

    public String getS_memberId() {
        return s_memberId;
    }

    public void setS_memberId(String s_memberId) {
        this.s_memberId = s_memberId;
    }

    public String getS_memName() {
        return s_memName;
    }

    public void setS_memName(String s_memName) {
        this.s_memName = s_memName;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JSONObject getJb() {
        return jb;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public void setJb(JSONObject jb) {
        this.jb = jb;
    }
    public static IMemberService getInstance(){
        if(null == memberService){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("subscriber-dubbo.xml");
            memberService = context.getBean(IMemberService.class);
        }
        return memberService;
    }
    public String login() {
        jb = new JSONObject();
        if (code.isEmpty()) {
            jb.put("success", false);
            jb.put("errMsg", "验证码为空");
        } else {
            Map session = ServletActionContext.getContext().getSession();
            if (!code.equalsIgnoreCase((String) session.get("imageCode"))) {
                jb.put("success", false);
                jb.put("errMsg", "验证码错误");
            } else {
                if (!StringUtil.isNotEmpty(version)) {
                    List<?> list = getInstance().validateMember(member);
                    if (list.size() == 0) {
                        jb.put("success", false);
                        jb.put("errMsg", "账号或密码错误");
                        return SUCCESS;
                    }
                    if (list.get(0) != null) {
                        jb.put("success", true);
                        session.put("currentMember", list.get(0));
                        if (list.size() == 2) {
                            if (list.get(1) instanceof Student) {
                                session.put("currentStu", list.get(1));
                            } else {
                                session.put("currentAdmin", list.get(1));
                            }
                        }
                    }
                } else {
                    int succFlag = getInstance().save_memRegister(member);
                    if (succFlag > 0) {
                        jb.put("success", true);
                        session.put("currentMember", member);
                    } else jb.put("success", false);
                }
            }
        }
        return SUCCESS;
    }

    public String logout() {
        return "logoutSuccess";
    }

    public String getCMember() {
        Member currentMember = (Member) ActionContext.getContext().getSession().get("currentMember");
        jb = new JSONObject();
        jb.put("memberName", currentMember.getMemName());
        Admin currentAdmin = currentMember.getAdmin();
        Student currentStu = currentMember.getStudent();
        if (currentStu != null) {
            jb.put("loginRead", currentStu.getLoginRead());
            jb.put("editInfoRead", currentStu.getEditInfoRead());
            jb.put("sex", currentStu.getStuSex());
            jb.put("phone", currentStu.getStuPhone());
            jb.put("identity", currentStu.getStuIDCard());
            jb.put("address", currentStu.getStuAddress());
        }
        if (null != currentAdmin) {
            jb.put("sex", currentAdmin.getAdminSex());
            jb.put("phone", currentAdmin.getAdminPhone());
            jb.put("identity", currentAdmin.getAdminIDCard());
            jb.put("address", currentAdmin.getAdminAddress());
        }
        jb.put("id", currentMember.getMemberId());
        jb.put("isApproved", currentMember.getIsApproved());
        String level = String.valueOf(currentMember.getLevel());
        switch (level) {
            case "0":
                level = "注册未审核";
                break;
            case "1":
                level = "考生";
                break;
            case "2":
                level = "系统管理员";
        }
        jb.put("level", level);
        return SUCCESS;
    }

    public String getMemberImage() throws Exception {
        Member currentMember = (Member) ActionContext.getContext().getSession().get("currentMember");
        String filePath = "";
        Admin currentAdmin = currentMember.getAdmin();
        Student currentStu = currentMember.getStudent();
        String imgPath = "";
        if (currentAdmin != null) {
            imgPath = getInstance().refreshImgHeader(currentAdmin);
        } else if (currentStu != null) {
            imgPath = getInstance().refreshImgHeader(currentStu);
        }
        if (StringUtil.isNotEmpty(imgPath)) {
            filePath = imgPath;
        } else filePath = ServletActionContext.getServletContext().getRealPath(File.separator) + "/images/imgDf.jpg";
        jb = new JSONObject();
        File upfile = new File(filePath);
        BufferedInputStream inputStream = null;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("image/jpeg");
        OutputStream writer = null;
        try {
            writer = response.getOutputStream();
            if (upfile != null && upfile.exists()) {
                inputStream = new BufferedInputStream(new FileInputStream(upfile));
            } else {
                inputStream = new BufferedInputStream(new FileInputStream(ServletActionContext.getServletContext().getRealPath(File.separator) + "/images/imgDf.jpg"));
            }
            int temp = 0;
            byte[] buffer = new byte[1024];
            while ((temp = inputStream.read(buffer)) != -1) {
                writer.write(buffer, 0, temp);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String realFileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        jb.put("imgHeader", realFileName);
        return SUCCESS;
    }

    public String updateImg() throws Exception {
        jb = new JSONObject();
        if (file == null) {
            jb.put("success", false);
            jb.put("errMsg", "当前没有上传的头像，请选择后再重试");
        } else {
            if (file.length() > 1024 * 1024 * 10) {
                jb.put("success", false);
                jb.put("errMsg", "头像文件大小超过10mb");
                return SUCCESS;
            }
            Member currentMember = (Member) ActionContext.getContext().getSession().get("currentMember");
            Student currentStu = currentMember.getStudent();
            Admin currentAdmin = currentMember.getAdmin();
            String imgHeader = "";
            if (currentStu != null) {
                imgHeader = currentStu.getStuImg();
            }
            if (currentAdmin != null) {
                imgHeader = currentAdmin.getAdminImg();
            }
            BufferedInputStream input = null;
            BufferedOutputStream out = null;
            String savePath = "D:/fileUpload";
            String fileSaveName = this.mkSaveFileName(fileFileName, savePath);
            try {
                input = new BufferedInputStream(new FileInputStream(file));
                out = new BufferedOutputStream(new FileOutputStream(fileSaveName));
                byte[] buffer = new byte[1024];
                int temp = 0;
                while ((temp = input.read(buffer)) != -1) {
                    out.write(buffer, 0, temp);
                }
                int succFlag = -1;
                if (currentStu != null) {
                    currentStu.setStuImg(fileSaveName);
                    succFlag = getInstance().updateImg(currentStu);

                } else {
                    currentAdmin.setAdminImg(fileSaveName);
                    succFlag = getInstance().updateImg(currentAdmin);
                }
                if (succFlag > 0) {
                    jb.put("success", true);
                    if (imgHeader != null) {
                        File oldfile = new File(imgHeader);
                        if (oldfile != null) {
                            oldfile.delete();
                        }
                    }
                } else {
                    jb.put("success", false);
                    jb.put("errMsg", "服务器开了小差，请重试");
                }
            } catch (IOException e) {
                e.printStackTrace();
                jb.put("success", false);
                jb.put("errMsg", "服务器开了小差，请重试");
            } finally {
                input.close();
                out.close();
            }
        }
        return SUCCESS;
    }

    public String mkSaveFileName(String fileName, String savePath) {
        return savePath + File.separator + UUID.randomUUID().toString().replaceAll("-", "_") + "_" + fileName;
    }

    public String viewAllReg() {
        Page p = new Page(page, limit);
        List<Member> list = getInstance().viewAllReg(p, s_memName, s_memberId);
        jb = new JSONObject();
        JSONArray array = new JSONArray();
        if (list.size() > 0) {
            for (Member m : list) {
                String level = String.valueOf(m.getLevel());
                switch (level) {
                    case "0":
                        level = "注册未审批";
                        break;
                    case "1":
                        level = "考生";
                        break;
                    case "2":
                        level = "管理员";
                        break;
                }
                String isApproved = String.valueOf(m.getIsApproved());
                switch (isApproved) {
                    case "0":
                        isApproved = "未审批";
                        break;
                    case "1":
                        isApproved = "审批";
                        break;
                }
                jb.put("memberId", m.getMemberId());
                jb.put("memName", m.getMemName());
                jb.put("isApproved", isApproved);
                jb.put("level", level);
                array.add(jb);
            }
            jb.put("count", getInstance().countReg(s_memName, s_memberId));
            jb.put("data", array);
        } else {
            jb.put("count", 0);
            jb.put("data", "");
        }
        jb.put("code", 0);
        jb.put("msg", "");
        return SUCCESS;
    }

    public String approReg() {
        int flag = -1;
        if (StringUtil.isNotEmpty(memberIds)) {
            flag = getInstance().updateRegAppro(memberIds);
        } else {
            flag = getInstance().updateRegAppro(memberId);
        }
        jb = new JSONObject();
        if (flag > 0) {
            jb.put("success", true);
        } else {
            jb.put("success", false);
        }
        return SUCCESS;
    }

    public String getRegCount() {
        int counts = getInstance().countReg(s_memName, s_memberId);
        jb = new JSONObject();
        jb.put("counts", counts);
        return SUCCESS;
    }

    public String loginRead() {
        jb = new JSONObject();
        Student stu = (Student) ServletActionContext.getContext().getSession().get("currentStu");
        if (loginRead != null) {
            stu.setLoginRead(Integer.parseInt(loginRead));
            int flag = getInstance().updateLoginRead(stu);
            if (flag > 0) {
                System.out.println("考生第一次登陆修改状态位为1");
            }
        }
        return SUCCESS;
    }

    public String updateMemberInfo() {
        jb = new JSONObject();
        JSONArray array = new JSONArray();
        Member member = (Member) ServletActionContext.getContext().getSession().get("currentMember");
        Student student = member.getStudent();
        Admin adm = member.getAdmin();
        int flag = -1;
        if (null != updateVersion) {
            if (!password.equals(member.getMemPassword())) {
                jb.put("success", false);
                jb.put("errMsg", "原密码不对应");
            } else {
                member.setMemPassword(newPassword);
                flag = getInstance().updateMemberInfo(member);
                if (flag > 0) {
                    jb.put("success", true);
                } else {
                    jb.put("success", false);
                    jb.put("errMsg", "修改密码失败，请重试");
                }
            }
            return SUCCESS;
        }
        if (null != student) {
            if (StringUtil.isNotEmpty(editInfoRead)) {
                student.setEditInfoRead(Integer.parseInt(editInfoRead));
            }
            student.setStuName(edit_memberName);
            student.setStuSex(edit_sex);
            student.setStuPhone(edit_phone);
            student.setStuIDCard(edit_idCard);
            student.setStuAddress(edit_address);
            flag = getInstance().updateMemberInfo(student);
            if (flag > 0) {
                jb.put("success", true);
                jb.put("name", student.getStuName());
                jb.put("sex", student.getStuSex());
                jb.put("phone", student.getStuPhone());
                jb.put("idCard", student.getStuIDCard());
                jb.put("address", student.getStuAddress());
            } else {
                jb.put("errMsg", "修改失败,请重试");
            }
        } else {
            adm.setAdminName(edit_memberName);
            adm.setAdminSex(edit_sex);
            adm.setAdminPhone(edit_phone);
            adm.setAdminIDCard(edit_idCard);
            adm.setAdminAddress(edit_address);
            flag = getInstance().updateMemberInfo(adm);
            if (flag > 0) {
                jb.put("success", true);
                jb.put("success", true);
                jb.put("name", adm.getAdminName());
                jb.put("sex", adm.getAdminSex());
                jb.put("phone", adm.getAdminPhone());
                jb.put("idCard", adm.getAdminIDCard());
                jb.put("address", adm.getAdminAddress());
            } else {
                jb.put("errMsg", "修改失败,请重试");
            }
        }
        return SUCCESS;
    }
}

