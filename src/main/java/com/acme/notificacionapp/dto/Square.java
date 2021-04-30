package com.acme.notificacionapp.dto;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Square {

    @NotBlank
    private String leyend;
    @Min(0)
    @NonNull
    private Integer side;
    private String colour;

    public Square(@NotBlank String leyend, @NonNull @Min(0) Integer side, String colour) {
        this.leyend = leyend;
        this.side = side;
        this.colour = colour;
    }


    public String getLeyend() {
        return leyend;
    }

    public void setLeyend(String leyend) {
        this.leyend = leyend;
    }

    @NonNull
    public Integer getSide() {
        return side;
    }

    public void setSide(@NonNull Integer side) {
        this.side = side;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
