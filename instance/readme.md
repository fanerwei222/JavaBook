

## instanceof 
            1.obj.instanceof(class) 表示对象obj是否是class类或其子类的对象
            2.在编译时编译器需要知道类的具体类型
            3.用来在运行时指出对象是否是特定类的一个实例
            4.用法： 
              result = object instanceof class 
              参数： 
              Result：布尔类型。 
              Object：必选项。任意对象表达式。 
              Class：必选项。任意已定义的对象类。 
              说明： 
              如果 object 是 class 的一个实例，则 instanceof 运算符返回 true。
              如果 object 不是指定类的一个实例，或者 object 是 null，则返回 false。
              
              例子 ： 
              例子：

              接口Person

              public interface Person {
              public void eat();
              }

              实现类People

              public class People implements Person {
              private int a=0;
               @Override
               public void eat() {
                System.out.println("======"+a);

               }

              }

              子类xiaoming：

              public class xiaoming extends People {
              private String name;

              @Override
              public void eat() {
               System.out.println("+++++++++");
              }
              }

              主函数

              public static void main(String[] args) {
                People p=new People();
                xiaoming x=new xiaoming();
                System.out.println(p instanceof Person);
                System.out.println(p instanceof xiaoming); -----2
                System.out.println(x instanceof Person);
                System.out.println(x instanceof People);
               }

              注意：上面2处的代码在编译时不会报错。

              运行结果：

              true
              false
              true
              true
              
## isInstance
             1.class.isInstance(obj) 对象obj能否转化为类class的对象
             2.编译器在运行时才进行类型检查，故可用于反射，泛型中
             3.动态等价性
                        class Father{}
                        class Son extends Father{}

                        public class Test{
                            public static boolean DynamicEqual(Object fatherObj,Object sonObj){
                                return fatherObj.getClass().isInstance(sonObj); // pass
                                // return sonObj instanceof Father; // pass
                                // return sonObj instanceof (fatherObj.getClass()); //error
                            }
                            public static void main(String[] args){
                                //same using
                                Father father = new Father();
                                Son son = new Son();
                                System.out.println(son instanceof Son); // true
                                System.out.println(son instanceof Father); // true
                                System.out.println(son instanceof Object); // true
                                System.out.println(null instanceof Object); // false
                                System.out.println();

                                System.out.println(Son.class.isInstance(son)); // true
                                System.out.println(Father.class.isInstance(son)); // true
                                System.out.println(Object.class.isInstance(son)); // true
                                System.out.println(Object.class.isInstance(null)); // false
                                System.out.println();
                                //different using
                                System.out.println(DynamicEqual(father, son));
                            }
                        }
             
