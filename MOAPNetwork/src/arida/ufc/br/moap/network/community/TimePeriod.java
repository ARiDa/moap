/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

/**
 *
 * @author igobrilhante
 */
public enum TimePeriod {

    HOUR, DAY, MONTH, YEAR;

    
    public String getFormat2Query(String time) {
        if (this == HOUR) {
            return "extract(year from "+time+") as year,extract(month from "+time+") as month,extract(day from "+time+") as day,extract(hour from "+time+") as hour";
        }
        if (this == DAY) {
            return "extract(year from "+time+") as year,extract(month from "+time+") as month,extract(day from "+time+") as day";
        }
        if (this == MONTH) {
            return "extract(year from "+time+") as year,extract(month from "+time+") as month";
        }
        if (this == YEAR) {
            return "extract(year from "+time+") as year";
        }

        return null;
    
    }
    
   public String getFormat2GroupBy(String time) {
        if (this == HOUR) {
            return "extract(year from "+time+") ,extract(month from "+time+") ,extract(day from "+time+") ,extract(hour from "+time+")";
        }
        if (this == DAY) {
            return "extract(year from "+time+") ,extract(month from "+time+") ,extract(day from "+time+") ";
        }
        if (this == MONTH) {
            return "extract(year from "+time+") ,extract(month from "+time+") ";
        }
        if (this == YEAR) {
            return "extract(year from "+time+") ";
        }

        return null;
    
    }

    public String getFormat2Select() {
        if (this == HOUR) {
            return "year, month, day, hour";
        }
        if (this == DAY) {
            return " year, month, day";
        }
        if (this == MONTH) {
            return " year, month";
        }
        if (this == YEAR) {
            return " year";
        }

        return null;
    }
    
//    public String getTimeStampFormat(){
//        if (this == HOUR) {
//            return "to_timestamp(year month day, hour";
//        }
//        if (this == DAY) {
//            return " year, month, day";
//        }
//        if (this == MONTH) {
//            return " year, month";
//        }
//        if (this == YEAR) {
//            return " year";
//        }
//
//        return null;
//    }
}
