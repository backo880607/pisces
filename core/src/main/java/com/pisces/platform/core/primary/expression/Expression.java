package com.pisces.platform.core.primary.expression;

import com.pisces.platform.core.config.CoreMessage;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.exception.ExpressionException;
import com.pisces.platform.core.primary.expression.calculate.*;
import com.pisces.platform.core.primary.expression.function.BaseFunction;
import com.pisces.platform.core.utils.IExpression;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Expression implements IExpression {
    public ExpressionNode root = null;
    public boolean hasField = false;

    public boolean parse(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        Entry<Integer, ExpressionNode> entry = this.create(str, 0, false);
        if (entry.getKey() < str.length()) {
            return false;
        }

        this.root = entry.getValue();
        return getReturnClass() != null;
    }

    @Override
    public Object getValue() {
        return this.getValue(null);
    }

    @Override
    public Object getValue(EntityObject entity) {
        try {
            return this.getValueImpl(entity);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean getBoolean() {
        return this.getBoolean(null);
    }

    @Override
    public boolean getBoolean(EntityObject entity) {
        Object value = this.getValue(entity);
        if (value == null) {
            return false;
        }
        if (value.getClass() != Boolean.class) {
            throw new ExpressionException(CoreMessage.NotSupportOperation, value.getClass().getSimpleName() + " to Boolean");
        }

        return (Boolean) value;
    }

    @Override
    public String getString() {
        return this.getString(null);
    }

    @Override
    public String getString(EntityObject entity) {
        Object value = this.getValue(entity);
        return value != null ? BaseFunction.funToStr(value, null) : "";
    }

    public Object getValueImpl(EntityObject entity) {
        if (this.root == null) {
            throw new NullPointerException();
        }
        return this.getValueImpl(this.root, entity);
    }

    private Object getValueImpl(ExpressionNode node, EntityObject entity) {
        if (node.type == OperType.DATA) {
            return node.calculate.getValue(entity);
        }

        Object lValue = null;
        if (node.lchild != null) {
            lValue = this.getValueImpl(node.lchild, entity);
        }

        Object rValue = null;
        if (node.rchild != null) {
            rValue = this.getValueImpl(node.rchild, entity);
        }

        return node.<OperTypeCalculate>getCalculate().getValue(lValue, rValue, entity);
    }

    public Class<?> getReturnClass() {
        if (root == null) {
            return null;
        }
        return getReturnClassImpl(root);
    }

    private Class<?> getReturnClassImpl(ExpressionNode node) {
        if (node.type == OperType.DATA) {
            return node.calculate.getReturnClass();
        }

        Class<?> lClass = null;
        if (node.lchild != null) {
            lClass = this.getReturnClassImpl(node.lchild);
        }
        Class<?> rClass = null;
        if (node.rchild != null) {
            rClass = this.getReturnClassImpl(node.rchild);
        }
        return node.<OperTypeCalculate>getCalculate().getReturnClass(lClass, rClass);
    }

    public Entry<Integer, ExpressionNode> create(String str, int index, boolean bFun) {
        boolean bError = false;
        ExpressionNode root = null;
        this.hasField = false;
        while (index < str.length()) {
            char curChar = str.charAt(index);
            if (Character.isSpaceChar(curChar)) {
                ++index;
                continue;
            }

            if (curChar == '#') {
                Calculate cal = new DateTimeCalculate();
                index = cal.parse(str, index);
                if (index < 0) {
                    bError = true;
                    break;
                }

                if (root == null)
                    root = new ExpressionNode(OperType.DATA, cal, null, null);
                else if (root.rchild == null)
                    root.rchild = new ExpressionNode(OperType.DATA, cal, null, null);
                else
                    root.rchild.rchild = new ExpressionNode(OperType.DATA, cal, null, null);
            } else if (curChar == '\'') {
                Calculate cal = new TextCalculate();
                index = cal.parse(str, index);
                if (index < 0) {
                    bError = true;
                    break;
                }

                if (root == null)
                    root = new ExpressionNode(OperType.DATA, cal, null, null);
                else if (root.rchild == null)
                    root.rchild = new ExpressionNode(OperType.DATA, cal, null, null);
                else
                    root.rchild.rchild = new ExpressionNode(OperType.DATA, cal, null, null);
            } else if (curChar == '(') {
                Calculate expCalc = new BracketCalculate();
                index = expCalc.parse(str, index);
                if (index == -1) {
                    bError = true;
                    break;
                }
                if (root == null)
                    root = new ExpressionNode(OperType.DATA, expCalc, null, null);
                else if (root.rchild == null)
                    root.rchild = new ExpressionNode(OperType.DATA, expCalc, null, null);
                else
                    root.rchild.rchild = new ExpressionNode(OperType.DATA, expCalc, null, null);
            } else if (curChar == ')') {
                break;
            } else if (curChar == ',') {
                if (!bFun)
                    bError = true;
                ++index;
                break;
            } else if (Character.isDigit(curChar)) {
                Entry<Integer, Calculate> cal = this.getNumberCalculate(str, index);
                index = cal.getKey();
                if (index < 0) {
                    bError = false;
                    break;
                }

                if (root == null)
                    root = new ExpressionNode(OperType.DATA, cal.getValue(), null, null);
                else if (root.rchild == null)
                    root.rchild = new ExpressionNode(OperType.DATA, cal.getValue(), null, null);
                else
                    root.rchild.rchild = new ExpressionNode(OperType.DATA, cal.getValue(), null, null);
            } else {
                Entry<Integer, OperType> calType = this.getCallType(str, index);
                index = calType.getKey();
                if (calType.getValue() != OperType.DATA) {
                    Calculate cal = new OperTypeCalculate(calType.getValue());
                    if (root == null)
                        root = new ExpressionNode(calType.getValue(), cal, null, null);
                    else if (calType.getValue().value() < root.type.value())    // 优先级更高
                        root.rchild = new ExpressionNode(calType.getValue(), cal, root.rchild, null);
                    else // 作为优先级更小的左子树
                        root = new ExpressionNode(calType.getValue(), cal, root, null);
                } else {
                    // 自定义函数调用
                    Entry<Integer, Calculate> cal = this.getFunOrFieldCalculate(str, index);
                    index = cal.getKey();
                    if (index < 0) {
                        bError = true;
                        break;
                    }

                    if (root == null)
                        root = new ExpressionNode(OperType.DATA, cal.getValue(), null, null);
                    else if (root.rchild == null)
                        root.rchild = new ExpressionNode(OperType.DATA, cal.getValue(), null, null);
                    else
                        root.rchild.rchild = new ExpressionNode(OperType.DATA, cal.getValue(), null, null);
                }
            }
        }

        if (bError) {
            root = null;
        }

        return new AbstractMap.SimpleEntry<Integer, ExpressionNode>(index, root);
    }

    private Entry<Integer, OperType> getCallType(String str, int index) {
        OperType operType = OperType.DATA;
        switch (str.charAt(index)) {
            case '+':
                operType = OperType.PLUS;
                break;
            case '-':
                operType = OperType.MINUS;
                break;
            case '*':
                operType = OperType.MULTIPLIED;
                break;
            case '/':
                operType = OperType.DIVIDED;
                break;
            case '&':
                if (str.charAt(++index) == '&') {
                    operType = OperType.AND;
                }
                break;
            case '|':
                if (str.charAt(++index) == '|') {
                    operType = OperType.OR;
                }
                break;
            case '=':
                if (str.charAt(++index) == '=') {
                    operType = OperType.EQUAL;
                }
                break;
            case '!':
                if (str.charAt(index + 1) == '=') {
                    operType = OperType.NOTEQUAL;
                    ++index;
                } else {
                    operType = OperType.NOT;
                }
                break;
            case '>':
                if (str.charAt(index + 1) == '=') {
                    operType = OperType.GREATEREQUAL;
                    ++index;
                } else {
                    operType = OperType.GREATER;
                }
                break;
            case '<':
                if (str.charAt(index + 1) == '=') {
                    operType = OperType.LESSEQUAL;
                    ++index;
                } else {
                    operType = OperType.LESS;
                }
                break;
        }

        int rtIndex = operType != OperType.DATA ? index + 1 : index;
        return new AbstractMap.SimpleEntry<Integer, OperType>(rtIndex, operType);
    }

    /**
     * 解析除整数或者浮点数
     *
     * @param str
     * @param index
     * @return
     */
    private Entry<Integer, Calculate> getNumberCalculate(String str, int index) {
        int temp = index;
        do {
            ++index;
        } while (index < str.length() && Character.isDigit(str.charAt(index)));

        if (index < str.length() && str.charAt(index) == '.') {
            if (!Character.isDigit(str.charAt(++index))) {
                //ApplicationUtil.log.error("浮点数格式错误");
                return new AbstractMap.SimpleEntry<Integer, Calculate>(-1, null);
            }

            do {
                ++index;
            } while (index < str.length() && Character.isDigit(str.charAt(index)));
            DoubleCalculate calculate = new DoubleCalculate();
            calculate.value = Double.valueOf(str.substring(temp, index));
            return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
        }

        LongCalculate calculate = new LongCalculate();
        calculate.value = Long.valueOf(str.substring(temp, index));
        return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
    }

    private Entry<Integer, Calculate> getFunOrFieldCalculate(String str, int index) {
        int startIndex = index;
        int temp = index;
        while (temp < str.length()) {
            if (str.charAt(temp) == '.') {
                Calculate calculate = new EnumClaculate();
                index = calculate.parse(str, index);
                if (index == -1) {    // 非枚举类型
                    calculate = new PropertyCalculate();
                    index = calculate.parse(str, startIndex);
                    if (index == -1) {
                        break;
                    }
                    this.hasField = true;
                }

                return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
            } else if (str.charAt(temp) == '(') {
                Calculate calculate = null;
                String name = str.substring(startIndex, temp);
                if (name.equalsIgnoreCase("Reverse")) {
                    calculate = new ReverseCalculate();
                    ++temp;
                    index = calculate.parse(str, temp);
                } else {
                    calculate = new FunctionCalculate();
                    index = calculate.parse(str, index);
                }

                if (index == -1) {
                    break;
                }

                return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
            }

            if (!Character.isAlphabetic(str.charAt(temp)) && !Character.isDigit(str.charAt(temp)) && str.charAt(temp) != '_') {
                Calculate calculate = new EntityCalculate();
                index = calculate.parse(str, index);
                if (index == -1) {
                    break;
                }

                return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
            }
            ++temp;
        }
        return new AbstractMap.SimpleEntry<Integer, Calculate>(-1, null);
    }

    @Override
    public <T extends EntityObject> void filter(List<T> entities) {
        Iterator<T> iter = entities.iterator();
        while (iter.hasNext()) {
            T entity = iter.next();
            if (!this.getBoolean(entity)) {
                iter.remove();
            }
        }
    }

    @Override
    public int compare(EntityObject o1, EntityObject o2) {
        Object value1 = this.getValueImpl(o1);
        Object value2 = this.getValueImpl(o2);
        if (value1 == null) {
            if (value2 == null) {
                return o1.getId() < o2.getId() ? -1 : 1;
            }
            return -1;
        }

        if (value2 == null) {
            return 1;
        }

        if ((boolean) BaseFunction.equal(value1, value2)) {
            return o1.getId() < o2.getId() ? -1 : 1;
        }
        return (boolean) BaseFunction.less(value1, value2) ? -1 : 1;
    }
}
