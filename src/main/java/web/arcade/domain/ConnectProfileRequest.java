package web.arcade.domain;

public class ConnectProfileRequest {
    private Long profileId1;
    private Long profileId2;

    public Long getProfileId1() {
        return profileId1;
    }

    public void setProfileId1(Long profileId1) {
        this.profileId1 = profileId1;
    }

    public Long getProfileId2() {
        return profileId2;
    }

    public void setProfileId2(Long profileId2) {
        this.profileId2 = profileId2;
    }
}
