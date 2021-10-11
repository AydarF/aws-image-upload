package com.aydarfz.awsimageupload.datastore;

import com.aydarfz.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    /**
     * Add fake user profiles to the ArrayList of profiles
     */
    static {
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "John Doe", null));
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "Jane Doe", null));
    }

    /**
     * @return This method return user profiles
     */
    public static List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
