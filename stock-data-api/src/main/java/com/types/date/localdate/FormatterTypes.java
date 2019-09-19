package com.types.date.localdate;

import java.time.format.DateTimeFormatter;

public enum FormatterTypes {
    YYYYMM01(0, "yyyyMM01"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return DateTimeFormatter.ofPattern("yyyy-MM-01");
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return DateTimeFormatter.ofPattern("yyyy/MM/01");
        }
    },

    YYYYMMDD(1, "yyyyMMdd"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return DateTimeFormatter.ofPattern("yyyy/MM/dd");
        }
    },
    YYYYMMDDHH(2, "yyyyMMddhh"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return null;
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return null;
        }
    },
    YYYYMMDDHHMI(3, "yyyyMMddhhmm"){
        @Override
        public DateTimeFormatter processDashFormat() {
            return null;
        }

        @Override
        public DateTimeFormatter processSlashFormat() {
            return null;
        }
    },
    YYYYMMDDHHMISS(4, "yyyyMMddhhmmss"){
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

    public DateTimeFormatter ofPattern(){
        return DateTimeFormatter.ofPattern(getFormat());
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
