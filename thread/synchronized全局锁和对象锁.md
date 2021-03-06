

## synchronized 全局锁和对象锁

### 对象锁 ：
        synchronized(this) 以及非 static 的 synchronized 方法，只能防止多个线程同时执行同一个实例对象的同步代码块。
        即 synchronized 锁住的是括号里的对象，而不是代码块。获取到的非static方法的synchronized方法实际获取到的都是该方法所属
        实例对象的锁。
        例如：线程A里面有一个synchronized方法methodA，那么一个类B获取到methodA的同步锁就代表它获取到了线程类A的实例对象A*的锁，
        如果其他类C、B、D想要执行A*里面除了methodA以外其他的方法，则需要等到B释放了methodA的锁（也就是A*的锁）才行。
        
        记住：锁住的是对象。
        类比：厕所里有个电视机，某人上厕所时关上了锁，其他人也不能进来看电视
        
### 全局锁 ：
        synchronized(xxxx.class) 
        或者 
        public static synchronized test() {
            // statement
        }
        都可以实现全局锁。
        每次只能由一个对象进入该类。
        这个类里面的属性，方法都是同步的。
        
### 对象锁和类锁的区别：
        对象锁可以理解为是实例锁，
        类锁可以理解为是静态类锁，
        实例 != 静态类 
        类的实例和静态类的编译时期和内存地址都是不同的。
        下面举例：
        
        pulbic class Something {
            public synchronized void isSyncA(){}
            public synchronized void isSyncB(){}
            public static synchronized void cSyncA(){}
            public static synchronized void cSyncB(){}
        }
        假设，Something有两个实例x和y,分析下面4组表达式获取的锁的情况;
        (01) x.isSyncA()与x.isSyncB() --> 实例x调用isSyncA方法，则实例x被锁，x.isSyncB()需要等到实例x的锁被释放才能执行。
        (02) x.isSyncA()与y.isSyncA() -->实例x调用isSyncA方法，则实例x被锁，实例y调用isSyncA方法，则实例y被锁，两个不同的实例不会相互影响。
        (03) x.cSyncA()与y.cSyncB() -->实例x调用静态方法cSyncA，则实际调用的是Something.cSyncA()，
                                        所以锁住的是Something这个类，不影响其它实例化的对象，y调用的是静态方法cSyncB，
                                        则实际调用的是Something.cSyncB()，所以锁住的是Something这个类，
                                        所以x、y需要获取的锁相同，所以不可以同时进行。
        (04) x.isSyncA()与Something.cSyncA() -->实例x调用的是isSyncA方法，Something调用的是静态方法cSyncA，此处实例x和类Something不会相互影响。
        
        01**不能被同时访问**
        02**可以同时被访问**
        03**不能被同时访问**
        04**可以被同时访问**
