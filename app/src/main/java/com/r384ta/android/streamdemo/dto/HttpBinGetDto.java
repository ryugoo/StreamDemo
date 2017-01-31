package com.r384ta.android.streamdemo.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r384ta.android.streamdemo.model.HttpBinGet;

import io.github.kobakei.spot.annotation.Pref;
import io.github.kobakei.spot.annotation.PrefString;
import io.github.kobakei.spot.converter.TypeConverter;

@Pref(name = "dto")
public class HttpBinGetDto {
    @PrefString(name = "http_bin_get", converter = Converter.class)
    public HttpBinGet httpBinGet;

    public HttpBinGetDto() {
    }

    public HttpBinGetDto(HttpBinGet httpBinGet) {
        this.httpBinGet = httpBinGet;
    }

    public static class Converter extends TypeConverter<HttpBinGet, String> {
        private static final Gson GSON = new GsonBuilder().serializeNulls().create();

        @Override
        public HttpBinGet convertFromSupportedType(String text) {
            return GSON.fromJson(text, HttpBinGet.class);
        }

        @Override
        public String convertToSupportedType(HttpBinGet httpBinGet) {
            return GSON.toJson(httpBinGet);
        }
    }
}
