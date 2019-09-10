package com.types.date.localdate;

import java.time.format.DateTimeFormatter;

public enum FormatterTypes {

    YYYYMMDD(1, "YYYYMMDD"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return null;
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return null;
        }
    },
    YYYYMMDDHH(2, "YYYYMMDDHH"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return null;
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return null;
        }
    },
    YYYYMMDDHHMI(3, "YYYYMMDDHHMI"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return null;
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return null;
        }
    },
    YYYYMMDDHHMISS(4, "YYYYMMDDHHMISS"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return null;
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return null;
        }

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

    public abstract DateTimeFormatter processDashFormat();
    public abstract DateTimeFormatter processSlashFormat();

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
