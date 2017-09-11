package cn.wingene.mallxm.map;

import com.google.gson.annotations.SerializedName;

public class Location {
    /**
     * 经度
     */
    @SerializedName("Longitude")
    private Double longitude;

    /**
     * 纬度
     */
    @SerializedName("Latitude")
    private Double latitude;

    /**
     * 地址
     */
    @SerializedName("Address")
    private String address;

    /**
     * 经度
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 经度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 纬度
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 纬度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public Location(String address) {
        this.longitude = null;
        this.latitude = null;
        this.address = address;
    }

    public Location(Double longitude, Double latitude, String address) {
        load(longitude, latitude, address);
    }

    public Location() {
    }

    public Location(Location location) {
        load(location);
    }

    public void load(Location location) {
        this.longitude = location.longitude;
        this.latitude = location.latitude;
        this.address = location.address;
    }

    public void load(Double longitude, Double latitude, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (longitude != null ? !longitude.equals(location.longitude) : location.longitude != null) return false;
        if (latitude != null ? !latitude.equals(location.latitude) : location.latitude != null) return false;
        return address != null ? address.equals(location.address) : location.address == null;

    }

    //    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Location other = (Location) obj;
//        if (latitude == null) {
//            if (other.latitude != null)
//                return false;
//        } else if (!latitude.equals(other.latitude))
//            return false;
//        if (longitude == null) {
//            if (other.longitude != null)
//                return false;
//        } else if (!longitude.equals(other.longitude))
//            return false;
//        return true;
//    }

    public final static class F {
        /**
         * 经度
         */
        public final static String longitude = "longitude";

        /**
         * 纬度
         */
        public final static String latitude = "latitude";

        /**
         * 地址
         */
        public final static String address = "address";
    }

}
