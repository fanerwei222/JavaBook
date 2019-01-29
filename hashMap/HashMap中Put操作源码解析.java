package main.base.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * HashMap 源码解析客户端
 * @author fanwei
 *在JDK1.8中，HashMap的存储结构已经发生变化，它采用数组+链表+红黑树这种组合型数据结构。
 *当hash值发生冲突时，会采用链表或者红黑树解决冲突。
 *当同一hash值的结点数小于8时，则采用链表，否则，采用红黑树。这个重大改变，主要是提高查询速度
 */
public class HashMapClient
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 362498820763181265L;
    /**
     * 默认的初始容量是16 : 1 << 4
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    /**
     * 最大容量 : 1 << 30
     */
    static final int MAXIMUM_CAPACITY = 1073741824;
    /**
     * 默认的填充因子
     * 如果HashMap的大小超过了负载因子(load factor)定义的容量，怎么办？”
     * 默认的负载因子大小为0.75，也就是说，当一个map填满了75%的bucket时候，
     * 和其它集合类(如ArrayList等)一样，将会创建原来HashMap大小的两倍的bucket数组，
     * 来重新调整map的大小，并将原来的对象放入新的bucket数组中。
     * 这个过程叫作rehashing，因为它调用hash方法找到新的bucket位置。
     * 
     * (条件竞争)在多线程的情况下使用HashMap在它rehashing过程中会产生条件竞争。
     * 
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75F;
    /**
     * 当节点上的结点数大于这个值时会转成红黑树
     */
    static final int TREEIFY_THRESHOLD = 8;
    /**
     * 当节点上的结点数小于这个值时树转链表
     */
    static final int UNTREEIFY_THRESHOLD = 6;
    /**
     *  节点中结构转化为红黑树对应的数组的最小大小，如果当前容量小于它，就不会将链表转化为红黑树，而是用resize()代替
     */
    static final int MIN_TREEIFY_CAPACITY = 64;
    /**
     * 哈希表.存储元素的数组，总是2的幂
     */
    transient Node table[];
    /**
     * 存放元素的个数，注意这个不等于数组的长度
     */
    transient int size;
    /**
     * 每次扩容和更改map结构的计数器
     */
    transient int modCount;
    /**
     * 临界值 当实际节点个数超过临界值(容量*填充因子)时，会进行扩容
     */
    int threshold;
    /**
     * 填充因子
     */
    final float loadFactor = 0F;

    public static void main(String[] args)
    {

    }

    /**
     * put方法
     * @param obj
     * @param obj1
     * @return
     */
    public Object put(Object key, Object value)
    {
        /**
         * 实际调用putVal()方法
         */
        return putVal(hash(key), key, value, false, true);
    }

    /**
     * 获取hash值
     * @param obj
     * @return
     */
    static final int hash(Object obj)
    {
        /**
         * hash计算后的值
         */
        int i;
        return obj != null ? (i = obj.hashCode()) ^ i >>> 16 : 0;
    }

    /**
     * putVal()方法
     * @param hashNumber hash计算过后得到的值，并不是真正的hash值，真正的hash值是(tableLength - 1 & hashNumber)
     * @param key 传入的key
     * @param value 传入的value
     * @param flag
     * @param flag1
     * @return
     */
    final Object putVal(int hashNumber, Object key, Object value, boolean flag, boolean flag1)
    {
        /**
         * 用来存储hash表
         */
        Node anode[];
        /**
         * hash表新的长度
         */
        int newLength;
        /**
         * 如果hash表为null或者hash表还没有元素
         */
        if ((anode = table) == null || (newLength = anode.length) == 0)
        {
            /**
             * 如果hash表为null或者hash表还没有元素
             * 就给hash表分配空间
             */
            newLength = (anode = resize()).length;
        }
        /**
         * 通过hash值找到的hash表中的以该hash值为索引的首节点对象
         */
        Object findedObj;
        int headNodeIndex;
        /**
         * (newLength - 1 & hashNumber)计算出来的是真实的hash值，也就是hash表中真实的下标索引值
         * 如果根据hash值在hash表中找不到以该hash值为索引的首节点对象，则直接新建一个以该hash值为索引的首节点
         * (不存在元素)
         */
        if ((findedObj = anode[headNodeIndex = newLength - 1 & hashNumber]) == null)
        {
            /**
             * 新建一个hash表中的以该hash值为索引的首节点
             * hash表添加方法
             */
            anode[headNodeIndex] = newNode(hashNumber, key, value, null);
        }
        /**
         * 根据hash值找到了以该hash值为索引的首节点对象(已经存在元素)
         */
        else
        {
            /**
             * 存储记录对象，把遇到的相同hash值和key的对象先存储起来
             * 以便后续需要的地方使用，也有可能不用
             */
            Object recordObj;
            /**
             * 根据hash值找到的node对象的key
             */
            Object findedObjKey;
            /**
             * 情况1：hash值相等和key相等
             * 如果在首结点与我们待插入的元素有相同的hash和key值，则先记录
             * （如果找到的首节点对象的hash值和传入的hash值相同而且它的key和传入的key引用地址是相同的，
             *  或者传入的key不为null而且传入的key会和找到的node对象的key相等） 
             */
            if (((Node) (findedObj)).hash == hashNumber
                    && ((findedObjKey = ((Node) (findedObj)).key) == key || key != null && key.equals(findedObjKey)))
            {
                recordObj = findedObj;
            }
            /**
             * 情况2：hash值不相等或key不相等
             * 如果首结点的类型是红黑树类型，则按照红黑树方法添加该元素
             */
            else if (findedObj instanceof TreeNode)
            {
                /**
                 * 红黑树方法添加或者修改该元素
                 */
                recordObj = ((TreeNode) findedObj).putTreeVal(this, anode, hashNumber, key, value);
            }
            /**
             * 情况2：hash值不相等或key不相等
             * 到这一步，说明首结点类型为链表类型
             */
            else
            {
                /**
                 * 链表长度
                 */
                int listLength = 0;
                do
                {
                    /**
                     * 遍历已经找到的对象findedObj，一直遍历到它的下一个对象为空的时候，直接先在尾部追加该元素结点
                     */
                    if ((recordObj = ((Node) (findedObj)).next) == null)
                    {
                        /**
                         * 先在尾部追加该元素结点
                         */
                        findedObj.next = newNode(hashNumber, key, value, null);
                        /**
                         * 链表长度大于等于7，就转换成树化结构
                         */
                        if (listLength >= 7)
                        {
                            /**
                             * 采取树化结构
                             */
                            treeifyBin(anode, hashNumber);
                        }
                        break;
                    }
                    /**
                     * 在链表中被找到的对象
                     */
                    Object findListObj;
                    /**
                     * 如果找到与我们待插入的元素具有相同的hash和key值的结点，则停止遍历
                     * 此时recordObj也就是我们想要找到的那个对象
                     */
                    if (((Node) (recordObj)).hash == hashNumber
                            && ((findListObj = ((Node) (recordObj)).key) == key || key != null
                                    && key.equals(findListObj)))
                    {
                        break;
                    }
                    findedObj = recordObj;
                    listLength++;
                } while (true);
            }
            /**
             * 如果记录的对象不为空,表明记录到具有相同元素的结点
             * 那么就执行value覆盖操作
             * 找到key值、hash值与插入元素相等的结点
             */
            if (recordObj != null)
            {
                /**
                 * oldValue 查找到的对象的旧的value
                 * 
                 */
                Object oldValue = ((Node) (recordObj)).value;
                /**
                 * flag默认传入为false；所以!flag总为true
                 * 或者当oldValue为null时，把传入的value值赋给记录对象
                 */
                if (!flag || oldValue == null)
                {
                    recordObj.value = value;
                }
                afterNodeAccess(((Node) (recordObj)));
                return oldValue;
            }
        }
        /**
         * 
         */
        modCount++;
        /**
         * 当结点数+1大于threshold时，则进行扩容
         */
        if (++size > threshold)
        {
            resize();
        }
        /**
         * 空函数，可以由用户根据需要覆盖
         */
        afterNodeInsertion(flag1);
        return null;
    }

    /**
     * 采取树化结构
     * @param anode
     * @param i
     */
    final void treeifyBin(Node anode[], int i)
    {
        int j;
        int k;
        Node node;
        if (anode == null || (j = anode.length) < 64)
            resize();
        else if ((node = anode[k = j - 1 & i]) != null)
        {
            TreeNode treenode = null;
            TreeNode treenode1 = null;
            do
            {
                TreeNode treenode2 = replacementTreeNode(node, null);
                if (treenode1 == null)
                {
                    treenode = treenode2;
                } else
                {
                    treenode2.prev = treenode1;
                    treenode1.next = treenode2;
                }
                treenode1 = treenode2;
            } while ((node = node.next) != null);
            if ((anode[k] = treenode) != null)
                treenode.treeify(anode);
        }
    }

    /**
     * 重新计算和分配hash表容量
     * @return
     */
    final Node[] resize()
    {
        Node anode[] = table;
        int i = anode != null ? anode.length : 0;
        int j = threshold;
        int l = 0;
        int k;
        if (i > 0)
        {
            if (i >= 1073741824)
            {
                threshold = 2147483647;
                return anode;
            }
            if ((k = i << 1) < 1073741824 && i >= 16)
                l = j << 1;
        } else if (j > 0)
        {
            k = j;
        } else
        {
            k = 16;
            l = 12;
        }
        if (l == 0)
        {
            float f = (float) k * loadFactor;
            l = k >= 1073741824 || f >= 1.073742E+009F ? 2147483647 : (int) f;
        }
        threshold = l;
        Node anode1[] = (Node[]) new Node[k];
        table = anode1;
        if (anode != null)
        {
            for (int i1 = 0; i1 < i; i1++)
            {
                Node node;
                if ((node = anode[i1]) == null)
                    continue;
                anode[i1] = null;
                if (node.next == null)
                {
                    anode1[node.hash & k - 1] = node;
                    continue;
                }
                if (node instanceof TreeNode)
                {
                    ((TreeNode) node).split(this, anode1, i1, i);
                    continue;
                }
                Node node1 = null;
                Node node2 = null;
                Node node3 = null;
                Node node4 = null;
                Node node5;
                do
                {
                    node5 = node.next;
                    if ((node.hash & i) == 0)
                    {
                        if (node2 == null)
                            node1 = node;
                        else
                            node2.next = node;
                        node2 = node;
                    } else
                    {
                        if (node4 == null)
                            node3 = node;
                        else
                            node4.next = node;
                        node4 = node;
                    }
                } while ((node = node5) != null);
                if (node2 != null)
                {
                    node2.next = null;
                    anode1[i1] = node1;
                }
                if (node4 != null)
                {
                    node4.next = null;
                    anode1[i1 + i] = node3;
                }
            }

        }
        return anode1;
    }

    /**
     * hashmap中的Node静态
     * @author fanwei
     *
     */
    static class Node implements Map.Entry
    {

        public final Object getKey()
        {
            return key;
        }

        public final Object getValue()
        {
            return value;
        }

        public final String toString()
        {
            return (new StringBuilder()).append(key).append("=").append(value).toString();
        }

        public final int hashCode()
        {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final Object setValue(Object obj)
        {
            Object obj1 = value;
            value = obj;
            return obj1;
        }

        public final boolean equals(Object obj)
        {
            if (obj == this)
                return true;
            if (obj instanceof Map.Entry)
            {
                Map.Entry entry = (Map.Entry) obj;
                if (Objects.equals(key, entry.getKey()) && Objects.equals(value, entry.getValue()))
                    return true;
            }
            return false;
        }

        final int hash;
        final Object key;
        Object value;
        Node next;

        Node(int i, Object obj, Object obj1, Node node)
        {
            hash = i;
            key = obj;
            value = obj1;
            next = node;
        }
    }

    /**
     * 红黑树类型节点
     * @author fanwei
     *
     */
    static final class TreeNode extends LinkedHashMap.Entry
    {

        public TreeNode prev;

        public TreeNode(int hash, Object key, Object value, Node node1)
        {
            // TODO Auto-generated constructor stub
        }

        /**
         * put一个红黑树节点
         * @param hashMapClient
         * @param anode
         * @param i
         * @param obj
         * @param obj1
         * @return
         */
        public Object putTreeVal(HashMapClient hashMapClient, Node[] anode, int i, Object obj, Object obj1)
        {
            // TODO Auto-generated method stub
            return null;
        }

        //代码太长，省略

        public void treeify(Node[] anode)
        {
            // TODO Auto-generated method stub

        }
    }

    /**
     * 创建一个节点对象
     * @param i
     * @param obj
     * @param obj1
     * @param node
     * @return
     */
    Node newNode(int i, Object obj, Object obj1, Node node)
    {
        return new Node(i, obj, obj1, node);
    }

    /**
     * 空方法
     * @param node
     */
    void afterNodeAccess(Node node)
    {
        //空方法
    }

    /**
     * 空方法
     * @param flag
     */
    void afterNodeInsertion(boolean flag)
    {
        //空方法
    }

    /**
     * 替换成红黑树
     * @param node
     * @param node1
     * @return
     */
    TreeNode replacementTreeNode(Node node, Node node1)
    {
        return new TreeNode(node.hash, node.key, node.value, node1);
    }

}
