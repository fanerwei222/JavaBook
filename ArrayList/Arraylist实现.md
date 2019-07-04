
### 1.  ArrayList 动态数组，底层使用数组保存所有元素
#### 属性：
		transient Object[] elementData;数据的数组，transient表示不参与序列化过程
		private int size;实际数据的数量
	
#### 构造方法：
		private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
		public ArrayList(){
			this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
		}
#### 默认情况下初始化空数组：
		private static final Object[] EMPTY_ELEMENTDATA = {};
		public ArrayList(int initialCapacity){
			if(initialCapacity > 0){
				this.elementData = new Object[initialCapacity];
			}else if(initialCapacity == 0){
				this.elementData = EMPTY_ELEMENTDATA;
			}else{
				throw new IllegalArgumentException("初始化容量不能小于0！");
			}
		}
#### 初始化指定集合的数组：
		public ArrayList(Collection<? extends E> c){
			elementData = c.toArray();
			if((size = elementData.length) != 0 ){
				if(elementData.getClass() != Object[].class){
					elementData = Arrays.copyOf(elementData, size, Object[].class);
				}
			}else{
				this.elementData = EMPTY_ELEMENTDATA;
			}
		}
#### 常用方法：
		1) E get(int index); 获取index位置的元素
			E elementData(int index){
				//返回index下标的元素且强制转化为E（List<E>中的E）类型
				return (E) elementData[index];
			}
			public E get(int index){
				//检查index是否越界
				rangeCheck(index);
				//返回index下标的元素
				return elementData(index);
			}
			public void rangeCheck(int index){
				// 检查index是否大于等于size（数组的元素数量），因为数组下标从0开始计算，所以也不能等于元素数量
				// 这里没有检查index < 0的情况，因为index < 0时数组会自动抛出异常，所以并未检查index<0的情况
				if(index >= size){
					throw new IndexOutOfBoundsException("下标越界！");
				}
			}
		2) E set(int index, E element);设置（覆盖）index位置的元素
			public E set(int index, E element){
				rangeCheck(index);
				
				E oldValue = elementData[index];
				elementData[index] = element;
				return oldValue;
			}
		3) boolean add(E e) 添加一个元素到表尾
			private static final int DEFAULT_CAPACITY = 10;  // 默认容量为10
			private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;  // 数组最大容量为Integer最大值-8
			public boolean add(E e){
				// 检查当前容量是否还可以容纳一个元素，不够则扩容
				ensureCapacityInternal(size + 1);
				
				// 添加到数组末尾
				// 这个语句可以分解为
				// elementData[size] = e;
				// size += 1;
				elementData[size++] = e;
				return true;
			}
			private void ensureCapacityInternal(int minCapacity){
				ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
			}
			// 如果数据等于默认数据，返回默认容量和minCapacity（所需容量最小值）中的最大值，反之返回所需容量最小值
			private static int calculateCapacity(Object[] elementData, int minCapacity){
				if(elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA){
					return Math.max(DEFAULT_CAPACITY, minCapacity);
				}
				return minCapacity;
			}
			private void ensureExplicitCapacity(int minCapacity){
				modCount++; //操作数+1
				if(minCapacity - elementData.length > 0){
					// 如果所需容量最小值大于实际数组的长度就扩大实际数组容量
					grow(minCapacity);
				}
			}
			private void grow(int minCapacity){
				//旧数组容量
				int oldCapacity = elementData.length;
				// >>1 表示无符号右移一位，减半；新的容量为旧的容量的1.5倍
				int newCapacity = oldCapacity + (oldCapacity >> 1);
				//如果扩充容量后还是不够，则新的容量等于所需容量最小值（一般就是数组实际元素个数）
				if(newCapacity - minCapacity < 0){
					newCapacity = minCapacity;
				}
				// 如果新的容量大于数组最大容量，再调用hugeCapacity计算新的容量
				if(newCapacity - MAX_ARRAY_SIZE > 0){
					newCapacity = hugeCapacity(minCapacity);
				}
				// 复制原来的数据到新的数组，数组容量为新的容量
				elementData = Arrays.copyOf(elementData, newCapacity);
			}
			private static int hugeCapacity(int minCapacity){
					if(minCapacity < 0){
						throw new OutOfMemoryError();
					}
					// 大于数组最大容量返回Integer最大值，反之返回数组最大容量
					return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
				}
		4) void add(int index , E element);  在index处放置元素
			public void add(int index, E element){
				//检查下标是否越界
				rangeCheckForAdd(index);
				// 检查当前容量是否还可以再容纳一个元素，不够则扩容
				ensureCapacityInternal(size + 1);
				// 将elementData从index开始后面的元素往后移一位
				System.arraycopy(elementData, index, elementData, index + 1, size - index);
				elementData[index] = element;
				size++;
			}
			private void rangeCheckForAdd(int index){
				// 当index等于size时相当于添加元素到列表尾
				if(index > size || index < 0){
					throw new IndexOutOfBoundsException("下标越界");
				}
			}
		5) boolean addAll(Collection<? extends E> c);  添加一个集合里的所有元素到列表尾
			public boolean addAll(Collection<? extends E> c) {
				Object[] a = c.toArray();
				int numNew = a.length;
				// 检查当前容量是否还可以在容纳a数组的元素，不够则扩容
				ensureCapacityInternal(size + numNew);  // Increments modCount
				// 将a数组里的元素添加到elementData末尾
				System.arraycopy(a, 0, elementData, size, numNew);
				size += numNew;
				// a数组不为空（长度不为0）时返回true，反之false
				return numNew != 0;
			}
		6) boolean addAll(int index, Collection<? extends E> c);  添加一个集合里的所有元素到index位置
			public boolean addAll(int index, Collection<? extends E> c) {
				// 检查下标是否越界
				rangeCheckForAdd(index);

				Object[] a = c.toArray();
				int numNew = a.length;
				// 检查当前容量是否还可以在容纳a数组的元素，不够则扩容
				ensureCapacityInternal(size + numNew);  // Increments modCount

				// 需要往后移动几个位置
				int numMoved = size - index;
				if (numMoved > 0)
					// 从index开始，往后的元素向后移动numMoved个位置
					System.arraycopy(elementData, index, elementData, index + numNew,
							numMoved);
				// 将a数组里的所有元素复制到elementData从index到index + numNew -1的位置上
				System.arraycopy(a, 0, elementData, index, numNew);
				size += numNew;
				// a数组不为空（长度不为0）时返回true，反之false
				return numNew != 0;
			}
		7) void trimToSize();  改变列表内部数组容量至列表实际元素数量
			public void trimToSize() {
				modCount++;  // 操作数+1
				// 如果数组实际元素数量小于数组长度
				if (size < elementData.length) {
					// 如果数组实际元素数量等于0则数组被赋值为空数组，反之创建一个新的元素数量等于数组长度的数组
					elementData = (size == 0)
							? EMPTY_ELEMENTDATA
							: Arrays.copyOf(elementData, size);
				}
			}
		8) int indexOf(Object o);  查找o元素在列表第一次出现的位置
			public int indexOf(Object o){
				//元素可以为null，如果为null返回null的下标
				if(o == null){
					for(int i = 0; i > size; i++){
						if(elementData[i] == null){
							return i;
						}
					}
				}else{
					for(int i = 0; i < size; i++){
						if(o.equals(elementData[i])){
							return i;
						}
					}
				}
				// 没有找到对应的元素返回-1
				return -1;
			}
		9) E remove(int index);  删除index位置上的元素
			public E remove(int index){
				// 检查下标是否越界
				rangeCheck(index);

				modCount++;  // 操作数+1
				E oldValue = elementData(index);  // 获取index位置上的元素

				int numMoved = size - index - 1;  // 需要往前移动几个位置
				if (numMoved > 0){
					// 从index + 1开始，往后的元素向前移动1个位置
					System.arraycopy(elementData, index+1, elementData, index,
							numMoved);
				}
				// 将数组末尾元素置空
				elementData[--size] = null; // clear to let GC do its work

				return oldValue;
			}
		10) boolean remove(Object o);  删除o元素
			public boolean remove(Object o){
				//元素可以为null，分开搜索
				if(o == null){
					for(int index = 0; index < size; index++){
						if(elementData[index] == null){
							fastRemove(index);
							return true;
						}
					}
				}else{
					for(int index = 0; index < size; index++){
						if(o.equals(elementData[index])){
							fastRemove(index);
							return true;
						}
					}
				}
				// 没有找到返回false
				return false;
			}
			// 由于已经找到元素，则元素必定存在，则index必定合理，所以不需要在检查index是否越界
			private void fastRemove(int index){
				modCount++;
				int numMoved = size - index - 1;
				if(numMoved > 0){
					System.arraycopy(elementData, index + 1, elementData, index, numMoved);
				}
				elementData[--size] = null;
			}
		11) forEach(Consumer<? super E> action);遍历列表
			protected transient int modCount = 0;//操作数
			public void forEach(Consumer<? super E> action){
				Objects.requireNonNull(action);
				final int expectedModCount = modCount;
				final E[] elementData = (E[]) this.elementData;
				final int size = this.size;
				for(int i=0; modCount == expectedModCount && i < size; i++){
					action.accept(elementData[i]);
				}
				这里可以看到modCount的用处，当modCount发生改变后，立刻抛出ConcurrentModificationException异常。通过之前的分析可以知道当列表内容被修改时modCount会增加。也就是说如果在遍历ArrayList的过程中有其他线程修改了ArrayList，那么将抛出ConcurrentModificationException异常
				if(modCount != expectedModCount){
					throw new ConcurrentModificationException();
				}
			}
			public static <T> T requireNonNull(T obj){
				if (obj == null){
					throw new NullPointerException();
				}
				return obj;
			}
			
### ArrayList小结：
			ArrayList是List接口的一个可变大小的数组的实现
			ArrayList的内部是使用一个Object对象数组来存储元素的
			初始化ArrayList的时候，可以指定初始化容量的大小，如果不指定，就会使用默认大小，为10
			当添加一个新元素的时候，首先会检查容量是否足够添加这个元素，如果够就直接添加，如果不够就进行扩容，扩容为原数组容量的1.5倍
			当在index处放置一个元素的时候，会将数组index处右边的元素全部右移
			当在index处删除一个元素的时候，会将数组index处右边的元素全部左移
			
### 主要关键点：
① Object[] 数组
② 检查下标是否越界
③ 扩容
④ 拷贝（Arrays.copyOf和System.arraycopy）
⑥ modCount操作数
