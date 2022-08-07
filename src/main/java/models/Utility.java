package models;

import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public abstract class Utility {
    private static HashMap<String,Image> images = new HashMap<>();
    public static String encodeImageFile(File file){
        if (file == null)
            return "-1";
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = fis.readAllBytes();
            String imageString = Base64.getEncoder().encodeToString(data);
            fis.close();
            return imageString;
        }catch (IOException e){
            System.out.println("error while encoding image");
            return "-1";
        }
    }

    public static Image decodeImageFile(String imageString){
        try {
            if (!imageString.equals( "-1")) {
                if (images.containsKey(imageString))
                    return images.get(imageString);
                byte[] data = Base64.getDecoder().decode(imageString);
                InputStream input = new ByteArrayInputStream(data);
                Image image = new Image(input);
                images.put(imageString,image);
                return image;
            }else {
                if (images.containsKey("-1"))
                    return images.get("-1");
                else {
                    Image image = new Image(Utility.class.getResource("/pics/profile.jpg").toString());
                    images.put("-1",image);
                    return image;
                }
            }
        }catch (Exception e){
            System.out.println("error while decoding");
            return images.get("-1");
        }
    }
}
