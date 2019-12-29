package com.pisces.platform.core.primary.expression.function;

import com.pisces.platform.core.annotation.ELFunction;
import com.pisces.platform.core.annotation.ELParm;

class MathFunction {

    protected MathFunction() {}

    static void register(FunctionManager manager) {
        manager.registerInnerFunction(MathFunction.class);
    }

    /**
     * 取绝对值
     *
     * @param param
     * @return
     */
    @ELFunction(returnBy = 1)
    public static Object funAbs(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        if (param.getClass() == Long.class) {
            return Math.abs((long) param);
        }
        return Math.abs((double) param);
    }

    /**
     * 对弧度取sin(三角正弦)
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funSin(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.sin(value);
    }

    /**
     * 对弧度取sinh(双曲正弦)
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funSinh(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.sinh(value);
    }

    /**
     * 对弧度取cos(三角余弦)
     *
     * @param param
     * @return
     */
    @ELFunction()
    static Double funCos(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.cos(value);
    }

    /**
     * 对弧度取tan(三角正切)
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funTan(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.tan(value);
    }

    /**
     * 对弧度取tanh(双曲正切)
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funTanh(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.tanh(value);
    }

    /**
     * 对值取弧度asin(反正弦)
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funASin(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.asin(value);
    }

    /**
     * 对值取弧度acos(反余弦)
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funACos(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.acos(value);
    }

    /**
     * 对值取弧度atan(反正切)
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funATan(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.atan(value);
    }

    /**
     * 将弧度转换为角度
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funToDegrees(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.toDegrees(value);
    }

    /**
     * 将角度转换为弧度
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funToRadians(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.toRadians(value);
    }

    /**
     * 返回欧拉数 e 的n次幂
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funExp(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.exp(value);
    }

    /**
     * 计算自然对数
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funLog(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.log(value);
    }

    /**
     * 计算底数为10的对数
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funLog10(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.log10(value);
    }

    /**
     * 返回参数与1之和的自然对数
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funLog1p(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.log1p(value);
    }

    /**
     * 取整，返回小于目标数的最大整数
     *
     * @param param
     * @return
     */
    @ELFunction(returnBy = 1, options = 2)
    public static Object funFloor(@ELParm(clazz = {Long.class, Double.class}) Object param, Long digits) {
        double multiply = Math.pow(10.0, digits != null ? digits : 0.0);
        double value = 0.0;
        if (param.getClass() == Long.class) {
            value = (Long) param;
        } else if (param.getClass() == Double.class) {
            value = (Double) param;
        }
        value = Math.floor(value * multiply);
        if (digits != null) {
            if (digits > 0) {
                value /= digits;
            } else if (digits < 0) {
                value /= -digits;
            }
        }

        return param.getClass() == Long.class ? (long) value : value;
    }

    /**
     * 取整，返回大于目标数的最小整数
     *
     * @param param
     * @return
     */
    @ELFunction(returnBy = 1, options = 2)
    public static Object funCeil(@ELParm(clazz = {Long.class, Double.class}) Object param, Long digits) {
        double multiply = Math.pow(10.0, digits != null ? digits : 0.0);
        double value = 0.0;
        if (param.getClass() == Long.class) {
            value = (Long) param;
        } else if (param.getClass() == Double.class) {
            value = (Double) param;
        }
        value = Math.ceil(value * multiply);
        if (digits != null) {
            if (digits > 0) {
                value /= digits;
            } else if (digits < 0) {
                value /= -digits;
            }
        }
        return param.getClass() == Long.class ? (long) value : value;
    }

    /**
     * 四舍五入取整
     *
     * @param param
     * @return
     */
    @ELFunction(returnBy = 1, options = 2)
    public static Object funRound(@ELParm(clazz = {Long.class, Double.class}) Object param, Long digits) {
        double multiply = Math.pow(10.0, digits != null ? digits : 0.0);
        double value = 0.0;
        if (param.getClass() == Long.class) {
            value = (Long) param;
        } else if (param.getClass() == Double.class) {
            value = (Double) param;
        }
        value = Math.round(value * multiply);
        if (digits != null) {
            if (digits > 0) {
                value /= digits;
            } else if (digits < 0) {
                value /= -digits;
            }
        }
        return param.getClass() == Long.class ? (long) value : value;
    }

    /**
     * 计算平方根
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funSqrt(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.sqrt(value);
    }

    /**
     * 计算立方根
     *
     * @param param
     * @return
     */
    @ELFunction()
    public static Double funCbrt(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.cbrt(value);
    }

    /**
     * 计算立方根
     *
     * @param param1
     * @param param2
     * @return
     */
    @ELFunction()
    public static Double funPow(@ELParm(clazz = {Long.class, Double.class}) Object param1,
                                @ELParm(clazz = {Long.class, Double.class}) Object param2) {
        double value1 = param1.getClass() == Long.class ? (Long) param1 : (Double) param1;
        double value2 = param2.getClass() == Long.class ? (Long) param2 : (Double) param2;
        return Math.pow(value1, value2);
    }

    /**
     * 符号函数:如果参数为0,则返回0;如果参数大于0,则返回1.0;如果参数小于0,则返回 -1.0
     *
     * @param param
     * @return
     */
    @ELFunction()
    static Double funSignum(@ELParm(clazz = {Long.class, Double.class}) Object param) {
        double value = param.getClass() == Long.class ? (Long) param : (Double) param;
        return Math.signum(value);
    }

    /**
     * 返回一个伪随机数，该值大于等于 0.0 且小于 1.0
     *
     * @return
     */
    @ELFunction()
    public static Double funRandom() {
        return Math.random();
    }
}
