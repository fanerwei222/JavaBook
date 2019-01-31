

##  a=a+b和a+=b的区别

        byte x = 5;
        x = (byte) (x + 666);//需要进行强转，不然右边的(x+666)会自动转换成int类型，不能直接赋值给byte类型
        System.out.println(x);//x==-97
        byte y = 10;
        y += 10;//不需要强转，+=符号会自动进行类型转换，超过byte类型范围会溢出,不会报错
        System.out.println(y);//y==20
