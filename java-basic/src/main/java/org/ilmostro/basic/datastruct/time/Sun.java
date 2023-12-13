package org.ilmostro.basic.datastruct.time;

import org.shredzone.commons.suncalc.SunTimes;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;

public class Sun {

    /**
     * 根据经纬度计算日出日落时间
     * @param lat 纬度
     * @param lng 经度
     * @param isSunRise true计算日出，false计算日落
     * @return int[] 0位置为小时，1位置为分钟
     */
    public static int[] getDayTime(double lat, double lng, boolean isSunRise){
        lat = Math.toRadians(lat);

        Calendar cNow = Calendar.getInstance();
        Calendar cStart = Calendar.getInstance();
        cStart.set(2000, 0, 1);

        //2000年1月1日到今天天数
        int dayCount = (int) ((cNow.getTimeInMillis() - cStart.getTimeInMillis()) / (1000 * 3600 * 24));

        double ut0 = 180;
        double utStart = 0;
        final double h = Math.sin(Math.toRadians(-0.833));

        for(; Math.abs(utStart - ut0) >= 0.1; ut0 = utStart){
            double t = (dayCount + ut0 / 360d) / 36525; // 世纪数
            double L = 280.460 + 36000.777 * t; // 太阳平均黄径
            double G = Math.toRadians(357.528 + 35999.050 * t); // 太阳平近点角
            double lamda = Math.toRadians(L + 1.915 * Math.sin(G) + 0.020 * Math.sin(2 * G)); // 太阳黄道经度
            double epc = Math.toRadians(23.4393- 0.0130 * t); // 地球倾角
            double sigam = Math.asin(Math.sin(epc) * Math.sin(lamda));// 太阳的偏差

            // 格林威治时间太阳时间角
            double gha = ut0 - 180 - 1.915 * Math.sin(G) - 0.020 * Math.sin(2 * G)
                    + 2.466 * Math.sin(2 * lamda) - 0.053 * Math.sin(4 * lamda);
            // 修正值e
            double e = Math.toDegrees(Math.acos((h - Math.tan(lat) * Math.tan(sigam))));

            utStart = ut0 - gha - lng + (isSunRise ? -e : e);
        }

        int zone = (int) (lng / 15 + (lng >= 0 ? 1 : -1));// 当前时区
        return new int[]{
                (int) (utStart / 15 + zone ),
                (int) (60 * (utStart / 15 + zone - (int) (utStart / 15 + zone)))
        };
    }

    private static Object execute_java_script(double lat, double lng){
        // 当前日期
        String date = "new Date()";
        // 使用 JavaScript 引擎执行 Suncalc 脚本
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        try {
            // 加载 Suncalc 库
            engine.eval("load('suncalc.js')");
            // 计算日出和日落时间
            String script = "Suncalc.getTimes(" + date + ", " + lat + ", " + lng + ")";
            return engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object common_suncalc(double lat, double lng){
        ZonedDateTime time = ZonedDateTime.now();
        return SunTimes.compute().on(time)
                .latitude(30.29)
                .longitude(120.21)
                .execute();
    }

    public static void main(String[] args) {
//        final var dayTime = getDayTime(120.21, 30.29, false);
//        System.out.println(Arrays.toString(dayTime));
        final var execute_java_script = common_suncalc(30.29, 120.21);
        System.out.println(execute_java_script);
    }
}
