package cn.sp.chapter01.regex;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配某个网页的a标签
 * Created by 2YSP on 2018/1/9.
 */
public class HrefMatch {
    public static void main(String[] args) throws Exception{
        //get URL string from command line or use default
        String urlString;
        if (args.length > 0){
            urlString = args[0];
        }else {
            urlString = "http://java.sun.com";
        }

        //open reader for URL
        InputStreamReader reader = new InputStreamReader(new URL(urlString).openStream());
        //read content into string builder
        StringBuilder input = new StringBuilder();
        int ch;
        while ((ch=reader.read())!=-1){
            input.append((char)ch);
        }
//        System.out.println(input.toString());
        //search for all occurrences of pattern
        String pathString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
        Pattern pattern = Pattern.compile(pathString,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            String match = input.substring(start,end);
            System.out.println(match);
        }


    }
}
