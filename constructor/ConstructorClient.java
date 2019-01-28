package main.base.constructor;

/**
 * 构造函数客户端
 * @author fanwei
 * 构造代码块执行顺序：
 * 静态代码块 --> 构造代码块 --> 构造函数
 */
public class ConstructorClient
{
    /**
     * 静态代码块
     */
    static
    {
        System.out.println("执行静态代码块...");
    }

    /**
     * 构造代码块1
     */
    {
        System.out.println("执行构造代码块1...");
    }

    /**
     * 构造代码块2
     */
    {
        System.out.println("执行构造代码块2...");
    }

    /**
     * 无参构造函数
     */
    public ConstructorClient()
    {
        System.out.println("执行无参构造函数...");
    }

    /**
     * 有参构造函数
     * @param id
     */
    public ConstructorClient(String id)
    {
        System.out.println("执行有参构造函数...");
    }

    public static void main(String[] args)
    {
        System.out.println("----------------------");
        new ConstructorClient();
        System.out.println("----------------------");
        new ConstructorClient("1");
    }
}

/**
 * 执行结果:
 * 执行静态代码块...
 * ----------------------
 * 执行构造代码块1...
 * 执行构造代码块2...
 * 执行无参构造函数...
 * ----------------------
 * 执行构造代码块1..
 * 执行构造代码块2...
 * 执行有参构造函数...
 */

