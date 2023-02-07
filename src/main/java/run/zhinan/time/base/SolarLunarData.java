package run.zhinan.time.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import run.zhinan.time.util.FileUtil;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SolarLunarData {
    private final static List<List<Integer>> SOLAR_DATA = new ArrayList<>();
    private final static List<List<Integer>> LUNAR_DATA = new ArrayList<>();

    private final static int minYear = -3501;
    private final static int maxYear =  3501;

    private final static int offsetSolar = 5;
    private final static int offsetLunar = 5;

    private final static HashMap<Integer, List<Integer>> solarDataCache = new HashMap<>();
    private final static HashMap<Integer, List<Integer>> lunarDataCache = new HashMap<>();

    static {
        loadData("data/solarTermData.json", SOLAR_DATA);
        loadData("data/lunarTermData.json", LUNAR_DATA);
    }

    private static void loadData(String dataFilePath, List<List<Integer>> data) {
        JSONArray dataSet = JSON.parseArray(FileUtil.loadResource(dataFilePath));
        for (Object o : dataSet) {
            JSONArray yearData = (JSONArray) o;
            List<Integer> dataLine = new ArrayList<>();
            data.add(dataLine);
            for (Object yearDatum : yearData) {
                dataLine.add((int) yearDatum);
            }
        }
    }

    private static boolean isValid(int year) {
        return minYear <= year && year <= maxYear;
    }

    private static double mod2piDe(double x) {
        return x - 2 * Math.PI * Math.floor(0.5 * x / Math.PI + 0.5);
    }

    private static double integratedLod(double y) {
        double u = y - 1825;
        return 3.14115e-3 * u * u + 284.8435805251424 * Math.cos(0.4487989505128276 * (0.01 * u + 0.75));
    }

    private static double deltaTSplineY(double y) {
        if (y < -720) {
            // use integrated lod + constant
            return integratedLod(y) + 1.007739546148514;
        }
        if (y > 2022) {
            // use integrated lod + constant
            // const c = -150.263031657016;
            return integratedLod(y) - 150.3150351029286;
        }

        // use cubic spline fit
        double[] y0 = {-720, -100, 400, 1000, 1150, 1300, 1500, 1600, 1650, 1720, 1800, 1810, 1820, 1830, 1840, 1850, 1855, 1860, 1865, 1870, 1875, 1880, 1885, 1890, 1895, 1900, 1905, 1910, 1915, 1920, 1925, 1930, 1935, 1940, 1945, 1950, 1953, 1956, 1959, 1962, 1965, 1968, 1971, 1974, 1977, 1980, 1983, 1986, 1989, 1992, 1995, 1998, 2001, 2004, 2007, 2010, 2013, 2016, 2019};
        double[] y1 = {-100, 400, 1000, 1150, 1300, 1500, 1600, 1650, 1720, 1800, 1810, 1820, 1830, 1840, 1850, 1855, 1860, 1865, 1870, 1875, 1880, 1885, 1890, 1895, 1900, 1905, 1910, 1915, 1920, 1925, 1930, 1935, 1940, 1945, 1950, 1953, 1956, 1959, 1962, 1965, 1968, 1971, 1974, 1977, 1980, 1983, 1986, 1989, 1992, 1995, 1998, 2001, 2004, 2007, 2010, 2013, 2016, 2019, 2022};
        double[] a0 = {20371.848, 11557.668, 6535.116, 1650.393, 1056.647, 681.149, 292.343, 109.127, 43.952, 12.068, 18.367, 15.678, 16.516, 10.804, 7.634, 9.338, 10.357, 9.04, 8.255, 2.371, -1.126, -3.21, -4.388, -3.884, -5.017, -1.977, 4.923, 11.142, 17.479, 21.617, 23.789, 24.418, 24.164, 24.426, 27.05, 28.932, 30.002, 30.76, 32.652, 33.621, 35.093, 37.956, 40.951, 44.244, 47.291, 50.361, 52.936, 54.984, 56.373, 58.453, 60.678, 62.898, 64.083, 64.553, 65.197, 66.061, 66.919, 68.128, 69.248};
        double[] a1 = {-9999.586, -5822.27, -5671.519, -753.21, -459.628, -421.345, -192.841, -78.697, -68.089, 2.507, -3.481, 0.021, -2.157, -6.018, -0.416, 1.642, -0.486, -0.591, -3.456, -5.593, -2.314, -1.893, 0.101, -0.531, 0.134, 5.715, 6.828, 6.33, 5.518, 3.02, 1.333, 0.052, -0.419, 1.645, 2.499, 1.127, 0.737, 1.409, 1.577, 0.868, 2.275, 3.035, 3.157, 3.199, 3.069, 2.878, 2.354, 1.577, 1.648, 2.235, 2.324, 1.804, 0.674, 0.466, 0.804, 0.839, 1.005, 1.341, 0.620};
        double[] a2 = {776.247, 1303.151, -298.291, 184.811, 108.771, 61.953, -6.572, 10.505, 38.333, 41.731, -1.126, 4.629, -6.806, 2.944, 2.658, 0.261, -2.389, 2.284, -5.148, 3.011, 0.269, 0.152, 1.842, -2.474, 3.138, 2.443, -1.329, 0.831, -1.643, -0.856, -0.831, -0.449, -0.022, 2.086, -1.232, 0.22, -0.61, 1.282, -1.115, 0.406, 1.002, -0.242, 0.364, -0.323, 0.193, -0.384, -0.14, -0.637, 0.708, -0.121, 0.21, -0.729, -0.402, 0.194, 0.144, -0.109, 0.275, 0.061, -0.782};
        double[] a3 = {409.16, -503.433, 1085.087, -25.346, -24.641, -29.414, 16.197, 3.018, -2.127, -37.939, 1.918, -3.812, 3.25, -0.096, -0.539, -0.883, 1.558, -2.477, 2.72, -0.914, -0.039, 0.563, -1.438, 1.871, -0.232, -1.257, 0.72, -0.825, 0.262, 0.008, 0.127, 0.142, 0.702, -1.106, 0.614, -0.277, 0.631, -0.799, 0.507, 0.199, -0.414, 0.202, -0.229, 0.172, -0.192, 0.081, -0.165, 0.448, -0.276, 0.11, -0.313, 0.109, 0.199, -0.017, -0.084, 0.128, -0.071, -0.281, 0.193};

        int i;
        for (i = y0.length - 1; i >= 0; i--) {
            if (y >= y0[i]) {
                break;
            }
        }
        double t = (y - y0[i]) / (y1[i] - y0[i]);
        return a0[i] + t * (a1[i] + t * (a2[i] + t * a3[i]));
    }

    private static double deltaT(double t) {
        double jd = 36525 * t + 2451545;
        if (jd > 2459945.5 || jd < 2441317.5) {
            double y = (jd >= 2299160.5 ? (jd - 2451544.5) / 365.2425 + 2000 : (jd + 0.5) / 365.25 - 4712);
            return deltaTSplineY(y) / 86400;
        }

        // Leap second Julian Date's
        double[] julianDateLeaps = {
                2457754.5, 2457204.5, 2456109.5, 2454832.5,
                2453736.5, 2451179.5, 2450630.5, 2450083.5,
                2449534.5, 2449169.5, 2448804.5, 2448257.5,
                2447892.5, 2447161.5, 2446247.5, 2445516.5,
                2445151.5, 2444786.5, 2444239.5, 2443874.5,
                2443509.5, 2443144.5, 2442778.5, 2442413.5,
                2442048.5, 2441683.5, 2441499.5, 2441133.5
        };

        int n = julianDateLeaps.length;
        double dT = 42.184;
        for (int i = 0; i < n; i++) {
            if (jd > julianDateLeaps[i]) {
                dT += n - i - 1;
                break;
            }
        }
        return dT / 86400;
    }

    private static List<Integer> decompressSolarData(int y, int start, List<Integer> solarData) {
        double jd0 = Date.of(y - 1, 12, 31).toJulianDate() - 1.0 / 3;
        double deltaT = deltaT((jd0 - 2451545 + 365.25 * 0.5) / 36525);
        double offset = 2451545 - jd0 - deltaT;
        double[] w = {2 * Math.PI, 6.282886, 12.565772, 0.337563, 83.99505, 77.712164, 5.7533, 3.9301};
        double[] poly, amp, ph;
        if (y > 2500) {
            poly = new double[]{-10.60617210417765, 365.2421759265393, -2.701502510496315e-08, 2.303900971263569e-12};
            amp  = new double[]{0.1736157870707964, 1.914572713893651, 0.0113716862045686, 0.004885711219368455, 0.0004032584498264633, 0.001736052092601642, 0.002035081600709588, 0.001360448706185977};
            ph   = new double[]{-2.012792258215681, 2.824063083728992, -0.4826844382278376, 0.9488391363261893, 2.646697770061209, -0.2675341497460084, 0.9646288791219602, -1.808852094435626};
        } else if (y > 1500) {
            poly = new double[]{-10.6111079510509, 365.2421925947405, -3.888654930760874e-08, -5.434707919089998e-12};
            amp  = new double[]{0.1633918030382493, 1.95409759473169, 0.01184405584067255, 0.004842563463555804, 0.0004137082581449113, 0.001732513547029885, 0.002025850272284684, 0.001363226024948773};
            ph   = new double[]{-1.767045717746641, 2.832417615687159, -0.465176623256009, 0.9461667782644696, 2.713020913181211, -0.2031148059020781, 0.9980808019332812, -1.832536089597202};
        } else if (y > 500) {
            poly = new double[]{-10.60709730410452, 365.2422127270854, -1.134058393100622e-08, 4.141936158464871e-12};
            amp  = new double[]{0.1535334251510338, 1.990156031948326, 0.0124085292700179, 0.004817476776395462, 0.0004231197796757052, 0.001727333350295303, 0.002027272034304837, 0.001362243549150769};
            ph   = new double[]{-1.53391167489547, 2.838890252423028, -0.4473425740492739, 0.9470892985673597, 2.756067116397267, -0.1593276951249671, 1.040350517138996, -1.860525672516114};
        } else if (y > -500) {
            poly = new double[]{-10.68709748628232, 365.2420821934196, -8.026384367656781e-08, -7.348485292495799e-12};
            amp  = new double[]{0.1440763971158344, 2.022894736306276, 0.01308204534105261, 0.004736765939053456, 0.0004322697656559368, 0.001723596791368871, 0.002029120044384331, 0.001364115073101618};
            ph   = new double[]{-1.313506201837862, 2.843634771064815, -0.4359114589514807, 0.9348292620644294, 2.780387520671063, -0.1339346072120813, 1.076267591985513, -1.884934776236827};
        } else if (y > -1500) {
            poly = new double[]{-10.30754340442452, 365.2424975592232, 6.988619764895545e-08, 1.050737967068955e-11};
            amp  = new double[]{0.1351903251881648, 2.052311107597184, 0.0138099647007606, 0.004710022184619089, 0.0004401559998742864, 0.001719717876055052, 0.002028576898954891, 0.001361457276255351};
            ph   = new double[]{-1.107079422161242, 2.846723981804216, -0.4355459012284469, 0.9153537139546954, 2.781449417943638, -0.1293697938800294, 1.107571558152934, -1.914227331488101};
        } else if (y > -2500) {
            poly = new double[]{-11.12685596324429, 365.2417944496895, -1.31748160241216e-07, -8.828530061476014e-12};
            amp  = new double[]{0.1271197172968171, 2.078422226635316, 0.0145337050557788, 0.004656482196663851, 0.0004478230812071957, 0.001715373412940888, 0.002025010123327869, 0.001364403550886664};
            ph   = new double[]{-0.9155939884500043, 2.848238199827564, -0.446868230266368, 0.8937419981479656, 2.76402935045861, -0.1424989896840534, 1.147354850309886, -1.939150748948836};
        } else {
            poly = new double[]{-9.915643653391776, 365.2426106062384, 5.136755803058566e-08, 4.851734864125751e-12};
            amp  = new double[]{0.1201183328932843, 2.101463483123601, 0.01517212621633674, 0.004594133041759479, 0.000454693884957534, 0.001712582968849221, 0.002031323893731684, 0.001361713041935677};
            ph   = new double[]{-0.7372346873381378, 2.848221611489024, -0.4687032592016843, 0.8646822120084756, 2.724905252819503, -0.1746432218443849, 1.182372859354316, -1.970163397303521};
        }

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < solarData.size(); i++) {
            double ls = (y - 2000) + (i + start) / 24.0;
            double s = poly[0] + offset + ls * (poly[1] + ls * (poly[2] + ls * poly[3]));
            for (int j = 0; j < 8; j++) {
                double angle = mod2piDe(w[j] * ls) + ph[j];
                s += amp[j] * Math.sin(angle);
            }
            double s1 = Math.floor((s - Math.floor(s)) * 1440 + 0.5);
            data.add(new Double(s1 + 1441 * Math.floor(s) + solarData.get(i) - offsetSolar).intValue());
        }
        return data;
    }

    private static List<Integer> decompressLunarData(int y, List<Integer> lunarData, double dp) {
        double[] w = {
                2 * Math.PI, 6.733776, 13.467552, 0.507989,
                0.0273143, 0.507984, 20.201328, 6.225791, 7.24176, 5.32461,
                12.058386, 0.901181, 5.832595, 12.56637061435917, 19.300146,
                11.665189, 18.398965, 6.791174, 13.636974, 1.015968, 6.903198,
                13.07437, 1.070354, 6.340578614359172
        };

        double[] poly, amp, ph;
        if (y > 2500) {
            poly = new double[] {5.093879710922470, 29.53058981687484, 2.670339910922144e-11, 1.807808217274283e-15};
            amp  = new double[] {0.00306380948959271, 6.08567588841838, 0.3023856209133756,   0.07481389897992345, 0.0001587661348338354, 0.1740759063081489,     0.0004131985233772993, 0.005796584475300004, 0.008268929076163079, 0.003256244384807976, 0.000520983165608148, 0.003742624708965854,   1.709506053530008, 28216.70389751519, 1.598844831045378,           0.314745599206173, 6.602993931108911, 0.0003387269181720862,       0.009226112317341887, 0.00196073145843697, 0.001457643607929487,   6.467401779992282e-05, 0.0007716739483064076, 0.001378880922256705};
            ph   = new double[] {-0.0001879456766404132, -2.745704167588171, -2.348884895288619, 1.420037528559222, -2.393586904955103, -0.3914194006325855, 1.183088056748942, -2.782692143601458, 0.4430565056744425, -0.4357413971405519, -3.081209195003025, 0.7945051912707899, -0.4010911170136437, 3.003035462639878e-10, 0.4040070684461441, 2.351831380989509, 2.748612213507844, 3.133002890683667, -0.6902922380876192, 0.09563473131477442, 2.056490394534053, 2.017507533465959, 2.394015964756036, -0.3466427504049927};
        } else if (y > 1500) {
            poly = new double[] {5.097475813506625, 29.53058886049267, 1.095399949433705e-10, -6.926279905270773e-16};
            amp  = new double[] {0.003064332812182054, 0.8973816160666801, 0.03119866094731004, 0.07068988004978655, 0.0001583070735157395, 0.1762683983928151, 0.0004131592685474231, 0.005950873973350208, 0.008489324571543966, 0.00334306526160656, 0.00052946042568393, 0.003743585488835091, 0.2156913373736315, 44576.30467073629, 0.1050203948601217, 0.01883710371633125, 0.380047745859265, 0.0003472930592917774, 0.009225665415301823, 0.002061407071938891, 0.001454599562245767, 5.856419090840883e-05, 0.0007688706809666596, 0.001415547168551922};
            ph   = new double[] {-0.0003231124735555465, 0.380955331199635, 0.762645225819612, 1.4676293538949, -2.15595770830073, -0.3633370464549665,           1.134950591549256, -2.808169363709888, 0.422381840383887,           -0.4226859182049138, -3.091797336860658, 0.7563140142610324,       -0.3787677293480213, 1.863828515720658e-10, 0.3794794147818532,     -0.7671105159156101, -0.3850942687637987, -3.098506117162865,       -0.6738173539748421, 0.09011906278589261, 2.089832317302934,       2.160228985413543, -0.6734226930504117, -0.3333652792566645};
        } else if (y > 500) {
            poly = new double[] {5.0948493625436, 29.53058813432996, 4.541038292359934e-11, -1.433948116687516e-15};
            amp  = new double[] {0.003064956920792383, 7.626671193494865, 0.3529404145144266, 0.06663245200646868, 0.0001656994624049456, 0.1780412300742746,     0.0004129816755068378, 0.006095944636667532, 0.008695590022889563, 0.003424299117184891, 0.0005515527343732152, 0.003742333984175168, 1.224053299174028, 176991.1834881092, 1.334720614777735,           0.3405821721536166, 7.109371433096744, 0.0003560766227481428,       0.009224952244699717, 0.002167310175417191, 0.00145747297333307,   6.905311594176517e-05, 0.0007663900274006137, 0.001450045869473766};
            ph   = new double[] {-0.0001281591820683625, 0.3999218900311343, 0.7995524274617156, 1.508412085555246, -1.881674550900291, -0.3372987373864469, 1.194027417083641, -2.796075179602183, 0.4393400618829684, -0.4472253032575864, -3.092352779831784, 0.7922405652032801, 2.74642858200881, 3.141592653536662, -2.749358740054584, -0.802473527945417, -0.4028522786681306, -3.042507206934622, -0.6685214893088991, 0.08024121301611123, 2.074730054920348, 2.441134296311371, 2.530597648408565, -0.3581563380138128};
        } else if (y >-500) {
            poly = new double[] {5.130095079700586, 29.53059287487281, 2.636980277832712e-10, 2.052305877536176e-15};
            amp  = new double[] {0.00306543297257588, 14.09588496307647, 0.6617066917316977, 0.06231761740474433, 0.0001621481421580083, 0.1803727596053063,     0.0004122954668341743, 0.006230329593671887, 0.008887770370333892, 0.003500155329482855, 0.000558947967625051, 0.003741096738952335,   2.608226638669044, 50542.18069263954, 2.718878820026565,           0.6493570893966033, 13.57867763180954, 0.0003633688807409536,       0.009224258842274426, 0.002262774438471774, 0.001454301982165044,   5.929659866201886e-05, 0.000763615236511344, 0.001482096374627237};
            ph   = new double[] {-0.0001911116155991919, 0.4564474773113714, 0.9125642790676649, 1.558420410352335, -1.615075137671266, -0.3102316104986274, 1.367043231973365, -2.747995863411374, 0.4922894548028837, -0.5077715423980653, -3.111138869000636, 0.9013875085046367, 2.694602435147117, 1.52125540685336e-10, -2.700307554743696, -0.9182634836956796, -0.4621531782893399, -2.988266067737921, -0.6739776220614646, 0.07536718624267376, 2.011936054858174, 2.670273005931625, -0.5598053887004412, -0.4183716494621109};
        } else if (y > -1500) {
            poly = new double[] {5.079718676105367, 29.53058724850103, 5.440354464348641e-11, -5.468236785612416e-16};
            amp  = new double[] {0.003066172321651567, 20.20421099746479, 0.953147358211669, 0.05777263834741492, 0.000167155937367274, 0.1817914870452167,     0.0004110619112965207, 0.00635489365137433, 0.009064912566900313,   0.003569740807369779, 0.0005688818240760059, 0.003735749079425952, 3.915275156924653, 39403.13175717759, 4.025902627484639,           0.9408112231195798, 19.68714067094437, 0.0003711473110688735,       0.009223564429585131, 0.002355406030018675, 0.001455968390804814,   6.18620656762775e-05, 0.0007611302984458841, 0.00151155168787448};
            ph   = new double[] {-0.0001182377120875701, 0.5474342061780443, 1.095125553421923, 1.593634986164386, -1.391341374922795, -0.2836974095888444, 1.637785145483585, -2.665415976998966, 0.5797263424578055, -0.6027880638180879, -3.11321101839494, 1.078250080139086, 2.608426657200007, 1.93198194091318e-10, -2.616940287603615, -1.103645618516418, -0.5559504104878054, -2.935100907832121, -0.6895175116390319, 0.06532581668357881, 1.906582152940109, 2.810602254413188, 2.623859337267746, -0.5136092265706852};
        } else if (y > -2500) {
            poly = new double[] {4.920327568643614, 29.53057810854203, -1.167792190030497e-10, -1.583855893697448e-15};
            amp  = new double[] {0.003066403588146123, 25.97285431650071, 1.227398706424804, 0.05306121616330708, 0.0001674282816546016, 0.1838020089526355,     0.0004104047888941337, 0.006468463012973411, 0.009227336473996919, 0.003634052696176652, 0.0005798186158708589, 0.003732868199411895, 5.149697403148286, 12401.77151905057, 5.260291570325439,           1.215078544477321, 25.45595777650416, 0.0003775792151000861,       0.009222544052606635, 0.002444243008103569, 0.001453903031915251,   5.421306878220423e-05, 0.000759081715535765, 0.001538847376383916};
            ph   = new double[] {-0.0001507793344783692, 0.6709668630334686, 1.341794792859588, 1.638502778621967, -1.130532781281018, -0.2567494329437287, 2.011373739177783, -2.550428654484634, 0.6995983719242018, -0.7302217908960755, -3.137243552882407, 1.32121552913955, 2.489673207882953, 3.141592652896045, -2.501007850254794, -1.353130434244637, -0.6823038095853962, -2.878539068363566, -0.7144571457715599, 0.06005904102994138, 1.756101076165754, -3.106746594121269, -0.4865573476299297, -0.6407793196413091};
        } else {
            poly = new double[] {5.196025334677748, 29.53059367589925, 1.791808676644833e-10, 3.084238604788304e-16};
            amp  = new double[] {0.003067074674386277, 31.28212124873814, 1.480358213218683,   0.04838496132256848, 0.0001616484395521156, 0.1848643427245596,     0.0004081562570123425, 0.006571587117762985, 0.009374087165905235, 0.003691430314517222, 0.0005838974444010396, 0.003725586348669139, 6.285551836276414, 14576.13697619591, 6.396107897119155,           1.468057505710156, 30.76542268690632, 0.0003836638780947706,       0.009222306378168128, 0.002520799603456325, 0.001452713690208897,   4.996384744103974e-05, 0.0007564825878224146, 0.001563166684692854};
            ph   = new double[] {-2.888275750894949e-05, 0.824846111325144, 1.649741803946661, 1.663775730196169, -0.9078368027144129, -0.2311274511667410,       2.4714245474208, -2.405036659816741, 0.8498606487973193,           -0.8881264552237513, 3.13858712419766, 1.623989809477561,           2.340584258762797, 5.43689811418326e-10, -2.354742450426987,       -1.663903006717207, -0.8390067716987786, -2.82704991559508,         -0.7480552897281627, 0.05087287783658471, 1.570826541508687,       -2.99806369998241, 2.679366682613425, -0.7988224904722718};
        }

        double jd0 = Date.of(y - 1,12,31).toJulianDate() - 1.0 / 3;
        double deltaT = deltaT((jd0 - 2451545 + 365.25 * 0.5) / 36525);
        double offset = 2451545 - jd0 - deltaT;
        double jdL0 = 2451550.259469 + 0.25 * lunarData.get(0) * 29.5306;

        // Find the lunation number of the first moon phase in the year
        double lm0 = Math.floor((jd0 + 1 - jdL0) / 29.5306) - 1;
        double lm = 0, s = 0, ang, s1;
        for (int i = 0; i < 10; i++) {
            lm = lm0 + 0.25 * lunarData.get(0) + i;
            s  = poly[0] + offset + lm * (poly[1] + lm * (poly[2] + lm * poly[3]));
            for (int j = 0; j < 24; j++) {
                ang = mod2piDe(w[j] * lm) + ph[j];
                s += amp[j] * Math.sin(ang);
            }
            s1 = Math.floor((s - Math.floor(s)) * 1440 + 0.5);
            s = s1 + 1441 * Math.floor(s) + lunarData.get(1) - offsetLunar;
            if (s > 1440) { break; }
        }
        lm0 = lm;
        List<Integer> data = new ArrayList<>();
        data.add(lunarData.get(0));
        data.add(new Double(s).intValue());

        // Now decompress the remaining moon-phase times
        for (int i = 2; i < lunarData.size(); i++) {
            lm = lm0 + (i - 1) * dp;
            s = poly[0] + offset + lm * (poly[1] + lm * (poly[2] + lm * poly[3]));
            for (int j = 0; j < 24; j++) {
                ang = mod2piDe(w[j] * lm) + ph[j];
                s += amp[j] * Math.sin(ang);
            }
            s1 = Math.floor((s - Math.floor(s)) * 1440 + 0.5);
            data.add(new Double(s1 + 1441 * Math.floor(s) + lunarData.get(i) - offsetLunar).intValue());
        }
        return data;
    }

    public static List<Integer> getSolarData(int year) {
        if (isValid(year)) {
            List<Integer> data = solarDataCache.get(year);
            if (data == null) {
                data = decompressSolarData(year, 0, SOLAR_DATA.get(year - minYear));
                solarDataCache.put(year, data);
            }
            return data;
        } else {
            throw new DateTimeException("Year is out of range(-3501 - 3501)");
        }
    }

    public static List<Integer> getLunarData(int year) {
        if (isValid(year)) {
            List<Integer> data = lunarDataCache.get(year);
            if (data == null) {
                data = decompressLunarData(year, LUNAR_DATA.get(year - minYear), 0.25);
                lunarDataCache.put(year, data);
            }
            return data;
        } else {
            throw new DateTimeException("Year is out of range(-3501 - 3501)");
        }
    }

    private static final double D = 0.2422;

}
