package com.trunghoang.aroundhere.data.remote;

import com.trunghoang.aroundhere.data.model.Place;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceDownloadHandler extends BaseDataDownloadHandler<Place> {

    @Override
    public Place parseRawToData(String in) throws JSONException {
        if (in == null) return null;
        String placeDetailString = getPlaceDetailString(in);
        if (placeDetailString == null) return null;
        JSONObject readerPlace = new JSONObject(placeDetailString);
        return new Place(readerPlace);
    }

    private String getPlaceDetailString(String htmlResponseString) {
        Pattern pattern = Pattern.compile("initDataMain = (.*?);");
        Matcher matcher = pattern.matcher(htmlResponseString);
        if (!matcher.find()) return null;
        return matcher.group(1);
    }
}
