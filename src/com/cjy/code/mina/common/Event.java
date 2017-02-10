package com.cjy.code.mina.common;

import java.io.Serializable;

public class Event implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -886637457203470115L;

    /**
     * 
     */

    //
    private String            classPath;

    private EventMethod       eventMethod;

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public EventMethod getEventMethod() {
        return eventMethod;
    }

    public void setEventMethod(EventMethod eventMethod) {
        this.eventMethod = eventMethod;
    }

    public Event(String classPath, String methodName, Class[] paramTypes, Object[] params) {
        this.classPath = classPath;

        EventMethod method = new EventMethod();
        method.setMethodName(methodName);
        method.setParams(params);
        method.setParamTypes(paramTypes);

        this.eventMethod = method;
    }

    public class EventMethod implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 4220124422269988359L;
        /**
         * 
         */
        private String            methodName;
        private Class[]           paramTypes;
        private Object[]          params;

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Class[] getParamTypes() {
            return paramTypes;
        }

        public void setParamTypes(Class[] paramTypes) {
            this.paramTypes = paramTypes;
        }

        public Object[] getParams() {
            return params;
        }

        public void setParams(Object[] params) {
            this.params = params;
        }

    }
}
