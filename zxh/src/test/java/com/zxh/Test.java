package com.zxh;

public class Test {
 static  StringBuffer stringBuffer = new StringBuffer();

    public static void main(String[] args) {

            stringBuffer.append("aaaaa");
            stringBuffer.setLength(0);
            stringBuffer.append("bbbb");
        System.out.println(stringBuffer.toString());
    }

}
