package ir.ttic.web.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Utils {
    public static String readWholeFile(FileInputStream fis) throws IOException {
        byte[] buffer = new byte[(int) 10E6]; // 1mg
        int i =0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (i!=-1){
            i = fis.read(buffer);
            bos.write(buffer);
        }
        return new String(bos.toByteArray());
    }


    public static Object JSONStringToObject(String source,Class<?> type) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(source,type);
    }
}
