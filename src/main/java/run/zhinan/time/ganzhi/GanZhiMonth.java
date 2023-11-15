package run.zhinan.time.ganzhi;

public class GanZhiMonth extends GanZhi {
    public GanZhiMonth(Gan gan, Zhi zhi) {
        super(gan, zhi);
    }

    public GanZhiMonth(GanZhi ganZhi) {
        this(ganZhi.getGan(), ganZhi.getZhi());
    }
}
