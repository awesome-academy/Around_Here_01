package com.trunghoang.aroundhere.data.remote;

import com.trunghoang.aroundhere.data.model.Place;

import org.json.JSONArray;
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
        long priceMin = readerPlace.getLong(Place.JSONKey.PRICE_MIN);
        long priceMax = readerPlace.getLong(Place.JSONKey.PRICE_MAX);
        JSONArray timeRangesJson = readerPlace.getJSONArray(Place.JSONKey.TIME_RANGES);
        String startTime = null;
        String endTime = null;
        if (timeRangesJson.length() != 0) {
            JSONObject time0 = timeRangesJson.getJSONObject(0);
            startTime = time0.getString(Place.JSONKey.TIME_START);
            endTime = time0.getString(Place.JSONKey.TIME_END);
        }
        String resId = readerPlace.getString(Place.JSONKey.RES_ID);
        return new Place.Builder()
                .setPriceMin(priceMin)
                .setPriceMax(priceMax)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setResId(resId)
                .build();
    }

    private String getPlaceDetailString(String htmlResponseString) {
        Pattern pattern = Pattern.compile("initDataMain = (.*?);");
        Matcher matcher = pattern.matcher(htmlResponseString);
        if (!matcher.find()) return null;
        return matcher.group(1);
    }
}
