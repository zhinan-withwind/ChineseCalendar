package run.zhinan.time.ganzhi;

public class GanZhi {
    HeavenlyStem  gan;
    EarthlyBranch zhi;

    public GanZhi(HeavenlyStem gan, EarthlyBranch zhi) {
        this.gan = gan;
        this.zhi = zhi;
    }

    public HeavenlyStem getGan() {
        return gan;
    }

    public EarthlyBranch getZhi() {
        return zhi;
    }

    public String getName() {
        return gan.getName() + zhi.getName();
    }
}
