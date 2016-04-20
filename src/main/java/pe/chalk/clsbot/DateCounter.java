/*
 * Copyright (C) 2015-2016  ChalkPE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pe.chalk.clsbot;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressWarnings("unused")

/**
 * @author ChalkPE <chalkpe@gmail.com>
 * @since 2016-01-02
 */
public class DateCounter {
    //void, Date
    public static String count(Date dest){
        return DateCounter.count(dest.getTime());
    }

    //void, Calendar
    public static String count(Calendar dest){
        return DateCounter.count(dest.getTimeInMillis());
    }

    //void, (int, int, int)
    public static String count(int destYear, int destMonth, int destDate){
        return DateCounter.count(new GregorianCalendar(destYear, destMonth, destDate).getTimeInMillis());
    }

    //void, long
    public static String count(long dest){
        return DateCounter.count(System.currentTimeMillis(), dest);
    }



    //Date, Date
    public static String count(Date src, Date dest){
        return DateCounter.count(src, dest.getTime());
    }

    //Date, Calendar
    public static String count(Date src, Calendar dest){
        return DateCounter.count(src, dest.getTimeInMillis());
    }

    //Date, (int, int, int)
    public static String count(Date src, int destYear, int destMonth, int destDate){
        return DateCounter.count(src, new GregorianCalendar(destYear, destMonth, destDate).getTimeInMillis());
    }

    //Date, long
    public static String count(Date src, long dest){
        return DateCounter.count(src.getTime(), dest);
    }



    //Calendar, Date
    public static String count(Calendar src, Date dest){
        return DateCounter.count(src, dest.getTime());
    }

    //Calendar, Calendar
    public static String count(Calendar src, Calendar dest){
        return DateCounter.count(src, dest.getTimeInMillis());
    }

    //Calendar, (int, int, int)
    public static String count(Calendar src, int destYear, int destMonth, int destDate){
        return DateCounter.count(src, new GregorianCalendar(destYear, destMonth, destDate).getTimeInMillis());
    }

    //Calendar, long
    public static String count(Calendar src, long dest){
        return DateCounter.count(src.getTimeInMillis(), dest);
    }



    //(int, int, int), Date
    public static String count(int srcYear, int srcMonth, int srcDate, Date dest){
        return DateCounter.count(srcYear, srcMonth, srcDate, dest.getTime());
    }

    //(int, int, int), Calendar
    public static String count(int srcYear, int srcMonth, int srcDate, Calendar dest){
        return DateCounter.count(srcYear, srcMonth, srcDate, dest.getTimeInMillis());
    }

    //(int, int, int), (int, int, int)
    public static String count(int srcYear, int srcMonth, int srcDate, int destYear, int destMonth, int destDate){
        return DateCounter.count(srcYear, srcMonth, srcDate, new GregorianCalendar(destYear, destMonth, destDate).getTimeInMillis());
    }

    //(int, int, int), long
    public static String count(int srcYear, int srcMonth, int srcDate, long dest){
        return DateCounter.count(new GregorianCalendar(srcYear, srcMonth, srcDate).getTimeInMillis(), dest);
    }



    //long, Date
    public static String count(long src, Date dest){
        return DateCounter.count(src, dest.getTime());
    }

    //long, Calendar
    public static String count(long src, Calendar dest){
        return DateCounter.count(src, dest.getTimeInMillis());
    }

    //long, (int, int, int)
    public static String count(long src, int destYear, int destMonth, int destDate){
        return DateCounter.count(src, new GregorianCalendar(destYear, destMonth, destDate).getTimeInMillis());
    }

    //long, long
    public static String count(long src, long dest){
        src -= src % 86400000;
        dest -= dest % 86400000;

        long days = (src - dest) / 86400000;
        return String.format(days == 0 ? "D-DAY" : "D%+d", days);
    }
}
