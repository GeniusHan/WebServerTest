package xxx.serverStudy01;

import java.lang.reflect.InvocationTargetException;

/*
 *   #反射
 *	   反射：把Java类中的各种结构（方法、属性、构造器、类名）映射成为一个个Java对象
 *   1、获取Class对象 - 三种方式 - Class.forName("包名.类名")
 *   2、可以动态创建对象 - clz.getConstructor().newInstance();
 */
public class ReflectTest {
	
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// 三种方式
		// 1 对象.getClass()
		Iphone iphone = new Iphone();
		Class clz = iphone.getClass();
		// 2 类.Class
		clz = Iphone.class;
		// 3 Class.forName("包名.类名")
		clz = Class.forName("xxx.serverStudy01.Iphone");  
		
		//创建对象
		//Iphone iphone7 = (Iphone) clz.newInstance(); //JDK9之后不推荐使用
		Iphone iphone7 = (Iphone) clz.getConstructor().newInstance(); //JDK9推荐使用
		System.out.println(iphone7);
	}

}

class Iphone {
	public Iphone() {
		
	}
}
