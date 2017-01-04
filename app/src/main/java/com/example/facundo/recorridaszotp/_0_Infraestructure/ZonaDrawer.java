package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by gonzalo on 25/08/16.
 */
public class ZonaDrawer {

    private static int purpleStrokeColor = Color.parseColor("#3b1684");
    private static int greenStrokeColor = Color.parseColor("#2aa31f");
    private static int purpleFillColor = 0x274e1fad;
    private static int greenFillColor = 0x2738db29;

    public static void draw(GoogleMap googleMap, String name) {
        PolygonOptions polygon = new PolygonOptions();
        int strokeColor = purpleStrokeColor;
        int fillColor = purpleFillColor;
        switch (name) {
            case "Ciudadela":
                polygon.add(new LatLng(-34.627008, -58.565394),
                        new LatLng(-34.618014, -58.543605),
                        new LatLng(-34.621034, -58.541937),
                        new LatLng(-34.620416, -58.540628),
                        new LatLng(-34.620010, -58.541122),
                        new LatLng(-34.619103, -58.538788),
                        new LatLng(-34.623945, -58.532576),
                        new LatLng(-34.622607, -58.531062),
                        new LatLng(-34.654543, -58.529076)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Haedo":
                polygon.add(new LatLng(-34.632693, -58.620531),
                        new LatLng(-34.633480, -58.619137),
                        new LatLng(-34.636001, -58.602394),
                        new LatLng(-34.635478, -58.586819),
                        new LatLng(-34.642322, -58.577665),
                        new LatLng(-34.642719, -58.577527),
                        new LatLng(-34.643461, -58.581886),
                        new LatLng(-34.649336, -58.580723),
                        new LatLng(-34.650484, -58.583062),
                        new LatLng(-34.659867, -58.593466),
                        new LatLng(-34.636811, -58.621079),
                        new LatLng(-34.634735, -58.621677),
                        new LatLng(-34.633918, -58.620779)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Liniers":
                polygon.add(new LatLng(-34.634780, -58.530340),
                        new LatLng(-34.633633, -58.519810),
                        new LatLng(-34.634709, -58.510856),
                        new LatLng(-34.640103, -58.509872),
                        new LatLng(-34.645752, -58.502341),
                        new LatLng(-34.656943, -58.525715),
                        new LatLng(-34.654349, -58.529300)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Ramos":
                polygon.add(new LatLng(-34.634662, -58.556171),
                        new LatLng(-34.651741, -58.532967),
                        new LatLng(-34.658220, -58.541853),
                        new LatLng(-34.666264, -58.550333),
                        new LatLng(-34.668864, -58.552383),
                        new LatLng(-34.673064, -58.557039),
                        new LatLng(-34.667901, -58.562925),
                        new LatLng(-34.663636, -58.565897),
                        new LatLng(-34.650680, -58.583256),
                        new LatLng(-34.649414, -58.580771),
                        new LatLng(-34.647014, -58.581093),
                        new LatLng(-34.643426, -58.581886),
                        new LatLng(-34.641556, -58.570235),
                        new LatLng(-34.638284, -58.571003),
                        new LatLng(-34.637873, -58.564352),
                        new LatLng(-34.637271, -58.561778),
                        new LatLng(-34.636468, -58.562065)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "San Justo":
                polygon.add(new LatLng(-34.661794, -58.568635),
                        new LatLng(-34.663694, -58.565737),
                        new LatLng(-34.667699, -58.563137),
                        new LatLng(-34.669592, -58.561360),
                        new LatLng(-34.673157, -58.556987),
                        new LatLng(-34.668763, -58.552326),
                        new LatLng(-34.667188, -58.551058),
                        new LatLng(-34.666835, -58.549811),
                        new LatLng(-34.673759, -58.541875),
                        new LatLng(-34.679032, -58.538952),
                        new LatLng(-34.679412, -58.539391),
                        new LatLng(-34.679730, -58.539133),
                        new LatLng(-34.682360, -58.542660),
                        new LatLng(-34.682866, -58.542026),
                        new LatLng(-34.691984, -58.552110),
                        new LatLng(-34.700144, -58.542084),
                        new LatLng(-34.708300, -58.552454),
                        new LatLng(-34.714145, -58.562679),
                        new LatLng(-34.694952, -58.588085),
                        new LatLng(-34.691812, -58.584609),
                        new LatLng(-34.692416, -58.583626),
                        new LatLng(-34.689200, -58.580095),
                        new LatLng(-34.682507, -58.588845),
                        new LatLng(-34.679759, -58.583962),
                        new LatLng(-34.678728, -58.585533),
                        new LatLng(-34.672237, -58.578393),
                        new LatLng(-34.671473, -58.579342)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Tres de Febrero":
                polygon.add(new LatLng(-34.547741, -58.617818),
                        new LatLng(-34.591369, -58.562278),
                        new LatLng(-34.597388, -58.522935),
                        new LatLng(-34.615510, -58.531407),
                        new LatLng(-34.654490, -58.529145),
                        new LatLng(-34.626031, -58.566815),
                        new LatLng(-34.628805, -58.572241),
                        new LatLng(-34.606117, -58.588907),
                        new LatLng(-34.608063, -58.592764),
                        new LatLng(-34.569484, -58.638495),
                        new LatLng(-34.562152, -58.644609),
                        new LatLng(-34.556250, -58.639080),
                        new LatLng(-34.552201, -58.631084),
                        new LatLng(-34.548141, -58.625526)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Villa Luro":
                polygon.add(new LatLng(-34.624277, -58.511616),
                        new LatLng(-34.632209, -58.500997),
                        new LatLng(-34.631790, -58.499952),
                        new LatLng(-34.641290, -58.487762),
                        new LatLng(-34.644856, -58.495282),
                        new LatLng(-34.643428, -58.497120),
                        new LatLng(-34.645806, -58.502272),
                        new LatLng(-34.640069, -58.509793),
                        new LatLng(-34.637509, -58.510490),
                        new LatLng(-34.634790, -58.510844),
                        new LatLng(-34.634587, -58.508344),
                        new LatLng(-34.628447, -58.516348)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Villa Sarmiento":
                polygon.add(new LatLng(-34.625980, -58.566816),
                        new LatLng(-34.634702, -58.556152),
                        new LatLng(-34.636481, -58.562079),
                        new LatLng(-34.637262, -58.561780),
                        new LatLng(-34.637797, -58.564367),
                        new LatLng(-34.637959, -58.564328),
                        new LatLng(-34.638268, -58.566398),
                        new LatLng(-34.638357, -58.570990),
                        new LatLng(-34.641596, -58.570250),
                        new LatLng(-34.642761, -58.577556),
                        new LatLng(-34.642302, -58.577653),
                        new LatLng(-34.635678, -58.586569),
                        new LatLng(-34.629969, -58.576949),
                        new LatLng(-34.630797, -58.576376),
                        new LatLng(-34.630908, -58.574331),
                        new LatLng(-34.629760, -58.571553),
                        new LatLng(-34.628803, -58.572239)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 1":
                polygon.add(new LatLng(-34.61009197114083, -58.4060375526253),
                        new LatLng(-34.60769011603498, -58.406123383313776),
                        new LatLng(-34.59857656330151, -58.40376303938067),
                        new LatLng(-34.59953035404766, -58.400930626660944),
                        new LatLng(-34.59960100477833, -58.39299128797686),
                        new LatLng(-34.60927958674872, -58.39209006574786)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 2":
                polygon.add(new LatLng(-34.60919128361864, -58.39209006574786),
                        new LatLng(-34.59958334210143, -58.39299128797686),
                        new LatLng(-34.599053460043805, -58.381962044507624),
                        new LatLng(-34.60837889041364, -58.38131831434405)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 3":
                polygon.add(new LatLng(-34.60837889041364, -58.38131831434405),
                        new LatLng(-34.60371630612957, -58.381489975721),
                        new LatLng(-34.603009831126386, -58.37033198621905),
                        new LatLng(-34.60763713315387, -58.36990283277667)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 4":
                polygon.add(new LatLng(-34.60837889041364, -58.38131831434405),
                        new LatLng(-34.60763713315387, -58.36990283277667),
                        new LatLng(-34.61229949727399, -58.369473679334284)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 5":
                polygon.add(new LatLng(-34.60931490797466, -58.3921329810921),
                        new LatLng(-34.608431872821406, -58.38136122968829),
                        new LatLng(-34.617650297021015, -58.38097499159014),
                        new LatLng(-34.61814474295427, -58.39174674299395)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 6":
                polygon.add(new LatLng(-34.61012729202111, -58.406034870416306),
                        new LatLng(-34.60920894425221, -58.39204446819463),
                        new LatLng(-34.61821537784721, -58.3917869761292),
                        new LatLng(-34.618815772009555, -58.40350286510625)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 7":
                polygon.add(new LatLng(-34.59841759711242, -58.403803272515916),
                        new LatLng(-34.59439035217253, -58.402344150811814),
                        new LatLng(-34.5958034428078, -58.39337484386601),
                        new LatLng(-34.599618667451736, -58.39294569042363),
                        new LatLng(-34.59958334210143, -58.401099605828904)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 8":
                polygon.add(new LatLng(-34.5958387697657, -58.39341775921025),
                        new LatLng(-34.59537951814119, -58.3822597697083),
                        new LatLng(-34.599088785619486, -58.38178770092168),
                        new LatLng(-34.599618667451736, -58.39298860576787)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 9":
                polygon.add(new LatLng(-34.595397181712066, -58.38200227764287),
                        new LatLng(-34.59511456412586, -58.377882404595994),
                        new LatLng(-34.596280355471634, -58.37599412944951),
                        new LatLng(-34.5938074452825, -58.37389127758183),
                        new LatLng(-34.5983999341836, -58.37131635692754),
                        new LatLng(-34.5990711228334, -58.381830616265916)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 10":
                polygon.add(new LatLng(-34.61305887269422, -58.381101055413865),
                        new LatLng(-34.612281837297644, -58.36942808178105),
                        new LatLng(-34.62247101922263, -58.36841957119145),
                        new LatLng(-34.62278885919803, -58.380607528955125)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 11":
                polygon.add(new LatLng(-34.602868535404085, -58.42415587452092),
                        new LatLng(-34.60414018824616, -58.413276834756516),
                        new LatLng(-34.604564068199544, -58.40550915744939),
                        new LatLng(-34.607831403552424, -58.4061421587769),
                        new LatLng(-34.611610755420024, -58.405798836022996),
                        new LatLng(-34.614648240624284, -58.405069275170945),
                        new LatLng(-34.615813757766134, -58.42012183216252),
                        new LatLng(-34.60857315907665, -58.42147366550603),
                        new LatLng(-34.6086438021143, -58.42467085865178)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 13":
                polygon.add(new LatLng(-34.60200309386211, -58.43213812854924),
                        new LatLng(-34.599495028659454, -58.42992798832097),
                        new LatLng(-34.59792303368877, -58.42673079517522),
                        new LatLng(-34.59776406624879, -58.4201003744904),
                        new LatLng(-34.598117326813366, -58.40924279239812),
                        new LatLng(-34.59804667482064, -58.4046293928925),
                        new LatLng(-34.5984705858755, -58.4037281706635),
                        new LatLng(-34.60458172981732, -58.40544478443303),
                        new LatLng(-34.60403421791974, -58.41038004902043),
                        new LatLng(-34.604157849954085, -58.413298292428635),
                        new LatLng(-34.60316878852565, -58.42093722370305)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 14":
                polygon.add(new LatLng(-34.58887906901956, -58.43837158229985),
                        new LatLng(-34.587607182527535, -58.43785659816899),
                        new LatLng(-34.58075279240703, -58.42884437587895),
                        new LatLng(-34.57799674425241, -58.42661277797856),
                        new LatLng(-34.58905571838124, -58.41026203182378),
                        new LatLng(-34.59329519039617, -58.40511219051518),
                        new LatLng(-34.59442567973072, -58.40240852382817),
                        new LatLng(-34.598488248788975, -58.403824730188035),
                        new LatLng(-34.59802901181287, -58.40472595241704),
                        new LatLng(-34.59771107703433, -58.423265381127976),
                        new LatLng(-34.59598007744638, -58.42747108486333)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 15":
                polygon.add(new LatLng(-34.58744819534759, -58.39723722484746),
                        new LatLng(-34.58479836422339, -58.391829891473435),
                        new LatLng(-34.58508101690248, -58.38899747875371),
                        new LatLng(-34.588932063868015, -58.38153020885625),
                        new LatLng(-34.595397181712165, -58.38200227764287),
                        new LatLng(-34.59589176017437, -58.39337484386601),
                        new LatLng(-34.59546783595847, -58.39792387035527)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 17":
                polygon.add(new LatLng(-34.611540114907235, -58.42114107158818),
                        new LatLng(-34.615849076212314, -58.42011110332646),
                        new LatLng(-34.61468355956639, -58.40496198681035),
                        new LatLng(-34.61881577200948, -58.403545780450486),
                        new LatLng(-34.62065224483916, -58.412643833429),
                        new LatLng(-34.62111135669698, -58.41822282817998)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 19":
                polygon.add(
                        new LatLng(-34.62298309413939, -58.447255058557175),
                        new LatLng(-34.61510738569462, -58.42935936000981),
                        new LatLng(-34.62697363866983, -58.42682735469975),
                        new LatLng(-34.62958681162728, -58.444508476525925)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 20":
                polygon.add(new LatLng(-34.58518701140894, -58.41596977260747),
                        new LatLng(-34.581971784556835, -58.41116325405278),
                        new LatLng(-34.57783773868629, -58.40867416408696),
                        new LatLng(-34.57932177881002, -58.406099243432664),
                        new LatLng(-34.58144179016681, -58.40339557674565),
                        new LatLng(-34.58472770090302, -58.391808433801316),
                        new LatLng(-34.58886140406278, -58.399576111108445),
                        new LatLng(-34.5898859653517, -58.400305671960496),
                        new LatLng(-34.594478671041, -58.40236560848393),
                        new LatLng(-34.59338351042959, -58.405069275170945),
                        new LatLng(-34.58903805346208, -58.410090370446824)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 21":
                polygon.add(new LatLng(-34.61881577200902, -58.40352432277837),
                        new LatLng(-34.617614979341184, -58.38099376705327),
                        new LatLng(-34.62287714786378, -58.38073627498784),
                        new LatLng(-34.623654084074325, -58.39163677242436),
                        new LatLng(-34.625914220780444, -58.401807709008835)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 22":
                polygon.add(new LatLng(-34.599071122832996, -58.38189498928227),
                        new LatLng(-34.598364608314554, -58.37125198391118),
                        new LatLng(-34.60066075852621, -58.37056533840337),
                        new LatLng(-34.602992169173774, -58.3702649309937),
                        new LatLng(-34.60366332071228, -58.38168041256108)
                );
                strokeColor = purpleStrokeColor;
                fillColor = purpleFillColor;
                break;
            case "Zona 23":
                polygon.add(new LatLng(-34.62205605964442, -58.42784659412541),
                        new LatLng(-34.621420372862914, -58.422568006784104),
                        new LatLng(-34.62064341573949, -58.41265456226506),
                        new LatLng(-34.618806942714436, -58.403556509286545),
                        new LatLng(-34.623751200589055, -58.4022690489594),
                        new LatLng(-34.62583476389332, -58.40175406482854),
                        new LatLng(-34.62678824151532, -58.41016547229924),
                        new LatLng(-34.626858869051446, -58.41196791675725),
                        new LatLng(-34.62887172855814, -58.42638747242131)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 24":
                polygon.add(new LatLng(-34.61135468327355, -58.46788661029973),
                        new LatLng(-34.610295065996205, -58.46608416584172),
                        new LatLng(-34.608140469159295, -58.46393839862981),
                        new LatLng(-34.60450225250757, -58.45861689594426),
                        new LatLng(-34.597543277632795, -58.456256552011155),
                        new LatLng(-34.59782588695585, -58.455484075814866),
                        new LatLng(-34.59432852890773, -58.450505895883225),
                        new LatLng(-34.591678917206316, -58.4476734831635),
                        new LatLng(-34.59983945055006, -58.43887583759465),
                        new LatLng(-34.602029587104084, -58.43213812854924),
                        new LatLng(-34.6028420424292, -58.42419878986516),
                        new LatLng(-34.60867029323767, -58.42471377399602),
                        new LatLng(-34.60881157908758, -58.441493673593186),
                        new LatLng(-34.60838772081656, -58.4416224196259),
                        new LatLng(-34.60757531974905, -58.44608561542668),
                        new LatLng(-34.60870561472267, -58.448789282113694),
                        new LatLng(-34.61393302882114, -58.459475202829026),
                        new LatLng(-34.61573429120781, -58.464496298104905)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            case "Zona 27":
                polygon.add(new LatLng(-34.59535302277691, -58.381583853036545),
                        new LatLng(-34.59037174430583, -58.381583853036545),
                        new LatLng(-34.58938251871046, -58.380811376840256),
                        new LatLng(-34.59277410028857, -58.37540404346623),
                        new LatLng(-34.59510573231004, -58.37763564136662)
                );
                strokeColor = greenStrokeColor;
                fillColor = greenFillColor;
                break;
            default:
                return;
        }
        polygon.strokeColor(strokeColor)
                .strokeWidth(2)
                .fillColor(fillColor);
        googleMap.addPolygon(polygon);
    }

    public static void centrarEnZona(GoogleMap googleMap, String zona) {
        LatLng ubicacion = null;
        switch (zona) {
            case "Ciudadela":
                ubicacion = new LatLng(-34.633990, -58.541851);
                break;
            case "Haedo":
                ubicacion = new LatLng(-34.645314, -58.593969);
                break;
            case "Liniers":
                ubicacion = new LatLng(-34.644772, -58.518225);
                break;
            case "Ramos":
                ubicacion = new LatLng(-34.652899, -58.557034);
                break;
            case "San Justo":
                ubicacion = new LatLng(-34.689364, -58.563382);
                break;
            case "Tres de Febrero":
                ubicacion = new LatLng(-34.599354, -58.575835);
                break;
            case "Villa Luro":
                ubicacion = new LatLng(-34.637727, -58.501150);
                break;
            case "Villa Sarmiento":
                ubicacion = new LatLng(-34.6346318,-58.5695524);
                break;
            case "Zona 1":
                ubicacion = new LatLng(-34.60518222258696, -58.39946882274783);
                break;
            case "Zona 2":
                ubicacion = new LatLng(-34.60401655618605, -58.38663713482058);
                break;
            case "Zona 3":
                ubicacion = new LatLng(-34.60560609722129, -58.37462083843386);
                break;
            case "Zona 4":
                ubicacion = new LatLng(-34.61033921698684, -58.37414876964724);
                break;
            case "Zona 5":
                ubicacion = new LatLng(-34.61358866538588, -58.38659421947634);
                break;
            case "Zona 6":
                ubicacion = new LatLng(-34.61429505038134, -58.39908258464968);
                break;
            case "Zona 7":
                ubicacion = new LatLng(-34.597410804182466, -58.398567600518824);
                break;
            case "Zona 8":
                ubicacion = new LatLng(-34.59748145671607, -58.387838764459254);
                break;
            case "Zona 9":
                ubicacion = new LatLng(-34.59691623476465, -58.37710992839968);
                break;
            case "Zona 10":
                ubicacion = new LatLng(-34.61719116601724, -58.37436334636843);
                break;
            case "Zona 11":
                ubicacion = new LatLng(-34.60723092994192, -58.41672079113162);
                break;
            case "Zona 13":
                ubicacion = new LatLng(-34.60108465624003, -58.41684953716433);
                break;
            case "Zona 14":
                ubicacion = new LatLng(-34.59016860072591, -58.42195646312869);
                break;
            case "Zona 15":
                ubicacion = new LatLng(-34.5907338685887, -58.39187280681765);
                break;
            case "Zona 17":
                ubicacion = new LatLng(-34.617968155459366, -58.41822282817996);
                break;
            case "Zona 19":
                ubicacion = new LatLng(-34.62503136220726, -58.44139711406863);
                break;
            case "Zona 20":
                ubicacion = new LatLng(-34.585451997084995, -58.40506927517092);
                break;
            case "Zona 21":
                ubicacion = new LatLng(-34.62088180108582, -58.39172260311282);
                break;
            case "Zona 23":
                ubicacion = new LatLng(-34.624130836784744, -58.41665641811526);
                break;
            case "Zona 24":
                ubicacion = new LatLng(-34.599583342101624, -58.44807045009768);
                break;
            case "Zona 27":
                ubicacion = new LatLng(-34.59276526822574, -58.37927715328372);
                break;
            default:
                return;
        }
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(ubicacion, Utils.ZOOM_ZONA));
    }
}
