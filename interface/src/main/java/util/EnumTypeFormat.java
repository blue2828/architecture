package util;

public class EnumTypeFormat {
    public static String formatLevelToStr(int level) {
        String str = "";
        switch (level) {
            case 0:
                str = "注册未审核";
                break;
            case 1:
                str = "考生";
                break;
            case 2:
                str = "系统管理员";
                break;
        }
        return str;
    }

    public static String formatApproToStr(int isApproved) {
        String str = "";
        switch (isApproved) {
            case 0:
                str = "未审批";
                break;
            case 1:
                str = "审批";
                break;
        }
        return str;
    }
}
