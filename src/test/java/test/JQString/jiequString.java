package test.JQString;

import org.apache.commons.lang3.StringUtils;

public class jiequString {
    public static void main(String[] args) {
        String path="group1/M00/00/00/tlxkUl6IP2uAACOVAAOFnOIZA_Q958.pdf";
        System.out.println(StringUtils.substringBefore(path,"/"));
        System.out.println(StringUtils.substringAfter(path,"/"));
    }
}
