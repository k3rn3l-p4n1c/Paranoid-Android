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
    private Class queryClass;
    private String queryString;

    public GraphQuery(Class queryClass) {
        this.queryClass = queryClass;
    }

    public String getQueryString() {
        if (queryString == null)
            generateQueryString();
        return queryString;
    }

    private void generateQueryString() {
        queryString = "";

        for (Field field :
                queryClass.getFields()) {
            GraphQuery child = new GraphQuery(field.getType());

            if (isFieldInvalid(field))
                continue;
            if (child.isPermittedType()) {
                if (!isFilter(field)) {
                    queryString += " " + field.getName() + ", ";
                }
            } else if (child.isListType()) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                GraphQuery childL = new GraphQuery(listClass);

                if (childL.isPermittedType()) {
                    queryString += " " + field.getName() + childL.generateFilterQuery();
                } else {
                    queryString += " " + field.getName() + childL.generateFilterQuery() + " { " + childL.getQueryString() + " } ";
                }
            } else {
                queryString += " " + field.getName() + child.generateFilterQuery() + " { " + child.getQueryString() + " } ";
            }
        }
    }

    private String generateFilterQuery() {
        HashMap<String, Object> filters = getFilters();
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
                    throw new IllegalAccessError("Type " + value.getClass() + " is illegal.");
                str += filter.getKey() + ": " + valueStr + ", ";
            }
            return str + ")";
        }
    }

    private boolean isPermittedType() {
        return queryClass.isPrimitive()
                || ClassUtils.wrapperToPrimitive(queryClass) != null
                || queryClass.equals(String.class);
    }

    private boolean isListType() {
        return queryClass.isArray() || queryClass.equals(List.class);
    }

    private HashMap<String, Object> getFilters() {
        HashMap<String, Object> filters = new HashMap<String, Object>();
        for (Field field :
                queryClass.getFields()) {
            GraphQuery child = new GraphQuery(field.getType());

            if (child.isPermittedType() && isFilter(field)) {
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


    private boolean isFieldInvalid(Field field) {
        return field.getName().contains("$") || field.getName().equals("serialVersionUID");
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
}
