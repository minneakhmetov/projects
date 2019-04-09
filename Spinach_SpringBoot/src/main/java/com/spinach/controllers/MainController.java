package com.spinach.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spinach.dto.ProfileInfoDto;
import com.spinach.dto.*;
import com.spinach.exceptions.UserNotFoundException;
import com.spinach.forms.RegisterForm;
import com.spinach.models.LoginModel;
import com.spinach.security.details.UserDetailsImpl;
import com.spinach.services.*;
import com.spinach.utils.PathUtil;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.websocket.services.WebSocketService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private ChatsService chatsService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private VkService vkService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SimilarityService similarityService;

    @Autowired
    private PathUtil pathUtil;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SimilaritySingleton similaritySingleton;


//    @GetMapping(value = "/main")
//    public String getMainPage(){
//        return "main";
//    }

    @GetMapping(value = "/profile")
    @ResponseBody
    public String getProfile() {
        return "profile";
    }

//    @GetMapping("/users")
//    @ResponseBody
//    public String getUsers(){
//        return repository.readShort(1).toString();
//    }


    //ApplicationContext context = new ClassPathXmlApplicationContext("beans/context.xml");

    @GetMapping(value = "/login")
    public String printHello(@RequestParam("code") String code, ModelMap model) {

        //VkService vkService = context.getBean(VkService.class);
//        VkApiClientResponseDto dto = vkService.auth(code);
//        long id = vkService.register(dto);
//        vkService.getGroups(dto, id);
        vkService.auth(code);
        // model.addAttribute("message", "Hello Spring MVC Framework!");
        return "redirect:/profile";
    }

    @GetMapping(value = "/")
    public String redirect(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/chats";
        } else return "redirect:/main";
    }

    @GetMapping(value = "/main")
    public String getLoginPage(Authentication authentication, ModelMap model, HttpServletRequest request) {
        if (authentication != null) {
            return "redirect:/chats";
        }
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("height", 19.8);
            model.addAttribute("error", true);
        } else model.addAttribute("height", 16.3);
        return "main";
    }

    @GetMapping(value = "/regvk")
    public String redirect() {
        return "redirect:https://oauth.vk.com/authorize?client_id=6733902&display=page&redirect_uri=http://localhost:8000/login&scope=friends,photos,audio,video,status,wall,groups,email&response_type=code&v=5.87";
    }

    @GetMapping(value = "/register")
    public String register(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/chats";
        }
        return "register";
    }

    @PostMapping(value = "/profileInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProfileInfoDto profileInfo(Authentication authentication) {
        LoginModel model = ((UserDetailsImpl) authentication.getPrincipal()).getModel();
        return ProfileInfoDto.builder()
                .id(model.getId())
                .name(model.getName() + " " + model.getSurname())
                .photo(model.getPhoto())
                .build();
    }

    @PostMapping(value = "/register")
    public String register(ModelMap model, @RequestParam("name") String name,
                                   @RequestParam("surname") String surname,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("confirmpassword") String confirmpassword,
                                   @RequestParam("birthdate") String birthdate,
                                   @RequestParam("city") String city,
                                   @RequestParam("file") MultipartFile file) {

        if(!name.equals("") && !surname.equals("") && !email.equals("") && !password.equals("") && !confirmpassword.equals("") && !birthdate.equals("") && !city.equals("")){

            if(confirmpassword.equals(password)){
                RegisterForm form = RegisterForm.builder()
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .password(password)
                        .birthdate(birthdate)
                        .city(city)
                        .photo(file)
                        .build();
                try {
                    registerService.register(form);
                    return "redirect:/main";
                } catch (DuplicateKeyException e){
                    model.addAttribute("userexists", true);
                    return "register";
                }

            } else {
                model.addAttribute("donotmatch", true);
                return "register";
            }

        } else {
            model.addAttribute("donotfillall", true);
            return "register";
        }



    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public String auth(@RequestParam("password") String password, @RequestParam("email") String email, HttpServletResponse response) {
//        LoginForm form = LoginForm.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        AuthModel model = .auth(form);
//        if(model == null){
//            return "redirect:/main";
//        }
//        Cookie id = new Cookie("id", String.valueOf(model.getId()));
//        id.setMaxAge(60 * 60 * 60 * 24);
//        Cookie cookie = new Cookie("auth", model.getCookieValue());
//        cookie.setMaxAge(60 * 60 * 60 * 24);
//        response.addCookie(id);
//        response.addCookie(cookie);
//        return "redirect:/profile";
//    }

//    @GetMapping(value = "/profile")
//    public String profile() {
//        return "login";
//    }

    @PostMapping(value = "/getChats", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public StartChatsListDto getChats(Authentication authentication) {

//        if (authentication == null) {
//            return "redirect:/login";
//        }
//        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
//        LoginModel user = details.getModel();
        //String string = ;
        //System.out.println(string);
        return chatsService.getChats(getId(authentication));
    }

    @GetMapping(value = "/chats", produces = MediaType.APPLICATION_JSON_VALUE)
    private String chat() {
        return "chat";
    }

    @PostMapping(value = "/openChat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MessagePartnerIdListDto getChats(Authentication authentication, @RequestParam("chatId") String chatId) {
        //String string = ;
        //System.out.println(string);
        return chatsService.getChat(Long.valueOf(chatId), getId(authentication));
    }

    @PostMapping(value = "/sendMessage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String sendMessage(Authentication authentication, @RequestParam("partnerId") String partnerId,
                              @RequestParam("chatId") String chatId,
                              @RequestParam("inputPlace") String inputPlace) {
        //MessagePartnerIdListDto dto = context.getBean(ChatsService.class).getChat(Long.valueOf(chatId), Long.valueOf(userId));
        //String string = ;
        //System.out.println(string);
        MessageDto messageDto = MessageDto.builder()
                .chatId(Long.valueOf(chatId))
                .partnerId(Long.valueOf(partnerId))
                .userId(getId(authentication))
                .text(inputPlace)
                .build();


        TimeAndEmailDto dto = chatsService.sendMessage(messageDto);
        JSONObject object = new JSONObject();
        object.put("time", dto.getTime());

        webSocketService.notify(new MessageTimeDto(messageDto, dto.getTime()), dto.getEmail());

        return object.toJSONString();
    }


    @PostMapping(value = "/startChat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    public ChatDto startChat(Authentication authentication) {
        try {
            return similarityService.createChat(getId(authentication));
        } catch (UserNotFoundException e) {
            return null;
        }


    }

    @GetMapping(value = "/usersPhotos/{file-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable("file-name") String filename) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet( pathUtil.getProperty("spinach.static.url")
                + pathUtil.getProperty("spinach.static.photo.relative.url") + filename + "." +
                pathUtil.getProperty("spinach.static.photo.ext"));
        return IOUtils.toByteArray(client.execute(request).getEntity().getContent());
    }

    @GetMapping(value = "/usersPhotos/default/{file-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getDefaultImageWithMediaType(@PathVariable("file-name") String filename) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet( pathUtil.getProperty("spinach.static.url")
                + pathUtil.getProperty("spinach.static.photo.relative.default.url") + filename + "." +
                pathUtil.getProperty("spinach.static.photo.ext"));
        return IOUtils.toByteArray(client.execute(request).getEntity().getContent());
    }


    @PostMapping(value = "/addToFriends")
    public ResponseEntity<Object> addToFriends(Authentication authentication){
        surveyService.invite(getId(authentication));
        //return ResponseEntity.ok(new Object());
        return null;
    }

    @PostMapping(value = "/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ChatsListDto accept(Authentication authentication){
        return surveyService.acceptFriendship(getId(authentication));
    }

    @PostMapping(value = "/notAccept")
    public ResponseEntity<Object> notAccept(Authentication authentication){
        surveyService.notAccept(getId(authentication));
        //return ResponseEntity.ok(new Object());
        return null;
    }

    @PostMapping(value = "/leaveChat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ChatIdBuilder leaveChat(Authentication authentication){
        long chatId = surveyService.leaveChat(getId(authentication));
        return ChatIdBuilder.builder().chatId(chatId).build();
    }

    @PostMapping(value = "/continueChatting")
    public ResponseEntity<Object> continueChatting(Authentication authentication){
        surveyService.continueChatting(getId(authentication));
        //return ResponseEntity.ok(new Object());
        return null;
    }

    @PostMapping("/liked")
    public ResponseEntity<Object> liked(Authentication authentication){
        surveyService.liked(getId(authentication));
        //return ResponseEntity.ok(new Object());
        return null;
    }
    @PostMapping("/notLiked")
    public ResponseEntity<Object> notLiked(Authentication authentication){
        surveyService.notLiked(getId(authentication));
        //return ResponseEntity.ok(new Object());
        return null;
    }


//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public OutputMessage send(Message message) throws Exception {
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        return new OutputMessage(message.getFrom(), message.getText(), time);
//    }

//    @RequestMapping(value = "/getChat", method = RequestMethod.GET)
//    private String getChat(){
//        return "chats";
//    }

    private long getId(Authentication authentication) {
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        LoginModel user = details.getModel();
        return user.getId();
    }

    @GetMapping("/randomUser")
    @ResponseBody
    public String randomUser(){

        String emailName = UUID.randomUUID().toString().replace("-", "").substring(1, 10);
        String password = "password";
        RegisterForm form = RegisterForm.builder()
                .name(emailName)
                .surname("Random")
                .email(emailName + "@email.com")
                .password(password)
                .birthdate("Random")
                .city("Random")
                .build();
        registerService.register(form);
        String response = "Random email: " + emailName + "@email.com <br> "
                +" Random password: " + password;
        return response;
    }


    @GetMapping("/sim")
    @ResponseBody
    public String sim(){
        StringBuilder builder = new StringBuilder();
        builder.append("RandomUsers: ");

        Long[] queue = similaritySingleton.getQueue().toArray(new Long[similaritySingleton.getQueue().size()]);
        List<Long> randomUsers = similaritySingleton.getAllRandomUsers();
        for(Long q: randomUsers){
            builder.append(q + " ");
        }
        builder.append("<br>");
        builder.append("Queue: ");
        for(Long q: queue){
            builder.append(q + " ");
        }
        return builder.toString();
    }

    @PostMapping("/cancelSearching")
    @ResponseBody
    public boolean cancelSearching(Authentication authentication){
        return similarityService.cancelSearching(getId(authentication));
    }
}
