package pe.area51.reversegeocoder;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseParser {

    private static final String KEY_LATITUDE = "lat";
    private static final String KEY_LONGITUDE = "lon";
    private static final String KEY_DISPLAY_NAME = "display_name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_COUNTRY = "country";

    public static Location parseServerResponse(final String serverResponse) throws JSONException {
        final JSONObject response = new JSONObject(serverResponse);
        final double latitude = Double.parseDouble(response.getString(KEY_LATITUDE));
        final double longitude = Double.parseDouble(response.getString(KEY_LONGITUDE));
        final String address = response.getString(KEY_DISPLAY_NAME);
        final String country = response.getJSONObject(KEY_ADDRESS).getString(KEY_COUNTRY);
        return new Location(latitude, longitude, address, country);
    }

}
