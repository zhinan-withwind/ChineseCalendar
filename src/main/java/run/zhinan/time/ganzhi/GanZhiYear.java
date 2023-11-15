package run.zhinan.time.ganzhi;

public class GanZhiYear extends GanZhi {
    int year;

    private GanZhiYear(Gan gan, Zhi zhi) {
        super(gan, zhi);
    }

    private GanZhiYear(GanZhi ganZhi) {
        this(ganZhi.gan, ganZhi.zhi);
    }

    public static GanZhiYear of(int year) {
        GanZhiYear ganZhiYear = new GanZhiYear(toGanZhi(year));
        ganZhiYear.year = year;
        return ganZhiYear;
    }
}
