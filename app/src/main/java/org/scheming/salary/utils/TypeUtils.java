package org.scheming.salary.utils;

import org.scheming.salary.entity.Attendance;
import org.scheming.salary.entity.Project;

/**
 * Created by Scheming on 2015/10/26.
 */
public class TypeUtils {

    public static String toString(int type, Class clazz) {
        String re = "";
        if (clazz == Project.class) {
            switch (type) {
                case 1:
                    re = "销售提成";
                    break;
                case 2:
                    re = "实施提成";
                    break;
                case 3:
                    re = "服务提成";
                    break;
            }
        } else if (clazz == Attendance.class) {
            switch (type) {
                case 1:
                    re = "事假";
                    break;
                case 2:
                    re = "病假";
                    break;
                case 3:
                    re = "迟到";
                    break;
                case 4:
                    re = "早退";
                    break;
                case 5:
                    re = "旷工";
                    break;
            }
        }
        return re;
    }
}
