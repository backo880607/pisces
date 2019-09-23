package com.pisces.core.primary.expression;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.pisces.core.config.CoreMessage;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.exception.ExpressionException;
import com.pisces.core.primary.expression.calculate.BracketCalculate;
import com.pisces.core.primary.expression.calculate.Calculate;
import com.pisces.core.primary.expression.calculate.DateTimeCalculate;
import com.pisces.core.primary.expression.calculate.DoubleCalculate;
import com.pisces.core.primary.expression.calculate.EnumClaculate;
import com.pisces.core.primary.expression.calculate.PropertyCalculate;
import com.pisces.core.primary.expression.calculate.FunctionCalculate;
import com.pisces.core.primary.expression.calculate.LongCalculate;
import com.pisces.core.primary.expression.calculate.EntityCalculate;
import com.pisces.core.primary.expression.calculate.OperTypeCalculate;
import com.pisces.core.primary.expression.calculate.ReverseCalculate;
import com.pisces.core.primary.expression.calculate.TextCalculate;
import com.pisces.core.primary.expression.value.Type;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueBoolean;
import com.pisces.core.primary.expression.value.ValueDouble;
import com.pisces.core.primary.expression.value.ValueHelp;
import com.pisces.core.primary.expression.value.ValueInt;
import com.pisces.core.primary.expression.value.ValueText;
import com.pisces.core.utils.IExpression;

public class Expression implements IExpression {
	public ExpressionNode root = null;
	public boolean hasField = false;
	
	/**
	 * 解析字符串表达式
	 * @param str
	 * @return
	 */
	public boolean Parse(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		}
		
		Entry<Integer, ExpressionNode> entry = this.Create(str, 0, false);
		if (entry.getKey() < str.length()) {
			return false;
		}
		
		this.root = entry.getValue();
		return getReturnClass() != null;
	}
	
	/**
	 * 计算表达式值
	 * @return
	 */
	@Override
	public Object getValue() {
		return this.getValue(null);
	}
	
	/**
	 * 计算表达式值，涉及对象的计算
	 * @param Id
	 * @return
	 */
	@Override
	public Object getValue(EntityObject entity) {
		ValueAbstract valueAbstract = this.getValueAbstract(entity);
		return valueAbstract != null ? valueAbstract.getValue() : null;
	}
	
	/**
	 * 计算表达式结果是否为真
	 * @return
	 */
	@Override
	public boolean getBoolean() {
		return this.getBoolean(null);
	}
	
	/**
	 * 计算表达式结果是否为真，涉及对象计算
	 * @return
	 */
	@Override
	public boolean getBoolean(EntityObject entity) {
		Object value = this.getValue(entity);
		if (value == null) {
			return false;
		}
		if (value.getClass() != Boolean.class) {
			throw new ExpressionException(CoreMessage.NotSupportOperation, value.getClass().getSimpleName() + " to Boolean");
		}
		
		return (Boolean)value;
	}
	
	/**
	 * 计算表达式结果为字符串
	 * @return
	 */
	@Override
	public String getString() {
		return this.getString(null);
	}
	
	@Override
	public String getString(EntityObject entity) {
		ValueAbstract valueAbstract = this.getValueAbstract(entity);
		if (valueAbstract != null) {
			ValueText text = valueAbstract.toText();
			if (text != null) {
				return text.value;
			}
		}
		return "";
	}
	
	public ValueAbstract getValueAbstract(EntityObject entity) {
		if (this.root == null) {
			return null;
		}
		
		return this.getValue(this.root, entity);
	}
	
	private ValueAbstract getValue(ExpressionNode node, EntityObject entity) {
		if (node.type == OperType.DATA) {
			return node.calculate.GetValue(entity);
		}
		
		List<ValueAbstract> params = new ArrayList<>();
		if (node.lchild != null) {
			params.add(this.getValue(node.lchild, entity));
		}
		if (node.rchild != null) {
			params.add(this.getValue(node.rchild, entity));
		}
		
		return node.<OperTypeCalculate>getCalculate().GetValue(params, entity);
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
		
		List<ValueAbstract> params = new ArrayList<>();
		if (node.lchild != null) {
			Class<?> param1 = this.getReturnClassImpl(node.lchild);
			if (param1 == null) {
				return null;
			}
			params.add(ValueHelp.get(param1));
		}
		if (node.rchild != null) {
			Class<?> param2 = this.getReturnClassImpl(node.rchild);
			if (param2 == null) {
				return null;
			}
			params.add(ValueHelp.get(param2));
		}
		
		return node.<OperTypeCalculate>getCalculate().GetValue(params, null).getReturnClass();
	}
	
	public Entry<Integer, ExpressionNode> Create(String str, int index, boolean bFun) {
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
				index = cal.Parse(str, index);
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
				index = cal.Parse(str, index);
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
				index = expCalc.Parse(str, index);
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
				Entry<Integer, Calculate> cal = this.GetNumberCalculate(str, index);
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
				Entry<Integer, OperType> calType = this.GetCallType(str, index);
				index = calType.getKey();
				if (calType.getValue() != OperType.DATA) {
					Calculate cal = new OperTypeCalculate(calType.getValue());
					if (root == null)
						root = new ExpressionNode(calType.getValue(), cal, null, null);
					else if (calType.getValue().value() < root.type.value())	// 优先级更高
						root.rchild = new ExpressionNode(calType.getValue(), cal, root.rchild, null);
					else // 作为优先级更小的左子树
						root = new ExpressionNode(calType.getValue(), cal, root, null);
				} else {
					// 自定义函数调用
					Entry<Integer, Calculate> cal = this.GetFunOrFieldCalculate(str, index);
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
	
	private Entry<Integer, OperType> GetCallType(String str, int index) {
		OperType operType = OperType.DATA;
		switch (str.charAt(index)) 
		{
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
	 * @param str
	 * @param index
	 * @return
	 */
	private Entry<Integer, Calculate> GetNumberCalculate(String str, int index) {
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
			calculate.value = new ValueDouble(Double.valueOf(str.substring(temp, index)));
			return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
		}
		
		LongCalculate calculate = new LongCalculate();
		calculate.value = new ValueInt(Long.valueOf(str.substring(temp, index)));
		return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
	}
	
	private Entry<Integer, Calculate> GetFunOrFieldCalculate(String str, int index) {
		int startIndex = index;
		int temp = index;
		while (temp < str.length()) {
			if (str.charAt(temp) == '.') {
				Calculate calculate = new EnumClaculate();
				index = calculate.Parse(str, index);
				if (index == -1) {	// 非枚举类型
					calculate = new PropertyCalculate();
					index = calculate.Parse(str, startIndex);
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
					index = calculate.Parse(str, temp);
				} else {
					calculate = new FunctionCalculate();
					index = calculate.Parse(str, index);
				}
				
				if (index == -1) {
					break;
				}
				
				return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
			}
			
			if (!Character.isAlphabetic(str.charAt(temp)) && !Character.isDigit(str.charAt(temp)) && str.charAt(temp) != '_') {
				Calculate calculate = new EntityCalculate();
				index = calculate.Parse(str, index);
				if (index == -1) {
					break;
				}
				
				return new AbstractMap.SimpleEntry<Integer, Calculate>(index, calculate);
			}
			++temp;
		}
		return new AbstractMap.SimpleEntry<Integer, Calculate>(-1, null);
	}
	
	/**
	 * 刷选对象集合
	 * @param entities
	 * @return
	 */
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
	
	/**
	 * 比较两对象
	 * @param entity
	 * @return
	 */
	@Override
	public int compare(EntityObject o1, EntityObject o2) {
		ValueAbstract value1 = this.getValueAbstract(o1);
		ValueAbstract value2 = this.getValueAbstract(o2);
		if (value1.getType() == Type.None) {
			return value2.getType() == Type.None ? 0 : -1; 
		}
		
		if (value2.getType() == Type.None) {
			return 1;
		}
		
		if (((ValueBoolean)value1.equal(value2)).value) {
			return 0;
		}
		return ((ValueBoolean)value1.less(value2)).value ? -1 : 1;
	}
}
