package run.zhinan.time.ganzhi;

public class GanZhiYear extends GanZhi {
    int year;
    int cycle;

    public GanZhiYear(Gan gan, Zhi zhi) {
        super(gan, zhi);
    }

    public GanZhiYear(GanZhi ganZhi) {
        super(ganZhi.gan, ganZhi.zhi);
    }

    public static GanZhiYear of(int year) {
        return new GanZhiYear(toGanZhi(year));
    }
}
