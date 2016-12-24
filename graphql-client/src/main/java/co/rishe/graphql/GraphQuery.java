package co.rishe.graphql;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GraphQuery class can change Java class to GraphQL string
 * Created by Bardia on 12/11/16.
 */
public class GraphQuery {
    public String getQueryString() {
        return generateQueryOf(this.getClass());
    }

    private String generateQueryOf(Class cl) {
        String s = "";

        for (Field field :
                cl.getFields()) {
            if (isInValidType(field))
                continue;
            if (isPermittedType(field.getType())) {
                if (!isFilter(field)) {
                    s += " " + field.getName() + ", ";
                }
            } else if (isListType(field.getType())) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                if(isPermittedType(listClass))
                    s += " " + field.getName() + formatFilters(getFilters(listClass));
                else
                    s += " " + field.getName() + formatFilters(getFilters(listClass)) + " { " + generateQueryOf(listClass) + " } ";
            } else {
                s += " " + field.getName() + formatFilters(getFilters(field.getType())) + " { " + generateQueryOf(field.getType()) + " } ";
            }
        }

        return s;
    }

    private boolean isInValidType(Field field) {
//        System.out.println(field.getName());
        return field.getName().contains("$");
    }

    static private boolean isPermittedType(Class cl) {
        return cl.isPrimitive() || ClassUtils.wrapperToPrimitive(cl) != null || cl.equals(String.class);
    }

    static private boolean isListType(Class cl) {
        return cl.isArray() || cl.equals(List.class);
    }

    private HashMap<String, Object> getFilters(Class cl) {
        HashMap<String, Object> filters = new HashMap<String, Object>();
        for (Field field :
                cl.getFields()) {
            if (isPermittedType(field.getType()) && isFilter(field)) {
                try {
                    field.setAccessible(true);
                    filters.put(getFilterName(field), field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return filters;
    }

    static private boolean isFilter(Field field) {
        String str = field.getName();
        int len = str.length();

        return len > 4
                && str.charAt(0) == '_'
                && str.charAt(1) == '_'
                && str.charAt(len - 2) == '_'
                && str.charAt(len - 1) == '_';
    }

    static private String getFilterName(Field field) {
        String str = field.getName();
        int len = str.length();
        return str.substring(2, len - 2);
    }

    private String formatFilters(HashMap<String, Object> filters) {
        if (filters.size() == 0)
            return "";
        else {
            String str = "( ";
            for (Map.Entry<String, Object> filter : filters.entrySet()) {
                Object value = filter.getValue();
                String valueStr;
                if (value instanceof Integer || value instanceof Double)
                    valueStr = String.valueOf(value);
                else if (value instanceof String)
                    valueStr = "\\\"" + value.toString() + "\\\"";
                else
                    throw new IllegalAccessError("Type "+ value.getClass() + " is illegal.");
                str += filter.getKey() + ": " + valueStr +", ";
            }
            return str + ")";
        }
    }
}
