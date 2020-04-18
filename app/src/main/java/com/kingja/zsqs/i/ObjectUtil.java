package com.kingja.zsqs.i;


import android.util.Log;

import com.kingja.zsqs.net.entiy.FileInfo;
import com.kingja.zsqs.net.entiy.FileItem;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/2/25 0025 下午 4:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ObjectUtil {

    /**
     * 获取属性名数组
     * */
    public static String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
//            System.out.println(fields[i].getType());
//            System.out.println(fields[i].getName());
//
//            System.out.println(fieldValueByName);
//            fieldNames[i]=fields[i].getName();

            if ("FileList".equals(fields[i].getName())) {
             List<FileItem> infoList = (List<FileItem>) getFieldValueByName(fields[i].getName(), o);
                Log.e("dddd", "infoList: "+infoList );
            }
        }
        return fieldNames;
    }

    private static Object getFieldValueByName(String filedName, Object o) {
        try {
            String firstLetter = filedName.substring(0,1).toUpperCase();
            String getter = "get"+firstLetter+filedName.substring(1);
            Method method =o.getClass().getMethod(getter,new Class[]{});
            Object value = method.invoke(o,new Object[]{});
            return value;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
