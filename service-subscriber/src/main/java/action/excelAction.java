package action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
import service.IExamService;
import util.DateUtil;
import util.EnumTypeFormat;
import remote.vo.ExamType;
import remote.vo.SignUpStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller("excelAction")
public class excelAction extends ActionSupport {
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
    public String output() {
        List<Object[]> list = ExamAction.getInstance().outPutExams(ids);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet hs = wb.createSheet("报名审批表");
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFRow r1 = hs.createRow(0);
        HSSFCell c0 = r1.createCell(0);
        c0.setCellValue("审批列表");
        c0.setCellStyle(cellStyle);
        hs.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        hs.setColumnWidth(1, 30 * 255);
        hs.setColumnWidth(2, 30 * 255);
        hs.setColumnWidth(3, 30 * 255);
        hs.setColumnWidth(4, 30 * 255);
        HSSFRow r2 = hs.createRow(1);
        HSSFCell c1 = r2.createCell(0);
        c1.setCellStyle(cellStyle);
        c1.setCellValue("审批ID");
        HSSFCell c2 = r2.createCell(1);
        c2.setCellStyle(cellStyle);
        c2.setCellValue("考试名");
        HSSFCell c3 = r2.createCell(2);
        c3.setCellStyle(cellStyle);
        c3.setCellValue("报名考生");
        HSSFCell c4 = r2.createCell(3);
        c4.setCellStyle(cellStyle);
        c4.setCellValue("开始时间");
        HSSFCell c5 = r2.createCell(4);
        c5.setCellStyle(cellStyle);
        c5.setCellValue("结束时间");
        HSSFCell c6 = r2.createCell(5);
        c6.setCellStyle(cellStyle);
        c6.setCellValue("类别");
        HSSFCell c7 = r2.createCell(6);
        c7.setCellStyle(cellStyle);
        c7.setCellValue("审批状态");
        int i = 2;
        for (Object[] objects : list) {
            for (Object o : objects) {
                if (o instanceof SignUpStatus) {
                    HSSFRow r3 = hs.createRow(i);
                    HSSFCell c11 = r3.createCell(0);
                    c11.setCellStyle(cellStyle);
                    c11.setCellValue(((SignUpStatus) o).getsId());
                    HSSFCell c12 = r3.createCell(1);
                    c12.setCellStyle(cellStyle);
                    c12.setCellValue(((SignUpStatus) o).getExam().getExamName());
                    HSSFCell c13 = r3.createCell(2);
                    c13.setCellStyle(cellStyle);
                    c13.setCellValue(((SignUpStatus) o).getStudent().getStuName());
                    HSSFCell c14 = r3.createCell(3);
                    c14.setCellStyle(cellStyle);
                    c14.setCellValue(DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", ((SignUpStatus) o).getExam().getExamBeginTime()));
                    HSSFCell c15 = r3.createCell(4);
                    c15.setCellStyle(cellStyle);
                    c15.setCellValue(DateUtil.formatDateToStr("yyyy-MM-dd hh:mm:ss", ((SignUpStatus) o).getExam().getExamEndTime()));
                    HSSFCell c16 = r3.createCell(5);
                    c16.setCellStyle(cellStyle);
                    Set<ExamType> et = ((SignUpStatus) o).getExam().getExamTypes();
                    for (ExamType ett : et) {
                        c16.setCellValue(ett.getType_examName());
                    }
                    HSSFCell c17 = r3.createCell(6);
                    c17.setCellStyle(cellStyle);
                    c17.setCellValue(EnumTypeFormat.formatApproToStr(((SignUpStatus) o).getAppro_stat()));
                    i++;
                }
            }
        }
        String filename = this.mkUniqueName("报名审批表");
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream output = null;
        try {
            response.reset();
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
            response.setContentType("application/msexcel");
            output = response.getOutputStream();
            wb.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String mkUniqueName(String name) {
        return new Date().getTime() + "_" + name;
    }
}
