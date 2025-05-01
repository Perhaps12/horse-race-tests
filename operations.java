public class operations {
    public static double angle(double a, double b, double c, double d){
        
        if(a==c){
            if(b<d)return 0;
            return Math.PI;
        }
        if(b==d){
            if(a>c)return Math.PI/2;
            return Math.PI*3/2;
        }

        double ang = Math.atan(Math.abs(b-d) / Math.abs(a-c));
        if(a < c && b > d)return ang;
        if(a < c && b < d)return 2*Math.PI-ang;
        if(a>c&&b>d)return Math.PI-ang;
        return Math.PI + ang;
    }
}



