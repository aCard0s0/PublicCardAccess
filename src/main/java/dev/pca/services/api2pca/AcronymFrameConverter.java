package dev.pca.services.api2pca;

import com.google.common.base.Converter;
import org.springframework.stereotype.Service;

@Service
public class AcronymFrameConverter extends Converter<String, String> {
    @Override
    protected String doForward(String acronym) {
        switch (acronym) {
            case "gf":
                return "Gold Foil";
            case "cf":
                return "Cold Foil";
            case "rf":
                return "Rainbow";
            case "ds":
                return "Double Side";
        }
        return null;
    }

    @Override
    protected String doBackward(String s) {
        return null;
    }
}
