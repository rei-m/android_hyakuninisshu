package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class ImageNo implements ValueObject {

    private static final Pattern pattern = Pattern.compile("^(?!000)(0\\d\\d|001|100)$");

    private final String value;

    public ImageNo(@NonNull String value) throws IllegalArgumentException {
        if (pattern.matcher(value).matches()) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("ImageNo is Invalid, value is " + value);
        }
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageNo imageNo = (ImageNo) o;

        return value.equals(imageNo.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "ImageNo{" +
                "value='" + value + '\'' +
                '}';
    }
}
