

## 设计模式概括

###  ！！！！类比三种类别模式的作用：
             ***以公司（创建型）、部门（对象）、公司的组织结构（结构型）、部门负责的事情和其他部门的交互（行为型）为例子。
                        1."公司"可以看成是创建型，它可以创建部门
                        2."公司的组织结构"可以看成是结构型，它可以随意组织人事部、财务部、运营部的人员分布或者说场地分布等等
                        3."部门负责的事情和其他部门的交互"可以看成是行为型，
                           它体现了各个部门之间的配合以及各个部门负责的事情，人事部门负责招聘，
                           和人员工资单整理，财务部负责人员工资结算和发放处理，人事部需要和财务部分别负责各自的事情，
                           但是人事部整理完工资单需要把工资单交给财务部进行工资发放等行为处理，
                           这里就涉及到了部门之间的交互，各自的职责之内也会涉及到与别的部门交互的情况。

### 一 ： 创建型 ： 涉及对象的实例化，由模式创建实例，避免用户通过new创建实例（对象的创建）
                1.工厂方法模式
                2.抽象工厂方法模式
                3.单例模式
                4.建造者模式
                        定义：将一个复杂对象的构造与它的表示分离，使同样的构建过程可以创建不同的表示，这样的设计模式被称为建造者模式。
                        个人理解：（需要将复杂对象的构造和最终形态（根据构造的顺序或者构造步骤的多少形成不同的样子）分离开。）
                        适用场景：   1.多构造器
                                    2.方法执行顺序可形成不同形态的对象
                                    3.所要实现的对象十分复杂
                        具体实现： 
                                    //要实现的对象X
                                    Class ObjectX{
                                          String a;
                                          String b;
                                          String c;
                                          setter/getter;
                                    }
                                    //建造方法接口
                                    Interface CreateMethod{
                                          //方法1-3调换顺序可以生成不同形态的对象
                                          part_one();
                                          part_two();
                                          part_three();
                                          //创建对象
                                          ObjectX createObject();
                                    }
                                    //实现接口的类MethodImpl
                                    Class MethodImpl implements CreateMethod{
                                          ObjectX x;
                                          @Override
                                          part_one(){
                                                //do something;
                                                x.setter;
                                          };
                                          @Override
                                          part_two(){
                                                //do something;
                                                x.setter;
                                          };
                                          @Override
                                          part_three){
                                                //do something;
                                                x.setter;
                                          };
                                          //创建对象
                                          @Override
                                          ObjectX createObject(){
                                                return x;
                                          };
                                    }
                                    
                                    //执行器---通过执行器来创建一个对象
                                    Class Director{
                                          ObjectX createObjectXByDirector(CreateMethod method){
                                                method.part_one();
                                                method.part_two();
                                                method.part_three();
                                                
                                                return method.createObject();
                                          }
                                    }
                5.原型模式

### 二 ： 结构型 ： 组合类和对象形成新的结构。类有关的多用继承，对象有关的多用对象组合（类或对象之间的组织关系）。
                1.适配器模式 ： 
                        适用场景：解决两个不同的接口A、B之间兼容性的问题。
                        a.类适配器 : 通过让适配器类实现A接口和继承B接口的某个实现类以达到A接口使用B接口中的方法。
                                    Interface A;
                                    Interface B;
                                    Class C implements B;
                                    Class Adapter extends C implements A;
                                    Adapter调用C中的方法;
                        b.对象适配器 : 通过组合实现适配器功能
                                    Interface A;
                                    Interface B;
                                    Class C implements B;
                                    Class Adapter implements A{
                                          B b;
                                          Adapter(B b){
                                                this.b = b;
                                          }
                                          Method(){
                                                b.doSomething();
                                          }
                                    };
                                    Adapter调用C中的方法;
                        c.接口适配器 : 普通类通过继承一个实现了有很多方法的接口的抽象父类来调用该接口部分方法的方式。
                                    Interface A{
                                          method_one();
                                          method_two();
                                          method_three();
                                    };
                                    abstract Class B implements A{
                                          @Override
                                          method_one(){
                                                //do something;
                                          };
                                          @Override
                                          method_two(){
                                                //do something;
                                          };
                                          @Override
                                          method_three(){
                                                //do something;
                                          };
                                    };
                                    Class C extends B{
                                          //只重写method_one方法
                                          @Override
                                          method_one(){
                                                //do something;
                                          };
                                    }
                2.装饰器模式
                        适用场景：动态的修饰一个对象。用对象之间的关联关系取代类之间的继承关系，扩充新功能。
                        具体实现：
                                    //一个统一接口，装饰类和被装饰类都要实现的基本类型
                                    Interface Component{
                                          //共同的方法
                                          method_toge();
                                    }
                                    //装饰类
                                    Class Decorator implements Component{
                                          //传入的被装饰类
                                          Component cpt;
                                          Decorator(Component cpt){
                                                this.cpt = cpt;
                                          }
                                          @Override
                                          method_toge(){
                                                //do something;
                                                cpt.method_toge();
                                          }
                                    }
                                    //被装饰类
                                    Class ConcreteComponent implements Component{
                                          //被装饰类本身的执行方法
                                          @Override
                                          method_toge(){
                                                //do something;
                                                sysout("do something");
                                          }
                                    }
                                    //具体装饰类
                                    Class ConcreteDecorator extends Decorator{
                                          //构造器传入具体被装饰类
                                          ConcreteDecorator(Component cpt){
                                                super(cpt);
                                          }
                                          @Override
                                          method_toge(){
                                                //重写父类的方法，在下面的方法前执行一些扩展操作
                                                <!-- 此处执行某些扩展操作 -->
                                                cpt.method_toge();
                                          }
                                    }
                3.代理模式
                4.外观模式
                5.桥接模式
                        适用场景：一个类需要使用到两个独立维度（功能形态完全不相关）的对象。
                        具体实现：
                                    //维度X
                                    Interface X{
                                          //X维度的方法,Param由相关的维度实现类去选择
                                          method_xone(Param);
                                    };
                                    //维度Y
                                    abstract Class Y{
                                          //组合X
                                          X x;
                                          method_setX(X x){
                                                this.x = x;
                                          }
                                          //Y维度的方法
                                          abstract method_yone();
                                    };
                                    Class z extends Y{
                                          @Override
                                          method_yone(){
                                                //直接调用Y中的x，Param可传入与z有关的东西
                                                x.method_xone(Param);
                                          };
                                    }
                6.组合模式
                        使用场景：处理树形结构。一致性地处理树形结构中的叶子节点（不包含子节点的节点）和容器节点（包含子节点的节点）
                        具体实现：
                                    //定义个抽象父类，里面会有子类共有属性，并且可以提供一个构造器或者setter/getter方法
                                    abstract Class Component{
                                          protected String name;
                                          protected String desc;
                                          Component(String name, String desc){
                                                this.name = name;
                                                this.desc = desc;
                                          }
                                          //只有容器才有的方法
                                          abstract method_one(Component c);
                                          abstract method_two(Component c);
                                          //叶子和容器共有的方法
                                          abstract method_opration();
                                    }
                                    //定义一个容器构件
                                    Class Composite extends Component{
                                          List<Component> list = new List<Component>;
                                          @Override
                                          abstract method_one(Component c){
                                                //例如添加构件
                                                list.add(c);
                                          }
                                          @Override
                                          abstract method_two(Component c){
                                                //例如删除构件
                                                list.remove(c);
                                          }
                                          @Override
                                          abstract method_opration(){
                                                //容器构件具体业务方法的实现  ；
                                                //例如 ： 递归调用成员构件的业务方法
                                                for (Component component : list)
                                                {
                                                    component.operation();
                                                }
                                          }
                                    }
                                    //定义一个叶子构件
                                    Class Leaf extends Component{
                                          @Override
                                          abstract method_one(){
                                                //抛出异常或者其他处理
                                          }
                                          @Override
                                          abstract method_two(){
                                                //抛出异常或者其他处理
                                          }
                                          @Override
                                          abstract method_opration(){
                                                //叶子构件具体业务方法的实现  
                                          }
                                    }
                7.享元模式

### 三 ： 行为型 ： 关注对象之间的通信，关注类或对象间的交互和职责分配。（职责和行为）
                1.策略模式
                2.模板方法模式
                3.观察者模式
                4.迭代子模式
                5.责任链模式
                6.命令模式
                        适用场景：高级对象给中级对象下命令，中级对象命令低级对象处理问题。（高中低之间的通信问题）
                        具体实现：
                                    //低级对象
                                    Class Low{
                                          doWork(){
                                                //do work;
                                          };
                                    }
                                    //中级对象
                                    Class Middle{
                                          //会收到的命令
                                          Command cmd;
                                          //set命令进来
                                          setter/getter;
                                          //执行命令方法（假执行，实际是叫低级对象去执行）
                                          action(){
                                                cmd.execute();
                                          }
                                    }
                                    //命令
                                    abstract Class Command{
                                          //实际执行者
                                          Low low;
                                          //执行命令
                                          abstract execute();
                                    }
                                    //具体命令
                                    Class ConcreteCommand extends Command{
                                          //构造器传入Low对象
                                          ConcreteCommand(Low low){
                                                super.low = low;
                                          }
                                          @Override
                                          execute(){
                                                //直接调用父类的low
                                                low.doWork();
                                          }
                                    }
                                    
                7.备忘录模式
                8.状态模式
                9.访问者模式
                10.中介者模式
                11.解释器模式
                
                
                
### 四 ：其中一些模式之间的区别
                1.装饰者模式 VS 代理模式
                        使用代理模式，代理和真实对象之间的的关系通常在编译时就已经确定了，而装饰者能够在运行时递归地被构造。
                        装饰模式应该为所装饰的对象增强功能；代理模式对代理的对象施加控制，并不提供对象本身的增强功能。
                        
                
