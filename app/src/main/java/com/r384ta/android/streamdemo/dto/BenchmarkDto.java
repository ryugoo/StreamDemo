package com.r384ta.android.streamdemo.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r384ta.android.streamdemo.model.Benchmark;

import io.github.kobakei.spot.annotation.Pref;
import io.github.kobakei.spot.annotation.PrefString;
import io.github.kobakei.spot.converter.TypeConverter;

@Pref(name = "dto")
public class BenchmarkDto {
    @PrefString(name = "benchmark", converter = Converter.class)
    public Benchmark benchmark;

    public BenchmarkDto() {
    }

    public BenchmarkDto(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public static class Converter extends TypeConverter<Benchmark, String> {
        private static final Gson GSON = new GsonBuilder().serializeNulls().create();

        @Override
        public Benchmark convertFromSupportedType(String text) {
            return GSON.fromJson(text, Benchmark.class);
        }

        @Override
        public String convertToSupportedType(Benchmark benchmark) {
            return GSON.toJson(benchmark);
        }
    }
}
