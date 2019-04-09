package com.spinach.services;


import com.spinach.forms.RegisterForm;
import com.spinach.models.UserPhotoModel;
import com.spinach.models.UserRegModel;
import com.spinach.dao.UsersRepository;
import com.spinach.utils.PathUtil;
import lombok.SneakyThrows;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class RegisterService {

    private UsersRepository usersRepository;
    private PasswordEncoder encoder;

  //  @Autowired
//    private Environment environment;


    private String path; //= "C:\\Users\\razil\\Desktop\\spinach_gitlab\\Spinach_node\\public\\";

    private String url; //= "http://localhost:8080/";

    private String defaultUrl50;
    private String defaultUrl100;
    private String defaultUrl200;
    private String defaultUrl400;

    private String photoExt;

    //private String defaultPhoto

    @Autowired
    @SneakyThrows
    public RegisterService(UsersRepository usersRepository, PasswordEncoder encoder, PathUtil pathUtil) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
        path = "C:\\Users\\razil\\Desktop\\spinach_gitlab\\Spinach_node\\public\\usersPhotos\\";

               // pathUtil.getStaticPath();
        url = pathUtil.getProperty("spinach.static.photo.relative.url") ;
        defaultUrl50 = pathUtil.getProperty("spinach.static.default.50");
        defaultUrl100 = pathUtil.getProperty("spinach.static.default.100");
        defaultUrl200 = pathUtil.getProperty("spinach.static.default.200");
        defaultUrl400 = pathUtil.getProperty("spinach.static.default.400");
        photoExt = pathUtil.getProperty("spinach.static.photo.ext");
    }

    @SneakyThrows
    public void register(RegisterForm form)  throws DuplicateKeyException {
        UserRegModel model = UserRegModel.builder()
                .firstName(form.getName())
                .lastName(form.getSurname())
                .birthday(form.getBirthdate())
                .city(form.getCity())
                .email(form.getEmail())
                .hashPassword(encoder.encode(form.getPassword()))
                .build();
        Long id = usersRepository.save(model);
        boolean flag;
        try {
            flag = !form.getPhoto().isEmpty();
        } catch (NullPointerException e){
            flag = false;
        }
        if (flag) {
            String ext = form.getPhoto().getOriginalFilename().split("\\.")[1];
            byte[] bytes = form.getPhoto().getBytes();
            File file = new File(path + id + "." + ext);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
            BufferedImage source = ImageIO.read(file);
            BufferedImage image50;
            BufferedImage image100;
            BufferedImage image200;
            BufferedImage image400;
            if (source.getHeight() > source.getWidth()) {
                image50 = Scalr.resize(source, Scalr.Mode.FIT_TO_WIDTH, 50);
                image100 = Scalr.resize(source, Scalr.Mode.FIT_TO_WIDTH, 100);
                image200 = Scalr.resize(source, Scalr.Mode.FIT_TO_WIDTH, 200);
                image400 = Scalr.resize(source, Scalr.Mode.FIT_TO_WIDTH, 400);
            } else {
                image50 = Scalr.resize(source, Scalr.Mode.FIT_TO_HEIGHT, 50);
                image100 = Scalr.resize(source, Scalr.Mode.FIT_TO_HEIGHT, 100);
                image200 = Scalr.resize(source, Scalr.Mode.FIT_TO_HEIGHT, 200);
                image400 = Scalr.resize(source, Scalr.Mode.FIT_TO_HEIGHT, 400);
            }
            if (source.getHeight() != source.getWidth()) {
                image50 = Scalr.crop(image50, 50, 50);
                image100 = Scalr.crop(image100, 100, 100);
                image200 = Scalr.crop(image200, 200, 200);
                image400 = Scalr.crop(image400, 400, 400);
            }
            BufferedImage image50Buff = new BufferedImage(image50.getWidth(),
                    image50.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage image100Buff = new BufferedImage(image100.getWidth(),
                    image100.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage image200Buff = new BufferedImage(image200.getWidth(),
                    image200.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage image400Buff = new BufferedImage(image400.getWidth(),
                    image400.getHeight(), BufferedImage.TYPE_INT_RGB);


            image50Buff.createGraphics().drawImage(image50, 0, 0, Color.WHITE, null);
            image100Buff.createGraphics().drawImage(image100, 0, 0, Color.WHITE, null);
            image200Buff.createGraphics().drawImage(image200, 0, 0, Color.WHITE, null);
            image400Buff.createGraphics().drawImage(image400, 0, 0, Color.WHITE, null);

            File out50 = new File(path + id + "_50." + photoExt);
            File out100 = new File(path + id + "_100." + photoExt);
            File out200 = new File(path + id + "_200." + photoExt);
            File out400 = new File(path + id + "_400." + photoExt);
            file.delete();

            ImageIO.write(image50Buff, photoExt, out50);
            ImageIO.write(image100Buff, photoExt, out100);
            ImageIO.write(image200Buff, photoExt, out200);
            ImageIO.write(image400Buff, photoExt, out400);


            UserPhotoModel photoModel = UserPhotoModel.builder()
                    .photo50(url + id + "_50." + photoExt)
                    .photo100(url + id + "_100." + photoExt)
                    .photo200(url + id + "_200." + photoExt)
                    .photo400(url + id + "_400." + photoExt)
                    .build();
            usersRepository.updatePhoto(photoModel, id);
        } else {
            UserPhotoModel photoModel = UserPhotoModel.builder()
                    .photo50(url + defaultUrl50)
                    .photo100(url + defaultUrl100)
                    .photo200(url + defaultUrl200)
                    .photo400(url + defaultUrl400)
                    .build();
            usersRepository.updatePhoto(photoModel, id);
        }
    }




}
