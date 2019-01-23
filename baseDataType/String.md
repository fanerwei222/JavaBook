

## String 字符串

### 1.创建方式：
              ** 直接赋值方式创建对象是在方法区的常量池
              String str="hello";//直接赋值的方式
              ** 通过构造方法创建字符串对象是在堆内存
              String str=new String("hello");//实例化的方式

### 2.== 和 equals ：
              ==:
               * 基本数据类型：比较的是基本数据类型的值是否相同
               * 引用数据类型：比较的是引用数据类型的地址值是否相同
               * 所以在这里的话：String类对象==比较，比较的是地址，而不是内容
              equals:
               * 如果没有对equals方法进行重写，则比较的是引用类型的变量所指向的对象的地址；
                 诸如String、Date等类对equals方法进行了重写的话，比较的是所指向的对象的内容

### 3.比较 ：
              String str1 = "Lance";
              String str2 = new String("Lance");
              String str3 = str2; //引用传递，str3直接指向st2的堆内存地址
              String str4 = "Lance";
              /**
               *  ==:
               * 基本数据类型：比较的是基本数据类型的值是否相同
               * 引用数据类型：比较的是引用数据类型的地址值是否相同
               * 所以在这里的话：String类对象==比较，比较的是地址，而不是内容
               */
               System.out.println(str1==str2);//false
               System.out.println(str1==str3);//false
               System.out.println(str3==str2);//true
               System.out.println(str1==str4);//true

### 4.例子 ：
            
