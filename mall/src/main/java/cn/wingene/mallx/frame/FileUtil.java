package cn.wingene.mallx.frame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Wingene on 2017/8/12.
 */

public class FileUtil extends junze.java.util.FileUtil {

    public static String readTextfile(String path, String charsetName) {
        try {
            FileInputStream in = new FileInputStream(path);
            return readTextfile(in, charsetName);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return null;

    }

    public static String readTextfile(InputStream in, String charsetName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, charsetName));
            StringBuilder sb = new StringBuilder();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp).append("\r\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
