package run.zhinan.time.ganzhi;

public class GanZhi {
    Gan gan;
    Zhi zhi;

    public GanZhi(Gan gan, Zhi zhi) {
        this.gan = gan;
        this.zhi = zhi;
    }

    public static GanZhi of(int ganValue, int zhiValue) {
        return new GanZhi(Gan.getByValue(ganValue), Zhi.getByValue(zhiValue));
    }

    public static GanZhi getByValue(int value) {
        return of(value % 10 + 1, value % 12 + 1);
    }

    public Gan getGan() {
        return gan;
    }

    public Zhi getZhi() {
        return zhi;
    }

    public Xun getXun() {
        return Xun.getByValue((6 - ((zhi.getValue() - gan.getValue() + 12) % 12) / 2) % 6);
    }

    public int getValue() {
        return getXun().getValue() * 10 + gan.getValue();
    }

    public String getName() {
        return getGan().getName() + getZhi().getName();
    }

    public GanZhi roll(int i) {
        return getByValue((getValue() + i + 60) % 60);
    }

    @Override
    public String toString() {
        return getName();
    }
}
