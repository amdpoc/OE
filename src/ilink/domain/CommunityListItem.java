package ilink.domain;

import java.io.Serializable;


public class CommunityListItem implements Serializable{

    private static final long serialVersionUID = 1L;

    private long communityId;
    private String communityName;

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    @Override
    public String toString() {
        return "{" +
                "communityId:\"" + communityId + "\"" +
                ", communityName:\"" + communityName + "\"" +
                "}";
    }
}
