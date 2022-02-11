/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ffmpeg工具类
 */
public class FfmpegUtil {

    public static final Logger logger = LoggerFactory.getLogger(FfmpegUtil.class);

    // ffmpeg安装目录
    public static String FFMPEG_PATH = "D:\\ffmpeg.exe";

    // 设置图片大小
    private final static String IMG_SIZE = "1920x1080";

    /**
     * 视频截图
     *
     * @param videoPath 视频地址
     * @param imagePath 图片地址
     * @param timePoint 截图时间点
     * @return
     */
    public static boolean ffmpegToImage(String videoPath, String imagePath, int timePoint) {
        List<String> commands = new ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-ss");
        commands.add(timePoint + "");//这个参数是设置截取视频多少秒时的画面
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        commands.add(IMG_SIZE); //这个参数是设置截取图片的大小
        commands.add(imagePath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功:" + imagePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查文件能否被ffmpeg解析
     *
     * @param fileName 文件名
     * @return
     */
    public static int checkFileType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
                .toLowerCase();
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        } else if (type.equals("png")) {
            return 1;
        } else if (type.equals("jpg")) {
            return 1;
        } else if (type.equals("jpeg")) {
            return 1;
        }
        return 9;
    }

    /**
     * 获取视频时长
     *
     * @param videoPath 视频地址
     * @return
     */
    static int getVideoTime(String videoPath) {
        List<String> commands = new ArrayList<String>();
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getTimelen(m.group(1));
                System.out.println(videoPath + ",视频时长：" + time + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取时间
     * 格式:"00:00:10.68"
     *
     * @param timelen 时间长度
     * @return
     */
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    /**
     * 秒转化成 hh:mm:ss
     *
     * @param duration 时间
     * @return
     */
    public static String convertInt2Date(long duration) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(cal.getTimeInMillis() + duration * 1000);
    }

    /**
     * 视频抽取音频文件
     *
     * @param videoPath 视频地址
     * @param type      类型
     * @param audioPath 音频输出地址
     * @return
     */
    public static boolean ffmpegToAudio(String videoPath, String type, String audioPath) {
        List<String> commands = new ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-f");
        commands.add(type);
        commands.add("-vn");
        commands.add("-y");
        commands.add("-acodec");
        if ("wav".equals(type)) {
            commands.add("pcm_s16le");
        } else if ("mp3".equals(type)) {
            commands.add("mp3");
        }
        commands.add("-ar");
        commands.add("16000");
        commands.add("-ac");
        commands.add("1");
        commands.add(audioPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            System.out.println("抽离成功:" + audioPath);

            // 1. start
            BufferedReader buf = null; // 保存ffmpeg的输出结果流
            String line = null;

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                continue;
            }
            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            // 1. end
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * wav 转 mp3
     * 备注：wav转mp3命令：ffmpeg -i test.wav -f mp3 -acodec libmp3lame -y wav2mp3.mp3
     *
     * @param wavPath WAV文件地址
     * @param mp3Path MP#文件输出地址
     * @return
     */
    public static boolean ffmpegOfwavTomp3(String wavPath, String mp3Path) {
        List<String> commands = new ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(wavPath);
        commands.add("-f");
        commands.add("mp3");
        commands.add("-acodec");
        commands.add("libmp3lame");
        commands.add("-y");
        commands.add(mp3Path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            System.out.println("转换成功:" + mp3Path);

            // 1. start
            BufferedReader buf = null; // 保存ffmpeg的输出结果流
            String line = null;

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                continue;
            }
            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            // 1. end
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 拷贝视频
     *
     * @param videoInputPath 视频的输入路径
     * @param videoOutPath   视频的输出路径
     * @throws Exception
     */
    // 拷贝视频，并指定新的视频的名字以及格式
    // ffmpeg.exe -i old.mp4 new.avi
    public static void convetor(String videoInputPath, String videoOutPath) throws Exception {

        List<String> command = new ArrayList<String>();
        command.add(FFMPEG_PATH);
        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }

    /**
     * 视频和音频结合
     *
     * @param videoInputPath 原视频的路径
     * @param audioInputPath 音频的路径
     * @param videoOutPath   视频与音频结合之后的视频的路径
     * @param time           视频的长度 ,单位为 s
     * @throws Exception
     */
    // 将视频和音频结合，并指定视频的长度，同时生成结合之后的视频文件
    // ffmpeg.exe -i tsd.mp4 -i "周笔畅+-+最美的期待.mp3" -t 7 -y new.avi
    public static void convetor(String videoInputPath, String audioInputPath, String videoOutPath, double time)
            throws Exception {

        List<String> command = new ArrayList<String>();
        command.add(FFMPEG_PATH);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(audioInputPath);
        command.add("-t");
        command.add(String.valueOf(time));
        command.add("-y");
        command.add(videoOutPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }


    /**
     * 视频截图
     *
     * @param time_coverimg   视频的第几秒作为封面图
     * @param videoInputPath  视频的路径
     * @param frame           帧数
     * @param coverOutputPath 视频的封面图的路径
     * @throws Exception
     */
    // ffmpeg.exe -ss 00:00:01 -y -i 视频.mp4 -vframes 1 new.jpg
    public static void convetor(String time_coverimg, String videoInputPath, int frame, String coverOutputPath)
            throws Exception {

        List<String> command = new ArrayList<String>();
        command.add(FFMPEG_PATH);
        command.add("-ss");
        command.add(time_coverimg);
        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);
        command.add("-vframes");
        command.add(String.valueOf(frame));
        command.add(coverOutputPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }

//    public static void main(String[] args) {
//        // 视频文件
//        String videoRealPath = "D:\\huawei.mp4";
////        // 截图的路径（输出路径）
////        String imageRealPath1 = "D:\\31.jpg";
////        String imageRealPath2 = "D:\\32.jpg";
////        // 检查文件能否被解析
////        if (checkFileType(videoRealPath) == 0) {
////            ffmpegToImage(videoRealPath, imageRealPath1, 10);
////            ffmpegToImage(videoRealPath, imageRealPath2, 11);
////        }
////        String audioRealPath = "D:\\huawei.wav";
////        String audioType = "wav";
////        ffmpegToAudio(videoRealPath, audioType, audioRealPath);
////
////        String wavPath = "D:\\huawei.wav";
////        String mp3Path = "D:\\huawei.mp3";
////        ffmpegOfwavTomp3(wavPath, mp3Path);
////
////        // 获取视频时长
////        getVideoTime(videoRealPath);
//
//
//        String coverOutputPath = "D:\\33.jpg";
//        try {
//            convetor("00:00:01", videoRealPath, 1, coverOutputPath);
//            convetor(videoRealPath, "D:\\huawei.flv");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
