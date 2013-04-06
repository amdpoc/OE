package ilink.domain;

import java.io.Serializable;

public class AddressDataType implements Serializable {

	private String houseNo = "";
    private String suite = "";
    private String directional = "";
    private String postDirectional = "";
	private String streetName = "";
	private String zip5 = "";
	private String zip4 = "";
    private String communityName = "";
    private String communityId = "";
    private String poBox = "";
	private String country = "";
	private String state = "";
	private String stateAbbr = "";
    private String carrierRoute = "";
    private String addressLocation = "";
    private String latitude = "";
	private String longtitude = "";

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getDirectional() {
        return directional;
    }

    public void setDirectional(String directional) {
        this.directional = directional;
    }

    public String getPostDirectional() {
        return postDirectional;
    }

    public void setPostDirectional(String postDirectional) {
        this.postDirectional = postDirectional;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getZip5() {
        return zip5;
    }

    public void setZip5(String zip5) {
        this.zip5 = zip5;
    }

    public String getZip4() {
        return zip4;
    }

    public void setZip4(String zip4) {
        this.zip4 = zip4;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public String getCarrierRoute() {
        return carrierRoute;
    }

    public void setCarrierRoute(String carrierRoute) {
        this.carrierRoute = carrierRoute;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "{" +
                "houseNo:\"" + houseNo + "\"" +
                ", suite:\"" + suite + "\"" +
                ", directional:\"" + directional + "\"" +
                ", postDirectional:\"" + postDirectional + "\"" +
                ", streetName:\"" + streetName + "\"" +
                ", zip5:\"" + zip5 + "\"" +
                ", zip4:\"" + zip4 + "\"" +
                ", communityName:\"" + communityName + "\"" +
                ", communityId:\"" + communityId + "\"" +
                ", poBox:\"" + poBox + "\"" +
                ", country:\"" + country + "\"" +
                ", state:\"" + state + "\"" +
                ", stateAbbr:\"" + stateAbbr + "\"" +
                ", carrierRoute:\"" + carrierRoute + "\"" +
                ", addressLocation:\"" + addressLocation + "\"" +
                ", latitude:\"" + latitude + "\"" +
                ", longtitude:\"" + longtitude + "\"" +
                "}";
    }
}
