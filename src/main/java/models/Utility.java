package models;

import javafx.scene.image.Image;

import java.io.*;
import java.util.Base64;

public abstract class Utility {
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
        if (imageString != "-1") {
            byte[] data = Base64.getDecoder().decode(imageString);
            InputStream input = new ByteArrayInputStream(data);
            Image image = new Image(input);
            return image;
        }else {
            Image image = new Image(Utility.class.getResource("/pics/profile.jpg").toString());
            return image;
        }
    }
}
