/*
 * Developed by Razil Minneakhmetov on 10/24/18 10:12 PM.
 * Last modified 10/24/18 10:12 PM.
 * Copyright © 2018. All rights reserved.
 */

package app;

import context.MyApplicationContext;
import dto.ProductDto;
import lombok.SneakyThrows;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


public class Application {

    private static final Integer APP_ID = 6733902;
    private static final String CLIENT_SECRET = "N5eZ94mx68gTDxCYytCx";
    //private static final String REDIRECT_URI = ;



    @SneakyThrows
    public static void main(String[] args) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setUrl("jdbc:postgresql://localhost:5432/stickershop");
////        dataSource.setUsername("postgres");
////        dataSource.setPassword("r1a2z3i4l5");
////
////        //repository = new UserRepositoryImpl(dataSource);
////
////        //repository.create(User.builder().name("разиль").hashPassword("пароль").build());
//////
//////        TransportClient transportClient = HttpTransportClient.getInstance();
//////        VkApiClient vk = new VkApiClient(transportClient);
////
////        CartServiceImpl cartService = new CartServiceImpl(new CartServiceImpl(dataSource));
////        List<Product> products = cartService.getProductsInCart(193108295l);
////        String string = new Gson().toJson(products);
////        System.out.println(string);
////
//////        UserAuthResponse authResponse = vk.oauth()
//////                .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
//////                .execute();
////
////        //UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
////
////        TransportClient transportClient = HttpTransportClient.getInstance();
////        VkApiClient vk = new VkApiClient(transportClient);
////        String code = "627637be0845f6db94";
////        UserAuthResponse authResponse = vk.oauth()
////                .userAuthorizationCodeFlow(Constants.APP_ID, Constants.CLIENT_SECRET, Constants.REDIRECT_URI_VK, code)
////                .execute();
////
////        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
////        MessagesGetDialogsQuery a = vk.messages().getDialogs(actor);
////
//////        GetDialogsResponse chat = a.execute();
//////        List<Dialog> messages = chat.getItems();
//////        for (int i = 0; i< messages.size(); i++){
//////            System.out.println(messages.get(i).);
//////            System.out.println(messages.get(i).getPhoto50());
//////        }
////
////        System.out.println();


//        Avatar avatar = Avatar.builder().id(123l).URL("jicbsef").build();
//
//        String string = new Gson().toJson(avatar);
        //System.out.println(new Timestamp(System.currentTimeMillis()));

        //System.out.println(repository.readOne(193108295l));
        //List<Integer> userIds = chat.getUsers();
        //List<LoginForm> users = new ArrayList<>();

        Class.forName("org.postgresql.Driver");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/cartshop");
        dataSource.setUsername("postgres");
        dataSource.setPassword("r1a2z3i4l5");
//
//
//        Class<CartRepository> cartRepositoryClass = (Class<CartRepository>) Class.forName("repositories.CartRepositoryImpl");
//        Constructor<CartRepository> terminalConstructor = cartRepositoryClass.getConstructor(DriverManagerDataSource.class);
//        CartRepository cartRepository = terminalConstructor.newInstance(dataSource);
//
//        System.out.println(cartRepository);

        ProductRepositoryImpl productRepository = new ProductRepositoryImpl(dataSource);
        List<ProductDto> productDtoList = new ArrayList<>();
        long alltime = System.nanoTime();
        //for(int j = 0; j< ; j++) {
            long time = System.nanoTime();
            for (int i = 0; i < 1000000; i++) {
                ProductDto productDto = ProductDto.builder()
                        .activity(1l)
                        .price(1)
                        .vkId(1l)
                        .name("cbsjk")
                        .photoURL("kjbsjoe")
                        .build();
                productDtoList.add(productDto);
           // }
            //System.out.println(System.nanoTime() - time);
            productRepository.batchUpdate(productDtoList);
        }
        System.out.println("alltime" +  (System.nanoTime() - alltime));




    }
}