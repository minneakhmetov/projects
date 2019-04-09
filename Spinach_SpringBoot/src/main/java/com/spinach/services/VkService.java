package com.spinach.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spinach.dto.VkApiClientResponseDto;
import com.spinach.models.AuthModel;
import com.spinach.models.GroupsModel;
import com.spinach.models.UserModel;
import com.spinach.dao.AuthRepository;
import com.spinach.dao.GroupsRepository;
import com.spinach.dao.UsersRepository;
import com.spinach.utils.PathUtil;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VkService {

    private UsersRepository usersRepository;
    private GroupsRepository groupsRepository;
    private AuthRepository authRepository;

    @Autowired
    private PathUtil pathUtil;

//    @Autowired
//    private Environment environment;


//    private static Integer APP_ID = null;
//
//    private static String CLIENT_SECRET = null;
//
//    private static  String REDIRECT_URL = null;

//    public VkService() {
//        //ApplicationContext context = new ClassPathXmlApplicationContext("beans/context.xml");
//        //usersRepository = context.getBean(UsersRepository.class);
//        //groupsRepository = context.getBean(GroupsRepository.class);
//    }

    @Autowired
    public VkService(UsersRepository usersRepository, GroupsRepository groupsRepository, AuthRepository authRepository) {
        this.usersRepository = usersRepository;
        this.groupsRepository = groupsRepository;
        this.authRepository = authRepository;
    }

    @SneakyThrows
    public VkApiClientResponseDto auth(String code) {

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(Integer.valueOf(pathUtil.getProperty("spinach.vk.app.id")),
                        pathUtil.getProperty("spinach.vk.client.secret"),
                        pathUtil.getProperty("spinach.vk.redirect.url"), code)
                .execute();
        return VkApiClientResponseDto.builder()
                .vk(vk)
                .authResponse(authResponse)
                .id(Long.valueOf(authResponse.getUserId()))
                .build();
    }

    @SneakyThrows
    public long register(VkApiClientResponseDto dto) {
        UserActor actor = new UserActor(dto.getAuthResponse().getUserId(), dto.getAuthResponse().getAccessToken());

        List<UserXtrCounters> users = dto.getVk().users().get(actor)
                .userIds(String.valueOf(dto.getAuthResponse().getUserId()))
                .fields(UserField.VERIFIED, UserField.SEX, UserField.CITY, UserField.PHOTO_50,
                        UserField.ABOUT, UserField.ACTIVITIES, UserField.BDATE, UserField.BLACKLISTED,
                        UserField.BOOKS, UserField.CAN_POST, UserField.CAN_SEE_ALL_POSTS, UserField.CAN_SEE_AUDIO,
                        UserField.CAN_SEND_FRIEND_REQUEST, UserField.CAN_WRITE_PRIVATE_MESSAGE, UserField.CAREER,
                        UserField.COMMON_COUNT, UserField.CONNECTIONS, UserField.CONTACTS, UserField.COUNTERS,
                        UserField.EDUCATION, UserField.EXPORTS, UserField.CROP_PHOTO, UserField.DOMAIN,
                        UserField.FOLLOWERS_COUNT, UserField.FRIEND_STATUS, UserField.HAS_MOBILE, UserField.GAMES,
                        UserField.INTERESTS, UserField.LISTS, UserField.MAIDEN_NAME, UserField.MILITARY,
                        UserField.MOVIES, UserField.MUSIC, UserField.NICKNAME, UserField.RELATION, UserField.PERSONAL,
                        UserField.SCHOOLS, UserField.WALL_COMMENTS, UserField.VERIFIED, UserField.UNIVERSITIES,
                        UserField.STATUS, UserField.QUOTES, UserField.SCREEN_NAME, UserField.RELATIVES,
                        UserField.PHOTO_100, UserField.PHOTO_200, UserField.PHOTO_400_ORIG, UserField.TV, UserField.LISTS)
                .lang(Lang.RU)
                .execute();

        return usersRepository.save(converter(users.get(0), dto.getAuthResponse().getEmail()));
    }

    @SneakyThrows
    public void getGroups(VkApiClientResponseDto dto, long userId) {
        UserActor actor = new UserActor(dto.getAuthResponse().getUserId(), dto.getAuthResponse().getAccessToken());

        JsonElement jsonElement = dto.getVk().execute().code(actor, "return API.groups.get({\"user_id\":" + actor.getId() + ",extended:\"1\",\"fields\":\"id,name,activity\"});")
                .execute();

        JsonObject object = jsonElement.getAsJsonObject();

        JsonArray map = object.get("items").getAsJsonArray();

        List<GroupsModel> groups = new ArrayList<GroupsModel>();

        for (int i = 0; i < map.size(); i++) {
            GroupsModel group = GroupsModel.builder()
                    .groupVkId(Long.valueOf(map.get(i).getAsJsonObject().get("id").toString()))
                    .userId(userId)
                    .userVkId(actor.getId())
                    .name(map.get(i).getAsJsonObject().get("name").toString())
                    .isClosed(Short.valueOf(map.get(i).getAsJsonObject().get("is_closed").toString()))
                    .type(map.get(i).getAsJsonObject().get("type").toString())
                    .photoUrl50(map.get(i).getAsJsonObject().get("photo_50").toString())
                    .photoUrl100(map.get(i).getAsJsonObject().get("photo_100").toString())
                    .photoUrl200(map.get(i).getAsJsonObject().get("photo_200").toString())
                    //.activity(map.get(i).getAsJsonObject().get("activity").toString())
                    .build();
            if (map.get(i).getAsJsonObject().get("activity") != null)
                group.setActivity(map.get(i).getAsJsonObject().get("activity").toString());
            else group.setActivity(null);
            if (map.get(i).getAsJsonObject().get("deactivated") != null)
                group.setDeactivated(map.get(i).getAsJsonObject().get("deactivated").toString());
            else group.setDeactivated(null);
            if (map.get(i).getAsJsonObject().get("city") != null)
                group.setCity(map.get(i).getAsJsonObject().get("city").toString());
            else group.setCity(null);
            if (map.get(i).getAsJsonObject().get("country") != null)
                group.setCountry(map.get(i).getAsJsonObject().get("country").toString());
            else group.setCountry(null);
            if (map.get(i).getAsJsonObject().get("description") != null)
                group.setDescription(map.get(i).getAsJsonObject().get("description").toString());
            else group.setDescription(null);
            if (map.get(i).getAsJsonObject().get("status") != null)
                group.setStatus(map.get(i).getAsJsonObject().get("status").toString());
            else group.setStatus(null);
            if (map.get(i).getAsJsonObject().get("site") != null)
                group.setSite(map.get(i).getAsJsonObject().get("site").toString());
            else group.setSite(null);
            if (map.get(i).getAsJsonObject().get("is_favourite") != null) {
                if (map.get(i).getAsJsonObject().get("is_favourite").toString().equals("0"))
                    group.setIsFavourite(false);
                if (map.get(i).getAsJsonObject().get("is_favourite").toString().equals("1"))
                    group.setIsFavourite(true);
            } else group.setIsFavourite(null);
            groups.add(group);
        }
        groupsRepository.saveAll(groups);
    }

    private UserModel converter(UserXtrCounters user, String email) {
        UserModel model = UserModel.builder()
                .vkId(Long.valueOf(user.getId()))
                .about(user.getAbout())
                .activities(user.getActivities())
                .activity(user.getActivity())
                .birthday(user.getBdate())
                .books(user.getBooks())
                .photoUrl50(user.getPhoto50())
                .photoUrl100(user.getPhoto100())
                .photoUrl200(user.getPhoto200())
                .photoUrl400(user.getPhoto400Orig())
                .domainVk(user.getDomain())
                .facebook(user.getFacebook())
                .facebookName(user.getFacebookName())
                .facultyName(user.getFacultyName())
                .firstName(user.getFirstName())
                .instagram(user.getInstagram())
                .interests(user.getInterests())
                .lastName(user.getLastName())
                .mobilePhone(user.getMobilePhone())
                .movies(user.getMovies())
                .music(user.getMusic())
                .nickname(user.getNickname())
                .quotes(user.getQuotes())
                .sex(user.getSex().name())
                .site(user.getSite())
                .skype(user.getSkype())
                .status(user.getStatus())
                .tv(user.getTv())
                .universityName(user.getUniversityName())
                .email(email)
                .build();
        if (user.getCity() != null)
            model.setCity(user.getCity().getTitle());
        else model.setCity(null);
        if (user.getCountry() != null)
            model.setCountry(user.getCountry().getTitle());
        else model.setCountry(null);
        if (user.getSex() != null)
            model.setSex(user.getSex().name());
        else model.setSex(null);
        if (user.getPersonal() != null) {
            model.setPolitical(user.getPersonal().getPolitical());
            model.setLang(stringBuilder(user.getPersonal().getLangs()));
            model.setReligion(user.getPersonal().getReligion());
            model.setInspiredBy(user.getPersonal().getInspiredBy());
            model.setPeopleMain(user.getPersonal().getPeopleMain());
            model.setLifeMain(user.getPersonal().getLifeMain());
            model.setSmoking(user.getPersonal().getSmoking());
            model.setAlcohol(user.getPersonal().getAlcohol());
        } else {
            model.setPolitical(null);
            model.setLang(null);
            model.setReligion(null);
            model.setInspiredBy(null);
            model.setPeopleMain(null);
            model.setLifeMain(null);
            model.setSmoking(null);
            model.setAlcohol(null);
        }
        if (model.getAbout().equals(""))
            model.setAbout(null);
        if (model.getActivities().equals(""))
            model.setActivities(null);
        if (model.getBirthday().equals(""))
            model.setBirthday(null);
        if (model.getBooks().equals(""))
            model.setBooks(null);
        if (model.getCity().equals(""))
            model.setCity(null);
        if (model.getFacultyName().equals(""))
            model.setFacultyName(null);
        if (model.getInterests().equals(""))
            model.setInterests(null);
        if (model.getMobilePhone().equals(""))
            model.setMobilePhone(null);
        if (model.getMovies().equals(""))
            model.setMovies(null);
        if (model.getMusic().equals(""))
            model.setMusic(null);
        if (model.getNickname().equals(""))
            model.setNickname(null);
        if (model.getSex().equals(""))
            model.setSex(null);
        if (model.getStatus().equals(""))
            model.setStatus(null);
        if (model.getTv().equals(""))
            model.setTv(null);
        if (model.getUniversityName().equals(""))
            model.setUniversityName(null);
        if (model.getPeopleMain() == 0)
            model.setPeopleMain(null);
        if (model.getLifeMain() == 0)
            model.setLifeMain(null);
        if (model.getSmoking() == 0)
            model.setSmoking(null);
        if (model.getAlcohol() == 0)
            model.setAlcohol(null);
        if (model.getQuotes().equals(""))
            model.setQuotes(null);
        if (model.getEmail().equals(""))
            model.setEmail(null);
        return model;

    }

    private String stringBuilder(List<String> strings) {
        if (strings == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            stringBuilder.append(strings.get(i));
            if (i + 1 != strings.size())
                stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    public AuthModel authOrRegister(String code) {
        long id;
        VkApiClientResponseDto dto = auth(code);
        Long dbId = usersRepository.existsVkUser(dto.getId());
        if (dbId == null) {
            id = register(dto);
            //todo: переделать группы
            getGroups(dto, id);
        } else
            id = dbId;
        AuthModel authModel = AuthModel.builder()
                .cookieValue(UUID.randomUUID().toString())
                .id(id)
                .build();
        authRepository.save(authModel);
        return authModel;
    }


}
