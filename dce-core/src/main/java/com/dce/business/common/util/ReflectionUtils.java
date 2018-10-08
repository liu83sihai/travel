package com.dce.business.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

    /**
     * @param m
     * @param deafultErrorValue
     * @param paObject
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Method m,T deafultErrorValue,Object obj){
        try {
            return (T) m.invoke(obj);
        } catch (IllegalAccessException e) {
            
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return deafultErrorValue;
    }

    public static <E> E invokeMethod(Object obj, String string, E deafultErrorValue) {
        
        try {
            return invokeMethod(obj.getClass().getMethod(string),deafultErrorValue,obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return deafultErrorValue;
    }

}
