package com.example.riskcontrol.model;

import java.util.Date;

/**
 * Created by sunpeak on 2016/8/6.
 */
public enum EnumTimePeriod {

    ALL,
    LASTMIN,
    LAST5MIN,
    LAST10MIN,
    LASTHOUR,
    LASTDAY,
    LASTWEEK,
    LASTTWOWEEK,
    LASTYEAR;

    public Date getMinTime(Date now) {
        if (this.equals(ALL)) {
            return new Date(0);
        } else {
            return new Date(now.getTime() - getTimeDiff());
        }
    }

    public Date getMaxTime(Date now) {
        return now;
    }

    public long getTimeDiff() {
        long timeDiff;
        switch (this) {
            case ALL:
                timeDiff = Long.MAX_VALUE;
                break;
            case LASTMIN:
                timeDiff = 60 * 1000L;
                break;
            case LAST5MIN:
                timeDiff = 60 * 1000L*5;
                break;
            case LAST10MIN:
                timeDiff = 60 * 1000L*10;
                break;
            case LASTHOUR:
                timeDiff = 3600 * 1000L;
                break;
            case LASTDAY:
                timeDiff = 24 * 3600 * 1000L;
                break;
            case LASTWEEK:
                timeDiff = 7 * 24 * 3600 * 1000L;
                break;
            case LASTTWOWEEK:
                timeDiff = 7 * 24 * 2 * 3600 * 1000L;
                break;
            case LASTYEAR:
                timeDiff = 365 * 24 * 3600 * 1000L;
                break;
            default:
                timeDiff = 60 * 1000L;
                break;
        }
        return timeDiff;
    }
}
