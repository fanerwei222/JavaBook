

## ** Integer 和 int 

### 1.自动装箱 和 自动拆箱
              自动装箱： Integer a = 128; ---> Integer a = Integer.valueOf(128);  
                        //直接给Integer对象赋值会自动把128这个数值装进Integer对象。
              
              自动拆箱： Integer a = new Integer(128);
                        int x  = a; ---> int m = a.intValue();
                        //直接赋值给int类型变量的Integer对象会被自动拆箱成int类型的数值。
      
### 2.默认值
              Integer 默认值是null；
              int 默认值是0；
### 3.存储类型
              Integer是对象，用一个引用指向这个对象；
              int 是基本数据类型，直接存储数值；

### 4.equals 和 == 
              ==(自动进行类型转换)  ：   
                       如果作用于基本数据类型的变量，则直接比较其存储的 “值”是否相等；
                       如果作用于引用类型的变量，则比较的是所指向的对象的地址；
                       Long x = 3L;
                       int y = 3;
                       x == y;//true ---> x首先会拆箱成long类型，long类型与int类型的y比较则y会向上转换成long类型
                       ==运算符能将隐含的将小范围的数据类型(int)转换为大范围的数据类型(long/double)，
                       也就是int会被转换成long类型
                    
              equals(不会进行类型转换)： 
                       如果没有对equals方法进行重写，则比较的是引用类型的变量所指向的对象的地址；
                       诸如String、Date等类对equals方法进行了重写的话，比较的是所指向的对象的内容
                       
              /**
               *Integer equals方法源码
               */
              public boolean equals(Object obj)
              {
                  if(obj instanceof Integer)
                      /**
                       *此处比较的是两个int类型的数值
                       */
                      return value == ((Integer)obj).intValue();
                  else
                      return false;
              }

### 5.缓存
              Integer 类的valueOf()方法;
              Integer i = 10;
              在 i >= -128 并且 i <= 127 的时候，
              第一次声明会将 i 的值放入缓存中，
              第二次直接取缓存里面的数据，而不是重新创建一个Ingeter 对象;

### 6.new关键字
              通过 new 关键字来创建的两个对象，是不存在缓存的概念的。
              两个用new关键字创建的对象用 == 进行比较肯定是false；
              Integer m = new Integer(10);
              Integer n = new Integer(10);
              System.out.println(m == n);//false

### 7.运算

### 8.比较
              例子中的：<情况2>;
              int和Integer(无论new Integer否)比，都为true，
              因为会把Integer自动拆箱为int再去比较；

### 9.例子：
        Integer x1 = new Integer(1);
        Integer x2 = 1;
        int x3 = 1;
        int x4 = new Integer(1);

        Integer x11 = new Integer(200);
        Integer x21 = 200;
        int x31 = 200;
        int x41 = new Integer(200);

        System.out.println(x1 == x2);//false  -------> 情况1
        System.out.println(x1 == x3);//true  ------->情况2
        System.out.println(x2 == x3);//true  ------->情况3
        System.out.println(x3 == x4);//true  ------->情况4
        System.out.println(x2 == x4);//true  ------->情况5

        System.out.println(x11 == x21);//false  ------->情况6
        System.out.println(x11 == x31);//true  ------->情况7
        System.out.println(x21 == x31);//true  ------->情况8
        System.out.println(x31 == x41);//true  ------->情况9
        System.out.println(x21 == x41);//true  ------->情况10
        
        ***-------------------------------------------------------***
        
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;

        Integer e = 321;
        Integer f = 321;

        Long g = 3L;
        Long h = 2L;

        System.out.println(c == d);--------------------->true
        System.out.println(e == f);--------------------->false
        System.out.println(c == (a + b));--------------->true "==比较符又将左边的自动拆箱，因此它们比较的是数值是否相等"
        System.out.println(c.equals((a+b)));------------>true
        System.out.println(g == (a+b));----------------->true
        System.out.println(g.equals(a+b));-------------->false
        System.out.println(g.equals(a+h));-------------->true

### 10.扩展理解
        int和任意Integer都是同一地址                        
        Integer只在127范围内才是同地址，超出就false              
        new Integer()与Integer,new Integer()无论什么数范围 都不同地址
