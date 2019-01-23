

## String 字符串

### 1.创建方式：
              ** 直接赋值方式创建对象是在方法区的常量池
              String str="hello";//直接赋值的方式
              ** 通过构造方法创建字符串对象是在堆内存
              String str=new String("hello");//实例化的方式
              ** 对字符串进行拼接操作，也就是做"+"运算的时候，
                *分2中情况：
                      i.表达式右边是纯字符串常量，那么存放在栈里面。
                        *如：
                          String str1 = "abcd";
                          String str2 = "ab";
                          String str3 = "cd";
                          String str4 = "ab" + "cd";
                         这里的str4就是纯字符常量，所以str4放在栈里面。
                        
                      ii.表达式右边如果存在字符串引用，也就是字符串对象的句柄，那么就存放在堆里面
                        *如：
                          String str1 = "abcd";
                          String str2 = "ab";
                          String str3 = "cd";
                          String str4 = str2 + str3;
                         这里的str4就存在字符串对象引用，引用了str2和str3，所以str4放在堆里面。

### 2.== 和 equals ：
              ==:
               * 基本数据类型：比较的是基本数据类型的值是否相同
               * 引用数据类型：比较的是引用数据类型的地址值是否相同
               * 所以在这里的话：String类对象==比较，比较的是地址，而不是内容
              equals:
               * 如果没有对equals方法进行重写，则比较的是引用类型的变量所指向的对象的地址；
                 诸如String、Date等类对equals方法进行了重写的话，比较的是所指向的对象的内容
                 
              *****||>>>   ==在对字符串比较的时候，对比的是内存地址，而equals比较的是字符串内容   <<<||*****

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

### 4.直接赋值方式创建String对象:
               String str = "jeky";
               会将匿名对象“jeky”放入对象池，每当下一次对不同的对象进行直接赋值的时候会直接利用池中原有的匿名对象;

### 5.new方式创建String ：
               String str = new String("hello");
               当执行new String("hello")语句时，new 关键字还没有生效，括号中的 "hello"会先被产生到堆内存中，此时这个"hello"1是匿名对象"hello"1;
               当new关键字生效时，此时会产生一个新的字符串对象"hello"2，str直接指向"hello"2的堆内存地址,此时"hello"1变为垃圾；
               
### 6.String的intern()方法：
               String str1 = "aaa";
               String str2 = "bbb";
               String str3 = "aaabbb";
               String str4 = str1 + str2;
               String str5 = "aaa" + "bbb";
               System.out.println(str3 == str4); // false
               System.out.println(str3 == str4.intern()); // true
               System.out.println(str3 == str5);// true
               
            
