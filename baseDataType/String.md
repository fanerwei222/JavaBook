

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
                        
                      ii.表达式右边如果存在字符串引用，也就是字符串对象的句柄，那么就存放在堆里面；
                         常量字符串和变量拼接时（如：String str3=baseStr + “01”;）会调用stringBuilder.append()在堆上创建新的对象。
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
               JDK1.6中 ： str.intern()方法会先去查询常量池中是否已经存在str该字符串，
                            1)若存在 ：   返回常量池中的引用
                            2)若不存在 ： 将字符串拷贝到常量池
               JDK1.7中 ： str.intern()方法会先去查询常量池中是否已经存在str该字符串，
                            1)若存在 ：   返回常量池中的引用
                            2)若不存在 ： 不会再将字符串拷贝到常量池，而只是在常量池中生成一个对原字符串在堆上的引用

               String str1 = "aaa";
               String str2 = "bbb";
               String str3 = "aaabbb";
               String str4 = str1 + str2;
               String str5 = "aaa" + "bbb";
               System.out.println(str3 == str4); // false
               System.out.println(str3 == str4.intern()); // true
               System.out.println(str3 == str5);// true
               
### 7.final字符：
               String s1 = "abc"; 
               final String s2 = "a"; 
               final String s3 = "bc"; 
               String s4 = s2 + s3; 
               System.out.println(s1 == s4); 
               A：true，因为final变量在编译后会直接替换成对应的值，所以实际上等于s4="a"+"bc"，
                  而这种情况下，编译器会直接合并为s4="abc"，所以最终s1==s4。
                  
                  
### 8.举例：
               String str2 = new String("str")+new String("01");
               str2.intern();
               String str1 = "str01";
               System.out.println(str2==str1);
               
               在JDK 1.7下，当执行str2.intern();时，因为常量池中没有“str01”这个字符串，
               所以会在常量池中生成一个对堆中的"str01"的引用(注意这里是引用 ，
               就是这个区别于JDK 1.6的地方。在JDK1.6下是生成原字符串的拷贝)，
               而在进行String str1 = "str01";字面量赋值的时候，
               常量池中已经存在一个引用，所以直接返回了该引用，
               因此str1和str2都指向堆中的同一个字符串，返回true
               
               String str2 = new String("str")+new String("01");
               String str1 = "str01";
               str2.intern();
               System.out.println(str2==str1);
               将中间两行调换位置以后，因为在进行字面量赋值（String str1 = “str01″）的时候，
               常量池中不存在，
               所以str1指向的常量池中的位置，
               而str2指向的是堆中的对象，
               再进行intern方法时，对str1和str2已经没有影响了，
               所以返回false

### 9.经典面试题：
               Q：下列程序的输出结果： 
               String s1 = "abc"; 
               String s2 = "abc"; 
               System.out.println(s1 == s2); 
               A：true，均指向常量池中对象。

               Q：下列程序的输出结果： 
               String s1 = new String("abc"); 
               String s2 = new String("abc"); 
               System.out.println(s1 == s2); 
               A：false，两个引用指向堆中的不同对象。

               Q：下列程序的输出结果： 
               String s1 = "abc"; 
               String s2 = "a"; 
               String s3 = "bc"; 
               String s4 = s2 + s3; 
               System.out.println(s1 == s4); 
               A：false，因为s2+s3实际上是使用StringBuilder.append来完成，会生成不同的对象。

               Q：下列程序的输出结果： 
               String s1 = "abc"; 
               final String s2 = "a"; 
               final String s3 = "bc"; 
               String s4 = s2 + s3; 
               System.out.println(s1 == s4); 
               A：true，因为final变量在编译后会直接替换成对应的值，
                  所以实际上等于s4="a"+"bc"，而这种情况下，编译器会直接合并为s4="abc"，所以最终s1==s4。

               Q：下列程序的输出结果： 
               String s = new String("abc"); 
               String s1 = "abc"; 
               String s2 = new String("abc"); 
               System.out.println(s == s1.intern()); 
               System.out.println(s == s2.intern()); 
               System.out.println(s1 == s2.intern()); 
               A：false，false，true。

### 10.详细解析：
               String s = new String("abc");
               String s1 = "abc";
               String s2 = new String("abc");
               System.out.println("=============================================================");
               System.out.println("s的地址： " + java.lang.System.identityHashCode(s));
               System.out.println("s1的地址： " + java.lang.System.identityHashCode(s1));
               System.out.println("s2的地址： " + java.lang.System.identityHashCode(s2));
               System.out.println("=============================================================");
               /**
                * 此时s1.intern()返回的是s1的常量池引用地址
                * s == s1.intern() ?： false
                */
               System.out.println("s == s1.intern() ?： " + (s == s1.intern()));
               System.out.println("s的地址： " + java.lang.System.identityHashCode(s));
               System.out.println("s1的地址： " + java.lang.System.identityHashCode(s1));
               System.out.println("=============================================================");
               /**
                * 此时s2.intern()返回的是s1的常量池引用地址
                * s == s2.intern() ?： false
                */
               System.out.println("s == s2.intern() ?： " + (s == s2.intern()));
               System.out.println("s的地址： " + java.lang.System.identityHashCode(s));
               System.out.println("s2的地址： " + java.lang.System.identityHashCode(s2));
               System.out.println("=============================================================");
               /**
                * 此时s2.intern()返回的是s1的常量池引用地址
                * s1 == s2.intern() ?： true
                */
               System.out.println("s1 == s2.intern() ?： " + (s1 == s2.intern()));
               System.out.println("s1的地址： " + java.lang.System.identityHashCode(s1));
               System.out.println("s2的地址： " + java.lang.System.identityHashCode(s2));
               System.out.println("=============================================================");
               System.out.println("s的地址： " + java.lang.System.identityHashCode(s));
               System.out.println("s1的地址： " + java.lang.System.identityHashCode(s1));
               System.out.println("s2的地址： " + java.lang.System.identityHashCode(s2));
               System.out.println("=============================================================");
               
### 11.new String() + new String() 情况：
               //声明：String s = new String(字面量);字面量会和new String(字面量)同时存在；且不是
               String s = new String("1");//此时存在一个字面量 1，并且在常量池中；
               String s2 = "1";//此时的s2真实引用其实是常量池中的字面量“1”，所以和s不同
               s.intern();
               System.out.println(s == s2);//false
               String s3 = new String("1") + new String("1");
               String s4 = "11";//此时的常量池中不存在字面量“11”
               s3.intern();
               System.out.println(s3 == s4);//false

               String ss = new String("2");
               ss.intern();
               String ss2 = "2";//此时的ss2真实引用其实是常量池中的字面量“2”，所以和ss不同，因为ss.intern()发现常量池中已经有字面量“2”了就不会再生成任何变量“2”了，所以ss2和ss的地址引用是不同的。
               System.out.println(ss == ss2);//false
               String ss3 = new String("2") + new String("2");//此过程不产生字面量“22”，只产生一个字面量“2”和一个堆上变量“22”
               ss3.intern();//此处会将堆变量“22”的地址引用放入常量池
               String ss4 = "22";//此处的“22” 其实是ss3的内存地址。
               System.out.println(ss3 == ss4);//true

