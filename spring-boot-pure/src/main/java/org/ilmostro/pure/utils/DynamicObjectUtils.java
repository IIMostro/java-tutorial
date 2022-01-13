package org.ilmostro.pure.utils;

import com.google.common.collect.Maps;
import javassist.*;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author li.bowei
 */
public class DynamicObjectUtils {
    public static <T> Class<T> prepareClass(String className, List<String> fields) throws CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass evalClass = pool.makeClass(className + UUID.randomUUID().toString());
        for ( String field : fields) {
            CtField ctField = CtField.make("public java.lang.String " + field + ";", evalClass);
            evalClass.addField(ctField);
            evalClass.addMethod(CtNewMethod.getter("get" + StringUtils.capitalize(field), ctField));
        }
        //noinspection unchecked
        return (Class<T>) evalClass.toClass();
    }

    public static <T> Object createObject(Class<T> clazz, List<String> fields, Map<String, Object> values) {
        try {
            Object obj = clazz.newInstance();
            for (String field : fields) {
                ReflectionUtils.setField(clazz.getField(field), obj, values.get(field));
            }
            return obj;
        } catch (Exception e) {
            return new BeanCreationException(String.format("class name: %s", clazz));
        }
    }



    public static Object getTarget(Object dest, Map<String, Object> addProperties) {
        PropertyUtilsBean propertyUtilsBean =new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class<?>> propertyMap = Maps.newHashMap();
        for(PropertyDescriptor d : descriptors) {
            if(!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // add extra properties
        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
        // new dynamic bean
        DynamicBean dynamicBean =new DynamicBean(dest.getClass(), propertyMap);
        // add old value
        propertyMap.forEach((k, v) -> {
            try{
                // filter extra properties
                if(!addProperties.containsKey(k)) {
                    dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(dest, k));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        // add extra value
        addProperties.forEach((k, v) -> {
            try{
                dynamicBean.setValue(k, v);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        return dynamicBean.getTarget();
    }

    public static class DynamicBean {
        /**
         * 目标对象
         */
        private final Object target;

        /**
         * 属性集合
         */
        private final BeanMap beanMap;

        public DynamicBean(Class<?> superclass, Map<String, Class<?>> propertyMap) {
            this.target = generateBean(superclass, propertyMap);
            this.beanMap = BeanMap.create(this.target);
        }


        /**
         * bean 添加属性和值
         *
         * @param property
         * @param value
         */
        public void setValue(String property, Object value) {
            beanMap.put(property, value);
        }

        /**
         * 获取属性值
         *
         * @param property
         * @return
         */
        public Object getValue(String property) {
            return beanMap.get(property);
        }

        /**
         * 获取对象
         *
         * @return
         */
        public Object getTarget() {
            return this.target;
        }


        /**
         * 根据属性生成对象
         *
         * @param superclass
         * @param propertyMap
         * @return
         */
        private Object generateBean(Class<?> superclass, Map<String, Class<?>> propertyMap) {
            BeanGenerator generator =new BeanGenerator();
            if(null != superclass) {
                generator.setSuperclass(superclass);
            }
            BeanGenerator.addProperties(generator, propertyMap);
            return generator.create();
        }
    }
}
