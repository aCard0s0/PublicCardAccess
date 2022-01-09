package dev.pca.models.converters;

import com.google.common.base.Converter;
import org.springframework.stereotype.Service;

@Service
public class SiglaFrameConverter extends Converter<String, String> {
    @Override
    protected String doForward(String sigla) {
        switch (sigla) {
            case "cf":
                return "Cold Foil";
            case "rf":
                return "Rainbow Foil";
            case "ds":
                return "Double Slided";
        }
        return null;
    }

    @Override
    protected String doBackward(String s) {
        return null;
    }
}
