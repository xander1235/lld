package org.example;

import java.util.Objects;
import java.util.Stack;


public class Main {
    public static void main(String[] args) {
        System.out.println("mohit");


        Stack<String> stack = new Stack<>();


        String str="<speak>\n" +
                " Here are <say-as interpret-as=\"characters\">SSML</say-as> samples.\n" +
                " I can pause <break time=\"3s\"/>.\n" +
                " I can play a sound\n" +
                " <audio src=\"https://www.example.com/MY_MP3_FILE.mp3\">didn't get your MP3 audio file</audio>.\n" +
                " I can speak in cardinals. Your number is <say-as interpret-as=\"cardinal\">10</say-as>.\n" +
                " Or I can speak in ordinals. You are <say-as interpret-as=\"ordinal\">10</say-as> in line.\n" +
                " Or I can even speak in digits. The digits for ten are <say-as interpret-as=\"characters\">10</say-as>\n" +
                " <p><s>This is my name </s><s>Mohit</s></p>\n" +
                "</speak>";


/*
<p><s name="first">This is my name </s><s>Mohit</s></p>

    word = <s>
    res = This is my name + Mohit


    <p>
 */

        for(int i=0;i<str.length()-1;i++) {


            if(str.charAt(i)=='<' ) { // <speak>
                String word ="";
                if(str.charAt(i+1) == '/') {
                    i=i+2;
                    word="<";
                    while(str.charAt(i)!='>') {
                        word+=str.charAt(i);
                        i++;
                    }
                    word=word+'>';
                    String res="";


                    while(!stack.isEmpty()) {
                        if (((stack.peek().split(" ").length > 1 && Objects.equals(stack.peek().split(" ")[0]+'>', word)) || Objects.equals(stack.peek(), word))) {
                            break;
                        }
                        String tmp = stack.pop();
                        res = tmp+res;
                    }
                    if(!stack.isEmpty()) {
                        String temp = stack.pop();
                        if (temp.split(" ").length > 1) {
                            String attr = temp.split(" ")[1].split("=")[1];
                            System.out.println("Applying attribute: " + attr.substring(0, attr.length() - 1));
                        }
                    }
                    stack.push(res);
                } else {
                    while(str.charAt(i)!='>') {
                        word=word+str.charAt(i);
                        i++;
                    }
                    stack.push(word + str.charAt(i));

                }


            } else {
                String newString= "";
                while(i<str.length()-1 &&str.charAt(i)!='<') {
                    newString +=str.charAt(i);
                    i++;
                }
                stack.push(newString);
                i=i-1;
            }


        }

        System.out.println(stack.peek());

    }
}
