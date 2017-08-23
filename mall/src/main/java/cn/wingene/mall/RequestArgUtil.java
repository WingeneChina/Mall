package cn.wingene.mall;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junze.androidxf.http.requestargs.ArgsResult;
import junze.androidxf.http.requestargs.RequestArgs;

/**
 * Created by Wingene on 2017/8/23.
 */

public class RequestArgUtil {
    public static ArgsResult parseArgs(Object inputArgs) {
        Map<String, Object> params = new HashMap<String, Object>();
        Set<String> removes = new HashSet<String>();
        Class<?> aClass = inputArgs.getClass();
        while ((aClass != Object.class)){
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    RequestArgs args = field.getAnnotation(RequestArgs.class);
                    if (args != null) {
                        switch (args.value()) {
                        case HTTP_PARAMS:
                            params.put(field.getName(), field.get(inputArgs));
                            // no break;
                        case IGNORE:
                            removes.add(field.getName());
                            break;
                        default:
                            break;
                        }
                    }
                    field.setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            aClass = aClass.getSuperclass();
        }
        return new ArgsResult(params, removes);
    }
}
