package com.pisces.core.exception;

/**
 *  异常基类，所有自定义异常均由此类派生。
 *  一下列出Java内置的异常，使用此类异常时不应暴露给用户，且不需要进行多语言化。
 *  ArithmeticExecption-算数异常类
 *  NullPointerException-空指针异常
 *  ClassCastException-类型强制转换
 *  NegativeArrayException-数组负下标
 *  ArrayIndexOutOfBoundsException-数组下标越界
 *  SecturityException-违背安全原则
 *  EOFException-文件已结束
 *  FileNotFoundException-文件未找到
 *  SQLException-操作数据库异常
 *  IOException-输入输出异常
 *  NoSuchMethodException-方法未找到
 *  IndexOutOfBoundsExecption-下标越界
 *  SystemException-系统异常
 *  NegativeArraySizeException-创建一个大小为负数的数组错误
 *  NumberFormatException-数据格式异常
 *  UnsupportedOperationException-不支持的操作
 *  NetworkOnMainThreadException-网络操作在主线程
 *  IllegalStateException-请求状态异常
 *  HttpHostConnectException-网络请求异常
 *  IllegalArgumentException-非法参数异常
 *  ZeroException-参数不能小于0异常
 * @author Jason
 *
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7541134573166923226L;

	public BaseException() {
		super();
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseException(Throwable cause) {
		super(cause);
	}
}