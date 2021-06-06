package com.example.the_health_compass;

public class Affine {
    public static String Encryption(String s, int Key1, int Key2) {
        String result = "";
        int index;
        int[] Key = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        char[] LowerCase = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] UpperCase = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char []txt = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if(txt[i]>=97 && txt[i]<=122){
                for(int j = 0;j<Key.length;j++){
                    if(txt[i] ==LowerCase[j]){
                        index = ((Key1*Key[j])+Key2)%26;
                        result +=LowerCase[index];
                        break;
                    }
                }
            }
            else if(txt[i]>=65 && txt[i]<=90){
                for (int j= 0;j<Key.length;j++){
                    if(txt[i]==UpperCase[j]){
                        index = ((Key1*Key[j])+Key2)%26;
                        result += UpperCase[j];
                        break;
                    }
                }
            }
            else {
                result +=txt[i];
            }
        }
        return result;
    }

    public static String Decode(String s, int Key1, int Key2) {
        String result="";

        return result;
    }
}
