/*
 * Developed by Razil Minneakhmetov on 12/26/18 8:27 PM.
 * Last modified 12/26/18 8:27 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import context.MyApplicationContext;
import dto.Group;
import dto.VKUser;
import models.*;
import repositories.*;

import java.util.ArrayList;
import java.util.List;

public class SimilarityService {
    private SimilarityRepository similarityRepository;
    private UserGroupsRepository userGroupsRepository;
    private UserGroupsFriendsRepository userGroupsFriendsRepository;
    private FriendsRepository friendsRepository;

    public SimilarityService(SimilarityRepository similarityRepository,
                             UserGroupsRepository userGroupsRepository,
                             UserGroupsFriendsRepository userGroupsFriendsRepository,
                             FriendsRepository friendsRepository) {
        this.similarityRepository = similarityRepository;
        this.userGroupsRepository = userGroupsRepository;
        this.userGroupsFriendsRepository = userGroupsFriendsRepository;
        this.friendsRepository = friendsRepository;
    }

    public void computeSimilarity(Long vkId){
        List<GroupModel> groupModels = userGroupsRepository.readByUserId(vkId);
        List<Group> myGroups = new ArrayList<Group>();
        List<Similarity> similarities = new ArrayList<Similarity>();
        for (int i = 0; i < groupModels.size(); i++){
            Group group = Group.builder()
                    .activity(groupModels.get(i).getActivity())
                    .id(groupModels.get(i).getGroupVkId())
                    .name(groupModels.get(i).getName())
                    .photoUrl(groupModels.get(i).getPhotoUrl())
                    .build();
            myGroups.add(group);
        }

        List<VKUser> vkUsers = convertToVKUsers(vkId);
        MyApplicationContext.getInstance().setObject("all" + vkId, vkUsers.size());
        MyApplicationContext.getInstance().setObject("status" + vkId, "Подчет похожести...");
        for (int i = 0; i < vkUsers.size(); i++){
            MyApplicationContext.getInstance().setObject("current" + vkId, i);

            List<Group> common = new ArrayList<Group>();
            List<Group> all = new ArrayList<Group>();
            all.addAll(myGroups);
            List<Group> friendsGroups = vkUsers.get(i).getGroups();

            for(int j = 0; j < friendsGroups.size(); j++){
                if (!all.contains(friendsGroups.get(j))){
                    all.add(friendsGroups.get(j));
                }
                if (myGroups.contains(friendsGroups.get(j))) {
                    common.add(friendsGroups.get(j));
                }
            }
            double sum = ((double) common.size()) / ((double) all.size()) * 10.0;
            sum = Math.round(sum * 100.0) / 100.0;
            Similarity similarity = Similarity.builder()
                    .friendVkId(vkUsers.get(i).getId())
                    .userVkId(vkId)
                    .similarity(sum)
                    .build();
            similarities.add(similarity);
        }
        similarityRepository.saveAll(similarities);

    }

    public List<VKUser> convertToVKUsers(Long vkId){
        MyApplicationContext.getInstance().setObject("status" + vkId, "Конвертирование...");
        List<GroupModelFriends> groupModelsFriends = userGroupsFriendsRepository.readByUserVkId(vkId);
        List<User> friends = friendsRepository.readFriendsByUserVkId(vkId);
        List<VKUser> vkUsers = new ArrayList<VKUser>();
        MyApplicationContext.getInstance().setObject("all" + vkId, friends.size());
        for (int i = 0; i <friends.size(); i++){
            MyApplicationContext.getInstance().setObject("current" + vkId, i);
            List<Group> groups = new ArrayList<Group>();
            for (int j = 0; j < groupModelsFriends.size(); j++){
                if(groupModelsFriends.get(j).getFriendVkId() == friends.get(i).getVkId()){
                    Group group = Group.builder()
                            .name(groupModelsFriends.get(j).getName())
                            .photoUrl(groupModelsFriends.get(j).getPhotoUrl())
                            .id(groupModelsFriends.get(j).getGroupVkId())
                            .activity(groupModelsFriends.get(j).getActivity())
                            .build();
                    groups.add(group);
                }
            }
            VKUser vkUser = VKUser.builder()
                    .photoUrl(friends.get(i).getPhotoUrl())
                    .groups(groups)
                    .surname(friends.get(i).getSurname())
                    .name(friends.get(i).getName())
                    .id(friends.get(i).getVkId())
                    .build();
            vkUsers.add(vkUser);
        }
        return vkUsers;
    }

    public List<SimilarityUsers> getWithNoAndPercents(Long vkId){
        List<SimilarityUsers> similarityUsers = similarityRepository.getSimilarityUsers(vkId);
        for (int i = 0; i <similarityUsers.size(); i++){
           similarityUsers.get(i).setNo(i + 1);
           similarityUsers.get(i).setSimilarity(similarityUsers.get(i).getSimilarity() * 100);
        }
        return similarityUsers;
    }
}