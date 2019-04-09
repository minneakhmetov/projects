package com.spinach.dao;


import com.spinach.exceptions.UserNotFoundException;
import com.spinach.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component
public class UsersRepository {
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_USER = "insert into users_table (vk_id, about, activities, activity, birthday, books, city, country, " +
            "photo_50, photo_100, photo_200, photo_400, domain_vk, facebook, facebook_name, faculty_name, first_name, instagram, interests, " +
            "last_name, mobile_phone, movies, music, nickname, quotes, sex, site, skype, status, tv, university_name, political, langs, " +
            "religion, inspired_by, people_main, life_main, smoking, alcohol, email) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_ALL = "select * from users_table where id = ?";

    private static final String SELECT_SHORT = "select id, first_name, last_name, photo_50 from users_table where id = ?";

    private static final String INSERT_USER_REG = "INSERT INTO users_table (first_name, last_name, email, birthday, city, hash_password) values (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_PHOTO = "update users_table set photo_50 = ?, photo_100 = ?, photo_200 = ?, photo_400 = ? where id = ?;";

    private static final String SELECT_BY_EMAIL = "SELECT id, email, hash_password, photo_100, first_name, last_name from users_table where email = ?";

    private static final String EXISTS_VK_USER = "select id from users_table where vk_id = ?";

    private static final String SELECT_EMAIL_BY_ID = "select email from users_table where id = ?";

    private static final String SELECT_IN_SEARCHING_AND_RANDOM = "select * from users_table where in_searching = true and is_random = true;";

    private static final String UPDATE_SEARCHING = "update users_table set in_searching = ? where id in(?, ?);";

    private static final String UPDATE_SEARCHING_USER = "update users_table set in_searching = ? where id = ?;";

    private static final String SELECT_USERS_CHATS = "select * from getUsers(?);";

    private static final String SELECT_IS_RANDOM_USERS = "SELECT is_random from users_table where id = ?";

    private static final String SELECT_BOTH_EMAILS_BY_IDS = "select id, email from users_table where id in (?, ?);";

    public UsersRepository(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void updatePhoto(UserPhotoModel model, long id) {
        jdbcTemplate.update(UPDATE_PHOTO, model.getPhoto50(), model.getPhoto100(), model.getPhoto200(), model.getPhoto400(), id);
    }

    public long save(UserRegModel model) throws DuplicateKeyException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_USER_REG, new String[]{"id"});
                    ps.setString(1, model.getFirstName());
                    ps.setString(2, model.getLastName());
                    ps.setString(3, model.getEmail());
                    ps.setString(4, model.getBirthday());
                    ps.setString(5, model.getCity());
                    ps.setString(6, model.getHashPassword());
                    return ps;
                }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public String getEmailById(long id){
        return jdbcTemplate.queryForObject(SELECT_EMAIL_BY_ID, rowMapperEmail, id);
    }

    public long save(UserModel user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_USER, new String[]{"id"});
                    if (user.getVkId() == null)
                        ps.setNull(1, Types.BIGINT);
                    else ps.setLong(1, user.getVkId());
                    ps.setString(2, user.getAbout());
                    ps.setString(3, user.getActivities());
                    ps.setString(4, user.getActivity());
                    ps.setString(5, user.getBirthday());
                    ps.setString(6, user.getBooks());
                    ps.setString(7, user.getCity());
                    ps.setString(8, user.getCountry());
                    ps.setString(9, user.getPhotoUrl50());
                    ps.setString(10, user.getPhotoUrl100());
                    ps.setString(11, user.getPhotoUrl200());
                    ps.setString(12, user.getPhotoUrl400());
                    ps.setString(13, user.getDomainVk());
                    ps.setString(14, user.getFacebook());
                    ps.setString(15, user.getFacebookName());
                    ps.setString(16, user.getFacultyName());
                    ps.setString(17, user.getFirstName());
                    ps.setString(18, user.getInstagram());
                    ps.setString(19, user.getInterests());
                    ps.setString(20, user.getLastName());
                    ps.setString(21, user.getMobilePhone());
                    ps.setString(22, user.getMovies());
                    ps.setString(23, user.getMusic());
                    ps.setString(24, user.getNickname());
                    ps.setString(25, user.getQuotes());
                    ps.setString(26, user.getSex());
                    ps.setString(27, user.getSite());
                    ps.setString(28, user.getSkype());
                    ps.setString(29, user.getStatus());
                    ps.setString(30, user.getTv());
                    ps.setString(31, user.getUniversityName());
                    if (user.getPolitical() == null)
                        ps.setNull(32, Types.INTEGER);
                    else ps.setInt(32, user.getPolitical());
                    ps.setString(33, user.getLang());
                    ps.setString(34, user.getReligion());
                    ps.setString(35, user.getInspiredBy());
                    if (user.getPeopleMain() == null)
                        ps.setNull(36, Types.INTEGER);
                    else ps.setInt(36, user.getPeopleMain());
                    if (user.getLifeMain() == null)
                        ps.setNull(37, Types.INTEGER);
                    else ps.setInt(37, user.getLifeMain());
                    if (user.getSmoking() == null)
                        ps.setNull(38, Types.INTEGER);
                    else ps.setInt(38, user.getSmoking());
                    if (user.getAlcohol() == null)
                        ps.setNull(39, Types.INTEGER);
                    else ps.setInt(39, user.getAlcohol());
                    ps.setString(40, user.getEmail());
                    return ps;
                }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private RowMapper<UserModel> rowMapperAll = new RowMapper<UserModel>() {
        public UserModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return UserModel.builder()
                    .id(resultSet.getLong("id"))
                    .vkId(resultSet.getLong("vk_id"))
                    .about(resultSet.getString("about"))
                    .activities(resultSet.getString("activities"))
                    .activity(resultSet.getString("activity"))
                    .birthday(resultSet.getString("birthday"))
                    .books(resultSet.getString("books"))
                    .city(resultSet.getString("city"))
                    .country(resultSet.getString("country"))
                    .photoUrl50(resultSet.getString("photo_50"))
                    .photoUrl100(resultSet.getString("photo_100"))
                    .photoUrl200(resultSet.getString("photo_200"))
                    .photoUrl400(resultSet.getString("photo_400"))
                    .domainVk(resultSet.getString("domain_vk"))
                    .facebook(resultSet.getString("facebook"))
                    .facebookName(resultSet.getString("facebook_name"))
                    .facultyName(resultSet.getString("faculty_name"))
                    .firstName(resultSet.getString("first_name"))
                    .instagram(resultSet.getString("instagram"))
                    .interests(resultSet.getString("interests"))
                    .lastName(resultSet.getString("last_name"))
                    .mobilePhone(resultSet.getString("mobile_phone"))
                    .movies(resultSet.getString("movies"))
                    .music(resultSet.getString("music"))
                    .nickname(resultSet.getString("nickname"))
                    .quotes(resultSet.getString("quotes"))
                    .sex(resultSet.getString("sex"))
                    .site(resultSet.getString("site"))
                    .skype(resultSet.getString("skype"))
                    .status(resultSet.getString("status"))
                    .tv(resultSet.getString("tv"))
                    .universityName(resultSet.getString("university_name"))
                    .political(resultSet.getInt("political"))
                    .lang(resultSet.getString("langs"))
                    .religion(resultSet.getString("religion"))
                    .inspiredBy(resultSet.getString("inspired_by"))
                    .peopleMain(resultSet.getInt("people_main"))
                    .lifeMain(resultSet.getInt("life_main"))
                    .smoking(resultSet.getInt("smoking"))
                    .alcohol(resultSet.getInt("alcohol"))
                    .email(resultSet.getString("email"))
                    .build();
        }
    };

    private RowMapper<UserShortModel> rowMapperShort = new RowMapper<UserShortModel>() {
        public UserShortModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return UserShortModel.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .photoUrl(resultSet.getString("photo_50"))
                    .build();
        }
    };

    private RowMapper<UserShortModel> rowMapperUsers = new RowMapper<UserShortModel>() {
        public UserShortModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return UserShortModel.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .photoUrl(resultSet.getString("photo_url"))
                    .build();
        }
    };

    private RowMapper<String> rowMapperEmail = new RowMapper<String>() {
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString("email");
        }
    };

    public UserModel readAll(long id) {
        return jdbcTemplate.queryForObject(SELECT_ALL, rowMapperAll, id);
    }

    public UserShortModel readShort(long id) {
        return jdbcTemplate.queryForObject(SELECT_SHORT, rowMapperShort, id);
    }

    private RowMapper<LoginModel> rowMapperLogin = new RowMapper<LoginModel>() {
        @Override
        public LoginModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return LoginModel.builder()
                    .email(resultSet.getString("email"))
                    .id(resultSet.getLong("id"))
                    .hashPassword(resultSet.getString("hash_password"))
                    .name(resultSet.getString("first_name"))
                    .surname(resultSet.getString("last_name"))
                    .photo(resultSet.getString("photo_100"))
                    .build();
        }
    };

    public LoginModel readByEmail(String email) throws UserNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_EMAIL, rowMapperLogin, email);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    private RowMapper<Long> rowMapper = new RowMapper<Long>() {
        @Override
        public Long mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getLong("id");
        }
    };

    public Long existsVkUser(long vkId) {
        try {
            return jdbcTemplate.queryForObject(EXISTS_VK_USER, rowMapper, vkId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<UserShortModel> getSearchRandomUsers(){
        return jdbcTemplate.query(SELECT_IN_SEARCHING_AND_RANDOM, rowMapperShort);
    }

    public void updateSearching(boolean state, long userId, long partnerId){
        jdbcTemplate.update(UPDATE_SEARCHING, state, userId, partnerId);
    }


    public void setSearching(long userId, boolean state){
        jdbcTemplate.update(UPDATE_SEARCHING_USER, state, userId);
    }

    public List<UserShortModel> getUsers(String query){
        return jdbcTemplate.query(SELECT_USERS_CHATS, rowMapperUsers, query);
    }


    private RowMapper<Boolean> rowMapperIsRandom = new RowMapper<Boolean>() {
        @Override
        public Boolean mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getBoolean("is_random");
        }
    };

    public boolean isRandom(long userId){
        return jdbcTemplate.queryForObject(SELECT_IS_RANDOM_USERS, rowMapperIsRandom, userId);
    }

    private RowMapper<IdEmailModel> idEmailModelRowMapper = new RowMapper<IdEmailModel>() {
        @Override
        public IdEmailModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return IdEmailModel.builder()
                    .email(resultSet.getString("email"))
                    .id(resultSet.getLong("id"))
                    .build();
        }
    };

    public List<IdEmailModel> getBothEmailsByIds(long userId, long partnerId){
        return jdbcTemplate.query(SELECT_BOTH_EMAILS_BY_IDS, idEmailModelRowMapper, userId, partnerId);
    }






}
