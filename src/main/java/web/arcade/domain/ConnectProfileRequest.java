package web.arcade.domain;

public class ConnectProfileRequest {
    private String profileId1;
    private String profileId2;

    public String getProfileId1() {
        return profileId1;
    }

    public void setProfileId1(String profileId1) {
        this.profileId1 = profileId1;
    }

    public String getProfileId2() {
        return profileId2;
    }

    public void setProfileId2(String profileId2) {
        this.profileId2 = profileId2;
    }

    // Método para retornar o valor de profileId1 como long
    public long getProfileId1AsLong() {
        return Long.parseLong(profileId1);
    }

    // Método para retornar o valor de profileId2 como long
    public long getProfileId2AsLong() {
        return Long.parseLong(profileId2);
    }
}
