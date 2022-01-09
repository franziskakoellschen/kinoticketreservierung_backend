package com.kinoticket.backend.initData;


import com.kinoticket.backend.model.Image;
import com.kinoticket.backend.repositories.ImageDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ImageDataLoader implements ApplicationRunner {

    private ImageDbRepository imageDbRepository;

    @Autowired
    public ImageDataLoader(ImageDbRepository imageDbRepository) {
        this.imageDbRepository = imageDbRepository;
    }
    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    // convert byte[] to BufferedImage
    public static BufferedImage toBufferedImage(byte[] bytes)
            throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;
    }

    public static List<Image> getImage() throws IOException {
        BufferedImage bi = ImageIO.read(new FileInputStream("src/main/java/com/kinoticket/backend/assets/img.png"));
        List<Image> imageList = new ArrayList<>();
        // convert BufferedImage to byte[]
        byte[] bytes = toByteArray(bi, "png");
        Image image = new Image(bytes,"house of Gucci" ,99l);
        imageList.add(image);
        return imageList;
    }

    public void run(ApplicationArguments args) throws IOException {

        imageDbRepository.save(getImage().get(0));
         }
}