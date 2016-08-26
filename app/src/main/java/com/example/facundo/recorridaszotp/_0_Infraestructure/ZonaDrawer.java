package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by gonzalo on 25/08/16.
 */
public class ZonaDrawer {
    public static void draw(GoogleMap googleMap, String name) {
        PolygonOptions polygon = new PolygonOptions();
        polygon.strokeColor(Color.parseColor("#FF0000"))
                .strokeWidth(2)
                .fillColor(0x27FF0000);
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
                googleMap.addPolygon(polygon);
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
                googleMap.addPolygon(polygon);
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
                googleMap.addPolygon(polygon);
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
                googleMap.addPolygon(polygon);
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
                googleMap.addPolygon(polygon);
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
                googleMap.addPolygon(polygon);
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
                googleMap.addPolygon(polygon);
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
                googleMap.addPolygon(polygon);
                break;
        }
    }
}
