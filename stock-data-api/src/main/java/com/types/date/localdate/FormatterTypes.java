package com.types.date.localdate;

import java.time.format.DateTimeFormatter;

public enum FormatterTypes {
    YYYYMMDD(1, "YYYYMMDD"){

    },
    YYYYMMDDHH(2, "YYYYMMDDHH"){

    },
    YYYYMMDDHHMI(3, "YYYYMMDDHHMI"){

    },
    YYYYMMDDHHMISS(4, "YYYYMMDDHHMISS"){

    };

    private int formatterTypeCd;
    private String format;

    FormatterTypes(int formatterTyepCd, String format){
        this.formatterTypeCd = formatterTyepCd;
        this.format = format;
    }

    public DateTimeFormatter getDateTimeFormatter(){
        return DateTimeFormatter.ofPattern(this.getFormat());
    }

    public int getFormatterTypeCd() {
        return formatterTypeCd;
    }

    public void setFormatterTypeCd(int formatterTypeCd) {
        this.formatterTypeCd = formatterTypeCd;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
