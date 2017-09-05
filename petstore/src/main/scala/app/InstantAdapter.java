package app;

import com.squareup.moshi.ToJson;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Created by Sławomir Kluz on 05/09/2017.
 */
public class InstantAdapter {

    @ToJson
    public String toJson(Instant instant) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX").withZone(ZoneOffset.UTC).format(instant);
    }

}
